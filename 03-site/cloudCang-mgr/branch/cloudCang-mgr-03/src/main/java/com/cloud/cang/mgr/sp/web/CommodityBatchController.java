package com.cloud.cang.mgr.sp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.CommodityBatchDomain;
import com.cloud.cang.mgr.sp.service.*;
import com.cloud.cang.mgr.sp.vo.CommodityBatchVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName CommodityBatchController
 * @Description 商品批次管理controller
 * @Author zengzexiong
 * @Date 2018年6月3日14:01:11
 */
@Controller
@RequestMapping("/commodity")
public class CommodityBatchController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(CommodityBatchController.class);

    @Autowired
    private CommodityInfoService commodityInfoService;//商品信息表

    @Autowired
    private MerchantInfoService merchantInfoService;//商户信息表

    @Autowired
    private OperatorService operatorService;//操作员信息表

    @Autowired
    private CommodityBatchService commodityBatchService;//商品批次


 /* ----------4.商品批次管理开始 ----------*/

    /**
     * 商品批次列表
     *
     * @return
     */
    @RequestMapping("/commodityBatch/list")
    public String commodityBatchList() {
        return "sp/commodityBatch/commodityBatch-list";
    }

    /**
     * 商品批次列表数据
     *
     * @param commodityBatchVo 初始化页面对象
     * @param paramPage        初始化分页对象
     * @return
     */
    @RequestMapping("/commodityBatch/queryData")
    @ResponseBody
    public PageListVo<List<CommodityBatchDomain>> queryCommodityBatchData(CommodityBatchVo commodityBatchVo, ParamPage paramPage) {
        PageListVo<List<CommodityBatchDomain>> pageListVo = new PageListVo<List<CommodityBatchDomain>>();//返回的页面对象
        Page<CommodityBatchDomain> page = new Page<CommodityBatchDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commodityBatchVo) {
            commodityBatchVo = new CommodityBatchVo();
        }
        commodityBatchVo.setIdelFlag(0);//是否删除：0否，1是

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        commodityBatchVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commodityBatchVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = commodityBatchService.selectPageByDomainWhere(page, commodityBatchVo);

        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 某类商品批次管理页面
     *
     * @return
     */
    @RequestMapping("/singleCommodityBatch/list")
    public String singleCommodityBatchList(String sid, ModelMap modelMap) {
        modelMap.put("sid", sid);
        return "sp/commodityBatch/commodityBatch-single-list";
    }

    /**
     * 某类商品的批次信息list
     *
     * @param commodityBatchVo
     * @param paramPage
     * @return
     */
    @RequestMapping("/commodityBatch/single/queryData")
    @ResponseBody
    public PageListVo<List<CommodityBatchDomain>> querySingleCommodityBatchData(CommodityBatchVo commodityBatchVo, ParamPage paramPage, String sid) {
        PageListVo<List<CommodityBatchDomain>> pageListVo = new PageListVo<List<CommodityBatchDomain>>();//返回的页面对象
        Page<CommodityBatchDomain> page = new Page<CommodityBatchDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtils.isBlank(sid)) {
            return pageListVo;
        }
        if (null == commodityBatchVo) {
            commodityBatchVo = new CommodityBatchVo();
        }
        commodityBatchVo.setIdelFlag(0);//是否删除：0否，1是

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        commodityBatchVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commodityBatchVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        // 查询某类商品
        commodityBatchVo.setScommodityId(sid);

        //分页查询
        page = commodityBatchService.selectPageByDomainWhere(page, commodityBatchVo);

        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 添加商品批次信息
     * @param sid 商品ID
     * @param map
     * @return
     */
    @RequestMapping("/commodityBatch/toAddBatch")
    public String toAddBatch(String sid, ModelMap map) {
        if (StringUtils.isNotBlank(sid)) {

            //数据库查询该商品信息
            CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(sid);
            MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            if (null != commodityInfo && null != merchantInfo) {
                map.put("commodityInfo", commodityInfo);
                map.put("merchantInfo", merchantInfo);
                return "sp/commodityBatch/commodityBatch-toAdd";
            }
        }
        return "sp/commodityBatch/commodityBatch-list";
    }


    /**
     * 添加商品批次信息
     *
     * @param commodityBatch
     * @return
     */
    @RequestMapping("/commodityBatch/addBatch")
    @ResponseBody
    public ResponseVo<CommodityBatch> addBatch(CommodityBatch commodityBatch ) {
        logger.debug("<=== 添加商品批次信息开始===>");
        try {

            String sbatchNo = CoreUtils.newCode(EntityTables.SP_COMMODITY_BATCH);//商品批次编号;
            //商品批次编号为空，直接返回false
            if (StringUtils.isBlank(sbatchNo)) {
                logger.error("商品批次编号为空");
                return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.error",null));
            }

            //插入数据到商品批次信息表
            commodityBatchService.insertCommodityBatch(commodityBatch,sbatchNo);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.log",null)+"，"+ MessageSourceUtils.getMessageByKey("main.batch.code",null)+"{0}", sbatchNo);
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            return ResponseVo.getSuccessResponse(commodityBatch);
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("添加商品品批次信息出现Exception异常：{}", e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.add.sp.batch.error",null));
    }


    /**
     * 编辑商品批次信息按钮
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/commodityBatch/toEditBatch")
    public String toEditBatch(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                CommodityBatch commodityBatch = commodityBatchService.selectByPrimaryKey(sid);
                if (null != commodityBatch) {
                    map.put("commodityBatch", commodityBatch);
                }
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.modify.sp.batch.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.batch.code",null)+"{0}", commodityBatch.getSbatchNo());
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                logger.debug("编辑商品批次信息按钮");
                return "sp/commodityBatch/commodityBatch-toEdit";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑商品批次信息
     * @param commodityBatch
     * @return
     */
    @RequestMapping("/commodityBatch/editBatch")
    @ResponseBody
    public ResponseVo<CommodityBatch> editBatch(CommodityBatch commodityBatch ) {
        logger.debug("编辑商品批次信息开始");
        try {
            String sid = commodityBatch.getId();
            if (StringUtils.isBlank(sid)) {
                logger.error(MessageSourceUtils.getMessageByKey("spcon.sp.batch.not.exist",null));
                return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.sp.batch.not.exist",null));
            }
            // 10待上架 20部分上架 30全部上架
            commodityBatch.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
            commodityBatch.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            //修改数据到商品批次信息表
            commodityBatchService.updateBySelectiveVo(commodityBatch);
            //操作日志
            CommodityBatch commodityBatch1 = commodityBatchService.selectByPrimaryKey(sid);
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.edit.sp.batch.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.batch.code",null)+"{0}", commodityBatch1.getSbatchNo());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.info("编辑商品批次结束,商品批次表ID为", commodityBatch.getId());
            return ResponseVo.getSuccessResponse(commodityBatch);
        } catch (ServiceException e) {
            logger.error("编辑商品批次信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑商品批次信息出现Exception异常：{}",e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.edit.sp.batch.error",null));
    }

    /**
     * 查看商品批次信息页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/commodityBatch/view")
    public String commodityBatchView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品批次信息
                CommodityBatch commodityBatch = commodityBatchService.selectByPrimaryKey(sid);
                if (null != commodityBatch) {
                    BigDecimal ftaxPoint = commodityBatch.getFtaxPoint();
                    if (null != ftaxPoint) {
                        commodityBatch.setFtaxPoint(ftaxPoint.setScale(2, BigDecimal.ROUND_HALF_UP));   // 四舍五入保留两位小数
                    }
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(commodityBatch.getSmerchantId());//查询商户信息
                    CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(commodityBatch.getScommodityId());//查询商品信息
                    StringBuffer sb = new StringBuffer();
                    if (StringUtils.isNotBlank(commodityInfo.getSbrandName())) {
                        sb.append(commodityInfo.getSbrandName());
                    }
                    if (StringUtils.isNotBlank(commodityInfo.getSname())) {
                        sb.append(commodityInfo.getSname());
                    }
                    if (StringUtils.isNotBlank(commodityInfo.getStaste())) {
                        sb.append(commodityInfo.getStaste());
                    }
                    if (null != commodityInfo.getIspecWeight()) {
                        sb.append(commodityInfo.getIspecWeight());
                    }
                    if (null != commodityInfo.getSspecUnit()) {
                        sb.append(commodityInfo.getSspecUnit());
                    }
                    if (StringUtils.isNotBlank(commodityInfo.getSpackageUnit())) {
                        sb.append("/"+commodityInfo.getSpackageUnit());
                    }
                    commodityInfo.setSname(sb.toString());
                    map.put("commodityBatch", commodityBatch);
                    map.put("merchantInfo", merchantInfo);
                    map.put("commodityInfo", commodityInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.view.sp.batch.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.batch.code",null)+"{0}", commodityBatch.getSbatchNo());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看商品批次信息页面结束", commodityBatch.getSbatchNo());
                    return "sp/commodityBatch/commodityBatch-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 跳转到批量添加商品批次信息
     * @param sid
     * @param modelMap
     * @return
     */
    @RequestMapping("/commodityBatch/manageCommodityBatch")
    public String manageCommodityBatch(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                List<CommodityInfo> commodityInfoList = new ArrayList<>();
                String[] deviceArray = sid.split(",");
                commodityInfoList = commodityInfoService.selectByKeys(deviceArray);
                modelMap.put("sid", sid);
                if (CollectionUtils.isNotEmpty(commodityInfoList)) {
                    modelMap.put("commodityInfoList", commodityInfoList);
                }
                return "/sp/commodityBatch/commodityBatch-manage-list";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 批量添加商品批次信息
     * @param commodityIds
     * @param commodityBatch
     * @return
     */
    @RequestMapping("/commodityBatch/addToCommodityBatch")
    public @ResponseBody
    ResponseVo<String> addToCommodityBatch(String commodityIds,CommodityBatch commodityBatch) {
        try {
            if (StringUtil.isNotBlank(commodityIds)) { //商品ID

                commodityBatchService.insertCommodityIds(commodityIds, commodityBatch);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.batch.add.sp.batch.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.batch.code",null)+"{0}", commodityBatch.getSbatchNo());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                return ResponseVo.getSuccessResponse(commodityIds);
            }
        } catch (ServiceException e) {
            logger.error("批量添加商品批次信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("批量添加商品批次信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.batch.add.sp.batch.error",null));
    }

    /* ----------4.商品批次管理开始结束 ----------*/


    /**
     * 处理上传图片
     *
     * @param file
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        if (file == null) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.empty",null));
        }


        String filName = file.getOriginalFilename();//文件原名
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//获取后缀名====>jpg|png|bmp
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.error",null));
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
            logger.error("上传文件出现IOException异常：{}",e);
        }
        return null;
    }


}
