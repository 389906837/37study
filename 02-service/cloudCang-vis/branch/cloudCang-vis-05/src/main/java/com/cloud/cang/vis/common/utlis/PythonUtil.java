package com.cloud.cang.vis.common.utlis;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Session;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.vis.common.VisContant;
import com.cloud.cang.zookeeper.confclient.secret.AESCryptUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private final static String info_flag ="infoMsg==>";
    private final static String return_data_flag ="retData==>";
    public final static String class_number_flag ="class_number=";
    public final static String jpg_number_flag ="jpg_number=";
    public final static String PY_CMD = "python3";






    /**
     * 调用python-初始化
     * @param
     * @return
     */
    public static ReturnData loadServer(String requestSign,String requestParms){
        logger.info("开始-调用python");
        List errorXmlList = new ArrayList();
        String[] arguments = new String[] { PY_CMD, VisContant.DETECT_PY, requestSign,
                requestParms};
        Process process = RuntimeUtil.exec(arguments);
        List<String> readlines = RuntimeUtil.getResultLines(process);
        try{
            for(String readLine: readlines){
                System.out.println(readLine);
                if(readLine.contains(error_flag)){
                    String errmsg = readLine.replace(error_flag,StrUtil.EMPTY);
                    logger.warn("模型初始化失败:{}", errmsg);
                    return new ReturnData(-1, errmsg);
                    //errorXmlList.add(errmsg.split("#")[0]);
                }

                if(readLine.contains(return_data_flag)){
                    String retData = readLine.replace(return_data_flag,StrUtil.EMPTY);
                    logger.info("模型初始化成功:{}", retData);
                    return new ReturnData(1, retData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("结束-调用python");

        return new ReturnData(1, "");
    }



    /**
     * 调用python-初始化
     * @param
     * @return
     */
    public static ReturnData reloadServer(String requestSign,String requestParms){
        logger.info("开始-调用python");
        List errorXmlList = new ArrayList();
        String[] arguments = new String[] { PY_CMD, VisContant.DETECT_PY, requestSign,
                requestParms};
        Process process = RuntimeUtil.exec(arguments);
        List<String> readlines = RuntimeUtil.getResultLines(process);
        try{
            for(String readLine: readlines){
                System.out.println(readLine);
                if(readLine.contains(error_flag)){
                    String errmsg = readLine.replace(error_flag,StrUtil.EMPTY);
                    logger.warn("模型重载失败:{}", errmsg);
                    return new ReturnData(-1, errmsg);
                    //errorXmlList.add(errmsg.split("#")[0]);
                }

                if(readLine.contains(return_data_flag)){
                    String retData = readLine.replace(return_data_flag,StrUtil.EMPTY);
                    logger.info("模型重载成功:{}", retData);
                    return new ReturnData(1, retData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("结束-调用python");

        return new ReturnData(1, "");
    }




    /**
     * 调用python-识别
     * @param
     * @return
     */
    public static ReturnData doPython(String requestSign,String requestParms){
        logger.info("开始-调用python");
        List errorXmlList = new ArrayList();
        String[] arguments = new String[] { PY_CMD, VisContant.DETECT_PY, requestSign,
                requestParms};
        Process process = RuntimeUtil.exec(arguments);
        List<String> readlines = RuntimeUtil.getResultLines(process);
        try{
            for(String readLine: readlines){
                System.out.println(readLine);
                if(readLine.contains(error_flag)){
                    String errmsg = readLine.replace(error_flag,StrUtil.EMPTY);
                    logger.warn("样本检验:{}", errmsg);
                    return new ReturnData(-1, errmsg);
                    //errorXmlList.add(errmsg.split("#")[0]);
                }

                if(readLine.contains(return_data_flag)){
                    String retData = readLine.replace(return_data_flag,StrUtil.EMPTY);
                    logger.info("识别结果:{}", retData);
                    return new ReturnData(1, retData);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("结束-调用python");

        return new ReturnData(1, "");
    }




//    /**
//     * 导出模型
//     * @param
//     */
//    public static ExportData exportModel( train){
//        logger.info("导出模型开始-调用python");
//        //导出序列号
//        String serialNumber = System.currentTimeMillis()+"";
//
//        // 获取导出模型服务器信息
//        TrainServer trainServer = getExportServer();
//        if(trainServer == null){
//            String errmsg = "导出模型, 训练编号："+train.getScode()+", 获取导出服务器信息失败！";
//            return new ExportData(-1, errmsg,serialNumber);
//        }
//
////        // 获得一个SSH会话
////        Session session = getSession(trainServer);
////        if(session == null){
////            String errmsg = "导出模型, 训练编号："+train.getScode()+", 获取SSH会话失败！";
////            return new ExportData(-1, errmsg,serialNumber);
////        }
//
//        // 执行py
//        //String cmd = export_cmd(train, serialNumber);
//        String cmd;
//        if (train.getItype() != 40){
//            cmd = export_cmd(train, serialNumber);
//        }else {
//            cmd = export_cmd_v4(train, serialNumber);
//        }
//
//
//        logger.info("导出模型执行脚本命令：{}",cmd);
//        String exec = JschUtil.exec(session, cmd, CharsetUtil.CHARSET_UTF_8);
//
//        for (String line : StrUtil.split(exec, StrUtil.LF)) {
//            // logger.info("导出模型==>"+line);
//            if(line.contains(info_flag)){
//                String infomsg = line.replace(error_flag,StrUtil.EMPTY);
//                logger.info("导出模型, "+infomsg);
//            }
//
//            if(line.contains(error_flag)){
//                line = line.replace(error_flag,StrUtil.EMPTY);
//                return new ExportData(-1, line, serialNumber);
//            }
//        }
//
////        // 关闭一个SSH会话
////        JschUtil.close(session);
//        return new ExportData(1,"",serialNumber);
//    }



//    // 获得一个SSH会话
//    public static Session getSession(TrainServer server){
//        try{
//            if(server == null){
//                return null;
//            }
//            return JschUtil.getSession(server.getIp(),
//                    Integer.valueOf(server.getPort()),
//                    AESCryptUtil.decryptByKey(server.getUser()),
//                    AESCryptUtil.decryptByKey(server.getPassword()));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReturnData{
        // 1=成功 非1=失败
        int status = 1;
        String desc = "";
    }



}
