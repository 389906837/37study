package com.cloud.cang.junit;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class Hessian_client {
	private static CuratorFramework zkTools;
	// 获取src路径的正则  
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";  
    
    static Interner<String> pool = Interners.newWeakInterner();
	public static void main(String[] args) throws Exception {
		getZookeeper();
		System.out.println(1e8);
		//runService();
		//serviceTest("T0002");
		//callRed("");
		System.out.println("================================");
		//runRedService();//压力测试远程方法调用，Hessian接口调用 
		//runService();//压力测试发短信
		synchronized (pool.intern("id")) {
			//System.out.println("==");
		}
		
		
		
		
		//getZookeeper();
	}

	private static List<String> getImageSrc(List<String> listImageUrl) {  
        List<String> listImgSrc = new ArrayList<String>();  
        for (String image : listImageUrl) {  
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);  
            while (matcher.find()) {  
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));  
            }  
        }  
        return listImgSrc;  
    }  

	private static List<String> getImageUrl(String HTML) {  
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);  
        List<String> listImgUrl = new ArrayList<String>();  
        while (matcher.find()) {  
            listImgUrl.add(matcher.group());  
        }  
        return listImgUrl;  
    }  
	private static void getZookeeper() throws Exception {
		zkTools = CuratorFrameworkFactory.builder()
				//.connectString("61.147.165.117:60081")
				.connectString("10.0.101.50:2181")
				.retryPolicy(new RetryNTimes(2000, 20000)).build();
		zkTools.start();

		List<String> conf = zkTools.getChildren().forPath("/37cang/conf");
		for (String path : conf) {
			if(path.indexOf("hrb-mac")==-1)continue;
			// System.out.println(path);
			//System.out.println("create /37cang/conf/"+path+" 0");
			List<String> conf1 = zkTools.getChildren().forPath("/37cang/conf/" + path);
			for (String sub : conf1) {
				
				//if(sub.indexOf("manager")==-1)continue;
				 byte[]  aa=zkTools.getData().forPath("/37cang/conf/"+path+"/"+sub);
				if(aa!=null){
					String da=new String(aa);
					//if(da.indexOf("manager")!=-1)
						System.out.println("set /37cang/conf/"+path+"/"+sub+" "+ da);
				}
				
			}
		}
	}

}
