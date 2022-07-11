
package com.cang.os.front.cn.index.web;


import java.util.List;

import com.cang.os.bean.wz.Article;
import com.cang.os.front.cn.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/cn")
public class IndexController {

	@Autowired
	private IndexService indexService;


	@RequestMapping("/index")
	public String index(ModelMap map) {

		//获取首页导航栏类目
		List<Article> arts = indexService.getNavigationCategory();
		map.put("arts", arts);

		return "front/cn/index/index";
	}



}
