package com.cloud.cang.mgr.sys.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.MenuService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.vo.MenuTreeVo;
import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.utils.BoolUtils;
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
import java.util.*;


/**
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/purview")
public class PurviewController extends GenericController {

    private static final Logger log = LoggerFactory.getLogger(PurviewController.class);

    @Autowired
    private PurviewService purviewService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 权限码列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sys/purview/purview-list";
    }


    @RequestMapping("/getAllMenu")
    public @ResponseBody
    List<MenuTreeVo> getAllMenu() {
        //获取所有菜单
        List<Menu> menuList = menuService.queryAllMenu();
        Map<String, MenuTreeVo> treeMap = new LinkedHashMap<String, MenuTreeVo>();
        MenuTreeVo root = new MenuTreeVo();
        // 把菜单加到Map里
        for (Menu menu : menuList) {
            MenuTreeVo tree = new MenuTreeVo();
            tree.setText(menu.getSname());
            tree.setHref(menu.getId());
            tree.setBisroot(menu.getBisRoot().toString());
            tree.setId(menu.getId());
            tree.setSparentId(menu.getSparentId());
            treeMap.put(menu.getId(), tree);
        }
        // 递归添加子节点
        Iterator<Map.Entry<String, MenuTreeVo>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, MenuTreeVo> entry = it.next();
            MenuTreeVo tree = entry.getValue();
            if (BoolUtils.toBooleanObject(tree.getBisroot())) {// 如果是根节点
                root.getNodes().add(tree);
            } else {
                MenuTreeVo parent = treeMap.get(tree.getSparentId());// 根据父Id查询
                if (parent != null) {
                    parent.getNodes().add(tree);
                }
            }
        }
        return root.getNodes();
    }


    @RequestMapping("/page")
    public String purviewPage(String menuId, ModelMap map) {
        map.put("menuId", menuId);
        return "sys/purview/purview-menu-list";
    }

    /**
     * 根据菜单ID获取权限码
     *
     * @param menuId
     * @return
     */
    @RequestMapping("/getPurviewByMenuId")
    public @ResponseBody
    PageListVo<List<Purview>> getPurviewByMenuId(ParamPage paramPage, String menuId) {
        PageListVo<List<Purview>> pageListVo = new PageListVo<List<Purview>>();
        /*if (StringUtil.isBlank(menuId)) {
            return pageListVo;
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNotBlank(menuId)) {
            map.put("sparentId", menuId);
        }
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            map.put("orderCondition", paramPage.getSidx() + " " + paramPage.getSord());
        }

        Page<Purview> page = new Page<Purview>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        page = purviewService.selectPageByMap(page, map);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
       /* List<Purview> purviews = purviewService.selectByMapWhere(map);
        if (purviews != null) {
            pageListVo.setRows(purviews);
        }
        return pageListVo;*/
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid, String sparentId) {
        Purview purview = purviewService.selectByPrimaryKey(sid);
        if (purview == null) {
            purview = new Purview();
            if (StringUtil.isNotBlank(sparentId)) {
                purview.setSparentId(sparentId);
            }
        }
        map.put("purview", purview);
        return "sys/purview/purview-edit";
    }

    /**
     * 删除权限码
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            purviewService.delete(checkboxId);
            ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
            responseVo.setMsg("删除成功");
            // 操作日志
            String logText = MessageFormat.format("删除权限码，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return responseVo;
        } catch (Exception e) {
            log.error("删除权限码异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 权限码同步到商户
     *
     * @param checkboxId 同步权限码ID集合
     * @throws Exception
     */
    @RequestMapping("/sync")
    public @ResponseBody
    ResponseVo<String> syncPurview(String[] checkboxId) {
        try {
            merchantInfoService.syncPurview(checkboxId);
            ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
            responseVo.setMsg("同步成功");
            // 操作日志
            String logText = MessageFormat.format("同步权限码，权限码ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return responseVo;
        } catch (Exception e) {
            log.error("权限码同步到商户异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常同步失败！");
        }
    }

    /**
     * 保存/修改权限码
     *
     * @param purview 权限码对象
     * @return
     */
    @RequestMapping("/save")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<Purview> save(Purview purview) {
        try {
            if (StringUtils.isBlank(purview.getSparentId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("菜单ID为空！");
            }
            Menu menu = menuService.selectByPrimaryKey(purview.getSparentId());
            // 执行保存
            if (StringUtils.isBlank(purview.getId())) {
                if (purviewService.isNameExist(purview)) {
                    return ErrorCodeEnum.ERROR_COMMON_UNIQUE.getResponseVo("权限编码已经存在");
                }
                purview.setSpurNo(CoreUtils.newCode(EntityTables.SYS_PURVIEW));
                purview.setDaddDate(DateUtils.getCurrentDateTime());
                purview.setSpyName(StringUtil.getFullSpell(purview.getSpurName()));
                purview.setSjpName(StringUtil.getFirstSpell(purview.getSpurName()));
                purview.setSparentName(menu.getSname());
                purviewService.insert(purview);
                //操作日志
                String logText = MessageFormat.format("新增权限码，编号{0}", purview.getSpurNo());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行更新
                Purview presitPurview = purviewService.selectByPrimaryKey(purview.getId());
                if (presitPurview != null) {
                    if (!presitPurview.getSpurCode().equalsIgnoreCase(purview.getSpurCode())) {
                        if (purviewService.isNameExist(purview)) {
                            return ErrorCodeEnum.ERROR_COMMON_UNIQUE.getResponseVo("权限编码已经存在");
                        }
                    }
                }
                purview.setSpyName(StringUtil.getFullSpell(purview.getSpurName()));
                purview.setSjpName(StringUtil.getFirstSpell(purview.getSpurName()));
                purviewService.updateBySelective(purview);
                //操作日志
                String logText = MessageFormat.format("修改权限码，编号{0}", presitPurview.getSpurNo());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }

        } catch (Exception ex) {
            log.error("保存/修改权限码异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("权限码操作失败");
        }
        return ResponseVo.getSuccessResponse(purview);
    }

}
