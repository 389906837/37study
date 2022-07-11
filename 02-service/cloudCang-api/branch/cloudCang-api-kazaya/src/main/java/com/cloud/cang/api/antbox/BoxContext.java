package com.cloud.cang.api.antbox;

import com.cloud.cang.api.antbox.constant.AntboxCounter;
import com.cloud.cang.api.antbox.constant.BoxStatus;
import com.cloud.cang.api.antbox.constant.ParamKey;
import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.exception.*;
import com.cloud.cang.api.antbox.message.AntboxMessage;
import com.cloud.cang.api.antbox.message.AntboxMessageProcessed;
import com.cloud.cang.api.antbox.message.CardMessage;
import com.cloud.cang.api.antbox.protocol.AntboxAck;
import com.cloud.cang.api.antbox.protocol.AntboxDataPkg;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import com.cloud.cang.api.sb.service.DeviceSnapshotService;
import com.cloud.cang.model.sb.DeviceSnapshot;
import com.google.gson.reflect.TypeToken;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 售货机连接的上下文
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 下午5:14:54
 */
public class BoxContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Logger log = LoggerFactory.getLogger(BoxContext.class);
    private transient SimpleDateFormat connectIdSDF = new SimpleDateFormat("yyyyMMddHHmmssS");
    public transient SimpleDateFormat fullDateSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private transient ChannelHandlerContext channelContext;
    private transient CommandSender commandSender = new CommandSender(this);
    private transient BoxInfo boxInfo = new BoxInfo();
    private transient InventoryContext inventoryContext = new InventoryContext();
    private DeviceSnapshotService deviceSnapshotService;
    private Environment env;
    /**
     * 客户机最大响应超时时间 15s 将会与心跳时间比较
     */
    private static transient int MAX_TIME_OUT = 15 * 1000;
    /**
     * 登录超时时间
     */
    private static transient int SERVER_LOGIN_TIMEOUT = 30;
    /**
     * 当前用户
     */
    private CustomerDto currentCustomerDto;
    /**
     * 上一个用户
     */
    private CustomerDto lastCustomerDto;
    /**
     * 连接ID
     */
    private final String connectionId;
    /**
     * BUYS状态计数器
     */
    private volatile int busyStatusCounter = 0;
    /**
     * LOGIN状态计数器
     */
    private volatile int loginStatusCounter = 0;
    /**
     * INIT状态计数器
     */
    private volatile int initStatusCounter = 0;
    /**
     * 交易会话ID
     */
    private volatile byte[] sessionId;

    private transient ReentrantLock ackLock = new ReentrantLock();
    private transient Condition ackOk = ackLock.newCondition();
    /**
     * -->当服务器向设备发送ACK（AckableMessage）指令时，向待确认滚码集合（ackRollCodeSet）插入当前滚码
     * -->设备返回ACK数据包，正常情况下返回0x00的状态值，此时删除待确认集合中当前滚码
     *                      若返回非正常状态，则重新发送（10次）
     */
    private Set<Integer> ackRollCodeSet = Collections.synchronizedSet(new HashSet<Integer>());
    /**
     * 记录客户端发送来的滚码
     */
    private int clientRollCode = Integer.MIN_VALUE;
    /**
     * 记录服务端滚码
     * 长度为2个字节的滚码，高字节在前。滚码范围从0～65535
     */
    private volatile int rollCode = 0;
    /**
     * 上次脉搏时间戳
     */
    private long lastPulseTime;
    /**
     * 时间加密算法
     * 用于动态秘钥
     */
    private transient UUID dynameicSecretKeyUUID = UUID.randomUUID();
    /**
     * 下发获取售货机参数队列
     */
    private LinkedList<List<ParamKey>> getParamQueue = new LinkedList<List<ParamKey>>();


    BoxContext(ChannelHandlerContext channelContext, ApplicationContext appContext) {
        this.deviceSnapshotService = appContext.getBean(DeviceSnapshotService.class);
        this.env = appContext.getEnvironment();
        this.lastPulseTime = System.currentTimeMillis();
        this.connectionId = connectIdSDF.format(new Date()) + "-" + AntboxCounter.CONNECTION_COUNT.incrementAndGet();
        this.channelContext = channelContext;

        refreshSessionId();
    }

    void addFirstToGetParamQueue(List<ParamKey> keys) {
        getParamQueue.addFirst(keys);
    }

    public List<ParamKey> pollLastFromGetParamQueue() {
        return getParamQueue.pollLast();
    }

    public InventoryContext getInventoryContext() {
        return inventoryContext;
    }

    public ChannelHandlerContext getChannelContext() {
        return channelContext;
    }

    /**
     * 阻止下一轮盘点进行
     * @throws BoxStatusLimitedException
     */
    public void stopNextRoundInventory() throws BoxStatusLimitedException {
        BoxStatus status = boxInfo.getStatus();
        if (!BoxStatus.BUSY.equals(status)) {
            throw new BoxStatusLimitedException("StopNextRoundInventory setting has been blocked", BoxStatus.BUSY, status);
        }

        inventoryContext.stopNextRound();
    }

    /**
     * 当前是否允许盘点
     * @return
     */
    public boolean isAllowInventoryNow() {
        BoxStatus status = boxInfo.getStatus();
        return BoxStatus.IDLE.equals(status) || BoxStatus.INIT.equals(status);
    }

    /**
     * 当前是否允许开门
     * @return
     */
    public boolean isAllowOpenDoorNow() {
        return BoxStatus.LOGIN.equals(boxInfo.getStatus());
    }

    public void setBoxId(long boxId) {
        this.boxInfo.setBoxId(boxId);
        loadCoreDataFromDB();
    }

    /**
     * 从数据库加载核心数据
     */
    private void loadCoreDataFromDB() {
        int inventoryTimes = AntboxConfig.INVENTORY_COUNT;
        this.inventoryContext.presetInventoryConfig(false, inventoryTimes, 0);
        DeviceSnapshot boxSnapshot = getDeviceSnapshot(String.valueOf(boxInfo.getBoxId()));
        if(boxSnapshot !=null){
            if (StringUtils.isNoneEmpty(boxSnapshot.getLastInventoryRfid())) {
                inventoryContext.getLastInventoryLabels().clear();
                Set<String> labels = AntboxUtil.GSON.fromJson(boxSnapshot.getLastInventoryRfid(), new TypeToken<Set<String>>() {
                }.getType());
                inventoryContext.getLastInventoryLabels().addAll(labels);
                this.logInfo("recover lastInventoryLabels: {} from snapshot", boxSnapshot.getLastInventoryRfid());
            }

            if (StringUtils.isNotEmpty(boxSnapshot.getLastInventoryCustomer())) {
                this.lastCustomerDto = AntboxUtil.GSON.fromJson(boxSnapshot.getLastInventoryCustomer(), CustomerDto.class);
                this.logInfo("recover lastCustomerDto: {} from snapshot", boxSnapshot.getLastInventoryCustomer());
            }
        }
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public void ack(AntboxAck ack) throws SendCommandException {
        commandSender.sendCommand(ack);
    }

    /**
     * 用户登录，只有开门时才会登录
     *
     * @param loginUser
     * @throws BoxStatusLimitedException
     * @throws BoxTradingWithOtherException
     * @throws BoxTradingWithSelfException
     * @throws BoxHeartbeatTimeoutException
     */
    public synchronized void login(CustomerDto loginUser)
            throws BoxStatusLimitedException, BoxTradingWithOtherException,
            BoxTradingWithSelfException, BoxHeartbeatTimeoutException {

        checkHeartbeatTimeout();

        // 已经有用户登录
        if (this.currentCustomerDto != null) {
            // 异常情况，重复登录
            if (currentCustomerDto.equals(loginUser)) {
                this.logIncredible("Repeated login, loginAt: {}",fullDateSDF.format(currentCustomerDto.getLoginTime()));
                throw new BoxTradingWithSelfException();
            }

            BoxStatus status = boxInfo.getStatus();
            // 上一位用户登录成功后，售货机由于各种原因没上报已开门信息，导致上一位用户一直停留在登录状态
            if (BoxStatus.LOGIN.equals(status)) {
                // 检查一下是否登录超时
                long seconds = (System.currentTimeMillis()- currentCustomerDto.getLoginTime().getTime()) / 1000;
                if (seconds > SERVER_LOGIN_TIMEOUT) {
                    this.logError("Login timeout, loginAt: {}", fullDateSDF.format(currentCustomerDto.getLoginTime()));

                    // 异常情况，上一个客户关门消息没上报售货机发生断电
                    if (currentCustomerDto.getCloseDoorTime() == null) {
                        currentCustomerDto.closeDoor(new Date());
                    }

                    // 登录超时，自动登出
                    this.logout();
                } else {
                    this.logError("User[{}] login has been blocked, current user in loginning, current user loginAt: {}",
                            loginUser.getId(), fullDateSDF.format(currentCustomerDto.getLoginTime()));
                    // 登录未超时，禁止用户登录s
                    throw new BoxTradingWithOtherException(currentCustomerDto);
                }
            } else {
                // TODO 有可能在盘点中，售货机断电没把最后一批标签上报，导致没法结算并且退出登录？但是售货机重启后应该会把标签上报，继续完成结算的
                this.logError("User[{}] login has been blocked, device trading with other user", loginUser.getId());
                throw new BoxTradingWithOtherException(currentCustomerDto);
            }
        }

        BoxStatus status = boxInfo.getStatus();
        // 非空闲状态不能登录
        if (!BoxStatus.IDLE.equals(status)) {
            logError("Abnormal operation[login] has been blocked, userId: {}", loginUser.getId());
            throw new BoxStatusLimitedException("Abnormal login has been blocked", BoxStatus.IDLE,status);
        }

        // 刷新会话
        refreshSessionId();
        loginUser.setSessionId(sessionId());
        deviceSnapshotService.updateCurrentShopper(boxInfo.getBoxId(), loginUser);
        // 切换当前购物者
        this.currentCustomerDto = loginUser;
        String beforeLoginStatus = status.name();

        // 状态改为登录
        setStatus(BoxStatus.LOGIN);
        logInfo("User login success, server status changed from: {}", beforeLoginStatus);
    }

    private void refreshSessionId() {
        UUID uuid = UUID.randomUUID();
        ByteBuf sessionId = Unpooled.buffer(AntboxDataPkg.SESSION_ID_BYTES_NUM);
        sessionId.writeLong(uuid.getMostSignificantBits());
        sessionId.writeLong(uuid.getLeastSignificantBits());
        this.sessionId = sessionId.array();
    }

    public boolean isDebugLogEnabled() {
        return log.isDebugEnabled();
    }

    public void logInfo(String format, Object... args) {
        log.info(format+ this.getStatusBrief(), args);
    }

    public void logDebug(String format, Object... args) {
        log.debug(this.getStatusBrief() + format, args);
    }

    public void logWarn(String format, Object... args) {
        log.warn(this.getStatusBrief() + format, args);
    }

    public void logError(String format, Object... args) {
        log.error(this.getStatusBrief() + format, args);
    }

    public void logIncredible(String format, Object... args) {
        log.info(this.getStatusBrief() + "Incredible, " + format, args);
    }

    public void logError(Throwable e, String format, Object... args) {
        log.error(this.getStatusBrief() + (args == null || args.length == 0 ? format : String.format(format, args)), e);
    }

    public synchronized void logout() {
        // ackRollCodeSet理应为空
        if (!getAckRollCodeSet().isEmpty()) {
            logError("ackRollCodeSet is not empty");
            getAckRollCodeSet().clear();
        }
        deviceSnapshotService.resetCurrentShopper(boxInfo.getBoxId());
        setStatus(BoxStatus.IDLE);
        this.lastCustomerDto = CustomerDto.copyFrom(currentCustomerDto);
        this.currentCustomerDto = null;
        this.sessionId = null;

        logInfo("User logout success, set lastCustomerDto to {}", lastCustomerDto.toString());
    }

    public void messageProcessed(AntboxMessage msg) {
        boolean messageProcessed = true;
        if (msg instanceof CardMessage) {
            messageProcessed = !((CardMessage) msg).hasNext();
        }

        if (messageProcessed) {
            try {
                commandSender.sendCommand(new AntboxMessageProcessed(msg.getSessionId(),msg.getEventNo(), msg.getMsgCode()));
            } catch (SendCommandException e) {
                this.logError(e, "messageProcessed exception.");
            }
        }
    }

    int increaseAndGetRollCode() {
        if (AntboxDataPkg.MAX_ROLLCODE == rollCode) {
            rollCode = 0;
        } else {
            rollCode++;
        }

        return rollCode;
    }

    /**
     * 向售货机发送数据包
     */
    public void writeAndFlush(AntboxDataPkg cmd) {
        channelContext.writeAndFlush(cmd);
    }

    private String getStatusBrief() {
        BoxStatus status = boxInfo.getStatus();
        StringBuilder b = new StringBuilder();
        b.append("[conn:").append(this.connectionId);
        b.append(" box:").append(boxInfo.getBoxId()).append("|").append(status.name());
        b.append(" session:").append(sessionId()).append("@").append(getUserDesc(currentCustomerDto));
        if (lastCustomerDto != null) {
            b.append(" lastBuyer:").append(getUserDesc(lastCustomerDto));
        }
        b.append("] ");

        return b.toString();
    }

    public String getUserDesc(CustomerDto user) {
        return user == null ? "nobody" : user.getId().toString();
    }

    public ByteBuf sessionIdByteBuf() {
        return sessionId == null ? Unpooled.EMPTY_BUFFER : Unpooled.wrappedBuffer(sessionId);
    }

    public String sessionId() {
        return ByteBufUtil.hexDump(sessionIdByteBuf());
    }

    /**
     * 脉动
     */
    public void pulsating() {
        this.lastPulseTime = System.currentTimeMillis();
    }

    public void setBoxLocalTime(Date boxLocalTime) {
        boxInfo.setBoxLocalTime(boxLocalTime);
    }

    public int getClientRollCode() {
        return clientRollCode;
    }

    public void setClientRollCode(int clientRollCode) {
        this.clientRollCode = clientRollCode;
    }

    public ByteBuf getDynamicSecretKey() {
        ByteBuf dynamicSecretKey = Unpooled.buffer(AntboxDataPkg.DYNAMIC_SECRET_KEY_BYTES_NUM);
        dynamicSecretKey.writeLong(dynameicSecretKeyUUID.getMostSignificantBits());
        dynamicSecretKey.writeInt((int) dynameicSecretKeyUUID.getLeastSignificantBits());
        return dynamicSecretKey;
    }

    public Set<Integer> getAckRollCodeSet() {
        return ackRollCodeSet;
    }

    /**
     * 获取上一次交易的用户
     */
    public CustomerDto getLastCustomerDto() {
        return this.lastCustomerDto;
    }

    /**
     * 获取当前登录的用户
     */
    public CustomerDto getCurrentCustomerDto() {
        return currentCustomerDto;
    }

    public void setStatus(BoxStatus status) {
        // 当前设置状态 != 售货机状态时,所有状态计数器设置为0
        if (status != this.boxInfo.getStatus() || status == BoxStatus.IDLE) {
            this.loginStatusCounter = 0;
            this.initStatusCounter = 0;
            this.busyStatusCounter = 0;
        }
        boxInfo.setStatus(status);
    }

    public void ackLock() {
        ackLock.lock();
    }

    public void ackUnlock() {
        ackLock.unlock();
    }

    public void ackAwait(long timeoutMillis) throws InterruptedException {
        ackOk.await(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void markAck(int rollCode) {
        getAckRollCodeSet().add(rollCode);
    }

    public boolean isWaitingAck(int rollCode) {
        return getAckRollCodeSet().contains(rollCode);
    }

    public ReentrantLock getAckLock() {
        return ackLock;
    }

    public Condition getAckOk() {
        return ackOk;
    }

    /**
     * 当前响应是否超时 比如客户机网络故障断开
     *
     * @return
     * @throws BoxHeartbeatTimeoutException
     */
    public void checkHeartbeatTimeout() throws BoxHeartbeatTimeoutException {
        if ((System.currentTimeMillis() - lastPulseTime) > MAX_TIME_OUT)
            throw new BoxHeartbeatTimeoutException();
    }

    public BoxInfo getBoxInfo() {
        return boxInfo;
    }

//    public BoxOpenDoorLog getCurrentOpenDoorLog() {
//        return currentOpenDoorLog;
//    }
//
//    public void setCurrentOpenDoorLog(BoxOpenDoorLog currentOpenDoorLog) {
//        this.currentOpenDoorLog = currentOpenDoorLog;
//    }

    public int getBusyStatusCounter() {
        return busyStatusCounter;
    }

    public void setBusyStatusCounter(int busyStatusCounter) {
        this.busyStatusCounter = busyStatusCounter;
    }

    public int getLoginStatusCounter() {
        return loginStatusCounter;
    }

    public void setLoginStatusCounter(int loginStatusCounter) {
        this.loginStatusCounter = loginStatusCounter;
    }

    public int getInitStatusCounter() {
        return initStatusCounter;
    }

    public void setInitStatusCounter(int initStatusCounter) {
        this.initStatusCounter = initStatusCounter;
    }

    private DeviceSnapshot getDeviceSnapshot(String deviceId){
        DeviceSnapshot entity = new DeviceSnapshot();
        entity.setSdeviceId(deviceId);
        List<DeviceSnapshot> list = deviceSnapshotService.selectByEntityWhere(entity);
        if(list !=null && list.size() >0){
            return list.get(0);
        }
        return null;
    }
}
