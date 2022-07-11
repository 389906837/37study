/**
 * 
 */
package com.cang.os.mgr.wz.web;

import java.util.List;

import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.bean.wz.Advertis;
import com.cang.os.bean.wz.Region;
import com.cang.os.mgr.wz.service.AdvertisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.mgr.wz.service.RegionService;
import com.cang.os.mgr.wz.vo.AdvertisVo;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr/ad")
public class AdvertisController {
	
	@Autowired
    AdvertisService advertisService;
	
	@Autowired
	RegionService regionService;
	
	/**
	 * 广告列表
	 * @param advertisVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(AdvertisVo advertisVo, ParamPage paramPage, ModelMap map) {
		 
		    Page<Advertis> page = new Page<Advertis>();
			page.setPageNum(paramPage.pageNo());
			page.setPageSize(paramPage.getLimit());
			
			Query query = new Query();
			if (StringUtils.isNotBlank(advertisVo.getStitle())) {
				query.addCriteria(Criteria.where("stitle").regex(MongoDbUtil.getLikeQueryCondition(advertisVo.getStitle())));
			
			}
			
			if (StringUtils.isNotBlank(advertisVo.getSregionId())) {
				query.addCriteria(Criteria.where("sregionId").regex(MongoDbUtil.getLikeQueryCondition(advertisVo.getSregionId())));
			
			}
			
			if (null != advertisVo.getIstatus()) {
				query.addCriteria(Criteria.where("istatus").is(advertisVo.getIstatus()));
			}
			
			if (null != advertisVo.getIlinkType()) {
				query.addCriteria(Criteria.where("ilinkType").is(advertisVo.getIlinkType()));
			}
			
			if (null != advertisVo.getIisDefault()) {
				query.addCriteria(Criteria.where("iisDefault").is(advertisVo.getIisDefault()));
			}
			
			if (null != advertisVo.getStartDate() &&  null ==advertisVo.getEndDate()){
				
				query.addCriteria(Criteria.where("tendDate").gte(advertisVo.getStartDate()));
			}
			
			if (null != advertisVo.getEndDate() && null == advertisVo.getStartDate()){
				
				query.addCriteria(Criteria.where("tendDate").lt(DateUtils.addDays(advertisVo.getEndDate(), 1)));
			}
			
			if (null != advertisVo.getStartDate() &&  null !=advertisVo.getEndDate()){
				query.addCriteria(Criteria.where("tendDate").gte(advertisVo.getStartDate()).lt(DateUtils.addDays(advertisVo.getEndDate(), 1)));
				
			}
			query.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC,"tstartDate")));
			page=advertisService.findPage(page, query) ;
			map.put("page", page);
			map.put("regions", getRegions());
	    	return "mgr/wz/adList";
	 }
	 
	 
	 private List<Region> getRegions(){
		 return regionService.findAll();
		 
	 }
	 
	 
	 /**
	  * 编辑广告管理
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Advertis advertis = advertisService.findById(id);
			 map.put("advertis", advertis);
			 
		 }
		 map.put("regions", getRegions());
		 return new ModelAndView("mgr/wz/adEdit");
	 }
	 
	 
	 /**
	  * 保存广告位
	  * @param id
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,@RequestParam(value = "icoFile", required = false) MultipartFile icoFile,Advertis advertis,ModelMap map,String backUri){
		 if(advertis.getTstartDate() !=null && advertis.getTendDate()!=null){
	    		if(DateUtils.compareDate(advertis.getTstartDate(), advertis.getTendDate())==1){
	    			 map.put(OperatorConstant.RETURN_FLAG, "开始日期不能大于结束日期！");
	    			 map.put("regions", getRegions());
	    			 return new ModelAndView("mgr/wz/adEdit");
	    		
	    		}
	    	}
	    	advertisService.save( imgFile,icoFile,advertis);
	  
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 /**
	  * 删除广告
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 advertisService.removeById(id);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
