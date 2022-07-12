package com.cloud.cang.mgr.rm.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.rm.domain.ReplenishCommodityDomain;
import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.mgr.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.mgr.rm.service.ReplenishmentRecordService;
import com.cloud.cang.mgr.rm.vo.ReplenishMentVo;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品补货记录查询
 * @author ChangTanchang
 * @time:2018-01-24 16:07:00
 * @version 1.0
 */
@Controller
@RequestMapping("/replenishment")
public class ReplenishMentController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(ReplenishMentController.class);

    // 注入serv
    @Autowired
    private ReplenishmentRecordService replenishmentRecordService;

    @Autowired
    private OperatorService operatorService; // 操作员信息表

    @Autowired
    MerchantInfoService merchantInfoService; // 商户信息表

    @Autowired
    DeviceInfoService deviceInfoService; // 设备基础信息表

    @Autowired
    private ReplenishmentCommodityService replenishmentCommodityService; // 补货商品明细表
    @RequestMapping("/list")
    public String list(){
        return "rm/replenishment-list";
    }

    /**
     * 商品补货记录查询
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<ReplenishMentDomain>> queryReplenishMent(ParamPage paramPage, ReplenishMentVo replenishMentVo){
        PageListVo<List<ReplenishMentDomain>> pageListVo = new PageListVo<List<ReplenishMentDomain>>();
        Page<ReplenishMentDomain> page = new Page<ReplenishMentDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == replenishMentVo) {
            replenishMentVo = new ReplenishMentVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,80);//60 商品列表
        replenishMentVo.setQueryCondition(queryCondition);

//        //只能查询自己供应商
        if (SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId().equals(replenishMentVo.getSmerchantId())) {
            replenishMentVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            replenishMentVo.setIstatus(10);
            replenishMentVo.setIdelFlag(0);/* 是否删除0否1是 */
        }
        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            replenishMentVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = replenishmentRecordService.selectReplenishMent(page,replenishMentVo);
        if (page != null){
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 商品补货记录信息表明细
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryReplenishMentInfo")
    public String queryReplenishMentInfo(ModelMap modelMap, String sid){
        try{
        if (StringUtils.isNotBlank(sid)) {
            ReplenishmentRecord replenishmentRecord = replenishmentRecordService.selectByPrimaryKey(sid);
            MerchantInfo merchantInfo = null; // 商户信息表
            DeviceInfo deviceInfo = null; // 设备信息表
            List<ReplenishCommodityDomain> list = null; // 补货商品明细表

            String getSid = replenishmentRecord.getId();
            if (StringUtils.isNotBlank(getSid)) {
                merchantInfo = merchantInfoService.selectOne(replenishmentRecord.getSmerchantId());
                deviceInfo = deviceInfoService.selectByPrimaryKey(replenishmentRecord.getSdeviceId());
                list = replenishmentCommodityService.selectInfoId(getSid);
            }
            modelMap.put("replenishmentRecord",replenishmentRecord);
            modelMap.put("merchantInfo",merchantInfo);
            modelMap.put("deviceInfo",deviceInfo);
            modelMap.put("list",list);
        }
        return "rm/replenishment-infoList";
        } catch (Exception e) {
            log.error("跳转页面异常：{}",e);
        }
        return ExceptionUtil.to404();
    }


    /**
     * 后台导出Excel
     * @param request
     * @param response
     * @param replenishMentVo
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, ReplenishMentVo replenishMentVo) {
        ExcelExportUtil e = new ExcelExportUtil();
        List<Map<String, Object>> list = replenishmentRecordService.selectReplenishMentByExport(replenishMentVo);
        e.createRow(0);
        String[] names = new String[]{
                MessageSourceUtils.getMessageByKey("main.serial.number"),
                MessageSourceUtils.getMessageByKey("rm.replenishment.bill.number"),
                MessageSourceUtils.getMessageByKey("main.merchant.number"),
                MessageSourceUtils.getMessageByKey("main.merchant.name"),
                MessageSourceUtils.getMessageByKey("main.device.id"),
                MessageSourceUtils.getMessageByKey("main.device.name"),
                MessageSourceUtils.getMessageByKey("main.device.address"),
                MessageSourceUtils.getMessageByKey("main.product.number"),
                MessageSourceUtils.getMessageByKey("main.product.name"),
                MessageSourceUtils.getMessageByKey("om.commodity.sales.price.yuan"),
                MessageSourceUtils.getMessageByKey("report.total.merchandise.yuan"),
                MessageSourceUtils.getMessageByKey("rm.replenishment.quantity"),
                MessageSourceUtils.getMessageByKey("spcon.commodity.status"),
                MessageSourceUtils.getMessageByKey("rm.replenishment.status"),
                MessageSourceUtils.getMessageByKey("rm.replenishment.type"),
                MessageSourceUtils.getMessageByKey("rm.name.of.replenisher"),
                MessageSourceUtils.getMessageByKey("rm.replenisher-s.mobile.phone.number")};
        for (int j = 0; j < names.length; j++) {//表头
            e.setCell(j, names[j]);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> obj = list.get(i);
            String spStatus = String.valueOf(obj.get("SPISTATUS"));
            String istatus = String.valueOf(obj.get("ISTATUS"));
            String itype = String.valueOf(obj.get("SPITYPE"));
            String sbname = String.valueOf(obj.get("SBNAME"));
            String spcode = String.valueOf(obj.get("SPCODE"));
            String spname = String.valueOf(obj.get("SPNAME"));
            String spfsaleprice = String.valueOf(obj.get("SPFSALEPRICE"));
            String spallprice = String.valueOf(obj.get("SPALLPRICE"));
            String counts = String.valueOf(obj.get("COUNTS"));
            e.createRow(i + 1);
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, String.valueOf(obj.get("SCODE")));
            e.setCell(2, String.valueOf(obj.get("SMERCHANT_CODE")));
            e.setCell(3, String.valueOf(obj.get("MERCHANTNAME")));
            e.setCell(4, String.valueOf(obj.get("SDEVICE_CODE")));
            if(sbname.equals("null")){
                e.setCell(5, String.valueOf(" "));
            }else {
                e.setCell(5, String.valueOf(obj.get("SBNAME")));
            }
            e.setCell(6, String.valueOf(obj.get("SDEVICE_ADDRESS")));
            if(spcode.equals("null")){
                e.setCell(7, String.valueOf(" "));
            }else {
                e.setCell(7, String.valueOf(obj.get("SPCODE")));
            }
            if(spname.equals("null")){
                e.setCell(8, String.valueOf(" "));
            }else {
                e.setCell(8, String.valueOf(obj.get("SPNAME")));
            }
            if(spfsaleprice.equals("null")){
                e.setCell(9, String.valueOf(" "));
            }else {
                e.setCell(9, String.valueOf(obj.get("SPFSALEPRICE")));
            }
            if(spallprice.equals("null")){
                e.setCell(10, String.valueOf(" "));
            }else {
                e.setCell(10, String.valueOf(obj.get("SPALLPRICE")));
            }
            if(counts.equals("null")){
                e.setCell(11, String.valueOf(" "));
            }else {
                e.setCell(11, String.valueOf(obj.get("COUNTS")));
            }
            if(spStatus.equals("null")){
                e.setCell(12, String.valueOf(" "));
            }
            if(spStatus.equals("10")){
                e.setCell(12, String.valueOf(MessageSourceUtils.getMessageByKey("main.normal",null)));
            }
            if(spStatus.equals("20")) {
                e.setCell(12, MessageSourceUtils.getMessageByKey("excel.status.invalid"));
            }
            if(istatus.equals("10")){
                e.setCell(13, MessageSourceUtils.getMessageByKey("rm.to.be.delivered"));
            }
            if(istatus.equals("20")){
                e.setCell(13, MessageSourceUtils.getMessageByKey("rm.distribution"));
            }
            if(istatus.equals("30")){
                e.setCell(13, MessageSourceUtils.getMessageByKey("rm.completed="));
            }
            if(istatus.equals("40")){
                e.setCell(13, MessageSourceUtils.getMessageByKey("rm.cancellation.of.distribution"));
            }
            if(itype.equals("10")){
                e.setCell(14, MessageSourceUtils.getMessageByKey("rm.upper.shelf"));
            }else {
                e.setCell(14, String.valueOf(MessageSourceUtils.getMessageByKey("main.obtained",null)));
            }
            e.setCell(15, String.valueOf(obj.get("SRENEWAL_NAME")));
            e.setCell(16, String.valueOf(obj.get("SRENEWAL_MOBILE")));
        }
        e.downloadExcel(request, response, MessageSourceUtils.getMessageByKey("menu.replenishment.list")+"-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }
}
