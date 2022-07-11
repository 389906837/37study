package com.cloud.cang.vis.common;

/**
 * @program: 37cang-云平台
 * @description: 常量类
 * @author: qzg
 * @create: 2019-11-21 15:57
 **/
public class VisContant {
    public static final String MODE_FIEL ="37.weights";
    public static final String NAMES_FILE ="37.names";
    public static final String CONFIG_FILE ="37.detect.cfg";
    public static final String VIS_IP ="vis";



    //初始化模型请求参数标志
    public static final String REQUEST_LOAD ="load";
    //重启模型请求参数标志
    public static final String REQUEST_RELOAD ="reload";
    //识别图片请求参数标志
    public static final String REQUEST_RECOG ="recog";
    //识别图片请求参数标志 + 定位
    public static final String REQUEST_RECOGPOS ="recogPos";

    // 测试服务路径
    public static final String FIFE_SERVER ="/data/fileServer";

    //测试入口文件
    public static final String DETECT_PY = FIFE_SERVER + "/detect.py";


    // 中林清风图片保存服务路径
    public static final String IMAGE_SAVE_PATH ="/data/zlqf/images/";




}
