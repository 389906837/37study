package com.cloud.cang.mgr.ws.service;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.common.aiFace.Result;
import com.cloud.cang.core.common.aiFace.UserDomain;
import com.cloud.cang.core.common.utils.AmFaceUtils;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.mgr.common.BDErrorCodeEnum;
import com.cloud.cang.mgr.common.utils.FileUtils;
import com.cloud.cang.mgr.common.utils.ZipUtils;
import com.cloud.cang.mgr.fr.service.FaceRegisterInfoService;
import com.cloud.cang.mgr.ws.vo.FaceListResp;
import com.cloud.cang.mgr.ws.vo.FaceRegisterVo;
import com.cloud.cang.mgr.ws.vo.RegisterResponse;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 雨森人脸注册
 * Created by YLF
 * on 2020/6/4.
 */
@Component
public class FaceRegisterService {
    private static final Logger logger = LoggerFactory.getLogger(FaceRegisterService.class);

    @Autowired
    private FaceRegisterInfoService faceRegisterInfoService;
    @Autowired
    private ICached iCached;

    /**
     * 注册人脸信息
     *
     * @param faceRegisterVo
     * @return
     */
    public RegisterResponse registerFace(FaceRegisterVo faceRegisterVo) {
        List<FaceRegisterVo.FaceVo> faceVos = faceRegisterVo.getFaceList();
        RegisterResponse registerResponse = new RegisterResponse();
        FaceRegisterInfo faceRegisterInfo = null;
        String GROUP_ID = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "YuSenFaceGroup");    //数据字典配置
        String YSVersion = "";
        //上传用户图片到本地服务器路径
        String uploadFilePath = "";
        FaceListResp faceListResp = null;
        List<FaceListResp> faceListResps = new ArrayList<>();
        boolean fileDirFlag = true;
        String dataStr = DateUtils.dateParseShortString(new Date());
        String prefix = "";
        for (int x = 0; x < faceVos.size(); x++) {

            faceRegisterInfo = new FaceRegisterInfo();
            FaceRegisterVo.FaceVo faceVo = faceVos.get(x);
            String imgUrl = faceVo.getImgUrl();
            if (StringUtil.isBlank(imgUrl)) {
                logger.info("#######人脸注册图片信息为空########");
                registerResponse.setCode("-105");
                registerResponse.setMsg("Picture information is empty");
                return registerResponse;
            }
            if (StringUtil.isBlank(faceVo.getUsername())) {
                logger.info("#######人脸注册会员名称为空########");
                registerResponse.setCode("-106");
                registerResponse.setMsg("username is empty");
                return registerResponse;
            }
            //jpg png  jpeg  bmp
            String formatName = imgUrl.substring(imgUrl.lastIndexOf(".") + 1).trim().toLowerCase();
            if (!formatName.toLowerCase().equals("jpg") && !formatName.toLowerCase().equals("png")
                    && !formatName.toLowerCase().equals("bmp") && !formatName.toLowerCase().equals("jpeg")) {
                //注册失败返回
                logger.info("百度人脸注册失败：{}", faceVo.getUsername());
                registerResponse.setCode("-104");
                registerResponse.setMsg("image format error");
                return registerResponse;
            }
            String memberId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
            //1,调用百度人脸识别接口 返回facetoken是否在本地数据库有 有的话直接返回facetoken
            //2,百度返回的facetoken数据库里面没有,则调用百度的人脸注册接口返回facetoken
            //调用百度人脸查询接口
            Map<String, String> searchMap = baiduFaceSearch(imgUrl);
            if (null == searchMap || StringUtils.isBlank(searchMap.get("faceToken"))) {
                ResponseVo<FaceRegisterInfo> registerInfoResponseVo = baiduFaceRegister(faceRegisterInfo, memberId, imgUrl, faceVo.getUsername());
                if (null == registerInfoResponseVo || !registerInfoResponseVo.isSuccess() || registerInfoResponseVo.getData() == null) {
                   /* if (null == faceRegisterInfo) {*/
                    //注册失败返回
                    logger.info("百度人脸注册失败：{}", faceVo.getUsername());
                    registerResponse.setCode("-102");
                    registerResponse.setMsg(registerInfoResponseVo.getMsg());
                    return registerResponse;
                }
            } else {
                String userId = searchMap.get("userId");
                String faceToken = searchMap.get("faceToken");
                //根据faceToken userId  groupId 查询数据库人脸注册信息
                FaceRegisterInfo selectTemp = new FaceRegisterInfo();
                selectTemp.setSgroupId(GROUP_ID);
                selectTemp.setSmemberId(userId);
                selectTemp.setSfaceToken(faceToken);
                selectTemp.setIdelFlag(0);
                FaceRegisterInfo temp = faceRegisterInfoService.selectByYSSelectTive(selectTemp);
                if (null != temp) {
                    //人脸已经注册过直接返回数据
                    faceListResp = new FaceListResp();
                    faceListResp.setUserCode(faceVo.getUserCode());
                    faceListResp.setFirstRegister("0");
                    faceListResp.setFaceId(faceToken);
                    faceListResps.add(faceListResp);
                    registerResponse.setCode("200");
                    registerResponse.setMsg("Success");
                   /* String updateUrl = EvnUtils.getValue("dynamic.http.domain") + "/YuSenFaceZip";
                    registerResponse.setUpdateUrl(updateUrl);*/
                    registerResponse.setFaceListResp(faceListResps);
                    registerResponse.setVersion(temp.getSversion());
                    return registerResponse;
                } else {
                    //数据库查询不到则删除百度人脸图片再注册 也可以不删除百度图片直接在数据库插入数据
                    logger.info("数据库该人脸注册信息为空：{}", faceToken);
                    AmFaceUtils.delete(userId, faceToken, AmFaceUtils.YS_GROUP_ID);
                    //再次注册 调用注册接口
                  /*  faceRegisterInfo = baiduFaceRegister(faceRegisterInfo, memberId, imgUrl, faceVo.getUsername());
                    if (null == faceRegisterInfo) {*/
                    ResponseVo<FaceRegisterInfo> registerInfoResponseVo = baiduFaceRegister(faceRegisterInfo, memberId, imgUrl, faceVo.getUsername());
                    if (null == registerInfoResponseVo || !registerInfoResponseVo.isSuccess() || registerInfoResponseVo.getData() == null) {
                        //注册失败返回
                        logger.info("百度人脸注册失败：{}", faceVo.getUsername());
                        registerResponse.setCode("-102");
                        registerResponse.setMsg(registerInfoResponseVo.getMsg());
                        return registerResponse;
                    }
                }
            }
            //新增
            String faceCode = CoreUtils.newCode(EntityTables.FR_FACE_REG_INFO);        // 人脸注册编号
            if (StringUtils.isBlank(faceCode)) {
                logger.error("生成人脸注册信息表编号出错");
            }
            faceRegisterInfo.setSfaceCode(faceCode);                        //人脸编号
            faceRegisterInfo.setSmerchantId("f6befd4fff6e11e7be44000c2937a246");        //商户ID
            faceRegisterInfo.setSmerchantCode("MI000001");    //商户编号
            faceRegisterInfo.setSmemberId(memberId);                //会员ID
            String userCode = faceVo.getUserCode();
            if (StringUtils.isBlank(userCode)) {
                userCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
            }
            faceRegisterInfo.setSmemberCode(userCode);            //会员编号
            faceRegisterInfo.setSmemberName(faceVo.getUsername());            //会员名称
            faceRegisterInfo.setItype(10);                                    //状态 10：注册
            faceRegisterInfo.setTregisterTime(DateUtils.getCurrentDateTime());    // 注册时间
            faceRegisterInfo.setTbindTime(DateUtils.getCurrentDateTime());            // 绑定时间

            faceRegisterInfo.setSgroupId(GROUP_ID);               // 人脸识别库用户组
            // 获取网络图片,存储到本地服务器
            boolean iisTodayFirstRegister = false;
            String serverPath = "";
            if (fileDirFlag) {
            /*
                path = System.getProperty("catalina.home") + File.separator + "YuSenFacePic" + File.separator + dataStr;
                File pathFile = new File(path);
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                    iisTodayFirstRegister = true;
                }
                fileDirFlag = false;*/
                String fileName = faceRegisterInfo.getSfaceToken() + "_" + faceVo.getUsername() + "_" + GROUP_ID + "." + formatName;//保存图片文件名
                // base64转图片,存储到本地服务器
                String filePathType = "linux";
                // String prefix = SysParaUtil.getValue("upload_image_path_prefix");//服务器路径前缀 redis配置 系统参数
                // String prefix = BizParaUtil.get("upload_image_path_prefix");//运营参数
                prefix = GrpParaUtil.getValue(GrpParaUtil.AI_FACE_CONFIG, "uploadImagePathPrefix");    //数据字典配置
                String folder = "YuSenFacePic";
                if (filePathType.equals("windows")) {
                    uploadFilePath = prefix + "\\" + folder + "\\" + dataStr;
                    File tempfile = new File(uploadFilePath);
                    if (null == tempfile || !tempfile.exists()) {
                        tempfile.mkdirs();
                        iisTodayFirstRegister = true;
                    }
                    String uploadPath = uploadFilePath + "\\" + fileName;//上传文件路径
                } else {//linux 直接保存到nfs文件共享目录
                    uploadFilePath = prefix + "/" + folder + "/" + dataStr;
                    File tempfile = new File(uploadFilePath);
                    if (null == tempfile || !tempfile.exists()) {
                        tempfile.mkdirs();
                        iisTodayFirstRegister = true;
                    }
                    serverPath = uploadFilePath + "/" + fileName;//上传文件路径
                }
                fileDirFlag = false;
            }
            if (iisTodayFirstRegister) {
                //如果是今天首次注册,则创建版本号
                YSVersion = CoreUtils.newCode(EntityTables.YS_VERSION_INFO);        // 版本号
                try {
                    iCached.put("ys_register_version", YSVersion);
                } catch (Exception e) {
                    logger.error("雨森版本号放入缓存异常：{}", e);
                }
            } else {
                //不是今天首次注册的人脸,查询版本号
                try {
                    YSVersion = (String) iCached.get("ys_register_version");
                } catch (Exception e) {
                    logger.error("获取雨森版本号异常：{}", e);
                }
            }
            faceRegisterInfo.setSversion(YSVersion);
            // base64转图片,存储到本地服务器
          /*  boolean flag = FileUtils.copyImg(imgUrl, serverPath);
            File file = new File(serverPath);
            if (flag && new File(serverPath).exists()) {
                //上传图片到FTP服务器
                if (null != file) {
                    //图片上传
                    //    String dataStr = DateUtils.dateParseShortString(new Date());
                    url = FileUtils.uploadHome(file, "YuSenFacePic", dataStr);
                }
            } else {
                logger.error("网络图片,存储到本地服务器失败");
            }*/
            // 图片存储到共享服务器
            boolean flag = FileUtils.copyImg(imgUrl, serverPath);
            if (flag && new File(serverPath).exists()) {
                //图片
                logger.info("网络图片,图片存储到共享服务器成功");
            } else {
                logger.error("网络图片,图片存储到共享服务器失败");
                registerResponse.setCode("-103");
                registerResponse.setMsg("img to sharedServer error");
                return registerResponse;
            }
            faceRegisterInfo.setSregisterFaceAddress(serverPath);                    //人脸图片地址
            faceRegisterInfo.setSuserInfo("");                                // 人脸库会员信息
            faceRegisterInfo.setIdelFlag(0);
            faceRegisterInfo.setTaddTime(DateUtils.getCurrentDateTime());
            faceRegisterInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            faceRegisterInfoService.insert(faceRegisterInfo);
            faceListResp = new FaceListResp();
            faceListResp.setUserCode(faceVo.getUserCode());
            faceListResp.setFirstRegister("1");
            faceListResp.setFaceId(faceRegisterInfo.getSfaceToken());
            faceListResps.add(faceListResp);
        }
        //打包图片上传共享服务器
        //   String zipPath = System.getProperty("catalina.home") + File.separator + "YuSenFaceZip";
        String zipPath = prefix + File.separator + "YuSenFaceZip";
        File pathFile = new File(zipPath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        String zipName = zipPath + File.separator + "YusenFace_" + YSVersion;
        ZipUtils.compress(uploadFilePath, zipName);
        // String dataStr = DateUtils.dateParseShortString(new Date());
        // this.asyncUptoFtp(zipName, new AtomicInteger(2));
       /* String filePath = FileUtils.uploadHome(new File(zipName + ".zip"), "YuSenFaceZip", "");
        logger.info("雨森上传到FTP服务器ZIP目录：{}", filePath);*/
        //删除本地服务器临时文件 ZIP
        //ReadFileUtil.deleteDirectory(zipPath);
        //  String updateUrl = EvnUtils.getValue("dynamic.http.domain") + "/YuSenFaceZip";
        String updateUrl = EvnUtils.getValue("dynamic.http.domain") + prefix + File.separator + "YuSenFaceZip";
        registerResponse.setCode("200");
        registerResponse.setMsg("Success");
        registerResponse.setUpdateUrl(updateUrl);
        registerResponse.setFaceListResp(faceListResps);
        registerResponse.setVersion(YSVersion);
        return registerResponse;
    }


