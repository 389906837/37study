package com.cloud.cang.vis.ws;



import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.mq.message.Mq_ImgResult;
//import com.cloud.cang.vis.model.Mq_ImgResult;
import com.cloud.cang.vis.detector.DetectorLibrary;
import com.cloud.cang.vis.model.ImageCellBean;
import com.cloud.cang.vis.model.ImageGoodsBean;
import com.cloud.cang.vis.model.ModelParamsDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 连接测试
 */
@RestController
@RequestMapping("/connTest")
@RegisterRestResource
public class ConnTestService {

    @RequestMapping(value = "/load")
    @ResponseBody
    public String toTest() {


        DetectorLibrary instance = new DetectorLibrary();
        String weightPath = "/data/fileServer/model/37.weights";
        String configPath = "/data/fileServer/model/37.detect.cfg";
        String classesPath = "/data/fileServer/model/37.names";
        ModelParamsDto build = ModelParamsDto.builder()
                .weightPath(weightPath)
                .configPath(configPath)
                .classesPath(classesPath)
                .classes(8)
                .thresh(0.8f)
                .build();
        Integer status = instance.loadRecogitionServer(JSONUtil.toJsonStr(build));

        String imagePath = "/data/fileServer/data/111.jpg";
        System.out.println(1111);
        System.out.println(status);
        return "200";
    }


    @RequestMapping(value = "/reload")
    @ResponseBody
    public String reload() {

        DetectorLibrary instance = new DetectorLibrary();
        String weightPath = "/data/fileServer/model/37.weights";
        String configPath = "/data/fileServer/model/37.detect.cfg";
        String classesPath = "/data/fileServer/model/37.names";
        ModelParamsDto build = ModelParamsDto.builder()
                .weightPath(weightPath)
                .configPath(configPath)
                .classesPath(classesPath)
                .classes(8)
                .thresh(0.8f)
                .build();
        Integer status = instance.reloadRecogitionServer(JSONUtil.toJsonStr(build));

        String imagePath = "/data/fileServer/data/111.jpg";
        System.out.println(1111);
        System.out.println(status);
        return "200";
    }


    @RequestMapping(value = "/detect")
    @ResponseBody
    public String toDetect() {

        //String result ="";
        List<Mq_ImgResult.ImgResultItem> result;
        DetectorLibrary instance = new DetectorLibrary();
        List<ImageCellBean> imageCellBeans = new ArrayList<ImageCellBean>();

        ImageCellBean cellBean = new ImageCellBean();
        cellBean.setCameraCode("001");
        cellBean.setImageUrl("/data/baohuqu/111.jpg");
        imageCellBeans.add(cellBean);

        ImageCellBean cellBean1 = new ImageCellBean();
        cellBean1.setCameraCode("001");
        cellBean1.setImageUrl("/data/baohuqu/222.jpg");
        imageCellBeans.add(cellBean1);

        ImageCellBean cellBean2 = new ImageCellBean();
        cellBean2.setCameraCode("001");
        cellBean2.setImageUrl("/data/baohuqu/333.jpg");
        imageCellBeans.add(cellBean2);

        String rearms = JSONObject.toJSONString(imageCellBeans);

        System.out.println("++++++++++1111+++++");
        System.out.println("rearms======"+rearms);

        result = instance.recogitionImage(rearms);


        System.out.println(222);
        System.out.println(result.toString());
        return result.toString();
    }


