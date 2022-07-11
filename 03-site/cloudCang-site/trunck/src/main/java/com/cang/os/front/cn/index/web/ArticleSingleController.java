package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.common.utils.StringUtil;
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
@RequestMapping("/")
public class ArticleSingleController {

    @Autowired
    ArticleService articleService;
    /*
    * 文章单页
    * */
    @RequestMapping("{code}")
    public String articleSingle(HttpServletRequest request,ModelMap map,@PathVariable String code) {

        if (StringUtil.isNotBlank(code) && code.equals("zctx")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                Article article = contents.get(0);
                return "forward:cn/skip/" + article.getId();
            }
        } else if (StringUtil.isNotBlank(code) && code.equals("product")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                Article article = contents.get(0);
                return "forward:cn/skip/" + article.getId();

            }
        } else if (StringUtil.isNotBlank(code) && code.equals("core")) {
            Query contentQuery = new Query(Criteria.where("ssubjoinTitle").is(code));
            List<Article> contents = articleService.find(contentQuery);
            if (null != contents && contents.size() > 0) {
                Article article = contents.get(0);
                return "forward:cn/skip/" + article.getId();
            }


        }
        return "";
    }
}