    /**
     * 人脸查询接口
     *
     * @param imgUrl 图片地址
     * @return
     */
    public Map<String, String> baiduFaceSearch(String imgUrl) {
        // 调用百度人脸识别API查询该人脸是否已经注册过，人脸只能绑定一个账号
        ResponseAip responseAipDomain = AmFaceUtils.findUserByUrl(imgUrl, null, AmFaceUtils.YS_GROUP_ID);
        Map<String, String> map = null;
        logger.info("百度人脸查询接口返回：{}", responseAipDomain);
        if ("SUCCESS".equals(responseAipDomain.getError_msg())) {
            List<UserDomain> userDomainList = responseAipDomain.getResult().getUser_list();
            if (CollectionUtils.isNotEmpty(userDomainList)) {
                map = new HashMap<>();
                Float tempScore = new Float(0);
                String userIdTemp = "";
                UserDomain u = userDomainList.get(0);
                // 循环比较取最高分
                if (tempScore.compareTo(u.getScore()) == -1) {
                    tempScore = u.getScore();
                    userIdTemp = u.getUser_id();
                }
                if (tempScore.compareTo(new Float(80)) == 1 && StringUtils.isNotBlank(userIdTemp)) {    // 阈值设置为80分
                    logger.info("用户已经注册过人脸信息：{}", userIdTemp);
                    //返回faceToken
                    map.put("faceToken", responseAipDomain.getResult().getFace_token());
                    map.put("userId", userIdTemp);
                    return map;
                }
            }
        }
        return null;
    }