    @RequestMapping(value = "/detectByPos")
    @ResponseBody
    public String toDetect1() {

        //String result ="";
        List<Mq_ImgResult.ImgResultItem> result;
        DetectorLibrary instance = new DetectorLibrary();
        List<ImageCellBean> imageCellBeans = new ArrayList<ImageCellBean>();

        ImageCellBean cellBean = new ImageCellBean();
        cellBean.setCameraCode("001");
        cellBean.setImageUrl("/data/baohuqu/111.jpg");
        imageCellBeans.add(cellBean);

        ImageCellBean cellBean1 = new ImageCellBean();
        cellBean1.setCameraCode("001");
        cellBean1.setImageUrl("/data/baohuqu/222.jpg");
        imageCellBeans.add(cellBean1);

        ImageCellBean cellBean2 = new ImageCellBean();
        cellBean2.setCameraCode("001");
        cellBean2.setImageUrl("/data/baohuqu/333.jpg");
        imageCellBeans.add(cellBean2);

        String rearms = JSONObject.toJSONString(imageCellBeans);

        System.out.println("++++++++++1111+++++");
        System.out.println("rearms======"+rearms);

        result = instance.recogitionImagePos(rearms);


        System.out.println(222);
        System.out.println(result.toString());
        return result.toString();
    }


//
//    @RequestMapping(value = "/detect")
//    @ResponseBody
//    public String toDetect() {
//
//        String result ="";
//
//        DetectorLibrary instance = new DetectorLibrary();
//        ImageCellBean cellBean = new ImageCellBean();
//        cellBean.setCameraCode("001");
//        cellBean.setImageUrl("/data/fileServer/img/test.jpg");
//        result = instance.recogitionImage(JSONObject.toJSONString(cellBean));
//
//        String imagePath = "/data/fileServer/data/111.jpg";
//        System.out.println(222);
//        System.out.println(result);
//        return "200";
//    }
//
//
//    @RequestMapping(value = "/detectByPos")
//    @ResponseBody
//    public String toDetect1() {
//
//        String result ="";
//        DetectorLibrary instance = new DetectorLibrary();
//        ImageCellBean cellBean = new ImageCellBean();
//        cellBean.setCameraCode("001");
//        cellBean.setImageUrl("/data/fileServer/img/test.jpg");
//        result = instance.recogitionImagePos(JSONObject.toJSONString(cellBean));
//
//        String imagePath = "/data/fileServer/data/111.jpg";
//        System.out.println(222);
//        System.out.println(result);
//        return "200";
//    }


//
//    public static void main(String[] args) {
//
//        List<ImageCellBean> imageCellBeans = new ArrayList<ImageCellBean>();
//
//        ImageCellBean cellBean = new ImageCellBean();
//        cellBean.setCameraCode("001");
//        cellBean.setImageUrl("/data/fileServer/model/2019-11-01_141730_461.jpg");
//        imageCellBeans.add(cellBean);
//
//        ImageCellBean cellBean1 = new ImageCellBean();
//        cellBean1.setCameraCode("001");
//        cellBean1.setImageUrl("/data/fileServer/model/2019-11-01_141730_461.jpg");
//        imageCellBeans.add(cellBean1);
//
//        String res =  JSONObject.toJSONString(imageCellBeans);
//
//        System.out.println(res);
//
//
//        JSONArray array = JSONArray.parseArray(res);
//
//        System.out.println(array.get(0));
//
//        String jsonString = array.get(0).toString();
//
//
//        System.out.println(jsonString);
//
//
//    }



//
//
//    public static void main(String[] args) {
//
//        String result = "{'status':200,'msg':[{'cameraCode':001, 'imageUrl':'/data/fileServer/img/test.jpg', 'goods':[{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread2', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'}]}]}";
//
//        JSONObject object = JSONObject.parseObject(result);
//        JSONArray msgJson = (JSONArray) object.get("msg");
//        JSONObject msgObject = JSONObject.parseObject(msgJson.get(0).toString());
//
//        Mq_ImgResult imgResult = null;
//        imgResult = JSONUtil.toBean(result, Mq_ImgResult.class);
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
//        msgObject.put("goods",imageGoodsBeans);
//        object.put("msg",msgObject);
//
//        System.out.println(msgObject.toString());
//
//    }






    public static void main(String[] args) {

        List<String> returnStr = new ArrayList<String>();
        String test = "[{'cameraCode':001,'status':200, 'imageUrl':'/data/fileServer/img/test.jpg', 'goods':[{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread2', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'}]},{'cameraCode':001,'status':200, 'imageUrl':'/data/fileServer/img/test.jpg', 'goods':[{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread2', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'}]}]";
        System.out.println("test ====="+test);
        JSONArray list = JSONObject.parseArray(test);
        System.out.println("list.get(0) ====" + list.get(0));
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObj = JSONObject.parseObject(list.get(i).toString());
            //System.out.println(jsonObj.get("goods"));
            JSONArray list1 = JSONObject.parseArray(jsonObj.get("goods").toString());
            System.out.println(list1);
            if (jsonObj.get("goods") == null ) continue;
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (int j = 0; j <list1.size() ; j++) {
                JSONObject jsonObj1 = JSONObject.parseObject(list1.get(i).toString());
                System.out.println("svrCode===="+jsonObj1.get("svrCode"));
                Integer count = map.get(jsonObj1.get("svrCode"));
                map.put(jsonObj1.get("svrCode").toString(), (count == null) ? 1 : count + 1);
            }
            List<ImageGoodsBean> imageGoodsBeans = new ArrayList<ImageGoodsBean>();
            for(Map.Entry<String, Integer> entry : map.entrySet()){
                ImageGoodsBean cellBean = new ImageGoodsBean();
                cellBean.setSvrCode(entry.getKey());
                cellBean.setNumber(entry.getValue());
                imageGoodsBeans.add(cellBean);
            }
            jsonObj.put("goods",imageGoodsBeans);
            returnStr.add(jsonObj.toString());
        }

        System.out.println("returnStr=====>"+returnStr);
    }

}
