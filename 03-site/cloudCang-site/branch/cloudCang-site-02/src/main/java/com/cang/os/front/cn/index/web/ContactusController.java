package com.cang.os.front.cn.index.web;

import com.cang.os.bean.wz.Article;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zhouhong
 * 关于我们 媒体报道
 */
@Controller
@RequestMapping("/")
public class ContactusController {

	@Autowired
	private IndexService indexService;

	@RequestMapping("aboutus")
	public String aboutus(ModelMap map) {

		//获取首页导航栏类目
		List<Article> arts = indexService.getNavigationCategory();
		map.put("arts", arts);

		return "front/cn/index/aboutus";
	}

	@RequestMapping("report")
	public String report(ModelMap map, String type) {

		//获取首页导航栏类目
		List<Article> arts = indexService.getNavigationCategory();
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

		return "front/cn/index/report";
	}


}
