package com.cloud.cang.vis.ws;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.mq.message.Mq_ImgResult;
//import com.cloud.cang.vis.model.Mq_ImgResult;
import com.cloud.cang.vis.detector.DetectorLibrary;
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

        String result ="";

        DetectorLibrary instance = new DetectorLibrary();
        ImageCellBean cellBean = new ImageCellBean();
        cellBean.setCameraCode("001");
        cellBean.setImageUrl("/data/fileServer/img/test.jpg");
        result = instance.recogitionImageByFileName(JSONObject.toJSONString(cellBean));

        String imagePath = "/data/fileServer/data/111.jpg";
        System.out.println(222);
        System.out.println(result);
        return "200";
    }


    @RequestMapping(value = "/detectByPos")
    @ResponseBody
    public String toDetect1() {

        String result ="";
        DetectorLibrary instance = new DetectorLibrary();
        ImageCellBean cellBean = new ImageCellBean();
        cellBean.setCameraCode("001");
        cellBean.setImageUrl("/data/fileServer/img/test.jpg");
        result = instance.recogitionImageByFileNamePos(JSONObject.toJSONString(cellBean));

        String imagePath = "/data/fileServer/data/111.jpg";
        System.out.println(222);
        System.out.println(result);
        return "200";
    }



    public static void main(String[] args) {


        String result = "{'status':200,'msg':[{'cameraCode':001, 'imageUrl':'/data/fileServer/img/test.jpg', 'goods':[{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread1', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'},{'svrCode':'bread2', 'posx':'633', 'posy':'652', 'posw':'265', 'posh':'214', 'prob':'0.996023'}]}]}";

        JSONObject object = JSONObject.parseObject(result);
        JSONArray msgJson = (JSONArray) object.get("msg");
        JSONObject msgObject = JSONObject.parseObject(msgJson.get(0).toString());

        Mq_ImgResult imgResult = null;
        imgResult = JSONUtil.toBean(result, Mq_ImgResult.class);

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Mq_ImgResult.Goods temp : imgResult.getMsg().get(0).getGoods()) {
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
        msgObject.put("goods",imageGoodsBeans);
        object.put("msg",msgObject);

        System.out.println();

    }


}
