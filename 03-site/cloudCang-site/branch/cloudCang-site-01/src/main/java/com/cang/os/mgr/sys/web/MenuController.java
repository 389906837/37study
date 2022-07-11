/**
 * 
 */
package com.cang.os.mgr.sys.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.sys.Menu;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.sys.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;
import com.cang.os.security.utils.SessionUserUtils;

/**
 * @author fuxinglong
 *
 */
@Controller
@RequestMapping("/mgr/menu")
public class MenuController {
	
	@Autowired
    MenuService menuService;
	
	 @RequestMapping("/list")
     public String list(Menu menu, ParamPage paramPage, ModelMap map) {
	    Page<Menu> page = new Page<Menu>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(menu.getSname())) {
			query.addCriteria(Criteria.where("sname").regex(MongoDbUtil.getLikeQueryCondition(menu.getSname())));
		}
		
		if (StringUtils.isNotBlank(menu.getSparentId())) {
			query.addCriteria(Criteria.where("sparentId").is(menu.getSparentId()));
		}
		
		page=menuService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/sys/menuList";
    }
	 
	 
	 /**
	  * 编辑用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Menu menu = menuService.findById(id);
			 map.put("menu", menu);
			 
		 }
		 return new ModelAndView("mgr/sys/menuEdit");
	 }
	 
	 
	 /**
	  * 保存菜单
	  * @param menu
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(Menu menu,ModelMap map,String backUri){
         // 如果id为空就就添加
         if (StringUtils.isBlank(menu.getId())) {
             if (isExistName(menu.getSname())) {
            	 map.put(OperatorConstant.RETURN_FLAG, "菜单名称已经存在");
            	 return new ModelAndView("mgr/sys/menuEdit");
             }
             menu.setId(null);
             menu.setDaddDate(new Date());
             menu.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
             menu.setSmenuNo(IdFactory.getOutRandomSerialNumber());
             // 先设置父节点不是叶子节点
             Menu menu1 = menuService.findById(menu.getSparentId());
             if (menu1 != null && menu1.getBisLeaf().equals("1")) {
                 menu1.setBisLeaf(0);
                 menuService.update(menu1);
             }
             // 设置节点
             menu.setBisLeaf(1);
             menu.setBisRoot(0);
             menu.setBisDisplay(1);
             menuService.save(menu);
         } else {// 修改
             Menu presisMenu = menuService.findById(menu.getId());
             if (presisMenu != null) {
                 if (!presisMenu.getSname().equals(menu.getSname())) {
                     if (isExistName(menu.getSname())) {
                    	 map.put(OperatorConstant.RETURN_FLAG, "菜单名称已经存在");
                    	 return new ModelAndView("mgr/sys/menuEdit");
                     }
                 }
             }
             menu.setSparentId(presisMenu.getSparentId());
             menuService.update(menu);
         }
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 private boolean isExistName(String name){
		 Query query = new Query(Criteria.where("sname").is(name));
		 List<Menu> list = menuService.find(query);
		 if (list != null && list.size() > 0){
			 return true;
		 }
		 return false;
	 }
	 
	 /**
	  * 获取树菜单
	  * @return
	  */
	 @RequestMapping("/getMenuTree")
	 public ModelAndView getMenuTree(ModelMap map){
		 Query query = new Query(Criteria.where("smenuNo").is("1611011622455540"));
		 List<Menu> list = menuService.find(query);
		 map.put("rootInfo", list.get(0));
		 Query treequery = new Query(Criteria.where("smenuNo").ne("1611011622455540"));
		 List<Menu> treeInfo = menuService.find(treequery);
		 map.put("treeInfo", treeInfo);
		return new ModelAndView("mgr/popCommonTree");
		 
	 }
	 
	 /**
	  * 删除菜单
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 Query query = new Query(Criteria.where("id").is(id));
		 menuService.remove(query);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
