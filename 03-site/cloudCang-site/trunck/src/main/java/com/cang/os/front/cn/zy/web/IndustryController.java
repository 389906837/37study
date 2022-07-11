/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class IndustryController {
	
	@Autowired
    ArticleService articleService;
	
	@Autowired
    ArticlecontentService articlecontentService;
	
	private static final String INDUSTRY_NAV_ID="5846204f6fd62fe8eb0c922d";
	

	/**
	 * 子雨产业
	 * @param map
	 * @return
	 */
   @RequestMapping("/industry")
   public String industry(ModelMap map) {
	   
	    Query articleQuery = new Query();
		articleQuery.addCriteria(Criteria.where("snavigationId").is(INDUSTRY_NAV_ID));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));
		articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		articleQuery.with(new Sort(Direction.ASC,"isort"));
		List<Article> arts = articleService.find(articleQuery);
		map.put("arts", arts);
		return "front/cn/zy/industry";
   }
   
   
	/**
	 * 子雨产业详情
	 * @param map
	 * @return
	 */
  @RequestMapping("/industry/detail/{id}")
  public String detail(@PathVariable String id,ModelMap map) {
	  
	    Article article = articleService.findById(id);
	    map.put("article", article);
	    Query contentQuery = new Query(Criteria.where("sarticleId").is(id));
		List<Articlecontent> contents = articlecontentService.find(contentQuery);
		if (null != contents && contents.size() > 0){
			map.put("content", contents.get(0));
		}
	    return "front/cn/zy/industryDetail";
  }

}
