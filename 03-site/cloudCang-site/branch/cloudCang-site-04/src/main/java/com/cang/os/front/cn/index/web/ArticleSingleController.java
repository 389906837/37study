package com.cang.os.front.cn.index.web;

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
 * Created by 37cang-1 on 2018/12/28.
 */

@Controller
@RequestMapping("/")
public class ArticleSingleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticlecontentService articlecontentService;
    @Autowired
    private IndexService indexService;

    /*
    * 产品介绍
    * */
    @RequestMapping("{code}")
    public String articleSingle(ModelMap map, @PathVariable String code ) {

        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory(map);
        map.put("arts", arts);

        Article article = null;
        if (StringUtil.isNotBlank(code) && code.equals("zctx")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                article = contents.get(0);
            }
        } else if (StringUtil.isNotBlank(code) && code.indexOf("product_") != -1) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            String[] arr = code.split("_");
            if (null != arr && arr.length == 2) {
                contentQuery.addCriteria(Criteria.where("sresourceName").is(arr[1]));
                contentQuery.addCriteria(Criteria.where("snavigationId").is(NewsConstant.PRODUCT_DESCRIPTION));
                Article contents = articleService.findOne(contentQuery);
                article = contents;
            }
        } else if (StringUtil.isNotBlank(code) && code.equals("product_vending")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("vending"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_species")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("species"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_display")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("display"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_classification")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("classification"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_commodity")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("commodity"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_perception")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("perception"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("product_faceTemperature")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is("product"));
            contentQuery.addCriteria(Criteria.where("sresourceName").is("faceTemperature"));
            Article contents = articleService.findOne(contentQuery);
            article = contents;
        } else if (StringUtil.isNotBlank(code) && code.equals("core")) {
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
        return "front/cn/index/product";
    }
}