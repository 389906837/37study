package com.cang.os.front.cn.index.service;

import com.cang.os.bean.wz.Article;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface IndexService {

    /**
     * 获取配置导航类目
     *
     * @return 文章集合
     * @param modelMap
     */
    List<Article> getNavigationCategory(ModelMap modelMap);


    /***
     * 获取报道文章列表分页
     * @param navigationId 类别ID
     * @return 文章集合
     */
    Page<Article> getReportArticleListPage(Integer pageNum, String navigationId);

    /***
     * 获取报道文章列表
     * @param navigationId 类别ID
     * @return 文章集合
     */
    List<Article> getReportArticleList(String navigationId);

    /***
     * 获取报道文章列表条数
     * @param navigationId 类别ID
     * @return 文章集合
     */
    Long getReportArticleListCount(String navigationId);
    /***
     * 获取推荐文章
     * @return 文章集合
     */
/*    List<Article> getRecommend();*/

    /**
     * 获取Wap配置导航类目
     *
     * @return 文章集合
     * @param modelMap
     */
    List<Article> getWapNavigationCategory(ModelMap modelMap);
}