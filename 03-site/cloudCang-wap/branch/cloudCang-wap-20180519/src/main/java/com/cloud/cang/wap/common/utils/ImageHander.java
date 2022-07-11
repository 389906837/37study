package com.cloud.cang.wap.common.utils;

import com.cloud.cang.zookeeper.utils.EvnUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageHander {

	private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
	private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";


	public static String resetImageSize(String content,String size){
		if(content==null)return "";
		Set<String> images=getImageSrc(getImageUrl(content.toString()));
		for(String imgsrc:images){
            String path=imgsrc+"!t"+size+".jpg";
            content=content.replaceAll(imgsrc, path);
		}
		return content;
	}
	
	private static Set<String> getImageSrc(Set<String> listImageUrl) {
		Set<String> listImgSrc = new HashSet<String>();
		for (String image : listImageUrl) {
			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
			while (matcher.find()) {
				listImgSrc.add(matcher.group().substring(0,
						matcher.group().length() - 1));
			}
		}
		return listImgSrc;
	}

	private static Set<String> getImageUrl(String HTML) {
	    String imgFilter = EvnUtils.getValue("dynamic.http.domain");
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
		Set<String> listImgUrl = new HashSet<String>();
		while (matcher.find()) {
			String src=matcher.group();
			if(src.indexOf(imgFilter)!=-1)
				listImgUrl.add(matcher.group());
		}
		return listImgUrl;
	}
}
