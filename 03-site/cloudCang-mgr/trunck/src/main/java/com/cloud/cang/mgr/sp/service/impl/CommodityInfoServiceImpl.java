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
     * @Description: 分页查询商品信息
     * @param: page 分页参数
     * @param: commodityVo 查询参数
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
     * @Description: 编辑订单选择商品
     * @param: page 分页参数
     * @param: commodityVo 查询参数
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
     * 编号获取商品信息
     *
     * @param commodityCode 商品编号
     * @return
     */
    @Override
    public CommodityInfo selectByCode(String commodityCode) {
        return commodityInfoDao.selectByCode(commodityCode);
    }

    /**
     * 根据ID批量删除（逻辑删除）
     *
     * @param checkboxId
     */
    @Override
    public void batchDelByIds(String[] checkboxId, String operater, Date stamp, String merchantId) {
        CommodityInfo commodityInfo = null;
        List<ActivityConfValidDomain> validActList = activityConfDao.selectAllValidByMerchantId(merchantId);    //根据商户ID查询所有包含部分商品有效范围内的活动信息

        //循环遍历商品是否都是下架状态，如果存在下架状态跑出异常
        for (String id : checkboxId) {
            CommodityInfo commodityInfo1 = commodityInfoDao.selectByPrimaryKey(id);
            if (null != commodityInfo1) {
                if (commodityInfo1.getIstatus() == 10) {
                    throw new ServiceException("选中的商品中存在未下架的商品，删除失败");
                }
            }

            // 循环遍历商品是否包含库存 >0 的商品
            DeviceStock deviceStock = new DeviceStock();
            deviceStock.setScommodityId(id);
            List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
            if (CollectionUtils.isNotEmpty(deviceStockList)) {
                for (DeviceStock deviceStock1 : deviceStockList) {
                    Integer temp = deviceStock1.getIstock();
                    if (temp != null && temp > 0) {
                        throw new ServiceException("选中的商品中存在库存大于0的商品，删除失败");
                    }
                }
            }

            // 判断该商品是否参加活动
            if (CollectionUtils.isNotEmpty(validActList)) {
                for (ActivityConfValidDomain act : validActList) {
                    String commodityIdString = act.getScommodityId();
                    if (StringUtils.isNotBlank(commodityIdString) && commodityIdString.contains(id)) {
                        throw new ServiceException("该商品正在参加优惠活动，无法删除");
                    }
                }
            }
        }


        //校验通过的可以进行逻辑删除
        for (String id : checkboxId) {
            commodityInfo = new CommodityInfo();
            commodityInfo.setId(id);
            commodityInfo.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
            commodityInfoDao.updateByIdSelective(commodityInfo);
            deviceStockDao.deleteByCommodityId(id);//根据商品ID删除对应设备中的商品信息

            // 修改设备商品表
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
     * 改变当前商品的状态
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
        List<ActivityConfValidDomain> validActList = activityConfDao.selectAllValidByMerchantId(merchantId); // 商品参加的活动

        for (String id : checkboxId) {
            CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(id);
            if (null != commodityInfo) {
                Integer status = commodityInfo.getIstatus();
                commodityCode = commodityInfo.getScode();
                if (Integer.valueOf(10).equals(status) && Constrants.COMMONDITY_DROPOFF.equals(operate)) { // 下架该商品 10=正常

                    // 判断该商品是否参加活动
                    if (CollectionUtils.isNotEmpty(validActList)) {
                        for (ActivityConfValidDomain act : validActList) {
                            String commodityIdString = act.getScommodityId();
                            if (StringUtils.isNotBlank(commodityIdString) && commodityIdString.contains(id)) {
                                throw new ServiceException("该商品正在参加优惠活动，无法下架");
                            }
                        }
                    }

                    // 判断该商品在设备库存表中是否存在
                    deviceStock = new DeviceStock();
                    deviceStock.setScommodityId(id);
                    List<DeviceStock> deviceStockList = deviceStockDao.selectByEntityWhere(deviceStock);
                    if (CollectionUtils.isNotEmpty(deviceStockList)) {
                        for (DeviceStock stock : deviceStockList) {
                            num = stock.getIstock();
                            if (num != null) {
                                if (num.compareTo(Integer.valueOf(0)) == 1) { // 数量大于0 返回1
                                    throw new ServiceException("设备中存在未售完的商品，因此不能下架该商品");
                                }
                            }
                        }
                    }
                    commodityInfo.setIstatus(20);
                    // 修改设备商品表
                    DeviceCommodity deviceCommodity = new DeviceCommodity();
                    deviceCommodity.setIstatus(20);
                    deviceCommodity.setScommodityId(id);
                    deviceCommodity.setTupdateTime(updateTime);
                    deviceCommodity.setSupdateUser(user);
                    deviceCommodityDao.updateToDropOffByCommodityId(deviceCommodity);
                } else if (Integer.valueOf(20).equals(status) && Constrants.COMMONDITY_SHELF.equals(operate)) { // 20=失效
                    commodityInfo.setIstatus(10);
                } else { // 状态和操作类型不一致
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
     * 编辑商品信息
     *
     * @param commodityInfo
     * @param scommodityImgfile
     * @return
     */
    @Override
    public ResponseVo<String> updateCommodityById(CommodityInfo commodityInfo, MultipartFile scommodityImgfile) {
        String commodityId = commodityInfo.getId();
        String sbrandName = commodityInfo.getSbrandName();//品牌--名称
        String sbrandId = commodityInfo.getSbrandId();//品牌--id
        String label = commodityInfo.getSlabelName();//标签--id_名称
        String bigCategory = commodityInfo.getSbigCategoryName();//大类--id_名称
        String smallCategory = commodityInfo.getSsmallCategoryName();//小类--id_名称
        String addUserName = SessionUserUtils.getSessionAttributeForUserDtl().getRealName(); //操作员
        Integer istoreDevice = commodityInfo.getIstoreDevice(); // 商品类型 10=视觉 20=rfid设备 30=其他
        Date date = DateUtils.getCurrentDateTime();    // 当前时间
        BigDecimal commodityFloat = commodityInfo.getIcommodityFloat();     // 商品重量允许浮动值
        if (null == commodityFloat) {
            commodityInfo.setIcommodityFloat(BigDecimal.ZERO);   // 不填时，默认为0.00
        }

        if (istoreDevice != 10) {
            if (StringUtils.isBlank(sbrandId) || StringUtils.isBlank(sbrandName)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("品牌信息不能为空");
            }
            if (StringUtils.isNotBlank(label)) {
                commodityInfo.setSlabelId(label.split("_")[0]);//标签ID
                commodityInfo.setSlabelName(label.split("_")[1]);//标签名称
            }
            if (StringUtils.isNotBlank(bigCategory)) {
                commodityInfo.setSbigCategoryId(bigCategory.split("_")[0]);//大类ID
                commodityInfo.setSbigCategoryName(bigCategory.split("_")[1]);//大类名称
            }
            if (StringUtils.isNotBlank(smallCategory)) {
                commodityInfo.setSsmallCategoryId(smallCategory.split("_")[0]);//小类ID
                commodityInfo.setSsmallCategoryName(smallCategory.split("_")[1]);//小类名称
            }

            if (null != scommodityImgfile && scommodityImgfile.getSize() > 0) {//图片上传
                String url = uploadHome(scommodityImgfile, "commodityInfo");
                if (StringUtils.isNotBlank(url)) {
                    commodityInfo.setScommodityImg(url);//图片路径
                }
            }
        }

        commodityInfo.setTupdateTime(date);//修改时间
        commodityInfo.setSupdateUser(addUserName);//修改人
        CommodityInfo commodityInfoTemp = commodityInfoDao.selectByPrimaryKey(commodityId); // 修改前商品信息
        String merchantId = commodityInfoTemp.getSmerchantId();
        String merchantCode = commodityInfoTemp.getSmerchantCode();
        String commodityCode = commodityInfoTemp.getScode();
        BigDecimal costPriceTemp = commodityInfoTemp.getFcostPrice();    // 历史成本价
        BigDecimal salePriceTemp = commodityInfoTemp.getFsalePrice();   // 历史销售价
        BigDecimal costPrice = commodityInfo.getFcostPrice();    // 成本价
        BigDecimal salePrice = commodityInfo.getFsalePrice();    // 销售价
        if (costPrice != null && BigDecimalUtils.ltZero(costPrice)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("成本价不能为负数");
        }
        if (salePrice != null && BigDecimalUtils.ltZero(salePrice)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("销售价不能为负数");
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
        BigDecimal avgSalePrice = null;    // 历史销售平均价
        BigDecimal avgCostPrice = null; // 历史销售成本价
        if (!BigDecimalUtils.equals(costPriceTemp, costPrice)) {    // 成本价改变
            HistoryPrice historyPrice = new HistoryPrice();
            historyPrice.setSmerchantId(merchantId);
            historyPrice.setSmerchantCode(merchantCode);
            historyPrice.setScommodityId(commodityId);
            historyPrice.setScommodityCode(commodityCode);
            historyPrice.setFoldAmount(costPriceTemp);    // 历史价
            historyPrice.setFnewAmount(costPrice);    // 当前价
            historyPrice.setItype(10); // 10=成本价 20=销售价
            historyPrice.setTaddTime(date);
            historyPrice.setSaddUser(addUserName);
            historyPriceDao.insert(historyPrice);

            historyPrice = new HistoryPrice();
            historyPrice.setScommodityId(commodityId);
            historyPrice.setItype(10);// 10=成本价 20=销售价
            avgCostPrice = historyPriceDao.selectAvgPriceById(historyPrice);
        }
        if (!BigDecimalUtils.equals(salePriceTemp, salePrice)) {    // 销售价改变
            HistoryPrice historyPrice = new HistoryPrice();
            historyPrice.setSmerchantId(merchantId);
            historyPrice.setSmerchantCode(merchantCode);
            historyPrice.setScommodityId(commodityId);
            historyPrice.setScommodityCode(commodityCode);
            historyPrice.setFoldAmount(salePriceTemp);    // 历史价
            historyPrice.setFnewAmount(salePrice);    // 当前价
            historyPrice.setItype(20); // 10=成本价 20=销售价
            historyPrice.setTaddTime(date);
            historyPrice.setSaddUser(addUserName);
            historyPriceDao.insert(historyPrice);

            historyPrice = new HistoryPrice();
            historyPrice.setScommodityId(commodityId);
            historyPrice.setItype(20);// 10=成本价 20=销售价
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
     * 更新商品活动参与记录
     *
     * @param activityConf 活动信息
     */
    @Override
    public void updateCommodityJoinNum(final ActivityConf activityConf) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (activityConf.getIrangeType().intValue() == 10 || activityConf.getIrangeType().intValue() == 20) {
                        //全部  更新商户所有正常商品 +1
                        commodityInfoDao.updateJoinNumByMerchantId(activityConf.getSmerchantId());
                    } else {
                        //查询参与活动的商品信息 	//获取设备的应用范围
                        UseRange useRange = useRangeService.selectByActId(activityConf.getId());
                        if (null == useRange || StringUtil.isBlank(useRange.getScommodityId())) {
                            logger.error("活动异常：{}", activityConf);
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
                    logger.error("商品活动参与次数更新异常：{}", activityConf);
                }
            }
        });
    }

    /**
     * 同步商品单价
     *
     * @param checkboxId
     */
    @Override
    public void syncComFsalePrice(String[] checkboxId) {
        // 查询当前设备商品的单价
        CommodityInfo commodityInfo = new CommodityInfo();
        List<CommodityInfo> commodityInfoList = this.selectByEntityWhere(commodityInfo);
        DeviceStock deviceStock = null;
        // 循环商品列表
        if (commodityInfoList != null && commodityInfoList.size() > 0) {
            for (CommodityInfo com : commodityInfoList) {
                // 循环库存表ID
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
     * 获取商户所有有效商品
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
     * 处理上传图片
     *
     * @param file
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
     * 查询商户商品
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