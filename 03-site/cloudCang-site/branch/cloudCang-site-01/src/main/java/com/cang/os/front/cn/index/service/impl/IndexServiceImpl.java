package com.cang.os.front.cn.index.service.impl;


import com.cang.os.bean.wz.Article;
import com.cang.os.front.cn.index.service.IndexService;
import com.cang.os.front.common.NewsConstant;
import com.cang.os.mgr.wz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	private ArticleService articleService;

	/**
	 * 获取配置导航类目
	 * @return 文章集合
	 */
	@Override
	public List<Article> getNavigationCategory() {
		Query articleQuery = new Query(Criteria.where("snavigationId").is(NewsConstant.PRODUCT_DESCRIPTION));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));

		articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
		List<Article> artss = articleService.find(articleQuery);
		return artss;
	}
	/**
	 * 获取配置导航类目
	 * @return 文章集合
	 */
	@Override
	public List<Article> getWapNavigationCategory() {
		Query articleQuery = new Query(Criteria.where("snavigationId").is(NewsConstant.WAP_PRODUCT_DESCRIPTION));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));

		articleQuery.with(new Sort(Sort.Direction.ASC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
		List<Article> artss = articleService.find(articleQuery);
		return artss;
	}

	/***
	 * 获取报道文章列表
	 * @param navigationId 类别ID
	 * @return 文章集合
	 */
	@Override
	public List<Article> getReportArticleList(String navigationId) {
		Query articleQuery = new Query(Criteria.where("snavigationId").is(navigationId));
		articleQuery.addCriteria(Criteria.where("istatus").is(20));
		articleQuery.with(new Sort(Sort.Direction.DESC,"isort").and(new Sort(Sort.Direction.DESC, "taddTime")));
		List<Article> artss = articleService.find(articleQuery);
		return artss;
	}
}