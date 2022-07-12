package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sys.domain.MenuDomain;
import com.cloud.cang.mgr.sys.service.MenuService;
import com.cloud.cang.model.sys.Menu;
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
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/menu")
public class SysMenuController extends GenericController {

    private static final Logger log = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    MenuService menuService;


    @RequestMapping("/list")
    public String list(ModelMap map) {
        //获取所有一级菜单
        List<Menu> parentMenu = menuService.selectByParentMenu();
        map.put("parentMenu", parentMenu);
        return "sys/menu/menu-list";
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid, String sparentId) {
        try {
            Menu menu = menuService.selectByPrimaryKey(sid);
            if (menu == null) {
                menu = new Menu();
                if (StringUtil.isNotBlank(sparentId)) {
                    menu.setSparentId(sparentId);
                }
            }
            map.put("menu", menu);
            //获取所有一级菜单
            List<Menu> parentMenu = menuService.selectByParentMenu();
            map.put("parentMenu", parentMenu);
            return "sys/menu/menu-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存菜单
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<Menu> save(Menu menu) {
        try {
            // 如果id为空就就添加
            if (StringUtils.isBlank(menu.getId())) {
                if (menuService.isExistName(menu)) {
                    return ErrorCodeEnum.ERROR_COMMON_UNIQUE.getResponseVo("菜单名称已经存在");
                }
                menu.setSmenuNo(CoreUtils.newCode("sys_menu"));
                menu.setStitle(menu.getSname());
                menuService.save(menu, menu.getId());
                //操作日志
                String logText = MessageFormat.format("新增菜单，编号{0}", menu.getSmenuNo());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 修改
                Menu presisMenu = menuService.selectByPrimaryKey(menu.getId());
                if (presisMenu != null) {
                    if (!presisMenu.getSname().equals(menu.getSname())) {
                        if (menuService.isExistName(menu)) {
                            return ErrorCodeEnum.ERROR_COMMON_UNIQUE.getResponseVo("菜单名称已经存在");
                        }
                    }
                }
                //menu.setSparentId(presisMenu.getSparentId());
                menuService.updateBySelective(menu);
                //操作日志
                String logText = MessageFormat.format("编辑菜单，编号{0}", presisMenu.getSmenuNo());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
        } catch (Exception ex) {
            log.error("保存菜单异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存菜单信息失败");
        }
        return ResponseVo.getSuccessResponse(menu);
    }

    /**
     * 删除菜单
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
            menuService.delete(checkboxId);
            ResponseVo responseVo = ResponseVo.getSuccessResponse();
            responseVo.setMsg("删除成功");
            //操作日志
            String logText = MessageFormat.format("删除菜单，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return responseVo;
        } catch (Exception e) {
            log.error("删除菜单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @RequestMapping("/queryAllMenu")
    public @ResponseBody
    PageListVo<List<Menu>> queryAllMenu(ParamPage paramPage, MenuDomain menu) {
        // 查询所有子菜单
        PageListVo<List<Menu>> pageListVo = new PageListVo<List<Menu>>();
        Page<Menu> page = new Page<Menu>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        //menu.setBisRoot(0);
        menu.setBisDisplay(1);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            menu.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = menuService.queryAllMenu(page, menu);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


}
