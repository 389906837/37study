package com.cang.os.wap.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页
 *
 * @author yanlingfeng
 */
@Controller
@RequestMapping("/m")
public class WapIndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticlecontentService articlecontentService;
    @Autowired
    private IndexService indexService;

    @RequestMapping("/index")
    public String index(ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory();
        map.put("arts", arts);
        return "wap/index/index";
    }


    /**
     * 关于我们
     *
     * @param map
     * @return
     */
    @RequestMapping("/aboutUs")
    public String aboutUs(ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory();
        map.put("arts", arts);
        return "wap/index/aboutUs";

    }

    /**
     * 动态/报道
     *
     * @param map
     * @return
     */
    @RequestMapping("/dynamic")
    public String dynamic(ModelMap map, String type) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory();
        map.put("arts", arts);
        if (StringUtil.isBlank(type) || !(type.equals("companyNews") || type.equals("mediaReport") || type.equals("industryNews"))) {
            type = "companyNews";
        }
        //默认 显示公司动态
        List<Article> companyNews = indexService.getReportArticleList(NewsConstant.ARTICLES_COMPANY_NEWS);
        List<Article> mediaReport = indexService.getReportArticleList(NewsConstant.ARTICLES_MEDIA_REPORT);
        List<Article> industryNews = indexService.getReportArticleList(NewsConstant.ARTICLES_INDUSTRY_NEWS);
        map.put("type", type);
        map.put("companyNews", companyNews);
        map.put("mediaReport", mediaReport);
        map.put("industryNews", industryNews);
        return "wap/index/dynamic";
    }


    /**
     * 产品介绍
     *
     * @param map
     * @param code
     * @return
     */
    @RequestMapping("/{code}")
    public String articleSingle(ModelMap map, @PathVariable String code) {

        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory();
        map.put("arts", arts);

        Article article = null;
        if (StringUtil.isNotBlank(code) && code.equals("supportSystem")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                article = contents.get(0);
            }
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                article = contents.get(0);
            }
        } else if (StringUtil.isNotBlank(code) && code.equals("coreAlgorithm")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                article = contents.get(0);
            }
        } else {
            return "error/404";
        }
        map.put("article", article);
        if (null != article) {
            Query contentQuery = new Query(Criteria.where("sarticleId").is(article.getId()));
            List<Articlecontent> contents = articlecontentService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                map.put("content2", contents.get(0));
            }
        } else {
            return "error/404";
        }
        return "wap/index/product";
    }

    /**
     * 董事长致词
     *
     * @return
     */
    @RequestMapping("ziyuIndex")
    public String temp() {
        return "front/cn/index/ziyu";
    }


    /**
     * 公司动态详情
     *
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/company/{id}")
    public String companyDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "company");
        return getArticleDetails("wap/index/articleDetail", id, NewsConstant.ARTICLES_COMPANY_NEWS, map);
    }


    /**
     * 媒体报道详情
     *
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/media/{id}")
    public String mediaDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "media");
        return getArticleDetails("wap/index/articleDetail", id, NewsConstant.ARTICLES_MEDIA_REPORT, map);
    }

    /**
     * 行业新闻详情
     *
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/industry/{id}")
    public String industryDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "industry");
        return getArticleDetails("wap/index/articleDetail", id, NewsConstant.ARTICLES_INDUSTRY_NEWS, map);
    }


    private String getArticleDetails(String url, String articleId, String navId, ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory();
        map.put("arts", arts);
        //媒体报道的文章内容
        Query contentQuery1 = new Query(Criteria.where("isort").is(Integer.parseInt(articleId)));
        contentQuery1.addCriteria(Criteria.where("snavigationId").is(navId));
        contentQuery1.addCriteria(Criteria.where("istatus").is(20));
        Article article = articleService.findOne(contentQuery1);
        if (null != article) {
            Query contentQuery2 = new Query(Criteria.where("sarticleId").is(article.getId()));
            List<Articlecontent> contents = articlecontentService.find(contentQuery2);
            if (null != contents && contents.size() > 0) {
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
