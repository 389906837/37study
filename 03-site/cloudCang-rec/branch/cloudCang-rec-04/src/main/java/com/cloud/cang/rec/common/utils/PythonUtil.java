package com.cloud.cang.rec.common.utils;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:
 * @description: 调用python脚本
 * @author:
 * @create:
 **/
public class PythonUtil {
    private static final Logger logger = LoggerFactory.getLogger(PythonUtil.class);


    private final static String error_flag ="errorMsg==>";
    public final static String PY_CMD = "python";


    // 测试服务路径
    public static final String FIFE_SERVER ="/data/fileServer";

    //测试入口文件
    public static final String UNZIP_MODEL = FIFE_SERVER + "/unzip_model.py";



    /**
     * 调用python-解压模型文件
     * @param
     * @return
     */
    public static Boolean unzipModel(String modelZipDir, Integer modelType){
        logger.info("开始-调用python解压文件");
        String[] arguments = new String[] { PY_CMD, UNZIP_MODEL,
                modelZipDir, String.valueOf(modelType)};
        logger.info("解压文件-执行命令:{}", JSON.toJSONString(arguments));
        Process process = RuntimeUtil.exec(arguments);
        List<String> readlines = RuntimeUtil.getResultLines(process);
        try{
            for(String readLine: readlines) {
                System.out.println(readLine);
                if (readLine.contains(error_flag)) {
                    String errmsg = readLine.replace(error_flag, StrUtil.EMPTY);
                    logger.error("模型解压失败:{}", errmsg);
                    return false;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        logger.info("结束-调用python解压文件");
        return true;
    }




}
