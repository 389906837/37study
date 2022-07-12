package com.cloud.cang.mgr.sys.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.utils.IdFactory;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.hy.MemberServicesDefine;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.hy.service.MbrRoleConfService;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.domain.OperatorDomain;
import com.cloud.cang.mgr.sys.service.MenuService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.RoleService;
import com.cloud.cang.mgr.sys.vo.OperatorVo;
import com.cloud.cang.mgr.sys.vo.RoleVo;
import com.cloud.cang.mgr.sys.vo.TreeVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.MbrRoleConf;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.BoolUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.*;

/**
 * 用户管理
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {

    private static final Logger log = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    MbrRoleConfService mbrRoleConfService;

    @RequestMapping("/list")
    public String list() {
        return "sys/operator/operator-list";
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            Operator operator = operatorService.selectByPrimaryKey(sid);
            if (null == operator) {
                operator = new Operator();
            }
            map.put("operator", operator);
            return "sys/operator/operator-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 根据用户ID获取用户
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOperatorById")
    public @ResponseBody
    ResponseVo<Operator> getOperatorById(String id) {

        Operator operator = operatorService.selectByPrimaryKey(id);
        if (null == operator) {
            operator = new Operator();
        }
        return ResponseVo.getSuccessResponse(operator);
    }

    /**
     * 用户管理列表数据
     *
     * @param paramPage 参数用于初始化分页大小
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<OperatorDomain>> queryData(OperatorVo operatorVo, ParamPage paramPage, String selectBd) {
        PageListVo<List<OperatorDomain>> pageListVo = new PageListVo<List<OperatorDomain>>();
        Page<OperatorDomain> page = new Page<OperatorDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(selectBd) && "true".equals(selectBd)) {
            operatorVo.setBisFreeze(1);
            operatorVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        } else {
            //生成查询条件
            Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
            String queryCondition = operatorService.generatePurviewSql(operator, 30);
            if (StringUtils.isNotBlank(queryCondition)) {
                operatorVo.setQueryCondition(queryCondition);
            }
        }
        operatorVo.setBisDelete(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            operatorVo.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = operatorService.selectPageByDomainWhere(page, operatorVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 修改增加用户信息
     *
     * @return
     */
    @RequestMapping("/save")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<Operator> save(Operator operator, String spasswordl) {
        try {
            if (StringUtils.isBlank(operator.getSrealName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.realname.error",null));
            }
            /*if (!StringUtil.hasCn(operator.getSrealName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("姓名不是中文！");
            }*/
            if (StringUtils.isBlank(operator.getSmobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.mobile.error",null));
            }
            /*if (!ValidateUtils.isMobile(operator.getSmobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.mobile.format.error",null));
            }*/
            if (!ValidateUtils.isEmail(operator.getSmail())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.email.format.error",null));
            }
            if (null == operator.getIdevType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.select.device.error",null));
            }
            if (null == operator.getImerType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.select.merchant.error",null));
            }
            // 执行新增
            if (StringUtils.isBlank(operator.getId())) {
                if (StringUtils.isBlank(operator.getSuserName())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.username.empty",null));
                }
                //判断手机号是否存在
                if (operatorService.isMobileExist(operator.getSmobile())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.username.exist",null));
                }
                if (StringUtils.isBlank(operator.getSpassword()) || StringUtils.isBlank(spasswordl)) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.pwd.empty",null));
                }
                if (!spasswordl.equals(operator.getSpassword())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.pwd.error",null));
                }
                if (null == operator.getIisBd()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.isbd",null));
                }
                if (null != operator.getImerType() && BizTypeDefinitionEnum.ImerType.DESIGNATED.getCode() == operator.getImerType() && StringUtils.isBlank(operator.getSmerList())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.merchant.empty",null));
                }
                if (null != operator.getIdevType() && (2 == operator.getIdevType()) && StringUtils.isBlank(operator.getSgroupDecList())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.device.empty",null));
                }
                if (1 == operator.getIisBd()) {
                    if (null != operator.getImerType() && BizTypeDefinitionEnum.ImerType.DESIGNATED.getCode() == operator.getImerType() && StringUtils.isBlank(operator.getSmerList())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.merchant.empty",null));
                    }
                    if (null != operator.getIdevType() && (2 == operator.getIdevType()) && StringUtils.isBlank(operator.getSgroupDecList())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.device.empty",null));
                    }
                }
                operator.setSoperatorNo(CoreUtils.newCode(EntityTables.SYS_OPERATOR));
                operator.setDaddDate(DateUtils.getCurrentDateTime());
                operator.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                operator.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());
                operator.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                operator.setDmodifyDate(DateUtils.getCurrentDateTime());
                operator.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                operator.setSpassword(MD5.encode(operator.getSpassword()));
                operator.setBisAdmin(0);
                operator.setBisDelete(0);
                operatorService.insert(operator);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.add.operator",null)+"，"+MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", operator.getSoperatorNo());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                return ResponseVo.getSuccessResponse(operator);
            } else// 执行修改
            {
                Operator operatorl = operatorService.selectByPrimaryKey(operator.getId());
        /*        if (!operator.getSuserName().equals(operatorl.getSuserName())) {
                    if (operatorService.isUsernameExist(operator.getSuserName())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名已经存在");
                    }
                }*/
                MemberInfo memberInfo = memberInfoService.selectByMobile(operatorl.getSmobile(), operatorl.getSmerchantCode());
                //是否补货员
                if (null != memberInfo && 1 == memberInfo.getIisReplenishment()) {
                    if (!operator.getSmobile().equals(operatorl.getSmobile())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.bby.mobile.empty",null));
                    }
                }
                if (!operator.getSmobile().equals(operatorl.getSmobile())) {
                    if (operatorService.isMobileExist(operator.getSmobile())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.mobile.exist",null));
                    }
                }
                if (null == operator.getIisBd()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.isbd",null));
                }
                if (1 == operator.getIisBd()) {
                    if (null != operator.getImerType() && BizTypeDefinitionEnum.ImerType.DESIGNATED.getCode() == operator.getImerType() && StringUtils.isBlank(operator.getSmerList())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.merchant.empty",null));
                    }
                    if (null != operator.getIdevType() && (2 == operator.getIdevType()) && StringUtils.isBlank(operator.getSgroupDecList())) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.device.select",null));
                    }
                }
                operator.setDmodifyDate(DateUtils.getCurrentDateTime());
                operator.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                operatorService.upByIdSelective(operator);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.modify.operator",null)+"，ID{0}", operator.getId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse(operator);
            }
        } catch (Exception ex) {
            log.error("编辑用户信息异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.save.operator.error",null));
        }
    }


    /**
     * 删除用户
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            operatorService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.delete.operator",null)+"{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException se) {
            log.error("删除用户异常：{}", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());

        } catch (Exception e) {
            log.error("删除用户异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.delete.operator.error",null));
        }
    }

    /**
     * 主页修改密码页面
     *
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(ModelMap map, String sid) {
        try {
      /*AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
       map.put("operator", authorizationVO);*/
            map.put("id", sid);
            return "sys/operator/updatePwd";
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
            Operator operator = operatorService.selectByPrimaryKey(id);
            if (null == operator) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.not.exist",null));
            }
            if (StringUtils.isBlank(id)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.param.error",null));
            }
            if (!spassword.equals(spassword1)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.pwd.exception",null));
            }
            operatorService.updatePassword(id, spassword);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.edit.operator.pwd",null)+"ID{0}", id);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("修改密码异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.save.error",null));
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

            operatorService.resetPassword(checkboxId);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.reset.operator.pwd",null)+"ID{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            log.error("重置密码异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.reset.operator.pwd.error",null));
        }
    }

    /**
     * 设置/取消 超级管理员
     *
     * @param checkboxId,type
     * @return
     */
    @RequestMapping("/setAdmin")
    public @ResponseBody
    ResponseVo<String> setAdmin(String checkboxId, Integer type) {
        try {
            if (StringUtils.isBlank(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.select.operator",null));
            }
            Operator operator = new Operator();
            operator.setId(checkboxId);
            operator.setBisAdmin(type);
            operator.setDmodifyDate(DateUtils.getCurrentDateTime());
            operator.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            operatorService.updateBySelective(operator);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.set.operator.admin",null)+"ID{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("设置/取消 超级管理员异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.set.operator.admin.error",null));
        }
    }

    /**
     * 设置/取消 补货员
     *
     * @param operatorId,type
     * @return
     */
    @RequestMapping("/setReplenishment")
    public @ResponseBody
    ResponseVo<String> setReplenishment(String operatorId, Integer type) {
        try {
            if (StringUtils.isBlank(operatorId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.select.operator",null));
            }
            Operator operator = operatorService.selectByPrimaryKey(operatorId);
            MemberInfo memberInfo = null;
            //如果是则新增补货员权限
            if (1 == type) {
                //新增会员表
                memberInfo = saveMember(operator);

                //更新会员表数据
                memberInfo.setIisReplenishment(type);
                memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());
                memberInfo.setImemberType(20);//设置会员类型为补货类型
                memberInfoService.updateBySelective(memberInfo);

                //会员角色关系表
                MbrRoleConf mbrRoleConf = new MbrRoleConf();
                mbrRoleConf.setSmemberId(memberInfo.getId());
                mbrRoleConf.setSroleId("d8294878204111e8a4fa000c2937a246");
                mbrRoleConfService.insert(mbrRoleConf);

            } else if (0 == type) {
                memberInfo = saveMember(operator);
                memberInfo.setIisReplenishment(type);
                memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());

                memberInfo.setImemberType(10);//设置会员类型为购物会员
                memberInfoService.updateBySelective(memberInfo);
                mbrRoleConfService.delByMbrIdRoleId(memberInfo.getId(), "d8294878204111e8a4fa000c2937a246");
            }
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.set.operator.bhy.qualifications",null)+"ID{0}", memberInfo.getId());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("设置/取消 补货员异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.set.operator.bhy.error",null));
        }
    }

    /**
     * 设置补货员，保存用户表信息
     * @param operator
     * @return
     * @throws Exception
     */
    private MemberInfo saveMember(Operator operator){
        //判断用是否存在
        MemberInfo memberInfo = getMember(operator.getSmobile(),operator.getSmerchantCode());
        if(memberInfo !=null){
            return memberInfo;
        }
        MemberInfoDto memberDto = new MemberInfoDto();
        memberDto.setNikeName(operator.getSuserName());
        memberDto.setSmobile(operator.getSmobile());
        memberDto.setSloginPassword(operator.getSpassword());
        memberDto.setImemberType(BizTypeDefinitionEnum.MemberType.M2_MEMBER.getCode());
        memberDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.VENDSTOP.getCode());
        memberDto.setSmerchantId(operator.getSmerchantId());
        memberDto.setSmerchantCode(operator.getSmerchantCode());
        memberDto.setThirdUserId(IdFactory.getUUIDSerialNumber());
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MemberServicesDefine.MEMBER_REGISTER);
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<MemberInfo>>() { });
            invoke.setRequestObj(memberDto);
            ResponseVo<MemberInfo> resMember = (ResponseVo<MemberInfo>) invoke.invoke();
            if(resMember!=null && resMember.isSuccess()){
                return resMember.getData();
            }else {
                log.error("会员注册失败===",resMember);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户是否存在
     * @param mobile
     * @param merchantCode
     * @return
     */
    private MemberInfo getMember(String mobile,String merchantCode){
        MemberInfo entity = new MemberInfo();
        entity.setSmobile(mobile);
        entity.setSmerchantCode(merchantCode);
        entity.setIdelFlag(0);
        List<MemberInfo> memberInfos = memberInfoService.selectByEntityWhere(entity);
        if(memberInfos!=null && memberInfos.size() >0){
            return memberInfos.get(0);
        }
        return null;
    }
    /****
     * 管理员分配角色页面
     * @param map
     * @param sid  管理员Id
     * @return
     */
    @RequestMapping("/toSaveOperatoRrole")
    public String toSaveOperatoRrole(ModelMap map, String sid) {
        try {
            Operator operator = operatorService.selectByPrimaryKey(sid);
            List<RoleVo> roleVoLsit = roleService.selectOperatorRole(operator);
            map.put("roleVoLsit", roleVoLsit);
            map.put("operator", operator);
            return "sys/operator/saveOperator";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 用户分配角色
     *
     * @param operatorRoleIds 选中的角色集合
     * @param operatorId      用户ID
     * @return
     */
    @RequestMapping("/saveOperatorRole")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> saveOperatorRole(String[] operatorRoleIds, String operatorId) {
        try {
            Operator operator = operatorService.selectByPrimaryKey(operatorId);
            if (null == operator) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.not.exist",null));
            }
           /* if (operatorRoleIds == null || operatorRoleIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择用户角色！");
            }*/
            operatorService.saveOperatorRole(operatorId, operatorRoleIds);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.set.operator.role",null)+"{0}", operatorRoleIds);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("用户分配角色异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.save.error",null));
        }
    }

    /**
     * 根据用户名获取动态树
     *
     * @return
     */
    @RequestMapping("/getMenuTree")
    @ResponseBody
    public List<TreeVo> getMenuTree() {
        // 获取登陆User信息
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
        // 获取所有菜单
        List<Menu> menuList = null;
        if (null != authVo && authVo.getBisdAdmin().intValue() == 1) {
            //超级管理员
            menuList = this.menuService.selectByMerchantId(authVo.getSmerchantId());
        } else {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", authVo.getId());
            param.put("merchantId", authVo.getSmerchantId());
            menuList = this.menuService.queryByMap(param);
        }
        Map<String, TreeVo> treeMap = new LinkedHashMap<String, TreeVo>();
        TreeVo root = new TreeVo();
        String tempName = "";
        // 把菜单加到Map里
        for (Menu menu : menuList) {
            TreeVo tree = new TreeVo();
            tree.setBisroot(menu.getBisRoot().toString());
            tree.setId(menu.getId());
            tempName = MessageSourceUtils.getMessageByKey(menu.getSname(),null);
            if (StringUtil.isNotBlank(tempName)) {
                tree.setText(tempName);
            } else {
                tree.setText(menu.getSname());
            }
            tree.setLeaf(BoolUtils.toBooleanObject(menu.getBisLeaf()));
            tree.setSparentid(menu.getSparentId());
            tree.setIconCls(menu.getSimagePath());
            tree.setHref(menu.getSmenuPath());
            /*if (BoolUtils.toBooleanObject(menu.getBisLeaf())) {
                tree.setHref(menu.getSmenuPath());
            } else {
                tree.setHref("");
            }*/
            //tree.setIconCls("resource");
            treeMap.put(menu.getId(), tree);
        }
        // 递归添加子节点
        Iterator<Map.Entry<String, TreeVo>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, TreeVo> entry = it.next();
            // String key = entry.getKey();
            TreeVo tree = entry.getValue();
            if (BoolUtils.toBooleanObject(tree.getBisroot())) {// 如果是根节点
                root.getChildren().add(tree);
            } else {
                TreeVo parent = treeMap.get(tree.getSparentid());// 根据父Id查询
                if (parent != null) {
                    parent.getChildren().add(tree);
                }
            }
        }
        // 删除没有子节点的
        root.removeNoRight();
        if (root.getChildren().size() > 0) {
            root.getChildren().get(0).setExpanded(true);
        }
        return root.getChildren();
    }

    /**
     * 商户选择指定系统用户
     *
     * @param
     * @return
     */
    @RequestMapping("/selectBdList")
    public String selectBdList() {
        return "sys/operator/operator-selBdList";
    }

    /**
     * 用户启禁用
     *
     * @param checkboxId 用户ID
     * @param type       1:启用  0:禁用
     * @return
     */
    @RequestMapping("/prohibitionMethod")
    @ResponseBody
    public ResponseVo prohibitionMethod(String checkboxId, Integer type) {
        try {
            Operator operator = new Operator();
            operator.setId(checkboxId);
            operator.setBisFreeze(type);
            operatorService.upByIdSelective(operator);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("用户启禁用异常：{}", e);
            e.printStackTrace();
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.set.operator.status.error",null));
        }
    }
}
