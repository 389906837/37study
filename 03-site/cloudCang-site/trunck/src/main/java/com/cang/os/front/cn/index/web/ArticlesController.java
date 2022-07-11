package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
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

/**
 * Created by 37cang-1 on 2018/12/28.
 */

@Controller
@RequestMapping("/news")
public class ArticlesController {
    /**
     * 叁拾柒号仓新闻媒体报道
     */
    private static final String ARTICLES="5c3d36d6b9e727cfcf4f6c95";
    /**
     * 关于我们叁拾柒号仓
     */
    private static final String ARTICLES37="5c3d36c4b9e727cfcf4f6c94";
    /**
     *  37cang文章导航
     */
    private static final String ZY_CULTURE_NAV_ID="5c3d36a4b9e727cfcf4f6c93";



    @Autowired
    ArticleService articleService;

    @Autowired
    ArticlecontentService articlecontentService;
    /**媒体报道列表**/
    @RequestMapping("/media")
    public String index(ModelMap map) {
        //获取首页文章
        List<Article> arts =getArticles();
        System.out.println("sssize--->"+arts.size());
        map.put("arts", arts);

        //媒体报道的文章列表
        List<Article> mediaList =getMediaList();
        System.out.println("mediaList--->"+mediaList.size());
        map.put("mediaList", mediaList);

        return "front/cn/index/mediaList1";
    }
    /**关于我们的文章媒体报道列表*/
    @RequestMapping("/company")
    public String index37(ModelMap map) {
        //获取首页文章
        List<Article> arts =getArticles();
        System.out.println("sssize--->"+arts.size());
        map.put("arts", arts);

        //媒体报道的文章列表
        List<Article> mediaList =getMediaList37();
        System.out.println("mediaList--->"+mediaList.size());
        map.put("mediaList", mediaList);

        return "front/cn/index/company";
    }


    /*
    * 获取新闻详情
    * */
    @RequestMapping("/company/{id}")
    public String newDetail(HttpServletRequest request,ModelMap map,@PathVariable String id) {
        //System.out.println("stitle"+request.getParameter("stitle"));
        if((id.length())==2)
        {
            id = new String(id+"0000000000000000000000");
        }
        //获得文章stitle
        String stitle =request.getParameter("stitle");
        //获取首页文章
        List<Article> arts =getArticles();
        System.out.println("sssize--->"+arts.size());
        map.put("arts", arts);



        //媒体报道的文章内容


        Query contentQuery1 = new Query(Criteria.where("id").is(id));
        contentQuery1.addCriteria(Criteria.where("istatus").is(20));
        Article article = articleService.findOne(contentQuery1);
        if(null!=article){
            Query contentQuery2 = new Query(Criteria.where("sarticleId").is(id));

            List<Articlecontent> contents = articlecontentService.find(contentQuery2);
            if (null != contents && contents.size() > 0){
                map.put("content", contents.get(0));
            }
        }
        //  Article article = articleService.findById(id);
        map.put("article", article);


        return "front/cn/index/conpanyDetail";
       /* System.out.println("需要查询的文章id"+id);
        Article article = articleService.findById(id);
        map.put("article", article);
        Query contentQuery = new Query(Criteria.where("sarticleId").is(id));
        List<Articlecontent> contents = articlecontentService.find(contentQuery);
        if (null != contents && contents.size() > 0){
            System.out.println("contents-->"+contents);
            map.put("content", contents.get(0));
        }
        return "front/cn/index/conpanyDetail";*/
    }


    /*
    * 获取新闻详情
    * */
    @RequestMapping("/media/{id}")
    public String mediaDetail(HttpServletRequest request,ModelMap map,@PathVariable String id) {

        //获得文章stitle
/*
        String stitle =request.getParameter("stitle");
*/

        if((id.length())==2)
        {
            id = new String(id+"0000000000000000000000");
        }
        //获取首页文章
        List<Article> arts =getArticles();
        map.put("arts", arts);



        //媒体报道的文章内容
        Query contentQuery1 = new Query(Criteria.where("id").is(id));
        contentQuery1.addCriteria(Criteria.where("istatus").is(20));
        Article article = articleService.findOne(contentQuery1);
      /*  System.out.println("需要查询的文章id"+id);
        Article article = articleService.findById(id);*/

        if(null!=article){
            Query contentQuery3 = new Query(Criteria.where("sarticleId").is(id));

            List<Articlecontent> contents = articlecontentService.find(contentQuery3);
            if (null != contents && contents.size() > 0){
                map.put("content", contents.get(0));
            }
        }

        map.put("article", article);
       /* Query contentQuery = new Query(Criteria.where("sarticleId").is(id));
        List<Articlecontent> contents = articlecontentService.find(contentQuery);
        if (null != contents && contents.size() > 0){
            System.out.println("contents-->"+contents);
            map.put("content", contents.get(0));
        }*/
        return "front/cn/index/mediaDetail1";
    }
    /*
    * 查询产品介绍栏目
    * */
    private List<Article> getArticles(){
        Query articleQuery = new Query(Criteria.where("snavigationId").is(ZY_CULTURE_NAV_ID));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));
        articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artss = articleService.find(articleQuery);
        return artss;
    }
    /*
    * 查询媒体报道列表
    * */
    private List<Article> getMediaList(){
        Query articleQuery = new Query(Criteria.where("snavigationId").is(ARTICLES));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));
        articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artss = articleService.find(articleQuery);
        return artss;
    }/*
    * 查询媒体报道列表
    * */
    private List<Article> getMediaList37(){
        Query articleQuery = new Query(Criteria.where("snavigationId").is(ARTICLES37));
        articleQuery.addCriteria(Criteria.where("istatus").is(20));
        articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
        List<Article> artss = articleService.find(articleQuery);
        return artss;
    }




}
