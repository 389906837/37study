/**
 * 
 */
package com.cang.os.front.cn.hr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.front.cn.hr.vo.ArticleVo;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;

/**
 * 人力资源
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn/hr")
public class HrController {
	
	private static final String JOINUS_NAV_ID="584a2c7e6fd6f42e09903e2a";
	
	@Autowired
	ArticlecontentService articlecontentService;
	
	@Autowired
	ArticleService articleService;
	
	/**
	 * 加入我们
	 * @param map
	 * @return
	 */
	 @RequestMapping("/joinus")
     public String joinus(ModelMap map) {
		 
	 Query articleQuery = new Query(Criteria.where("snavigationId").is(JOINUS_NAV_ID));
	 articleQuery.addCriteria(Criteria.where("istatus").is(20));
	 articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
	 articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
	 articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
	 articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
	 
	 List<Article> articles = articleService.find(articleQuery);
	 List<ArticleVo> vos = new ArrayList<ArticleVo>();
	 for (Article art : articles) {
		 ArticleVo articleVo = new ArticleVo();
		 try {
			BeanUtils.copyProperties(articleVo, art);
		}catch (Exception e) {
			e.printStackTrace();
		}
		Query contentQuery = new Query(Criteria.where("sarticleId").is(art.getId()));
		List<Articlecontent> cons = articlecontentService.find(contentQuery);
		if (null != cons && cons.size() > 0){
			Articlecontent content =  cons.get(0);
			articleVo.setContents(content.getScontent());
			
		}
		vos.add(articleVo);
	  }
	   map.put("vos", vos);	 
	   return "front/cn/hr/joinus"; 
	 }
	 
	/**
	 * 人才战略
	 * @param map
	 * @return
	 */
	 @RequestMapping("/education")
     public String education(ModelMap map) {
		 
		 return "front/cn/hr/education"; 
	 }
	 
	 /**
		 * 员工福利
		 * @param map
		 * @return
		 */
		 @RequestMapping("/employee")
	     public String employee(ModelMap map) {
			 
			 return "front/cn/hr/employee"; 
		 }

}
