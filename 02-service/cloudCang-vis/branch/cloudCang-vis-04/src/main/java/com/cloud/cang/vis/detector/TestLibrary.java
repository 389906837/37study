package com.cloud.cang.vis.detector;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.cang.vis.common.VisContant;
import com.cloud.cang.vis.common.utlis.PythonUtil;
import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLibrary {
    private static final Logger logger = LoggerFactory.getLogger(TestLibrary.class);


    /**
     * 初始化视觉服务
     *
     * @param loadParams
     * @return 成功 失败
     */
    public static int loadRecogitionServer(String loadParams) {


        DateTime startTime = DateUtil.date();
        PythonUtil.ReturnData returnData = PythonUtil.loadServer(VisContant.REQUEST_LOAD, loadParams);
        DateTime endTime = DateUtil.date();
        long between = DateUtil.between(startTime, endTime, DateUnit.SECOND);

        if (returnData.getStatus() == 1){
            logger.info("初始化, 耗时："+between+"秒");
            return 200;
        }else {
            return -100;
        }

        //PythonUtil.checkSample('', 'sampleCode');


    }

    /***
     * 重启视觉服务
     * @param reloadParams
     * @return 成功 失败
     */
    public static int reloadRecogitionServer(String reloadParams) {


        DateTime startTime = DateUtil.date();
        PythonUtil.ReturnData returnData = PythonUtil.reloadServer(VisContant.REQUEST_LOAD, reloadParams);
        DateTime endTime = DateUtil.date();
        long between = DateUtil.between(startTime, endTime, DateUnit.SECOND);

        if (returnData.getStatus() == 1){
            logger.info("初始化, 耗时："+between+"秒");
            return 200;
        }else {
            return -100;
        }
    }

    /***
     * 识别图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码和数量
     */
    public static String recogitionImageByFileName(String recogitionParams) {

        if (StrUtil.isEmpty(recogitionParams)){
            logger.error("参数异常, recogitionParams="+recogitionParams);

        }


        DateTime startTime = DateUtil.date();
        PythonUtil.ReturnData returnData = PythonUtil.doPython(VisContant.REQUEST_RECOG, recogitionParams);
        DateTime endTime = DateUtil.date();
        long between = DateUtil.between(startTime, endTime, DateUnit.SECOND);


        if (returnData.getStatus() == 1){
            logger.info("识别图片, 耗时："+between+"秒");
            return returnData.getDesc();
        }else {

        }


        String res = "[{'cameraCode':'001','status':'200','imageUrl':'/data/fileServer/model/2019-11-01_141730_461.jpg','goods':[{'svrCode':'6928804011173','number':10}]}]";

        return res;
    }

    /**
     * 识别图片
     *
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码、位置坐标、置信度和数量
     */
    public static String recogitionImageByFileNamePos(String recogitionParams) {


        if (StrUtil.isEmpty(recogitionParams)){
            logger.error("参数异常, recogitionParams="+recogitionParams);

        }


        DateTime startTime = DateUtil.date();
        PythonUtil.ReturnData returnData = PythonUtil.doPython(VisContant.REQUEST_RECOG, recogitionParams);
        DateTime endTime = DateUtil.date();
        long between = DateUtil.between(startTime, endTime, DateUnit.SECOND);


        if (returnData.getStatus() == 1){
            logger.info("识别图片带坐标, 耗时："+between+"秒");
            return returnData.getDesc();
        }else {

        }


        String res =  "[{'cameraCode':'001','status':'200','imageUrl':'/data/fileServer/model/2019-11-01_141730_461.jpg','goods':[{'svrCode':'6928804011173','posx':'490','posy':'164','posw':'59','posh':'51','prob':'0.999995'},{'svrCode':'6928804011173','posx':'472','posy':'302','posw':'55','posh':'51','prob':'0.999991'},{'svrCode':'6928804011173','posx':'531','posy':'120','posw':'25','posh':'36','prob':'0.999984'},{'svrCode':'6928804011173','posx':'482','posy':'242','posw':'62','posh':'48','prob':'0.999983'},{'svrCode':'6928804011173','posx':'478','posy':'105','posw':'56','posh':'51','prob':'0.999983'},{'svrCode':'6928804011173','posx':'511','posy':'79','posw':'27','posh':'27','prob':'0.999907'},{'svrCode':'6928804011173','posx':'517','posy':'225','posw':'51','posh':'33','prob':'0.999900'},{'svrCode':'6928804011173','posx':'548','posy':'171','posw':'19','posh':'36','prob':'0.999665'},{'svrCode':'6928804011173','posx':'518','posy':'289','posw':'40','posh':'29','prob':'0.999564'},{'svrCode':'6928804011173','posx':'469','posy':'61','posw':'47','posh':'40','prob':'0.999425'},{'svrCode':'6902538004045','posx':'203','posy':'210','posw':'101','posh':'76','prob':'0.999991'},{'svrCode':'6902538004045','posx':'156','posy':'61','posw':'60','posh':'53','prob':'0.999990'},{'svrCode':'6902538004045','posx':'210','posy':'110','posw':'92','posh':'93','prob':'0.999987'},{'svrCode':'6902538004045','posx':'124','posy':'191','posw':'83','posh':'62','prob':'0.999981'},{'svrCode':'6902538004045','posx':'224','posy':'44','posw':'73','posh':'72','prob':'0.999981'},{'svrCode':'6902538004045','posx':'140','posy':'115','posw':'68','posh':'71','prob':'0.999976'},{'svrCode':'6902538004045','posx':'223','posy':'284','posw':'81','posh':'102','prob':'0.999956'},{'svrCode':'6902538004045','posx':'138','posy':'264','posw':'72','posh':'67','prob':'0.999904'},{'svrCode':'6928804010220','posx':'97','posy':'99','posw':'43','posh':'44','prob':'0.999984'}]}]";
        return res;
    }
}
