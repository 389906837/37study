package com.cang.os.wap.index.web;

import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.AlbumImageService;
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

import java.util.Iterator;
import java.util.List;

import static com.cang.os.front.common.NewsConstant.HOME_BANNER_WAP;
import static com.cang.os.front.common.NewsConstant.PARTNERS_WAP;

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
    @Autowired
    AlbumImageService albumImageService;
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${localWapNewsAddress}")
    private String localWapNewsAddress;

    @RequestMapping("/index")
    public String index(ModelMap map) {
        //获取首页导航栏类目
        List<Article> arts = indexService.getWapNavigationCategory(map);
        map.put("arts", arts);
        Query query = new Query();
        query.addCriteria(Criteria.where("albumId").regex(MongoDbUtil.getLikeQueryCondition(HOME_BANNER_WAP)));
        query.with(new Sort(Sort.Direction.DESC, "isort"));
        List<AlbumImage> albumImages = albumImageService.find(query);
        map.put("albumImages", albumImages);
        query = new Query();
        query.addCriteria(Criteria.where("albumId").regex(MongoDbUtil.getLikeQueryCondition(PARTNERS_WAP)));
        query.with(new Sort(Sort.Direction.DESC, "isort"));
        List<AlbumImage> cooPartnerImages = albumImageService.find(query);
        map.put("cooPartnerImages", cooPartnerImages);
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
        List<Article> arts = indexService.getWapNavigationCategory(map);
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
        List<Article> arts = indexService.getWapNavigationCategory(map);
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



/*        Query query = new Query(Criteria.where("iisExhibition").is(1));
        Sort sort = new Sort(Sort.Direction.DESC, "isort");
        //当前页码，每页条数，排序方式
        MongoPageable pageable = new MongoPageable(1, 4, sort);
        List<Recommend> recommends = mongoTemplate.find(query.with(pageable), Recommend.class);
        map.put("recommends", recommends);*/

        // 创建条件
        AggregationOperation lookup = Aggregation.lookup("article", "advertisId", "_id", "article");
        AggregationOperation unwind = Aggregation.unwind("article");
        Sort sort1 = new Sort(Sort.Direction.DESC, "sort");
        AggregationOperation isort = Aggregation.sort(sort1);
        AggregationOperation limit = Aggregation.limit(4);
        AggregationOperation match = Aggregation.match(Criteria.where("iisExhibition").is(1));
        Query query = new Query();
        query.with(new Sort(Sort.Direction.DESC, "isort").and(new Sort(Sort.Direction.DESC, "tpublishDate")));
        AggregationOperation project = Aggregation.project("article.isort", "article.stitleImage", "sketch", "article.taddTime", "sort", "article.snavigationId");
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
                    articleVo.setAdvertisUrl(localWapNewsAddress + "media/" + articleVo.getIsort() + ".html");
                } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_COMPANY_NEWS)) {
                    articleVo.setAdvertisUrl(localWapNewsAddress + "company/" + articleVo.getIsort() + ".html");
                } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_INDUSTRY_NEWS)) {
                    articleVo.setAdvertisUrl(localWapNewsAddress + "industry/" + articleVo.getIsort() + ".html");
                }
            }
        }
        map.put("articles", articles);

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
        List<Article> arts = indexService.getWapNavigationCategory(map);
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
        } else if (StringUtil.isNotBlank(code) && code.indexOf("productCenter_") != -1) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("productCenter"));
            String[] arr = code.split("_");
            if (null != arr && arr.length == 2) {
                contentQuery.addCriteria(Criteria.where("sresourceName").is(arr[1]));
                contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));
                Article contents = articleService.findOne(contentQuery);
                article = contents;
            }
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter_vending")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("vending"));
            contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter_species")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("species"));
            contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));

            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter_display")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("display"));
            contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));

            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter_classification")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("classification"));
            contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));

            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("productCenter_commodity")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("commodity"));
            contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));

            Article contents = articleService.findOne(contentQuery);
            article = contents;
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
        List<Article> arts = indexService.getWapNavigationCategory(map);
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
                if (null != article.getIisRecommend() && article.getIisRecommend() == 1) {
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
                                articleVo.setAdvertisUrl(localWapNewsAddress + "media/" + articleVo.getIsort() + ".html");
                            } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_COMPANY_NEWS)) {
                                articleVo.setAdvertisUrl(localWapNewsAddress + "company/" + articleVo.getIsort() + ".html");
                            } else if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_INDUSTRY_NEWS)) {
                                articleVo.setAdvertisUrl(localWapNewsAddress + "industry/" + articleVo.getIsort() + ".html");
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
