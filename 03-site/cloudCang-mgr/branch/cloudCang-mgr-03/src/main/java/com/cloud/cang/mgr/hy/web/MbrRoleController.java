package com.cloud.cang.mgr.hy.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.hy.service.MbrPurviewService;
import com.cloud.cang.mgr.hy.service.MbrRoleService;
import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.mgr.hy.vo.MbrRoleVo;
import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
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
import java.util.Calendar;
import java.util.List;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 会员角色管理
 * @time 2018-02-08 15:03:10
 * @fileName MbrRoleController.java
 */
@Controller
@RequestMapping("/mbrRole")
public class MbrRoleController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(MbrRoleController.class);

    // 注入serv类
    @Autowired
    private MbrRoleService mbrRoleService;

    @Autowired
    private MbrPurviewService mbrPurviewService;

    @RequestMapping("/list")
    public String list() {
        return "hy/mbrRole-list";
    }

    /**
     * 会员角色表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<MbrRole>> queryMbrRole(ParamPage paramPage, MbrRoleVo mbrRoleVo) {
        PageListVo<List<MbrRole>> pageListVo = new PageListVo<List<MbrRole>>();
        Page<MbrRole> page = new Page<MbrRole>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            mbrRoleVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = mbrRoleService.selectPageAllRole(page, mbrRoleVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public @ResponseBody
    ResponseVo<MbrRole> queryDataByCondition(String id) {
        MbrRole mbrRole = mbrRoleService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(mbrRole);
    }

    /**
     * 跳转编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String sid) {
        try {
            MbrRole mbrRole = mbrRoleService.selectByPrimaryKey(sid);
            if (mbrRole == null) {
                mbrRole = new MbrRole();
            }
            modelMap.put("mbrRole", mbrRole);
            return "hy/mbrRole-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存会员角色
     *
     * @param mbrRole
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<MbrRole> save(MbrRole mbrRole) {
        //判断是否重复
        MbrRole m = mbrRoleService.selectByExist(mbrRole);
        if (m != null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.rnae", null));
        }
        // 如果id为空就就添加
        if (StringUtils.isBlank(mbrRole.getId())) {
            mbrRole.setDaddDate(DateUtils.getCurrentDateTime());
            mbrRole.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            mbrRole.setDmodifyDate(DateUtils.getCurrentDateTime());
            mbrRole.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            mbrRoleService.insert(mbrRole);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.hy.amr", null) + MessageSourceUtils.getMessageByKey("main.name", null) + "{0}", mbrRole.getSroleName());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            mbrRole.setDmodifyDate(DateUtils.getCurrentDateTime());
            mbrRole.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            mbrRoleService.updateBySelective(mbrRole);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.hy.mmr", null) + MessageSourceUtils.getMessageByKey("main.name", null) + "{0}", mbrRole.getSroleName());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

        }
        return ResponseVo.getSuccessResponse(mbrRole);
    }

    /**
     * 根据ID数据删除会员角色表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        mbrRoleService.delete(checkboxId);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 分配权限跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/assign")
    public String assign(String sid, ModelMap modelMap) {
        try {
            if (sid != null && sid != "") {
                //获取角色ID
                MbrRole mbrRole = mbrRoleService.selectByPrimaryKey(sid);
                //组装角色权限树结构
                List<MbrPurviewVo> mbrRoleList = mbrPurviewService.selectMbrRole(mbrRole);
                modelMap.put("mbrRoleList", mbrRoleList);
                modelMap.put("mbrRole", mbrRole);
                return "hy/mbrAssign-list";
            }
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 用户分配角色
     *
     * @param roleIds 选中的角色集合
     * @param mid     会员角色ID
     * @return
     */
    @RequestMapping("/saveMbrRole")
    public @ResponseBody
    ResponseVo<String> saveMbrRole(String[] roleIds, String mid) {
        try {
            MbrRole mbrRole = mbrRoleService.selectByPrimaryKey(mid);
            if (null == mbrRole) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.operator.not.exist", null));
            }
            if (roleIds == null || roleIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.psaur", null));
            }
            mbrRoleService.saveOperatorRole(roleIds, mid);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.set.operator.role", null) + "{0}", roleIds);
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("用户分配角色异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.save.error", null));
        }
    }
}
