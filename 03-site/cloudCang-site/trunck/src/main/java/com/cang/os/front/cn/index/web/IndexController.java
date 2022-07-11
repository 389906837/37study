/**
 *
 */
package com.cang.os.front.cn.index.web;

import java.util.Date;
import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.mgr.wz.service.AdvertisService;
import com.cang.os.bean.wz.Advertis;
import com.cang.os.bean.wz.Album;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.AlbumService;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoAdmin;
import org.springframework.data.mongodb.core.MongoDbUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页
 * @author hunter
 *
 */
@Controller
@RequestMapping("/cn")
public class IndexController {

	/**
	 * 首页Banner ID
	 */
	private static final String BANNER_REGION_ID="5812ee374d7c4c060dd1c98b";

	/**
	 * 叁拾柒号仓新闻媒体报道
	 */
	private static final String ARTICLES="5c3d36d6b9e727cfcf4f6c95";
	/**
	 *  37cang文章导航
	 */
	private static final String ZY_CULTURE_NAV_ID="5c3d36a4b9e727cfcf4f6c93";

	/**
	 * 37cang文章关于我们
	 */
	private static final String ZY_CULTURE_OBOUTUS_ID="5c3d36c4b9e727cfcf4f6c94";



	@Autowired
    AdvertisService advertisService;

	@Autowired
    ArticleService articleService;

	@Autowired
    ArticlecontentService articlecontentService;

	@Autowired
    AlbumService albumService;


	 @RequestMapping("/index")
     public String index(ModelMap map) {


		 //获取首页文章
		 List<Article> arts =getArticles();
		 map.put("arts", arts);


		 //关于我们文章
		 List<Article> artss =getArticless();
		 List<Article> artsss =getArticless37();
		 map.put("artsss", artsss);
		 map.put("artss", artss);


		 return "front/cn/index/index";
	 }


	//二级跳转
	@RequestMapping(value="/skip/{id}")
	public String skip(HttpServletRequest request,ModelMap map,@PathVariable String id)
	{
		//获取首页文章
		List<Article> arts =getArticles();
		map.put("arts", arts);



		String target =request.getParameter("parameter");
//		List<Article> ar =getArt(target);
		map.put("target", target);

		Article article = articleService.findById(id);
		map.put("article", article);
		Query contentQuery = new Query(Criteria.where("sarticleId").is(id));
		List<Articlecontent> contents = articlecontentService.find(contentQuery);
		if (null != contents && contents.size() > 0){
			map.put("content2", contents.get(0));
		}


		return "front/cn/index/product";
	}


	/**
	 * 37cang 视图转换
	 * @param
	 * @return
	 */

	private List<Article> getArt(String name){
		Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_CULTURE_NAV_ID));
		articleQuery.addCriteria(Criteria.where("stitle").is(name));
		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> ar = articleService.find(articleQuery);



		return ar;
	}



	/**
	 * 37cang 导航栏文章
	 * @param
	 * @return
	 */

	private List<Article> getArticles(){
		Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_CULTURE_NAV_ID));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));

		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> artss = articleService.find(articleQuery);



		return artss;
	}

	/**
	 * 37cang 获取关于我们的文章和媒体报道的文章
	 * @param
	 * @return
	 */

	private List<Article> getArticless37(){
		Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_CULTURE_OBOUTUS_ID));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));

		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> arts = articleService.find(articleQuery);



		return arts;
	}
	private List<Article> getArticless(){
		Query articleQuery = new Query(Criteria.where("snavigationId").is(ARTICLES));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));

		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> artss = articleService.find(articleQuery);



		return artss;
	}
	/**
	 * 37cang获取相册
	 * @return
	 */
	private List<Album> getAlbum() {
		Query query = new Query();
		query.addCriteria(Criteria.where("isdispaly").is(1));
		query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "taddTime")));
		return albumService.find(query);
	}

/*	 *//**
	  * 获取首页Banner
	  * @return
	  *//*
	private List<Advertis> getIndexBanner() {
		Query bannerQuery = new Query(Criteria.where("sregionId").is(BANNER_REGION_ID));
		 bannerQuery.addCriteria(Criteria.where("istatus").is(1));
		 bannerQuery.addCriteria(Criteria.where("tendDate").gte(new Date()));
		 bannerQuery.addCriteria(Criteria.where("tstartDate").lt(new Date()));
		 bannerQuery.with(new Sort(Direction.DESC, "iisDefault").and(new Sort(Direction.ASC,"isort")).and(new Sort(Direction.DESC, "taddTime")));
		 List<Advertis> ads = advertisService.find(bannerQuery);
		return ads;
	}

	*//**
	 * 获取首页推荐新闻
	 * @return
	 *//*
	private List<Article> getIndexArticle() {
		Query articleQuery = new Query();
		articleQuery.addCriteria(Criteria.where("snavigationId").in(NewsConstant.getIdNews()));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));
		articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		articleQuery.addCriteria(Criteria.where("iisRecommend").is(1));
		articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> arts = articleService.find(articleQuery);
		return arts;
	}

	*//**
	 * 获取首页子雨文化
	 * @return
	 *//*
	private List<Article> getZYculture() {
		Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_CULTURE_NAV_ID));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));
		articleQuery.addCriteria(Criteria.where("idelFlag").is(0));
		articleQuery.addCriteria(Criteria.where("tvalidDate").gte(new Date()));
		articleQuery.addCriteria(Criteria.where("tpublishDate").lt(new Date()));
		articleQuery.with(new Sort(Direction.ASC,"isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Article> arts = articleService.find(articleQuery);
		return arts;
	}

	*//**
	 * 子雨集团文化内容
	 * @param articleId
	 * @return
	 *//*
	private List<Articlecontent> getZYcultureContent(String articleId ){
		Query articleQuery = new Query(Criteria.where("sarticleId").is(articleId));
		List<Articlecontent> contents = articlecontentService.find(articleQuery);
		return contents;
	}

	*//**
	 * 获取员工风采
	 * @return
	 *//*
	private List<Album> getEmployeeCulture() {
		  Query query = new Query();
		  query.addCriteria(Criteria.where("isdispaly").is(1));
		  query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "taddTime")));
		  return albumService.find(query);
	}*/

}
