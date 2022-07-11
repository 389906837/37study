package com.cang.os.front.cn.index.service;

import com.cang.os.bean.wz.Article;

import java.util.List;

public interface IndexService {

	/**
	 * 获取配置导航类目
	 * @return 文章集合
	 */
	List<Article> getNavigationCategory();


	/***
	 * 获取报道文章列表
	 * @param navigationId 类别ID
	 * @return 文章集合
	 */
	List<Article> getReportArticleList(String navigationId);
}