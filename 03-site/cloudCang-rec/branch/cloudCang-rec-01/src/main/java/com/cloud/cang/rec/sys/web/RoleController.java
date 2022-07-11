/*
 * Copyright (C) 2015 37cang All rights reserved
 * Author: 【zhouhong】
 * Date: 2015年6月10日
 * Description:MailCenterAsyncServiceImpl.java 
 */
package com.cloud.cang.rec.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.sh.service.MerchantInfoService;
import com.cloud.cang.rec.sh.vo.PurviewTreeVo;
import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.rec.sys.domain.RoleDomain;
import com.cloud.cang.rec.sys.service.MenuService;
import com.cloud.cang.rec.sys.service.PurviewService;
import com.cloud.cang.rec.sys.service.RoleService;
import com.cloud.cang.rec.sys.service.RolepurviewService;

import com.cloud.cang.rec.sys.vo.CheckBoxTreeVo;
import com.cloud.cang.rec.sys.vo.RoleVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.model.sys.Role;
import com.cloud.cang.model.sys.Rolepurview;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.BoolUtils;
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
import java.util.*;

/**
 * 后台系统角色管理
 *
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends GenericController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PurviewService purviewService;

    @Autowired
    private RolepurviewService rolepurviewService;

    @Autowired
    MerchantInfoService merchantInfoService;

    /**
     * 角色列表
     *
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<RoleVo>> queryDataByCondition(ParamPage paramPage, RoleDomain roleDomain) {
        AuthorizationVO opertorVo = SessionUserUtils.getSessionAttributeForUserDtl();
        PageListVo<List<RoleVo>> pageListVo = new PageListVo<List<RoleVo>>();
        roleDomain.setSmerchantId(opertorVo.getSmerchantId());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            roleDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        Page<RoleVo> page = new Page<RoleVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getLimit());

        page = roleService.selectPageByDomainWhere(page, roleDomain);
        pageListVo.setRows(page.getResult());
        return pageListVo;
    }

    /**
     * 根据条件获取获取角色列表数据
     *
     * @param role
     * @return
     */
    @RequestMapping("/queryDataByCondition")
    public @ResponseBody
    ResponseVo<List<Role>> queryDataByCondition(Role role) {

        List<Role> roles = roleService.selectByEntityWhere(role);
        return ResponseVo.getSuccessResponse(roles);
    }

    @RequestMapping("/list")
    public String list() {
        return "sys/role/role-list";
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            Role role = roleService.selectByPrimaryKey(sid);
            if (role == null) {
                role = new Role();
            }
            map.put("role", role);
            return "sys/role/role-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 修改或增加角色
     *
     * @param role
     * @return
     */

    @RequestMapping("/save")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<Role> save(Role role) {
        try {
            if (StringUtil.isBlank(role.getSroleName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("角色名不能为空！");
            }
            // 执行新增
            if (StringUtils.isBlank(role.getId())) {
                AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
                role.setSmerchantId(authorizationVO.getSmerchantId());
               /* role.setSmerchantName(authorizationVO.getSmerchantName());*/
                role.setSmerchantCode(authorizationVO.getSmerchantCode());
                role.setSsystemSource("40");
                //操作日志
                String logText = MessageFormat.format("新增角色，角色名{0}", role.getSroleName());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                roleService.insert(role);
            } else// 执行修改
            {
                //操作日志
                roleService.updateBySelective(role);
                String logText = MessageFormat.format("编辑角色，id{0}", role.getId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
        } catch (Exception ex) {
            log.error("修改或增加角色异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("角色信息保存失败！");
        }
        return ResponseVo.getSuccessResponse(role);

    }

    /**
     * 删除角色
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
            roleService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除角色，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("系统异常删除失败:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }


    @RequestMapping("/saveRolePurview")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> saveRolePurview(String checkPurviewId, String roleId, String id) {
        try {
            if (StringUtils.isBlank(roleId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("数据异常，保存失败");
            }
            roleService.saveRolePurview(checkPurviewId, roleId);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("保存权限异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，保存失败！");
        }
    }

    /**
     * 分配权限页面
     *
     * @param sid 角色Id
     * @return
     */
    @RequestMapping("/toSavePurview")
    public String toSavePurview(ModelMap map, String sid) {
        try {
            //获取角色ID
            Role role = roleService.selectByPrimaryKey(sid);
            //组装角色权限树结构
            Map<String, Object> paramMap = new HashMap<String, Object>();

            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(role.getSmerchantId());
            if (!merchantInfo.getSparentId().equals("0")) {
                paramMap.put("iisMerchantUsed", 1);
            }
            paramMap.put("roleId", role.getId());
            paramMap.put("orderCondition", " b.sparent_id asc, a.dadd_date asc");
            List<PurviewDomain> purviewList = roleService.selectPurviewByMerchant(paramMap);
            Map<String, PurviewTreeVo> treeMap = new LinkedHashMap<String, PurviewTreeVo>();
            Map<String, PurviewTreeVo> treeMap1 = new LinkedHashMap<String, PurviewTreeVo>();
            PurviewTreeVo root = new PurviewTreeVo();
            // 把菜单加到Map里
            for (PurviewDomain purview : purviewList) {
                PurviewTreeVo tree = new PurviewTreeVo();
                tree.setId(purview.getId());
                tree.setText(purview.getSpurName());
                tree.setIsSelect(purview.getIsSelect());
                tree.setMenuName(purview.getMenuName());
                tree.setBisroot(String.valueOf(purview.getIsRoot()));
                if (purview.getMenuParentId().equals("0")) {
                    //tree.setBisroot("1");
                    treeMap1.put(purview.getMenuId(), tree);
                    tree.setSparentId("0");
                } else {
                    PurviewTreeVo temp = treeMap1.get(purview.getMenuParentId());
                    tree.setSparentId(temp.getId());
                    //tree.setBisroot("0");
                }

                treeMap.put(purview.getId(), tree);
            }
            // 递归添加子节点
            Iterator<Map.Entry<String, PurviewTreeVo>> it = treeMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, PurviewTreeVo> entry = it.next();
                PurviewTreeVo tree = entry.getValue();
                if (BoolUtils.toBooleanObject(tree.getBisroot())) {// 如果是根节点
                    root.getNodes().add(tree);
                } else {
                    PurviewTreeVo parent = treeMap.get(tree.getSparentId());// 根据父Id查询
                    if (parent != null) {
                        parent.getNodes().add(tree);
                    }
                }
            }

            map.put("roleList", root.getNodes());
            map.put("role", role);

            return "sys/role/savePurview";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 商户角色权限分配
     *
     * @param roleId     角色
     * @param purviewIds 商户权限ID
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateRoleAuth")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> updateRoleAuth(String roleId, String[] purviewIds) {
        try {
            Role role = roleService.selectByPrimaryKey(roleId);
            if (role == null) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("角色不存在！");
            }
            if (!SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId().equals(role.getSmerchantId())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，系统角色不属于自己商户！");
            }
            if (purviewIds == null || purviewIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择菜单权限！");
            }
            roleService.updateRoleAuth(role, purviewIds);
            // 操作日志
            String logText = MessageFormat.format("角色权限分配，权限码ID集合{0}", purviewIds);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("商户角色权限分配异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常操作失败！");
        }
    }

    /***
     * 角色分配权限树
     *
     * @return
     */
    @RequestMapping("/getCheckBoxTree")
    @ResponseBody
    public List<CheckBoxTreeVo> getCheckBoxTree(String sid) {
  /*  public String getCheckBoxTree(String sid, ModelMap modelMap) {*/
        if (null == sid) {
            return null;
        }
        // 获取所有菜单
        List<Menu> menuList = menuService.selectByEntityWhere(new Menu());
        // 获取商家所有的权限
        /*List<Purview> listPurview = purviewService.selectByEntityWhere(new Purview());*/

        List<Purview> listPurview = purviewService.selectMerchantPurview(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());

        Rolepurview paramRolepurview = new Rolepurview();
        paramRolepurview.setSroleId(sid);

        List<Rolepurview> rolepurviewList = rolepurviewService.selectByEntityWhere(paramRolepurview);

        // 构造treeMap
        Map<String, CheckBoxTreeVo> treeMap = new LinkedHashMap<String, CheckBoxTreeVo>();
        CheckBoxTreeVo root = new CheckBoxTreeVo();
        // 把菜单加到Map里
        for (Menu menu : menuList) {
            CheckBoxTreeVo tree = new CheckBoxTreeVo();
            tree.setBisroot(menu.getBisRoot().toString());
            tree.setId(menu.getId());
            tree.setText(menu.getSname());
            tree.setLeaf(false);
            tree.setSparentid(menu.getSparentId());
            tree.setHref("");
            //tree.setIconCls("resource");
            treeMap.put(menu.getId(), tree);
        }

        // 循环所有权限码
        for (Purview purview : listPurview) {
            CheckBoxTreeVo tree = new CheckBoxTreeVo();
            tree.setBisroot("0");
            tree.setId(purview.getId());
            tree.setText(purview.getSpurName());
            tree.setLeaf(true);
            tree.setSparentid(purview.getSparentId());
            if (rolepurviewList != null) {
                Iterator<Rolepurview> it = rolepurviewList.iterator();
                while (it.hasNext()) {
                    Rolepurview rolepurview = it.next();
                    if (rolepurview.getSpurviewId().equals(purview.getId())) {
                        tree.setChecked(true);
                        break;
                    }
                }
            }
            treeMap.put(purview.getId(), tree);
        }

        // 递归构造树
        Iterator<Map.Entry<String, CheckBoxTreeVo>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CheckBoxTreeVo> entry = it.next();
            CheckBoxTreeVo tree = entry.getValue();
            if (tree.getBisroot().equals("1")) {// 如果是根节点
                root.getChildren().add(tree);
            } else {
                CheckBoxTreeVo parent = treeMap.get(tree.getSparentid());// 根据父Id查询
                if (parent != null) {
                    parent.getChildren().add(tree);
                }
            }
        }

        // 选中有权限码的节点及父节点
        root.removeNoRight();
        root.checkall();
        if (root.getChildren().size() > 0) {
            root.getChildren().get(0).setExpanded(true);
        }
      /*  modelMap.put("root", root.getChildren());*/
        return root.getChildren();

     /*   return "sys/role/savepurview";*/
    }

}
