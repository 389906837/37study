/**
 * 
 */
package com.cang.os.front.cn.news.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
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
@RequestMapping("/cn/news")
public class NewsController {
	
	@Autowired
    ArticlecontentService articlecontentService;
	
	@Autowired
    ArticleService articleService;
	
	private static final String GROUP_NAV_ID="583e8a744d7c4c22804fb4b4";
	
	private static final String INDUSTRY_NAV_ID="583f8aca4d7cbc8b9e8cfc6c";
	private static final String LAWS_NAV_ID="583f8aeb4d7cbc8b9e8cfc6d";
	
	 @RequestMapping("/detail/{id}")
    public String detail(@PathVariable String id, ModelMap map) {
		Article article = articleService.findById(id);
		Query articleQuery = new Query(Criteria.where("sarticleId").is(id));
		List<Articlecontent> cons = articlecontentService.find(articleQuery);
		if (null != cons && cons.size() > 0){
			Articlecontent content =  cons.get(0);
		
			
			
			//查找上一篇文章ID
			 Page<Article> page = new Page<Article>();
			 page.setPageNum(0);
			 page.setPageSize(1);
			 Query prequery = new Query(Criteria.where("snavigationId").is(article.getSnavigationId()));
			 prequery.addCriteria(Criteria.where("istatus").is(20));
			 prequery.addCriteria(Criteria.where("idelFlag").is(0));
			 prequery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
			 prequery.addCriteria(Criteria.where("isort").gt(article.getIsort()));
			 prequery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "tpublishDate")));
			 page=articleService.findPage(page, prequery) ;
			 if (page != null){
				 List<Article> list = page.getResults();
				 if (null != list && list.size() > 0){
					 map.put("pre", list.get(0).getId());
				 }
			 }
			 
			//查找下一篇文章ID
			 Page<Article> nextPage = new Page<Article>();
			 nextPage.setPageNum(0);
			 nextPage.setPageSize(1);
			 Query nextQuery = new Query(Criteria.where("snavigationId").is(article.getSnavigationId()));
			 nextQuery.addCriteria(Criteria.where("istatus").is(20));
			 nextQuery.addCriteria(Criteria.where("idelFlag").is(0));
			 nextQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
			 nextQuery.addCriteria(Criteria.where("isort").lt(article.getIsort()));
			 nextQuery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.DESC,"isort")).and(new Sort(Direction.ASC, "tpublishDate")));
			 nextPage=articleService.findPage(nextPage, nextQuery) ;
			 if (nextPage != null){
				 List<Article> list = nextPage.getResults();
				 if (null != list && list.size() > 0){
					 Article a = list.get(0);
					 if (a.getTpublishDate().getTime() < new Date().getTime()){
						 map.put("next", a.getId());
					 }
					 
					
				 }
			 }
			map.put("content", content);
		}
		map.put("article", article);
		 return "front/cn/news/detail";
	 }
	 
	 /**
	  * 集团新闻
	  * @param map
	  * @param paramPage
	  * @return
	  */
	 @RequestMapping("/group")
     public String group(ModelMap map,ParamPage paramPage) {
		 //集团新闻
		 Page<Article> page = new Page<Article>();
		 paramPage.setLimit(6);
		 page.setPageNum(paramPage.pageNo());
		 page.setPageSize(paramPage.getLimit());
		
		 Query articleQuery = new Query(Criteria.where("snavigationId").is(GROUP_NAV_ID));
		 articleQuery.addCriteria(Criteria.where("istatus").is(20));
		 articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		 articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		 articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		 articleQuery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "tpublishDate")));
		 page=articleService.findPage(page, articleQuery) ;
		 map.put("page", page);
		 return "front/cn/news/group";
		 
	 }
	 
	 /**
	  * 行业动态新闻
	  * @param map
	  * @param paramPage
	  * @return
	  */
	 @RequestMapping("/industry")
     public String industry(ModelMap map,ParamPage paramPage) {
		 //集团新闻
		 Page<Article> page = new Page<Article>();
		 paramPage.setLimit(6);
		 page.setPageNum(paramPage.pageNo());
		 page.setPageSize(paramPage.getLimit());
		
		 Query articleQuery = new Query(Criteria.where("snavigationId").is(INDUSTRY_NAV_ID));
		 articleQuery.addCriteria(Criteria.where("istatus").is(20));
		 articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		 articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		 articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		 articleQuery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "tpublishDate")));
		 page=articleService.findPage(page, articleQuery) ;
		 map.put("page", page);
		 return "front/cn/news/industry";
		 
	 }
	 
	 
	 /**
	  * 政策法规新闻
	  * @param map
	  * @param paramPage
	  * @return
	  */
	 @RequestMapping("/laws")
     public String laws(ModelMap map,ParamPage paramPage) {
		 //集团新闻
		 Page<Article> page = new Page<Article>();
		 paramPage.setLimit(6);
		 page.setPageNum(paramPage.pageNo());
		 page.setPageSize(paramPage.getLimit());
		
		 Query articleQuery = new Query(Criteria.where("snavigationId").is(LAWS_NAV_ID));
		 articleQuery.addCriteria(Criteria.where("istatus").is(20));
		 articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		 articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		 articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		 articleQuery.with(new Sort(Direction.DESC,"iisRecommend").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "tpublishDate")));
		 page=articleService.findPage(page, articleQuery) ;
		 map.put("page", page);
		 return "front/cn/news/laws";
		 
	 }

}
