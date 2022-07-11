package com.cloud.cang.wap.common.utils;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.utils.SpringContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * cookie utils
 * @author zhouhong
 * @version 1.0
 */
public class CookieUtils {
    
    public static ICached redisCached= SpringContext.getBean(ICached.class);
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     */
    public static void addCookie(HttpServletResponse response,String name,String value){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     * @throws Exception 
     */
    public static String getCookieByName(HttpServletRequest request,String name) throws Exception{
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            String userToken = cookie.getValue();
            // 用户id
            return (String) redisCached.get(userToken);
        }else{
            return null;
        }
     
    }
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     * @throws Exception 
     */
    public static String getTokenByCookie(HttpServletRequest request,String name) throws Exception{
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        String userToken ="";
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            userToken  = cookie.getValue();
        }
        return userToken;
    }
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    
}
