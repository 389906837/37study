package com.cloud.cang.mgr.sp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.utils.BigDecimalUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.ActivityConfDao;
import com.cloud.cang.mgr.ac.domain.ActivityConfValidDomain;
import com.cloud.cang.mgr.ac.service.UseRangeService;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.om.vo.OrderCommodityEditVo;
import com.cloud.cang.mgr.sb.dao.DeviceCommodityDao;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sm.service.DeviceStockService;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sp.dao.HistoryPriceDao;
import com.cloud.cang.mgr.sp.domain.CommodityDomain;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sp.vo.CommodityVo;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sp.HistoryPrice;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CommodityInfoServiceImpl extends GenericServiceImpl<CommodityInfo, String> implements
        CommodityInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityInfoServiceImpl.class);

    @Autowired
    CommodityInfoDao commodityInfoDao;

    @Autowired
    DeviceCommodityDao deviceCommodityDao;

    @Autowired
    HistoryPriceDao historyPriceDao;

    @Autowired
    private UseRangeService useRangeService;

    @Autowired
    DeviceStockDao deviceStockDao;

    @Autowired
    DeviceStockService deviceStockService;

    @Autowired
    ActivityConfDao activityConfDao;

    @Override
    public GenericDao<CommodityInfo, String> getDao() {
        return commodityInfoDao;
    }

    /**
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.sp.domain.CommodityDomain>
     * @Author: zhouhong
     * @Description: ????????????????????????
     * @param: page ????????????
     * @param: commodityVo ????????????
     * @Date: 2018/2/10 12:38
     */
    @Override
    public Page<CommodityDomain> selectPageByDomainWhere(Page<CommodityDomain> page, CommodityVo commodityVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = commodityVo.getSname();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            commodityVo.setSname(StringUtils.join(chars, '%'));
        }
        return commodityInfoDao.selectPageByDomainWhere(commodityVo);
    }

    /**
     * @Description: ????????????????????????
     * @param: page ????????????
     * @param: commodityVo ????????????
     */
    @Override
    public Page<CommodityDomain> selectOrderCommodity(Page<CommodityDomain> page, CommodityVo commodityVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = commodityVo.getSname();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            commodityVo.setSname(StringUtils.join(chars, '%'));
        }
        return commodityInfoDao.selectOrderCommodity(commodityVo);
    }


    /**
     * ????????????????????????
     *
     * @param commodityCode ????????????
     * @return
     */
    @Override
    public CommodityInfo selectByCode(String commodityCode) {
        return commodityInfoDao.selectByCode(commodityCode);
    }

    /**
     * ??????ID??????????????????????????????
     *
     * @param checkboxId
     */
    @Override
    public void batchDelByIds(String[] checkboxId, String operater, Date stamp, String merchantId) {
        CommodityInfo commodityInfo = null;
        List<ActivityConfValidDomain> validActList = activityConfDao.selectAllValidByMerchantId(merchantId);    //????????????ID????????????????????????????????????????????????????????????

        //?????????????????????????????????????????????????????????????????????????????????
        for (String id : checkboxId) {
            CommodityInfo commodityInfo1 = commodityInfoDao.selectByPrimaryKey(id);
            if (null != commodityInfo1) {
                if (commodityInfo1.getIstatus() == 10) {
                    throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.commodity.status.error",null)+"???"+MessageSourceUtils.getMessageByKey("main.delete.error",null)+"");
                }
            }

            // ???????????????????????????????????? >0 ?????????
            DeviceStock deviceStock = new DeviceStock();
            deviceStock.setScommodityId(id);
            List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
            if (CollectionUtils.isNotEmpty(deviceStockList)) {
                for (DeviceStock deviceStock1 : deviceStockList) {
                    Integer temp = deviceStock1.getIstock();
                    if (temp != null && temp > 0) {
                        throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.commodity.stock.status.error",null)+"???"+ MessageSourceUtils.getMessageByKey("main.delete.error",null)+"");
                    }
                }
            }

            // ?????????????????????????????????
            if (CollectionUtils.isNotEmpty(validActList)) {
                for (ActivityConfValidDomain act : validActList) {
                    String commodityIdString = act.getScommodityId();
                    if (StringUtils.isNotBlank(commodityIdString) && commodityIdString.contains(id)) {
                        throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.commodity.active.status.error",null));
                    }
                }
            }
        }


        //???????????????????????????????????????
        for (String id : checkboxId) {
            commodityInfo = new CommodityInfo();
            commodityInfo.setId(id);
            commodityInfo.setIdelFlag(1);//??????????????????????????????????????????1
            commodityInfoDao.updateByIdSelective(commodityInfo);
            deviceStockDao.deleteByCommodityId(id);//????????????ID????????????????????????????????????

            // ?????????????????????
            DeviceCommodity deviceCommodity = new DeviceCommodity();
            deviceCommodity.setIstatus(20);
            deviceCommodity.setScommodityId(id);
            deviceCommodity.setTupdateTime(stamp);
            deviceCommodity.setSupdateUser(operater);
            deviceCommodityDao.updateToDropOffByCommodityId(deviceCommodity);
        }
    }

    @Override
    public List<CommodityInfo> selectByKeys(String[] commodityArray) {

        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(commodityArray)) {
            for (String id : commodityArray) {
                CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(id);
                if (null != commodityInfo) {
                    commodityInfoList.add(commodityInfo);
                }
            }
        }
        return commodityInfoList;
    }

    /**
     * ???????????????????????????
     *
     * @param checkboxId
     * @param operate
     * @param merchantId
     */
    @Override
    @Transactional
    public ResponseVo<String> updateStatusByIds(String[] checkboxId, String operate, String user, Date updateTime, String merchantId) {
        DeviceStock deviceStock = null;
        Integer num = null;
        Boolean gtZero = false;
        String commodityCode = "";
        List<ActivityConfValidDomain> validActList = activityConfDao.selectAllValidByMerchantId(merchantId); // ?????????????????????

        for (String id : checkboxId) {
            CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(id);
            if (null != commodityInfo) {
                Integer status = commodityInfo.getIstatus();
                commodityCode = commodityInfo.getScode();
                if (Integer.valueOf(10).equals(status) && Constrants.COMMONDITY_DROPOFF.equals(operate)) { // ??????????????? 10=??????

                    // ?????????????????????????????????
                    if (CollectionUtils.isNotEmpty(validActList)) {
                        for (ActivityConfValidDomain act : validActList) {
                            String commodityIdString = act.getScommodityId();
                            if (StringUtils.isNotBlank(commodityIdString) && commodityIdString.contains(id)) {
                                throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.obtained.commodity.exception",null));
                            }
                        }
                    }

                    // ????????????????????????????????????????????????
                    deviceStock = new DeviceStock();
                    deviceStock.setScommodityId(id);
                    List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
                    if (CollectionUtils.isNotEmpty(deviceStockList)) {
                        for (DeviceStock stock : deviceStockList) {
                            num = stock.getIstock();
                            if (num != null) {
                                if (num.compareTo(Integer.valueOf(0)) == 1) { // ????????????0 ??????1
                                    throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.obtained.commodity.selling.exception",null));
                                }
                            }
                        }
                    }
                    commodityInfo.setIstatus(20);
                    // ?????????????????????
                    DeviceCommodity deviceCommodity = new DeviceCommodity();
                    deviceCommodity.setIstatus(20);
                    deviceCommodity.setScommodityId(id);
                    deviceCommodity.setTupdateTime(updateTime);
                    deviceCommodity.setSupdateUser(user);
                    deviceCommodityDao.updateToDropOffByCommodityId(deviceCommodity);
                } else if (Integer.valueOf(20).equals(status) && Constrants.COMMONDITY_SHELF.equals(operate)) { // 20=??????
                    commodityInfo.setIstatus(10);
                } else { // ??????????????????????????????
                    continue;
                }
                commodityInfoDao.updateByIdSelective(commodityInfo);


            }
        }
        return ResponseVo.getSuccessResponse(commodityCode);
    }

    @Override
    public List<CommodityInfo> selectUnDelById(String merchantId, String skuId) {
        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setSmerchantId(merchantId);
        commodityInfo.setSvrCommodityId(skuId);
        return commodityInfoDao.selectUnDelById(commodityInfo);
    }

    /**
     * ??????????????????
     *
     * @param commodityInfo
     * @param scommodityImgfile
     * @return
     */
    @Override
    public ResponseVo<String> updateCommodityById(CommodityInfo commodityInfo, MultipartFile scommodityImgfile) {
        String commodityId = commodityInfo.getId();
        String sbrandName = commodityInfo.getSbrandName();//??????--??????
        String sbrandId = commodityInfo.getSbrandId();//??????--id
        String label = commodityInfo.getSlabelName();//??????--id_??????
        String bigCategory = commodityInfo.getSbigCategoryName();//??????--id_??????
        String smallCategory = commodityInfo.getSsmallCategoryName();//??????--id_??????
        String addUserName = SessionUserUtils.getSessionAttributeForUserDtl().getRealName(); //?????????
        Integer istoreDevice = commodityInfo.getIstoreDevice(); // ???????????? 10=?????? 20=rfid?????? 30=??????
        Date date = DateUtils.getCurrentDateTime();    // ????????????
        BigDecimal commodityFloat = commodityInfo.getIcommodityFloat();     // ???????????????????????????
        if (null == commodityFloat) {
            commodityInfo.setIcommodityFloat(BigDecimal.ZERO);   // ?????????????????????0.00
        }

        if (istoreDevice != 10) {
            if (StringUtils.isBlank(sbrandId) || StringUtils.isBlank(sbrandName)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("vrcon.brandinfo.empty",null));
            }
            if (StringUtils.isNotBlank(label)) {
                commodityInfo.setSlabelId(label.split("_")[0]);//??????ID
                commodityInfo.setSlabelName(label.split("_")[1]);//????????????
            }
            if (StringUtils.isNotBlank(bigCategory)) {
                commodityInfo.setSbigCategoryId(bigCategory.split("_")[0]);//??????ID
                commodityInfo.setSbigCategoryName(bigCategory.split("_")[1]);//????????????
            }
            if (StringUtils.isNotBlank(smallCategory)) {
                commodityInfo.setSsmallCategoryId(smallCategory.split("_")[0]);//??????ID
                commodityInfo.setSsmallCategoryName(smallCategory.split("_")[1]);//????????????
            }

            if (null != scommodityImgfile && scommodityImgfile.getSize() > 0) {//????????????
                String url = uploadHome(scommodityImgfile, "commodityInfo");
                if (StringUtils.isNotBlank(url)) {
                    commodityInfo.setScommodityImg(url);//????????????
                }
            }
        }

        commodityInfo.setTupdateTime(date);//????????????
        commodityInfo.setSupdateUser(addUserName);//?????????
        CommodityInfo commodityInfoTemp = commodityInfoDao.selectByPrimaryKey(commodityId); // ?????????????????????
        String merchantId = commodityInfoTemp.getSmerchantId();
        String merchantCode = commodityInfoTemp.getSmerchantCode();
        String commodityCode = commodityInfoTemp.getScode();
        BigDecimal costPriceTemp = commodityInfoTemp.getFcostPrice();    // ???????????????
        BigDecimal salePriceTemp = commodityInfoTemp.getFsalePrice();   // ???????????????
        BigDecimal costPrice = commodityInfo.getFcostPrice();    // ?????????
        BigDecimal salePrice = commodityInfo.getFsalePrice();    // ?????????
        if (costPrice != null && BigDecimalUtils.ltZero(costPrice)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.modify.commodity.exception",null));
        }
        if (salePrice != null && BigDecimalUtils.ltZero(salePrice)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.modify.commodity.sale.price.exception",null));
        }
        if (null == costPrice) {
            costPrice = new BigDecimal("0");
        }
        if (null == costPriceTemp) {
            costPriceTemp = new BigDecimal("0");
        }
        if (null == salePrice) {
            salePrice = new BigDecimal("0");
        }
        if (null == salePriceTemp) {
            salePriceTemp = new BigDecimal("0");
        }
        String avgSaleId = "";
        String avgCostId = "";
        BigDecimal avgSalePrice = null;    // ?????????????????????
        BigDecimal avgCostPrice = null; // ?????????????????????
        if (!BigDecimalUtils.equals(costPriceTemp, costPrice)) {    // ???????????????
            HistoryPrice historyPrice = new HistoryPrice();
            historyPrice.setSmerchantId(merchantId);
            historyPrice.setSmerchantCode(merchantCode);
            historyPrice.setScommodityId(commodityId);
            historyPrice.setScommodityCode(commodityCode);
            historyPrice.setFoldAmount(costPriceTemp);    // ?????????
            historyPrice.setFnewAmount(costPrice);    // ?????????
            historyPrice.setItype(10); // 10=????????? 20=?????????
            historyPrice.setTaddTime(date);
            historyPrice.setSaddUser(addUserName);
            historyPriceDao.insert(historyPrice);

            historyPrice = new HistoryPrice();
            historyPrice.setScommodityId(commodityId);
            historyPrice.setItype(10);// 10=????????? 20=?????????
            avgCostPrice = historyPriceDao.selectAvgPriceById(historyPrice);
        }
        if (!BigDecimalUtils.equals(salePriceTemp, salePrice)) {    // ???????????????
            HistoryPrice historyPrice = new HistoryPrice();
            historyPrice.setSmerchantId(merchantId);
            historyPrice.setSmerchantCode(merchantCode);
            historyPrice.setScommodityId(commodityId);
            historyPrice.setScommodityCode(commodityCode);
            historyPrice.setFoldAmount(salePriceTemp);    // ?????????
            historyPrice.setFnewAmount(salePrice);    // ?????????
            historyPrice.setItype(20); // 10=????????? 20=?????????
            historyPrice.setTaddTime(date);
            historyPrice.setSaddUser(addUserName);
            historyPriceDao.insert(historyPrice);

            historyPrice = new HistoryPrice();
            historyPrice.setScommodityId(commodityId);
            historyPrice.setItype(20);// 10=????????? 20=?????????
            avgSalePrice = historyPriceDao.selectAvgPriceById(historyPrice);
        }

        if (null != avgCostPrice) {
            commodityInfo.setFavgCostPrice(avgCostPrice);
        }
        if (null != avgSalePrice) {
            commodityInfo.setFavgSalePrice(avgSalePrice);
        }
        commodityInfoDao.updateByIdSelectiveVo(commodityInfo);
        return ResponseVo.getSuccessResponse(commodityInfo.getScode());
    }

    /**
     * ??????????????????????????????
     *
     * @param activityConf ????????????
     */
    @Override
    public void updateCommodityJoinNum(final ActivityConf activityConf) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (activityConf.getIrangeType().intValue() == 10 || activityConf.getIrangeType().intValue() == 20) {
                        //??????  ?????????????????????????????? +1
                        commodityInfoDao.updateJoinNumByMerchantId(activityConf.getSmerchantId());
                    } else {
                        //????????????????????????????????? 	//???????????????????????????
                        UseRange useRange = useRangeService.selectByActId(activityConf.getId());
                        if (null == useRange || StringUtil.isBlank(useRange.getScommodityId())) {
                            logger.error("???????????????{}", activityConf);
                        }
                        String arr[] = useRange.getScommodityId().split(",");
                        if (null != arr && arr.length > 0) {
                            StringBuffer sb = new StringBuffer();
                            for (String commodityId : arr) {
                                sb.append("'" + commodityId + "',");
                            }
                            if (sb.toString().length() > 0) {
                                String commodityIds = sb.toString().substring(0, sb.toString().length() - 1);
                                Map<String, String> updateParams = new HashMap<String, String>();
                                updateParams.put("smerchantId", activityConf.getSmerchantId());
                                updateParams.put("commodityIds", commodityIds);
                                commodityInfoDao.updateJoinNumByCommodityIds(updateParams);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("???????????????????????????????????????{}", activityConf);
                }
            }
        });
    }

    /**
     * ??????????????????
     *
     * @param checkboxId
     */
    @Override
    public void syncComFsalePrice(String[] checkboxId) {
        // ?????????????????????????????????
        CommodityInfo commodityInfo = new CommodityInfo();
        List<CommodityInfo> commodityInfoList = this.selectByEntityWhere(commodityInfo);
        DeviceStock deviceStock = null;
        // ??????????????????
        if (commodityInfoList != null && commodityInfoList.size() > 0) {
            for (CommodityInfo com : commodityInfoList) {
                // ???????????????ID
                for (String id : checkboxId) {
                    deviceStock = deviceStockService.selectByPrimaryKey(id);
                    DeviceStock deviceStocks = new DeviceStock();
                    if (com.getId().equals(deviceStock.getScommodityId())) {
                        deviceStocks.setId(id);
                        deviceStocks.setScommodityId(com.getId());
                        deviceStocks.setFsalePrice(com.getFsalePrice());
                        deviceStockService.updateBySelective(deviceStocks);
                    }
                }
            }
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param smerchantId
     * @return
     */
    @Override
    public List<CommodityInfo> selectAllCommodityByMerchantId(String smerchantId) {
        return commodityInfoDao.selectAllCommodityByMerchantId(smerchantId);
    }

    @Override
    public List<CommodityInfo> queryAllCom() {
        return commodityInfoDao.queryAllCom();
    }

    /**
     * ??????????????????
     *
     * @param file
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        if (file == null) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.empty",null));
        }


        String filName = file.getOriginalFilename();//????????????
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//???????????????====>jpg|png|bmp
        //??????????????????
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.error",null));
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/

            String tempName = DateUtils.getCurrentSTimeNumber() + "." + exp;//?????????=="??????"+"."+"??????????????????"
            // ??????URL??????
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// ?????????------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("??????????????????IOException?????????{}", e);
        }
        return null;
    }


    /**
     * ??????????????????
     *
     * @return
     */
    @Override
    public OrderCommodityEditVo selectExisCommodity(String id, String deviceId) {
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap();
        map.put("id", id);
        map.put("deviceId", deviceId);
        map.put("merchantId", merchantId);
        return commodityInfoDao.selectExisCommodity(map);
    }

    @Override
    public List<Map<String, Object>> selectCommodityInfoByExport(CommodityVo commodityVo) {
        return commodityInfoDao.selectCommodityInfoByExport(commodityVo);
    }

}