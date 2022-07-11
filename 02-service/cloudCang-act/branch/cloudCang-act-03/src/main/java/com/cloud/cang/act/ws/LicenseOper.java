package com.cloud.cang.act.ws;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.act.common.EnviornConfig;
import com.cloud.cang.act.common.HttpUtil.HttpClientUtil;
import com.cloud.cang.act.cr.service.CameraInfoService;
import com.cloud.cang.act.ws.domain.LicenseInfo;
import com.cloud.cang.act.ws.domain.LicenseParamInfo;
import com.cloud.cang.act.ws.entity.RequestEntity;
import com.cloud.cang.act.ws.entity.ResponseEntity;
import com.cloud.cang.act.ws.util.FileUtil;
import com.cloud.cang.act.ws.util.RSAUtil;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.lock.JedisLock;
import com.cloud.cang.model.cr.CameraInfo;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by YLF on 2020/5/26.
 */
@Component
public class LicenseOper {
    private static final Logger logger = LoggerFactory.getLogger(LicenseOper.class);
    @Autowired
    EnviornConfig enviornConfig;
    @Autowired
    ICached iCached;

    JedisLock licensLock = null;
    JedisLock cameraLock = null;
    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    private CameraInfoService cameraInfoService;
    private boolean isLicenseRun = true;
    private boolean isCameraRun = true;

    static int EMAIL_DEFAULT_EXPIRY_TIME = new Long(TimeUnit.MINUTES.toMillis(5)).intValue();

    @PostConstruct
    public void init() {
        if (enviornConfig.isProduct()) {
            licensLock = new JedisLock(jedisCluster, "lock_license", JedisLock.DEFAULT_ACQUIRE_TIMEOUT_MILLIS, EMAIL_DEFAULT_EXPIRY_TIME);
            cameraLock = new JedisLock(jedisCluster, "lock_license", JedisLock.DEFAULT_ACQUIRE_TIMEOUT_MILLIS, EMAIL_DEFAULT_EXPIRY_TIME);
            licenseSchedule();
            cameraSchedule();
        }
     /*   licensLock = new JedisLock(jedisCluster, "lock_license", JedisLock.DEFAULT_ACQUIRE_TIMEOUT_MILLIS, EMAIL_DEFAULT_EXPIRY_TIME);
        cameraLock = new JedisLock(jedisCluster, "lock_license");
        licenseSchedule();
        cameraSchedule();*/
    }

