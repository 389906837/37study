/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import java.util.Date;

import com.cang.os.common.core.Page;
import com.cang.os.mgr.wz.service.AdvertisService;
import com.cang.os.bean.wz.Advertis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 荣誉资质
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class HonorsController {
	
	@Autowired
    AdvertisService advertisService;
	
	/**
	 * 荣誉Banner ID
	 */
	private static final String  HONOR_REGION_ID="583e702d4d7c788226f28c58";
	
	 @RequestMapping("/honors")
    public String honors(ModelMap map) {
		 Page<Advertis> page = new Page<Advertis>();
		 page.setPageNum(0);
		 page.setPageSize(1000);
		 Query bannerQuery = new Query(Criteria.where("sregionId").is(HONOR_REGION_ID));
		 bannerQuery.addCriteria(Criteria.where("istatus").is(1));
		 bannerQuery.addCriteria(Criteria.where("tendDate").gte(new Date()));
		 bannerQuery.addCriteria(Criteria.where("tstartDate").lt(new Date()));
		 bannerQuery.with(new Sort(Direction.DESC, "iisDefault").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "taddTime")));
		 page = advertisService.findPage(page, bannerQuery);
		 map.put("page", page);
		 return "front/cn/zy/honors";
	 }


}
