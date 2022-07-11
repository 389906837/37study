package com.antbox.rfidmachine.client;

import com.antbox.rfidmachine.helper.AntboxrHelper;
import com.antbox.rfidmachine.service.AntBoxService;
import com.antbox.rfidmachine.dto.AntboxRequest;
import com.antbox.rfidmachine.helper.AntboxObject;
import com.antbox.rfidmachine.dto.AntboxResponse;
import com.antbox.rfidmachine.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DK on 17/5/12.
 */
public class AntboxClient implements AntBoxService, SerialPortEventListener {
    private final Logger log = LoggerFactory.getLogger(AntboxClient.class);

    //一体机机型型号
    private String selectMachine;

    /**
     * 是否处于打开状态
     */
    private volatile boolean opened = false;

    private final String commAddress;
    private final SerialPort serialPort;
    /**
     * 当前命令（命令+控制模式 才能唯一标识一个命令）
     */
    private volatile short currentCommand = AntboxrHelper.NO_COMMAND;

    /**
     * 当前控制模式（命令+控制模式 才能唯一标识一个命令）
     */
    private volatile short currentCommandState = AntboxrHelper.NO_COMMAND;

    /**
     * 读写器返回的结果
     */
    private volatile List<AntboxResponse> result = Collections
            .synchronizedList(new LinkedList<>());

    /**
     * 缓存串口数据
     */
    private final ByteBuf serialBuffer = Unpooled.buffer(AntboxrHelper.MAX_FRAME_LENGTH);

    /**
     * 读写器（串口）操作锁
     */
    private final ReentrantLock readerLock = new ReentrantLock();

    /**
     * 读写器结果可用
     */
    private final Condition resultReady = readerLock.newCondition();

    public AntboxClient(String commAddress) {
        super();
        this.commAddress = commAddress;
        this.serialPort = new SerialPort(commAddress);
        this.open();
    }

    public synchronized boolean isOpened() {
        return opened;
    }

    @Override
    public synchronized void close() {
        try {
            serialPort.closePort();
            opened = false;
        } catch (SerialPortException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + commAddress + "] Shut down the serial port: " + ex.getClass().getSimpleName() + ": "
                        + ex.getMessage());
            }
        }
    }


    @Override
    public synchronized void open() {
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(this);
            opened = true;
        } catch (SerialPortException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + commAddress + "] Open serial port failed: " + ex.getClass().getSimpleName() + ": "
                        + ex.getMessage());
            }
        }
    }

    @Override
    public void setInventoryTimeSeconds(int seconds) {
        ByteBuf data = Unpooled.buffer(1);
        data.writeByte(0xff & (10 * seconds));
        this.sendCommand(AntboxrHelper.WRITE_INVENTORYSCANTIME, AntboxrHelper.STATE_READER_COMMAND,data);
        this.resetCommandAndResult();
    }

    @Override
    public synchronized List<String> inventory(String selectMachine) {
        this.selectMachine = selectMachine;
        short scanCommand = AntboxrHelper.STATE_RSSI_INVENTORY_SCAN;
        if (AntboxrHelper.OLD_MACHINE_MODEL.equals(selectMachine)) {
            scanCommand = AntboxrHelper.STATE_NEW_INVENTORY_SCAN;
        }
        this.sendCommand(AntboxrHelper.INVENTORY, scanCommand, null);
        List<AntboxResponse> rspList = new ArrayList<>();
        if (!result.isEmpty()) {
            rspList = AntboxrHelper.SINGLETON.getResult(AntboxResponse.class, result, currentCommand, currentCommandState);
        } else {
            log.info("The reader did not return a result");
        }
        List<String> uidList = new LinkedList<>();
        for (AntboxResponse rsp : rspList) {
            if (StringUtils.isEmpty(rsp.getUid())) {
                break;
            }
            uidList.add(rsp.getUid());
        }
        return uidList;
    }


    /**
     * 向一体机发送命令
     */
    private void sendCommand(final short command, final short state, final ByteBuf data) {
        if (!tryLockReader()) {
            log.info("Lock reader: reader communication timeout");
        }

        // 先休眠一下（比如0.1秒）
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("[" + commAddress + "] Send command failed: " + e.getClass().getSimpleName() + ": "
                    + e.getMessage());
        }
        // 发命令给读写器
        this.currentCommand = command;
        this.currentCommandState = state;
        AntboxRequest cmd = new AntboxRequest(command, state, data);