    private void licenseSchedule() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("自建License服务器定时任务启动......:{}", DateUtils.getCurrentDateTime());
                do {
                    try {
                        TimeUnit.SECONDS.sleep(30);//30秒发起一次请求
                    } catch (InterruptedException e) {
                    }
                    try {
                        if (licensLock.acquire()) {
                            logger.info("获取到reids lock,开发者自建 License 服务器开始");
                            String tokenValue = (String) iCached.get("LICENSE_TOKEN");
                            //  String string = " { \"auth\": { \"identity\": { \"methods\": [ \"password\"], \"password\": { \"user\": { \"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\", \"password\": \"z1987622\",\"domain\": {\"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\"}}}},\"scope\": {\"project\": {\"id\": \"0855ec2ee18026232ffdc01a2a073cad\"}}}}";
                            //   String tokenValue = HttpClientUtil.sendPostGetToken("https://iam.cn-north-4.myhuaweicloud.com/v3/auth/tokens", string, -1, "X-Subject-Token");
                            if (StringUtils.isBlank(tokenValue)) {
                                String string = " { \"auth\": { \"identity\": { \"methods\": [ \"password\"], \"password\": { \"user\": { \"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\", \"password\": \"z1987622\",\"domain\": {\"name\": \"AEB1882847F94BAE9ACCE4FAFFB80B40\"}}}},\"scope\": {\"project\": {\"id\": \"0855ec2ee18026232ffdc01a2a073cad\"}}}}";
                                String url = GrpParaUtil.getValue("SP000183", "login_authentication");
                                tokenValue = HttpClientUtil.sendPostGetToken(url, string, -1, "X-Subject-Token");
                                iCached.put("LICENSE_TOKEN", tokenValue, 60 * 60 * 23);
                            }
                            if (StringUtils.isNotBlank(tokenValue)) {
                                String url = GrpParaUtil.getValue("SP000183", "query_license");
                                String getResult = HttpClientUtil.sendGetAddHeader(url, "x-auth-token", tokenValue);
                                LicenseInfo licenseInfo = JSONObject.parseObject(getResult, LicenseInfo.class);
                                logger.info("HoloSen Store查询license列表返回：{}", JSON.toJSONString(licenseInfo));
                               /* Integer totalCount = Integer.valueOf(licenseInfo.getTotalCount());*/
                                // List<LicenseParamInfo> licenseParamInfos = licenseInfo.getLicenseParamLists();
                                //  if (null != totalCount && totalCount > 0) {
                                if (null != licenseInfo && !CollectionUtils.isEmpty(licenseInfo.getLicenseParamLists())) {
                                    List<LicenseParamInfo> licenseParamInfos = licenseInfo.getLicenseParamLists();
                                    String finalTokenValue = tokenValue;
                                    licenseParamInfos.forEach((li) ->
                                            {   //新增新的设备信息
                                                Date nowDate = DateUtils.getCurrentDateTime();
                                                String allocalTable = li.getAllocalTable();
                                                //List<String> asList = Arrays.asList(allocalTable.split(";"));
                                                List<String> asList = Arrays.stream(allocalTable.split(";")).collect(Collectors.toList());
                                                if (StringUtils.isNotBlank(li.getRequestType()) && "new".equals(li.getRequestType())) {
                                                    if (CollectionUtil.isNotEmpty(asList)) {
                                                        asList.forEach(allocalTableTe -> {
                                                            String cameraCode = allocalTableTe.split("=")[0];
                                                            CameraInfo cameraInfo = new CameraInfo();
                                                            if ("commercial".equals(li.getLicenseType())) {
                                                                //商用
                                                                cameraInfo.setIisCommercial(1);
                                                            } else if ("trial".equals(li.getLicenseType())) {
                                                                //试用 设置过期时间
                                                                cameraInfo.setIisCommercial(0);
                                                                String requestDays = li.getRequestDays();
                                                                cameraInfo.setTexpiresTime(DateUtils.addDays(nowDate, Integer.valueOf(requestDays)));
                                                            }
                                                            // upCameraLicenseType(cameraInfo, li, nowDate);
                                                            cameraInfo.setScode(cameraCode);
                                                            cameraInfo.setIdelFlag(0);
                                                            cameraInfo.setTaddTime(nowDate);
                                                            cameraInfo.setTupdateTime(nowDate);
                                                            // cameraInfo.setTregisterTime(nowDate);
                                                            cameraInfo.setIstatus(20);
                                                            cameraInfoService.insert(cameraInfo);
                                                        });
                                                    }
                                                } else if (StringUtils.isNotBlank(li.getRequestType()) && "change".equals(li.getRequestType())) {
                                                    //变更设备信息
                                                    if (CollectionUtil.isNotEmpty(asList)) {
                                                        asList.forEach(allocalTableTe -> {
                                                            String cameraCode = allocalTableTe.split("=")[0];
                                                            CameraInfo cameraInfo = cameraInfoService.selectByCode(cameraCode);
                                                            if (null != cameraInfo) {
                                                                CameraInfo temp = new CameraInfo();
                                                                temp.setId(cameraInfo.getId());
                                                                if ("commercial".equals(li.getLicenseType()) && null != cameraInfo.getIisCommercial() && cameraInfo.getIisCommercial() == 0) {
                                                                    //商用
                                                                    temp.setIisCommercial(1);
                                                                } else if ("trial".equals(li.getLicenseType()) && null != cameraInfo.getIisCommercial() && cameraInfo.getIisCommercial() == 1) {
                                                                    //试用 设置过期时间
                                                                    temp.setIisCommercial(0);
                                                                    String requestDays = li.getRequestDays();
                                                                    temp.setTexpiresTime(DateUtils.addDays(nowDate, Integer.valueOf(requestDays)));
                                                                }
                                                                temp.setTupdateTime(nowDate);
                                                                cameraInfoService.updateBySelective(temp);
                                                            } else {
                                                                logger.info("变更设备本地数据库不存在：{}", cameraCode);
                                                                //新增
                                                            }
                                                        });
                                                    }
                                                }
                                                //  String url = "https://holosensdev.top:9054/license/upload/" + li.getConsumptionId();
                                                // String url = "https://holosensstore.cn-north-4.myhuaweicloud.com/v1/holosens/consumptions/" + li.getConsumptionId() + "/upload_license";
                                                String urlTemp = GrpParaUtil.getValue("SP000183", "license_callback");
                                                urlTemp = urlTemp.replace("{consumption_id}", li.getConsumptionId().toString());
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
                                                ResponseEntity responseEntity = null;
                                                String vendor = "cang_ai";
                                                try {
                                                    responseEntity = execute(request, vendor);
                                                } catch (Exception e) {
                                                    logger.error("生成licens文件加密异常：{}", e);
                                                    return;
                                                }
                                                //判断文件夹是否存在
                                                // String path = System.getProperty("catalina.home") + File.separator + li.getAppName() + "_" + li.getAllocalTable().split("=")[0];
                                                String path = System.getProperty("catalina.home") + File.separator + "LicenseFile" + File.separator + DateUtils.getCurrentTimeNumber() + "_" + li.getItemId() + "_" + li.getBuyerID();
                                                FileUtil.judeDirExists(new File(path));                                                //设置当前时间的格式，为年-月-日
                                                String cfgData =
                                                        "AppName=" + responseEntity.getAppName() + "\r\n" +
                                                                "AppVersion=" + responseEntity.getAppVersion() + "\r\n" +
                                                                "Vendor=" + vendor + "\r\n" +
                                                                "LicenseType=" + responseEntity.getLicenseType() + "\r\n" +
                                                                "ExpiredDays=" + responseEntity.getExpiredDays() + "\r\n" +
                                                                "AuthorizationDate=" + responseEntity.getAuthorizationDate() + "\r\n" +
                                                                "HardwareID=" + responseEntity.getAllocalTable() + "\r\n" +
                                                                "SerialNumber=" + responseEntity.getSerialNumber();
                                                String fileName = path + File.separator + "license.lic";
                                                FileUtil.clearFile(fileName);
                                                FileUtil.appendStringToFile(fileName, cfgData);
                                                File file = new File(fileName);
                                                try {
                                                    FileInputStream inputStream = new FileInputStream(file);
                                                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                                                            ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                                                    Map<String, MultipartFile> multipartFileMap = new HashMap<>();
                                                    multipartFileMap.put("file", multipartFile);
                                                    String result = HttpClientUtil.sendPostAddHeaderAndFile(urlTemp, "", 1, "x-auth-token", finalTokenValue, multipartFileMap);
                                                    logger.info("生成的 license 文件回传成功,返回内容：{}", result);
                                                } catch (Exception e) {
                                                    logger.info("生成的 license 文件回传异常：{}", e);
                                                    return;
                                                }
                                            }
                                    );
                                }
                            } else {
                                logger.info("login获取token失败");
                            }
                        }
                    } catch (ServiceException se) {
                        logger.error("自建License服务器发送请求异常：{}", se);
                    } catch (Exception e) {
                        logger.error("自建License服务器定时任务异常：{}", e);
                    } finally {
                        licensLock.release();
                    }
                } while (isLicenseRun);

            }
        });

    }


    /**
     * 相机心跳检测定时任务
     */
    private void cameraSchedule() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("相机心跳检测定时任务启动......");
                do {
                    try {
                        TimeUnit.SECONDS.sleep(60 * 1);//休眠300秒
                    } catch (InterruptedException e) {
                    }
                    try {
                        logger.info("相机心跳检测定时任务开始");
                        //查询相机列表 便利相机 检测心跳
                        CameraInfo temp = new CameraInfo();
                        temp.setIdelFlag(0);
                        List<CameraInfo> cameraInfoList = cameraInfoService.selectByEntityWhere(temp);
                        if (!CollectionUtils.isEmpty(cameraInfoList)) {
                            cameraInfoList.forEach(cameraInfoTemp -> {
                                String code = cameraInfoTemp.getScode();
                                try {
                                    // String heart = (String) iCached.get("HEART_DETECTION_" + code);
                                    Date date = (Date) iCached.hget("CAMERA_INFO", "HEART_DETECTION_" + code);
                                    if (null == date) {
                                        logger.info("相机心跳检测定时任务缓存为空：{}", code);
                                        if (cameraInfoTemp.getIstatus() != null && cameraInfoTemp.getIstatus() == 10) {
                                            //相机设置为离线
                                            upCameraStatus(cameraInfoTemp.getId(), 20);
                                        }
                                        return;
                                    }
                                    long sunMinute = DateUtils.subMinute(date);
                                    if (sunMinute >= 10 && null != cameraInfoTemp.getIstatus() && cameraInfoTemp.getIstatus() == 10) {
                                        //相机设置为离线
                                        upCameraStatus(cameraInfoTemp.getId(), 20);
                                    } else if (sunMinute < 10 && null != cameraInfoTemp.getIstatus() && cameraInfoTemp.getIstatus() == 20) {
                                        //相机设置为在线
                                        upCameraStatus(cameraInfoTemp.getId(), 10);
                                    }
                                } catch (Exception e) {
                                    logger.info("获取相机心跳缓存异常：{}", e);
                                }
                            });
                        }
                    } catch (Exception e) {
                        logger.error("相机心跳检测定时任务异常：{}", e);
                    } finally {
                        cameraLock.release();
                    }
                } while (isCameraRun);

            }
        });
    }

    /**
     * 更新相机状态
     *
     * @param id
     * @param status
     */
    private void upCameraStatus(String id, Integer status) {
        CameraInfo cameraInfo = new CameraInfo();
        cameraInfo.setId(id);
        cameraInfo.setIstatus(status);
        cameraInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        cameraInfoService.updateBySelective(cameraInfo);
    }

    /**
     * 追加文件
     *
     * @param request
     * @return
     */
    public static ResponseEntity execute(RequestEntity request, String vendor) throws Exception {
        ResponseEntity response = new ResponseEntity();
        response.setAppName(request.getAppName());
        response.setAppVersion(request.getAppVersion());
        response.setBuyerID(request.getBuyerID());
        response.setExpiredDays(request.getRequestDays());
        response.setLicenseType(request.getLicenseType());
        response.setMessage("");
        response.setResultCode("101");
        String allocalTable = request.getAllocalTable();
        if (StringUtils.isNotBlank(allocalTable)) {
            allocalTable = allocalTable.replaceAll("=1;", "-");
            allocalTable = allocalTable.replaceAll("=1", "");
            response.setAllocalTable(allocalTable);
        }
        //设置当前时间的格式，为年-月-日
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
        String date = dateFormat.format(new Date());
        response.setAuthorizationDate(date);
        response.setSerialNumber(creatEncryptionData(request.getAppName(), request.getAppVersion(), vendor, request.getLicenseType(), request.getRequestDays(), date, allocalTable));
        return response;
    }

    /**
     * 生成加密后的数据
     *
     * @param appName
     * @param appVersion
     * @param vendor
     * @param licenseType
     * @param expiredDays
     * @param date
     * @param allocTable
     * @return
     * @throws Exception
     */
    private static String creatEncryptionData(String appName, String appVersion, String vendor, String
            licenseType, String expiredDays, String date, String allocTable) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("AppName", appName);
        map.put("AppVersion", appVersion);
        map.put("Vendor", vendor);
        map.put("LicenseType", licenseType);
        map.put("ExpiredDays", expiredDays);
        if (StringUtils.isNotBlank(allocTable)) {
            String temp = allocTable.replaceAll("-", ";");
            map.put("hardware", temp);
        }
        map.put("AuthorizationDate", date);
        //RSA PEM文件签名
        String privateKeyPath = GrpParaUtil.getValue("SP000183", "license_privatekey");
        //String privateKeyPath = "H:\\rsa_private_key_01.pem";
        PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyPath);
        String sign = RSAUtil.doSignBySHA256withRSA(getOpen(map), privateKey);
        return sign;
    }


    public static void main(String[] args) {
        File file = new File("E:\\license\\license.txt");
        txt2String(file);
    }

    /**
     * 创建license
     */
    private static void creatLicense(String licenseType, String requestDays, String allocalTable, String path) {
        RequestEntity request = new RequestEntity();
        request.setAppName("species_protection");
        request.setAppVersion("1.0.0");
        request.setBuyerID("29388814803a4056aa16c13947602bde");
        request.setLicenseType(licenseType);
        request.setRequestAmount("36");
        request.setRequestContent("SerialNumber");
        request.setRequestDays(requestDays);
        request.setRequestType("new");
        request.setAllocalTable(allocalTable);
        ResponseEntity responseEntity = null;
        String vendor = "cang_ai";
        try {
            responseEntity = execute(request, vendor);
        } catch (Exception e) {
            logger.error("加密异常：{}", e);
        }
        //判断文件夹是否存在
        FileUtil.judeDirExists(new File(path));
        //设置当前时间的格式，为年-月-日
        String cfgData =
                "AppName=" + responseEntity.getAppName() + "\r\n" +
                        "AppVersion=" + responseEntity.getAppVersion() + "\r\n" +
                        "Vendor=" + vendor + "\r\n" +
                        "LicenseType=" + responseEntity.getLicenseType() + "\r\n" +
                        "ExpiredDays=" + responseEntity.getExpiredDays() + "\r\n" +
                        "AuthorizationDate=" + responseEntity.getAuthorizationDate() + "\r\n" +
                        "HardwareID=" + responseEntity.getAllocalTable() + "\r\n" +
                        "SerialNumber=" + responseEntity.getSerialNumber();
        String name = "license.lic";
        FileUtil.clearFile(path + "\\" + name);
        FileUtil.appendStringToFile(path + "\\" + name, cfgData);
        System.out.println("SUCCESS");
    }

    /**
     * 8      * 读取txt文件的内容
     * 9      * @param file 想要读取的文件对象
     * 10      * @return 返回文件内容
     * 11
     */
    public static String txt2String(File file) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            Integer size = 0;
            Integer temp = 7;
            String allocalTable = "";
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + "\n" + s;
                allocalTable = allocalTable + s + "=1;";
                //读取到了s 的内容
               /* if (size < 22) {
                    //试用
                    String path = "E:\\license\\trial\\" + s;
                    creatLicense("trial", temp.toString(), allocalTable, path);
                    temp++;
                    if (temp == 21) {
                        temp = 7;
                    }
                } else {
                    //商用
                    String path = "E:\\license\\commercial\\" + s;
                    creatLicense("commercial", "unlimited", allocalTable, path);
                }*/
                size++;
            }
            System.out.println(allocalTable);
            String tempStr = null;
           /* String path = "E:\\license\\trial";
            creatLicense("trial", "1", tempStr, path);*/
            String path = "E:\\license";
            creatLicense("commercial", "unlimited", tempStr, path);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成需要加密的数据
     *
     * @param map
     * @return
     */
    public static String getOpen(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + "p2p@58066815";
        logger.info("Open Before HMACSHA256:" + result);
        return result;
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
        isLicenseRun = false;
        isCameraRun = false;
    }
}