    /**
     * 人脸注册接口
     *
     * @param
     * @return
     */
    public ResponseVo<FaceRegisterInfo> baiduFaceRegister(FaceRegisterInfo faceRegisterInfo, String userId, String imgUrl, String userName) {
        // 调用百度人脸识别API注册人脸信息
        ResponseAip responseAip = AmFaceUtils.registerByUrl(userId, imgUrl, AmFaceUtils.YS_GROUP_ID, userName);
        logger.info("百度人脸注册接口返回：{}", responseAip);
        if ("SUCCESS".equals(responseAip.getError_msg()) && 0 == responseAip.getError_code()) {    // 注册成功
            Result result = responseAip.getResult();
           /* FaceRegInfo faceRegInfoVo = new FaceRegInfo();
            faceRegInfoVo.setSfaceToken(result.getFace_token());
            faceRegInfoVo.setSleft(String.valueOf(result.getLocation().getLeft().doubleValue()));
            faceRegInfoVo.setStop(String.valueOf(result.getLocation().getTop().doubleValue()));
            faceRegInfoVo.setSwidth(String.valueOf(result.getLocation().getWidth().doubleValue()));
            faceRegInfoVo.setSheight(String.valueOf(result.getLocation().getHeight().doubleValue()));
            faceRegInfoVo.setSratation(String.valueOf(result.getLocation().getRotation().doubleValue()));
            return faceRegInfoVo;*/
            faceRegisterInfo.setSfaceToken(result.getFace_token());
            faceRegisterInfo.setSleft(String.valueOf(result.getLocation().getLeft().doubleValue()));
            faceRegisterInfo.setStop(String.valueOf(result.getLocation().getTop().doubleValue()));
            faceRegisterInfo.setSwidth(String.valueOf(result.getLocation().getWidth().doubleValue()));
            faceRegisterInfo.setSheight(String.valueOf(result.getLocation().getHeight().doubleValue()));
            faceRegisterInfo.setSratation(String.valueOf(result.getLocation().getRotation().doubleValue()));
            return ResponseVo.getSuccessResponse(faceRegisterInfo);
        } else {
            String msg = BDErrorCodeEnum.getNameByCode(responseAip.getError_code());
            if(StringUtils.isBlank(msg)){
               return BDErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
            }
            return ResponseVo.getErrorResponse(msg);
        }
    }


    /**
     * 异步上传本地服务器zip包到ftp服务器
     *
     * @param localFilePath
     * @param send          失败后可重试的次数
     */
    private void asyncUptoFtp(String localFilePath, AtomicInteger send) {
        logger.info("异步操作把本地zip文件上传到FTP服务器:{}", localFilePath);
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String filePath = FileUtils.uploadHome(new File(localFilePath + ".zip"), "YuSenFaceZip", "");
                    logger.info("雨森上传到FTP服务器ZIP目录：{}", filePath);
                    if (StringUtil.isBlank(filePath)) {
                        logger.info("上传zip包到FTP服务器失败");
                        if (send.get() > 0) {
                            send.lazySet(send.decrementAndGet());
                            asyncUptoFtp(localFilePath, send);
                        }
                    }

                } catch (Exception e) {
                    logger.error("异步上传本地服务器zip包到ftp服务器异常：{}", e);
                    if (send.get() > 0) {
                        send.lazySet(send.decrementAndGet());
                        asyncUptoFtp(localFilePath, send);
                    }
                }
            }
        });
    }

}
