package com.cloud.cang.mgr.sb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.DeviceConst;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.*;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.utils.IdGenerator;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.dao.*;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.DeviceInventoryStockDomain;
import com.cloud.cang.mgr.sb.service.BackstageOperService;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceRegisterService;
import com.cloud.cang.mgr.sb.service.SendMsgToNettyService;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.mgr.sb.vo.DeviceSelectVo;
import com.cloud.cang.mgr.sb.vo.Goods;
import com.cloud.cang.mgr.sb.vo.TagModel;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sm.dao.StandardStockDao;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sb.*;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.sb.*;
import com.cloud.cang.sb.operatingParams.AdjustVolumeVo;
import com.cloud.cang.sb.operatingParams.RebootVo;
import com.cloud.cang.sb.operatingParams.ShutdownVo;
import com.cloud.cang.sb.operatingParams.UpgradeVoiceVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.server.GpuServerServicesDefine;
import com.cloud.cang.server.MgrJobDto;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

@Service
public class DeviceInfoServiceImpl extends GenericServiceImpl<DeviceInfo, String> implements
        DeviceInfoService {

    @Autowired
    ICached iCached;

    @Autowired
    DeviceInfoDao deviceInfoDao;

    @Autowired
    MerchantInfoDao merchantInfoDao;

    @Autowired
    DeviceManageDao deviceManageDao;

    @Autowired
    DeviceModelDao deviceModelDao;

    @Autowired
    MonitorDataConfDao monitorDataConfDao;

    @Autowired
    GroupRelationshipDao groupRelationshipDao;

    @Autowired
    StandardStockDao standardStockDao;//标准库存

    @Autowired
    AiInfoDao aiInfoDao;    // 小屏设备

    @Autowired
    DeviceRegisterService deviceRegisterService;//2.设备注册信息

    @Autowired
    SendMsgToNettyService sendMsgToNettyService;    // 发送消息给netty服务

    @Autowired
    DeviceUpgradeDao deviceUpgradeDao;      //  设备升级记录主表

    @Autowired
    DeviceUpgradeDetailsDao deviceUpgradeDetailsDao;    // 设备升级记录明细表

    @Autowired
    CommodityInfoDao commodityInfoDao;  // 商品信息表

    @Autowired
    DeviceStockDao deviceStockDao;  // 设备库存信息

    @Autowired
    BackstageOperDao backstageOperDao;  // 设备后台操作记录表

    @Autowired
    TimingTaskDao timingTaskDao;        // 定时操作设备任务执行表
    @Autowired
    RegionService regionService;
    @Autowired
    BackstageOperService backstageOperService;

    @Override
    public GenericDao<DeviceInfo, String> getDao() {
        return deviceInfoDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);


    @Override
    public void delete(String[] checkboxId) {
        DeviceInfo deviceInfo = null;
        // 检测设备是否被注册过，注册过的不能删除
        for (String id : checkboxId) {
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("存在设备ID为空");
            }
            DeviceRegister deviceRegister = deviceRegisterService.selectByDeviceId(id);
            if (null != deviceRegister && deviceRegister.getIstatus() == 40) {
                throw new ServiceException("选中设备中存在已经被注册过的设备，不能删除");
            }
        }
        for (String id : checkboxId) {
            deviceInfo = new DeviceInfo();
            deviceInfo.setId(id);
            deviceInfo.setIdelFlag(1);
            deviceInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            //逻辑删除，将表中是否删除改为1
            deviceInfoDao.updateByIdSelective(deviceInfo);

            // 设备管理信息表
            DeviceManage deviceManage = deviceManageDao.selectByDeviceId(id);
            if (null != deviceManage) {
                deviceManageDao.deleteByPrimaryKey(deviceManage.getId());
            }
            // 设备详细信息表
            DeviceModel deviceModel = deviceModelDao.selectByDeviceId(id);
            if (null != deviceModel) {
                deviceModelDao.deleteByPrimaryKey(deviceModel.getId());
            }
            // 设备实时信息表
            MonitorDataConf monitorDataConf = monitorDataConfDao.selectByDeviceId(id);
            if (null != monitorDataConf) {
                monitorDataConfDao.deleteByPrimaryKey(monitorDataConf.getId());
            }
            // 设备注册表
            DeviceRegister deviceRegister = deviceRegisterService.selectByDeviceId(id);
            if (null != deviceRegister) {
                deviceRegisterService.deleteByIds(new String[]{deviceRegister.getId()});
            }
        }
        deviceRegisterService.deleteByIds(checkboxId);//逻辑删除,checkboxId为设备ID
    }

    @Override
    public Page<DeviceInfoDomain> selectPageByDomainWhere(Page<DeviceInfoDomain> page, DeviceInfoVo deviceInfoVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String address = deviceInfoVo.getSputAddress();
        if (StringUtils.isNotBlank(address)) {
            char[] chars = address.toCharArray();
            deviceInfoVo.setSputAddress(StringUtils.join(chars, '%'));
        }
        return (Page<DeviceInfoDomain>) deviceInfoDao.selectByDomainWhere(deviceInfoVo);
    }

    /**
     * 长连接批量操作设备(操作参数为空)
     *
     * @param checkboxId 设备ID集合
     * @param method
     * @return
     */
    @Override
    public ResponseVo<String> operate(String[] checkboxId, String method) {
        return operate(checkboxId, method, null);
    }

    /**
     * 长连接批量操作设备(操作参数不为空)
     *
     * @param checkboxId    设备ID集合
     * @param method        操作类型
     * @param operateParams 操作参数
     * @return
     */
    @Override
    public ResponseVo<String> operate(String[] checkboxId, String method, String operateParams) {
        ResponseVo<String> responseVo = new ResponseVo<>(false, 3333, "操作设备失败!");

        //调用长连接服务前，对DTO对象进行赋值
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(StringUtils.join(checkboxId, ","));//设备ID数组使用“,”拼接成字符串
        deviceDto.setFunction(method);//调用设备执行的方法名
        deviceDto.setUserId(SessionUserUtils.getSessionUserId());
        if (StringUtils.isNotBlank(operateParams)) {//方法执行参数
            deviceDto.setOperateParam(operateParams);
        }

        //开始调用长连接服务接口，调用netty提供的接口，发送消息给对应的设备
        logger.debug("开始调用长连接服务接口" + DeviceServicesDefine.OPERATE_DEVICE);
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.OPERATE_DEVICE);
            invoke.setRequestObj(deviceDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("调用长连接服务接口成功，执行方法名：{} ，方法参数：{}", method, operateParams);
                return responseVo;
            } else {
                throw new ServiceException("调用向设备推送消息服务失败");
            }
        } catch (Exception e) {
            logger.error("长连接批量操作设备异常：{}", e);
        }
        return responseVo;


    }

    /**
     * 后台操作设备远程调用api
     *
     * @param deviceOperatingDto
     * @param method
     * @return
     */
    public ResponseVo<String> invokeDeviceOperating(DeviceOperatingDto deviceOperatingDto, String method) {
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("发送消息失败");
        logger.debug("开始远程调用api服务接口:{}", DeviceServicesDefine.DEVICE_OPERATING);
        logger.debug("远程调用api方法名:{}", method);
        try {
            // 创建Rest服务客户端
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.DEVICE_OPERATING);
            invoke.setRequestObj(deviceOperatingDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            responseVo = (ResponseVo<String>) invoke.invoke();
            if (responseVo.isSuccess()) {
                logger.debug("mgr远程调用api接口成功，执行方法名：{} ，调用对象：{}", method, deviceOperatingDto);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("mgr远程调用api接口异常：{}", e);
        }
        return responseVo;
    }

    /**
     * 添加设备信息
     *
     * @param deviceInfo      设备基础信息表
     * @param deviceManage    设备管理信息表
     * @param deviceModel     设备详细信息表
     * @param monitorDataConf 设备配置信息表
     * @param request         其他参数
     * @param readerID        读写器
     * @param merchantId      商户ID
     */
    @Override
    @Transactional
    public ResponseVo<DeviceInfo> insertDeviceInfo(DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request, String readerID, String merchantId) {
        //根据商户ID查询商户
        MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(merchantId);
        String merchantCode = merchantInfo.getScode();//商户编号
        String deviceCode = CoreUtils.newCode(EntityTables.SB_DEVICE_INFO);//设备编号
        if (StringUtils.isNotEmpty(deviceCode) && StringUtils.isNotBlank(merchantCode)) {
            //设置属性
            if (StringUtil.isNotBlank(deviceInfo.getSprovinceName())) {//省份
                deviceInfo.setSprovinceId(deviceInfo.getSprovinceName().split("_")[0]);
                deviceInfo.setSprovinceName(deviceInfo.getSprovinceName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceInfo.getScityName())) {//城市
                deviceInfo.setScityId(deviceInfo.getScityName().split("_")[0]);
                deviceInfo.setScityName(deviceInfo.getScityName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceInfo.getSareaName())) {//区县
                deviceInfo.setSareaId(deviceInfo.getSareaName().split("_")[0]);
                deviceInfo.setSareaName(deviceInfo.getSareaName().split("_")[1]);
            }

            // 插入数据到小屏幕设备基础信息表
            Integer isupportAi = deviceInfo.getIsupportAi();
            if (null == isupportAi) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("是否支持AI设备为必填项");
            }
            AiInfo aiInfo = new AiInfo();
            if (isupportAi == 1) {
                String aiCode = CoreUtils.newCode(EntityTables.SB_AI_INFO);//小屏设备编号
                if (StringUtils.isBlank(aiCode)) {
                    throw new ServiceException("小屏设备编号没有正常生成");
                }
                aiInfo.setSaiCode(aiCode);
                aiInfo.setSmerchantId(merchantId);
                aiInfo.setSmerchantCode(merchantCode);
                aiInfo.setIstatus(10);  // 状态： 10=未注册 20=正常30=故障 40=报废 50=离线
                aiInfo.setIoperType(10);    // 10=启用 20=停用
                aiInfo.setIsuppoerOpenType(10); //10=人脸识别 20=NFC
                aiInfo.setIdelFlag(0);
                aiInfo.setTaddTime(DateUtils.getCurrentDateTime());
                aiInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                aiInfo.setTupdateTime(DateUtils.getCurrentDateTime());
                aiInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                aiInfoDao.insert(aiInfo);
                deviceInfo.setSaiId(aiInfo.getId());            // 小屏设备ID
                deviceInfo.setSaiCode(aiInfo.getSaiCode());     // 小屏设备编号
            }

            // 判断是否支持称重
            Integer isupportWeigh = deviceInfo.getIsupportWeigh();
            BigDecimal ielectronicFloat = deviceInfo.getIelectronicFloat();
            if (null == isupportWeigh) {
                throw new ServiceException("是否称重必填项");
            }
            if (isupportWeigh == 1) {
                if (null == ielectronicFloat) {
                    deviceInfo.setIelectronicFloat(new BigDecimal(0.00));
                }
            } else {
                deviceInfo.setIweightType(null);
                deviceInfo.setIelectronicFloat(null);
            }

            deviceInfo.setScode(deviceCode);/* 设备编号 */
            deviceInfo.setSmerchantCode(merchantCode);//商户编号
            deviceInfo.setSreaderSerialNumber(readerID);//读写器序列号
            deviceInfo.setIdelFlag(0);/* 是否删除0否1是 */
            deviceInfo.setIoperateStatus(DeviceConst.DEVICE_BUSINESS_ENABLE);/* 运营状态：10=启用    20=停用 */
            deviceInfo.setIstatus(DeviceConst.DEVICE_UNREGISTERED);/* 设备状态：  10=未注册 20=正常   30=故障   40=报废 50=离线  */
            deviceInfo.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            deviceInfo.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            deviceInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            deviceInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人

            //插入数据到设备基础信息表
            deviceInfoDao.insert(deviceInfo);

            //操作日志
            String logText = MessageFormat.format("新增设备编号，编号{0}", deviceInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);


            //插入数据到设备注册信息表与设备管理表，设备详细信息表，设备配置表
            String deviceId = deviceInfo.getId();//设备ID
            if (StringUtils.isNotBlank(deviceId)) {
                DeviceRegister deviceRegister = new DeviceRegister();
                String regIp = InviteCodeUtil.buildDeviceRegisterCode();//注册码（6位）
                String securityKey = IdGenerator.randomUUID();//安全密钥（32位）

                /*  1.插入售货机设备注册信息到数据库  */
                deviceRegister.setSdeviceId(deviceId);//设备ID
                deviceRegister.setSdeviceCode(deviceCode);//设备编号
                deviceRegister.setSregIp(regIp);//注册IP，生成6位随机数
                deviceRegister.setSsecurityKey(securityKey);//安全密钥UUID
                deviceRegister.setIdelFlag(0); //是否删除0否1是
                deviceRegister.setIstatus(DeviceConst.DEVICE_REGISTER_AUDIT_WAIT); //设备状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
                deviceRegister.setTaddTime(DateUtils.getCurrentDateTime()); //添加日期
                deviceRegister.setTupdateTime(DateUtils.getCurrentDateTime()); //修改日期
                deviceRegister.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
                deviceRegister.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                deviceRegisterService.insert(deviceRegister);   // 售货机设备

                /*  1.1 插入人脸识别设备注册信息到数据库  */
                if (StringUtils.isNotBlank(aiInfo.getSaiCode())) {
                    DeviceRegister deviceRegister1 = new DeviceRegister();
                    deviceRegister1.setSdeviceCode(aiInfo.getSaiCode());                     //小屏设备编号
                    deviceRegister1.setSdeviceId(aiInfo.getId());                            //小屏设备ID
                    deviceRegister1.setSregIp(InviteCodeUtil.buildDeviceRegisterCode());     //注册IP，生成6位随机数
                    deviceRegister1.setSsecurityKey(IdGenerator.randomUUID());               //安全密钥UUID
                    deviceRegister1.setIstatus(10);                                          //注册状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
                    deviceRegister1.setIdelFlag(0); //是否删除0否1是
                    deviceRegister1.setTaddTime(DateUtils.getCurrentDateTime()); //添加日期
                    deviceRegister1.setTupdateTime(DateUtils.getCurrentDateTime()); //修改日期
                    deviceRegister1.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
                    deviceRegister1.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                    deviceRegisterService.insert(deviceRegister1);                           //小屏设备
                }


                /*  2.插入设备管理信息到数据库  */
                deviceManage.setSdeviceId(deviceId);//设备ID
                deviceManage.setTaddTime(DateUtils.getCurrentDateTime());//添加日期
                deviceManage.setTupdateTime(DateUtils.getCurrentDateTime());//修改日期
                deviceManage.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
                deviceManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                deviceManageDao.insert(deviceManage);

                /*  3.插入设备详细信息到数据库  */
                Integer ilayerNum = deviceModel.getIlayerNum(); // 设备层数
                if (ilayerNum == null) {
                    deviceModel.setIlayerNum(5); // 设备层数默认为5
                }
                String scameraMethod1 = request.getParameter("scameraMethod1");
                if (StringUtil.isNotBlank(scameraMethod1)) {
                    deviceModel.setScameraMethod(scameraMethod1);
                }
                deviceModel.setSdeviceId(deviceId);
                deviceModel.setSdeviceModel(request.getParameter("sdeviceCoreModel"));//设备核心型号
                deviceModel.setTaddTime(DateUtils.getCurrentDateTime());
                deviceModel.setTupdateTime(DateUtils.getCurrentDateTime());
                deviceModel.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                deviceModel.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                deviceModelDao.insert(deviceModel);

                /*  4.插入设备配置信息到数据库  */
                monitorDataConf.setSdeviceId(deviceId);
                monitorDataConf.setIactualVolume(40);//实时音量
                monitorDataConf.setTaddTime(DateUtils.getCurrentDateTime());
                monitorDataConf.setTupdateTime(DateUtils.getCurrentDateTime());
                monitorDataConf.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                monitorDataConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                monitorDataConfDao.insert(monitorDataConf);

//                /*  5.插入设备分组信息到数据库  */
//                String groupId = request.getParameter("sgroup_id");
//                if (StringUtils.isNotBlank(groupId)) {
//                    GroupRelationship groupRelationship = new GroupRelationship();//设备分组关系表
//                    groupRelationship.setSgroupId(groupId);
//                    groupRelationship.setSdeviceId(deviceId);
//                    groupRelationshipDao.insert(groupRelationship);
//                }

                /*  6.插入设备标准库存信息到数据库  */
                StandardStock standardStock = new StandardStock();
                standardStock.setSdeviceId(deviceId);//设备ID
                standardStock.setSdeviceCode(deviceCode);//设备编号
                standardStock.setIstatus(20);//商品状态 10=启用 20=禁用
                standardStock.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                standardStock.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                standardStock.setTaddTime(DateUtils.getCurrentDateTime());
                standardStock.setTupdateTime(DateUtils.getCurrentDateTime());
                standardStockDao.insert(standardStock);


            }
            return ResponseVo.getSuccessResponse(deviceInfo);

        }

        return new ResponseVo<>(false, 1111, "添加设备出错");
    }

    /**
     * @param oldDevice       修改前设备基础信息
     * @param deviceInfo      设备基础信息表
     * @param deviceManage    设备管理信息表
     * @param deviceModel     设备详细信息表
     * @param monitorDataConf 设备配置信息表
     * @param request         其他参数
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<DeviceInfo> updateInfo(DeviceInfo oldDevice, DeviceInfo deviceInfo, DeviceManage deviceManage, DeviceModel deviceModel, MonitorDataConf monitorDataConf, HttpServletRequest request) throws Exception {

        String deviceId = request.getParameter("deviceInfoId");//设备ID
        String deviceManageId = request.getParameter("deviceManageId");//设备管理主键ID
        String deviceModelId = request.getParameter("deviceModelId");//设备详细信息主键ID
        String monitorDataConfId = request.getParameter("monitorDataConfId");//设备配置信息主键ID

        // 查询设备是否存在
        DeviceInfo deviceInfoBean = deviceInfoDao.selectByPrimaryKey(deviceId);
        if (null == deviceInfoBean) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("设备不存在");
        }

        // 判断是否支持AI设备
        Integer oldIsSupportAi = deviceInfoBean.getIsupportAi();
        Integer newIsSupportAi = deviceInfo.getIsupportAi();
        String oldAiId = deviceInfoBean.getSaiId();
        if (null == newIsSupportAi) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("是否支持AI设备为必填项");
        }
        if ((null == oldIsSupportAi && 1 == newIsSupportAi) || (null != oldIsSupportAi && 0 == oldIsSupportAi && 1 == newIsSupportAi)) { // 以前不支持，现在支持
            // 判断以前是否支持过
            String oldAiCode = deviceInfoBean.getSaiCode();
            if (StringUtils.isNotBlank(oldAiCode) && StringUtils.isNotBlank(oldAiId)) {
                logger.debug("修改大屏设备支持小屏，大屏ID：{}，原逻辑删除的小屏编号：{}", deviceId, oldAiCode);
                // 逻辑修改大屏对应的小屏信息
                AiInfo aiInfoTemp = new AiInfo();
                aiInfoTemp.setId(oldAiId);
                aiInfoTemp.setIdelFlag(0);
                aiInfoTemp.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                aiInfoTemp.setTupdateTime(DateUtils.getCurrentDateTime());
                aiInfoDao.updateByIdSelective(aiInfoTemp);

                // 逻辑修改小屏注册表信息
                DeviceRegister aiRegister = new DeviceRegister();
                aiRegister.setSdeviceId(oldAiId);
                aiRegister.setIdelFlag(0);
                aiRegister.setIstatus(10); // 状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
                aiRegister.setTupdateTime(DateUtils.getCurrentDateTime());
                aiRegister.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                deviceRegisterService.updateAiRegisterByAiId(aiRegister);

            } else {
                logger.debug("修改大屏设备支持小屏，大屏ID：{}，原来没有小屏数据", deviceId);
                String aiCode = CoreUtils.newCode(EntityTables.SB_AI_INFO);                    // 小屏设备编号
                if (StringUtils.isBlank(aiCode)) {
                    logger.error("小屏编号生成出错，设备ID：{}", deviceId);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统错误，修改设备信息出错");
                }
                AiInfo aiInfo = new AiInfo();
                aiInfo.setSaiCode(aiCode);
                aiInfo.setSmerchantId(deviceInfoBean.getSmerchantId());
                aiInfo.setSmerchantCode(deviceInfoBean.getSmerchantCode());
                aiInfo.setIstatus(10);  // 状态： 10=未注册 20=正常30=故障 40=报废 50=离线
                aiInfo.setIoperType(10);    // 10=启用 20=停用
                aiInfo.setIsuppoerOpenType(10); //10=人脸识别 20=NFC
                aiInfo.setIdelFlag(0);
                aiInfo.setTaddTime(DateUtils.getCurrentDateTime());
                aiInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                aiInfo.setTupdateTime(DateUtils.getCurrentDateTime());
                aiInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                aiInfoDao.insert(aiInfo);
                deviceInfo.setSaiCode(aiCode);
                deviceInfo.setSaiId(aiInfo.getId());

                /*  插入人脸识别设备注册信息到数据库  */
                if (StringUtils.isNotBlank(aiCode)) {
                    DeviceRegister deviceRegister1 = new DeviceRegister();
                    deviceRegister1.setSdeviceCode(aiInfo.getSaiCode());                     //小屏设备编号
                    deviceRegister1.setSdeviceId(aiInfo.getId());                            //小屏设备ID
                    deviceRegister1.setSregIp(InviteCodeUtil.buildDeviceRegisterCode());     //注册IP，生成6位随机数
                    deviceRegister1.setSsecurityKey(IdGenerator.randomUUID());               //安全密钥UUID
                    deviceRegister1.setIstatus(10);                                          //注册状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
                    deviceRegister1.setIdelFlag(0); //是否删除0否1是
                    deviceRegister1.setTaddTime(DateUtils.getCurrentDateTime()); //添加日期
                    deviceRegister1.setTupdateTime(DateUtils.getCurrentDateTime()); //修改日期
                    deviceRegister1.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
                    deviceRegister1.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                    deviceRegisterService.insert(deviceRegister1);                           //小屏设备
                }
            }
        } else if (null != oldIsSupportAi && 1 == oldIsSupportAi && 0 == newIsSupportAi) { // 以前支持，现在不支持

            logger.debug("修改大屏设备不支持小屏信息，大屏设备ID：{}，逻辑删除小屏编号：{}", deviceId, deviceInfoBean.getSaiCode());
            if (StringUtils.isBlank(oldAiId)) {
                logger.error("获取小屏ID出错，设备ID：{}", deviceId);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统错误，修改设备信息出错");
            }
            // 检查小屏设备状态
            AiInfo aiInfoStatus = aiInfoDao.selectByPrimaryKey(oldAiId);
            if (null != aiInfoStatus && 20 == aiInfoStatus.getIstatus() && 10 == aiInfoStatus.getIoperType()) {
                logger.error("小屏设备正常使用，不能修改，大屏设备ID：{}", deviceId);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("AI设备正在使用，无法修改");
            }

            // 逻辑删除大屏对应的小屏信息
            AiInfo aiInfoTemp = new AiInfo();
            aiInfoTemp.setId(oldAiId);
            aiInfoTemp.setIdelFlag(1);
            aiInfoTemp.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            aiInfoTemp.setTupdateTime(DateUtils.getCurrentDateTime());
            aiInfoDao.updateByIdSelective(aiInfoTemp);

            // 逻辑删除小注册表信息
            DeviceRegister aiRegister = new DeviceRegister();
            aiRegister.setSdeviceId(oldAiId);
            aiRegister.setIdelFlag(1);
            aiRegister.setIstatus(10);
            aiRegister.setTupdateTime(DateUtils.getCurrentDateTime());
            aiRegister.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceRegisterService.updateAiRegisterByAiId(aiRegister);
        }

        /*  1.修改数据入设备基础信息表  */
        //省份信息拼接
        if (StringUtil.isNotBlank(deviceInfo.getSprovinceName())) {//省份
            deviceInfo.setSprovinceId(deviceInfo.getSprovinceName().split("_")[0]);
            deviceInfo.setSprovinceName(deviceInfo.getSprovinceName().split("_")[1]);
        }
        if (StringUtil.isNotBlank(deviceInfo.getScityName())) {//城市
            deviceInfo.setScityId(deviceInfo.getScityName().split("_")[0]);
            deviceInfo.setScityName(deviceInfo.getScityName().split("_")[1]);
        }
        if (StringUtil.isNotBlank(deviceInfo.getSareaName())) {//区县
            deviceInfo.setSareaId(deviceInfo.getSareaName().split("_")[0]);
            deviceInfo.setSareaName(deviceInfo.getSareaName().split("_")[1]);
        }

        //手动修改为故障时，如果设备在线,发送消息非服务器将设备断开连接
        if (Integer.valueOf(DeviceConst.DEVICE_MALFUNCTION).equals(deviceInfo.getIstatus())) {
            sendMsgToNettyService.offline(deviceId);
        }

        // 判断是否支持称重
        Integer isupportWeigh = deviceInfo.getIsupportWeigh();
        BigDecimal ielectronicFloat = deviceInfo.getIelectronicFloat();
        if (null == isupportWeigh) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("是否支持称重为必填项");
        }
        if (isupportWeigh == 1) {
            if (null == ielectronicFloat) {
                deviceInfo.setIelectronicFloat(new BigDecimal(0.00));
            }
        } else {
            deviceInfo.setIweightType(null);
            deviceInfo.setIelectronicFloat(new BigDecimal(0.00));
        }
        deviceInfo.setId(deviceId);
        deviceInfo.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
        deviceInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
        deviceInfoDao.updateByIdSelective(deviceInfo);
        //操作日志
        String logText1 = MessageFormat.format("修改设备基础信息，编号{0}", deviceInfo.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);

        /*  2.修改数据入设备管理表  */
        if (StringUtils.isNotBlank(deviceManageId)) {
            deviceManage.setId(deviceManageId);
            deviceManage.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            deviceManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
            deviceManageDao.updateByIdSelectiveVo(deviceManage);
            String logText2 = MessageFormat.format("修改设备管理信息，编号{0}", deviceInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText2, null);
        }

        /*  3.修改数据入设备详细信息表  */
        if (StringUtils.isNotBlank(deviceModelId)) {
            String scameraMethod1 = request.getParameter("scameraMethod1");
            if (StringUtil.isNotBlank(scameraMethod1)) {
                deviceModel.setScameraMethod(scameraMethod1);
            }
            deviceModel.setId(deviceModelId);//设置ID
            deviceModel.setSdeviceModel(request.getParameter("sdeviceCoreModel"));
            deviceModel.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            deviceModel.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
            deviceModelDao.updateByIdSelective(deviceModel);
            String logText3 = MessageFormat.format("修改设备详细信息，编号{0}", deviceInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText3, null);
        }

        /*  4.修改数据入设备配置信息表  */
        if (StringUtils.isNotBlank(monitorDataConfId)) {
            monitorDataConf.setId(monitorDataConfId);//设置ID
            monitorDataConf.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            monitorDataConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
//            monitorDataConfDao.updateByIdSelective(monitorDataConf);
            monitorDataConfDao.updateByIdSelectiveVo(monitorDataConf);
            String logText4 = MessageFormat.format("修改设备配置信息，编号{0}", deviceInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText4, null);
        }

