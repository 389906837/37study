package com.cloud.cang.mgr.sm.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sm.dao.StockDetailDao;
import com.cloud.cang.mgr.sm.dao.StockOperRecordDao;
import com.cloud.cang.mgr.sm.domain.DeviceStockDomain;
import com.cloud.cang.mgr.sm.service.DeviceStockService;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
        DeviceStockService {

    private static final Logger log = LoggerFactory.getLogger(DeviceStockServiceImpl.class);

    @Autowired
    DeviceStockDao deviceStockDao;

    @Autowired
    StockDetailDao stockDetailDao;

    @Autowired
    StockOperRecordDao stockOperRecordDao;

    @Autowired
    CommodityInfoDao commodityInfoDao;


    @Override
    public GenericDao<DeviceStock, String> getDao() {
        return deviceStockDao;
    }

    /**
     * 查询总库存
     *
     * @param page
     * @param deviceStockVo
     * @return
     */
    @Override
    public Page<DeviceStockDomain> selectDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String spName = deviceStockVo.getSpName();
        if (StringUtils.isNotBlank(spName)) {
            char[] chars = spName.toCharArray();
            deviceStockVo.setSpName(StringUtils.join(chars, '%'));
        }
        return deviceStockDao.selectDeviceStock(deviceStockVo);
    }

    /**
     * 查询单机库存
     *
     * @param page
     * @param deviceStockVo
     * @return
     */
    @Override
    public Page<DeviceStockDomain> selectOneDeviceStock(Page<DeviceStockDomain> page, DeviceStockVo deviceStockVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String spName = deviceStockVo.getSpName();
        if (StringUtils.isNotBlank(spName)) {
            char[] chars = spName.toCharArray();
            deviceStockVo.setSpName(StringUtils.join(chars, '%'));
        }
        return deviceStockDao.selectOneDeviceStock(deviceStockVo);
    }

    /**
     * 修改库存单价和库存数量
     *
     * @param deviceStock
     */
    @Override
    public ResponseVo<DeviceStock> updatePriceAndStock(DeviceStock deviceStock) {
        String deviceStockId = deviceStock.getId();
        DeviceStock oldDeviceStock = deviceStockDao.selectByPrimaryKey(deviceStockId);
        if (null == oldDeviceStock) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("sm.stock.not.exist"));
        }
        Integer newStock = deviceStock.getIstock();
        BigDecimal newPrice = deviceStock.getFsalePrice();
        BigDecimal oldPrice = oldDeviceStock.getFsalePrice();
        Integer oldStock = oldDeviceStock.getIstock();

        List<StockDetail> stockDetailList = stockDetailDao.selectSingleStockDetail(deviceStockId);
        List<StockDetail> stockDetailList1 = new ArrayList<>();
        for (StockDetail s : stockDetailList) {
            if (null == s.getIsaleStatus()) {
                stockDetailList1.add(s);
            }
        }
        Integer size = stockDetailList1.size();

        if (newStock != oldStock || newPrice != oldPrice) {    // 库存变更
            DeviceStock deviceStockVo = new DeviceStock();
            deviceStockVo.setId(deviceStockId);
            deviceStockVo.setIstock(newStock);
            deviceStockVo.setFsalePrice(newPrice);
            deviceStockVo.setTlastUpdateTime(new Date());
            deviceStockDao.updateByIdSelective(deviceStockVo);
        }

        if (newStock > size) {    // 上架
            Integer addNum = newStock - size;
            while (addNum > 0) {
                CommodityInfo commodityInfo = commodityInfoDao.selectByPrimaryKey(oldDeviceStock.getScommodityId());
                Integer ilifeType = commodityInfo.getIlifeType();
                StockDetail stockDetaiVo = new StockDetail();
                stockDetaiVo.setSstockId(deviceStockId);
                stockDetaiVo.setFcostAmount(deviceStock.getFsalePrice());
                if (10 == ilifeType) {
                    stockDetaiVo.setSshelfLife(commodityInfo.getIshelfLife() + MessageSourceUtils.getMessageByKey("vrsku.day"));
                } else {
                    stockDetaiVo.setSshelfLife(commodityInfo.getIshelfLife() + MessageSourceUtils.getMessageByKey("vrsku.month"));
                }
                stockDetaiVo.setSstockCode(oldDeviceStock.getScode());
                stockDetaiVo.setScommodityId(oldDeviceStock.getScommodityId());
                stockDetaiVo.setScommodityCode(oldDeviceStock.getScommodityCode());
                stockDetaiVo.setSidentifies(IdGenerator.randomUUID());
                stockDetaiVo.setIstatus(10);
                stockDetaiVo.setTaddTime(new Date());
                stockDetailDao.insert(stockDetaiVo);

                StockOperRecord stockOperRecordVo = new StockOperRecord();
                stockOperRecordVo.setIstatus(10);
                stockOperRecordVo.setScommodityId(oldDeviceStock.getScommodityId());
                stockOperRecordVo.setScommodityCode(oldDeviceStock.getScommodityCode());
                stockOperRecordVo.setSdeviceId(oldDeviceStock.getSdeviceId());
                stockOperRecordVo.setSdeviceCode(oldDeviceStock.getSdeviceCode());
                stockOperRecordVo.setSmerchantId(oldDeviceStock.getSmerchantId());
                stockOperRecordVo.setSmerchantCode(oldDeviceStock.getSmerchantCode());
                stockOperRecordVo.setSidentifies(stockDetaiVo.getSidentifies());
                stockOperRecordVo.setTaddTime(new Date());
                stockOperRecordDao.insert(stockOperRecordVo);
                addNum--;
            }
        } else {    // 下架
            Integer subNum = size - newStock;
            List<StockDetail> subShelf = new ArrayList<>();
            subShelf.addAll(stockDetailList1);
            while (subNum > 0) {
                stockDetailList1.remove(0);
                subNum--;
            }
            subShelf.removeAll(stockDetailList1);
            for (StockDetail s : subShelf) {
                StockDetail stockDetaiVo = new StockDetail();
                stockDetaiVo.setId(s.getId());
                stockDetaiVo.setIsaleStatus(90);
                stockDetaiVo.setIstatus(null);
                stockDetailDao.updateById(stockDetaiVo);

                StockOperRecord stockOperRecordVo = new StockOperRecord();
                stockOperRecordVo.setIstatus(20);
                stockOperRecordVo.setScommodityId(oldDeviceStock.getScommodityId());
                stockOperRecordVo.setScommodityCode(oldDeviceStock.getScommodityCode());
                stockOperRecordVo.setSdeviceId(oldDeviceStock.getSdeviceId());
                stockOperRecordVo.setSdeviceCode(oldDeviceStock.getSdeviceCode());
                stockOperRecordVo.setSmerchantId(oldDeviceStock.getSmerchantId());
                stockOperRecordVo.setSmerchantCode(oldDeviceStock.getSmerchantCode());
                stockOperRecordVo.setSidentifies(s.getSidentifies());
                stockOperRecordVo.setTaddTime(new Date());
                stockOperRecordDao.insert(stockOperRecordVo);
            }
        }
        return ResponseVo.getSuccessResponse(deviceStock);
    }

    /**
     * 商品库存手动下架
     *
     * @param deviceStock
     * @param unType
     * @param unNum
     * @return
     */
    @Override
    @Transactional
    public ResponseVo saveUndercarriage(DeviceStock deviceStock, Integer unType, Integer unNum) {
        deviceStock = deviceStockDao.selectByPrimaryKey(deviceStock.getId());
        if (unType == 10) {
            unNum = deviceStock.getIstock();
        }
        if (unNum > deviceStock.getIstock()) {
            unNum = deviceStock.getIstock();
        }

        Map map = new HashMap();
        map.put("stockId", deviceStock.getId());
        map.put("commodityId", deviceStock.getScommodityId());
        List<StockDetail> stockDetailList = stockDetailDao.selectDetailByMap(map);
        DeviceStock temp = new DeviceStock();
        //手动下架
        if (null != stockDetailList && !stockDetailList.isEmpty()) {
            StockDetail stockDetailTemp = new StockDetail();
            for (int i = 0; i < unNum; i++) {
                stockDetailTemp.setId(stockDetailList.get(i).getId());
                // stockDetailTemp.setIstatus(60);
                stockDetailTemp.setIsaleStatus(90);
                stockDetailDao.updateById(stockDetailTemp);
                //添加下架记录
                StockOperRecord stockOperRecord = new StockOperRecord();
                stockOperRecord.setSmerchantId(deviceStock.getSmerchantId());
                stockOperRecord.setSmerchantCode(deviceStock.getSmerchantCode());
                stockOperRecord.setSdeviceId(deviceStock.getSdeviceId());
                stockOperRecord.setSdeviceCode(deviceStock.getSdeviceCode());
                stockOperRecord.setScommodityId(deviceStock.getScommodityId());
                stockOperRecord.setScommodityCode(deviceStock.getScommodityCode());
                stockOperRecord.setIstatus(70);
                stockOperRecord.setSidentifies(stockDetailList.get(i).getSidentifies());
                stockOperRecord.setSremark(MessageSourceUtils.getMessageByKey("smcon.obtained.stock.remark",null));
                stockOperRecord.setTaddTime(DateUtils.getCurrentDateTime());
                stockOperRecordDao.insert(stockOperRecord);
            }
            BigDecimal totalWeight = deviceStock.getFcommodityTotalWeight();
            if (totalWeight == null) {
                temp.setFcommodityTotalWeight(BigDecimal.ZERO);
            } else {
                temp.setFcommodityTotalWeight(deviceStock.getFcommodityTotalWeight().subtract(stockDetailList.get(0).getFcommodityWeight()).multiply(new BigDecimal(unNum)));
            }
        }
        temp.setId(deviceStock.getId());
        temp.setIstock(deviceStock.getIstock() - unNum);
        deviceStockDao.updateByIdSelective(temp);
        return ResponseVo.getSuccessResponse();
    }
}