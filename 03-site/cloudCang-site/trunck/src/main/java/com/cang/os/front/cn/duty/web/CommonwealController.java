/**
 * 
 */
package com.cang.os.front.cn.duty.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.mgr.wz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/duty")
public class CommonwealController {
	
	@Autowired
    ArticleService articleService;
	
	private static final String COMMONWEAL_NAV_ID="583d20074d7c9de4c8c19660";
	
	 @RequestMapping("/commonweal")
     public String commonweal(ModelMap map,ParamPage paramPage) {
		
		 Query topQuquery = new Query(Criteria.where("snavigationId").is(COMMONWEAL_NAV_ID));
		 topQuquery.addCriteria(Criteria.where("istatus").is(20));
		 topQuquery.addCriteria(Criteria.where("idelFlag").is(0));
		 topQuquery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		 topQuquery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		 topQuquery.addCriteria(Criteria.where("iistopic").is(1));
		 topQuquery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		 List<Article> tops = articleService.find(topQuquery);
		 Article topArt = null;
		 if (null != tops && tops.size() > 0){
			 topArt = tops.get(0);
			 map.put("topArt", topArt);
		 }
		 
		 //党建新闻
		 Page<Article> page = new Page<Article>();
		 paramPage.setLimit(6);
		 page.setPageNum(paramPage.pageNo());
		 page.setPageSize(paramPage.getLimit());
		
		 Query articleQuery = new Query(Criteria.where("snavigationId").is(COMMONWEAL_NAV_ID));
		 if (null != topArt){
			 articleQuery.addCriteria(Criteria.where("id").ne(topArt.getId()));
		 }
		 articleQuery.addCriteria(Criteria.where("istatus").is(20));
		 articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		 articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		 articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		 articleQuery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "tpublishDate")));
		 page=articleService.findPage(page, articleQuery) ;
		 map.put("page", page);
		 return "front/cn/duty/commonweal";
	 }
	 


}
