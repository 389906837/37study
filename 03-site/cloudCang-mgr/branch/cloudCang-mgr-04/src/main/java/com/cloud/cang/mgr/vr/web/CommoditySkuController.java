package com.cloud.cang.mgr.vr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.domain.CommoditySkuDomain;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.mgr.vr.service.MerchantSkuService;
import com.cloud.cang.mgr.vr.vo.CommoditySkuVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.model.vr.MerchantSku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName CommoditySkuController
 * @Description 视觉商品管理controller
 * @Author zengzexiong
 * @Date 2018年3月7日20:39:40
 */
@Controller
@RequestMapping("/commoditySku")
public class CommoditySkuController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(CommoditySkuController.class);

    @Autowired
    private MerchantInfoService merchantInfoService;                //商户

    @Autowired
    private OperatorService operatorService;                        //操作员信息表

    @Autowired
    private BrandInfoService brandInfoService;                      //商品品牌

    @Autowired
    private CategoryService categoryService;                        //商品分类

    @Autowired
    private CommoditySkuService commoditySkuService;                //视觉识别商品标准SKU表

    @Autowired
    private MerchantSkuService merchantSkuService;                  //商户SKU库


    /* ----------0.视觉识别商品标准SKU表开始 ----------*/


    @RequestMapping("/merchantSelectList")
    public String merchantSelectList(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
                if (null != merchantInfo) {
                    //获取商户当前sku
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("smerchantId", merchantInfo.getId());
                    List<MerchantSku> list = merchantSkuService.selectByMapWhere(paramMap);
                    if (null != list && list.size() > 0) {
                        StringBuffer sb = new StringBuffer();
                        for (MerchantSku sku : list) {
                            sb.append(sku.getScommodityId()+",");
                        }
                        modelMap.put("skuList",sb.toString());
                        modelMap.put("selectNums",list.size());
                    }
                    modelMap.put("merchantInfo", merchantInfo);
                    return "vr/sku/commoditySku-allot-list";
                }
            }
            return ExceptionUtil.to404();
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 视觉识别商品标准SKU
     * @return
     */
    @RequestMapping("/radioSelect")
    public String radioSelect() {
        return "sp/brand/commoditySku-radioSelect-list";
    }

    /**
     * 视觉识别商品标准SKU
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "vr/sku/commoditySku-list";
    }


    /**
     * 分配商户视觉商品分页查询
     * 在售
     * 已上架
     * @param paramPage
     * @param commoditySkuVo
     * @return
     */
    @RequestMapping("/merchantSelect/queryData")
    @ResponseBody
    public PageListVo<List<CommoditySkuDomain>> queryMerchantSelectData(ParamPage paramPage, CommoditySkuVo commoditySkuVo) {
        logger.debug("<===分页查询商品管理信息开始===>");
        PageListVo<List<CommoditySkuDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CommoditySkuDomain>>();
        Page<CommoditySkuDomain> page = new Page<CommoditySkuDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commoditySkuVo) {
            commoditySkuVo = new CommoditySkuVo();
        }
        commoditySkuVo.setIdelFlag(0);/* 是否删除0否1是 */
        commoditySkuVo.setIstatus(10); // 10=在售 20=停用
        commoditySkuVo.setIonlineStatus(20); // 上线状态 10 草稿 20 已上架 30 已下架

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commoditySkuVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        //分页查询
        page = commoditySkuService.selectPageByDomainWhere(page, commoditySkuVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询商品管理信息结束===>",pageListVo);
        return pageListVo;
    }


    /**
     * 分页查询视觉商品信息
     * @param paramPage
     * @param commoditySkuVo
     * @return
     */
    @RequestMapping("/list/queryData")
    @ResponseBody
    public PageListVo<List<CommoditySkuDomain>> queryCommodityData(ParamPage paramPage, CommoditySkuVo commoditySkuVo) {
        logger.debug("<===分页查询商品管理信息开始===>");
        PageListVo<List<CommoditySkuDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CommoditySkuDomain>>();
        Page<CommoditySkuDomain> page = new Page<CommoditySkuDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commoditySkuVo) {
            commoditySkuVo = new CommoditySkuVo();
        }
        commoditySkuVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commoditySkuVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        //分页查询
        page = commoditySkuService.selectPageByDomainWhere(page, commoditySkuVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询商品管理信息结束===>",pageListVo);
        return pageListVo;
    }


    /**
     * 视觉识别商品标准SKU
     * @return
     */
    @RequestMapping("/selectList")
    public String selectList() {
        return "vr/sku/commoditySku-select-list";
    }

    /**
     * 单选页面--分页查询视觉商品信息--在售、已上架视觉商品
     * @param paramPage
     * @param commoditySkuVo
     * @return
     */
    @RequestMapping("/selectList/queryData")
    @ResponseBody
    public PageListVo<List<CommoditySkuDomain>> selectSKUData(ParamPage paramPage, CommoditySkuVo commoditySkuVo) {
        logger.debug("<===分页查询商品管理信息开始===>");
        PageListVo<List<CommoditySkuDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CommoditySkuDomain>>();
        Page<CommoditySkuDomain> page = new Page<CommoditySkuDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commoditySkuVo) {
            commoditySkuVo = new CommoditySkuVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());

        // 10=商户查询 20 活动列表 30 系统用户 40 系统角色 50 设备列表 60 商品列表 70 会员列表
        String queryCondition = operatorService.generatePurviewSql(operator,60);
        commoditySkuVo.setQueryCondition(queryCondition);

        //只能查询自己供应商----如果商户sku使用范围为全部，返回在售，已上架商品；否则按照商户sku表查询对应视觉商品
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
        if (null != merchantInfo) {
            Integer skuType = merchantInfo.getIvmSkuType(); // 1全部    2部分
            if (!Integer.valueOf(1).equals(skuType)) {  // 不是全部范围的都按照商户ID查
                commoditySkuVo.setSmerchantId(merchantId);
            }
        }
        //查询==在售+已上架+未删除的商品
        commoditySkuVo.setIstatus(10);//10=在售 20=停用
        commoditySkuVo.setIonlineStatus(20);//上线状态 10 草稿 20 已上架 30 已下架
        commoditySkuVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commoditySkuVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        //分页查询--商户不为空
        if (StringUtils.isNotBlank(commoditySkuVo.getSmerchantId())) {
            page = commoditySkuService.selectPageByMerchantId(page, commoditySkuVo);
        } else {
            commoditySkuVo.setQueryCondition(""); // 全部时，清空查询条件
            page = commoditySkuService.selectPageByValidStatus(page, commoditySkuVo);
        }
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询商品管理信息结束===>",pageListVo);
        return pageListVo;
    }



    /**
     * 商品SKU信息添加按钮
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String infoToAdd(ModelMap map) {
        logger.debug("<===商品信息添加按钮开始===>");
        //new 对象
        CommoditySku commoditySku= new CommoditySku();
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();//商户ID
        if (StringUtils.isNotBlank(merchantId)) {
            List<BrandInfo> brandInfoList = brandInfoService.selectBrandByMerchantId(merchantId);//品牌
            List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(merchantId);//大类

            map.put("brandInfoList", brandInfoList);
            map.put("bigCategoryList", bigCategoryList);
        }
        map.put("commoditySku", commoditySku);
        logger.debug("<===商品信息添加按钮结束===>");
        return "vr/sku/commoditySku-toAdd";
    }

    /**
     * 给设备推送广告
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/toBatchImportVr")
    public String sendAdToDevice(ModelMap modelMap) {
        try {
            return "vr/sku/commoditySku-batchImport";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 批量导入Excel视觉商品SKU信息
     *
     * @param file Excel文件（支持2003，2007）
     * @return
     */
    @RequestMapping("/batchImportVrCommondityInfo")
    @ResponseBody
    public ResponseVo<String> batchImpVrComInfo(@RequestParam(value = "file", required = true) MultipartFile file) {
        logger.debug("开始批量导入视觉商品信息");
        try {
            String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();//商户ID
            ResponseVo<String> responseVo = commoditySkuService.batchUploadVrComInfo(file);
            //操作日志
            if (responseVo.isSuccess()) {
                String logText = MessageFormat.format("批量导入新增视觉商品,视觉商品编号{0}", responseVo.getData());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {
                if (null != responseVo.getData()) {
                    String logText = MessageFormat.format("批量导入新增视觉商品,视觉商品编号{0}", responseVo.getData());
                    LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                }
            }
            logger.debug("批量导入视觉商品信息完成：{}", responseVo.getMsg());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("批量上传视觉商品信息出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("批量上传视觉商品信息出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量上传视觉商品信息出错");
    }

    /**
     * 添加商品SKU信息入库
     * @param skuImgfile
     * @param commoditySku
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<String> skuAdd(@RequestParam(value = "file", required = false) MultipartFile skuImgfile, CommoditySku commoditySku) {
        logger.debug("<===添加视觉商品信息入库开始===>");
        try {
            String commodityName = commoditySku.getScommodityName();//商品名称
            String svrCode = commoditySku.getSvrCode();             //视觉识别编号

            if (StringUtils.isBlank(commodityName)) {
                return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "商品名称不能为空！");
            }
            if (StringUtils.isBlank(svrCode)) {
                return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "视觉商品识别编号为空！");
            }

            // 判断视觉商品识别码是否已经存在
            CommoditySku commoditySku2 = new CommoditySku();
            commoditySku2.setSvrCode(svrCode);
            List<CommoditySku> vrCommodityList = commoditySkuService.selectByEntityWhere(commoditySku2);
            if (CollectionUtils.isNotEmpty(vrCommodityList)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("该视觉商品识别编号已经存在！");
            }

            //插入数据到商品信息表
            ResponseVo<String> responseVo = commoditySkuService.insertCommoditySku(commoditySku, skuImgfile);
            //操作日志
            String logText = MessageFormat.format("新增视觉商品，编号{0}", commoditySku.getScode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.debug("<===添加视觉商品信息入库结束===>", commoditySku.getId());
            return responseVo;

        } catch (ServiceException e) {
            logger.error("添加视觉商品出现ServiceExceptionException异常", e);
        } catch (Exception e) {
            logger.error("添加视觉商品出现Exception异常", e);
        }
        logger.debug("<===添加视觉商品出错===>");
        return new ResponseVo<>(false, 111, "添加视觉商品出错");
    }

    /**
     * 删除商品SKU信息（逻辑删除）
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> skuDelete(String[] checkboxId) {
        logger.debug("<===删除商品信息（逻辑删除）开始===>");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                commoditySkuService.batchDelByIds(checkboxId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format("删除视觉商品信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("<===删除商品SKU信息（逻辑删除）结束===>", checkboxId);
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常删除商品SKU信息失败！", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除商品SKU信息失败！");
    }

    /**
     * 商品SKU信息编辑按钮
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String skuEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品信息
                CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(sid);

                if (null != commoditySku) {
                    String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();//商户ID
                    String bigCateId = commoditySku.getSbigCategoryId();//大类ID
                    if (StringUtils.isNotBlank(merchantId)) {
                        List<BrandInfo> brandInfoList = brandInfoService.selectBrandByMerchantId(merchantId);//品牌
                        List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(merchantId);//大类
                        map.put("brandInfoList", brandInfoList);//品牌集合
                        map.put("bigCategoryList", bigCategoryList);//大类集合

                        if (StringUtils.isNotBlank(bigCateId)) {
                            List<Category> smallCategoryList = categoryService.selectSmallCategoryByPid(bigCateId);//小类选项==根据大类ID查询
                            map.put("smallCategoryList", smallCategoryList);//小类集合
                        }
                    }
                    map.put("commoditySku", commoditySku);
                    String logText = MessageFormat.format("视觉商品信息编辑按钮，编号{0}", commoditySku.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    logger.debug("<===商品信息编辑按钮开始结束===>", sid);
                    return "vr/sku/commoditySku-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑商品SKU信息页面入库
     * @param scommodityImgfile
     * @param commoditySku
     * @return
     */
    @RequestMapping("/Edit")
    public @ResponseBody ResponseVo<String> vrCommodityToEdit(@RequestParam(value = "file", required = false) MultipartFile scommodityImgfile,CommoditySku commoditySku) {
        logger.debug("<=== 编辑商品SKU信息入库开始===>");
        String sid = commoditySku.getId();
        String svrCode = commoditySku.getSvrCode();
        if (StringUtils.isBlank(svrCode)) {
            return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_PARAM.getCode(), "视觉商品识别编号为空！");
        }
        if (StringUtils.isBlank(sid)) {
            return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_PARAM.getCode(), "视觉商品信息为空！");
        }
        try {
            // 修改数据入商品SKU信息表，返回ID，切勿返回编号
            ResponseVo<String> responseVo = commoditySkuService.updateVrCommodity(commoditySku, scommodityImgfile);
            //操作日志
            CommoditySku commoditySku1 = commoditySkuService.selectByPrimaryKey(sid);
            String logText1 = MessageFormat.format("修改视觉商品信息，编号{0}", commoditySku1.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
            logger.debug("<=== 编辑商品SKU信息页面入库开始===>", commoditySku.getScode());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("编辑视觉商品信息出现ServiceException异常",e);
        } catch (Exception e) {
            logger.error("编辑视觉商品信息出现Exception异常",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("修改商品SKU信息失败");
    }

    /**
     * 查看商品SKU详情页面
     * @param sid 商品ID
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String commodityInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该设备信息
                CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(sid);
                if (null != commoditySku) {
                    if (null != commoditySku.getIidentificationType()) {
                        map.put("markType", GrpParaUtil.getName(GrpParaUtil.COMMODITY_MARK_TYPE, commoditySku.getIidentificationType().toString()));
                    }

                    StringBuffer sb = new StringBuffer();
                    if (StringUtils.isNotBlank(commoditySku.getSbrandName())) {
                        sb.append(commoditySku.getSbrandName());
                    }
                    if (StringUtils.isNotBlank(commoditySku.getScommodityName())) {
                        sb.append(commoditySku.getScommodityName());
                    }
                    if (StringUtils.isNotBlank(commoditySku.getStaste())) {
                        sb.append(commoditySku.getStaste());
                    }
                    if (null != commoditySku.getIspecWeight()) {
                        sb.append(commoditySku.getIspecWeight());
                    }
                    if (null != commoditySku.getSspecUnit()) {
                        sb.append(commoditySku.getSspecUnit());
                    }
                    if (StringUtils.isNotBlank(commoditySku.getSpackageUnit())) {
                        sb.append("/" + commoditySku.getSpackageUnit());
                    }
                    commoditySku.setScommodityName(sb.toString());
                    map.put("commoditySku", commoditySku);
                    String logText4 = MessageFormat.format("查看视觉商品信息，编号{0}", commoditySku.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("<=== 查看商品SKU详情页面结束===>", commoditySku.getId());
                    return "vr/sku/commoditySku-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 停用该视觉商品
     * @param checkboxId
     * @return
     */
    @RequestMapping("/skuDisable")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> skuDisable(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = commoditySkuService.updateCommodityStatus(checkboxId, Constrants.SKU_COMMONDITY_DISABLE);
                //操作日志
                String id = checkboxId[0];
                if (StringUtils.isNotBlank(id)) {
                    CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(id);
                    if (null != commoditySku) {
                        String logText = MessageFormat.format("停用视觉商品,编号为{0}", commoditySku.getScode());
                        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                    }
                }
                return responseVo;
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("停用该商品出现异常！{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("停用该商品失败！");
    }

    /**
     * 启用该视觉商品
     * @param checkboxId
     * @return
     */
    @RequestMapping("/skuEnable")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> skuEnable(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = commoditySkuService.updateCommodityStatus(checkboxId, Constrants.SKU_COMMONDITY_ENABLE);
                //操作日志
                String id = checkboxId[0];
                if (StringUtils.isNotBlank(id)) {
                    CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(id);
                    if (null != commoditySku) {
                        String logText = MessageFormat.format("启用视觉商品,视觉商品编号{0}", commoditySku.getScode());
                        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                    }
                }
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("启用该商品出现异常！异常信息：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("启用该商品失败！");
    }

    /**
     * 下架该视觉商品
     * @param checkboxId
     * @return
     */
    @RequestMapping("/skuDropoff")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> skuDropoff(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = commoditySkuService.updateVrOnlineStatus(checkboxId, Constrants.SKU_COMMONDITY_DROPOFF);
                //操作日志
                String id = checkboxId[0];
                if (StringUtils.isNotBlank(id)) {
                    CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(id);
                    String logText = MessageFormat.format("下架视觉商品，编号为{0}", commoditySku.getScode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                }
                return responseVo;
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("下架视觉商品出现异常！异常信息：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("下架视觉商品失败！");
    }

    /**
     * 上架该视觉商品
     * @param checkboxId
     * @return
     */
    @RequestMapping("/skuShelf")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> skuShelf(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo<String> responseVo = commoditySkuService.updateVrOnlineStatus(checkboxId, Constrants.SKU_COMMONDITY_SHELF);
                //操作日志
                String logText = MessageFormat.format("上架视觉商品，编号为{0}", responseVo.getData());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (Exception e) {
            logger.error("下架视觉商品出现异常！异常信息：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("上架视觉商品失败！");
    }


    /**
     * 同步视觉商品信息到商品列表
     * @param checkboxId
     * @return
     */
    @RequestMapping("/synchronize")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> toSynchronize(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                //数据库查询该商品信息
                String user = SessionUserUtils.getSessionUserName();
                ResponseVo<String> responseVo = commoditySkuService.synchVrToCommodityInfo(checkboxId, user);
                String logText = MessageFormat.format("同步视觉商品信息到商品列表,视觉商品编号{0}", responseVo.getData());
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                logger.debug("<===同步视觉商品信息到商品列表完成===>");
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("跳转页面异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("同步视觉商品失败");
    }
    /* ----------0.视觉识别商品标准SKU表结束 ----------*/



    /* ----------1.商户SKU库开始 ----------*/

    /**
     * 给商户添加视觉商品
     *
     * @param vrCommodityIds
     * @param merchantId
     * @return
     */
    @RequestMapping("/merchantVrSku/addToMerchantSKU")
    public @ResponseBody
    ResponseVo<String> addToMerchantSKU(String vrCommodityIds, String merchantId) {
        try {
            if (StringUtils.isBlank(merchantId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("视觉商品或者商户不能为空！");
            }
            //商户和视觉商品SKU关系表
            ResponseVo<String> responseVo = merchantSkuService.insertVrCommodityIds(vrCommodityIds, merchantId);
            //操作日志
            String logText = MessageFormat.format("给商户分配视觉商品，商户编号{0}", responseVo.getData());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("给商户添加视觉商品出现ServiceException异常", e);
        } catch (Exception e) {
            logger.error("给商户添加视觉商品出现Exception异常", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("给商户添加视觉商品出现异常");
    }
    /* ----------1.商户SKU库结束 ----------*/


}
