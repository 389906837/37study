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
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
     * ????????????
     *
     * @param merchantClientConf
     */
    @Override
    public void insertAll(MerchantClientConf merchantClientConf) {
        merchantClientConfDao.insertAll(merchantClientConf);
    }

    /**
     * ??????Id?????????????????????
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
        //??????????????????????????????
        if (null != loginLogofile && loginLogofile.getSize() > 0) {
            // 1.????????????
            String url;
            try {
                url = uploadHome(loginLogofile);
                merchantClientConf.setSloginLogo(url);
            } catch (ServiceException e) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
            }
        }
        if (null != merLogofile && merLogofile.getSize() > 0) {
            // 1.????????????
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
            log.error("----????????????????????????Redis?????????????????????????????????????????????" + e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("shcon.add.merchant.conf.error",null));
        }
        log.debug("----??????Redis?????????KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + merchantClientConf);
        //????????????
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("shcon.edit.merchant.conf.log",null)+"???"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", merchantClientConf.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        //??????????????????APPID?????????????????????????????????????????????????????????????????? ????????? ?????????????????????????????? ??????
        if ((StringUtil.isBlank(oldMerchantClientConf.getScloudAppId()) && StringUtil.isNotBlank(merchantClientConf.getScloudAppId())) ||
                oldMerchantClientConf.getScloudAppId() != merchantClientConf.getScloudAppId() ||
                (StringUtil.isBlank(oldMerchantClientConf.getScloudHost()) && StringUtil.isNotBlank(merchantClientConf.getScloudHost())) ||
                oldMerchantClientConf.getScloudHost() != merchantClientConf.getScloudHost() ||
                (null == oldMerchantClientConf.getIisOpeningInventory() && null != merchantClientConf.getIisOpeningInventory()) ||
                oldMerchantClientConf.getIisOpeningInventory() != merchantClientConf.getIisOpeningInventory()) {
            log.info("???????????????????????????,?????????????????????????????????????????????{}", oldMerchantClientConf.getScode());
            CloudParamConfigDto cloudParamConfigDto = new CloudParamConfigDto();
            cloudParamConfigDto.setType(20);
            cloudParamConfigDto.setMerchantCode(oldMerchantClientConf.getScode());
            cloudParamConfigDto.setMerchantId(oldMerchantClientConf.getId());
            //??????????????????APPID??????????????????????????? ?????????APPID
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
            invoke.setRequestObj(cloudParamConfigDto); // post ??????
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null == responseVo || !responseVo.isSuccess()) {
                log.error("???????????????????????????,????????????????????????????????????????????????:{}", responseVo);
                throw new ServiceException(MessageSourceUtils.getMessageByKey("shcon.add.merchant.conf.exception",null));
            }
        }
        return ResponseVo.getSuccessResponse();
    }

    public String uploadHome(MultipartFile file) {
        if (file == null) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.empty",null));
        }
        //??????????????????
      /*  if (111 < file.getSize()) {

        }*/
        //????????????
        String filName = file.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //??????????????????
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.error",null));
        }
        String url = "";
        try {
            String serverPath = "merchant_clientConf/" + DateUtils.dateParseShortString(new Date()) + "/";
            String fileFileName = file.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            // ??????URL??????
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.not.found",null));
        }
        return null;
    }
}