/**
 * 
 */
package com.cang.os.mgr.sys.web;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cang.os.bean.sys.Operator;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.mgr.sys.service.OperatorService;
import com.cang.os.common.utils.MD5;
import com.cang.os.common.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.bean.sys.Operatorrole;
import com.cang.os.bean.sys.Role;
import com.cang.os.common.core.OperatorConstant;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.sys.service.OperatorroleService;
import com.cang.os.mgr.sys.service.RoleService;
import com.cang.os.mgr.sys.vo.OperatorVo;
import com.cang.os.security.utils.SessionUserUtils;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr/operator")
public class OperatorController {
	
	@Autowired
    OperatorService operatorService;
	
	@Autowired
	OperatorroleService operatorroleService;
	
	@Autowired
	RoleService roleService;
	
	/**
	 * 用户管理列表
	 * @param operatorVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(OperatorVo operatorVo, ParamPage paramPage, ModelMap map) {
	    Page<Operator> page = new Page<Operator>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(operatorVo.getSmobile())) {
			query.addCriteria(Criteria.where("smobile").regex(MongoDbUtil.getLikeQueryCondition(operatorVo.getSmobile())));
		}
		
		if (StringUtils.isNotBlank(operatorVo.getSuserName())) {
			query.addCriteria(Criteria.where("suserName").regex(MongoDbUtil.getLikeQueryCondition(operatorVo.getSuserName())));
		}
		
		if (StringUtils.isNotBlank(operatorVo.getSrealName())) {
			query.addCriteria(Criteria.where("srealName").regex(MongoDbUtil.getLikeQueryCondition(operatorVo.getSrealName())));
		}
		
		page=operatorService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/sys/operatorList";
    }
	 
	 @RequestMapping("/save")
     public String save(OperatorVo opt,ModelMap map,String backUri) {
		
         // 执行新增
         if (StringUtils.isBlank(opt.getId())) {
             if (!opt.getSpassword().equals(opt.getSpassword2())) {
            	 map.put(OperatorConstant.RETURN_FLAG, "密码与确认密码不相等！");
            	 map.put("opt", opt);
            	 return "mgr/sys/operatorEdit";
             }
             //判断用户名是否存在
             if (!checkUsername(opt.getSuserName()))
             {
            	 map.put(OperatorConstant.RETURN_FLAG, "用户名已经存在");
            	 map.put("opt", opt);
            	 return "mgr/sys/operatorEdit";
             }
             Operator operator = new Operator();
             BeanUtils.copyProperties(opt, operator);
          
             operator.setSoperatorNo(IdFactory.getOutRandomSerialNumber());
             operator.setDaddDate(DateUtils.getCurrentDateTime());
             operator.setSaddOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
             operator.setBisAdmin(0);
             operator.setSpyName(StringUtil.getFullSpell(operator.getSrealName()));
             operator.setSjpName(StringUtil.getFirstSpell(operator.getSrealName()));
             operator.setSpassword(MD5.encode(operator.getSpassword()));
             operator.setBisDelete(0);
             operator.setId(null);
             operatorService.insert(operator); 
           
         } else// 执行修改
         {
             Operator operator = operatorService.findById(opt.getId());
             ObjectUtils.copyObjValue(opt, operator);
             operator.setDmodifyDate(DateUtils.getCurrentDateTime());
             operator.setSmodifyOperator(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
             operator.setSpyName(StringUtil.getFullSpell(operator.getSrealName()));
             operator.setSjpName(StringUtil.getFirstSpell(operator.getSrealName()));
             operator.setSpassword(null);
             operatorService.update(operator);
            
         }
		 map.put("backUri", backUri);
		 return "mgr/success";
	 }
	 
	 /**
	  * 编辑用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public String edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Operator operator = operatorService.findById(id);
			 map.put("opt", operator);
			 
		 }
		 return "mgr/sys/operatorEdit";
	 }
	 
	 /**
	  * 修改用户密码
	  * @param id
	  * @return
	  */
	 @RequestMapping("/updatePassword")
	 public String toUpdatePassword(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Operator operator = operatorService.findById(id);
			 map.put("opt", operator);
			 
		 }
		 return "mgr/sys/updatePassword";
	 }
	 
	 /**
	  * 确认修改用户密码
	  * @param id
	  * @return
	  */
	 @RequestMapping("/confirmUpdatePassword")
	 public String confirmUpdatePassword(OperatorVo opt,ModelMap map,String backUri){
		 
		 if (StringUtils.isBlank(opt.getId())) {
			 map.put(OperatorConstant.RETURN_FLAG, "数据异常！");
			 return "mgr/success";
		 }
		 
		 if (!opt.getSpassword().equals(opt.getSpassword2())) {
        	 map.put(OperatorConstant.RETURN_FLAG, "密码与确认密码不相等！");
        	 map.put("opt", opt);
        	 return "mgr/sys/updatePassword";
         }
		 
	    Operator operator = new Operator();
		operator.setSpassword(MD5.encode(opt.getSpassword()));
		operator.setId(opt.getId());
		operator.setDmodifyDate(new Date());
        operatorService.update(operator);
		 map.put("backUri", backUri);
		 return "mgr/success";
	 }
	 
 /*
     * 返回String类型的结果
     * 检查用户名的合法性,如果用户已经存在，返回false，否则返回true(返回json数据，格式为{"valid",true})
     */
    @RequestMapping(value = "/checkNameExists", produces = "application/json;charset=UTF-8")
    public @ResponseBody
    Map<String, Boolean> checkNameExists(@RequestParam String suserName) {
       
       
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", checkUsername(suserName));
        
        return map;
    }
    
    private boolean checkUsername(String username){
    	 boolean result = false;
         Query query = new Query(Criteria.where("suserName").is(username));
         List<Operator> list = operatorService.find(query);
        if (null == list || list.isEmpty()){
     	   result = true;
    	
        	}
        return result;
    }
    
    @RequestMapping("/saveRolePurview")
    public ModelAndView saveRolePurview( String operatorId,String[] sselectrole,String [] snotselectrole,ModelMap map,String backUri) {
    	 operatorroleService.saveRolePurview(operatorId, sselectrole, snotselectrole);
    	 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
    	
    }
    
    /**
     * 设置用户角色
     * @param id
     * @param map
     * @return
     */
	 @RequestMapping("/setOperatorRole")
	 public ModelAndView setOperatorRole(String id,ModelMap map){
		 Operator operator = operatorService.findById(id);
		 map.put("operator", operator);
		 
		 List<Role> allRole = roleService.findAll();
		 Map<String,Operatorrole> operatorRoleMap = new HashMap<String,Operatorrole>();
	
		 Query query = new Query(Criteria.where("soperatorId").is(id));
		 List<Operatorrole> operatorroleList = operatorroleService.find(query);
		 if (null !=operatorroleList && !operatorroleList.isEmpty()){
			 for (Operatorrole operatorrole: operatorroleList ){
				 operatorRoleMap.put(operatorrole.getSroleId(), operatorrole);
				 
			 }
		 }
		 Set<Role> notHasRoles = new HashSet<Role>();
		 Set<Role> hasRoles = new HashSet<Role>();
		 for (Role role: allRole ){
			if (operatorRoleMap.get(role.getId()) != null){
				hasRoles.add(role);
			}else{
				notHasRoles.add(role);
			}
		 }
		 map.put("hasRoles", hasRoles);
		 map.put("notHasRoles", notHasRoles);
		 
		 return new ModelAndView("mgr/sys/setOperatorRole");
	 }
	 
	 /**
	  * 删除用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 operatorService.deleteOperator(id);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
