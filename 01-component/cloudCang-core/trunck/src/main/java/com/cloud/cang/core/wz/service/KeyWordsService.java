package com.cloud.cang.core.wz.service;




import com.cloud.cang.model.wz.KeyWords;

import java.util.List;

public interface KeyWordsService {
	
	/**
	 * 根据URL获取页面关键字
	 * @param url
	 * @return
	 */
	KeyWords getKeyWords(String url);
	/**
     * 
     * @param t where参数
     * @return
     */
    List<KeyWords> selectByEntityWhere(KeyWords t);
    
}