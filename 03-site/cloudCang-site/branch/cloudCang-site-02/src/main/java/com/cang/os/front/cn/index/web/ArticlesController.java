package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/news")
public class ArticlesController {


    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticlecontentService articlecontentService;
    @Autowired
    private IndexService indexService;



    /**媒体报道列表**/
    @RequestMapping("/media")
    public String mediaList() {
        return "redirect:/report.html?type=mediaReport";
    }
    /**公司动态列表*/
    @RequestMapping("/company")
    public String companyList() {
        return "redirect:/report.html?type=companyNews";
    }
    /**行业新闻列表*/
    @RequestMapping("/industry")
    public String industryList() {
        return "redirect:/report.html?type=industryNews";
    }


    /*
    * 公司动态详情
    * */
    @RequestMapping("/company/{id}")
    public String companyDetail(ModelMap map,@PathVariable String id) {
        map.put("type","company");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_COMPANY_NEWS, map);
    }


    /*
    * 媒体报道详情
    * */
    @RequestMapping("/media/{id}")
    public String mediaDetail(ModelMap map, @PathVariable String id) {
        map.put("type","media");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_MEDIA_REPORT, map);
    }

    /*
    * 行业新闻详情
    * */
    @RequestMapping("/industry/{id}")
    public String industryDetail(ModelMap map, @PathVariable String id) {
        map.put("type","industry");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_INDUSTRY_NEWS, map);
    }



    private String getArticleDetails(String url, String articleId, String navId, ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory();
        map.put("arts", arts);
        //媒体报道的文章内容
        Query contentQuery1 = new Query(Criteria.where("isort").is(Integer.parseInt(articleId)));
        contentQuery1.addCriteria(Criteria.where("snavigationId").is(navId));
        contentQuery1.addCriteria(Criteria.where("istatus").is(20));
        Article article = articleService.findOne(contentQuery1);
        if(null != article){
            Query contentQuery2 = new Query(Criteria.where("sarticleId").is(article.getId()));
            List<Articlecontent> contents = articlecontentService.find(contentQuery2);
            if (null != contents && contents.size() > 0){
                map.put("content", contents.get(0));
            } else {
                return "error/404";
            }
        } else {
            return "error/404";
        }
        map.put("article", article);
        return url;
    }

}
