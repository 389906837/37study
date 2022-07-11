package com.cang.os.front.cn.index.service.impl;


import com.cang.os.bean.wz.Article;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取配置导航类目
     *
     * @return 文章集合
     * @param modelMap
     */
    @Override
    public List<Article> getNavigationCategory(ModelMap modelMap) {
        Query articleQuery = new Query(Criteria.where("snavigationId").is(NewsConstant.PRODUCT_DESCRIPTION));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));

        articleQuery.with(new Sort(Sort.Direction.ASC, "isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artss = articleService.find(articleQuery);

        Query articleQuery1 = new Query(Criteria.where("snavigationId").is(NewsConstant.PRODUCT_DESCRIPTION));
        articleQuery1.addCriteria(Criteria.where("sresourceUrl").is("10"));
        articleQuery1.addCriteria(Criteria.where("istatus").is(10));

        articleQuery1.with(new Sort(Sort.Direction.ASC, "isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artsss = articleService.find(articleQuery1);
        modelMap.put("ttt_artsss", artsss);
        return artss;
    }

    /***
     * 获取报道文章列表
     * @param navigationId 类别ID
     * @return 文章集合
     */
    @Override
    public Page<Article> getReportArticleListPage(Integer pageNum, String navigationId) {

        Page<Article> articles = articleService.paginationQuery(pageNum, navigationId);
        return articles;
    }

    /***
     * 获取报道文章列表
     * @param navigationId 类别ID
     * @return 文章集合
     */
    @Override
    public List<Article> getReportArticleList(String navigationId) {
        Query articleQuery = new Query(Criteria.where("snavigationId").is(navigationId));
        articleQuery.with(new Sort(Sort.Direction.DESC, "isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));
        List<Article> artss = articleService.find(articleQuery);
        return artss;
    }

    /***
     * 获取报道文章列表条数
     * @param navigationId 类别ID
     * @return 文章集合
     */
    @Override
    public Long getReportArticleListCount(String navigationId) {
        Query articleQuery = new Query(Criteria.where("snavigationId").is(navigationId));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));
        Long artss = articleService.count(articleQuery);
        return artss;
    }
    /**
     * 获取配置导航类目
     * @return 文章集合
     * @param modelMap
     */
    @Override
    public List<Article> getWapNavigationCategory(ModelMap modelMap) {
        Query articleQuery = new Query(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));

        articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artss = articleService.find(articleQuery);

        Query articleQuery1 = new Query(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));
        articleQuery1.addCriteria(Criteria.where("sresourceUrl").is("10"));
        articleQuery1.addCriteria(Criteria.where("istatus").is(10));

        articleQuery1.with(new Sort(Sort.Direction.ASC, "isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artsss = articleService.find(articleQuery1);
        modelMap.put("ttt_artsss", artsss);

        return artss;
    }

}