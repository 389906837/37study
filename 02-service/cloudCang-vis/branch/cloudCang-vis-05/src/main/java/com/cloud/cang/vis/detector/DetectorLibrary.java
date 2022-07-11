package com.cloud.cang.vis.detector;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectorLibrary {

    static {
        Native.register("/data/fileServer/so/baohuqu/libdarknet.so");
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
     * 识别图片 +位置坐标 单张图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码、位置坐标、置信度
     */
    public static native  String recogitionImageByFileNamePos(String recogitionParams);


    /**
     * 识别图片 +位置坐标 多张图片
     * @param recogitionParams
     * @return List<String>
     */
    public static  List<Mq_ImgResult.ImgResultItem> recogitionImagePos(String recogitionParams){

        JSONArray ParamsArray = JSONArray.parseArray(recogitionParams);
        Mq_ImgResult.ImgResultItem item;
        List<Mq_ImgResult.ImgResultItem> list = new ArrayList<>();
        for (int i = 0; i < ParamsArray.size(); i++) {
            String parm =  ParamsArray.get(i).toString();
            String resOne =  recogitionImageByFileNamePos(parm);
            item = JSONUtil.toBean(resOne, Mq_ImgResult.ImgResultItem.class,true);
            list.add(item);
        }
        return list;
    };


    /**
     * 识别图片 +识别数量 多张图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码和数量
     */
    public  static List<Mq_ImgResult.ImgResultItem> recogitionImage(String recogitionParams){
        List<Mq_ImgResult.ImgResultItem> recogitionTmp = recogitionImagePos(recogitionParams);
        for (int i = 0; i < recogitionTmp.size(); i++) {
            Mq_ImgResult.ImgResultItem item =  recogitionTmp.get(i);
            List<Mq_ImgResult.Goods> goods = recogitionTmp.get(i).getGoods();
            if (goods == null ) continue;
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (int j = 0; j <goods.size() ; j++) {
                Integer count = map.get(goods.get(j).getSvrCode());
                map.put(goods.get(j).getSvrCode(), (count == null) ? 1 : count + 1);
            }
            List<Mq_ImgResult.Goods> imageGoodsBeans = new ArrayList<Mq_ImgResult.Goods>();
            for(Map.Entry<String, Integer> entry : map.entrySet()){
                Mq_ImgResult imgResult = new Mq_ImgResult();
                Mq_ImgResult.Goods cellBean = imgResult.new Goods();
                cellBean.setSvrCode(entry.getKey());
                cellBean.setNumber(entry.getValue());
                imageGoodsBeans.add(cellBean);
            }
            item.setGoods(imageGoodsBeans);
        }
        return recogitionTmp;
    }


//
//    /**
//     * 识别图片
//     * @param recogitionParams 识别参数 json字符串
//     * @return 识别编码和数量
//     */
//    public static String recogitionImage(String recogitionParams){
//
//        String result ="";
//        result = recogitionImagePos(recogitionParams);

//        Mq_Zlqf_ImgResult imgResult = null;
//        imgResult = JSONUtil.toBean(result, Mq_Zlqf_ImgResult.class);
//
//        if (imgResult.getGoods() == null || imgResult.getGoods().size() == 0) return result;
//
//        Map<String, Integer> map = new HashMap<String, Integer>();
//        for (Mq_Zlqf_ImgResult.Goods temp : imgResult.getGoods()) {
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
//        object.put("goods",imageGoodsBeans);
//
//
//        return object.toString();
//
//    }
//


}
