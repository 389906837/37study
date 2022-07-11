/**
 * 
 */
package com.cang.os.mgr.wz.web;

import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.wz.service.NavigationService;
import com.cang.os.security.utils.SessionUserUtils;
import com.cang.os.bean.wz.Navigation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 新闻栏目管理
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/mgr/nav")
public class NavigationController {
	
	@Autowired
	private NavigationService navigationService;
	
	
	
	/**
	 * 用户管理列表
	 * @param operatorVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(Navigation navigation, ParamPage paramPage, ModelMap map) {
	    Page<Navigation> page = new Page<Navigation>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		
		Query query = new Query();
		if (StringUtils.isNotBlank(navigation.getScode())) {
			query.addCriteria(Criteria.where("scode").regex(MongoDbUtil.getLikeQueryCondition(navigation.getScode())));
		
		}
		
		if (StringUtils.isNotBlank(navigation.getSname())) {
			query.addCriteria(Criteria.where("sname").regex(MongoDbUtil.getLikeQueryCondition(navigation.getSname())));
		}
		
		page=navigationService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/wz/navList";
    }
	 
	 
	 /**
	  * 编辑用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Navigation navigation = navigationService.findById(id);
			 map.put("nav", navigation);
			 
		 }
		 return new ModelAndView("mgr/wz/navEdit");
	 }
	 
	 /**
	  * 编辑用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(Navigation navigation,ModelMap map,String backUri){
	        // 如果id为空就就添加
	        if (StringUtils.isBlank(navigation.getId())) {
	            navigation.setScode(IdFactory.getOutRandomSerialNumber());
	            navigation.setTaddTime(DateUtils.getCurrentDateTime());
				navigation.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				navigation.setTupdateTime(DateUtils.getCurrentDateTime());
	            navigation.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
	            navigation.setIdelFlag(0);
	            navigation.setId(null);
	            navigationService.insert(navigation);
				
	        } else {
	            // 修改
	        	navigation.setTupdateTime(DateUtils.getCurrentDateTime());
	            navigation.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
	            navigation.setIdelFlag(0);
	            navigationService.update(navigation);
			
	        }
	  
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 /**
	  * 编辑用户
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 Query query = new Query(Criteria.where("id").is(id));
		 navigationService.remove(query);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