//        if (log.isInfoEnabled()) {
//            log.info("[" + this.commAddress + AntboxrHelper.SINGLETON.cmdStr(currentCommand, currentCommandState) + "] sendCommand: " + cmd);
//        }
        try {
            // 发送命令
            serialPort.writeBytes(cmd.toByteBuf().array());
            // 等待结果
            if (!awaitResult()) {
                log.info("Waiting for results: reader communication timeout");
            }
        } catch (SerialPortException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + commAddress + "] Send command failed: " + ex.getClass().getSimpleName() + ": "
                        + ex.getMessage());
            }
        }
    }

    public void serialEvent(SerialPortEvent event) {
        if (log.isInfoEnabled()) {
            log.info("[" + commAddress + "] SerialPortEvent: " + event.getEventType());
        }

        if (!event.isRXCHAR() || event.getEventValue() <= 0) {
            return;
        }

        // 锁定
        if (!readerLock.isHeldByCurrentThread()) {
            readerLock.lock();
        }
        try {
            // 读取数据到缓冲区
            byte buffer[] = serialPort.readBytes(event.getEventValue());
            serialBuffer.writeBytes(buffer);

            // 如果已读取一个数据帧，转换该数据帧为业务对象
            while (true) {
                byte[] bodyLen0 = new byte[AntboxObject.LENGTH_FIELD_LENGTH];
                serialBuffer.getBytes(serialBuffer.readerIndex(), bodyLen0, 0, bodyLen0.length);
                int bodyLen = new BigInteger(1, bodyLen0).intValue();
                int frameLen = bodyLen + AntboxObject.LENGTH_FIELD_LENGTH;
                if (serialBuffer.readableBytes() < frameLen) {
                    break;
                }
                // 读取数据帧
                ByteBuf buf = serialBuffer.readBytes(frameLen);
                // 丢弃已读数据（移动读写指针）
                serialBuffer.discardReadBytes();
                AntboxResponse rsp = (AntboxResponse) new AntboxResponse(selectMachine).parse(buf);
//                if (log.isInfoEnabled()) {
//                    log.info("[" + commAddress + AntboxrHelper.SINGLETON.cmdStr(currentCommand, currentCommandState) + "] 收到数据: "
//                            + AntboxUtil.hexDump(buf.array()));
//                }
                if (this.addToResult(rsp)) {
                    break;
                }
            }
        } catch (SerialPortException ex) {
            if (log.isErrorEnabled()) {
                log.error("[" + commAddress + "] " + ex.getClass().getSimpleName() + ": "
                        + ex.getMessage());
            }
            this.resultReady();
        }
    }

    /**
     * 重置当前命令和结果
     */
    private void resetCommandAndResult() {
        this.currentCommand = AntboxrHelper.NO_COMMAND;
        this.currentCommandState = AntboxrHelper.NO_COMMAND;
        this.result.clear();
    }

    /**
     * 锁定读写器
     *
     */
    private boolean tryLockReader() {
        try {
            return readerLock.tryLock(6000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        return false;
    }

    /**
     * 等待读写器返回结果
     *
     */
    private boolean awaitResult() {
        try {
            return resultReady.await(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }
        return false;
    }

    /**
     * 通知调用方读写器结果可用
     */
    private void resultReady() {
        resultReady.signal();
        readerLock.unlock();
        this.serialBuffer.clear();
    }

    /**
     * 添加至结果
     *
     * @return 如果结果就绪，返回{@code true}
     */
    private boolean addToResult(AntboxResponse rsp) {
        this.result.add(rsp);
        if (!AntboxrHelper.SINGLETON.isInventoryCommand(this.currentCommand, this.currentCommandState)) {
            resultReady();
            return true;
        }
        if (AntboxrHelper.ALL_UID_SCANNED == rsp.getStatus() || AntboxrHelper.NO_UID_SCANNED == rsp.getStatus()) {
            resultReady();
            return true;
        }
        return false;
    }
}
