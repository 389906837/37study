/**
 * 
 */
package com.cang.os.front.cn.zy.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.mgr.wz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cang.os.mgr.wz.service.ArticlecontentService;

/**
 * 发展历程
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/zy")
public class DevelopingProcessController {
	
	/**
	 * 发展历程
	 */
	private static final String ZY_DEV_PROCESS_NAV_ID="5844d5184d7c30e067ffa606";
	
	@Autowired
	ArticlecontentService articlecontentService;
	
	@Autowired
    ArticleService articleService;
	
	/**
	 * 发展历程
	 */
	 @RequestMapping("/devProcess")
     public String devProcess(ModelMap map) {
		 map.put("contents", getZYcultureContent());
		 return "front/cn/zy/devProcess";
	 }
	 
		/**
		 * 子雨集团文化内容
		 * @param articleId
		 * @return
		 */
		private Articlecontent getZYcultureContent(){
			Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_DEV_PROCESS_NAV_ID));
			articleQuery.addCriteria(Criteria.where("istatus").is(20));
			articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
			articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
			articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
			articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
			List<Article> list = articleService.find(articleQuery);
			if (null != list && list.size() > 0) {
				Query contentQuery = new Query(Criteria.where("sarticleId").is(list.get(0).getId()));
				List<Articlecontent> contents = articlecontentService.find(contentQuery);
				if (null != contents && contents.size() > 0){
					return contents.get(0);
				}
				
			}
			
			return null;
		}
	


}
