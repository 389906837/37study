package com.cloud.cang.act.ws;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.act.cr.service.ReportManService;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ReportMan;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by YLF on 2020/5/29.
 */
@Controller
@RequestMapping("/identificationMan")
public class IdentificationOper {
    private static final Logger logger = LoggerFactory.getLogger(IdentificationOper.class);
    @Autowired
    private ReportManService reportManService;


    /**
     * 接收识别信息
     *
     * @param originalImgFile  原始图片地址
     * @param processedImgFile 已处理图片地址
     * @param
     * @return
     */
    @RequestMapping("/receivePic")
    @ResponseBody
    public String saveReport(@RequestParam(value = "originalImgFile", required = false) MultipartFile originalImgFile,
                             @RequestParam(value = "processedImgFile", required = false) MultipartFile processedImgFile,
                             HttpServletRequest request
                            /*@RequestParam("scameraCode" )String scameraCode, @RequestParam("sidenResult" )String sidenResult*/) {
        /*String sidenResult = map.get("faceJson");
        String scameraCode = map.get("scameraCode");*/
        String cameraCode = request.getParameter("cameraCode");
        String idenResult = request.getParameter("idenResult");
        logger.info("识别结果：{}", JSON.toJSONString(idenResult));
        logger.info("识别相机编号：{}", JSON.toJSONString(cameraCode));
        String url;
        ReportMan reportMan = new ReportMan();
        reportMan.setScameraCode(cameraCode);
        reportMan.setSidenResult(idenResult);
        if (null != originalImgFile && originalImgFile.getSize() > 0) {
            // 1.文件上传
            url = uploadHome(originalImgFile, "licenserOriginal");
            reportMan.setSoriginalPicUrl(url);
        }
        if (null != processedImgFile && processedImgFile.getSize() > 0) {
            // 1.文件上传
            url = uploadHome(processedImgFile, "licenserProcessd");
            reportMan.setSprocessedPicUrl(url);
        }
        reportMan.setIdelFlag(0);
        reportMan.setScode(CoreUtils.newCode(EntityTables.CR_REPORT_MAN));
        reportMan.setTaddTime(DateUtils.getCurrentDateTime());
        reportManService.insert(reportMan);
        //转换post请求数据
      /*  PicManTran picManTran = TransformatMessage(picInfoMan);
        logger.info("发送图片信息参数：{}", JSON.toJSONString(picManTran));
        try {
            // 获取httpclient
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //创建post请求
            HttpPost httpPost = new HttpPost(url);
            // 设置请求
            String str = JSON.toJSONString(picManTran);
            StringEntity reqEntity = new StringEntity(
                    str, ContentType.create("application/json", "utf-8"));
            httpPost.setEntity(reqEntity);
            httpPost.addHeader("Accept", "application/json");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            // 得到响应信息
            response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            logger.info("发送图片信息异常：{}", e.getMessage());
        }*/
        return "OK";

    }

    private String uploadHome(MultipartFile file, String pathStr) {
        if (file == null) {
            throw new ServiceException("没有找到上传文件");
        }
        //文件大小限制
      /*  if (111 < file.getSize()) {
        }*/
        //文件原名
        String filName = file.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";
        try {
            String serverPath = pathStr + "/" + DateUtils.dateParseShortString(new Date()) + "/";
            String fileFileName = file.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return null;
    }

}
