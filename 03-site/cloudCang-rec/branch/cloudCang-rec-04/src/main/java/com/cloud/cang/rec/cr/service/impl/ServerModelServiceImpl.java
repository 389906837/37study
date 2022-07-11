package com.cloud.cang.rec.cr.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.rec.common.utils.FTPTools;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.common.utils.PythonUtil;
import com.cloud.cang.rec.cr.dao.ServerModelDao;
import com.cloud.cang.rec.cr.domain.ServerModelDomain;
import com.cloud.cang.rec.cr.service.ServerModelService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Date;

@Service
public class ServerModelServiceImpl extends GenericServiceImpl<ServerModel, String> implements
        ServerModelService {
    private final static Logger logger = LoggerFactory.getLogger(ServerModelServiceImpl.class);

    @Autowired
    ServerModelDao serverModelDao;


    // 测试服务路径
    public static final String MODEL_SERVER ="/data/fileServer/model";

    public static final String DARKNET_FIEL ="darknet";

    public static final String BMODEL_FILE ="bmodel";

    @Override
    public GenericDao<ServerModel, String> getDao() {
        return serverModelDao;
    }

    /**
     * 查询列表
     *
     * @param page
     * @param serverModelDomain
     * @return
     */
    @Override
    public Page<ServerModel> selectPageByDomainWhere(Page<ServerModel> page, ServerModelDomain serverModelDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return serverModelDao.selectPageByDomainWhere(serverModelDomain);
    }

    /**
     * 新增
     *
     * @param serverModel
     * @return
     */
    @Override
    public void saveServerModel(ServerModel serverModel, File file) {
        if (!file.exists()) {
            serverModel.setSfileSize(String.valueOf(0));
        } else {
            serverModel.setSfileSize(String.valueOf(file.length() / 1024 * 1024));
        }
        serverModel.setSfileSizeUnit("MB");
        serverModel.setScode(CoreUtils.newCode(EntityTables.CR_SERVER_MODEL));
        serverModel.setIstatus(10);
        serverModel.setIdelFlag(0);
        serverModel.setTaddTime(DateUtils.getCurrentDateTime());
        serverModel.setTupdateTime(DateUtils.getCurrentDateTime());
        serverModel.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModel.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModelDao.insert(serverModel);
        //操作日志
        String logText = MessageFormat.format("新增识别服务模型，模型编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }





    /**
     * 复制文件
     *
     * @param in  输入流
     * @param savePath 输出文件保留路径
     * @param fileName 输出文件名
     * @return
     * @throws Exception
     */
    public static boolean copyFile(InputStream in, String savePath, String fileName) {
        OutputStream os = null;
        try {
            File tempfile = new File(savePath);
            if (!tempfile.exists()) {
                tempfile.mkdirs();
            }
            String filePath = savePath + fileName;
            File file = new File(filePath);
            os = new FileOutputStream(file);
            byte[] buff = new byte[2048];
            while (true) {
                int readed = in.read(buff);
                if (readed == -1) {
                    break;
                }
                byte[] temp = new byte[readed];
                System.arraycopy(buff, 0, temp, 0, readed);
                os.write(temp);
            }
            return true;
        } catch (Exception e) {
            logger.error("上传模型异常：{}", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭输入流异常：{}", e);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭输出流异常：{}", e);
                }
            }
        }
        return false;
    }


    public String upload(MultipartFile file, String code, Integer modelType) {
        //文件原名
        String filName = file.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("zip")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        try {
            String savePath = MODEL_SERVER + StrUtil.SLASH + code + StrUtil.SLASH;
            String fileFileName = file.getOriginalFilename();
            // 返回保存路径
            if (copyFile(file.getInputStream(), savePath, fileFileName)) { //复制文件
                if (PythonUtil.unzipModel(savePath, modelType)){  //解压文件
                    return savePath;
                }else {
                    throw new ServiceException("解压文件失败");
                }
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return null;
    }


    /**
     * @param modelFile MultipartFile 文件
     * @param jsonStr   对象json字符串
     * @param modelType 模型类型 10 bmodel模型， 20 darknet模型
     * @return
     */
    public Boolean insertModel(MultipartFile modelFile, String jsonStr, Integer modelType){
        ServerModel serverModel = JSONUtil.toBean(jsonStr, ServerModel.class);
        serverModel.setSfileSizeUnit("MB");
        serverModel.setScode(CoreUtils.newCode(EntityTables.CR_SERVER_MODEL));
        String modelPath = "";
        if (null != modelFile && modelFile.getSize() > 0) {
            try {
                modelPath = upload(modelFile, serverModel.getScode(),  modelType);
            } catch (ServiceException e) {
                return false;
            }
            serverModel.setImodelType(modelType);
        }
        File file = new File(modelPath);
        if (!file.exists()) {
            serverModel.setSfileSize(String.valueOf(0));
        } else {
            serverModel.setSfileSize(String.valueOf(file.length() / 1024 * 1024));
        }
        serverModel.setSfileType("10");
        serverModel.setSmodelAddress(modelPath);
        serverModelDao.insert(serverModel);
        //操作日志
        String logText = MessageFormat.format("新增识别服务模型，模型编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);

        return true;
    }


    /**
     * 上传
     *
     * @param
     * @return
     */
    @Override
    public Boolean uploadModel(MultipartFile bmodelZipFile, MultipartFile darknetZipFile,  String jsonStr) {
        //上传bmodel模型&&上传darknet模型
        return insertModel(bmodelZipFile, jsonStr, 10)&&insertModel(darknetZipFile, jsonStr, 20);
    }




    /**
     * 执行修改
     *
     * @param serverModel
     */
    @Override
    public void upServerModel(ServerModel serverModel, File file) {
         if (!file.exists()) {
            serverModel.setSfileSize(String.valueOf(0));
        } else {
            serverModel.setSfileSize(String.valueOf(file.length() / 1024 * 1024));
        }
        serverModel.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModel.setTupdateTime(DateUtils.getCurrentDateTime());
        serverModelDao.updateByIdSelective(serverModel);
        //操作日志
        String logText = MessageFormat.format("修改识别服务模型，模型编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                ServerModel serverModel = new ServerModel();
                serverModel.setId(id);
                serverModel.setIdelFlag(1);
                serverModelDao.updateByIdSelective(serverModel);
            }
        }
    }

    /**
     * 审核
     *
     * @param serverModel
     * @return
     */
    @Override
    public void serverModelAudit(ServerModel serverModel) {
        serverModel.setTauditTime(DateUtils.getCurrentDateTime());
        serverModel.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModelDao.updateByIdSelective(serverModel);
    }

    /**
     * 根据模型编号查询
     *
     * @param code
     * @return
     */
    @Override
    public ServerModel selectByCode(String code) {
        return serverModelDao.selectByCode(code);
    }


}