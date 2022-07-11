package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import com.cang.os.mgr.wz.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
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
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${localNewsAddress}")
    private String localNewsAddress;

    /**
     * 媒体报道列表
     **/
    @RequestMapping("/media")
    public String mediaList() {
        return "redirect:/report.html?type=mediaReport";
    }

    /**
     * 公司动态列表
     */
    @RequestMapping("/company")
    public String companyList() {
        return "redirect:/report.html?type=companyNews";
    }

    /**
     * 行业新闻列表
     */
    @RequestMapping("/industry")
    public String industryList() {
        return "redirect:/report.html?type=industryNews";
    }


    /*
    * 公司动态详情
    * */
    @RequestMapping("/company/{id}")
    public String companyDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "company");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_COMPANY_NEWS, map);
    }


    /*
    * 媒体报道详情
    * */
    @RequestMapping("/media/{id}")
    public String mediaDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "media");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_MEDIA_REPORT, map);
    }

    /*
    * 行业新闻详情
    * */
    @RequestMapping("/industry/{id}")
    public String industryDetail(ModelMap map, @PathVariable String id) {
        map.put("type", "industry");
        return getArticleDetails("front/cn/index/articleDetail", id, NewsConstant.ARTICLES_INDUSTRY_NEWS, map);
    }


    private String getArticleDetails(String url, String articleId, String navId, ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory(map);
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
                if (article.getIisRecommend() != null && article.getIisRecommend() == 1) {
                    // 创建条件
                    AggregationOperation lookup = Aggregation.lookup("article", "advertisId", "_id", "article");
                    AggregationOperation unwind = Aggregation.unwind("article");
                    Sort sort1 = new Sort(Sort.Direction.DESC, "sort");
                    AggregationOperation isort = Aggregation.sort(sort1);
                    AggregationOperation limit = Aggregation.limit(4);
                    AggregationOperation match = Aggregation.match(Criteria.where("iisExhibition").is(1));
                    Query query = new Query();
                    query.with(new Sort(Sort.Direction.DESC, "isort").and(new Sort(Sort.Direction.DESC, "tpublishDate")));
                    AggregationOperation project = Aggregation.project("article.isort", "article.stitleImage", "sketch", "sort", "article.snavigationId");
                    // 将条件封装到Aggregate管道
                    Aggregation aggregation = Aggregation.newAggregation(isort, limit, lookup, unwind, match, project);
                    // 查询
                    AggregationResults<ArticleVo> aggregate = mongoTemplate.aggregate(aggregation, "recommend", ArticleVo.class);
                    List<ArticleVo> articles = null;
                    if (null != aggregate) {
                        articles = aggregate.getMappedResults();
                        Iterator<ArticleVo> iterator = articles.iterator();
                        while (iterator.hasNext()) {
                            ArticleVo articleVo = iterator.next();
                            if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_MEDIA_REPORT)) {
                                articleVo.setAdvertisUrl(localNewsAddress + "media/" + articleVo.getIsort() + ".html");
                            } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_COMPANY_NEWS)) {
                                articleVo.setAdvertisUrl(localNewsAddress + "company/" + articleVo.getIsort() + ".html");
                            } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_INDUSTRY_NEWS)) {
                                articleVo.setAdvertisUrl(localNewsAddress + "industry/" + articleVo.getIsort() + ".html");
                            }
                        }
                    }
                    map.put("articles", articles);
                }
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
