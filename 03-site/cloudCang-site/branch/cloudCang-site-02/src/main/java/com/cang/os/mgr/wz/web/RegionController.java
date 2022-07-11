/**
 * 
 */
package com.cang.os.mgr.wz.web;

import com.cang.os.bean.wz.Region;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.wz.service.RegionService;
import com.cang.os.security.utils.SessionUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/mgr/region")
public class RegionController {
	
	@Autowired
    RegionService regionService;
	
	
	/**
	 * 用户管理列表
	 * @param operatorVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(Region region, ParamPage paramPage, ModelMap map) {
	    Page<Region> page = new Page<Region>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		
		Query query = new Query();
		if (StringUtils.isNotBlank(region.getScode())) {
			query.addCriteria(Criteria.where("scode").regex(MongoDbUtil.getLikeQueryCondition(region.getScode())));
		
		}
		
		if (StringUtils.isNotBlank(region.getSregionName())) {
			query.addCriteria(Criteria.where("sregionName").regex(MongoDbUtil.getLikeQueryCondition(region.getSregionName())));
		}
		
		page=regionService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/wz/regionList";
    }
	 
	 
	 
	 /**
	  * 编辑运营位
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Region region = regionService.findById(id);
			 map.put("region", region);
			 
		 }
		 return new ModelAndView("mgr/wz/regionEdit");
	 }
	 
	 
	 /**
	  * 编辑运营位
	  * @param id
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(Region region,ModelMap map,String backUri){
		 // 如果id为空就就添加
	        if (StringUtils.isBlank(region.getId())) {
	            region.setScode(IdFactory.getOutRandomSerialNumber());
	            region.setTaddTime(DateUtils.getCurrentDateTime());
				region.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				region.setTupdateTime(DateUtils.getCurrentDateTime());
	            region.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				region.setIdelFlag(0);
				region.setId(null);
	            regionService.insert(region);
	        } else {
	            // 修改
	          	region.setTupdateTime(DateUtils.getCurrentDateTime());
	            region.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
				region.setIdelFlag(0);
	            regionService.update(region);
	        }
	  
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 /**
	  * 编辑运营位
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 Query query = new Query(Criteria.where("id").is(id));
		 regionService.remove(query);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
