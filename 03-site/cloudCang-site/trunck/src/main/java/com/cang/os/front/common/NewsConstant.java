/**
 * 
 */
package com.cang.os.front.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hunter
 *
 */
public class NewsConstant {
	
	
	/**
	 * 新闻ID
	 * @return
	 */
	public static List<String> getIdNews(){
		List<String> newsId = new ArrayList<String>();
		//集团新闻
		newsId.add("583e8a744d7c4c22804fb4b4");
		//行业动态
		newsId.add("583f8aca4d7cbc8b9e8cfc6c");
		//政策法规
		newsId.add("583f8aeb4d7cbc8b9e8cfc6d");
		return newsId;
	}

}
