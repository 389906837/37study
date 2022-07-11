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
     * 生成补货单
     *
     * @param replenishmentDto 补货单参数
     * @return
     */
    @Override
    public ResponseVo<ReplenishmentResult> generateReplenishmentRecord(ReplenishmentDto replenishmentDto) throws Exception {
        ResponseVo<ReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
        //组装参数
        ReplenishmentRecord record = new ReplenishmentRecord();
        BeanUtils.copyProperties(record, replenishmentDto);//复制对象
        record.setScode(CoreUtils.newCode("rm_replenishment_record"));//编号
        if (replenishmentDto.getItype().intValue() == 10) {
            record.setIstatus(30);//已完成
        } else {
            record.setIstatus(10);//待配货
        }

        //获取补货员信息
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

        //新增补货单明细
        ReplenishmentCommodity commodity = null;
        boolean flag = false;
        List<ReplenishmentCommodity> commodities = new ArrayList<ReplenishmentCommodity>();
        //上架商品
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
                addNum = addNum + diffVo.getNumber();//上架商品总数量
                addAmount = addAmount.add(commodity.getFcommodityAmount());//上架商品总金额
                //上架商品变更库存
                if (replenishmentDto.getIrepWay() == null || replenishmentDto.getIrepWay().intValue() != 20) {
                    Map<String, Object> map = this.changeCommoditySotck(10, diffVo, replenishmentDto, "");//上架商品变更库存
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
                    //新增设备历史商品库存
                    paramCommodity = new DeviceCommodity();
                    paramCommodity.setSdeviceId(replenishmentDto.getSdeviceId());
                    paramCommodity.setScommodityId(diffVo.getScommodityId());
                    tempList = deviceCommodityService.selectByEntityWhere(paramCommodity);
                    if (null != tempList && tempList.size() > 0) {
                        tempCommodity = tempList.get(0);
                    } else {
                        tempCommodity = null;
                    }
                    if (null != tempCommodity) {//更新状态
                        if (tempCommodity.getIstatus().intValue() == 20) {//商品下架 重新在售
                            tempCommodity.setIstatus(10);
                            tempCommodity.setTupdateTime(new Date());
                            tempCommodity.setSupdateUser(record.getSrenewalName());
                            deviceCommodityService.updateBySelective(tempCommodity);
                        }
                    } else {//新增数据
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
        //下架商品
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
                subNum = subNum + diffVo.getNumber();//下架商品总数量
                subAmount = subAmount.add(commodity.getFcommodityAmount());//下架商品总金额
                //下架商品变更库存
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
            //修改库存
            //上架商品
            if (null != replenishmentDto.getAddVoStockList() && replenishmentDto.getAddVoStockList().size() > 0) {
                for (CommodityDiffVo diffVo : replenishmentDto.getAddVoStockList()) {
                    this.changeCommoditySotck(10, diffVo, replenishmentDto, "");
                }
            }
            //下架商品
            if (null != replenishmentDto.getSubVoStockList() && replenishmentDto.getSubVoStockList().size() > 0) {
                for (CommodityDiffVo diffVo : replenishmentDto.getSubVoStockList()) {
                    this.changeCommoditySotck(10, diffVo, replenishmentDto, "");
                }
            }
        }

        //更新原有补货单信息
        ReplenishmentRecord updateRecord = new ReplenishmentRecord();
        updateRecord.setId(record.getId());
        updateRecord.setIactualShelves(addNum);
        updateRecord.setIactualUnder(subNum);
        updateRecord.setIactualShelvesAmount(addAmount);
        updateRecord.setIactualUnderAmount(subAmount);
        replenishmentRecordService.updateBySelective(updateRecord);

        //返回参数
        ReplenishmentResult result = new ReplenishmentResult();
        result.setReplenishmentRecord(record);
        result.setCommodities(commodities);
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 商品库存变更操作
     *
     * @param diffVo  操作商品信息
     * @param baseDto 商户 设备数据
     * @param itype   操作类型 10 补货员补货 20 商品出售 30 定时盘点
     * @param orderId 订单ID
     * @return
     */
    @Override
    public Map<String, Object> changeCommoditySotck(Integer itype, CommodityDiffVo diffVo, StockBaseDto baseDto, String orderId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean flagTemp = false;
        map.put("flag", flagTemp);
        //获取商品库存数据
        DeviceStock deviceStock = this.selectDeviceStockByCommodityId(baseDto.getSdeviceId(), diffVo.getScommodityId());
        Integer currStock = 0;
        if (null != deviceStock) {//锁住库存
            deviceStock = this.selectByPrimaryKeyForUpdate(deviceStock.getId());
            currStock = deviceStock.getIstock();
        }
        String[] arr = null;
        if (StringUtil.isNotBlank(diffVo.getRfidList())) {
            arr = diffVo.getRfidList().split(",");
        }
        StockDetail stockDetail = null;
        StockOperRecord operRecord = null;
        if (diffVo.getItype() == 10) {//库存增加
            boolean isUpdateStock = true;
            boolean isQuery = false;
            if (null == deviceStock) {//新增设备商品库存
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
                this.insert(deviceStock);//新增商品库存数据
            }
            StockDetail detailTemp = null;
            CommodityBatch updateBatch = null;
            CommodityBatch commodityBatch = null;
            //补货员补货更新商品批次
            if (itype == 10) {//商品补货才需要获取批次数据 其他异常跟批次无关
                //获取商品批次数据
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
            //循环商品数量 新增库存明细
            for (int i = 0; i < diffVo.getNumber(); i++) {//新增库存明细数据
                stockDetail = new StockDetail();
                //商品库存信息
                stockDetail.setSstockId(deviceStock.getId());
                stockDetail.setSstockCode(deviceStock.getScode());
                //商品信息
                stockDetail.setScommodityId(deviceStock.getScommodityId());
                stockDetail.setScommodityCode(deviceStock.getScommodityCode());
                stockDetail.setFcostAmount(diffVo.getFcostPrice());
                stockDetail.setSshelfLife(diffVo.getSshelfLife());

                if (null != arr) {//rfid商品
                    if (arr.length >= (i + 1)) {
                        //判断唯一编号是否存在  存在说明商品已售
                        detailTemp = stockDetailService.selectBySid(arr[i]);//rfid获取商品是否已售
                        if (null != detailTemp) {//存在新增已售商品 库存
                            BeanUtils.copyProperties(stockDetail, detailTemp);//复制对象
                            if (stockDetail.getIstatus().intValue() == 30) {//判断原商品是否过期
                                stockDetail.setIstatus(40);
                            } else {
                                stockDetail.setIstatus(20);
                            }
                            stockDetail.setId(null);
                            isQuery = true;
                        } else {
                            stockDetail.setSidentifies(arr[i]);
                            if (diffVo.getIstatus().intValue() == 20) {
                                stockDetail.setIstatus(60);//无效商品
                                isQuery = true;
                            } else {
                                stockDetail.setIstatus(10);//商品状态 正常
                            }
                        }
                    } else {
                        //未知商品
                        logger.error("未知商品，商品信息：{}", diffVo);
                        stockDetail.setIstatus(50);//异常商品
                        isQuery = true;
                    }
                } else {
                    //视觉商品根据状态判断
                    stockDetail.setSidentifies(IdGenerator.randomUUID());
                    if (diffVo.getIstatus().intValue() == 20) {
                        stockDetail.setIstatus(60);//无效商品
                        isQuery = true;
                    } else if (itype.intValue() == 20) {
                        stockDetail.setIstatus(20);//商品状态 已出售
                        isQuery = true;
                    } else if (itype.intValue() == 30) {
                        stockDetail.setIstatus(50);//商品状态 异常商品
                        isQuery = true;
                    } else {
                        stockDetail.setIstatus(10);//商品状态 正常
                    }
                }
                stockDetail.setIsaleStatus(null);//商品销售状态
                stockDetail.setTaddTime(new Date());//上架时间
                stockDetail.setFcommodityWeight(diffVo.getIweigth());
                if (itype == 10) {//补货员补货更新商品批次
                    if (null != commodityBatch && (updateBatch.getIshelfNum() + updateBatch.getIlossGoodsNum() + 1) > commodityBatch.getIcommodityNum()) {//跨批次 重新获取对应的批次
                        //先更新上一个批次号数据
                        //updateBatch.setIsaleStatus(10);//销售状态为 销售中
                        //updateBatch.setIstockStatus(20);//库存状态 标记部分上架
                        commodityBatchService.updateBySelective(updateBatch);
                        commodityBatch = this.getCommodityBatchData(diffVo.getScommodityId());
                        if (null != commodityBatch) {//重新赋值要更新批次数据
                            updateBatch.setId(commodityBatch.getId());
                            updateBatch.setIshelfNum(commodityBatch.getIshelfNum());
                            updateBatch.setIlossGoodsNum(commodityBatch.getIlossGoodsNum());
                            updateBatch.setIstockStatus(commodityBatch.getIstockStatus());
                            updateBatch.setIsaleStatus(commodityBatch.getIsaleStatus());
                        } else {
                            updateBatch = null;
                        }
                    }
                    //商品批次数据
                    if (null != commodityBatch) {
                        //更新批次销售状态
                        if (updateBatch.getIsaleStatus().intValue() == 30) {//待销售
                            updateBatch.setIsaleStatus(10);//销售状态为 销售中
                        }
                        //更新批次已上架数量
                        updateBatch.setIshelfNum(updateBatch.getIshelfNum() + 1);
                        //更新批次库存状态
                        if (updateBatch.getIshelfNum() + updateBatch.getIlossGoodsNum() >= commodityBatch.getIcommodityNum()) {
                            updateBatch.setIstockStatus(30);//库存状态 标记全部上架
                        } else if (updateBatch.getIstockStatus().intValue() == 10) {
                            updateBatch.setIstockStatus(20);//库存状态 标记部分上架
                        }
                        //设置设备库存明细商品批次数据
                        stockDetail.setDexpiredDate(commodityBatch.getDexpiredDate());
                        if (stockDetail.getDexpiredDate().before(new Date())) {
                            //商品过期 更新商品状态
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
                stockDetailService.insert(stockDetail);//保存数据

                //新增库存操作记录
                operRecord = new StockOperRecord();
                if (stockDetail.getIstatus().intValue() == 10) {
                    operRecord.setIstatus(10);//正常上架
                } else {
                    operRecord.setIstatus(40);//异常上架
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

            //更新批次数据
            if (null != updateBatch) {
                commodityBatchService.updateBySelective(updateBatch);
            }
            String abnormalStr = "";
            if (isQuery) {//判断查询异常商品明细数量  更新到库存主表
                abnormalStr = stockDetailService.getAbnormalMap(deviceStock.getId(), diffVo.getSpackageUnit());
            }
            //更新设备商品库存数据
            if (isUpdateStock || abnormalStr.length() > 0) {
                DeviceStock ds = new DeviceStock();
                ds.setId(deviceStock.getId());
                if (abnormalStr.length() > 0) {//更新库存备注
                    ds.setSremark(abnormalStr.substring(0, abnormalStr.length() - 1));
                } else {
                    ds.setSremark(deviceStock.getSremark());
                }
                if (isUpdateStock) {
                    ds.setIstock(deviceStock.getIstock() + diffVo.getNumber());
                    ds.setFsalePrice(diffVo.getFcommodityPrice());
                }
                //更新设备库存总重量
                BigDecimal totalWeight = deviceStock.getFcommodityTotalWeight();
                if (null == totalWeight) {
                    totalWeight = BigDecimal.ZERO;
                }
                BigDecimal temp = totalWeight.add(diffVo.getIweigth().multiply(new BigDecimal(diffVo.getNumber())));
                ds.setFcommodityTotalWeight(temp);
                ds.setTlastUpdateTime(new Date());//最后更新时间
                currStock = ds.getIstock();
                this.updateBySelective(ds);
            }


        } else if (diffVo.getItype() == 20) {//库存减少
            if (null == deviceStock) {
                logger.error("库存变更异常，商品库存不足，商品编号-设备编号：{}", diffVo.getScommodityCode() + "-" + baseDto.getSdeviceCode());
                return map;
            }
            List<StockDetail> stockDetails = null;
            if (StringUtil.isBlank(diffVo.getRfidList())) {//视觉商品 判断库存是否足够
                //获取商品库存明细
                stockDetails = stockDetailService.selectByMapForUpdate(deviceStock.getId());
                if (null == stockDetails || stockDetails.size() <= 0/* || stockDetails.size() < diffVo.getNumber()*/) {
                    logger.error("库存变更异常，商品库存明细库存不足，商品编号-设备编号：{}", diffVo.getScommodityCode() + "-" + baseDto.getSdeviceCode());
                    return map;
                }
            }
            //10 补货员补货 下架 20 商品出售 出售 30 定时盘点 异常下架
            //循环商品数量 修改库存明细状态
            StockDetail updateDetail = null;
            //StringBuffer sids = new StringBuffer();
            for (int i = 0; i < diffVo.getNumber(); i++) {
                if (null != arr && arr.length >= (i + 1)) {//如果是rfid商品
                    stockDetail = stockDetailService.selectBySid(arr[i]);//获取rfid商品库存明细
                } else {
                    stockDetail = stockDetails.get(i);
                }
                if (null == stockDetail || stockDetail.getIsaleStatus() != null) {
                    logger.error("库存商品不存在或已销售，商品RFID-设备编号：{}", arr[i] + "-" + baseDto.getSdeviceCode());
                    continue;
                }
                updateDetail = new StockDetail();
                updateDetail.setId(stockDetail.getId());
                //新增库存操作记录
                operRecord = new StockOperRecord();
                //更新销售状态
                if (itype == 10 && stockDetail.getIstatus().intValue() != 50) {//补货员
                    updateDetail.setIsaleStatus(70);
                    operRecord.setIstatus(20);//正常下架
                } else if (itype == 20) {//出售
                    updateDetail.setIsaleStatus(stockDetail.getIstatus());
                    if (stockDetail.getIstatus().intValue() == 10) {
                        operRecord.setIstatus(30);//正常售出
                    } else {
                        operRecord.setIstatus(60);//异常售出
                    }
                    //sids.append(stockDetail.getSidentifies()+",");
                } else if (itype == 30) {//盘点
                    updateDetail.setIsaleStatus(80);//异常下架
                    operRecord.setIstatus(50);//异常下架
                    //sids.append(stockDetail.getSidentifies()+",");
                } else {
                    updateDetail.setIsaleStatus(80);//异常下架
                    operRecord.setIstatus(50);//异常下架
                }
                stockDetailService.updateBySelective(updateDetail);//更新库存明细

                operRecord.setSmerchantCode(baseDto.getSmerchantCode());
                operRecord.setSmerchantId(baseDto.getSmerchantId());
                operRecord.setScommodityCode(diffVo.getScommodityCode());
                operRecord.setScommodityId(diffVo.getScommodityId());
                operRecord.setSdeviceId(baseDto.getSdeviceId());
                operRecord.setSdeviceCode(baseDto.getSdeviceCode());
                operRecord.setSidentifies(stockDetail.getSidentifies());
                operRecord.setTaddTime(new Date());
                stockOperRecordService.insert(operRecord);


                //更新批次信息 商品批次不为空 并且不是出售
                if (StringUtil.isNotBlank(stockDetail.getSbatchNo()) && itype != 20) {
                    commodityBatchService.updateBySbatchNo(stockDetail.getSbatchNo(), stockDetail.getScommodityId());
                }

                //更新订单商品数据销售数据
                if (itype == 20 && StringUtil.isNotBlank(orderId) && StringUtil.isNotBlank(stockDetail.getSidentifies())) {//订单出售
                    String arrs[] = orderId.split(",");
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("sidentifies", stockDetail.getSidentifies());
                    updateMap.put("commodityId", diffVo.getScommodityId());
                    if (null != stockDetail.getFcostAmount()) {//更新成本价
                        updateMap.put("fcostAmount", stockDetail.getFcostAmount());
                    }
                    if (null != stockDetail.getFtaxPoint()) {//更新税点
                        updateMap.put("ftaxPoint", stockDetail.getFtaxPoint());
                    }
                    for (String id : arrs) {//更新订单商品数据
                        updateMap.put("orderId", id);
                        orderCommodityService.updateByOrderId(updateMap);
                    }
                } else if (itype == 30 && StringUtil.isNotBlank(orderId) && StringUtil.isNotBlank(stockDetail.getSidentifies())) {//定时盘点  审核订单
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("sidentifies", stockDetail.getSidentifies());
                    updateMap.put("commodityId", diffVo.getScommodityId());
                    updateMap.put("orderId", orderId);
                    orderAuditCommodityService.updateByOrderId(updateMap);
                }
            }

            //获取异常商品数量
            String abnormalStr = stockDetailService.getAbnormalMap(deviceStock.getId(), diffVo.getSpackageUnit());

            //更新设备商品库存数据
            DeviceStock ds = new DeviceStock();
            ds.setId(deviceStock.getId());
            if (abnormalStr.length() > 0) {//更新库存备注
                ds.setSremark(abnormalStr.substring(0, abnormalStr.length() - 1));
            } else {
                ds.setSremark(deviceStock.getSremark());
            }
            ds.setIstock(deviceStock.getIstock() - diffVo.getNumber());
            /*if (ds.getIstock() < 0) {
                ds.setIstock(0);
			}*/
            //设备库存总重量
            BigDecimal temp = deviceStock.getFcommodityTotalWeight().subtract(diffVo.getIweigth().multiply(new BigDecimal(diffVo.getNumber())));
            ds.setFcommodityTotalWeight(temp);
            ds.setTlastUpdateTime(new Date());//最后更新时间
            currStock = ds.getIstock();
            this.updateBySelective(ds);
        }
        flagTemp = true;
        map.put("flag", flagTemp);
        map.put("currStock", currStock);
        return map;
    }

    /***
     * 获取商品ID
     * @param commodityId
     * @return
     */
    private CommodityBatch getCommodityBatchData(String commodityId) {
        //获取商品批次数据
        CommodityBatch commodityBatch = commodityBatchService.selectByCommodityId(commodityId);
        if (null != commodityBatch) {
            commodityBatch = commodityBatchService.selectByPrimaryKeyForUpdate(commodityBatch.getId());//锁定批次
            if (null == commodityBatch.getIcommodityNum()) {
                commodityBatch.setIcommodityNum(0);
            }
            if (null == commodityBatch.getIshelfNum()) {
                commodityBatch.setIshelfNum(0);
            }
            if (null == commodityBatch.getIlossGoodsNum()) {
                commodityBatch.setIlossGoodsNum(0);
            }
            if (commodityBatch.getIstockStatus() == 30 || (commodityBatch.getIshelfNum() + commodityBatch.getIlossGoodsNum()) >= commodityBatch.getIcommodityNum()) {//批次已上架 获取下一个批次商品
                if (commodityBatch.getIstockStatus() != 30) {
                    //数据异常 造成死循环
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
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    @Override
    public DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId) {
        return deviceStockDao.selectDeviceStockByCommodityId(deviceId, commodityId);
    }

    /**
     * 根据主键 锁定设备商品库存数据
     *
     * @param id 设备商品库存ID
     * @return
     */
    @Override
    public DeviceStock selectByPrimaryKeyForUpdate(String id) {
        return deviceStockDao.selectByPrimaryKeyForUpdate(id);
    }

    /**
     * 库存操作服务
     *
     * @param stockOperDto 服务参数
     * @return
     */
    @Override
    public ResponseVo<String> stockOper(StockOperDto stockOperDto) throws Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        //商品增加
        String orderId = "";
        if (null != stockOperDto.getAddVoList() && stockOperDto.getAddVoList().size() > 0) {
            for (CommodityDiffVo diffVo : stockOperDto.getAddVoList()) {
                //变更库存
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
        //商品减少
        if (null != stockOperDto.getSubVoList() && stockOperDto.getSubVoList().size() > 0) {
            for (CommodityDiffVo diffVo : stockOperDto.getSubVoList()) {
                //变更库存
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