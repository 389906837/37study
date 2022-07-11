package com.cloud.cang.vis.detector;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.mq.message.Mq_Zlqf_ImgResult;
import com.cloud.cang.vis.model.ImageCellBean;
import com.cloud.cang.vis.model.ImageGoodsBean;
import com.cloud.cang.vis.ws.PuTong;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectorLibrary {

    static {
        Native.register("/data/zlqf/so/libdarknet.so");
    }


    /**
     * 初始化视觉服务
     * @param loadParams
     * @return 成功 失败
     */
    public static native int loadRecogitionServer(String loadParams);

    /***
     * 重启视觉服务
     * @param reloadParams
     * @return 成功 失败
     */
    public static native int reloadRecogitionServer(String reloadParams);

    /***
     * 识别图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码、位置坐标、置信度
     */
    public static native String recogitionImageByFileNamePos(String recogitionParams);


    /**
     * 识别图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码和数量
     */
    public static String recogitionImageByFileName(String recogitionParams){

        String result ="";
        result = recogitionImageByFileNamePos(recogitionParams);

        Mq_Zlqf_ImgResult imgResult = null;
        imgResult = JSONUtil.toBean(result, Mq_Zlqf_ImgResult.class);

        if (imgResult.getGoods() == null || imgResult.getGoods().size() == 0) return result;

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Mq_Zlqf_ImgResult.Goods temp : imgResult.getGoods()) {
            Integer count = map.get(temp.getSvrCode());
            map.put(temp.getSvrCode(), (count == null) ? 1 : count + 1);
        }
        List<ImageGoodsBean> imageGoodsBeans = new ArrayList<ImageGoodsBean>();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            ImageGoodsBean cellBean = new ImageGoodsBean();
            cellBean.setSvrCode(entry.getKey());
            cellBean.setNumber(entry.getValue());
            imageGoodsBeans.add(cellBean);
        }
        JSONObject object = JSONObject.parseObject(result);
        object.put("goods",imageGoodsBeans);


        return object.toString();

    }


//
//    /**
//     * 识别图片
//     * @param recogitionParams 识别参数 json字符串
//     * @return 识别编码和数量
//     */
//    public static String recogitionImageByFileName(String recogitionParams){
//
//        String result ="";
//        result = recogitionImageByFileNamePos(recogitionParams);
//
//        Mq_ImgResult imgResult = null;
//        imgResult = JSONUtil.toBean(result, Mq_ImgResult.class);
//
//        if (imgResult.getMsg().get(0).getGoods() == null || imgResult.getMsg().get(0).getGoods().size() == 0) return result;
//
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        for (Mq_ImgResult.Goods temp : imgResult.getMsg().get(0).getGoods()) {
//            Integer count = map.get(temp.getSvrCode());
//            map.put(temp.getSvrCode(), (count == null) ? 1 : count + 1);
//        }
//        List<ImageGoodsBean> imageGoodsBeans = new ArrayList<ImageGoodsBean>();
//        for(Map.Entry<String, Integer> entry : map.entrySet()){
//            ImageGoodsBean cellBean = new ImageGoodsBean();
//            cellBean.setSvrCode(entry.getKey());
//            cellBean.setNumber(entry.getValue());
//            imageGoodsBeans.add(cellBean);
//        }
//        JSONObject object = JSONObject.parseObject(result);
//        JSONArray msgJson = (JSONArray) object.get("msg");
//        JSONObject msgObject = JSONObject.parseObject(msgJson.get(0).toString());
//        msgObject.put("goods",imageGoodsBeans);
//        object.put("msg",msgObject);
//
//        return object.toString();
//
//    }


}
