package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sys.service.FastMenuService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.vo.MenuVo;
import com.cloud.cang.mgr.sys.vo.RoleVo;
import com.cloud.cang.model.sys.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 快捷菜单控制层
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/fastMenu")
public class FastMenuController {
    private static final Logger log = LoggerFactory.getLogger(FastMenuController.class);
    @Autowired
    private FastMenuService fastMenuService;
    @Autowired
    private PurviewService purviewService;

    /****
     * 添加快捷菜单页面
     * @param map
     * @return
     */
    @RequestMapping("/toAddFastMenu")
    public String toSaveOperatoRrole(ModelMap map) {
        try {
            Set<ParameterGroupDetail> parameterGroupDetailSet = GrpParaUtil.get("SP000134");
            Iterator<ParameterGroupDetail> it = parameterGroupDetailSet.iterator();
            List list = new ArrayList();
            Integer isAdmin = SessionUserUtils.getSessionAttributeForUserDtl().getBisdAdmin();
            while (it.hasNext()) {
                ParameterGroupDetail parameterGroupDetail = it.next();
            /*    if ((null != isAdmin && 1 == isAdmin) || this.hasPurCode(parameterGroupDetail.getSvalue())) {*/
                if ((null != isAdmin && 1 == isAdmin && fastMenuService.isMerchantPurview(parameterGroupDetail.getSvalue())) || SecurityUtils.getSubject().isPermitted(parameterGroupDetail.getSvalue())) {
                    MenuVo menuVo = new MenuVo();
                    String[] fileNameSplit = parameterGroupDetail.getSname().split("#");
                    String name = fileNameSplit[0];
                    menuVo.setDetailId(parameterGroupDetail.getId());
                    menuVo.setSmenuName(name);
                    if (fastMenuService.isSelect(name)) {
                        menuVo.setIsSelect("1");
                    } else {
                        menuVo.setIsSelect("0");
                    }
                    list.add(menuVo);
                }
            }
            String temp = SysParaUtil.getValue("fast_menu_maxnum");
            if ("".equals(temp)) {
                temp = "0";
            }
            Integer maxNum = Integer.valueOf(temp);
            map.put("fastMenuList", list);
            map.put("maxNum", maxNum);
            return "sys/operator/saveFastMenu";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /****
     * 查看用户有没有该权限
     * @param purCode 权限码
     * @return
     */
    private boolean hasPurCode(String purCode) {
        List<String> list = purviewService.selectOperatorPurview(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        if (list.contains(purCode)) {
            return true;
        }
        return false;
    }

    /**
     * 用户选择快捷菜单
     *
     * @param operatorRoleIds 选中的快捷菜单Id
     * @return
     */
    @RequestMapping("/saveFastMenu")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> saveOperatorRole(String[] operatorRoleIds) {
        try {
        /*    if (operatorRoleIds == null || operatorRoleIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择快捷菜单！");
            }*/
            String temp = SysParaUtil.getValue("fast_menu_maxnum");
            if ("".equals(temp)) {
                temp = "0";
            }
            Integer maxNum = Integer.valueOf(temp);
            if (null != operatorRoleIds && operatorRoleIds.length > maxNum) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("最多可选择" + maxNum + "个快捷菜单,请重新选择！");
            }
            fastMenuService.saveFastMenu(operatorRoleIds, SessionUserUtils.getSessionAttributeForUserDtl().getId());
            // 操作日志
            String logText = MessageFormat.format("用户选择快捷菜单，快捷菜单ID集合{0}", operatorRoleIds);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("用户选择快捷菜单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，保存失败！");
        }
    }
}
