/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cang.os.bean.wz.Advertis;
import com.cang.os.mgr.wz.service.AdvertisService;

/**
 * 董事长致辞
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class StatementsController {
	
	@Autowired
	AdvertisService  advertisService;
	
	/**
	 * 荣誉Banner ID
	 */
	private static final String  HONOR_REGION_ID="583d3c384d7cb6e608d6c03a";

	
	 @RequestMapping("/statements")
    public String statements(ModelMap map) {
		 //banner
		 List<Advertis> ads = getIndexBanner();
		 map.put("ads", ads);
		 return "front/cn/zy/statements";
	 }
	 
	 
	 /**
	  * 获取荣誉Banner
	  * @return
	  */
	private List<Advertis> getIndexBanner() {
		 Query bannerQuery = new Query(Criteria.where("sregionId").is(HONOR_REGION_ID));
		 bannerQuery.addCriteria(Criteria.where("istatus").is(1));
		 bannerQuery.addCriteria(Criteria.where("tendDate").gte(new Date()));
		 bannerQuery.addCriteria(Criteria.where("tstartDate").lt(new Date()));
		 bannerQuery.with(new Sort(Direction.DESC, "iisDefault").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "taddTime")));
		 List<Advertis> ads = advertisService.find(bannerQuery);
		return ads;
	}

}
