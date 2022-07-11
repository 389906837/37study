package com.cloud.cang.rec.ws.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.lock.JedisLock;
import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.rec.common.Constrants;
import com.cloud.cang.rec.common.EnviornConfig;
import com.cloud.cang.rec.common.HttpClientUtil;
import com.cloud.cang.rec.cr.service.CameraInfoService;
import com.cloud.cang.rec.ws.crypto.CryptoFactory;
import com.cloud.cang.rec.ws.domain.LicenseInfo;
import com.cloud.cang.rec.ws.domain.LicenseParamInfo;
import com.cloud.cang.rec.ws.entity.RequestEntity;
import com.cloud.cang.rec.ws.entity.ResponseEntity;
import com.cloud.cang.rec.ws.util.FileUtil;
import com.cloud.cang.utils.DateUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by YLF on 2020/5/26.
 */
@Component
public class LicenseOper {
    private static final Logger logger = LoggerFactory.getLogger(LicenseOper.class);
    @Autowired
    private ICached iCached;
    @Autowired
    EnviornConfig enviornConfig;
    JedisLock tokenLock = null;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    private CameraInfoService cameraInfoService;
    private boolean isSmsRun = true;
    static int EMAIL_DEFAULT_EXPIRY_TIME = new Long(TimeUnit.MINUTES.toMillis(5)).intValue();

    //@PostConstruct
    public void init() {
        logger.info("LicenseOper任务定时发送启动......");
      /*  if (enviornConfig.isProduct()) {*/
        tokenLock = new JedisLock(jedisCluster, "lock_sms_login", 10000, 10000);

        loginSchedule();
/*        }*/
    }

   /* public static void main(String[] args) {
        try {
            String string = " { \"auth\": { \"identity\": { \"methods\": [ \"password\"], \"password\": { \"user\": { \"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\", \"password\": \"z1987622\",\"domain\": {\"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\"}}}},\"scope\": {\"project\": {\"id\": \"0855ec2ee18026232ffdc01a2a073cad\"}}}}";

            String tokenValue = HttpClientUtil.sendPostGetToken("https://iam.cn-north-4.myhuaweicloud.com/v3/auth/tokens", string, 2, "X-Subject-Token");

            System.out.println(tokenValue);
            if (StringUtils.isNotBlank(tokenValue)) {
                String getResult = HttpClientUtil.sendGetAddHeader("https://holosensdev.top:9054/license/list", "x-auth-token", tokenValue);
                LicenseInfo licenseInfo = JSONObject.parseObject(getResult, LicenseInfo.class);
                System.out.println(licenseInfo);
                Integer totalCount = Integer.valueOf(licenseInfo.getTotalCount());
                System.out.println(totalCount);
                if (totalCount > 0) {
                    List<LicenseParamInfo> licenseParamInfos = licenseInfo.getLicenseParamLists();
                    licenseParamInfos.forEach((li) ->
                            {
                                String url = "https://holosensdev.top:9054/license/upload/" + li.getConsumptionId();
                                RequestEntity request = new RequestEntity();
                                request.setAppName(li.getAppName());
                                request.setAppVersion(li.getAppVersion());
                                request.setBuyerID(li.getBuyerID());
                                request.setLicenseType(li.getLicenseType());
                                request.setRequestAmount(li.getRequestAmount());
                                request.setRequestContent(li.getRequestContent());
                                request.setRequestDays(li.getRequestDays());
                                request.setRequestType(li.getRequestType());
                                request.setAllocalTable(li.getAllocalTable());

                                ResponseEntity responseEntity = execute(request);
                                //判断文件夹是否存在
                                FileUtil.judeDirExists(new File("D:/license"));
                                //设置当前时间的格式，为年-月-日
                                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
                                String cfgData =
                                        "AppName=" + responseEntity.getAppName() + "\r\n" +
                                                "AppVersion=" + responseEntity.getAppVersion() + "\r\n" +
                                                "Vender=HUAWEI" + "\r\n" +
                                                "LicenseType=" + responseEntity.getLicenseType() + "\r\n" +
                                                "ExpiredDays=" + responseEntity.getExpiredDays() + "\r\n" +
                                                "AuthorizationDate=" + dateFormat.format(new Date()) + "\r\n" +
                                                "SerialNumber=" + responseEntity.getSerialNumber();
                                String name = li.getAppName() + "_app" + li.getAppName() + "_app" + li.getAppVersion() + "license.lic";
                                FileUtil.clearFile("D:/license/" + name);
                                FileUtil.appendStringToFile("D:/license/" + name, cfgData);

                                File file = new File("D:/license/" + name);
                                try {
                                    FileInputStream inputStream = new FileInputStream(file);
                                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                                            ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                                    Map<String, MultipartFile> multipartFileMap = new HashMap<>();
                                    multipartFileMap.put("file", multipartFile);
                                    String result = HttpClientUtil.sendPostAddHeaderAndFile(url, "", 1, "x-auth-token", tokenValue, multipartFileMap);
                                    System.out.println(result);
                                } catch (Exception e) {

                                }
                            }
                    );
                }
            }
        } catch (Exception e) {

        }
    }*/

