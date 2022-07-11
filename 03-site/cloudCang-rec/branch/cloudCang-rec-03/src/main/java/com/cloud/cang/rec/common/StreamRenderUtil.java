package com.cloud.cang.rec.common;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.exception.ServiceException;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 流工具
 * @author zhouhong
 * @version 1.0
 */
public class StreamRenderUtil {
    
    
    /**
     * 
     * @param obj    对象
     * @param response  
     * @param ContentType 规则 默认为  text/html;charset=UTF-8
     */
    public static void render(Object obj,HttpServletResponse response,String ContentType){
        PrintWriter out=null;
        try {
             if(null==ContentType){
                 ContentType="text/html; charset=UTF-8";
             }
             response.setContentType(ContentType);
             response.setHeader("Pragma", "No-Cache");
             response.setHeader("Cache-Control", "No-Cache");
             response.setDateHeader("Expires", 0);
             out=response.getWriter();
             String result=JSON.toJSONString(obj);
             out.write(result);
             out.flush();
         } catch (Exception e) {
             //给异常拦截器统一处理
            throw new ServiceException("json输出失败！");
         }finally{
             if (out != null) {
                 out.close();
             }
         }
    }

}
