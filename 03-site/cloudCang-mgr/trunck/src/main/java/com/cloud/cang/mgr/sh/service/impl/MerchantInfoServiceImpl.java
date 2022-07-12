package com.cloud.cang.mgr.sh.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.dao.MerchantAttachDao;
import com.cloud.cang.mgr.sh.dao.MerchantClientConfDao;
import com.cloud.cang.mgr.sh.dao.MerchantDetailDao;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sh.domain.MerchantInfoDomain;
import com.cloud.cang.mgr.sh.service.*;
import com.cloud.cang.mgr.sh.vo.MerchantInfoVo;
import com.cloud.cang.mgr.sys.service.MerchantPurviewService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.tj.service.SummarizationDataPlfService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.*;
import com.cloud.cang.model.sys.MerchantPurview;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
        MerchantInfoService {

    @Autowired
    MerchantInfoDao merchantInfoDao;
    @Autowired
    private MerchantPurviewService merchantPurviewService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private MerchantDetailDao merchantDetailDao;
    @Autowired
    private MerchantAttachDao merchantAttachDao;
    @Autowired
    private MerchantClientConfDao merchantClientConfDao;
    @Autowired
    private ICached iCached;
    @Autowired
    private MerchantConfService merchantConfService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private MerchantDetailService merchantDetailService;
    @Autowired
    private MerchantClientConfService merchantClientConfService;
    @Autowired
    private MerchantAttachService merchantAttachService;
    @Autowired
    private SummarizationDataPlfService summarizationDataPlfService;
    @Autowired
    private DomainConfService domainConfService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    private static final Logger log = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);

    @Override
    public GenericDao<MerchantInfo, String> getDao() {
        return merchantInfoDao;
    }

    @Override
    public void updateByIdSelectiveBIS_FREEZE(MerchantInfo merchantInfo) {
        merchantInfoDao.updateByIdSelectiveBIS_FREEZE(merchantInfo);
    }

    @Override
    public void delete(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                // 执行逻辑删除
                MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(id);
                if (null == merchantInfo) {
                    log.info("商户不存在！");
                    throw new ServiceException("商户不存在！");
                }
                //1.绑定了设备的商户不能删除
                List<DeviceInfo> list = deviceInfoService.selectAllDeviceInfoByMerchantId(id);
                if (null != list && list.size() > 0) {
                    log.info("已有设备绑定了该商户，删除失败，商户编号：{}", merchantInfo.getScode());
                    throw new ServiceException("已有设备绑定了商户,删除失败！");
                }
                //2.有通过审核域名的商户不能删除
                String url = domainConfService.selectThroughAuditByMerchantId(id);
                if (StringUtils.isNotBlank(url)) {
                    log.info("商户已有域名通过审核，删除失败，商户编号：{}", merchantInfo.getScode());
                    throw new ServiceException("商户已有域名通过审核,删除失败！");
                }
                //3.签约通过的商户不能删除
                if (BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode() == merchantInfo.getIstatus()) {
                    log.info("该商户已签约，删除失败，商户编号：{}", merchantInfo.getScode());
                    throw new ServiceException("商户已签约,删除失败！");
                }
                //如果删除的商户状态为已签约则同时删除该商户缓存
               /* if (20 == merchantInfo.getIstatus()) {
                    //key为商户CODE
                    String catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                    String catcheKey2 = RedisConst.SH_WECHAT_CONFIG + merchantInfo.getScode();
                    String catcheKey3 = RedisConst.SH_ALIPAY_CONFIG + merchantInfo.getScode();
                    String catcheKey4 = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                    try {
                        iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey);
                        iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey2);
                        iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey3);
                        iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey4);
                    } catch (Exception e) {
                        log.error("----删除已签约商户，更新Redis结束，缓存失败！！，错误信息：" + e.getMessage());
                        throw new ServiceException(e.getMessage());
                    }
                    log.debug("----更新Redis缓存主KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + JSON.toJSONString(merchantInfo));
                }*/
                MerchantInfo upMerchantInfo = new MerchantInfo();
                upMerchantInfo.setId(merchantInfo.getId());
                upMerchantInfo.setIdelFlag(1);
                merchantInfoDao.updateByIdSelective(upMerchantInfo);
                //删除商户基础详细信息表
                merchantDetailDao.deleteByPrimaryKey(id);
                //删除商户资质附件信息表
                MerchantAttach merchantAttach = new MerchantAttach();
                merchantAttach.setSmerchantId(merchantInfo.getId());
                merchantAttach.setIdelFlag(1);
                merchantAttachService.upBymerchantId(merchantAttach);
                //删除商户客户端配置表
                MerchantClientConf merchantClientConf = new MerchantClientConf();
                merchantClientConf.setId(id);
                merchantClientConf.setIdelFlag(1);
                merchantClientConfDao.updateByIdSelective(merchantClientConf);
                //删除商户配置信息
                MerchantConf merchantConf = new MerchantConf();
                merchantConf.setIdelFlag(1);
                merchantConf.setSmerchantId(merchantInfo.getId());
                merchantConfService.upBySmerchantId(merchantConf);
            }
        }
    }

    @Override
    public Page<MerchantInfoVo> selectPageByDomainWhere(Page<MerchantInfoVo> page, MerchantInfoDomain merchantInfoDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return merchantInfoDao.selectPageByDomainWhere(merchantInfoDomain);
    }

    /**
     * 更新商户权限
     *
     * @param merchantInfo 商户信息
     * @param purviewIds   权限集合
     */
    @Override
    public void updateMenuAuth(MerchantInfo merchantInfo, String[] purviewIds) throws Exception {
        MerchantPurview merchantPurview = null;
        //删除商户所有菜单权限
        merchantPurviewService.deleteByMerchantId(merchantInfo.getId());
        Map<String, MerchantPurview> map = new HashMap<String, MerchantPurview>();
        for (String id : purviewIds) {//新增商户菜单权限
            if (map.get(id.split("#//#")[0]) == null) {//判断父菜单是否添加  没有则添加父菜单
                merchantPurview = new MerchantPurview();
                merchantPurview.setSpurviewId(id.split("#//#")[0]);
                merchantPurview.setSmerchantId(merchantInfo.getId());
                merchantPurviewService.insert(merchantPurview);
                map.put(id.split("#//#")[0], merchantPurview);
            }
            merchantPurview = new MerchantPurview();
            merchantPurview.setSpurviewId(id.split("#//#")[1]);
            merchantPurview.setSmerchantId(merchantInfo.getId());
            merchantPurviewService.insert(merchantPurview);
        }
    }

    /**
     * 同步权限码到商户
     *
     * @param checkboxId 权限码集合
     */
    @Override
    public void syncPurview(String[] checkboxId) {
        //查询所有有效的商户
        MerchantInfo param = new MerchantInfo();
        param.setIstatus(20);
        List<MerchantInfo> merchantInfoList = this.selectByEntityWhere(param);
        List<MerchantPurview> merchantPurviewList = null;
        MerchantPurview paramMp = null;
        MerchantPurview merchantPurview = null;
        Purview purview = null;
        for (MerchantInfo merchant : merchantInfoList) {//循环商户列表
            for (String id : checkboxId) {//循环权限ID
                //判断权限是否存在商户  如果存在跳过
                paramMp = new MerchantPurview();
                paramMp.setSmerchantId(merchant.getId());
                paramMp.setSpurviewId(id);
                merchantPurviewList = merchantPurviewService.selectByEntityWhere(paramMp);
                if (null == merchantPurviewList || merchantPurviewList.size() <= 0) {
                    //判断权限码是否要添加到商户
                    if (!merchant.getSparentId().equals("0")) {
                        purview = purviewService.selectByPrimaryKey(id);
                        if (purview.getIisMerchantUsed().intValue() == 0) {//商户不可用
                            continue;
                        }
                    }
                    //不存在新增
                    merchantPurview = new MerchantPurview();
                    merchantPurview.setSpurviewId(id);
                    merchantPurview.setSmerchantId(merchant.getId());
                    merchantPurviewService.insert(merchantPurview);
                }
            }
        }
    }

    @Override
    public MerchantInfo selectOne(String id) {
        return merchantInfoDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean isExistName(String merchantInfoName) {
        if (merchantInfoDao.isNameExist(merchantInfoName) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户父Id查询用户跟ID及跟编号
     *
     * @param sparentId 父商户Id
     */
    @Override
    public Map selectSroot(String sparentId) {
        if (selectMax(sparentId, 1)) {
            Map<String, String> map = new HashMap<>();
            MerchantInfo parentMerchantInfo = merchantInfoDao.selectByPrimaryKey(sparentId);
            if ("0".equals(parentMerchantInfo.getSrootId())) {
                map.put("srootId", parentMerchantInfo.getId());
                map.put("srootCode", parentMerchantInfo.getScode());
            } else {
                MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(parentMerchantInfo.getSrootId());
                //如果为0则表示当前商户为二级商户
                if ("0".equals(merchantInfo.getSrootId())) {
                    map.put("srootId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                    map.put("srootCode", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
                } else {
                    map.put("srootId", parentMerchantInfo.getSrootId());
                    map.put("srootCode", parentMerchantInfo.getSrootCode());
                }
            }
            return map;
        } else {
            return null;
        }
    }

    /**
     * 递归判断添加商户的级别是否超过限制的级别
     *
     * @param sparentId 父商户Id
     */
    private boolean selectMax(String sparentId, int max) {
        MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(sparentId);
        if (!"0".equals(merchantInfo.getSrootId())) {
            max++;
            if (max > Integer.valueOf(SysParaUtil.getValue("merchantInfoMax"))) {
                throw new ServiceException("最多添加" + SysParaUtil.getValue("merchantInfoMax") + "级商户");
            }
            return selectMax(merchantInfo.getSparentId(), max);
        }
        return true;
    }

    /**
     * 新增商户
     *
     * @param
     */
    @Transactional
    @Override
    public ResponseVo insertNewMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantInfo merchantInfo, String merchantUserName, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String KFscontactMobile) {
        if (this.isExistName(merchantInfo.getSname())) {
            throw new ServiceException("商户名已经存在");
        }
        merchantInfo.setDexpireDate(DateUtils.addDays(merchantInfo.getDsignDate(), 10000));
        merchantInfo.setSname(merchantInfo.getSname());
        merchantInfo.setScode(CoreUtils.newCode(EntityTables.SH_MERCHANT_INFO));
        merchantInfo.setSparentId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());//父级商户ID
        merchantInfo.setSparentCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());//父级商户编号
        //查询其跟商户Id及编号
        Map<String, String> srootMap = this.selectSroot(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        merchantInfo.setIisParent(0);//是否父商户
        merchantInfo.setIstatus(BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode());//默认状态为已签约
        merchantInfo.setSrootId(srootMap.get("srootId"));//跟商户Id
        merchantInfo.setSrootCode(srootMap.get("srootCode"));//跟商户code
        merchantInfo.setTaddTime(DateUtils.getCurrentDateTime());
        merchantInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantInfo.setIdelFlag(0);
        //如果Bd方式为个人，添加对接Bd信息
        if (null != merchantInfo.getIdbWap() && 20 == merchantInfo.getIdbWap()) {
            Operator operator = operatorService.selectByPrimaryKey(merchantInfo.getSdbId());
            if (null == operator) {
                throw new ServiceException("对接dB不存在");
            }
            merchantInfo.setSdbContact(operator.getSmobile());
            merchantInfo.setSdbEmail(operator.getSmail());
        }
        this.insert(merchantInfo);
        //把当前商户是否父商户修改为:是
        MerchantInfo parentMerchantInfo = new MerchantInfo();
        parentMerchantInfo.setId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        parentMerchantInfo.setIisParent(1);
        merchantInfoDao.updateByIdSelective(parentMerchantInfo);
        //如果为企业，增加其基础详细表
        if (null != merchantInfo.getItype() && BizTypeDefinitionEnum.MerchantType.ENTERPRISE.getCode() == merchantInfo.getItype()) {
            merchantDetail.setId(merchantInfo.getId());
            merchantDetail.setTaddTime(DateUtils.getCurrentDateTime());
            merchantDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantDetail.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantDetail.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantDetailService.insertAll(merchantDetail);
        }
        //添加商户客户端配置表
        if (null != loginLogofile && loginLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            url = uploadHome(loginLogofile,"merchantLoginLogo");
            merchantClientConf.setSloginLogo(url);
        }
        if (null != merLogofile && merLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            url = uploadHome(merLogofile,"merchantLogo");
            merchantClientConf.setSlogo(url);
        }

        merchantClientConf.setIisConfWechatGzh(0);
        merchantClientConf.setIisConfAlipayShh(0);
        merchantClientConf.setIisConfAlipay(0);
        merchantClientConf.setIisConfWechat(0);
        merchantClientConf.setId(merchantInfo.getId());
        merchantClientConf.setScode(merchantInfo.getScode());
        merchantClientConf.setTaddTime(DateUtils.getCurrentDateTime());
        merchantClientConf.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantClientConf.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantClientConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantClientConf.setIdelFlag(0);
        merchantClientConf.setScontactMobile(KFscontactMobile);//客服电话
        merchantClientConfService.insertAll(merchantClientConf);
        //添加平台数据汇总表
        SummarizationDataPlf summarizationDataPlf1 = new SummarizationDataPlf();
        summarizationDataPlf1.setSmerchantId(merchantInfo.getId());
        summarizationDataPlf1.setSmerchantCode(merchantInfo.getScode());
        summarizationDataPlf1.setForderAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setForderRefundAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setIrefundNum(0);
        summarizationDataPlf1.setIrefundAuditSuccessNum(0);
        summarizationDataPlf1.setIabnormalNum(0);
        summarizationDataPlf1.setIabnormalDealwithNum(0);
        summarizationDataPlf1.setIabnormalIgnoreNum(0);
        summarizationDataPlf1.setIabnormalChargebackNum(0);
        summarizationDataPlf1.setIorderNum(0);
        summarizationDataPlf1.setIorderManNum(0);
        summarizationDataPlf1.setIregisteNum(0);
        summarizationDataPlf1.setIdeviceNum(0);
        summarizationDataPlf1.setIvisitorsNum(0);
        summarizationDataPlf1.setIreplenishmentNum(0);
        summarizationDataPlf1.setIrefundAuditFailNum(0);
        summarizationDataPlf1.setIrefundAuditSuccessNum(0);
        summarizationDataPlf1.setFapplyRefundAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setForderSuccessAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setForderFailAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setTaddTime(DateUtils.getCurrentDateTime());

        summarizationDataPlf1.setFdiscountAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFfirstDiscountAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFcouponDeductionAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFpointDiscountAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFpromotionDiscountAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFactualPayAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setFotherDiscountAmount(BigDecimal.ZERO);
        summarizationDataPlf1.setImemberNum(0);
        summarizationDataPlf1.setInotReplenishmentNum(0);
        summarizationDataPlf1.setIrefundAuditNum(0);
        summarizationDataPlf1.setDsummerEndTime(DateUtils.addDays(DateUtils.getDayEnd(new Date()), -1));
        summarizationDataPlfService.insert(summarizationDataPlf1);
        //商户添加成功后同步数据到系统用户中，且默认为超级管理员
        operatorService.syncData(merchantInfo, merchantUserName);
        //添加商户默认为状态为已签约,将商户加入缓存
        try {
            //已签约才存入缓存
            String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
            iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(merchantInfo));
            catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
            iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, merchantClientConf);
            catcheKey = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode();
            iCached.put(catcheKey, JSON.toJSONString(summarizationDataPlf1));
        } catch (Exception e) {
            log.error("添加或更新域名缓存失败：{}", e);
            throw new ServiceException("更新商户缓存失败");
        }

        //操作日志
        String logText = MessageFormat.format("新增商户，Id{0}", merchantInfo.getId());
        LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        return ResponseVo.getSuccessResponse();
    }


    public String uploadHome(MultipartFile file, String pathStr) {
        if (file == null) {
            throw new ServiceException("没有找到上传文件");
        }
        //文件大小限制
      /*  if (111 < file.getSize()) {

        }*/
        //文件原名
        String filName = file.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";
        try {
            String serverPath = pathStr + "/" + DateUtils.dateParseShortString(new Date()) + "/";
            String fileFileName = file.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return null;
    }

    /**
     * 修改商户
     *
     * @param
     */
    @Transactional
    @Override
    public ResponseVo upMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantInfo merchantInfo, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String merchantDetailId, String KFscontactMobile) {
        MerchantInfo merchantInfo1 = this.selectByPrimaryKey(merchantInfo.getId());
        if (!merchantInfo.getSname().equals(merchantInfo1.getSname())) {
            if (this.isExistName(merchantInfo.getSname())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户名已经存在");
            }
        }
        merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        //如果Bd方式为个人，添加对接Bd信息
        if (null != merchantInfo.getIdbWap() && 20 == merchantInfo.getIdbWap()) {
            Operator operator = operatorService.selectByPrimaryKey(merchantInfo.getSdbId());
            if (null == operator) {
                throw new ServiceException("对接dB不存在");
            }
            merchantInfo.setSdbContact(operator.getSmobile());
            merchantInfo.setSdbEmail(operator.getSmail());
            //如果Bd方式为公司,删除对接Bd信息
        } else if (null != merchantInfo.getIdbWap() && 10 == merchantInfo.getIdbWap()) {
            merchantInfo.setSdbId("");
            merchantInfo.setSdbEmail("");
            merchantInfo.setSdbContact("");
            merchantInfo.setSdbName("");
        }
        this.updateById(merchantInfo);
        //如果为企业，增加其基础详细表
        if (null != merchantInfo.getItype() && BizTypeDefinitionEnum.MerchantType.ENTERPRISE.getCode() == merchantInfo.getItype()) {
            merchantDetail.setTaddTime(DateUtils.getCurrentDateTime());
            merchantDetail.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantDetail.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantDetail.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            //如果企业详细表为空则新增,不为空则修改
            if (StringUtils.isBlank(merchantDetailId)) {
                merchantDetailService.insertAll(merchantDetail);
            } else {
                merchantDetailService.updateBySelective(merchantDetail);
            }
            //如果为个人则删除企业详细信息表
        } else if (null != merchantInfo.getItype() && BizTypeDefinitionEnum.MerchantType.PERSONAL.getCode() == merchantInfo.getItype()) {
            merchantDetailDao.deleteByPrimaryKey(merchantInfo.getId());
        }
        //修改商户客户端配置表
        if (null != loginLogofile && loginLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            try {
                url = uploadHome(loginLogofile,"merchantLoginLogo");
                merchantClientConf.setSloginLogo(url);
            } catch (ServiceException e) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
            }
        }
        if (null != merLogofile && merLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            try {
                url = uploadHome(merLogofile,"merchantLogo");
                merchantClientConf.setSlogo(url);
            } catch (ServiceException e) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
            }
        }
        merchantClientConf.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantClientConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantClientConf.setScontactMobile(KFscontactMobile);//客服电话
        merchantClientConfService.updateBySelective(merchantClientConf);
        //更新已签约商户缓存
        if (BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode() == merchantInfo.getIstatus()) {
            try {
                MerchantConf merchantConf = new MerchantConf();
                merchantConf.setSmerchantId(merchantInfo.getId());
                merchantConf.setIdelFlag(0);
                merchantConf.setItype(10);
                MerchantConf WCmerchantConf = merchantConfService.selectByIdType(merchantConf);
                merchantConf.setSmerchantId(merchantInfo.getId());
                merchantConf.setIdelFlag(0);
                merchantConf.setItype(20);
                MerchantConf APmerchantConf = merchantConfService.selectByIdType(merchantConf);
                MerchantClientConf merchantClientConf1 = merchantClientConfService.selectByPrimaryKey(merchantInfo.getId());
                //已签约才存入缓存
                String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                merchantInfo1 = this.selectByPrimaryKey(merchantInfo.getId());
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(merchantInfo1));
                catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, merchantClientConf1);
                if (null != WCmerchantConf) {
                    catcheKey = RedisConst.SH_WECHAT_CONFIG + merchantInfo.getScode();
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(WCmerchantConf));
                }
                if (null != APmerchantConf) {
                    catcheKey = RedisConst.SH_ALIPAY_CONFIG + merchantInfo.getScode();
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(APmerchantConf));
                }
              /*  catcheKey = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode();
                iCached.put(catcheKey, JSON.toJSONString(summarizationDataPlf));*/
            } catch (Exception e) {
                log.error("编辑商户更新缓存失败：{}", e);
                throw new ServiceException("更新商户缓存失败");
            }
        }
        //操作日志
        String logText = MessageFormat.format("编辑商户，编号{0}", merchantInfo.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    @Override
    public void updateById(MerchantInfo merchantInfo) {
        merchantInfoDao.updateById(merchantInfo);
    }

    /**
     * 查询商户列表更新缓存
     *
     * @param merchantInfoDomain
     */
    @Override
    public List<MerchantInfo> selectByDomainWhere(MerchantInfoDomain merchantInfoDomain) {
        return merchantInfoDao.selectByDomainWhere(merchantInfoDomain);
    }

    /**
     * 更新商户缓存
     */
    @Override
    public void initCached() {
        try {
            //更新商户域名缓存
            DomainConf params = new DomainConf();
            params.setIdelFlag(0);
            params.setIstatus(20);
            List<DomainConf> domainConfList = domainConfService.selectByEntityWhere(params);
            if (null != domainConfList && domainConfList.size() > 0) {
                for (DomainConf domainConf : domainConfList) {
                    if (StringUtils.isNotBlank(domainConf.getSdomainUrl())) {
                        if (domainConf.getIstatus().intValue() == BizTypeDefinitionEnum.DomainStatus.AUDIT_SUCCESS.getCode()) {
                            iCached.remove(domainConf.getSdomainUrl());//remove 缓存
                            //审核通过才存入缓存
                            iCached.put(domainConf.getSdomainUrl(), domainConf);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("缓存更新异常:{}", e);
        }
    }

    /**
     * 获取商户信息
     *
     * @param merchantCode 商户编号
     * @return
     */
    @Override
    public MerchantInfo selectByCode(String merchantCode) {
        return merchantInfoDao.selectByCode(merchantCode);
    }
}