    private void loginSchedule() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("自建License服务器定时任务启动......");
                do {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    try {
                        if (tokenLock.acquire()) {
                            logger.debug("获取到reids lock,开发者自建 License 服务器开始,size:");
                            String string = " { \"auth\": { \"identity\": { \"methods\": [ \"password\"], \"password\": { \"user\": { \"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\", \"password\": \"z1987622\",\"domain\": {\"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\"}}}},\"scope\": {\"project\": {\"id\": \"0855ec2ee18026232ffdc01a2a073cad\"}}}}";
                            String tokenValue = HttpClientUtil.sendPostGetToken("https://iam.cn-north-4.myhuaweicloud.com/v3/auth/tokens", string, -1, "X-Subject-Token");
                            logger.info("login 认证获取 token返回：{}", tokenValue);
                            if (StringUtils.isNotBlank(tokenValue)) {
                                String getResult = HttpClientUtil.sendGetAddHeader("https://holosensdev.top:9054/license/list", "x-auth-token", tokenValue);
                                LicenseInfo licenseInfo = JSONObject.parseObject(getResult, LicenseInfo.class);
                                logger.info("HoloSen Store查询license列表返回：{}", JSON.toJSONString(licenseInfo));
                                Integer totalCount = Integer.valueOf(licenseInfo.getTotalCount());
                                if (null != totalCount && totalCount > 0) {
                                    List<LicenseParamInfo> licenseParamInfos = licenseInfo.getLicenseParamLists();
                                    licenseParamInfos.forEach((li) ->
                                            {   //新增新的设备信息
                                                CameraInfo cameraInfo = new CameraInfo();
                                                Date nowDate = DateUtils.getCurrentDateTime();
                                                if (StringUtils.isNotBlank(li.getRequestType()) && "new".equals(li.getRequestType())) {
                                                    if ("commercial".equals(li.getLicenseType())) {
                                                        //商用
                                                        cameraInfo.setIisCommercial(1);
                                                    } else if ("trial".equals(li.getLicenseType())) {
                                                        //试用 设置过期时间
                                                        cameraInfo.setIisCommercial(0);
                                                        String requestDays = li.getRequestDays();
                                                        cameraInfo.setTexpiresTime(DateUtils.addDays(nowDate, Integer.valueOf(requestDays)));
                                                    }
                                                    cameraInfo.setScode(li.getAllocalTable());
                                                    cameraInfo.setIdelFlag(0);
                                                    cameraInfo.setTaddTime(nowDate);
                                                    cameraInfo.setTregisterTime(nowDate);
                                                    cameraInfo.setIstatus(10);
                                                    cameraInfoService.insert(cameraInfo);
                                                } else if (StringUtils.isNotBlank(li.getRequestType()) && "change".equals(li.getRequestType())) {
                                                    //变更设备信息


                                                }
                                                String url = "https://holosensdev.top:9054/license/upload/" + li.getConsumptionId();
                                                RequestEntity request = new RequestEntity();
                                                request.setAppName(li.getAppName());
                                                request.setAppVersion(li.getAppVersion());
                                                request.setBuyerID(li.getBuyerID());
                                                request.setLicenseType(li.getLicenseType());
                                                request.setRequestAmount(li.getRequestAmount());
                                                request.setRequestContent(li.getRequestContent());
                                                request.setRequestDays(li.getRequestDays());
                                                request.setRequestType(li.getRequestType());
                                                request.setAllocalTable(li.getAllocalTable());
                                                ResponseEntity responseEntity = execute(request);
                                                //判断文件夹是否存在
                                                FileUtil.judeDirExists(new File("D:/license"));
                                                //设置当前时间的格式，为年-月-日
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
                                                String cfgData =
                                                        "AppName=" + responseEntity.getAppName() + "\r\n" +
                                                                "AppVersion=" + responseEntity.getAppVersion() + "\r\n" +
                                                                "Vender=HUAWEI" + "\r\n" +
                                                                "LicenseType=" + responseEntity.getLicenseType() + "\r\n" +
                                                                "ExpiredDays=" + responseEntity.getExpiredDays() + "\r\n" +
                                                                "AuthorizationDate=" + dateFormat.format(new Date()) + "\r\n" +
                                                                "SerialNumber=" + responseEntity.getSerialNumber();
                                                String name = li.getAppName() + "_app" + li.getAppName() + "_app" + li.getAppVersion() + "license.lic";
                                                FileUtil.clearFile("D:/license/" + name);
                                                FileUtil.appendStringToFile("D:/license/" + name, cfgData);
                                                File file = new File("D:/license/" + name);
                                                try {
                                                    FileInputStream inputStream = new FileInputStream(file);
                                                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                                                            ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                                                    Map<String, MultipartFile> multipartFileMap = new HashMap<>();
                                                    multipartFileMap.put("file", multipartFile);
                                                    String result = HttpClientUtil.sendPostAddHeaderAndFile(url, "", 1, "x-auth-token", tokenValue, multipartFileMap);
                                                    logger.info("生成的 license 文件回传返回内容：{}", result);
                                                } catch (Exception e) {
                                                    logger.info("生成的 license 文件回传异常：{}", e);
                                                }
                                            }
                                    );
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        tokenLock.release();
                    }
                } while (isSmsRun);

            }
        });

    }

    /**
     * 追加文件
     *
     * @param request
     * @return
     */
    public static ResponseEntity execute(RequestEntity request) {
        CryptoFactory cryptoFactory = CryptoFactory.getInstance();

        ResponseEntity response = new ResponseEntity();
        response.setAppName(request.getAppName());
        response.setAppVersion(request.getAppVersion());
        response.setBuyerID(request.getBuyerID());
        response.setExpiredDays(request.getRequestDays());
        response.setLicenseType(request.getLicenseType());
        response.setMessage("");
        response.setResultCode("101");
        response.setSerialNumber(cryptoFactory.getEncStringMD5(request.getAllocalTable()).toString());
        return response;
    }


    /*
        public static String jsonFormat(String jsonString) {
            JSONObject object = JSONObject.parseObject(jsonString);
            jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
            return jsonString;
        }
    */
    @PreDestroy
    public void destroy() {
        isSmsRun = false;
    }
}
