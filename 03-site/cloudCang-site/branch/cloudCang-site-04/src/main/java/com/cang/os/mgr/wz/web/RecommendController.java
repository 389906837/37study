package com.cang.os.mgr.wz.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.bean.wz.Articlecontent;
import com.cang.os.bean.wz.Recommend;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.wz.service.ArticleService;
import com.cang.os.mgr.wz.service.ArticlecontentService;
import com.cang.os.mgr.wz.service.RecommendService;
import com.cang.os.security.utils.SessionUserUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 推荐管理
 *
 * @author zhouhong
 */
@Controller
@RequestMapping("/mgr/recommend")
public class RecommendController {

    @Autowired
    RecommendService recommendService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticlecontentService articlecontentService;


    /**
     * 新闻列表
     *
     * @param paramPage
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public String list(Recommend recommend, ParamPage paramPage, ModelMap map) {
        Page<Recommend> page = new Page<Recommend>();
        page.setPageNum(paramPage.pageNo());
        page.setPageSize(paramPage.getLimit());

        Query query = new Query();

        if (null != (recommend.getSort())) {
            query.addCriteria(Criteria.where("sort").is(recommend.getSort()));
        }
        if (null != (recommend.getIisExhibition())) {
            query.addCriteria(Criteria.where("iisExhibition").is(recommend.getIisExhibition()));
        }
        if (StringUtils.isNotBlank(recommend.getSketch())) {
            query.addCriteria(Criteria.where("sketch").regex(MongoDbUtil.getLikeQueryCondition(recommend.getSketch())));
        }
        page = recommendService.findPage(page, query);
        map.put("page", page);
        return "mgr/wz/recommendList";
    }

    /**
     * 编辑运营位
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView edit(String id, ModelMap map) {
        if (StringUtils.isNotBlank(id)) {
            Recommend recommend = recommendService.findById(id);
            map.put("recommend", recommend);
        }
        return new ModelAndView("mgr/wz/recommendEdit");
    }

    /**
     * 编辑推荐广告信息
     *
     * @param recommend
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(Recommend recommend, ModelMap map, String backUri) {
        // 如果id为空就就添加
        try {
            if (StringUtils.isBlank(recommend.getId())) {
                Article article = articleService.findById(recommend.getAdvertisId().toString());
                if (null == article) {
                    return new ModelAndView("redirect:/mgr/error").addObject("backUri", backUri);
                }
                recommend.setTaddTime(DateUtils.getCurrentDateTime());
                recommend.setId(null);
                recommend.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                recommend.setTupdateTime(DateUtils.getCurrentDateTime());
                recommend.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                recommend.setStitleImage(article.getStitleImage());
                recommendService.insert(recommend);
            } else {
                // 修改
                Article article = articleService.findById(recommend.getAdvertisId().toString());
                if (null == article) {
                    return new ModelAndView("redirect:/mgr/error").addObject("backUri", backUri);
                }
                recommend.setTupdateTime(DateUtils.getCurrentDateTime());
                recommend.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                recommend.setStitleImage(article.getStitleImage());
                recommendService.update(recommend);
            }
        } catch (Exception e) {
            map.put("sRes", "系统异常，保存失败！");
            return new ModelAndView("redirect:/mgr/error").addObject("backUri", backUri);
        }
        return new ModelAndView("redirect:/mgr/success").addObject("backUri", backUri);
    }

    /**
     * 删除推荐新闻
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ModelAndView delete(String id, String backUri) {
        Query query = new Query(Criteria.where("id").is(id));
        recommendService.remove(query);
        return new ModelAndView("redirect:/mgr/success").addObject("backUri", backUri);
    }

    /**
     * 预览推荐新闻
     **/
    @RequestMapping("/preview")
    public String artPreview(String advertisId, ModelMap map) {
        //文章内容
        Query query = new Query(Criteria.where("id").is(advertisId));
        Article article = articleService.findOne(query);

        if (null != article) {
            Query contentQuery3 = new Query(Criteria.where("sarticleId").is(article.getId()));
            List<Articlecontent> contents = articlecontentService.find(contentQuery3);
            if (null != contents && contents.size() > 0) {
                map.put("content", contents.get(0));
            }
        }
        map.put("article", article);
        return "front/cn/news/artPreview";
    }

}
