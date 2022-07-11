
package com.cang.os.front.cn.index.web;


import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.bean.wz.Article;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.mgr.wz.service.AlbumImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.cang.os.front.common.NewsConstant.HOME_BANNER_PC;
import static com.cang.os.front.common.NewsConstant.PARTNERS_PC;

/**
 * 首页
 *
 * @author zhouhong
 */
@Controller
@RequestMapping("/cn")
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private AlbumImageService albumImageService;

    @RequestMapping("/index")
    public String index(ModelMap map) {

        //获取首页导航栏类目
        List<Article> arts = indexService.getNavigationCategory(map);
        map.put("arts", arts);
        Query query = new Query();
        query.addCriteria(Criteria.where("albumId").regex(MongoDbUtil.getLikeQueryCondition(HOME_BANNER_PC)));
        query.with(new Sort(Sort.Direction.DESC,"isort"));
        List<AlbumImage> albumImages = albumImageService.find(query);
        map.put("albumImages", albumImages);
        query =new Query();
        query.addCriteria(Criteria.where("albumId").regex(MongoDbUtil.getLikeQueryCondition(PARTNERS_PC)));
        query.with(new Sort(Sort.Direction.DESC,"isort"));
        List<AlbumImage> cooPartnerImages = albumImageService.find(query);
        map.put("cooPartnerImages", cooPartnerImages);
        return "front/cn/index/index_new";
    }


}
