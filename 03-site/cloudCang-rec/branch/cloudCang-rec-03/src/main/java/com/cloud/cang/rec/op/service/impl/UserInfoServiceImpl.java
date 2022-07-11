package com.cloud.cang.rec.op.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.model.lm.LabelBatchCommodity;
import com.cloud.cang.model.lm.PackingRecord;
import com.cloud.cang.model.lm.PackingRecordDetail;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.service.InterfaceAccountService;
import com.cloud.cang.rec.op.service.InterfaceInfoService;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.cloud.cang.rec.sys.util.DateUtil;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.MD5;
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

import com.cloud.cang.rec.op.dao.UserInfoDao;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.op.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl extends GenericServiceImpl<UserInfo, String> implements
        UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    InterfaceInfoService interfaceInfoService;
    @Autowired
    UserInterfaceAuthService userInterfaceAuthService;
    @Autowired
    InterfaceAccountService interfaceAccountService;


    @Override
    public GenericDao<UserInfo, String> getDao() {
        return userInfoDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param userInfoDomain
     * @return
     */
    @Override
    public Page<UserInfoVo> selectPageByDomainWhere(Page<UserInfoVo> page, UserInfoDomain userInfoDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return userInfoDao.selectPageByDomainWhere(userInfoDomain);
    }

    /**
     * 删除开放平台用户
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(id);
                userInfo.setIdelFlag(1);
                userInfoDao.updateByIdSelective(userInfo);
            }
        }
    }

    /**
     * 根据Id查看Vo
     *
     * @param id
     * @return
     */
    @Override
    public UserInfoVo selectVoById(String id) {
        return userInfoDao.selectVoById(id);
    }

    /**
     * 保存平台用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        userInfo.setSloginPwd(MD5.encode(userInfo.getSloginPwd()));
        userInfo.setIdelFlag(0);
        userInfo.setTregisterTime(DateUtils.getCurrentDateTime());
        userInfo.setIstatus(1);
        userInfo.setTaddTime(DateUtils.getCurrentDateTime());
        userInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        userInfo.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userInfo.setScode(CoreUtils.newCode(EntityTables.OP_USER_INFO));
        userInfoDao.insert(userInfo);
        return userInfo;
    }

    /**
     * 编辑平台用户
     *
     * @param userInfo
     */
    @Override
    public UserInfo upUserInfo(UserInfo userInfo) {
        userInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        userInfo.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        userInfoDao.updateByIdSelective(userInfo);
        return userInfo;

    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    @Override
    public void updatePassword(String userId, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setSloginPwd(MD5.encode(password));
        userInfo.setId(userId);
        userInfoDao.updateByIdSelective(userInfo);
    }

    /**
     * 重置密码
     *
     * @param sid 用户ID
     */
    @Override
    public void resetPassword(String sid) {
        //UserInfo userInfo = userInfoDao.selectByPrimaryKey(sid);
        String password = IdGenerator.randomUUID(6);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(sid);
        userInfo1.setSloginPwd(MD5.encode(password));
        userInfoDao.updateByIdSelective(userInfo1);
        //重置密码后发送短信通知
       /* MessageDto messageDto = new MessageDto();
        messageDto.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        messageDto.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
        // 模板类型
        messageDto.setTemplateType("reset_password");
        //发送短信
        //内容
        Map<String, Object> contentParam = new HashMap<String, Object>();
        contentParam.put("password", password);
        messageDto.setContentParam(contentParam);
        messageDto.setMobile(userInfo.getSmobile());
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);//服务名称
        invoke.setRequestObj(messageDto); //post 参数
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();*/
    }

    /**
     * 查询用户接口权限
     */
    @Override
    public List<UserInterfaceAuthVo> selectUserInterfaceAuthVoList(String sid) {
        return userInfoDao.selectUserInterfaceAuthVoList(sid);
    }

    /**
     * 保存接口权限
     * 开通接口权限且该接口为收费接口时增加接口账户记录
     *
     * @param pkIds
     * @param userInfo
     */
    @Override
    @Transactional
    public void saveInterfaceAuth(String[] pkIds, UserInfo userInfo) {
        if (null != pkIds && pkIds.length > 0) {
            for (String id : pkIds) {//新增接口权限
                String str[] = id.split("#");
                InterfaceInfo interfaceInfo = interfaceInfoService.selectByPrimaryKey(str[2]);
                UserInterfaceAuth userInterfaceAuth = userInterfaceAuthService.selectByAppIdAndInterfaceCodeAndUserId(str[0], interfaceInfo.getScode(), userInfo.getId());
                if (null == userInterfaceAuth) {
                    userInterfaceAuth = new UserInterfaceAuth();
                    userInterfaceAuth.setScode(CoreUtils.newCode(EntityTables.OP_USER_INTERFACE_AUTH));
                    userInterfaceAuth.setSuserId(userInfo.getId());
                    userInterfaceAuth.setSuserCode(userInfo.getScode());
                    userInterfaceAuth.setSappId(str[0]);
                    userInterfaceAuth.setSappCode(str[1]);
                    userInterfaceAuth.setSinterfaceCode(interfaceInfo.getScode());
                    userInterfaceAuth.setIauthStatus(10);
                    userInterfaceAuth.setIopenTime(DateUtils.getCurrentDateTime());
                    userInterfaceAuth.setAddTime(DateUtils.getCurrentDateTime());
                    userInterfaceAuth.setUpdateTime(DateUtils.getCurrentDateTime());
                    userInterfaceAuth.setIdelFlag(0);
                    userInterfaceAuthService.insert(userInterfaceAuth);
                    //开通接口权限且接口为收费接口时增加账户
                    if (30 == interfaceInfo.getIpayWay()) {
                        InterfaceAccount interfaceAccount = new InterfaceAccount();
                        interfaceAccount.setScode(CoreUtils.newCode(EntityTables.OP_INTERFACE_ACCOUNT));
                        interfaceAccount.setIaccountType(interfaceInfo.getIpayType());
                        if (30 == interfaceInfo.getIpayType() || 40 == interfaceInfo.getIpayType() || 50 == interfaceInfo.getIpayType()) {
                            //固定日期
                            interfaceAccount.setIvalidityType(10);
                        }
                        if (30 != interfaceInfo.getIpayType()) {
                            interfaceAccount.setIfreezeNum(0);
                            interfaceAccount.setIdeductionNum(0);
                            //默认余额次数
                            String itransferNum = BizParaUtil.get("interface_account_default_itransfer_num");
                            interfaceAccount.setFbalance(Integer.valueOf(itransferNum));
                            interfaceAccount.setItransferNum(0);
                        }
                        interfaceAccount.setSuserId(userInfo.getId());
                        interfaceAccount.setSuserCode(userInfo.getScode());
                        interfaceAccount.setSinterfaceId(interfaceInfo.getId());
                        interfaceAccount.setSinterfaceCode(interfaceInfo.getScode());
                      /*  interfaceAccount.setFbalance(0);
                        interfaceAccount.setIfreezeNum(0);
                        interfaceAccount.setIdeductionNum(0);
                        //默认调用次数
                        //String itransferNum = BizParaUtil.get("interface_account_default_itransfer_num");
                        interfaceAccount.setItransferNum(0);*/
                        interfaceAccount.setTopenTime(DateUtils.getCurrentDateTime());
                        interfaceAccount.setIstatus(10);
                        interfaceAccount.setIdelFlag(0);
                        interfaceAccount.setAddTime(DateUtils.getCurrentDateTime());
                        interfaceAccount.setUpdateTime(DateUtils.getCurrentDateTime());
                        interfaceAccount.setIisUnlimitedNumber(1);
                        interfaceAccountService.insert(interfaceAccount);
                    }
                } else {
                    UserInterfaceAuth temp = new UserInterfaceAuth();
                    temp.setIauthStatus(10);
                    temp.setId(userInterfaceAuth.getId());
                    userInterfaceAuthService.updateBySelective(temp);
                    //把接口账户打开
                    interfaceAccountService.closeOrOpenByUserIdAndInterfaceCode(userInterfaceAuth.getSuserId(), userInterfaceAuth.getSinterfaceCode(), 10);
                }
            }
        }
    }
}