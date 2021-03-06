package com.cloud.cang.mgr.sys.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.utils.IdFactory;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.mgr.common.EnumDefinition;
import com.cloud.cang.mgr.common.utils.IdGenerator;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sh.service.MerchantClientConfService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sh.web.MerchantAttachController;
import com.cloud.cang.mgr.sys.dao.OperatorroleDao;
import com.cloud.cang.mgr.sys.domain.OperatorDomain;
import com.cloud.cang.mgr.sys.domain.UserPurviewDomain;
import com.cloud.cang.mgr.sys.vo.OperatorVo;
import com.cloud.cang.mgr.sys.vo.UserDetail;
import com.cloud.cang.mgr.sys.vo.UserPurviewVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operatorrole;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.service.SecUserService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Op;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sys.dao.OperatorDao;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.mgr.sys.service.OperatorService;

@Service
public class OperatorServiceImpl extends GenericServiceImpl<Operator, String> implements
        OperatorService, SecUserService {

    @Autowired
    private OperatorDao operatorDao;
    @Autowired
    private OperatorroleDao operatorroleDao;
    @Autowired
    private MerchantClientConfService merchantClientConfService;

    @Autowired
    private MerchantInfoDao merchantInfoDao;
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Override
    public GenericDao<Operator, String> getDao() {
        return operatorDao;
    }

    private static final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);


    @Override
    public void saveOperatorRole(String operatorId, String[] RoleIds) {
        // ???????????????????????????
        operatorroleDao.deleteByOperatorId(operatorId);
        if (StringUtils.isNotBlank(operatorId)) {
            for (String roleId : RoleIds) {
                Operatorrole operatorRole = new Operatorrole();
                operatorRole.setSoperatorId(operatorId);
                operatorRole.setSroleId(roleId);
                operatorroleDao.insert(operatorRole);
            }
        }
    }

    @Override
    public void delete(String[] ids) {
        if (ids != null && ids.length > 0) {
            Operator upoperator = null;
            Operator operator = null;
            for (String id : ids) {
                operator = this.selectByIdAndMerchantId(id, SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                if (null == operator) {
                    throw new ServiceException("????????????????????????????????????????????????");
                }
                if (null != operator.getBisFreeze() && 1 == operator.getBisFreeze()) {
                    throw new ServiceException("??????????????????,???????????????");
                }
                // ??????????????????
                upoperator = new Operator();
                upoperator.setBisDelete(1);
                upoperator.setId(id);
                operatorDao.updateByIdSelective(upoperator);
            }
        }
    }

    @Override
    public void updatePassword(String operatorId, String password) {
        Operator operator = new Operator();
        operator.setSpassword(MD5.encode(password));
        operator.setId(operatorId);
        operatorDao.updateByIdSelective(operator);
    }

    @Override
    public boolean isUsernameExist(String username) {
        Operator operator = new Operator();
        operator.setSuserName(username);
        operator.setBisDelete(0);
        /*operator.setBisFreeze(1);*/
        List<Operator> operatorList = operatorDao.selectByEntityWhere(operator);
        return operatorList != null && operatorList.size() > 0;
    }

    /**
     * ??????????????????????????????
     *
     * @param mobile ?????????
     * @return
     */
    @Override
    public boolean isMobileExist(String mobile) {
        Operator operator = new Operator();
        operator.setSmobile(mobile);
        operator.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
        operator.setBisDelete(0);
        List<Operator> operatorList = operatorDao.selectByEntityWhere(operator);
        return operatorList != null && operatorList.size() > 0;
    }

    @Override
    public Page<OperatorDomain> selectPageByDomainWhere(Page<OperatorDomain> page, OperatorVo operatorVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return operatorDao.selectByDomainWhere(operatorVo);
    }

    @Override
    public Operator selectByUsername(String username) {
        //return operatorDao.selectByUserName(username);
        return this.selectByUsernameAndMerchant(username, (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE));
    }

    @Override
    public void updateByIdSelectiveBIS_FREEZE(Operator operator) {
        operatorDao.updateByIdSelectiveBIS_FREEZE(operator);
    }

    /***
     * ????????????????????? ?????????sql
     * @param oper ????????????
     * @param type ???????????? 10=???????????? 20 ???????????? 30 ???????????? 40 ???????????? 50 ????????????
     *             60 ???????????? 70 ????????????  110 ????????????  120 ???????????? 130 ???????????? 140 ????????????/?????????  150 ??????????????????
     *             160 ??????????????????/??????????????????/????????????/????????????????????????/??????????????????????????????  170 ??????????????????
     *             180 ????????????????????????  190 ????????????  200 ????????????????????????  210 ??????????????????  220 ???????????????
     *
     * @return
     */
    @Override
    public String generatePurviewSql(Operator oper, Integer type) {
        String sql = "";
        if (oper == null) {
            return sql;
        }
        MerchantInfo merchant = merchantInfoDao.selectByPrimaryKey(oper.getSmerchantId());
        if (type == 10) {//????????????
            if (oper.getImerType().intValue() == 0) {//????????????
                if (merchant.getSparentId().equals("0")) {//??????????????????
                    sql = " ((shi.id='" + merchant.getId() + "') or (shi.sparent_id='" + merchant.getId() + "') or (shi.sroot_id in (select smi.id from sh_merchant_info smi where smi.sparent_id='" + merchant.getId() + "')))";
                } else {//??????????????????
                    sql = " ((shi.id='" + merchant.getId() + "') or (shi.sparent_id='" + merchant.getId() + "') or (shi.sroot_id='" + merchant.getId() + "'))";
                }
            } else if (oper.getImerType().intValue() == 1) {//??????bd????????????
                sql = " (shi.sdb_id='" + oper.getId() + "')";
            } else if (oper.getImerType().intValue() == 4) {//??????bd???????????? ??? ??????
                sql = " ((shi.sdb_id='" + oper.getId() + "') or (shi.sparent_id in (select smi.id from sh_merchant_info smi where smi.sdb_id='" + oper.getId() + "')) or (shi.sroot_id in (select smi.id from sh_merchant_info smi where smi.sdb_id='" + oper.getId() + "')))";
            } else if (oper.getImerType().intValue() == 2) {//????????????
                sql = " shi.id='" + merchant.getId() + "'";
            } else if (oper.getImerType().intValue() == 3) {//????????????
                String arr[] = null;
                StringBuffer smerList = new StringBuffer();
                if (StringUtil.isNotBlank(oper.getSmerList())) {
                    arr = oper.getSmerList().split(",");
                    for (String str : arr) {
                        if (StringUtil.isNotBlank(str)) {
                            smerList.append("'" + str + "',");
                        }
                    }
                }
                if (smerList.toString().length() > 0) {
                    sql = " shi.id in (" + smerList.toString().substring(0, (smerList.toString().length() - 1)) + ")";
                } else {
                    sql = " 1 = 2";
                }
            }
        } else if (type == 20 || type == 30 || type == 40 || type == 50 || type == 60 || type == 70 || type == 80 || type == 90
                || type == 100 || type == 110 || type == 120 || type == 130 || type == 140 || type == 150 || type == 160 || type == 170
                || type == 180 || type == 190 || type == 200 || type == 210 || type == 220) {
            //20 ???????????? 30 ???????????? 40 ???????????? 50 ???????????? 60 ???????????? 70 ????????????/???????????????????????????
            // 80 ??????????????????/???????????? 90 ????????????/????????????/???????????? 100???????????? 110 ????????????
            // 120 ???????????? 130 ???????????? 140 ????????????/?????????  150 ?????????????????? 170 ??????????????????
            // 180 ????????????????????????/??????????????????/??????????????????/??????????????????????????????/????????????????????????
            // 190???????????? 200????????????????????????  210 ?????????????????? 220 ???????????????
            if (oper.getImerType().intValue() == 0) {//????????????
                if (merchant.getSparentId().equals("0")) {//??????????????????
                    sql = " 1=1";
                } else {//?????????????????? ?????????
                    sql = " A.smerchant_id in (select smi.id from sh_merchant_info smi where (smi.id ='" + merchant.getId() + "' or smi.sroot_id='" + merchant.getId() + "'))";
                }
            } else if (oper.getImerType().intValue() == 1) {//??????bd????????????
                sql = " A.smerchant_id in (select smi.id from sh_merchant_info smi where smi.sdb_id='" + oper.getId() + "')";
            } else if (oper.getImerType().intValue() == 4) {//??????bd???????????? ??? ??????
                sql = " ((A.smerchant_id in (select smi.id from sh_merchant_info smi where smi.sdb_id='" + oper.getId() + "')) or (A.smerchant_id in (select sminfo.id from sh_merchant_info sminfo where sminfo.sroot_id in (select smi.id from sh_merchant_info smi where smi.sdb_id='" + oper.getId() + "'))))";
            } else if (oper.getImerType().intValue() == 2) {//????????????
                sql = " A.smerchant_id='" + merchant.getId() + "'";
            } else if (oper.getImerType().intValue() == 3) {//????????????
                String arr[] = null;
                StringBuffer smerList = new StringBuffer();
                if (StringUtil.isNotBlank(oper.getSmerList())) {
                    arr = oper.getSmerList().split(",");
                    for (String str : arr) {
                        smerList.append("'" + str + "',");
                    }
                }
                if (smerList.toString().length() > 0) {
                    sql = " A.smerchant_id in (" + smerList.toString().substring(0, (smerList.toString().length() - 1)) + ")";
                } else {
                    sql = " 1 = 2";
                }
            }

            if (oper.getIdevType().intValue() != 0) {
                String arr[] = null;
                StringBuffer actSql = new StringBuffer();
                StringBuffer sdevList = new StringBuffer();
                if (StringUtil.isNotBlank(oper.getSgroupDecList())) {
                    arr = oper.getSgroupDecList().split(",");
                    if (210 == type) {
                        for (String str : arr) {
                            if (StringUtil.isNotBlank(str)) {
                                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(str);
                                if (null != deviceInfo) {
                                    sdevList.append("'" + deviceInfo.getScode() + "',");
                                }
                            }
                        }
                    } else {
                        for (String str : arr) {
                            if (StringUtil.isNotBlank(str)) {
                                sdevList.append("'" + str + "',");
                                actSql.append("find_in_set('" + str + "',aur.SDEVICE_ID) and ");
                            }
                        }
                    }
                }
                if (sdevList.toString().length() > 0) {
                    if (type == 20) {
                        sql += " and A.id in (select aur.SAC_ID from AC_USE_RANGE aur where (aur.IRANGE_TYPE in (10,30) and aur.sac_id in (select acac.id from ac_activity_conf acac where acac.smerchant_id = '" + merchant.getId() + "')) or (" + actSql + " 1=1))";
                    } else if (type == 50 || type == 130 || type == 200) {
                        sql += " and A.id in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + ")";
                    } else if (type == 60) {
                        sql += " and A.smerchant_id in (select sdinfo.smerchant_id from sb_device_info sdinfo where sdinfo.id in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + "))";
                    } else if (type == 70) {
                        sql += " and A.sreg_device_code in (select sdinfo.scode from sb_device_info sdinfo where sdinfo.id in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + "))";
                    } else if (type == 80 || type == 90 || type == 140 || type == 170 || type == 180 || type == 190) {
                        sql += " and A.sdevice_id in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + ")";
                    } else if (type == 150) {
                        sql += " and SGR.SDEVICE_ID in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + ")";
                    } else if (type == 210) {
                        sql += " and (A.SUSE_LIMIT_DEVICE = '' or A.SUSE_LIMIT_DEVICE  in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + "))";
                    } else if (type == 220) {
                        sql += " and C.sreg_device_code in (select sdinfo.scode from sb_device_info sdinfo where sdinfo.id in (" + sdevList.toString().substring(0, (sdevList.toString().length() - 1)) + "))";
                    }
                } else {

                    sql += " and 1 = 2";
                }
            }
        }
        return sql;
    }

    @Override
    public AuthorizationVO getUserByUserName(CaptchaUsernamePasswordToken loginObj) {
        String smerchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        Operator po = this.operatorDao.selectByUserName(loginObj.getUsername(), smerchantId);
        if (po == null)
            return null;
        AuthorizationVO vo = new AuthorizationVO();
        if (po.getBisFreeze().equals(EnumDefinition.SysUserStatus.FREEZE.getCode() + "")) {
            vo.setFreeze(true);
        }
        vo.setId(po.getId());
        vo.setUserName(po.getSuserName());
        vo.setPassword(po.getSpassword());
        vo.setBisdAdmin(po.getBisAdmin());
        if (po.getDlastLoginTime() != null) {
            vo.setLastLoginTime(DateUtils.dateParseShortString(po.getDlastLoginTime()));
        } else {
            vo.setLastLoginTime(DateUtils.dateParseString(DateUtils.getCurrentDateTime()));
        }
        //????????????
        vo.setSmerchantId(po.getSmerchantId());
        vo.setSmerchantCode(po.getSmerchantCode());
        MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(po.getSmerchantId());
        vo.setSmerchantName(merchantInfo.getSname());

        if (StringUtil.isNotBlank(po.getSmobile())) {
            vo.setMobile(po.getSmobile());
        }
        if (StringUtil.isNotBlank(po.getSmail())) {
            vo.setEmail(po.getSmail());
        }
        //????????????
        MerchantClientConf conf = merchantClientConfService.selectByPrimaryKey(po.getSmerchantId());
        if (conf != null) {
            if (StringUtil.isNotBlank(conf.getSlogo())) {
                vo.setSmerchantLogo(conf.getSlogo());
            }
            if (StringUtil.isNotBlank(conf.getSloginLogo())) {
                vo.setSmerchantLoginLogo(conf.getSloginLogo());
            }
            if (StringUtil.isNotBlank(conf.getSshortName())) {
                vo.setSshortName(conf.getSshortName());
            }
        }
        return vo;
    }

    @Override
    public AuthorizationVO getUserDetail(String userId) {
        UserDetail dtl = new UserDetail();
        Operator op = this.operatorDao.selectByPrimaryKey(userId);
        dtl.setId(op.getId());
        dtl.setCode(op.getSoperatorNo());
        dtl.setUserName(op.getSuserName());
        dtl.setRealName(op.getSrealName());
        dtl.setBisdAdmin(op.getBisAdmin());
        if (op.getDlastLoginTime() != null) {
            dtl.setLastLoginTime(DateUtils.dateToString(op.getDlastLoginTime(), "yyyy-MM-dd HH:mm"));
        } else {
            dtl.setLastLoginTime(DateUtils.dateToString(DateUtils.getCurrentDateTime(), "yyyy-MM-dd HH:mm"));
        }
        dtl.setMobile(op.getSmobile());
        if (StringUtil.isNotBlank(op.getSmobile())) {
            dtl.setPhone(op.getSmobile());
        }
        if (StringUtil.isNotBlank(op.getSmail())) {
            dtl.setMai(op.getSmail());
        }
        //????????????
        dtl.setSmerchantId(op.getSmerchantId());
        dtl.setSmerchantCode(op.getSmerchantCode());
        MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(op.getSmerchantId());
        dtl.setSmerchantName(merchantInfo.getSname());

        MerchantClientConf conf = merchantClientConfService.selectByPrimaryKey(op.getSmerchantId());
        if (conf != null) {
            if (StringUtil.isNotBlank(conf.getSlogo())) {
                dtl.setSmerchantLogo(conf.getSlogo());
            }
            if (StringUtil.isNotBlank(conf.getSloginLogo())) {
                dtl.setSmerchantLoginLogo(conf.getSloginLogo());
            }
            if (StringUtil.isNotBlank(conf.getSshortName())) {
                dtl.setSshortName(conf.getSshortName());
            }
        }
        return dtl;
    }

    @Override
    public void loginSuccess(String userId, String host) {
        //??????????????????
        Operator operator = operatorDao.selectByPrimaryKey(userId);
        String logText = MessageFormat.format("{0}???{1}????????????????????????,??????IP:{2}", operator.getSrealName(), new Date(), host);
        LogUtil.addOPLog(LogUtil.AC_LOGIN, logText, null, host);
        // ???????????????????????????
        this.operatorDao.updateForLoginSuccess(userId);
    }

    @Override
    public void loginError(String userName, String host) {

    }

    @Override
    public void logout(String userId) {

    }

    /**
     * ????????????
     *
     * @param sid ??????Id
     */
    @Override
    public void resetPassword(String sid) throws Exception {
        Operator operator = operatorDao.selectByPrimaryKey(sid);
        if (null == operator) {
            throw new ServiceException("??????????????????????????????????????????");
        }
        String password = IdGenerator.randomUUID(6);
        Operator operatorl = new Operator();
        operatorl.setId(sid);
        operatorl.setSpassword(MD5.encode(password));
        operatorDao.updateByIdSelective(operatorl);
        //?????????????????????????????????
        MessageDto messageDto = new MessageDto();
        messageDto.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        messageDto.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
        // ????????????
        messageDto.setTemplateType("reset_password");
        //????????????
        //??????
        Map<String, Object> contentParam = new HashMap<String, Object>();
        contentParam.put("password", password);
        messageDto.setContentParam(contentParam);
        messageDto.setMobile(operator.getSmobile());
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//????????????
        invoke.setRequestObj(messageDto); //post ??????
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
    }

    @Override
    public Page<UserPurviewVo> selectUserPurview(Page<UserPurviewVo> page, UserPurviewDomain userPurviewDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return operatorDao.selectOpertorPurview(userPurviewDomain);
    }

    /**
     * ??????????????????????????????
     *
     * @param merchantUserName ????????????????????????
     * @return
     */
    @Override
    public void syncData(MerchantInfo merchantInfo, String merchantUserName) {

        Operator operator = new Operator();
        if (this.isUsernameExist(merchantUserName)) {
            throw new ServiceException("????????????????????????????????????");
        }
        try {
            operator.setSuserName(merchantUserName);
            operator.setSmerchantId(merchantInfo.getId());
            operator.setSoperatorNo(CoreUtils.newCode(EntityTables.SYS_OPERATOR));
            operator.setSmerchantCode(merchantInfo.getScode());
            operator.setSpassword(MD5.encode("123abc"));
            operator.setSrealName(merchantInfo.getScontactName());
            operator.setSmobile(merchantInfo.getScontactMobile());
            operator.setSmail(merchantInfo.getScontactEmail());
            operator.setBisFreeze(1);
            operator.setBisAdmin(1);
            operator.setBisDelete(0);
            operator.setIisBd(0);
            operator.setIdevType(0);
            operator.setImerType(0);
            operator.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            operator.setDaddDate(DateUtils.getCurrentDateTime());
            operator.setDmodifyDate(DateUtils.getCurrentDateTime());
            operator.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            this.insert(operator);
        } catch (Exception e) {
            log.error("???????????????????????????????????????{}", e);
            throw new ServiceException("????????????????????????");
        }
    }

    /**
     * ??????Id ??????????????????
     *
     * @param operator
     * @return
     */
    @Override
    public void upByIdSelective(Operator operator) {
        operatorDao.upByIdSelective(operator);
    }

    /***
     * ??????????????????
     * @param username ?????????
     * @param merchantCode ????????????
     * @return
     */
    @Override
    public Operator selectByUsernameAndMerchant(String username, String merchantCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("suserName", username);
        map.put("merchantCode", merchantCode);
        return operatorDao.selectByUsernameAndMerchant(map);
    }

    /***
     * ??????ID?????????ID??????????????????
     * @param id
     * @param merchantId ??????ID
     * @return
     */
    public Operator selectByIdAndMerchantId(String id, String merchantId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("merchantId", merchantId);
        return operatorDao.selectByIdAndMerchantId(map);
    }
}