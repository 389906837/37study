package com.cloud.cang.mgr.sh.service.impl;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.dao.MerchantClientConfDao;
import com.cloud.cang.mgr.sh.service.MerchantClientConfService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.open.sdk.mapping.StringUtils;
import com.cloud.cang.sb.CloudParamConfigDto;
import com.cloud.cang.sb.DeviceServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

@Service
public class MerchantClientConfServiceImpl extends GenericServiceImpl<MerchantClientConf, String> implements
        MerchantClientConfService {

    @Autowired
    MerchantClientConfDao merchantClientConfDao;

    @Autowired
    private ICached iCached;

    private static final Logger log = LoggerFactory.getLogger(MerchantClientConfServiceImpl.class);

    @Override
    public GenericDao<MerchantClientConf, String> getDao() {
        return merchantClientConfDao;
    }

    /**
     * 添加数据
     *
     * @param merchantClientConf
     */
    @Override
    public void insertAll(MerchantClientConf merchantClientConf) {
        merchantClientConfDao.insertAll(merchantClientConf);
    }

    /**
     * 根据Id及其他数据查询
     *
     * @param merchantClientConf
     */
    @Override
    public MerchantClientConf selectByWhere(MerchantClientConf merchantClientConf) {
        return merchantClientConfDao.selectByWhere(merchantClientConf);
    }

    @Transactional
    @Override
    public ResponseVo upMerchant(MultipartFile merLogofile, MultipartFile loginLogofile, MerchantClientConf merchantClientConf) throws Exception {
        MerchantClientConf oldMerchantClientConf = merchantClientConfDao.selectByPrimaryKey(merchantClientConf.getId());
        merchantClientConf.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantClientConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        //修改商户客户端配置表
        if (null != loginLogofile && loginLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            try {
                url = uploadHome(loginLogofile);
                merchantClientConf.setSloginLogo(url);
            } catch (ServiceException e) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
            }
        }
        if (null != merLogofile && merLogofile.getSize() > 0) {
            // 1.文件上传
            String url;
            try {
                url = uploadHome(merLogofile);
                merchantClientConf.setSlogo(url);
            } catch (ServiceException e) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
            }
        }
        merchantClientConf.setTupdateTime(DateUtils.getCurrentDateTime());
        merchantClientConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        merchantClientConfDao.updateByIdSelective(merchantClientConf);
        String catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantClientConf.getScode();
        //MerchantClientConf newMerchantClientConf = merchantClientConfDao.selectByPrimaryKey(merchantClientConf.getId());
        try {
            iCached.hset(RedisConst.SH_CLIENT_CONFIG, catcheKey, merchantClientConf);
        } catch (Exception e) {
            log.error("----客户端配置，更新Redis结束，缓存失败！！，错误信息：" + e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新客户端配置缓存失败");
        }
        log.debug("----更新Redis缓存主KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + merchantClientConf);
        //操作日志
        String logText = MessageFormat.format("编辑商户，编号{0}", merchantClientConf.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        //修改云端识别APPID、云端识别访问地址、云端补货是否开启实时盘货 则调用 云端设备配置参数调整 接口
        if ((StringUtil.isBlank(oldMerchantClientConf.getScloudAppId()) && StringUtil.isNotBlank(merchantClientConf.getScloudAppId())) ||
                oldMerchantClientConf.getScloudAppId() != merchantClientConf.getScloudAppId() ||
                (StringUtil.isBlank(oldMerchantClientConf.getScloudHost()) && StringUtil.isNotBlank(merchantClientConf.getScloudHost())) ||
                oldMerchantClientConf.getScloudHost() != merchantClientConf.getScloudHost() ||
                (null == oldMerchantClientConf.getIisOpeningInventory() && null != merchantClientConf.getIisOpeningInventory()) ||
                oldMerchantClientConf.getIisOpeningInventory() != merchantClientConf.getIisOpeningInventory()) {
            log.info("修改商户客户端配置,调用云端设备配置参数调整接口：{}", oldMerchantClientConf.getScode());
            CloudParamConfigDto cloudParamConfigDto = new CloudParamConfigDto();
            cloudParamConfigDto.setType(20);
            cloudParamConfigDto.setMerchantCode(oldMerchantClientConf.getScode());
            cloudParamConfigDto.setMerchantId(oldMerchantClientConf.getId());
            //修改云端识别APPID、云端识别访问地址 需要传APPID
            if ((StringUtil.isBlank(oldMerchantClientConf.getScloudAppId()) && StringUtil.isNotBlank(merchantClientConf.getScloudAppId())) ||
                    oldMerchantClientConf.getScloudAppId() != merchantClientConf.getScloudAppId() ||
                    (StringUtil.isBlank(oldMerchantClientConf.getScloudHost()) && StringUtil.isNotBlank(merchantClientConf.getScloudHost())) ||
                    oldMerchantClientConf.getScloudHost() != merchantClientConf.getScloudHost()) {
                cloudParamConfigDto.setCloudAppId(merchantClientConf.getScloudAppId());
            }
            if ((null == oldMerchantClientConf.getIisOpeningInventory() && null != merchantClientConf.getIisOpeningInventory()) ||
                    oldMerchantClientConf.getIisOpeningInventory() != merchantClientConf.getIisOpeningInventory()) {
                cloudParamConfigDto.setReplenishmentInventory(merchantClientConf.getIisOpeningInventory());
            }
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.CLOUD_DEVICE_PARAM_CONFIG);
            invoke.setRequestObj(cloudParamConfigDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null == responseVo || !responseVo.isSuccess()) {
                log.error("修改商户客户端配置,调用云端设备配置参数调整接口失败:{}", responseVo);
                throw new ServiceException("修改商户客户端配置,调用云端设备配置参数调整接口失败！");
            }
        }
        return ResponseVo.getSuccessResponse();
    }

    public String uploadHome(MultipartFile file) {
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
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";
        try {
            String serverPath = "merchant_clientConf/" + DateUtils.dateParseShortString(new Date()) + "/";
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