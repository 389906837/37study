/**
 * 
 */
package com.cang.os.mgr.sys.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cang.os.bean.sys.Menu;
import com.cang.os.bean.sys.Purview;
import com.cang.os.bean.sys.Role;
import com.cang.os.bean.sys.Rolepurview;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.JsonUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.mgr.sys.service.MenuService;
import com.cang.os.mgr.sys.service.PurviewService;
import com.cang.os.mgr.sys.service.RoleService;
import com.cang.os.mgr.sys.service.RolepurviewService;
import com.cang.os.mgr.sys.vo.TreeDataVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;

/**
 * 角色管理
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr/role")
public class RoleController {
	
	@Autowired
    RoleService roleService;
	
	@Autowired
    MenuService menuService;
	
	@Autowired
    PurviewService purviewService;
	
	@Autowired
    RolepurviewService rolepurviewService;
	
	 @RequestMapping("/list")
     public String list(Role role, ParamPage paramPage, ModelMap map) {
	    Page<Role> page = new Page<Role>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(role.getSroleName())) {
			query.addCriteria(Criteria.where("sroleName").regex(MongoDbUtil.getLikeQueryCondition(role.getSroleName())));
		}
		
		page=roleService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/sys/roleList";
    }
	 
	 @RequestMapping("/edit")
	 public String edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Role role = roleService.findById(id);
			 map.put("role", role);
			 
		 }
		 return "mgr/sys/roleEdit";
	 }
	 
	 @RequestMapping("/save")
	 public ModelAndView save(Role role,ModelMap map,String backUri){
		 
		 if (StringUtils.isBlank(role.getId())) {
             if (isExistName(role.getSroleName())) {
            	 map.put(OperatorConstant.RETURN_FLAG, "角色名称已经存在");
            	 return new ModelAndView("mgr/sys/roleEdit");
             }
             role.setId(null);
             roleService.insert(role);
             
		 }else{
			 Role presisRole = roleService.findById(role.getId());
			 if (!role.getSroleName().equals(presisRole.getSroleName())){
				 if (isExistName(role.getSroleName())) {
	            	 map.put(OperatorConstant.RETURN_FLAG, "角色名称已经存在");
	            	 return new ModelAndView("mgr/sys/roleEdit");
	             }
			 }
			 
			 roleService.update(role);
			 
		 }
		 
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 private boolean isExistName(String name){
		 Query query = new Query(Criteria.where("sroleName").is(name));
		 List<Role> list = roleService.find(query);
		 if (list != null && list.size() > 0){
			 return true;
		 }
		 return false;
	 }
	 
	 
	 /**
	  * 删除角色
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 roleService.deleteRole(id);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 
    @RequestMapping("/saveRolePurview")
    public ModelAndView saveRolePurview(String checkPurviewId,String roleId,ModelMap map,String backUri) {
    	if (StringUtils.isBlank(roleId))
        {
        	map.put(OperatorConstant.RETURN_FLAG, "数据异常，保存失败");
            return  setPurview(roleId,map);
        }
    	rolepurviewService.saveRolePurview(checkPurviewId, roleId);
    	
    	 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
    }
	 
	 /**
	  * 分配权限
	  * @param id
	  * @return
	  */
	 @RequestMapping("/setPurview")
	 public ModelAndView setPurview(String id,ModelMap map){
		 Role role = roleService.findById(id);
		 map.put("role", role);
		 List<TreeDataVo> trees = new ArrayList<TreeDataVo>();
		 Query rolepurviewQuery = new Query(Criteria.where("sroleId").is(id));
		 List<Rolepurview>  rolepurviewList = rolepurviewService.find(rolepurviewQuery);
		 //查询所有菜单权限
	     List<Menu> menuList =  menuService.findAll();
	     for (Menu menu:menuList){
	    	 TreeDataVo menuTree = new TreeDataVo();
	    	 menuTree.setChecked(false);
	    	 menuTree.setId(menu.getId());
	    	 menuTree.setpId(StringUtils.isBlank(menu.getSparentId())?"0":menu.getSparentId() );
	    	 menuTree.setOpen(true);
	    	 menuTree.setIsLeaf(0);
	    	 menuTree.setName(menu.getSname());
	    	 trees.add(menuTree);
	    	 Query query = new Query(Criteria.where("sparentId").is(menu.getId()));
	    	 List<Purview> purList = purviewService.find(query);
	    	 for (Purview pur :purList) {
	    		 TreeDataVo purTree = new TreeDataVo();
	    		 purTree.setChecked(false);
	    		 purTree.setId(pur.getId());
	    		 
	    		 purTree.setChecked(false);
	    		 purTree.setIsLeaf(1);
	    		 purTree.setName(pur.getSpurName());
	    		 purTree.setOpen(true);
	    		 purTree.setpId(pur.getSparentId());
	    		 if (rolepurviewList != null)
	             {
	                 Iterator<Rolepurview> it = rolepurviewList.iterator();
	                 while(it.hasNext())
	                 {
	                     Rolepurview rolepurview = it.next();
	                     if (rolepurview.getSpurviewId().equals(pur.getId()))
	                     {
	                    	 purTree.setChecked(true);
	                         break;
	                     }
	                 }
	             }
	    		 trees.add(purTree);
	    	 }
	    	 
	     }
	     
	    map.put("trees", JsonUtils.toJson(trees));
		return new ModelAndView("mgr/sys/setPurview");
	 }

}
