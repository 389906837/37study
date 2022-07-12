package com.cloud.cang.mgr.sl.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.sl.domain.OperatelogDomain;
import com.cloud.cang.mgr.sl.service.DeviceOperService;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.mq.message.Mq_Cloud_ImgResul;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 设备操作日志
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-15 20:51:00
 */
@Controller
@RequestMapping("/deviceLog")
public class DeviceOperlogController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(DeviceOperlogController.class);

    // 注入serv
    @Autowired
    private DeviceOperService deviceOperService;

    @Autowired
    private OperatorService operatorService;

    /**
     * 设备操作日志
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sl/deviceLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<OperatelogDomain>> queryDataLog(ParamPage paramPage, DeviceLogVo deviceLogVo) {
        PageListVo<List<OperatelogDomain>> pageListVo = new PageListVo<List<OperatelogDomain>>();
        Page<OperatelogDomain> page = new Page<OperatelogDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 130);
        deviceLogVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceLogVo.setOrderStr("SDO." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = deviceOperService.deviceOperLog(page, deviceLogVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 查看开门图片
     *
     * @param sid 退款订单Id
     * @return
     */
    @RequestMapping("/viewPic")
    public String viewPic(ModelMap map, String sid) {
        try {
            DeviceOper deviceOper = deviceOperService.selectByPrimaryKey(sid);
            if (null == deviceOper) {
                deviceOper = new DeviceOper();
            }
            String jsonUrl = deviceOper.getSopenPicUrl();
            List<Mq_Cloud_ImgResul.Mq_Cloud_Img> openPics = null;
            if (StringUtil.isNotBlank(jsonUrl)) {
                openPics = JSON.parseObject(jsonUrl, new TypeReference<List<Mq_Cloud_ImgResul.Mq_Cloud_Img>>() {
                });
            }
            jsonUrl = deviceOper.getSclosePicUrl();
            List<Mq_Cloud_ImgResul.Mq_Cloud_Img> closePics = null;
            if (StringUtil.isNotBlank(jsonUrl)) {
                closePics = JSON.parseObject(jsonUrl, new TypeReference<List<Mq_Cloud_ImgResul.Mq_Cloud_Img>>() {
                });
            }
            if (null != openPics && !openPics.isEmpty()) {
                Collections.sort(openPics, new Comparator<Mq_Cloud_ImgResul.Mq_Cloud_Img>() {
                    @Override
                    public int compare(Mq_Cloud_ImgResul.Mq_Cloud_Img o1, Mq_Cloud_ImgResul.Mq_Cloud_Img o2) {
                        return o1.getCameraCode().compareTo(o2.getCameraCode());
                    }
                });
            }
            if (null != closePics && !closePics.isEmpty()) {
                Collections.sort(closePics, new Comparator<Mq_Cloud_ImgResul.Mq_Cloud_Img>() {
                    @Override
                    public int compare(Mq_Cloud_ImgResul.Mq_Cloud_Img o1, Mq_Cloud_ImgResul.Mq_Cloud_Img o2) {
                        return o1.getCameraCode().compareTo(o2.getCameraCode());
                    }
                });
            }
            if (null != openPics && !openPics.isEmpty() && null != closePics && !closePics.isEmpty()) {
                for (int i = 0; i < openPics.size(); i++) {
                    openPics.get(i).setCloseImgUrl(closePics.get(i).getImageUrl());
                }
            } else if ((null == openPics || openPics.isEmpty()) && (null != closePics && !closePics.isEmpty())) {
                openPics = new ArrayList<>();
                for (int i = 0; i < closePics.size(); i++) {
                    Mq_Cloud_ImgResul.Mq_Cloud_Img mq_cloud_img = new Mq_Cloud_ImgResul.Mq_Cloud_Img();
                    mq_cloud_img.setCameraCode(closePics.get(i).getCameraCode());
                    mq_cloud_img.setCloseImgUrl(closePics.get(i).getImageUrl());
                    openPics.add(mq_cloud_img);
                }
            }
            map.put("openPics", openPics);
            return "sl/deviceLog-viewOpenPic";
        } catch (Exception e) {
            log.error("查看设备开关门图片对比异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

}
