package com.cloud.cang.rmp.sm.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockBaseDto;
import com.cloud.cang.rm.StockOperDto;
import com.cloud.cang.rmp.om.service.OrderAuditCommodityService;
import com.cloud.cang.rmp.om.service.OrderCommodityService;
import com.cloud.cang.rmp.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.rmp.rm.service.ReplenishmentRecordService;
import com.cloud.cang.rmp.sb.service.DeviceCommodityService;
import com.cloud.cang.rmp.sb.service.DeviceManageService;
import com.cloud.cang.rmp.sm.service.StockDetailService;
import com.cloud.cang.rmp.sm.service.StockOperRecordService;
import com.cloud.cang.rmp.sp.service.CommodityBatchService;
import com.cloud.cang.rmp.sys.service.OperatorService;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rmp.sm.dao.DeviceStockDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.rmp.sm.service.DeviceStockService;

@Service
public class DeviceStockServiceImpl extends GenericServiceImpl<DeviceStock, String> implements
        DeviceStockService {

    @Autowired
    DeviceStockDao deviceStockDao;
    @Autowired
    private ReplenishmentRecordService replenishmentRecordService;
    @Autowired
    private ReplenishmentCommodityService replenishmentCommodityService;
    @Autowired
    private StockDetailService stockDetailService;
    @Autowired
    private CommodityBatchService commodityBatchService;
    @Autowired
    private StockOperRecordService stockOperRecordService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private OrderAuditCommodityService orderAuditCommodityService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private DeviceCommodityService deviceCommodityService;

    @Override
    public GenericDao<DeviceStock, String> getDao() {
        return deviceStockDao;
    }

    private static final Logger logger = LoggerFactory.getLogger(DeviceStockServiceImpl.class);

    @Override
    public int selectByMap(String sdeviceId, String commodityId) {
        return deviceStockDao.selectByMap(sdeviceId, commodityId);
    }

    /**
     * ???????????????
     *
     * @param replenishmentDto ???????????????
     * @return
     */
    @Override
    public ResponseVo<ReplenishmentResult> generateReplenishmentRecord(ReplenishmentDto replenishmentDto) throws Exception {
        ResponseVo<ReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
        //????????????
        ReplenishmentRecord record = new ReplenishmentRecord();
        BeanUtils.copyProperties(record, replenishmentDto);//????????????
        record.setScode(CoreUtils.newCode("rm_replenishment_record"));//??????
        if (replenishmentDto.getItype().intValue() == 10) {
            record.setIstatus(30);//?????????
        } else {
            record.setIstatus(10);//?????????
        }

        //?????????????????????
        Operator operator = operatorService.selectByMoblie(replenishmentDto.getSmemberName(), record.getSmerchantId());
        record.setSrenewalMobile(replenishmentDto.getSmemberName());
        if (null != operator) {
            record.setSrenewalName(operator.getSrealName());
        }

        record.setTreplenishmentTime(new Date());
        record.setIdelFlag(0);
        record.setTaddTime(new Date());
        record.setTupdateTime(new Date());
        record.setIversion(1);
        replenishmentRecordService.insert(record);

        //?????????????????????
        ReplenishmentCommodity commodity = null;
        boolean flag = false;
        List<ReplenishmentCommodity> commodities = new ArrayList<ReplenishmentCommodity>();
        //????????????
        Integer addNum = 0;
        BigDecimal addAmount = new BigDecimal("0");
        DeviceCommodity tempCommodity = null;
        List<DeviceCommodity> tempList = null;
        DeviceCommodity paramCommodity = null;
        if (null != replenishmentDto.getAddVoList() && replenishmentDto.getAddVoList().size() > 0) {
            for (CommodityDiffVo diffVo : replenishmentDto.getAddVoList()) {
                commodity = new ReplenishmentCommodity();
                commodity.setSreplenishmentCode(record.getScode());
                commodity.setSreplenishmentId(record.getId());
                commodity.setScommodityId(diffVo.getScommodityId());
                commodity.setScommodityCode(diffVo.getScommodityCode());
                commodity.setScommodityName(diffVo.getScommodityName());
                commodity.setFcommodityPrice(diffVo.getFcommodityPrice());
                commodity.setForderCount(diffVo.getNumber());
                commodity.setFcommodityAmount(commodity.getFcommodityPrice().multiply(new BigDecimal(commodity.getForderCount())));
                commodity.setItype(10);
                commodity.setIcommodityStatus(diffVo.getIstatus());
                commodity.setTaddTime(new Date());
                commodity.setTupdateTime(new Date());
                addNum = addNum + diffVo.getNumber();//?????????????????????
                addAmount = addAmount.add(commodity.getFcommodityAmount());//?????????????????????
                //????????????????????????
                if (replenishmentDto.getIrepWay() == null || replenishmentDto.getIrepWay().intValue() != 20) {
                    Map<String, Object> map = this.changeCommoditySotck(10, diffVo, replenishmentDto, "");//????????????????????????
                    int currStock = 0;
                    if (null != map) {
                        Boolean flagTemp = (Boolean) map.get("flag");
                        if (null != flagTemp) {
                            flag = flagTemp;
                        }
                        Integer currStockTemp = (Integer) map.get("currStock");
                        if (null != currStockTemp) {
                            currStock = currStockTemp;
                        }
                    }
                    commodity.setIcurrStock(currStock);
                } else {
                    commodity.setIcurrStock(diffVo.getCurrStock());
                }
                replenishmentCommodityService.insert(commodity);
                if (flag) {
                    commodities.add(commodity);
                    //??????????????????????????????
                    paramCommodity = new DeviceCommodity();
                    paramCommodity.setSdeviceId(replenishmentDto.getSdeviceId());
                    paramCommodity.setScommodityId(diffVo.getScommodityId());
                    tempList = deviceCommodityService.selectByEntityWhere(paramCommodity);
                    if (null != tempList && tempList.size() > 0) {
                        tempCommodity = tempList.get(0);
                    } else {
                        tempCommodity = null;
                    }
                    if (null != tempCommodity) {//????????????
                        if (tempCommodity.getIstatus().intValue() == 20) {//???????????? ????????????
                            tempCommodity.setIstatus(10);
                            tempCommodity.setTupdateTime(new Date());
                            tempCommodity.setSupdateUser(record.getSrenewalName());
                            deviceCommodityService.updateBySelective(tempCommodity);
                        }
                    } else {//????????????
                        tempCommodity = new DeviceCommodity();
                        tempCommodity.setSupdateUser(record.getSrenewalName());
                        tempCommodity.setTupdateTime(new Date());
                        tempCommodity.setIstatus(10);
                        tempCommodity.setScommodityId(diffVo.getScommodityId());
                        tempCommodity.setSdeviceId(replenishmentDto.getSdeviceId());
                        tempCommodity.setSdeviceCode(replenishmentDto.getSdeviceCode());
                        tempCommodity.setScommodityCode(diffVo.getScommodityCode());
                        tempCommodity.setTaddTime(new Date());
                        tempCommodity.setSaddUser(record.getSrenewalName());
                        deviceCommodityService.insert(tempCommodity);
                    }
                }
            }
        }
        //????????????
        Integer subNum = 0;
        BigDecimal subAmount = new BigDecimal("0");
        if (null != replenishmentDto.getSubVoList() && replenishmentDto.getSubVoList().size() > 0) {
            for (CommodityDiffVo diffVo : replenishmentDto.getSubVoList()) {
                commodity = new ReplenishmentCommodity();
                commodity.setSreplenishmentCode(record.getScode());
                commodity.setSreplenishmentId(record.getId());
                commodity.setScommodityId(diffVo.getScommodityId());
                commodity.setScommodityCode(diffVo.getScommodityCode());
                commodity.setScommodityName(diffVo.getScommodityName());
                commodity.setFcommodityPrice(diffVo.getFcommodityPrice());
                commodity.setForderCount(diffVo.getNumber());
                commodity.setFcommodityAmount(commodity.getFcommodityPrice().multiply(new BigDecimal(commodity.getForderCount())));
                commodity.setItype(20);
                commodity.setIcommodityStatus(10);
                commodity.setTaddTime(new Date());
                commodity.setTupdateTime(new Date());
                subNum = subNum + diffVo.getNumber();//?????????????????????
                subAmount = subAmount.add(commodity.getFcommodityAmount());//?????????????????????
                //????????????????????????
                if (replenishmentDto.getIrepWay() == null || replenishmentDto.getIrepWay().intValue() != 20) {
                    Map<String, Object> map = this.changeCommoditySotck(10, diffVo, replenishmentDto, "");
                    int currStock = 0;
                    if (null != map) {
                        Boolean flagTemp = (Boolean) map.get("flag");
                        if (null != flagTemp) {
                            flag = flagTemp;
                        }
                        Integer currStockTemp = (Integer) map.get("currStock");
                        if (null != currStockTemp) {
                            currStock = currStockTemp;
                        }
                    }
                    commodity.setIcurrStock(currStock);
                } else {
                    commodity.setIcurrStock(diffVo.getCurrStock());
                }

                replenishmentCommodityService.insert(commodity);
                if (flag) {
                    commodities.add(commodity);
                }
            }
        }

        if (replenishmentDto.getIrepWay() != null && replenishmentDto.getIrepWay().intValue() == 20) {
            //????????????
            //????????????
            if (null != replenishmentDto.getAddVoStockList() && replenishmentDto.getAddVoStockList().size() > 0) {
                for (CommodityDiffVo diffVo : replenishmentDto.getAddVoStockList()) {
                    this.changeCommoditySotck(10, diffVo, replenishmentDto, "");
                }
            }
            //????????????
            if (null != replenishmentDto.getSubVoStockList() && replenishmentDto.getSubVoStockList().size() > 0) {
                for (CommodityDiffVo diffVo : replenishmentDto.getSubVoStockList()) {
                    this.changeCommoditySotck(10, diffVo, replenishmentDto, "");
                }
            }
        }

        //???????????????????????????
        ReplenishmentRecord updateRecord = new ReplenishmentRecord();
        updateRecord.setId(record.getId());
        updateRecord.setIactualShelves(addNum);
        updateRecord.setIactualUnder(subNum);
        updateRecord.setIactualShelvesAmount(addAmount);
        updateRecord.setIactualUnderAmount(subAmount);
        replenishmentRecordService.updateBySelective(updateRecord);

        //????????????
        ReplenishmentResult result = new ReplenishmentResult();
        result.setReplenishmentRecord(record);
        result.setCommodities(commodities);
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * ????????????????????????
     *
     * @param diffVo  ??????????????????
     * @param baseDto ?????? ????????????
     * @param itype   ???????????? 10 ??????????????? 20 ???????????? 30 ????????????
     * @param orderId ??????ID
     * @return
     */
    @Override
    public Map<String, Object> changeCommoditySotck(Integer itype, CommodityDiffVo diffVo, StockBaseDto baseDto, String orderId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean flagTemp = false;
        map.put("flag", flagTemp);
        //????????????????????????
        DeviceStock deviceStock = this.selectDeviceStockByCommodityId(baseDto.getSdeviceId(), diffVo.getScommodityId());
        Integer currStock = 0;
        if (null != deviceStock) {//????????????
            deviceStock = this.selectByPrimaryKeyForUpdate(deviceStock.getId());
            currStock = deviceStock.getIstock();
        }
        String[] arr = null;
        if (StringUtil.isNotBlank(diffVo.getRfidList())) {
            arr = diffVo.getRfidList().split(",");
        }
        StockDetail stockDetail = null;
        StockOperRecord operRecord = null;
        if (diffVo.getItype() == 10) {//????????????
            boolean isUpdateStock = true;
            boolean isQuery = false;
            if (null == deviceStock) {//????????????????????????
                isUpdateStock = false;
                deviceStock = new DeviceStock();
                deviceStock.setFsalePrice(diffVo.getFcommodityPrice());
                deviceStock.setIstock(diffVo.getNumber());
                deviceStock.setScode(CoreUtils.newCode("sm_device_stock"));
                deviceStock.setScommodityCode(diffVo.getScommodityCode());
                deviceStock.setScommodityId(diffVo.getScommodityId());
                deviceStock.setSdeviceId(baseDto.getSdeviceId());
                deviceStock.setSdeviceCode(baseDto.getSdeviceCode());
                deviceStock.setSmerchantCode(baseDto.getSmerchantCode());
                deviceStock.setSmerchantId(baseDto.getSmerchantId());
                deviceStock.setTaddTime(new Date());
                deviceStock.setTlastUpdateTime(new Date());
                deviceStock.setFcommodityTotalWeight(diffVo.getIweigth().multiply(new BigDecimal(diffVo.getNumber())));
                this.insert(deviceStock);//????????????????????????
            }
            StockDetail detailTemp = null;
            CommodityBatch updateBatch = null;
            CommodityBatch commodityBatch = null;
            //?????????????????????????????????
            if (itype == 10) {//??????????????????????????????????????? ???????????????????????????
                //????????????????????????
                commodityBatch = this.getCommodityBatchData(diffVo.getScommodityId());
                if (null != commodityBatch) {
                    updateBatch = new CommodityBatch();
                    updateBatch.setId(commodityBatch.getId());
                    updateBatch.setIshelfNum(commodityBatch.getIshelfNum());
                    updateBatch.setIlossGoodsNum(commodityBatch.getIlossGoodsNum());
                    updateBatch.setIstockStatus(commodityBatch.getIstockStatus());
                    updateBatch.setIsaleStatus(commodityBatch.getIsaleStatus());
                }
            }
            //?????????????????? ??????????????????
            for (int i = 0; i < diffVo.getNumber(); i++) {//????????????????????????
                stockDetail = new StockDetail();
                //??????????????????
                stockDetail.setSstockId(deviceStock.getId());
                stockDetail.setSstockCode(deviceStock.getScode());
                //????????????
                stockDetail.setScommodityId(deviceStock.getScommodityId());
                stockDetail.setScommodityCode(deviceStock.getScommodityCode());
                stockDetail.setFcostAmount(diffVo.getFcostPrice());
                stockDetail.setSshelfLife(diffVo.getSshelfLife());

                if (null != arr) {//rfid??????
                    if (arr.length >= (i + 1)) {
                        //??????????????????????????????  ????????????????????????
                        detailTemp = stockDetailService.selectBySid(arr[i]);//rfid????????????????????????
                        if (null != detailTemp) {//???????????????????????? ??????
                            BeanUtils.copyProperties(stockDetail, detailTemp);//????????????
                            if (stockDetail.getIstatus().intValue() == 30) {//???????????????????????????
                                stockDetail.setIstatus(40);
                            } else {
                                stockDetail.setIstatus(20);
                            }
                            stockDetail.setId(null);
                            isQuery = true;
                        } else {
                            stockDetail.setSidentifies(arr[i]);
                            if (diffVo.getIstatus().intValue() == 20) {
                                stockDetail.setIstatus(60);//????????????
                                isQuery = true;
                            } else {
                                stockDetail.setIstatus(10);//???????????? ??????
                            }
                        }
                    } else {
                        //????????????
                        logger.error("??????????????????????????????{}", diffVo);
                        stockDetail.setIstatus(50);//????????????
                        isQuery = true;
                    }
                } else {
                    //??????????????????????????????
                    stockDetail.setSidentifies(IdGenerator.randomUUID());
                    if (diffVo.getIstatus().intValue() == 20) {
                        stockDetail.setIstatus(60);//????????????
                        isQuery = true;
                    } else if (itype.intValue() == 20) {
                        stockDetail.setIstatus(20);//???????????? ?????????
                        isQuery = true;
                    } else if (itype.intValue() == 30) {
                        stockDetail.setIstatus(50);//???????????? ????????????
                        isQuery = true;
                    } else {
                        stockDetail.setIstatus(10);//???????????? ??????
                    }
                }
                stockDetail.setIsaleStatus(null);//??????????????????
                stockDetail.setTaddTime(new Date());//????????????
                stockDetail.setFcommodityWeight(diffVo.getIweigth());
                if (itype == 10) {//?????????????????????????????????
                    if (null != commodityBatch && (updateBatch.getIshelfNum() + updateBatch.getIlossGoodsNum() + 1) > commodityBatch.getIcommodityNum()) {//????????? ???????????????????????????
                        //?????????????????????????????????
                        //updateBatch.setIsaleStatus(10);//??????????????? ?????????
                        //updateBatch.setIstockStatus(20);//???????????? ??????????????????
                        commodityBatchService.updateBySelective(updateBatch);
                        commodityBatch = this.getCommodityBatchData(diffVo.getScommodityId());
                        if (null != commodityBatch) {//?????????????????????????????????
                            updateBatch.setId(commodityBatch.getId());
                            updateBatch.setIshelfNum(commodityBatch.getIshelfNum());
                            updateBatch.setIlossGoodsNum(commodityBatch.getIlossGoodsNum());
                            updateBatch.setIstockStatus(commodityBatch.getIstockStatus());
                            updateBatch.setIsaleStatus(commodityBatch.getIsaleStatus());
                        } else {
                            updateBatch = null;
                        }
                    }
                    //??????????????????
                    if (null != commodityBatch) {
                        //????????????????????????
                        if (updateBatch.getIsaleStatus().intValue() == 30) {//?????????
                            updateBatch.setIsaleStatus(10);//??????????????? ?????????
                        }
                        //???????????????????????????
                        updateBatch.setIshelfNum(updateBatch.getIshelfNum() + 1);
                        //????????????????????????
                        if (updateBatch.getIshelfNum() + updateBatch.getIlossGoodsNum() >= commodityBatch.getIcommodityNum()) {
                            updateBatch.setIstockStatus(30);//???????????? ??????????????????
                        } else if (updateBatch.getIstockStatus().intValue() == 10) {
                            updateBatch.setIstockStatus(20);//???????????? ??????????????????
                        }
                        //??????????????????????????????????????????
                        stockDetail.setDexpiredDate(commodityBatch.getDexpiredDate());
                        if (stockDetail.getDexpiredDate().before(new Date())) {
                            //???????????? ??????????????????
                            if (stockDetail.getIstatus() == 10) {
                                stockDetail.setIstatus(30);
                            } else if (stockDetail.getIstatus() == 20) {
                                stockDetail.setIstatus(40);
                            }
                        }
                        stockDetail.setDproduceDate(commodityBatch.getDproduceDate());
                        stockDetail.setSbatchNo(commodityBatch.getSbatchNo());
                        stockDetail.setFcostAmount(commodityBatch.getFcostAmount());
                        stockDetail.setFtaxPoint(commodityBatch.getFtaxPoint());
                    }
                }
                stockDetailService.insert(stockDetail);//????????????

                //????????????????????????
                operRecord = new StockOperRecord();
                if (stockDetail.getIstatus().intValue() == 10) {
                    operRecord.setIstatus(10);//????????????
                } else {
                    operRecord.setIstatus(40);//????????????
                }
                operRecord.setSmerchantCode(baseDto.getSmerchantCode());
                operRecord.setSmerchantId(baseDto.getSmerchantId());
                operRecord.setScommodityCode(diffVo.getScommodityCode());
                operRecord.setScommodityId(diffVo.getScommodityId());
                operRecord.setSdeviceId(baseDto.getSdeviceId());
                operRecord.setSdeviceCode(baseDto.getSdeviceCode());
                operRecord.setSidentifies(stockDetail.getSidentifies());
                operRecord.setTaddTime(new Date());
                stockOperRecordService.insert(operRecord);
            }

            //??????????????????
            if (null != updateBatch) {
                commodityBatchService.updateBySelective(updateBatch);
            }
            String abnormalStr = "";
            if (isQuery) {//????????????????????????????????????  ?????????????????????
                abnormalStr = stockDetailService.getAbnormalMap(deviceStock.getId(), diffVo.getSpackageUnit());
            }
            //??????????????????????????????
            if (isUpdateStock || abnormalStr.length() > 0) {
                DeviceStock ds = new DeviceStock();
                ds.setId(deviceStock.getId());
                if (abnormalStr.length() > 0) {//??????????????????
                    ds.setSremark(abnormalStr.substring(0, abnormalStr.length() - 1));
                } else {
                    ds.setSremark(deviceStock.getSremark());
                }
                if (isUpdateStock) {
                    ds.setIstock(deviceStock.getIstock() + diffVo.getNumber());
                    ds.setFsalePrice(diffVo.getFcommodityPrice());
                }
                //???????????????????????????
                BigDecimal totalWeight = deviceStock.getFcommodityTotalWeight();
                if (null == totalWeight) {
                    totalWeight = BigDecimal.ZERO;
                }
                BigDecimal temp = totalWeight.add(diffVo.getIweigth().multiply(new BigDecimal(diffVo.getNumber())));
                ds.setFcommodityTotalWeight(temp);
                ds.setTlastUpdateTime(new Date());//??????????????????
                currStock = ds.getIstock();
                this.updateBySelective(ds);
            }


        } else if (diffVo.getItype() == 20) {//????????????
            if (null == deviceStock) {
                logger.error("??????????????????????????????????????????????????????-???????????????{}", diffVo.getScommodityCode() + "-" + baseDto.getSdeviceCode());
                return map;
            }
            List<StockDetail> stockDetails = null;
            if (StringUtil.isBlank(diffVo.getRfidList())) {//???????????? ????????????????????????
                //????????????????????????
                stockDetails = stockDetailService.selectByMapForUpdate(deviceStock.getId());
                if (null == stockDetails || stockDetails.size() <= 0/* || stockDetails.size() < diffVo.getNumber()*/) {
                    logger.error("??????????????????????????????????????????????????????????????????-???????????????{}", diffVo.getScommodityCode() + "-" + baseDto.getSdeviceCode());
                    return map;
                }
            }
            //10 ??????????????? ?????? 20 ???????????? ?????? 30 ???????????? ????????????
            //?????????????????? ????????????????????????
            StockDetail updateDetail = null;
            //StringBuffer sids = new StringBuffer();
            for (int i = 0; i < diffVo.getNumber(); i++) {
                if (null != arr && arr.length >= (i + 1)) {//?????????rfid??????
                    stockDetail = stockDetailService.selectBySid(arr[i]);//??????rfid??????????????????
                } else {
                    stockDetail = stockDetails.get(i);
                }
                if (null == stockDetail || stockDetail.getIsaleStatus() != null) {
                    logger.error("??????????????????????????????????????????RFID-???????????????{}", arr[i] + "-" + baseDto.getSdeviceCode());
                    continue;
                }
                updateDetail = new StockDetail();
                updateDetail.setId(stockDetail.getId());
                //????????????????????????
                operRecord = new StockOperRecord();
                //??????????????????
                if (itype == 10 && stockDetail.getIstatus().intValue() != 50) {//?????????
                    updateDetail.setIsaleStatus(70);
                    operRecord.setIstatus(20);//????????????
                } else if (itype == 20) {//??????
                    updateDetail.setIsaleStatus(stockDetail.getIstatus());
                    if (stockDetail.getIstatus().intValue() == 10) {
                        operRecord.setIstatus(30);//????????????
                    } else {
                        operRecord.setIstatus(60);//????????????
                    }
                    //sids.append(stockDetail.getSidentifies()+",");
                } else if (itype == 30) {//??????
                    updateDetail.setIsaleStatus(80);//????????????
                    operRecord.setIstatus(50);//????????????
                    //sids.append(stockDetail.getSidentifies()+",");
                } else {
                    updateDetail.setIsaleStatus(80);//????????????
                    operRecord.setIstatus(50);//????????????
                }
                stockDetailService.updateBySelective(updateDetail);//??????????????????

                operRecord.setSmerchantCode(baseDto.getSmerchantCode());
                operRecord.setSmerchantId(baseDto.getSmerchantId());
                operRecord.setScommodityCode(diffVo.getScommodityCode());
                operRecord.setScommodityId(diffVo.getScommodityId());
                operRecord.setSdeviceId(baseDto.getSdeviceId());
                operRecord.setSdeviceCode(baseDto.getSdeviceCode());
                operRecord.setSidentifies(stockDetail.getSidentifies());
                operRecord.setTaddTime(new Date());
                stockOperRecordService.insert(operRecord);


                //?????????????????? ????????????????????? ??????????????????
                if (StringUtil.isNotBlank(stockDetail.getSbatchNo()) && itype != 20) {
                    commodityBatchService.updateBySbatchNo(stockDetail.getSbatchNo(), stockDetail.getScommodityId());
                }

                //????????????????????????????????????
                if (itype == 20 && StringUtil.isNotBlank(orderId) && StringUtil.isNotBlank(stockDetail.getSidentifies())) {//????????????
                    String arrs[] = orderId.split(",");
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("sidentifies", stockDetail.getSidentifies());
                    updateMap.put("commodityId", diffVo.getScommodityId());
                    if (null != stockDetail.getFcostAmount()) {//???????????????
                        updateMap.put("fcostAmount", stockDetail.getFcostAmount());
                    }
                    if (null != stockDetail.getFtaxPoint()) {//????????????
                        updateMap.put("ftaxPoint", stockDetail.getFtaxPoint());
                    }
                    for (String id : arrs) {//????????????????????????
                        updateMap.put("orderId", id);
                        orderCommodityService.updateByOrderId(updateMap);
                    }
                } else if (itype == 30 && StringUtil.isNotBlank(orderId) && StringUtil.isNotBlank(stockDetail.getSidentifies())) {//????????????  ????????????
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("sidentifies", stockDetail.getSidentifies());
                    updateMap.put("commodityId", diffVo.getScommodityId());
                    updateMap.put("orderId", orderId);
                    orderAuditCommodityService.updateByOrderId(updateMap);
                }
            }

            //????????????????????????
            String abnormalStr = stockDetailService.getAbnormalMap(deviceStock.getId(), diffVo.getSpackageUnit());

            //??????????????????????????????
            DeviceStock ds = new DeviceStock();
            ds.setId(deviceStock.getId());
            if (abnormalStr.length() > 0) {//??????????????????
                ds.setSremark(abnormalStr.substring(0, abnormalStr.length() - 1));
            } else {
                ds.setSremark(deviceStock.getSremark());
            }
            ds.setIstock(deviceStock.getIstock() - diffVo.getNumber());
            /*if (ds.getIstock() < 0) {
                ds.setIstock(0);
			}*/
            //?????????????????????
            BigDecimal temp = deviceStock.getFcommodityTotalWeight().subtract(diffVo.getIweigth().multiply(new BigDecimal(diffVo.getNumber())));
            ds.setFcommodityTotalWeight(temp);
            ds.setTlastUpdateTime(new Date());//??????????????????
            currStock = ds.getIstock();
            this.updateBySelective(ds);
        }
        flagTemp = true;
        map.put("flag", flagTemp);
        map.put("currStock", currStock);
        return map;
    }

    /***
     * ????????????ID
     * @param commodityId
     * @return
     */
    private CommodityBatch getCommodityBatchData(String commodityId) {
        //????????????????????????
        CommodityBatch commodityBatch = commodityBatchService.selectByCommodityId(commodityId);
        if (null != commodityBatch) {
            commodityBatch = commodityBatchService.selectByPrimaryKeyForUpdate(commodityBatch.getId());//????????????
            if (null == commodityBatch.getIcommodityNum()) {
                commodityBatch.setIcommodityNum(0);
            }
            if (null == commodityBatch.getIshelfNum()) {
                commodityBatch.setIshelfNum(0);
            }
            if (null == commodityBatch.getIlossGoodsNum()) {
                commodityBatch.setIlossGoodsNum(0);
            }
            if (commodityBatch.getIstockStatus() == 30 || (commodityBatch.getIshelfNum() + commodityBatch.getIlossGoodsNum()) >= commodityBatch.getIcommodityNum()) {//??????????????? ???????????????????????????
                if (commodityBatch.getIstockStatus() != 30) {
                    //???????????? ???????????????
                    CommodityBatch updateCommodityBatch = new CommodityBatch();
                    updateCommodityBatch.setIstockStatus(30);
                    updateCommodityBatch.setId(commodityBatch.getId());
                    commodityBatchService.updateBySelective(updateCommodityBatch);
                }
                return getCommodityBatchData(commodityId);
            }
            return commodityBatch;
        }
        return null;
    }

    /***
     * ??????????????????
     * @param deviceId ??????ID
     * @param commodityId ??????ID
     */
    @Override
    public DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId) {
        return deviceStockDao.selectDeviceStockByCommodityId(deviceId, commodityId);
    }

    /**
     * ???????????? ??????????????????????????????
     *
     * @param id ??????????????????ID
     * @return
     */
    @Override
    public DeviceStock selectByPrimaryKeyForUpdate(String id) {
        return deviceStockDao.selectByPrimaryKeyForUpdate(id);
    }

    /**
     * ??????????????????
     *
     * @param stockOperDto ????????????
     * @return
     */
    @Override
    public ResponseVo<String> stockOper(StockOperDto stockOperDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //????????????
        String orderId = "";
        if (null != stockOperDto.getAddVoList() && stockOperDto.getAddVoList().size() > 0) {
            for (CommodityDiffVo diffVo : stockOperDto.getAddVoList()) {
                //????????????
                if (stockOperDto.getItype().intValue() == 20) {
                    orderId = stockOperDto.getOrderIds();
                } else if (stockOperDto.getItype().intValue() == 30) {
                    orderId = stockOperDto.getAuditOrderId();
                } else {
                    orderId = "";
                }
                this.changeCommoditySotck(stockOperDto.getItype(), diffVo, stockOperDto, orderId);
            }
        }
        //????????????
        if (null != stockOperDto.getSubVoList() && stockOperDto.getSubVoList().size() > 0) {
            for (CommodityDiffVo diffVo : stockOperDto.getSubVoList()) {
                //????????????
                if (stockOperDto.getItype().intValue() == 20) {
                    orderId = stockOperDto.getOrderIds();
                } else if (stockOperDto.getItype().intValue() == 30) {
                    orderId = stockOperDto.getAuditOrderId();
                } else {
                    orderId = "";
                }
                this.changeCommoditySotck(stockOperDto.getItype(), diffVo, stockOperDto, orderId);
            }
        }
        return responseVo;
    }


}