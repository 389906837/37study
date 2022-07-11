package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.vo.ArticleVo;
import com.cloud.cang.common.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhouhong
 *         关于我们 媒体报道
 */
@Controller
@RequestMapping("/")
public class ContactusController {

    @Autowired
    private IndexService indexService;
    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${localNewsAddress}")
    private String localNewsAddress;

    @RequestMapping("aboutus")
    public String aboutus(ModelMap map) {

        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory(map);
        map.put("arts", arts);

        return "front/cn/index/aboutus";
    }

    @RequestMapping("report")
    public String report(ModelMap map, String type) {

        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory(map);
        map.put("arts", arts);
        if (StringUtil.isBlank(type) || !(type.equals("companyNews") || type.equals("mediaReport") || type.equals("industryNews"))) {
            type = "companyNews";
        }
        //默认 显示公司动态
        Long temp = 0L;
        if (type.equals("companyNews")) {
            map.put("newType", "company");
            temp = indexService.getReportArticleListCount(NewsConstant.ARTICLES_COMPANY_NEWS);
        } else if (type.equals("mediaReport")) {
            map.put("newType", "media");
            temp = indexService.getReportArticleListCount(NewsConstant.ARTICLES_MEDIA_REPORT);
        } else if (type.equals("industryNews")) {
            map.put("newType", "industry");
            temp = indexService.getReportArticleListCount(NewsConstant.ARTICLES_INDUSTRY_NEWS);
        }
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
                    articleVo.setAdvertisUrl(localNewsAddress +"media/"+ articleVo.getIsort() + ".html");
                }else   if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_COMPANY_NEWS)) {
                    articleVo.setAdvertisUrl(localNewsAddress +"company/"+ articleVo.getIsort() + ".html");
                }else   if (articleVo.getSnavigationId().equals(NewsConstant.ARTICLES_INDUSTRY_NEWS)) {
                    articleVo.setAdvertisUrl(localNewsAddress +"industry/"+ articleVo.getIsort() + ".html");
                }
            }
        }

        map.put("articles", articles);
       /* Query query = new Query(Criteria.where("iisExhibition").is(1));
        Sort sort = new Sort(Sort.Direction.DESC, "isort");
        //当前页码，每页条数，排序方式
        MongoPageable pageable = new MongoPageable(1, 4, sort);
        List<Recommend> recommends = mongoTemplate.find(query.with(pageable), Recommend.class);
        map.put("recommends", recommends);*/


        map.put("pageSize", temp.intValue());
        map.put("type", type);
        return "front/cn/index/report_new";
    }

    @RequestMapping("getAA")
    @ResponseBody
    public ResponseVo<Page<Article>> getAA(String type, Integer page) {

        //获取首页导航栏类目
        if (StringUtil.isBlank(type) || !(type.equals("companyNews") || type.equals("mediaReport") || type.equals("industryNews"))) {
            type = "companyNews";
        }
        Page<Article> articlePage = null;
        //默认 显示公司动态
        if (type.equals("companyNews")) {
            articlePage = indexService.getReportArticleListPage(1, NewsConstant.ARTICLES_COMPANY_NEWS);
        } else if (type.equals("mediaReport")) {
            articlePage = indexService.getReportArticleListPage(1, NewsConstant.ARTICLES_MEDIA_REPORT);
        } else if (type.equals("industryNews")) {
            articlePage = indexService.getReportArticleListPage(1, NewsConstant.ARTICLES_INDUSTRY_NEWS);
        }

        articlePage.getTotalPages();
        return ResponseVo.getSuccessResponse(articlePage);
    }

    @RequestMapping("getBB")
    @ResponseBody
    public ResponseVo<Page<Article>> getBB(String type, Integer pageNum) {
        Page<Article> articlePage = null;
        if (type.equals("companyNews")) {
            articlePage = indexService.getReportArticleListPage(pageNum, NewsConstant.ARTICLES_COMPANY_NEWS);
        } else if (type.equals("mediaReport")) {
            articlePage = indexService.getReportArticleListPage(pageNum, NewsConstant.ARTICLES_MEDIA_REPORT);
        } else if (type.equals("industryNews")) {
            articlePage = indexService.getReportArticleListPage(pageNum, NewsConstant.ARTICLES_INDUSTRY_NEWS);
        }
        return ResponseVo.getSuccessResponse(articlePage);
    }
}
