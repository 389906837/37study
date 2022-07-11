package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.lm.PackingRecord;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.service.UserInfoService;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import com.cloud.cang.rec.op.vo.AppManageVo;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.cloud.cang.rec.sys.service.OperatorService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * 开放平台用户信息表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private UserInterfaceAuthService userInterfaceAuthService;

    /**
     * 开放平台用户列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/userInfo/userInfo-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param userInfoDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<UserInfoVo>> queryData(ParamPage paramPage, UserInfoDomain userInfoDomain, String selectUser) {
        PageListVo<List<UserInfoVo>> pageListVo = new PageListVo<List<UserInfoVo>>();

        Page<UserInfoVo> page = new Page<UserInfoVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        userInfoDomain.setIdelFlag(0);
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 90);
        userInfoDomain.setCondition(sql);
        if ("true".equals(selectUser)) {
            userInfoDomain.setIstatus(1);
        }
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            userInfoDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = userInfoService.selectPageByDomainWhere(page, userInfoDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            userInfoService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除开放平台用户，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除开放平台用户异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 选择开放平台用户
     *
     * @return
     */
    @RequestMapping("/selectUser")
    public String selectUser() {
        return "op/userInfo/userInfo-selectUser";
    }


    /**
     * 新增/编辑 平台用户
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            UserInfoVo userInfoVo = userInfoService.selectVoById(sid);
            if (null == userInfoVo) {
                userInfoVo = new UserInfoVo();
            }
            modelMap.put("userInfoVo", userInfoVo);
            return "op/userInfo/userInfo-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增/修改 平台用户
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(UserInfo userInfo, String loginPwdConfirm) {
        try {
            if (!ValidateUtils.isMobile(userInfo.getSmobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("手机号格式错误！");
            }
            if (!ValidateUtils.isEmail(userInfo.getSemail())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("邮箱格式错误！");
            }

            if (StringUtils.isBlank(userInfo.getId())) {
                if (StringUtils.isBlank(userInfo.getSloginPwd()) || StringUtils.isBlank(loginPwdConfirm)) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请填写密码");
                }
                if (!loginPwdConfirm.equals(userInfo.getSloginPwd())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("密码与确认密码不同");
                }
                //执行新增
                userInfo = userInfoService.saveUserInfo(userInfo);
            } else {
                //执行修改
                userInfo = userInfoService.upUserInfo(userInfo);
            }
        } catch (Exception e) {
            log.error("编辑平台用户异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑平台用户异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑平台用户，平台用户编号{0}", userInfo.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }


    /**
     * 用户选择挂靠商户
     *
     * @return
     */
    @RequestMapping("/selectMerchant")
    public String selectMerchant() {
        return "op/userInfo/userInfo-selectMerchant";
    }

    /**
     * 主页修改密码页面
     *
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(ModelMap map, String sid) {
        try {
            map.put("id", sid);
            return "op/userInfo/userInfo-upPwd";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 修改密码
     *
     * @param spassword  新密码
     * @param spassword1 确认密码
     * @param id         用户ID
     * @return
     */
    @RequestMapping("/updatePassword")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> updatePassword(String spassword, String spassword1, String id) {
        try {
            UserInfo userInfo = userInfoService.selectByPrimaryKey(id);
            if (null == userInfo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户不存在！");
            }
            if (StringUtils.isBlank(id)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("提交参数有误！");
            }
            if (!spassword.equals(spassword1)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("密码与确认密码不同！");
            }
            userInfoService.updatePassword(id, spassword);
            //操作日志
            String logText = MessageFormat.format("修改用户密码，修改用户编号{0}", userInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("修改密码异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，保存失败！");
        }
    }

    /**
     * 重置密码
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public ResponseVo<String> resetPass(String checkboxId) {
        try {
            UserInfo userInfo = userInfoService.selectByPrimaryKey(checkboxId);
            if (null == userInfo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户不存在，重置密码失败！");
            }
            userInfoService.resetPassword(checkboxId);
            //操作日志
            String logText = MessageFormat.format("重置用户密码成功,用户编号{0}", userInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            log.error("重置密码异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("重置用户密码错误");
        }
    }

    /**
     * 开通接口权限
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/openInterfaceAuth")
    public String openInterfaceAuth(String sid, ModelMap map) {
        try {
            UserInfo userInfo = userInfoService.selectByPrimaryKey(sid);
            List<UserInterfaceAuthVo> userInterfaceAuthVos = userInfoService.selectUserInterfaceAuthVoList(sid);
            if (null != userInterfaceAuthVos && 0 == userInterfaceAuthVos.size()) {
                int size = userInterfaceAuthService.selectUserAuthNum(sid);
                if (0 == size) {
                    map.put("msg", "无接口权限开通！");
                } else {
                    map.put("msg", "已开通所有接口权限！");
                }
            }
            map.put("userInterfaceAuthVos", userInterfaceAuthVos);
            map.put("userInfo", userInfo);
            return "op/userInfo/userInfo-openInterfaceAuth";
        } catch (Exception e) {
            log.error("开通接口权限异常：{}", e);
            return ExceptionUtil.to500();
        }
    }

    /**
     * 保存选择接口权限
     *
     * @param
     * @return
     */
    @RequestMapping("/saveInterfaceAuth")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo saveInterfaceAuth(String[] pkIds, UserInfo userInfo) {
        try {
            userInfoService.saveInterfaceAuth(pkIds, userInfo);
            //操作日志
            String logText = MessageFormat.format("保存接口权限，权限ID集合{0}", pkIds);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("保存接口权限异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("保存接口权限失败！");
        }
    }
}