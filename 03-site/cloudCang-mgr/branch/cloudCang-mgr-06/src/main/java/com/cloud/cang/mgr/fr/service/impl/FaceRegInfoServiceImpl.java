package com.cloud.cang.mgr.fr.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.common.aiFace.Result;
import com.cloud.cang.core.common.aiFace.UserDomain;
import com.cloud.cang.core.common.utils.AmFaceUtils;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.ExcelCommUtil;
import com.cloud.cang.mgr.fr.dao.FaceRegInfoDao;
import com.cloud.cang.mgr.fr.domain.FaceRegInfoDomain;
import com.cloud.cang.mgr.fr.service.FaceRegInfoService;
import com.cloud.cang.mgr.fr.vo.FaceRegInfoVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.fr.FaceRegInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUser;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.util.*;

@Service
public class FaceRegInfoServiceImpl extends GenericServiceImpl<FaceRegInfo, String> implements
        FaceRegInfoService {

    private static final Logger logger = LoggerFactory.getLogger(FaceRegInfoServiceImpl.class);

    private static final String ANNUAL_MEETING_FACE = "annualMeetingFace";

    @Autowired
    FaceRegInfoDao faceRegInfoDao;


    @Override
    public GenericDao<FaceRegInfo, String> getDao() {
        return faceRegInfoDao;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param faceRegInfoVo
     * @return
     */
    @Override
    public Page<FaceRegInfoDomain> selectPageByDomainWhere(Page<FaceRegInfoDomain> page, FaceRegInfoVo faceRegInfoVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<FaceRegInfoDomain>) faceRegInfoDao.selectByDomainWhere(faceRegInfoVo);
    }

    /**
     * 逻辑删除
     *
     * @param checkboxId 人脸信息IDs
     * @param operater   操作员
     * @return
     */
    @Override
    public ResponseVo<String> deleteFaceInfo(String[] checkboxId, String operater) {
        StringBuffer sb = new StringBuffer();
        for (String id : checkboxId) {
            FaceRegInfo faceRegInfoDomain = faceRegInfoDao.selectByPrimaryKey(id);
            if (null != faceRegInfoDomain) {
                if (faceRegInfoDomain.getIauditStatus() == 10 || faceRegInfoDomain.getIauditStatus() == 30) {
                    FaceRegInfo faceRegInfoVo = new FaceRegInfo();
                    faceRegInfoVo.setId(id);
                    faceRegInfoVo.setIdelFlag(1);
//                    faceRegInfoVo.setItype(20);
                    faceRegInfoVo.setTdeleteTime(DateUtils.getCurrentDateTime());
                    faceRegInfoVo.setSoperMan(operater);
                    faceRegInfoDao.updateByIdSelective(faceRegInfoVo);
                    sb.append(faceRegInfoDomain.getSfaceCode()).append(",");
                }
            }
        }
        String str = "";
        if (StringUtils.isNotEmpty(sb.toString().substring(0, sb.length() - 1))) {
            str = sb.toString().substring(0, sb.length() - 1);
        }
        return ResponseVo.getSuccessResponse(str);
    }

    /**
     * 修改人脸信息
     *
     * @param faceRegInfo 人脸信息
     * @param faceImg     图片
     * @return
     */
    @Override
    public ResponseVo<FaceRegInfo> updateFaceInfo(FaceRegInfo faceRegInfo, MultipartFile faceImg) {
        String imgUrl = "";
        if (null != faceImg && faceImg.getSize() > 0) {
            imgUrl = uploadHome(faceImg, ANNUAL_MEETING_FACE);
        }
        faceRegInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        if (StringUtils.isNotEmpty(imgUrl)) {
            faceRegInfo.setSregisterFaceAddress(imgUrl);
        }
        if (StringUtils.isNotEmpty(faceRegInfo.getSrealName())) {
            faceRegInfo.setSuserInfo(faceRegInfo.getSrealName());
        }
        faceRegInfoDao.updateByIdSelective(faceRegInfo);
        return ResponseVo.getSuccessResponse(faceRegInfo);
    }

    /**
     * 审核人脸信息
     *
     * @param faceRegInfo 人脸信息
     * @return
     */
    @Override
    public ResponseVo<FaceRegInfo> updateFaceInfoByAudit(FaceRegInfo faceRegInfo) {
        Integer iauditStatus = faceRegInfo.getIauditStatus();    // 审核状态	10=待审核 20=审核通过 30=审核拒绝
        String id = faceRegInfo.getId();
        FaceRegInfo faceRegInfoDomain = faceRegInfoDao.selectByPrimaryKey(id);
        ResponseVo errorResponseVo = new ResponseVo(false, 10086, "");
        errorResponseVo.setData(faceRegInfoDomain);
        if (null == faceRegInfoDomain) {
            errorResponseVo.setMsg("用户信息不存在");
            return errorResponseVo;
        }
        if (iauditStatus == 20) {
            String faceImgUrl = faceRegInfoDomain.getSregisterFaceAddress();
            InputStream in = null;
            byte[] data = null;
            try {
                in = downloadFormFtp(in, faceImgUrl, FtpParamUtil.getFtpUser());
                data = new byte[in.available()];
                in.read(data);
                in.close();
                BASE64Encoder encoder = new BASE64Encoder();
                String base64String = encoder.encode(data);

                // 调用百度人脸识别API查询该人脸是否已经注册过，人脸只能绑定一个账号
                ResponseAip responseAipDomain = AmFaceUtils.findUser(base64String, null, AmFaceUtils.AM_GROUP_ID);
                if ("SUCCESS".equals(responseAipDomain.getError_msg())) {
                    List<UserDomain> userDomainList = responseAipDomain.getResult().getUser_list();
                    if (CollectionUtils.isNotEmpty(userDomainList)) {
                        Float tempScore = new Float(0);
                        String userIdTemp = "";
                        for (UserDomain u : userDomainList) {
                            // 循环比较取最高分
                            if (tempScore.compareTo(u.getScore()) == -1) {
                                tempScore = u.getScore();
                                userIdTemp = u.getUser_id();
                            }
                            if (tempScore.compareTo(new Float(80)) == 1 && StringUtils.isNotBlank(userIdTemp)) {    // 阈值设置为80分
                                logger.error("用户已经注册过人脸信息");
                                errorResponseVo.setMsg("用户已经注册过人脸信息");
                                return errorResponseVo;
                            }
                        }
                    }
                }

                // 调用百度人脸识别API注册人脸信息
                ResponseAip responseAip = AmFaceUtils.register(faceRegInfoDomain.getId(), base64String, AmFaceUtils.AM_GROUP_ID, faceRegInfoDomain.getSrealName());
                if ("SUCCESS".equals(responseAip.getError_msg()) && 0 == responseAip.getError_code()) {    // 注册成功
                    Result result = responseAip.getResult();
                    FaceRegInfo faceRegInfoVo = new FaceRegInfo();
                    faceRegInfoVo.setTupdateTime(DateUtils.getCurrentDateTime());
                    faceRegInfoVo.setSoperMan(faceRegInfo.getSoperMan());
                    faceRegInfoVo.setIauditStatus(20);    // 审核状态 10=待审核 20=审核通过 30=审核拒绝
                    faceRegInfoVo.setItype(10);            //30=已申请	10=已注册 20=已删除
                    faceRegInfoVo.setSfaceToken(result.getFace_token());
                    faceRegInfoVo.setSleft(String.valueOf(result.getLocation().getLeft().doubleValue()));
                    faceRegInfoVo.setStop(String.valueOf(result.getLocation().getTop().doubleValue()));
                    faceRegInfoVo.setSwidth(String.valueOf(result.getLocation().getWidth().doubleValue()));
                    faceRegInfoVo.setSheight(String.valueOf(result.getLocation().getHeight().doubleValue()));
                    faceRegInfoVo.setSratation(String.valueOf(result.getLocation().getRotation().doubleValue()));
                    faceRegInfoVo.setSgroupId(AmFaceUtils.AM_GROUP_ID);
                    faceRegInfoVo.setId(faceRegInfoDomain.getId());
                    faceRegInfoVo.setTregisterTime(DateUtils.getCurrentDateTime());
                    faceRegInfoVo.setTauditTime(DateUtils.getCurrentDateTime());
                    faceRegInfoDao.updateByIdSelective(faceRegInfoVo);
                    return ResponseVo.getSuccessResponse(faceRegInfoDomain);
                }
            } catch (Exception e) {
                logger.error("读取图片出现异常：{}", e);
                throw new ServiceException("审核出现异常");
            }
        } else {
            String sauditRemark = faceRegInfo.getSauditRemark();
            if (StringUtils.isBlank(sauditRemark)) {
                errorResponseVo.setMsg("审核拒绝需填写审核意见");
                return errorResponseVo;
            }
            FaceRegInfo faceRegInfoVo = new FaceRegInfo();
            faceRegInfoVo.setTupdateTime(DateUtils.getCurrentDateTime());
            faceRegInfoVo.setSoperMan(faceRegInfo.getSoperMan());
            faceRegInfoVo.setIauditStatus(30);    // 审核状态 10=待审核 20=审核通过 30=审核拒绝
            faceRegInfoVo.setTauditTime(DateUtils.getCurrentDateTime());
            faceRegInfoVo.setId(faceRegInfoDomain.getId());
            faceRegInfoVo.setSauditRemark(sauditRemark);
            faceRegInfoDao.updateByIdSelective(faceRegInfoVo);
            return ResponseVo.getSuccessResponse(faceRegInfoDomain);
        }
        errorResponseVo.setMsg("未能识别图片中人脸信息，请确认图片的真实性");
        return errorResponseVo;
    }

    /**
     * 添加用户，
     * 上传用户照片到后台图片服务器
     *
     * @param faceImg     用户照片
     * @param faceRegInfo 用户信息
     * @return
     */
    @Override
    public ResponseVo<FaceRegInfo> insertFaceInfo(MultipartFile faceImg, FaceRegInfo faceRegInfo) {
        String picUrl = uploadHome(faceImg, ANNUAL_MEETING_FACE);    // 年会文件夹
        String faceCode = CoreUtils.newCode(EntityTables.FR_FACE_REG_INFO);        // 人脸注册编号
        if (StringUtils.isBlank(faceCode)) {
            logger.error("生成人脸注册信息表编号出错");
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("添加用户出错");
        }
        faceRegInfo.setSregisterFaceAddress(picUrl);
        faceRegInfo.setSfaceCode(faceCode);
        faceRegInfo.setIregSource(10);    // 10=后台注册 20=设备注册 30=H5页面注册
        faceRegInfo.setSuserInfo(faceRegInfo.getSrealName());
        faceRegInfo.setItype(30);// 30=已申请 10=已注册 20=已删除
        faceRegInfo.setIauditStatus(10); // 10=待审核 20=审核通过 30=审核拒绝
        faceRegInfo.setIdelFlag(0);
        faceRegInfo.setTaddTime(DateUtils.getCurrentDateTime());
        faceRegInfo.setTupdateTime(DateUtils.getCurrentDateTime());
        faceRegInfoDao.insert(faceRegInfo);
        return ResponseVo.getSuccessResponse(faceRegInfo);
    }

    /**
     * 批量导入年会人脸Excel识别信息
     *
     * @param file 照片+姓名文件
     * @return
     */
    @Override
    public ResponseVo<String> batchImportFaceInfo(MultipartFile file) {
        Workbook workbook = ExcelCommUtil.getWorkbook(file);
        StringBuffer errorStrBuf = new StringBuffer();        // 记录错误表格信息
        StringBuffer correctStrBuf = new StringBuffer();        // 记录正确表格信息
        List<String[]> vrCommodityList = new ArrayList<>();    // Excel表格信息
        List<HSSFPictureData> pictureDataList03 = new ArrayList<>();    // 03表格图片
        List<XSSFPictureData> pictureDataList07 = new ArrayList<>();    // 07表格图片
        HSSFPictureData hssfPictureData = null;    // 03图片data流
        XSSFPictureData xssfPictureData = null;    // 07图片data流

        if (null == workbook) {
            logger.error("工作薄不存在");
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("工作薄不存在");
        }

        /* 读取Excel文件中的内容 */
        if (workbook instanceof HSSFWorkbook) {
            int allSheetNum = workbook.getNumberOfSheets();
            if (allSheetNum > 0) {
                pictureDataList03 = ExcelCommUtil.getsheetPictures03(0, (HSSFWorkbook) workbook);
                vrCommodityList = ExcelCommUtil.readExcel(0, workbook, 1);
                logger.debug("批量导入年会人脸Excel识别信息，读取03版本.xls完成");
            }
        } else if (workbook instanceof XSSFWorkbook) {
            int allSheetNum = workbook.getNumberOfSheets();
            if (allSheetNum > 0) {
                pictureDataList07 = ExcelCommUtil.getsheetPictures07(0, (XSSFWorkbook) workbook);
                vrCommodityList = ExcelCommUtil.readExcel(0, workbook, 1);
                logger.debug("批量导入年会人脸Excel识别信息，读取07版本.xlsx完成");
            }
        }
        if (CollectionUtils.isEmpty(vrCommodityList)) {
            logger.error("批量导入年会人脸Excel识别信息，当前：{}sheet文件没有内容", workbook.getSheetName(0));
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量导入年会人脸Excel识别信息，上传文件中数据为空，请确认是否有数据");
        }


        for (int i = 0; i < vrCommodityList.size(); i++) {
            int j = i + 1; // 第j行数据
            int serialNum = i + 2;
            String srealName = "";                        // 姓名

            // 从Excel中获取第i+1行的数据
            String[] row = vrCommodityList.get(i);
            String[] strings = new String[8];
            if (ArrayUtils.isNotEmpty(row)) {
                for (int k = 0; k < row.length; k++) {
                    strings[k] = row[k];
                }
            }
            String picUrl = strings[0];                         // 图片（空字符串）
            srealName = strings[1];                             // 姓名


            // 1.1 必填项非空校验
            if (StringUtils.isBlank(srealName)) {
                logger.error("批量导入年会人脸Excel识别信息，姓名为空,第：{}行 出错", serialNum);
                errorStrBuf.append("第" + serialNum + "行,名称为空;");
                continue;
            }

            // 1.2 如果有图片上传图片，返回图片地址
            if (CollectionUtils.isNotEmpty(pictureDataList03)) {
                try {
                    hssfPictureData = pictureDataList03.get(i);
                } catch (Exception e) {
                    logger.error("从03格式的Excel中获取图片出现异常，异常原因：{}", e);
                    throw new ServiceException("图片需要全上传或全不上传");
                }
                if (null != hssfPictureData) {
                    String tempUrl = uploadExcelPic03(hssfPictureData, "annualMeetingFace");
                    if (StringUtils.isNotBlank(tempUrl)) {
                        picUrl = tempUrl;
                    }
                }
            } else if (CollectionUtils.isNotEmpty(pictureDataList07)) {
                try {
                    xssfPictureData = pictureDataList07.get(i);
                } catch (Exception e) {
                    logger.error("从07格式的Excel中获取图片出现异常，异常原因：{}", e);
                    throw new ServiceException("图片需要全上传或全不上传");
                }
                if (null != xssfPictureData) {
                    String tempUrl = uploadExcelPic07(xssfPictureData, "annualMeetingFace");
                    if (StringUtils.isNotBlank(tempUrl)) {
                        picUrl = tempUrl;
                    }
                }
            }

            // 1.3 开始插入数据
            String faceCode = CoreUtils.newCode(EntityTables.FR_FACE_REG_INFO); // 人脸信息编号
            if (StringUtils.isBlank(faceCode)) {
                logger.error("批量上传视觉商品没有获取到商品编号,第：{}行", serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",系统生成商品编号错误;");
                continue;
            }
            FaceRegInfo faceRegInfo = new FaceRegInfo();
            faceRegInfo.setSrealName(srealName);
            faceRegInfo.setSregisterFaceAddress(picUrl);
            faceRegInfo.setSfaceCode(faceCode);
            faceRegInfo.setIregSource(10);    // 10=后台注册 20=设备注册 30=H5页面注册
            faceRegInfo.setSuserInfo(faceRegInfo.getSrealName());
            faceRegInfo.setItype(30);// 30=已申请 10=已注册 20=已删除
            faceRegInfo.setIauditStatus(10); // 10=待审核 20=审核通过 30=审核拒绝
            faceRegInfo.setIdelFlag(0);
            faceRegInfo.setTaddTime(DateUtils.getCurrentDateTime());
            faceRegInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            faceRegInfoDao.insert(faceRegInfo);
            correctStrBuf.append(faceCode + ",");
        }
        String correctStr = correctStrBuf.toString();
        String errorStr = errorStrBuf.toString();
        if (StringUtils.isNotBlank(errorStr)) {
            if (StringUtils.isNotBlank(correctStr)) {
                return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "部分数据导入成功，下列数据出现错误：" + errorStr);
            }
            return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "全部导入失败，下列数据出现错误：" + errorStr);
        }
        return ResponseVo.getSuccessResponse(correctStr.substring(0, correctStr.length() - 1));
    }

    /**
     * 处理上传图片
     *
     * @param file     图片文件
     * @param pathName 路径
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        if (file == null) {
            throw new ServiceException("没有找到上传文件");
        }
        String filName = file.getOriginalFilename();//文件原名
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//获取后缀名====>jpg|png|bmp
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/

            String tempName = DateUtils.getCurrentSTimeNumber() + "." + exp;//文件名=="时间"+"."+"原图片名后缀"
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 从FTP服务器下载图片
     *
     * @param pathName FTP服务器上的图片路径
     * @return 图片流
     */
    private ByteArrayOutputStream getFileFromFtp(String pathName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FtpUtils.downloadFormFtp(byteArrayOutputStream, pathName, FtpParamUtil.getFtpUser());
        return byteArrayOutputStream;
    }

    /**
     * 从Ftp服务器上下载文件
     *
     * @param in
     * @param serverPath
     * @param config
     * @return
     */
    public static InputStream downloadFormFtp(InputStream in, String serverPath, FtpUser config) {
        String serverUrl = config.getIp();
        String userName = config.getUserName();
        String passWord = config.getPassword();
        String filepath = serverPath.substring(0, serverPath.lastIndexOf("/"));
        String filename = serverPath.substring(serverPath.lastIndexOf("/") + 1, serverPath.length());
        FtpClient ftpClient = null;
        try {
            ftpClient = FtpClient.create(serverUrl);//此为jdk1.7创建ftp连接
            loginToFtpServer(ftpClient, serverUrl,
                    userName, passWord);
            filepath = getFullPath(ftpClient, filepath);
            if (isDirExist(ftpClient, filepath)) {
                ftpClient.changeDirectory(filepath);
                ftpClient.setBinaryType();
                File file = new File(filename);
                in = ftpClient.getFileStream(file.getName());
                return in;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient != null)
                try {
                    ftpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    private static boolean isDirExist(FtpClient ftpClient, String dir) {
        String pwd = "";
        try {
            pwd = ftpClient.getWorkingDirectory();
            ftpClient.changeDirectory(dir);
            ftpClient.changeDirectory(pwd);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static String getFullPath(FtpClient ftpClient, String serverPath) throws FtpProtocolException, IOException {
        String workingdir = ftpClient.getWorkingDirectory();
        if (!serverPath.startsWith("/"))
            serverPath = "/" + serverPath;
        if (!workingdir.equals("/"))
            serverPath = workingdir + serverPath;
        return serverPath;
    }

    /**
     * 登录到Ftp服务器
     */
    private static boolean loginToFtpServer(FtpClient ftpClient, String serverUrl,
                                            String userName, String passWord) {
        try {
            ftpClient.login(userName, passWord.toCharArray());
            return true;
        } catch (IOException e) {
            System.out.println("Ftp 客户端打开失败");
            return false;
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * excel03图片上传
     *
     * @param hssfPictureData 03图片data流
     * @param pathName        文件路径名
     * @return
     */
    private String uploadExcelPic03(HSSFPictureData hssfPictureData, String pathName) {
        if (null == hssfPictureData || ArrayUtils.isEmpty(hssfPictureData.getData())) {
            return "";
        }
        InputStream inputStream = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
            String tempName = DateUtils.getCurrentSTimeNumber() + ".jpg";//文件名=="时间"+".jpg"
            inputStream = new ByteArrayInputStream(hssfPictureData.getData());
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(inputStream, serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (Exception e) {
            logger.error("上传文件失败，错误原因：{}", e);
        }
        return "";
    }

    /**
     * excel07图片上传
     *
     * @param xssfPictureData 07图片data流
     * @param pathName        文件路径名
     * @return
     */
    private String uploadExcelPic07(XSSFPictureData xssfPictureData, String pathName) {
        if (null == xssfPictureData || ArrayUtils.isEmpty(xssfPictureData.getData())) {
            return "";
        }
        InputStream inputStream = null;
        try {
            String serverPath = pathName + "/" + DateUtils.dateParseShortString(new Date()) + "/";    //------>pathName/2018-03-07/
            String tempName = DateUtils.getCurrentSTimeNumber() + ".jpg";//文件名=="时间"+".jpg"
            inputStream = new ByteArrayInputStream(xssfPictureData.getData());
            // 最后返回URL地址
            if (FtpUtils.uploadToFtpServer(inputStream, serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-08-07/20180307151504492.jpg
            }
        } catch (Exception e) {
            logger.error("上传文件失败，错误原因：{}", e);
        }
        return "";
    }
}