/**
 * 
 */
package com.cang.os.mgr.wz.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.bean.wz.Navigation;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import com.cang.os.mgr.wz.service.NavigationService;
import com.cang.os.mgr.wz.vo.ArticleVo;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;

/**
 * 新闻管理
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/mgr/article")
public class ArticleController {
	
	@Autowired
    ArticleService articleService;
	
	@Autowired
    NavigationService navigationService;
	
	@Autowired
    ArticlecontentService articlecontentService;
	
	/**
	 * 新闻列表
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(ArticleVo articleVo, ParamPage paramPage, ModelMap map) {
	    Page<Article> page = new Page<Article>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(articleVo.getStitle())) {
			query.addCriteria(Criteria.where("stitle").regex(MongoDbUtil.getLikeQueryCondition(articleVo.getStitle())));
		}
		if (null != articleVo.getIstatus()){
			query.addCriteria(Criteria.where("istatus").is(articleVo.getIstatus()));
		}
		
		if (null != articleVo.getIistopic()){
			query.addCriteria(Criteria.where("iistopic").is(articleVo.getIistopic()));
		}
		if (null != articleVo.getIisRecommend()){
			query.addCriteria(Criteria.where("iisRecommend").is(articleVo.getIisRecommend()));
		}
		
		if (StringUtils.isNotBlank(articleVo.getSnavigationId())){
			query.addCriteria(Criteria.where("snavigationId").regex(articleVo.getSnavigationId()));
		}
		
		if (null != articleVo.getStartDate() &&  null ==articleVo.getEndDate()){
			query.addCriteria(Criteria.where("tpublishDate").gte(articleVo.getStartDate()));
		}
		
		if (null != articleVo.getEndDate() && null == articleVo.getStartDate()){
			query.addCriteria(Criteria.where("tpublishDate").lt(DateUtils.addDays(articleVo.getEndDate(), 1)));
		}
		
		if (null != articleVo.getStartDate() &&  null !=articleVo.getEndDate()){
			query.addCriteria(Criteria.where("tpublishDate").gte(articleVo.getStartDate()).lt(DateUtils.addDays(articleVo.getEndDate(), 1)));
		}
	
		query.with(new Sort(Direction.DESC,"isort").and(new Sort(Direction.DESC,"tpublishDate")));
		page=articleService.findPage(page, query) ;
		map.put("page", page);
		map.put("navs", getNavs());
    	return "mgr/wz/articleList";
    }
	 
	 private List<Navigation> getNavs(){
		 return navigationService.findAll();
	 }
	 
	 /**
	  * 编辑新闻
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Article article = articleService.findById(id);
			 map.put("article", article);
			 if (null !=article){
				 Query query = new Query(Criteria.where("sarticleId").is(id));
				 Articlecontent cont = articlecontentService.findOne(query);
				 if (cont != null){
					 map.put("scontent", cont.getScontent());
				 }
				
			 }
		 }
		 map.put("navs", getNavs());
		 return new ModelAndView("mgr/wz/articleEdit");
	 }
	 
	    /**
	     * 保存新闻
	     * @param article
	     * @param scontent
	     * @return
	     */
	    @RequestMapping("/save")
	    public ModelAndView save(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,@RequestParam(value = "imgFileIndex", required = false) MultipartFile imgFileIndex,Article article,String scontent,String backUri,ModelMap map)
	    {
	       
	        try {
	        	if(article.getTpublishDate() !=null && article.getTvalidDate()!=null){
		    		if(DateUtils.compareDate(article.getTpublishDate(), article.getTvalidDate())==1){
		    			 map.put(OperatorConstant.RETURN_FLAG, "开始日期不能大于结束日期！");
		    			 return edit(article.getId(),map);
		    		
		    		}
		    	}
	            articleService.save(imgFile,imgFileIndex, article, scontent);
	         
	            map.put("sRes", "save_suc");
	        } catch (Exception e) {
	        	 map.put("sRes", "系统异常，保存失败！");
	        	 return new ModelAndView("redirect:/mgr/error").addObject("backUri",backUri);
	        }
	        return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	        
	    }
	    
	    
	    /**
	     * 图片上传
	     */
	    @RequestMapping("/upload")
	    @ResponseBody
	    public Map<String,Object>  upload(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,String navicationId,HttpServletResponse response){
	        String url = articleService.upload(imgFile, navicationId);
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("error", 0);
	        map.put("url", url);
	        return map;
	       
	    }
	    
		 /**
		  * 删除文章
		  * @param id
		  * @return
		  */
		 @RequestMapping("/delete")
		 public ModelAndView delete(String id,String backUri) {
			 Query query = new Query(Criteria.where("id").is(id));
			 articleService.remove(query);
			 Query contentquery = new Query(Criteria.where("sarticleId").is(id));
			 articlecontentService.remove(contentquery);
			 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
		 }
		 
		 /**
		  * 发布文章
		  * @param id
		  * @return
		  */
		 @RequestMapping("/publish")
		 public ModelAndView publish(String id,String backUri) {
			 Article article = new Article();
			 article.setIstatus(20);
			 article.setId(id);
			 articleService.update(article);
			 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
		 }


	/**
	 * 撤销文章发布
	 * @param id
	 * @return
	 */
	@RequestMapping("/repealPublish")
	public ModelAndView repealPublish(String id,String backUri) {
		Article article = new Article();
		article.setIstatus(10);
		article.setId(id);
		articleService.update(article);
		return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	}


	 /**预览新闻**/
	@RequestMapping("/preview")
	 public String artPreview(String pid,String nid, ModelMap map) {
		//文章内容
		Query contentQuery1 = new Query(Criteria.where("isort").is(Integer.parseInt(pid)));
		contentQuery1.addCriteria(Criteria.where("snavigationId").is(nid));
		Article article = articleService.findOne(contentQuery1);

		if(null != article){
			Query contentQuery3 = new Query(Criteria.where("sarticleId").is(article.getId()));
			List<Articlecontent> contents = articlecontentService.find(contentQuery3);
			if (null != contents && contents.size() > 0){
				map.put("content", contents.get(0));
			}
		}
		map.put("article", article);
		return "front/cn/news/artPreview";
	}

}
