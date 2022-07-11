package com.cloud.cang.vis.ws;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.vis.detector.DetectorLibrary;
import com.cloud.cang.vis.model.ImageCellBean;
import com.cloud.cang.vis.model.InitServerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试视觉操作
 */
@RestController
@RequestMapping("/test")
@RegisterRestResource
public class TestDetectService {

    private final static Logger logger = LoggerFactory.getLogger(TestDetectService.class);

    @RequestMapping(value = "/init")
    public ResponseVo<String> init() {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            DetectorLibrary instance = new DetectorLibrary();
            String modelPath = "/data/fileServer/model/compilation.bmodel";
            String nameCfg = "/data/fileServer/model/37.names";
            InitServerDto build = InitServerDto.builder()
                    .modelPath(modelPath)
                    .nameCfg(nameCfg)
                    .classes(10)
                    .istiny(0)
                    .thresh(0.9f)
                    .build();
            int status = instance.loadRecogitionServer(JSONUtil.toJsonStr(build));
            logger.info("initServer is status =====>>>>>>>{}", status);
            List<ImageCellBean> imageCellBeans = new ArrayList<ImageCellBean>();
            ImageCellBean cellBean = new ImageCellBean();
            cellBean.setCameraCode("001");
            cellBean.setImageUrl("/data/fileServer/model/2019-11-01_141730_461.jpg");
            imageCellBeans.add(cellBean);
            String reuslt = instance.recogitionImageByFileName(JSONObject.toJSONString(imageCellBeans));
            logger.info("recogitionImageByFileName is result =====>>>>>>>{}", reuslt);

            reuslt = instance.recogitionImageByFileNamePos(JSONObject.toJSONString(imageCellBeans));
            logger.info("recogitionImageByFileNamePos is result =====>>>>>>>{}", reuslt);

            return responseVo;
        } catch (Exception e) {
            logger.error("测试报错异常：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setMsg("测试报错，请重新操作");
        return responseVo;
    }

//    周红:
//    recogitionImageByFileName is result =====>>>>>>>
//    [{"cameraCode":"001","status":"200","imageUrl":"/data/fileServer/model/2019-11-01_141730_461.jpg","goods":[{"svrCode":"6928804011173","number":10},{"svrCode":"6928804010220","number":10},{"svrCode":"6925303754884","number":10},{"svrCode":"6902538004045","number":8}]}]
//
//    周红:
//    recogitionImageByFileNamePos is result =====>>>>>>>
//    [{"cameraCode":"001","status":"200","imageUrl":"/data/fileServer/model/2019-11-01_141730_461.jpg","goods":[{"svrCode":"6928804011173","posx":"490","posy":"164","posw":"59","posh":"51","prob":"0.999995"},{"svrCode":"6928804011173","posx":"472","posy":"302","posw":"55","posh":"51","prob":"0.999991"},{"svrCode":"6928804011173","posx":"531","posy":"120","posw":"25","posh":"36","prob":"0.999984"},{"svrCode":"6928804011173","posx":"482","posy":"242","posw":"62","posh":"48","prob":"0.999983"},{"svrCode":"6928804011173","posx":"478","posy":"105","posw":"56","posh":"51","prob":"0.999983"},{"svrCode":"6928804011173","posx":"511","posy":"79","posw":"27","posh":"27","prob":"0.999907"},{"svrCode":"6928804011173","posx":"517","posy":"225","posw":"51","posh":"33","prob":"0.999900"},{"svrCode":"6928804011173","posx":"548","posy":"171","posw":"19","posh":"36","prob":"0.999665"},{"svrCode":"6928804011173","posx":"518","posy":"289","posw":"40","posh":"29","prob":"0.999564"},{"svrCode":"6928804011173","posx":"469","posy":"61","posw":"47","posh":"40","prob":"0.999425"},{"svrCode":"6902538004045","posx":"203","posy":"210","posw":"101","posh":"76","prob":"0.999991"},{"svrCode":"6902538004045","posx":"156","posy":"61","posw":"60","posh":"53","prob":"0.999990"},{"svrCode":"6902538004045","posx":"210","posy":"110","posw":"92","posh":"93","prob":"0.999987"},{"svrCode":"6902538004045","posx":"124","posy":"191","posw":"83","posh":"62","prob":"0.999981"},{"svrCode":"6902538004045","posx":"224","posy":"44","posw":"73","posh":"72","prob":"0.999981"},{"svrCode":"6902538004045","posx":"140","posy":"115","posw":"68","posh":"71","prob":"0.999976"},{"svrCode":"6902538004045","posx":"223","posy":"284","posw":"81","posh":"102","prob":"0.999956"},{"svrCode":"6902538004045","posx":"138","posy":"264","posw":"72","posh":"67","prob":"0.999904"},{"svrCode":"6928804010220","posx":"97","posy":"99","posw":"43","posh":"44","prob":"0.999984"},{"svrCode":"6928804010220","posx":"79","posy":"160","posw":"68","posh"


    }