//        /*  5.修改数据入设备分组关系表  */
//        String sgroup_id = request.getParameter("sgroup_id");//分组ID
//        String oldGroupId = request.getParameter("oldGroupId");//设备分组关系ID
//        if (StringUtils.isNotBlank(sgroup_id) && StringUtils.isNotBlank(oldGroupId)) {
//            groupRelationshipDao.deleteByPrimaryKey(oldGroupId);//根据设备分组关系表主键，删除之前的记录
//            GroupRelationship groupRelationship = new GroupRelationship();
//            groupRelationship.setSdeviceId(deviceId);
//            groupRelationship.setSgroupId(sgroup_id);
//            groupRelationshipDao.insert(groupRelationship);
//        } else if (StringUtils.isBlank(sgroup_id) && StringUtils.isNotBlank(oldGroupId)) {
//            groupRelationshipDao.deleteByPrimaryKey(oldGroupId);
//        } else if (StringUtils.isNotBlank(sgroup_id) && StringUtils.isBlank(oldGroupId)) {
//            GroupRelationship groupRelationship = new GroupRelationship();
//            groupRelationship.setSdeviceId(deviceId);
//            groupRelationship.setSgroupId(sgroup_id);
//            groupRelationshipDao.insert(groupRelationship);
//        }
        //如果修改 是否开启实时盘货字段,调用 云端设备配置参数调整 接口
        if (50 == deviceInfo.getItype().intValue() || 60 == deviceInfo.getItype().intValue()) {
            if (oldDevice.getIisOpeningInventory() != deviceInfo.getIisOpeningInventory()) {
                logger.info("设备修改是否开启实时盘货字段,调用云端设备配置参数调整接口：{}", oldDevice.getScode());
                CloudParamConfigDto cloudParamConfigDto = new CloudParamConfigDto();
                cloudParamConfigDto.setType(10);
                cloudParamConfigDto.setMerchantCode(oldDevice.getSmerchantCode());
                cloudParamConfigDto.setMerchantId(oldDevice.getSmerchantId());
                List<String> list = new ArrayList();
                list.add(oldDevice.getId());
                cloudParamConfigDto.setDevices(list);
                cloudParamConfigDto.setShoppingInventory(deviceInfo.getIisOpeningInventory());
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.CLOUD_DEVICE_PARAM_CONFIG);
                invoke.setRequestObj(cloudParamConfigDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                if (null == responseVo || !responseVo.isSuccess()) {
                    logger.error("修改商户客户端配置,调用云端设备配置参数调整接口失败:{}", responseVo);
                    throw new ServiceException("修改商户客户端配置,调用云端设备配置参数调整接口失败！");
                }
                // 操作日志
                BackstageOper backstageOper = new BackstageOper();
                backstageOper.setIdelFlag(0);
                if ("1".equals(deviceInfo.getIisOpeningInventory())) {
                    backstageOper.setItype(60);
                    backstageOper.setSoperContent(JSON.toJSONString("开启实时盘货"));    // 操作内容
                } else {
                    backstageOper.setItype(70);
                    backstageOper.setSoperContent(JSON.toJSONString("关闭实时盘货"));    // 操作内容
                }
                backstageOper.setIdeviceType(20);
                backstageOper.setSoperObject(JSON.toJSONString(oldDevice.getScode()));     // 操作对象
                backstageOper.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                backstageOper.setTaddTime(DateUtils.getCurrentDateTime());
                backstageOperDao.insert(backstageOper);
            }
        }
        return ResponseVo.getSuccessResponse(deviceInfo);
    }

    /**
     * 查询设备Domain信息
     *
     * @param sid
     * @return
     */
    @Override
    public DeviceInfoDomain selectBydeviceId(String sid) {

        return deviceInfoDao.selectDomainByPrimaryKey(sid);
    }


    /**
     * @return java.util.List<com.cloud.cang.model.sb.DeviceInfo>
     * @Author: zhouhong
     * @Description: 获取商户所有有效设备
     * @param: smerchantId 商户Id
     * @Date: 2018/2/10 19:28
     */
    @Override
    public List<DeviceInfo> selectAllValidDeviceByMerchantId(String smerchantId) {
        return deviceInfoDao.selectAllValidDeviceByMerchantId(smerchantId);
    }

    /**
     * @return java.util.List<com.cloud.cang.model.sb.DeviceInfo>
     * @Author: zhouhong
     * @Description: 获取商户所有有效设备
     * @param: smerchantId 商户Id
     * @Date: 2018/2/10 19:28
     */
    @Override
    public List<DeviceInfo> selectAvailable(String smerchantId, String queryCondition) {
        Map<String, String> map = new HashMap();
        map.put("smerchantId", smerchantId);
        map.put("queryCondition", queryCondition);
        return deviceInfoDao.selectAvailable(map);
    }

    /**
     * 设备编号获取设备信息
     *
     * @param scode 设备编号
     * @return
     */
    @Override
    public DeviceInfo selectByCode(String scode) {
        return deviceInfoDao.selectByCode(scode);
    }

    /**
     * 根据批量ID查询设备信息集合
     *
     * @param deviceArray 设备ID数字
     * @return
     */
    @Override
    public List<DeviceInfoDomain> selectBykeys(String[] deviceArray) {
        List<DeviceInfoDomain> deviceInfoList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(deviceArray)) {
            for (String id : deviceArray) {
                DeviceInfoDomain deviceInfo = deviceInfoDao.selectDomainByPrimaryKey(id);
                if (null != deviceInfo) {
                    deviceInfoList.add(deviceInfo);
                }
            }
        }
        return deviceInfoList;
    }

    /**
     * 查询设备信息及分组信息
     *
     * @param deviceId 设备Id
     * @return
     */
    public DeviceInfoDomain selectDeviceMessageById(String deviceId) {
        return deviceInfoDao.selectDeviceMessageById(deviceId);
    }

    /**
     * 修改设备状态
     *
     * @param checkboxId
     * @return
     */
    @Override
    public ResponseVo<String> updateDeviceStatus(String[] checkboxId) {
        DeviceInfo deviceInfo = null;

        // 校验参数，检查设备状态
        for (String id : checkboxId) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            deviceInfo = deviceInfoDao.selectByPrimaryKey(id);
            if (null != deviceInfo && !Integer.valueOf(20).equals(deviceInfo.getIstatus())) { // 正常设备才能进行离线操作
                return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("选中设备中包含不是正常状态的设备");
            }
        }

        //修改设备状态为离线
        for (String id : checkboxId) {
            deviceInfo = new DeviceInfo();
            deviceInfo.setId(id);
            deviceInfo.setIstatus(DeviceConst.DEVICE_OFFLINE);  // 50 离线
            deviceInfoDao.updateByIdSelective(deviceInfo);
            sendMsgToNettyService.offline(id); // 向netty服务器发送消息将设备离线
        }
        return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
    }

    /**
     * 向设备发送二维码
     *
     * @param id
     * @param sqrUrl
     */
    @Override
    public void sendQrCode(String id, String sqrUrl) {
        sendMsgToNettyService.sendQrCode(id, sqrUrl);
    }


    /**
     * 运营广告位1
     *
     * @param deviceIds
     * @param userId
     * @return
     */
    @Override
    public ResponseVo<String> sendAdOne(String deviceIds, String userId, String merchantCode) {
        return sendMsgToNettyService.sendAdOne(deviceIds, userId, merchantCode);
    }

    /**
     * 运营广告位2
     *
     * @param deviceIds
     * @param userId
     * @return
     */
    @Override
    public ResponseVo<String> sendAdTwo(String deviceIds, String userId, String merchantCode) {
        return sendMsgToNettyService.sendAdTwo(deviceIds, userId, merchantCode);
    }

    /**
     * 运营广告位3
     *
     * @param deviceIds
     * @param userId
     * @return
     */
    @Override
    public ResponseVo<String> sendAdThree(String deviceIds, String userId, String merchantCode) {
        return sendMsgToNettyService.sendAdThree(deviceIds, userId, merchantCode);
    }

    /**
     * @param checkboxId 设备ID
     * @param status
     * @return
     */
    @Override
    public ResponseVo<String> updateDeviceOperatingStatus(String[] checkboxId, String status) {
        String deviceId = checkboxId[0];
        if (StringUtils.isNotBlank(deviceId)) {
            DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
            if (deviceInfo != null) {
                DeviceInfo deviceInfo1 = new DeviceInfo();
                deviceInfo1.setId(deviceId);
                if (Constrants.DEVICE_DISABLE.equals(status)) { // 禁用该设备
                    deviceInfo1.setIoperateStatus(20); // 停用
                } else if (Constrants.DEVICE_ENABLE.equals(status)) { // 启用该设备
                    deviceInfo1.setIoperateStatus(10); // 启用
                } else {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("操作参数不对");
                }
                deviceInfoDao.updateByIdSelective(deviceInfo1);
                return ResponseVo.getSuccessResponse("ok");
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("操作失败");
    }


    /**
     * 子集商户修改设备信息
     *
     * @param deviceInfo   设备信息
     * @param deviceManage 设备负责人信息
     * @param request      请求
     * @return
     */
    @Override
    public ResponseVo<DeviceInfo> updateInfo(DeviceInfo deviceInfo, DeviceManage deviceManage, HttpServletRequest request) {
        String deviceId = request.getParameter("deviceInfoId"); // 设备ID
        String deviceManageId = request.getParameter("deviceManageId");//设备管理主键ID
        if (StringUtils.isBlank(deviceInfo.getSname())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备名称不能为空");
        }
        if (StringUtils.isNotBlank(deviceId)) {

            /*  1.修改数据入设备基础信息表  */
            //省份信息拼接
            if (StringUtil.isNotBlank(deviceInfo.getSprovinceName())) {//省份
                deviceInfo.setSprovinceId(deviceInfo.getSprovinceName().split("_")[0]);
                deviceInfo.setSprovinceName(deviceInfo.getSprovinceName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceInfo.getScityName())) {//城市
                deviceInfo.setScityId(deviceInfo.getScityName().split("_")[0]);
                deviceInfo.setScityName(deviceInfo.getScityName().split("_")[1]);
            }
            if (StringUtil.isNotBlank(deviceInfo.getSareaName())) {//区县
                deviceInfo.setSareaId(deviceInfo.getSareaName().split("_")[0]);
                deviceInfo.setSareaName(deviceInfo.getSareaName().split("_")[1]);
            }
            deviceInfo.setId(deviceId);
            deviceInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            deviceInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            deviceInfoDao.updateByIdSelective(deviceInfo);


            /*  2.修改数据入设备管理表  */
            if (StringUtils.isNotBlank(deviceManageId)) {
                deviceManage.setId(deviceManageId);
                deviceManage.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                deviceManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
                deviceManageDao.updateByIdSelectiveVo(deviceManage);
                String logText2 = MessageFormat.format("修改设备管理信息，编号{0}", deviceInfo.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText2, null);
            }


            /*  2.修改数据入设备分组关系表  */
            String sgroup_id = request.getParameter("sgroup_id");//分组ID
            String oldGroupId = request.getParameter("oldGroupId");//设备分组关系ID
            if (StringUtils.isNotBlank(sgroup_id) && StringUtils.isNotBlank(oldGroupId)) {
                groupRelationshipDao.deleteByPrimaryKey(oldGroupId);//根据设备分组关系表主键，删除之前的记录
                GroupRelationship groupRelationship = new GroupRelationship();
                groupRelationship.setSdeviceId(deviceId);
                groupRelationship.setSgroupId(sgroup_id);
                groupRelationshipDao.insert(groupRelationship);
            } else if (StringUtils.isBlank(sgroup_id) && StringUtils.isNotBlank(oldGroupId)) {
                groupRelationshipDao.deleteByPrimaryKey(oldGroupId);
            } else if (StringUtils.isNotBlank(sgroup_id) && StringUtils.isBlank(oldGroupId)) {
                GroupRelationship groupRelationship = new GroupRelationship();
                groupRelationship.setSdeviceId(deviceId);
                groupRelationship.setSgroupId(sgroup_id);
                groupRelationshipDao.insert(groupRelationship);
            }
        }

        return ResponseVo.getSuccessResponse(deviceInfo);
    }

    /**
     * 查询该商户下的所有未删除设备
     *
     * @param merchantId
     * @return
     */
    @Override
    public List<DeviceInfo> selectAllDeviceInfoByMerchantId(String merchantId) {
        return deviceInfoDao.selectAllDeviceInfoByMerchantId(merchantId);
    }

    /**
     * 检查选中设备中是否包含不是37仓的设备
     * 检验选中设备的运行状态时否正常
     *
     * @param array
     * @param merchantId
     * @return
     */
    @Override
    public ResponseVo<String> checkDeviceAttribution(String[] array, String merchantId) {
        List<DeviceInfo> deviceInfoList = deviceInfoDao.selectAllDeviceInfoByMerchantId(merchantId);
        if (CollectionUtils.isNotEmpty(deviceInfoList)) {
            List<String> deviceIdList = new ArrayList<>();
            for (DeviceInfo deviceInfo : deviceInfoList) {
                deviceIdList.add(deviceInfo.getId());
            }

            for (String id : array) {
                if (StringUtils.isNotBlank(id)) {
                    if (!deviceIdList.contains(id)) {
                        throw new ServiceException("选中设备中包含非本商户的设备");

                    } else {    // 校验设备状态
                        for (DeviceInfo deviceInfo : deviceInfoList) {
                            if (id.equals(deviceInfo.getId()) && deviceInfo.getIstatus() != 20) {
                                throw new ServiceException("选中设备不是正常运行状态");
                            }
                        }
                    }

                }
            }

            return ResponseVo.getSuccessResponse("校验通过");
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户下没有查询到设备信息");
    }


    /**
     * 查询所有未注册的TCP连接
     *
     * @return
     */
    @Override
    public List<TcpVo> selectUnRegistered() {
        List<TcpVo> tcpVoList = new ArrayList<>();
        TcpResult tcpResult = sendMsgToNettyService.selectUnRegistered();
        if (null != tcpResult) {
            tcpVoList = tcpResult.getTcpVoList();
        }
        return tcpVoList;
    }

    /**
     * 查询所有已经注册的TCP连接
     *
     * @return
     */
    @Override
    public List<TcpVo> selectRegistered() {
        List<TcpVo> tcpVoList = new ArrayList<>();
        TcpResult tcpResult = sendMsgToNettyService.selectRegistered();
        if (null != tcpResult) {
            tcpVoList = tcpResult.getTcpVoList();
        }
        return tcpVoList;
    }

    /**
     * 断开未注册的TCP连接
     *
     * @param channelId
     * @return
     */
    @Override
    public ResponseVo<String> disconnetTcp(String channelId) {
        return sendMsgToNettyService.disconnetTcp(channelId);
    }

    /**
     * 断开已经注册到服务器的TCP连接
     *
     * @param deviceId
     * @return
     */
    @Override
    public ResponseVo<String> disconnectRegisterTcp(String deviceId) {
        return sendMsgToNettyService.disconnectRegisterTcp(deviceId);
    }

    /**
     * 根据sdeviceId查询设备名称
     *
     * @param sid
     * @return
     */
    @Override
    public DeviceInfo selectBySid(String sid) {
        return deviceInfoDao.selectBySid(sid);
    }

    @Override
    public Page<DeviceInfoDomain> selectAllDevice(Page<DeviceInfoDomain> page, DeviceInfoVo deviceInfoVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String address = deviceInfoVo.getSputAddress();
        if (StringUtils.isNotBlank(address)) {
            char[] chars = address.toCharArray();
            deviceInfoVo.setSputAddress(StringUtils.join(chars, '%'));
        }
        return deviceInfoDao.selectAllDevice(deviceInfoVo);
    }

    /**
     * 记录升级日志
     * 发送升级消息
     *
     * @param ids          设备ID集合
     * @param timeType     0立即，1定时
     * @param version      版本号
     * @param dproduceDate 定时时间
     * @param user         操作员
     * @param irangeDevice 升级设备范围（全部，部分）
     * @param apkFile      apk文件
     * @return
     */
    @Override
    public ResponseVo recordAndSendApkUpgradeMsg(String[] ids, String timeType, String version, String dproduceDate, String user, String irangeDevice, File apkFile, Integer upFileType, String localAddress) {
        //图片上传
        String url = "";
        if (10 == upFileType) {
            String suffixUrl = uploadApkHome(apkFile, "apkFile");
            if (StringUtils.isNotBlank(suffixUrl)) {
                String prefixUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  ftp服务器地址前缀
                url = prefixUrl + suffixUrl;
                //删除本地文件
                apkFile.delete();
            }
        } else {
            String prefixUrl = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG, "apkUpgrade");        //  ftp服务器地址前缀
            url = prefixUrl + localAddress;
        }
        if (StringUtils.isNotBlank(url)) {
            DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
            if ("0".equals(irangeDevice)) {
                deviceUpgrade.setIdeviceType(10); // 10全部 20部分
            } else {
                deviceUpgrade.setIdeviceType(20); // 10全部 20部分
            }
            if ("0".equals(timeType)) {
                deviceUpgrade.setIactionType(10);   // 10立即 20定时
            } else {
                deviceUpgrade.setIactionType(20);
                deviceUpgrade.setSexecutionTime(DateUtils.convertToDateTime(dproduceDate)); // 定时时间
            }
            deviceUpgrade.setItype(30);                                     // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级
            deviceUpgrade.setIdelFlag(0);
            deviceUpgrade.setIversion(new Integer(version));                // 版本号
            deviceUpgrade.setSresourcesUrl(url);                            // 升级包地址
            deviceUpgrade.setIdeviceNum(ids.length);                        // 升级推送台数
            deviceUpgrade.setInoticeNum(ids.length);                        // 升级通知台数
            deviceUpgrade.setIstatus(20);                                   //10=草稿 20=已通知 30=已完成 40=已取消
            deviceUpgrade.setTaddTime(DateUtils.getCurrentDateTime());
            deviceUpgrade.setSaddUser(user);
            deviceUpgrade.setTupdateTime(DateUtils.getCurrentDateTime());
            deviceUpgrade.setSupdateUser(user);
            deviceUpgradeDao.insert(deviceUpgrade);
            ResponseVo responseVo = this.operate(ids, NettyConst.SYSTEMUPGRADE, url + "-//-" + version + "-//-" + dproduceDate + "-//-" + deviceUpgrade.getId());
            if (!responseVo.isSuccess()) {
                throw new ServiceException("推送设备升级Apk消息失败");
            }
            return responseVo;
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
    }

    /**
     * @param ids
     * @param timeType
     * @param url
     * @param version
     * @param dproduceDate
     * @param user
     * @param irangeDevice
     * @return
     */
    @Override
    public ResponseVo recordAndSendOsServiceUpgradeMsg(String[] ids, String timeType, String url, String version, String dproduceDate, String user, String irangeDevice) {
        DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
        if ("0".equals(irangeDevice)) {
            deviceUpgrade.setIdeviceType(10); // 10全部 20部分
        } else {
            deviceUpgrade.setIdeviceType(20); // 10全部 20部分
        }
        if ("0".equals(timeType)) {
            deviceUpgrade.setIactionType(10);   // 10立即 20定时
        } else {
            deviceUpgrade.setIactionType(20);
            deviceUpgrade.setSexecutionTime(DateUtils.convertToDateTime(dproduceDate)); // 定时时间
        }
        deviceUpgrade.setItype(10);                                     // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级
        deviceUpgrade.setIdelFlag(0);
        deviceUpgrade.setIversion(new Integer(version));                // 版本号
        deviceUpgrade.setSresourcesUrl(url);                            // 升级包地址
        deviceUpgrade.setIdeviceNum(ids.length);                        // 升级推送台数
        deviceUpgrade.setInoticeNum(ids.length);                        // 升级通知台数
        deviceUpgrade.setIstatus(20);                                   //10=草稿 20=已通知 30=已完成 40=已取消
        deviceUpgrade.setTaddTime(DateUtils.getCurrentDateTime());
        deviceUpgrade.setSaddUser(user);
        deviceUpgrade.setTupdateTime(DateUtils.getCurrentDateTime());
        deviceUpgrade.setSupdateUser(user);
        deviceUpgradeDao.insert(deviceUpgrade);
        ResponseVo responseVo = this.operate(ids, NettyConst.VR_SERVER_UPGRADE, url + "-//-" + version + "-//-" + dproduceDate + "-//-" + deviceUpgrade.getId());
        if (!responseVo.isSuccess()) {
            throw new ServiceException("推送设备升级视觉服务消息失败");
        }
        return responseVo;

    }

    @Override
    public ResponseVo recordAndSendVrOsFeatureLibUpgradeMsg(String[] ids, String timeType, String url, String version, String dproduceDate, String user, String irangeDevice, Integer upFileType, File vrFile) {
        DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
        if ("0".equals(irangeDevice)) {
            deviceUpgrade.setIdeviceType(10); // 10全部 20部分
        } else {
            deviceUpgrade.setIdeviceType(20); // 10全部 20部分
        }
        if ("0".equals(timeType)) {
            deviceUpgrade.setIactionType(10);   // 10立即 20定时
        } else {
            deviceUpgrade.setIactionType(20);
            deviceUpgrade.setSexecutionTime(DateUtils.convertToDateTime(dproduceDate)); // 定时时间
        }

        if (10 == upFileType) {
            String suffixUrl = uploadApkHome(vrFile, "gradeVrLib");
            if (StringUtils.isNotBlank(suffixUrl)) {
                String prefixUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  ftp服务器地址前缀
                url = prefixUrl + suffixUrl;
                //删除本地文件
                vrFile.delete();
            }
        }
        deviceUpgrade.setItype(20);                                     // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级
        deviceUpgrade.setIdelFlag(0);
        deviceUpgrade.setIversion(new Integer(version));                // 版本号
        deviceUpgrade.setSresourcesUrl(url);                            // 升级包地址
        deviceUpgrade.setIdeviceNum(ids.length);                        // 升级推送台数
        deviceUpgrade.setInoticeNum(ids.length);                        // 升级通知台数
        deviceUpgrade.setIstatus(20);                                   //10=草稿 20=已通知 30=已完成 40=已取消
        deviceUpgrade.setTaddTime(DateUtils.getCurrentDateTime());
        deviceUpgrade.setSaddUser(user);
        deviceUpgrade.setTupdateTime(DateUtils.getCurrentDateTime());
        deviceUpgrade.setSupdateUser(user);
        deviceUpgradeDao.insert(deviceUpgrade);
        ResponseVo responseVo = null;
        if ("0".equals(timeType)) {
            responseVo = this.operate(ids, NettyConst.UPDATE_FEATURE_LIBRARY, url + "-//-" + version + "-//-" + dproduceDate + "-//-" + deviceUpgrade.getId());
        } else { // start timingTask
            responseVo = timingTaskUpgrade(ids, deviceUpgrade, 20, "本地识别视觉识别库升级");
        }
        if (!responseVo.isSuccess()) {
            throw new ServiceException("推送视觉服务库消息失败");
        }
        return responseVo;
    }

    /**
     * 定时任务
     *
     * @param ids           IDs
     * @param deviceUpgrade 升级记录对象
     * @param iType         10=APK升级 20=视觉库升级 30=视觉服务升级
     * @param remark        备注
     * @return
     */
    private ResponseVo timingTaskUpgrade(String[] ids, DeviceUpgrade deviceUpgrade, int iType, String remark) {
        TimingTask timingTask = new TimingTask();
        timingTask.setScode(CoreUtils.newCode(EntityTables.SB_TIMING_TASK));
        timingTask.setIdelFlag(0);
        timingTask.setStaskObject(JSON.toJSONString(ids));
        timingTask.setStaskContent(JSON.toJSONString(deviceUpgrade));
        timingTask.setItype(iType);
        timingTask.setIstatus(10);
        timingTask.setTaddTime(DateUtils.getCurrentDateTime());
        timingTask.setTupdateTime(DateUtils.getCurrentDateTime());
        timingTask.setSremark(remark);
        timingTask.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        timingTask.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        timingTaskDao.insert(timingTask);
        //定时更新
        MgrJobDto mgrJobDto = new MgrJobDto();
        mgrJobDto.setJobName(timingTask.getScode());
        mgrJobDto.setTime(deviceUpgrade.getSexecutionTime());
        mgrJobDto.setParamType(iType);
        RestServiceInvoker invoke = null;// 服务名称
        ResponseVo responseVo = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(GpuServerServicesDefine.MGR_OPERATE_DEVICE_UPGRADE);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(mgrJobDto); // post 参数
            responseVo = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error("调用操作设备定时执行服务出现异常");
            responseVo = ErrorCodeEnum.ERROR_COMMON_HANDING.getResponseVo();
        }
        return responseVo;
    }

    /**
     * 盘货
     *
     * @param deviceId 设备ID
     * @return
     */
    @Override
    public ResponseVo<String> inventory(String deviceId) {

        // 获取选中设备信息
        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
        if (null == deviceInfo) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备状态值不正确");
        }
        if (deviceInfo.getIstatus() != 20) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备不在线");
        }

        ResponseVo responseVo = ErrorCodeEnum.ERROR_COMMON_HANDING.getResponseVo();
        logger.info("设备盘货开始，设备编号：{}", deviceInfo.getScode());
        logger.debug("调用主动盘货后台接口开始");
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setDeviceId(deviceId);
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.ACTIVE_INVENTORY);
            invoke.setRequestObj(inventoryDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            logger.debug("调用主动盘货后台接口结束");
            responseVo = (ResponseVo<String>) invoke.invoke();

            // 从Redis中移除设备库存商品主键出现异常
            iCached.hremove(NettyConst.DEVICE_ACTIVE_INVENTORY_COMMODITY, deviceId);
        } catch (Exception e) {
            logger.error("调用设备主动盘货异常：{}", e);
        }
        return responseVo;

    }

    /**
     * 升级设备播放语音
     *
     * @param file         音频文件
     * @param voiceType    语音类型： 10 开门 20 购物 30 关门
     * @param deviceIds    设备IDs
     * @param irangeDevice 设备：10全部 20部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes  设备编号
     * @param merchantCode 商户编号
     * @return
     */
    @Override
    public ResponseVo upgradeVoice(MultipartFile file, String voiceType, String deviceIds, String
            irangeDevice, String merchantId, String user, String deviceCodes, String merchantCode) {
        String[] ids = getIds(deviceIds, irangeDevice, merchantId);         // 有效的商户ID

        // 上传文件
        String url = uploadHome(file, "voiceFile");
        if (StringUtils.isNotBlank(url)) {
                /* 开始记录升级记录*/
            DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
            // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级 40=开门语音 50=购物中语音 60=关门语音
            if ("10".equals(voiceType)) {
                deviceUpgrade.setItype(40);
            } else if ("20".equals(voiceType)) {
                deviceUpgrade.setItype(50);
            } else if ("30".equals(voiceType)) {
                deviceUpgrade.setItype(60);
            }
            if ("10".equals(irangeDevice)) {
                deviceUpgrade.setIdeviceType(10);
            } else {
                deviceUpgrade.setIdeviceType(20);
            }
            deviceUpgrade.setIactionType(10);
            deviceUpgrade.setIdelFlag(0);
            deviceUpgrade.setIversion(0);                                   // 版本号
            deviceUpgrade.setSresourcesUrl(url);                            // 升级包地址
            deviceUpgrade.setIdeviceNum(ids.length);                        // 升级推送台数
            deviceUpgrade.setInoticeNum(ids.length);                        // 升级通知台数
            deviceUpgrade.setIstatus(20);                                   //10=草稿 20=已通知 30=已完成 40=已取消
            deviceUpgrade.setTaddTime(DateUtils.getCurrentDateTime());
            deviceUpgrade.setSaddUser(user);
            deviceUpgrade.setTupdateTime(DateUtils.getCurrentDateTime());
            deviceUpgrade.setSupdateUser(user);
            deviceUpgradeDao.insert(deviceUpgrade);

            String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  ftp服务器地址前缀

            List<String> deviceIdList = Arrays.asList(deviceIds.split(","));
            List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
            UpgradeVoiceVo upgradeVoiceVo = new UpgradeVoiceVo();
            upgradeVoiceVo.setUrl(preUrl + url);
            upgradeVoiceVo.setVoiceType(voiceType);
            upgradeVoiceVo.setMainId(deviceUpgrade.getId());
            upgradeVoiceVo.setType(irangeDevice);
            upgradeVoiceVo.setDeviceIdList(deviceIdList);
            upgradeVoiceVo.setDeviceCodeList(deviceCodeList);
            String upgradeVoiceVoStr = JSON.toJSONString(upgradeVoiceVo);
            DeviceOperatingDto deviceOperatingDto = new DeviceOperatingDto(upgradeVoiceVoStr, NettyConst.UPGRADEVOICE, user, merchantId, merchantCode);
            logger.debug("调用升级设备播放语音远程服务开始，请求参数：{}", deviceOperatingDto);
            // 向设备推送升级语音消息
            ResponseVo<String> responseVo = invokeDeviceOperating(deviceOperatingDto, NettyConst.UPGRADEVOICE);
            logger.debug("调用升级设备播放语音远程服务完成，返回结果：{}", responseVo);
            return responseVo;
        } else {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("上传语音文件失败");
        }
    }

    /**
     * 调节设备音量大小
     *
     * @param volumeValue  音量值
     * @param deviceIds    设备ID
     * @param irangeDevice 设备：10全部 20部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes  设备编号
     * @param merchantCode 商户编号
     * @return
     */
    @Override
    public ResponseVo<String> adjustVolume(String volumeValue, String deviceIds, String irangeDevice,
                                           String merchantId, String user, String deviceCodes, String merchantCode) {
        List<String> deviceIdList = Arrays.asList(deviceIds.split(","));
        List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
        AdjustVolumeVo adjustVolumeVo = new AdjustVolumeVo();
        adjustVolumeVo.setVolumeValue(volumeValue);
        adjustVolumeVo.setType(irangeDevice);
        adjustVolumeVo.setDeviceIdList(deviceIdList);
        adjustVolumeVo.setDeviceCodeList(deviceCodeList);
        String adjustVolumeVoStr = JSON.toJSONString(adjustVolumeVo);
        DeviceOperatingDto deviceOperatingDto = new DeviceOperatingDto(adjustVolumeVoStr, NettyConst.VOLUME, user, merchantId, merchantCode);
        // 向设备推送消息
        logger.debug("调用调节设备音量大小远程服务开始，请求参数：{}", deviceOperatingDto);
        ResponseVo<String> responseVo = invokeDeviceOperating(deviceOperatingDto, NettyConst.VOLUME);
        logger.debug("调用调节设备音量大小远程服务完成，返回结果：{}", responseVo);
        if (responseVo.isSuccess()) {
            insertBackstageOperLog(10, irangeDevice, "调节音量值为：" + volumeValue, deviceOperatingDto, user);
        }
        return responseVo;
    }

    /**
     * 根据设备ID获取设备编号
     *
     * @param ids 设备IDs
     * @return
     */
    private String[] getDeviceCodes(String[] ids) {
        String[] deviceCodes = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(ids[i]);
            deviceCodes[i] = deviceInfo.getScode();
        }
        return deviceCodes;
    }

    /**
     * 重启设备操作
     *
     * @param deviceIds    设备ID
     * @param irangeDevice 设备范围：10 全部 20 部分
     * @param merchantId   商户ID
     * @param user         操作员
     * @param deviceCodes  设备编号
     * @param merchantCode 商户编号
     */
    @Override
    public ResponseVo<String> reboot(String deviceIds, String irangeDevice, String merchantId, String user, String deviceCodes, String merchantCode) {
        List<String> deviceIdList = Arrays.asList(deviceIds.split(","));
        List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
        RebootVo rebootVo = new RebootVo();
        rebootVo.setType(irangeDevice);
        rebootVo.setDeviceIdList(deviceIdList);
        rebootVo.setDeviceCodeList(deviceCodeList);
        String rebootVoStr = JSON.toJSONString(rebootVo);
        DeviceOperatingDto deviceOperatingDto = new DeviceOperatingDto(rebootVoStr, NettyConst.REBOOT, user, merchantId, merchantCode);
        // 向设备推送消息
        logger.debug("调用重启设备操作远程服务开始，请求参数：{}", deviceOperatingDto);
        ResponseVo<String> responseVo = invokeDeviceOperating(deviceOperatingDto, NettyConst.REBOOT);
        logger.debug("调用重启设备操作远程服务完成，返回结果：{}", responseVo);
        if (responseVo.isSuccess()) {
            insertBackstageOperLog(20, irangeDevice, "重启设备", deviceOperatingDto, user);
        }
        return responseVo;
    }


    /**
     * 设备关机操作
     *
     * @param deviceIds    设备ID
     * @param deviceCodes  设备编号
     * @param irangeDevice 设备范围：10 全部 20 部分
     * @param merchantId   商户ID
     * @param merchantCode 商户编号
     * @param user         操作员
     * @return
     */
    @Override
    public ResponseVo<String> shutdown(String deviceIds, String deviceCodes, String irangeDevice, String merchantId, String merchantCode, String user) {
        List<String> deviceIdList = Arrays.asList(deviceIds.split(","));
        List<String> deviceCodeList = Arrays.asList(deviceCodes.split(","));
        ShutdownVo shutdownVo = new ShutdownVo();
        shutdownVo.setType(irangeDevice);
        shutdownVo.setDeviceIdList(deviceIdList);
        shutdownVo.setDeviceCodeList(deviceCodeList);
        String shutdownVoStr = JSON.toJSONString(shutdownVo);
        DeviceOperatingDto deviceOperatingDto = new DeviceOperatingDto(shutdownVoStr, NettyConst.SHUTDOWN, user, merchantId, merchantCode);
        // 向设备推送消息
        logger.debug("调用设备关机远程服务开始，请求参数：{}", deviceOperatingDto);
        ResponseVo<String> responseVo = invokeDeviceOperating(deviceOperatingDto, NettyConst.SHUTDOWN);
        logger.debug("调用设备关机远程服务完成，返回结果：{}", responseVo);
        if (responseVo.isSuccess()) {
            insertBackstageOperLog(30, irangeDevice, "关机", deviceOperatingDto, user);
        }
        return responseVo;
    }

    /**
     * 设备操作日志记录
     *
     * @param itype       操作类型 10=调节音量 20=重启设备 30=关机 40=定时开关机 50=主动盘货
     * @param ideviceType 操作对象类型 10=全部    20=部分
     * @param content     操作内容
     * @param object      操作对象
     * @param operator    操作员
     */
    private void insertBackstageOperLog(Integer itype, String ideviceType, String content, Object object, String operator) {
        logger.debug("插入设备操作日志记录开始");
        BackstageOper backstageOper = new BackstageOper();
        backstageOper.setIdelFlag(0);
        if ("10".equals(ideviceType)) {
            backstageOper.setIdeviceType(10);
        } else {
            backstageOper.setIdeviceType(20);
        }
        backstageOper.setItype(itype);
        backstageOper.setSoperContent(JSON.toJSONString(content));
        backstageOper.setSoperObject(JSON.toJSONString(object));
        backstageOper.setSaddUser(operator);
        backstageOper.setTaddTime(DateUtils.getCurrentDateTime());
        backstageOperDao.insert(backstageOper);
        logger.debug("插入设备操作日志记录完成，日志：{}", JSON.toJSONString(backstageOper));
    }

    /**
     * 批量修改云端柜子实时盘货购物车开关
     *
     * @param merchantId             商户ID
     * @param merchantCode           商户编号
     * @param shoppingInventory      购物开关
     * @param replenishmentInventory 补货开关
     * @param deviceIds              设备ID
     * @param deviceCodes            设备编号
     * @param deviceType             操作设备范围：10=全部，20=部分
     * @return
     */
    @Override
    public ResponseVo<String> batchRealTimeShoppingCartSwitch(String merchantId, String merchantCode, String shoppingInventory, String replenishmentInventory, String deviceIds, String deviceCodes, String deviceType) {
        logger.info("批量操作设备修改云端识别实时购物车开关,操作类型：{}", "10".equals(deviceType) ? "全部" : "部分，设备编号" + deviceCodes);
        CloudParamConfigDto cloudParamConfigDto = new CloudParamConfigDto();
        cloudParamConfigDto.setType(10); //10 设备 20 商户
        cloudParamConfigDto.setMerchantCode(merchantCode);
        cloudParamConfigDto.setMerchantId(merchantId);
        cloudParamConfigDto.setDevices(Arrays.asList(deviceIds.split(",")));
        cloudParamConfigDto.setShoppingInventory(new Integer(shoppingInventory));
        cloudParamConfigDto.setReplenishmentInventory(new Integer(replenishmentInventory));
        RestServiceInvoker invoke = null;
        try {
            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.CLOUD_DEVICE_PARAM_CONFIG);
            invoke.setRequestObj(cloudParamConfigDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null == responseVo || !responseVo.isSuccess()) {
                logger.error("批量修改云端柜子实时盘货购物车失败:{}", responseVo);
                throw new ServiceException("批量修改云端柜子实时盘货购物车失败！");
            }
            // 操作日志
            BackstageOper backstageOper = new BackstageOper();
            backstageOper.setIdelFlag(0);
            if ("1".equals(shoppingInventory)) {
                backstageOper.setItype(60);
                backstageOper.setSoperContent(JSON.toJSONString("开启顾客实时盘货"));    // 操作内容
            } else {
                backstageOper.setItype(70);
                backstageOper.setSoperContent(JSON.toJSONString("关闭顾客实时盘货"));    // 操作内容
            }
            backstageOper.setIdeviceType(10);
            backstageOper.setSoperObject(JSON.toJSONString(cloudParamConfigDto));     // 操作对象
            backstageOper.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            backstageOper.setTaddTime(DateUtils.getCurrentDateTime());
            backstageOperDao.insert(backstageOper);
            logger.debug("批量操作设备是否开启实时盘货成功");
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("批量操作设备是否开启实时盘货字段出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量修改云端柜子实时盘货购物车失败");
    }


    /**
     * 从Redis中获取设备当前的库存信息
     *
     * @param deviceId 设备ID
     * @return
     */
    @Override
    public ResponseVo<List<DeviceInventoryStockDomain>> getDeviceStock(String deviceId) {
        String goodsString = "";
        List<DeviceInventoryStockDomain> domainList = new ArrayList<>();
        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
        String smerchantId = deviceInfo.getSmerchantId();
        // 从Redis中获取商品集合信息
        try {
            goodsString = (String) iCached.hget(NettyConst.DEVICE_ACTIVE_INVENTORY_COMMODITY, deviceId);
        } catch (Exception e) {
            throw new ServiceException("从Redis中获取当前设备实时库存信息出现异常");
        }
        if (StringUtils.isBlank(goodsString)) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("未获取到设备库存信息");
        }

        // 1.没有库存，库存商品信息为零
        if (NettyConst.DEVICE_STOCK_IS_ZERO.equals(goodsString)) {
            return ResponseVo.getSuccessResponse(domainList);
        }

        // 2.有库存
        Goods goods = new Goods();                                  // 设备商品集合mode
        // 将商品对象由字符串转化为对象
        try {
            if (StringUtil.isNotBlank(goodsString) && !goodsString.equals("[]")) {
                goods = JSON.parseObject(goodsString, Goods.class);                 //商品集合
                List<TagModel> tagModelList = goods.getGoodsList();
                if (null != tagModelList && CollectionUtils.isNotEmpty(tagModelList)) {
                    for (TagModel tag : tagModelList) {
                        String svrCode = tag.getSvrCode();
                        if (StringUtils.isBlank(svrCode)) {
                            logger.error("视觉商品编号为空");
                            continue;
                        }
                        // 查询视觉商品信息
                        CommodityInfo commodityInfoVo = new CommodityInfo();
                        commodityInfoVo.setSvrCode(svrCode);
                        commodityInfoVo.setSmerchantId(smerchantId);
                        commodityInfoVo.setIdelFlag(0);
                        commodityInfoVo.setIstatus(10);
                        commodityInfoVo.setIlifeType(10);   // 10=视觉
                        List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfoVo);
                        if (null == commodityInfoList || CollectionUtils.isEmpty(commodityInfoList)) {
                            logger.error("未查询到该视觉商品信息，视觉编号：{}", svrCode);
                            continue;
                        }
                        CommodityInfo commodityInfoDomain = commodityInfoList.get(0);
                        DeviceInventoryStockDomain domain = new DeviceInventoryStockDomain();
                        domain.setCommodityId(commodityInfoDomain.getId());
                        domain.setCommodityCode(commodityInfoDomain.getScode());
                        domain.setCommodityFullName(commodityInfoDomain.getSbrandName() + commodityInfoDomain.getSname()
                                + commodityInfoDomain.getStaste() + commodityInfoDomain.getIspecWeight() + commodityInfoDomain.getSspecUnit()
                                + "/" + commodityInfoDomain.getSpackageUnit());     // 拼接商品全称
                        domain.setFcommodityPrice(commodityInfoDomain.getFsalePrice());
                        domain.setNum(tag.getNumber());
                        domain.setSvrCode(svrCode);
                        domainList.add(domain);
                    }
                }
                return ResponseVo.getSuccessResponse(domainList);
            }
        } catch (Exception e) {
            logger.error("商品集合json格式转化出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("获取设备当前库存信息出错");
    }

    /**
     * 保存当前设备的库存信息
     *
     * @param deviceId     设备ID
     * @param commodityIds 商品IDs
     * @param request      请求
     * @return
     */
    @Override
    public ResponseVo<String> saveActiveInventoryStockInfo(String deviceId, String commodityIds, HttpServletRequest request) {
        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
        if (null == deviceInfo) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备不存在");
        }
        String sdeviceCode = deviceInfo.getScode();
        String smerchantId = deviceInfo.getSmerchantId();
        String smerchantCode = deviceInfo.getSmerchantCode();

        // 判断本次是否存在商品
        List<String> commodityList = new ArrayList<>();
        if (StringUtils.isNotBlank(commodityIds)) {
            String[] arr = commodityIds.substring(0, commodityIds.length() - 1).split(",");
            if (ArrayUtils.isNotEmpty(arr)) {
                for (int i = 0; i < arr.length; i++) {
                    // 查询商品是否存在，不存在插入，存在修改，库存有修改后没有删除
                    String scommodity = request.getParameter("scommodityId_" + arr[i]);                 // 商品ID
                    int num = new Integer(request.getParameter("fcommodityAmount_" + arr[i]));          // 商品数量
                    if (num < 0) {
                        String scommodityCode = request.getParameter("scommodityCode_" + arr[i]);                                 // 商品编号
                        throw new ServiceException("编号: " + scommodityCode + " 的商品数量不能小于0");
                    }
                    commodityList.add(scommodity);
                }
            }
        }


        // 对比库存商品与当前修改后的商品
        DeviceStock deviceStockVo = new DeviceStock();
        deviceStockVo.setSdeviceId(deviceId);
        deviceStockVo.setSmerchantId(smerchantId);
        List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStockVo);
        List<DeviceStock> publicStockList = new ArrayList<>();
        List<String> publicCommodityIdList = new ArrayList<>();

        // 没有商品，如果库存有商品需要将商品数量清零
        if (CollectionUtils.isEmpty(commodityList)) {
            logger.info("没有商品入库，设备ID：{}", deviceId);
            if (CollectionUtils.isNotEmpty(deviceStockList)) {
                updateStockToZero(deviceId, smerchantId, deviceStockList);
            }
            return ResponseVo.getSuccessResponse();
        }


        if (null == deviceStockList || CollectionUtils.isEmpty(deviceStockList)) {  // 库存为零，所有商品都是新增
            insertDeviceStock(deviceId, sdeviceCode, smerchantId, smerchantCode, request, commodityList);
        } else {    // 库存不为零，对比商品类型和数量变化
            for (DeviceStock deviceStock : deviceStockList) {
                for (String tempId : commodityList) {
                    if (deviceStock.getScommodityId().equals(tempId)) {
                        publicStockList.add(deviceStock);
                        publicCommodityIdList.add(tempId);
                    }
                }
            }

            List<DeviceStock> subStockList = new ArrayList<>(); // 减少的库存
            subStockList.addAll(deviceStockList);
            subStockList.removeAll(publicStockList);

            List<String> addCommodityList = new ArrayList<>();  // 新增的商品
            addCommodityList.addAll(commodityList);
            addCommodityList.removeAll(publicCommodityIdList);
            // 以前有现在没有，修改库存数为零（如果库存数量本身为零，不做修改）
            if (CollectionUtils.isNotEmpty(subStockList)) {
                updateStockToZero(deviceId, smerchantId, subStockList);
            }
            // 以前有现在有，不为零时，修改库存数为当前值
            if (CollectionUtils.isNotEmpty(publicStockList)) {
                updateStockByNowCommodity(deviceId, smerchantId, publicCommodityIdList, publicStockList, request);
            }
            // 以前没有现在有，新增库存
            if (CollectionUtils.isNotEmpty(addCommodityList)) {
                insertDeviceStock(deviceId, sdeviceCode, smerchantId, smerchantCode, request, addCommodityList);
            }
        }
        return ResponseVo.getSuccessResponse();
    }


    /**
     * 对比商品数量是否变化，如果变化，进行修改修改否则不变
     *
     * @param deviceId              设备ID
     * @param smerchantId           商户ID
     * @param publicCommodityIdList 相同的商品
     * @param publicStockList       相同库存商品
     * @param request               请求
     */
    private void updateStockByNowCommodity(String deviceId, String smerchantId, List<String> publicCommodityIdList, List<DeviceStock> publicStockList, HttpServletRequest request) {
        for (String scommodityId : publicCommodityIdList) {
            for (DeviceStock deviceStock : publicStockList) {
                if (scommodityId.equals(deviceStock.getScommodityId())) {
                    int nowNum = new Integer(request.getParameter("fcommodityAmount_" + scommodityId));                                // 商品数量
                    if (nowNum != deviceStock.getIstock()) {
                        DeviceStock deviceStockVo = new DeviceStock();
                        deviceStockVo.setId(deviceStock.getId());
                        deviceStockVo.setIstock(nowNum);
                        deviceStockVo.setTlastUpdateTime(new Date());
                        deviceStockDao.updateByIdSelective(deviceStockVo);
                    }
                }
            }
        }

    }

    /**
     * 修改商品库存数量为零（如果库存数量本身为零，不做修改）
     *
     * @param deviceId     设备ID
     * @param smerchantId  商户
     * @param subStockList 需要修改的商品
     */
    private void updateStockToZero(String deviceId, String smerchantId, List<DeviceStock> subStockList) {
        for (DeviceStock stock : subStockList) {
            if (stock.getIstock() == 0) {   // 如果库存商品数量为零，不做修改
                continue;
            }
            DeviceStock deviceStockVo = new DeviceStock();
            deviceStockVo.setId(stock.getId());
            deviceStockVo.setIstock(0);
            deviceStockVo.setTlastUpdateTime(new Date());
            deviceStockDao.updateByIdSelective(deviceStockVo);
        }
    }


    /**
     * 插入新的库存信息
     *
     * @param deviceId      设备ID
     * @param sdeviceCode   设备编号
     * @param smerchantId   商户ID
     * @param smerchantCode 商户编号
     * @param request       请求
     * @param commodityList 新商品集合
     */
    private void insertDeviceStock(String deviceId, String sdeviceCode, String smerchantId, String smerchantCode, HttpServletRequest request, List<String> commodityList) {
        for (String scommodityId : commodityList) {
            // 从request获取信息
            String smCode = CoreUtils.newCode("sm_device_stock");                                                   // 单据编号
            String scommodityCode = request.getParameter("scommodityCode_" + scommodityId);                                 // 商品编号
            int num = new Integer(request.getParameter("fcommodityAmount_" + scommodityId));                                // 商品数量
            BigDecimal scommodityPrice = new BigDecimal(request.getParameter("fcommodityPrice_" + scommodityId));           // 商品单价

            DeviceStock deviceStock = new DeviceStock();
            deviceStock.setFsalePrice(scommodityPrice);
            deviceStock.setIstock(num);
            deviceStock.setScode(smCode);
            deviceStock.setScommodityId(scommodityId);
            deviceStock.setScommodityCode(scommodityCode);
            deviceStock.setSdeviceId(deviceId);
            deviceStock.setSdeviceCode(sdeviceCode);
            deviceStock.setSmerchantCode(smerchantCode);
            deviceStock.setSmerchantId(smerchantId);
            deviceStock.setTaddTime(new Date());
            deviceStock.setTlastUpdateTime(new Date());
            deviceStockDao.insert(deviceStock);
        }
    }

    /**
     * 文件上传
     *
     * @param apkFile  apk文件
     * @param pathName 路径
     * @return
     */
    private String uploadHome(File apkFile, String pathName) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
           /* String originalFileName = file.getOriginalFilename();   // 原文件名
            int lastIndexOf = originalFileName.lastIndexOf(".");//文件后缀名
            String tempName = DateUtils.getCurrentSTimeNumber() + originalFileName.substring(lastIndexOf);//文件名=="时间"+".原文件名后缀"*/
            String tempName = apkFile.getName();
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(new FileInputStream(apkFile), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param apkFile  apk文件
     * @param pathName 路径
     * @return
     */
    private String uploadApkHome(File apkFile, String pathName) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
           /* String originalFileName = file.getOriginalFilename();   // 原文件名
            int lastIndexOf = originalFileName.lastIndexOf(".");//文件后缀名
            String tempName = DateUtils.getCurrentSTimeNumber() + originalFileName.substring(lastIndexOf);//文件名=="时间"+".原文件名后缀"*/
            String tempName = apkFile.getName();
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(new FileInputStream(apkFile), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param file     文件
     * @param pathName 路径
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
            String originalFileName = file.getOriginalFilename();   // 原文件名
            int lastIndexOf = originalFileName.lastIndexOf(".");//文件后缀名
            String tempName = DateUtils.getCurrentSTimeNumber() + originalFileName.substring(lastIndexOf);//文件名=="时间"+".原文件名后缀"
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 获取有效的设备ID
     *
     * @param deviceIds    设备IDs
     * @param irangeDevice 设备范围 ：10 全部，20 部分
     * @param merchantId   商户ID
     * @return
     */
    private String[] getIds(String deviceIds, String irangeDevice, String merchantId) {
        String[] ids = null;
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(deviceIds) && irangeDevice.equals("10")) {  // 全部设备
            List<DeviceInfo> deviceInfoList = deviceInfoDao.selectAllDeviceInfoByMerchantId(merchantId);
            if (CollectionUtils.isNotEmpty(deviceInfoList)) {
                for (DeviceInfo deviceInfo : deviceInfoList) {
                    list.add(deviceInfo.getId());
                }
            }
            ids = (String[]) list.toArray(new String[list.size()]);
        } else if (irangeDevice.equals("20")) {     // 部分设备
            ids = deviceIds.split(",");
        }
        return ids;
    }

    /**
     * 查询该渠道下的所有未删除设备
     *
     * @param channelId
     * @return
     */
    @Override
    public List<DeviceInfo> selectAllDeviceBySmerchantId(String channelId, Map<String, Object> paramMap) {
        paramMap.put("channelId", channelId);
        return deviceInfoDao.selectAllDeviceBySmerchantId(paramMap);
    }

    /**
     * 重置二维码
     */
    @Override
    public void updateSqrUrl(DeviceInfo deviceInfo) {
        deviceInfoDao.updateSqrUrl(deviceInfo);
    }

    /***
     * 设备广告推送
     * @param selectVo 选择设备信息
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public ResponseVo<String> saveNewsUpdate(DeviceSelectVo selectVo, HttpServletRequest request) throws ServiceException, Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        Region region = regionService.selectByCode(selectVo.getSregionCode());
        if (null == region) {
            throw new ServiceException("选择资讯运营位异常，请重新操作");
        }
        List<DeviceInfo> deviceInfos = null;
        if (selectVo.getNewsType().intValue() == 10) {//广告
            if (selectVo.getIrangeDevice().intValue() == 10) {
                //查询渠道下全部设备
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("itype", 100);
                deviceInfos = this.selectAllDeviceBySmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId(), paramMap);
                if (null == deviceInfos || deviceInfos.size() <= 0) {
                    throw new ServiceException("没有可操作的设备数据，请重新操作");
                }
            } else if (selectVo.getIrangeDevice().intValue() == 20) {
                String[] deviceIds = selectVo.getDeviceIds().split(",");
                if (null == deviceIds || deviceIds.length <= 0) {
                    throw new ServiceException("选择设备数据异常，请重新操作");
                }
                deviceInfos = new ArrayList<DeviceInfo>();
                DeviceInfo tempDeviceInfo = null;
                for (String deviceId : deviceIds) {
                    tempDeviceInfo = this.selectByPrimaryKey(deviceId);
                    if (null != tempDeviceInfo && tempDeviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.DeviceOperateStatus.ENABLE.getCode()
                            && (tempDeviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode() ||
                            tempDeviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode())) {
                        deviceInfos.add(tempDeviceInfo);
                    }
                }
                if (deviceInfos.size() <= 0) {
                    throw new ServiceException("没有可操作的设备数据，请重新操作");
                }
            }
            List<String> deviceCodes = new ArrayList<String>();
            for (DeviceInfo temp : deviceInfos) {
                deviceCodes.add(temp.getScode());
            }
            UpdateDeviceAdvertisDto dto = new UpdateDeviceAdvertisDto();
            dto.setSregionCode(region.getScode());
            dto.setDeviceCodes(deviceCodes);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.UPDATE_DEVICE_ADVERTIS);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(dto); // post 参数
            responseVo = (ResponseVo<String>) invoke.invoke();
            logger.info("调用设备广告更新服务返回参数：{}", JSONObject.toJSONString(responseVo));
            if (!responseVo.isSuccess()) {
                throw new ServiceException(responseVo.getMsg());
            }
            //新增设备参数记录
            BackstageOper backstageOper = new BackstageOper();
            backstageOper.setIdelFlag(0);
            backstageOper.setIdeviceType(selectVo.getIrangeDevice());
            backstageOper.setItype(10);
            backstageOper.setSoperContent("更新设备广告资讯，运营区域编号：" + region.getScode());
            backstageOper.setSoperObject(JSONObject.toJSONString(deviceCodes));
            backstageOper.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            backstageOper.setTaddTime(DateUtils.getCurrentDateTime());
            backstageOperService.insert(backstageOper);
            //操作日志
            String logText = MessageFormat.format("操作更新设备广告资讯，编号列表{0}", JSONObject.toJSONString(deviceCodes));
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        }
        return responseVo;
    }
}