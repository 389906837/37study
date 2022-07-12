package com.cloud.cang.mgr.sp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.*;
import com.cloud.cang.mgr.sp.service.*;
import com.cloud.cang.mgr.sp.vo.*;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.*;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @version 1.0
 * @ClassName CommodityController
 * @Description 商品管理controller
 * @Author zengzexiong
 * @Date 2018年2月12日11:48:32
 */
@Controller
@RequestMapping("/commodity")
public class CommodityInfoController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(CommodityInfoController.class);

    @Autowired
    private CommodityInfoService commodityInfoService;//商品信息表

    @Autowired
    private MerchantInfoService merchantInfoService;//商户信息表

    @Autowired
    private OperatorService operatorService;//操作员信息表

    @Autowired
    private BrandInfoService brandInfoService;//商品品牌

    @Autowired
    private LabelInfoService labelInfoService;//商品标签

    @Autowired
    private CategoryService categoryService;//商品分类

    @Autowired
    private CommodityBatchService commodityBatchService;//商品批次

    @Autowired
    private CommoditySkuService commoditySkuService;//视觉商品



    /* ----------0.设备商品管理信息开始 ----------*/

    /**
     * 商品信息列表
     *
     * @return
     */
    @RequestMapping("/commodityInfo/list")
    public String list(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "sp/commodityInfo/commodityInfo-list";
    }


    /**
     * 分页查询商品管理信息
     *
     * @param paramPage
     * @param commodityVo
     * @return
     */
    @RequestMapping("/commodityInfo/queryData")
    @ResponseBody
    public PageListVo<List<CommodityDomain>> queryCommodityData(ParamPage paramPage, CommodityVo commodityVo) {
        PageListVo<List<CommodityDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CommodityDomain>>();
        Page<CommodityDomain> page = new Page<CommodityDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commodityVo) {
            commodityVo = new CommodityVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        commodityVo.setQueryCondition(queryCondition);

        //只能查询自己供应商
//        commodityVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
//        commodityVo.setIstatus(10);
        commodityVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commodityVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = commodityInfoService.selectPageByDomainWhere(page, commodityVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    @ResponseBody
    @RequestMapping("/commodityInfo/getBrand")
    public ResponseVo<List<BrandInfo>> getBrand(String pid) {
        //获取商品品牌
        List<BrandInfo> brandinfoList = brandInfoService.selectBrandByMerchantId(pid);
        logger.debug("<===根据商户ID获取商户下的品牌信息结束===>");
        return ResponseVo.getSuccessResponse(brandinfoList);
    }

    /**
     * 商品信息添加按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/commodityInfo/toAdd")
    public String infoToAdd(ModelMap map) {
        //new 对象
        CommodityInfo commodityInfo = new CommodityInfo();
        MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        String merchantId = merchantInfo.getId();//商户ID
        if (null != merchantId) {
            List<LabelInfo> labelInfoList = labelInfoService.selectLabelByMerchantId(merchantId);//标签
            List<BrandInfo> brandInfoList = brandInfoService.selectAllValidBrand();//品牌
            List<Category> bigCategoryList = categoryService.selectAllValidBigCategory(merchantId);//大类

            map.put("brandInfoList", brandInfoList);
            map.put("bigCategoryList", bigCategoryList);
            map.put("labelInfoList", labelInfoList);
        }
        map.put("commodityInfo", commodityInfo);
        logger.debug("<===商品信息添加按钮结束===>");
        return "sp/commodityInfo/commodityInfo-toAdd";
    }


    /**
     * 根据父类ID查询小类
     *
     * @param pid
     * @return
     */
    @RequestMapping("/commodityInfo/getSmallCategory")
    public @ResponseBody
    ResponseVo<List<Category>> getsCategoryByParentId(String pid) {
        if (StringUtils.isNotBlank(pid)) {
            List<Category> smallCategoryList = categoryService.selectSmallCategoryByPid(pid);//小类
            if (CollectionUtils.isNotEmpty(smallCategoryList)) {
                return ResponseVo.getSuccessResponse(smallCategoryList);

            } else {
                return new ResponseVo<>(false, 111, "查询不到小类信息");
            }
        }
        logger.debug("<===根据父类ID查询小类出错===>");
        return new ResponseVo<>(false, 111, "根据父类ID查询小类出错");
    }

    /**
     * 添加商品信息入库
     *
     * @param commodityInfo
     * @return
     */
    @RequestMapping("/commodityInfo/add")
    @ResponseBody
    public ResponseVo<CommodityInfo> commodityAdd(@RequestParam(value = "file", required = false) MultipartFile scommodityImgfile, CommodityInfo commodityInfo, String skuId) {
        logger.debug("<===添加商品信息入库开始===>");
        try {
                String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();//商户ID
                String merchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();//商户编号
                String commodityCode = CoreUtils.newCode(EntityTables.SP_COMMODITY_INFO);//商品编号

                //商品编号，商户编号，商户ID之一为空，直接返回false
                if (StringUtils.isBlank(commodityCode) || StringUtils.isBlank(merchantCode) || StringUtils.isBlank(merchantId)) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，商品编号,商户编号不能为空!");
                }

                if (StringUtils.isNotBlank(skuId)) {    //视觉商品
                    CommoditySku commoditySku = commoditySkuService.selectByPrimaryKey(skuId);
                    if (commoditySku != null) {
                        //查询该视觉商品是否已经被添加过，并且没有删除
                        List<CommodityInfo> commodityInfoTemp = commodityInfoService.selectUnDelById(merchantId, skuId);
                        if (CollectionUtils.isNotEmpty(commodityInfoTemp)) {
                            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该视觉商品已经被添加过！");
                        }

                        String vrName = commoditySku.getScommodityName();// 视觉商品名称
                        String brandId = commoditySku.getSbrandId();//品牌ID
                        String brandName = commoditySku.getSbrandName();//品牌名称
                        String bigCategoryId = commoditySku.getSbigCategoryId();//大类ID
                        String bigCategoryName = commoditySku.getSbigCategoryName();//大类名称
                        String smallCategoryId = commoditySku.getSsmallCategoryId();//小类ID
                        String smallCategoryName = commoditySku.getSsmallCategoryName();//小类名称
                        Integer lifeType = commoditySku.getIlifeType();//保质期类型
                        Integer shelfLife = commoditySku.getIshelfLife();//保质期
                        String ispecWeight = commoditySku.getIspecWeight();//规格/重量
                        String sspecUnit = commoditySku.getSspecUnit();//规格单位
                        String spackageUnit = commoditySku.getSpackageUnit();//最小销售包装单位
                        if (StringUtils.isNotBlank(brandId)) {
                            commodityInfo.setSbrandId(brandId);
                        }
                        if (StringUtils.isNotBlank(brandName)) {
                            commodityInfo.setSbrandName(brandName);
                        }
                        if (StringUtils.isNotBlank(bigCategoryId)) {
                            //根据大类ID查询主类编号和主类名称
                            Category category = categoryService.selectByPrimaryKey(bigCategoryId);
                            if (null != category) {
                                String mainCategoryCode = category.getScategoryCode();  //主分类编号
                                String mainCategoryName = category.getScategoryName();  //主分类名称
                                if (StringUtils.isNotBlank(mainCategoryCode)) {
                                    commodityInfo.setScategoryCode(mainCategoryCode);
                                }
                                if (StringUtils.isNotBlank(mainCategoryName)) {
                                    commodityInfo.setScategoryName(mainCategoryName);
                                }
                            }
                            commodityInfo.setSbigCategoryId(bigCategoryId);
                        }
                        if (StringUtils.isNotBlank(bigCategoryName)) {
                            commodityInfo.setSbigCategoryName(bigCategoryName);
                        }
                        if (StringUtils.isNotBlank(smallCategoryId)) {
                            commodityInfo.setSsmallCategoryId(smallCategoryId);
                        }
                        if (StringUtils.isNotBlank(smallCategoryName)) {
                            commodityInfo.setSsmallCategoryName(smallCategoryName);
                        }
                        if (StringUtils.isNotBlank(sspecUnit)) {
                            commodityInfo.setSspecUnit(sspecUnit);
                        }
                        if (StringUtils.isNotBlank(spackageUnit)) {
                            commodityInfo.setSpackageUnit(spackageUnit);
                        }
                        commodityInfo.setSname(vrName);
                        commodityInfo.setIshelfLife(shelfLife);
                        commodityInfo.setIlifeType(lifeType);
                        commodityInfo.setIspecWeight(ispecWeight);
                        commodityInfo.setSvrCommodityId(skuId);
                        commodityInfo.setSvrCode(commoditySku.getSvrCode());            // 视觉商品识别码
                        commodityInfo.setSvrCommodityCode(commoditySku.getScode());     // 视觉商品编号
                        commodityInfo.setScommodityImg(commoditySku.getScommodityImg());// 视觉商品图片
                        commodityInfo.setStaste(commoditySku.getStaste());                                		// 口味
                        commodityInfo.setSpackagingMaterial(commoditySku.getSpackagingMaterial());        		// 包装材质
                        commodityInfo.setSorigin(commoditySku.getSorigin());                            		// 产地
                        commodityInfo.setSproductBarcode(commoditySku.getSproductBarcode());            		// 商品条形码
                        commodityInfo.setIidentificationType(commoditySku.getIidentificationType());    		// 标识类型
                        commodityInfo.setIweigth(commoditySku.getIweigth());                                    // 商品重量
                        commodityInfo.setIcommodityFloat(commoditySku.getIcommodityFloat());                    // 商品重量误差
                        commodityInfo.setIelectronicFloat(commoditySku.getIelectronicFloat());                  // 电子秤误差

                    }

                } else {    //普通商品
                    String commodityName = commodityInfo.getSname();//商品名称
                    if (StringUtils.isBlank(commodityName)) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商品名称不能为空");
                    }
                    BigDecimal commodityFloat = commodityInfo.getIcommodityFloat();     // 商品重量允许浮动值
                    BigDecimal electronicFloat = commodityInfo.getIelectronicFloat();   // 电子秤允许浮动值
                    if (null == commodityFloat) {
                        commodityInfo.setIcommodityFloat(BigDecimal.ZERO);   // 不填时，默认为0.00
                    }
                    if (null == electronicFloat) {
                        commodityInfo.setIelectronicFloat(BigDecimal.ZERO);  // 不填时，默认为0.00
                    }
                    String sbrandName = commodityInfo.getSbrandName();//品牌--名称
                    String sbrandId = commodityInfo.getSbrandId();//品牌--名称
                    String bigCategory = commodityInfo.getSbigCategoryName();//大类--id_名称
                    String smallCategory = commodityInfo.getSsmallCategoryName();//小类--id_名称
                    if (StringUtils.isBlank(sbrandId) || StringUtils.isBlank(sbrandName)) {
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("品牌信息不能为空");
                    }
                    if (StringUtils.isNotBlank(bigCategory)) {
                        commodityInfo.setSbigCategoryId(bigCategory.split("_")[0]);//大类ID
                        commodityInfo.setSbigCategoryName(bigCategory.split("_")[1]);//大类名称
                        //根据大类ID查询主类编号和主类名称
                        Category category = categoryService.selectByPrimaryKey(bigCategory.split("_")[0]);
                        if (null != category) {
                            String mainCategoryCode = category.getScategoryCode();  //主分类编号
                            String mainCategoryName = category.getScategoryName();  //主分类名称
                            if (StringUtils.isNotBlank(mainCategoryCode)) {
                                commodityInfo.setScategoryCode(mainCategoryCode);
                            }
                            if (StringUtils.isNotBlank(mainCategoryName)) {
                                commodityInfo.setScategoryName(mainCategoryName);
                            }
                        }
                    }
                    if (StringUtils.isNotBlank(smallCategory)) {
                        commodityInfo.setSsmallCategoryId(smallCategory.split("_")[0]);//小类ID
                        commodityInfo.setSsmallCategoryName(smallCategory.split("_")[1]);//小类名称
                    }

                }

                //图片上传
                if (null != scommodityImgfile && scommodityImgfile.getSize() > 0) {
                    //图片上传
                    String url = uploadHome(scommodityImgfile, "commodityInfo");
                    if (StringUtils.isNotBlank(url)) {
                        commodityInfo.setScommodityImg(url);
                    }
                }
                String label = commodityInfo.getSlabelName();//标签--id_名称
                if (StringUtils.isNotBlank(label)) {
                    commodityInfo.setSlabelId(label.split("_")[0]);//标签ID
                    commodityInfo.setSlabelName(label.split("_")[1]);//标签名称
                }

                BigDecimal costPrice = commodityInfo.getFcostPrice();    // 成本价
                BigDecimal salePrice = commodityInfo.getFsalePrice();    // 销售价
                BigDecimal marketPrice = commodityInfo.getFmarketPrice(); // 市场价
                if (null == costPrice) {
                    costPrice = new BigDecimal("0");
                }
                if (null == salePrice) {
                    salePrice = new BigDecimal("0");
                }
                if (null == marketPrice) {
                    marketPrice = new BigDecimal("0");
                }
                commodityInfo.setFsalePrice(salePrice);  // 销售价
                commodityInfo.setFcostPrice(costPrice); // 成本价
                commodityInfo.setFmarketPrice(marketPrice); // 市场价
                commodityInfo.setSmerchantId(merchantId); //商户ID
                commodityInfo.setSmerchantCode(merchantCode); //商户编号
                commodityInfo.setScode(commodityCode); // 商品编号
                commodityInfo.setIstatus(10); // 商品状态 10=正常 20=失效
                commodityInfo.setIdelFlag(0); // 是否删除0否1是
                commodityInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName()); //添加人
                commodityInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName()); // 修改人
                commodityInfo.setTaddTime(DateUtils.getCurrentDateTime()); // 添加日期
                commodityInfo.setTupdateTime(DateUtils.getCurrentDateTime()); // 修改日期
                commodityInfo.setIsaleNum(0);   //商品总销量
                commodityInfo.setIjoinActiveNum(0); //商品参与次数
                commodityInfo.setFavgCostPrice(BigDecimal.ZERO);
                commodityInfo.setFavgSalePrice(BigDecimal.ZERO);
                //插入数据到商品信息表
                commodityInfoService.insert(commodityInfo);
                //操作日志
                String logText = MessageFormat.format("新增商品编号，商品编号{0}", commodityInfo.getScode());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                logger.info("添加商品信息入库结束：{}", commodityInfo.getId());
                return ResponseVo.getSuccessResponse(commodityInfo);
        } catch (ServiceException e) {
            logger.error("添加商品信息信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加商品信息信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("添加商品信息信息");
    }


    /**
     * 删除商品信息（逻辑删除）
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/commodityInfo/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> groupManageDelete(String[] checkboxId) {
        logger.debug("删除商品信息（逻辑删除）开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String operater = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
                Date updateStamp = DateUtils.getCurrentDateTime();
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                commodityInfoService.batchDelByIds(checkboxId, operater, updateStamp, merchantId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format("删除商品信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除商品信息（逻辑删除）结束");
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("删除商品管理信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除商品管理信息失败！");
    }

    /**
     * 上架商品
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/commodityInfo/shelf")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> groupManageShelf(String[] checkboxId) {
        logger.debug("上架商品开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String operate = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
                Date updateStamp = DateUtils.getCurrentDateTime();
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                ResponseVo<String> responseVo = commodityInfoService.updateStatusByIds(checkboxId, Constrants.COMMONDITY_SHELF, operate, updateStamp, merchantId);
                //操作日志
                String logText = MessageFormat.format("上架商品，编号{0}", responseVo.getData());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.error("上架商品出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("上架商品出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常AC_EDIT失败！");
    }

    /**
     * 下架商品
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/commodityInfo/dropOff")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> groupManageDropOff(String[] checkboxId) {
        logger.debug("下架商品开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String operate = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
                Date updateStamp = DateUtils.getCurrentDateTime();
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                ResponseVo<String> responseVo = commodityInfoService.updateStatusByIds(checkboxId, Constrants.COMMONDITY_DROPOFF, operate, updateStamp, merchantId);
                //操作日志
                String logText = MessageFormat.format("下架商品，编号{0}", responseVo.getData());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return responseVo;
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("下架商品出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常下架商品失败！");
    }

    /**
     * 商品信息编辑按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/commodityInfo/toEdit")
    public String infotoEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品信息
                CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(sid);
                if (null != commodityInfo) {
                    String merchantId = commodityInfo.getSmerchantId();
                    if (null != merchantId) {
                        List<BrandInfo> brandInfoList = brandInfoService.selectBrandByMerchantId(merchantId);//品牌
                        List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(merchantId);//大类
                        List<Category> smallCategoryList = categoryService.selectSmallCategoryByMerchantId(merchantId);//小类
                        List<LabelInfo> labelInfoList = labelInfoService.selectLabelByMerchantId(merchantId);//标签

                        map.put("brandInfoList", brandInfoList);
                        map.put("bigCategoryList", bigCategoryList);
                        map.put("smallCategoryList", smallCategoryList);
                        map.put("labelInfoList", labelInfoList);
                    }
                    map.put("commodityInfo", commodityInfo);
                    String logText4 = MessageFormat.format("查询商品信息，编号{0}", commodityInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("<===商品信息编辑按钮开始结束===>", sid);
                    if (commodityInfo.getIstoreDevice() == 10) {
                        return "sp/commodityInfo/commodityInfo-vr-toEdit";
                    } else {
                        return "sp/commodityInfo/commodityInfo-com-toEdit";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 编辑商品信息页面入库
     *
     * @param commodityInfo
     * @return
     */
    @RequestMapping("/commodityInfo/Edit")
    public @ResponseBody
    ResponseVo<String> infotoEdit(@RequestParam(value = "file", required = false) MultipartFile file, CommodityInfo commodityInfo) {
        logger.debug("<=== 编辑商品信息页面入库开始===>");
        String sid = commodityInfo.getId();
        if (StringUtils.isNotBlank(sid)) {
            try {
                // 修改数据入商品信息表
                ResponseVo<String> responseVo = commodityInfoService.updateCommodityById(commodityInfo, file);
                //操作日志
                if (BooleanUtils.isTrue(responseVo.isSuccess())) {
                    String logText1 = MessageFormat.format("修改商品信息，编号{0}", responseVo.getData());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                    logger.debug("<=== 编辑商品信息页面入库完成===>", commodityInfo.getScode());
                }
                return responseVo;
            } catch (ServiceException e) {
                logger.error("编辑商品信息出现ServiceException异常：{}", e);
            } catch (Exception e) {
                logger.error("编辑商品信息出现Exception异常：{}", e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("修改商品信息失败");
    }

    /**
     * 查看商品详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/commodityInfo/view")
    public String commodityInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该设备信息
                CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(sid);
                if (null != commodityInfo) {
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(commodityInfo.getSmerchantId());//商户信息
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
                    map.put("commodityInfo", commodityInfo);
                    map.put("merchantInfo", merchantInfo);
                    String logText4 = MessageFormat.format("查看商品信息，编号{0}", commodityInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("<=== 查看商品详情页面结束===>", commodityInfo.getId());
                    return "sp/commodityInfo/commodityInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /* ----------0.设备商品信息信息结束 ----------*/



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
            logger.error("上传文件出现IOException异常：{}",e);
        }
        return null;
    }

    /**
     * 后台导出Excel
     *
     * @param request
     * @param response
     * @param commodityVo
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, CommodityVo commodityVo) {
        ExcelExportUtil e = new ExcelExportUtil();
        List<Map<String, Object>> list = commodityInfoService.selectCommodityInfoByExport(commodityVo);
        e.createRow(0);
        String[] names = new String[]{"序号","商品编号", "商品名称", "视觉商品编号","视觉商品识别编号", "商品标签名称","大类名称",
                "小类名称","成本价","市场价","销售价","最小销售包装单位","保质期","包装材质","产地",
        "商品条形码","商品状态","商品简称"};
        for (int j = 0; j < names.length; j++) {//表头
            e.setCell(j, names[j]);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> obj = list.get(i);
            String status = String.valueOf(obj.get("ISTATUS"));
            String type = String.valueOf(obj.get("ILIFE_TYPE"));
            String svrCommodityCode = String.valueOf(obj.get("SVR_COMMODITY_CODE"));
            String svrCode = String.valueOf(obj.get("SVR_CODE"));
            String spsshort = String.valueOf(obj.get("SSHORT_NAME"));
            e.createRow(i + 1);
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, String.valueOf(obj.get("SCODE")));
            e.setCell(2, String.valueOf(obj.get("SPNAME")));
            if(svrCommodityCode.equals("null")){
                e.setCell(3, String.valueOf(" "));
            }else {
                e.setCell(3, String.valueOf(obj.get("SVR_COMMODITY_CODE")));
            }
            if(svrCode.equals("null")){
                e.setCell(4, String.valueOf(" "));
            }else {
                e.setCell(4, String.valueOf(obj.get("SVR_CODE")));
            }
            e.setCell(5, String.valueOf(obj.get("SLABEL_NAME")));
            e.setCell(6, String.valueOf(obj.get("SBIG_CATEGORY_NAME")));
            e.setCell(7, String.valueOf(obj.get("SSMALL_CATEGORY_NAME")));
            e.setCell(8, String.valueOf(obj.get("FCOST_PRICE")));
            e.setCell(9, String.valueOf(obj.get("FMARKET_PRICE")));
            e.setCell(10, String.valueOf(obj.get("FSALE_PRICE")));
            e.setCell(11, String.valueOf(obj.get("SPACKAGE_UNIT")));
            if(type.equals("10")){
                e.setCell(12, String.valueOf(obj.get("ISHELF_LIFE"))+String.valueOf("天"));
            }else {
                e.setCell(12, String.valueOf(obj.get("ISHELF_LIFE"))+String.valueOf("个月"));
            }
            if(type.equals("null")){
                e.setCell(12, String.valueOf(" "));
            }
            e.setCell(13, String.valueOf(obj.get("SPACKAGING_MATERIAL")));
            e.setCell(14, String.valueOf(obj.get("SORIGIN")));
            e.setCell(15, String.valueOf(obj.get("SPRODUCT_BARCODE")));
            if(status.equals("10")){
                e.setCell(16, String.valueOf("正常"));
            }else {
                e.setCell(16, String.valueOf("下架"));
            }
            if(spsshort.equals("null")){
                e.setCell(17, String.valueOf(obj.get("SBRAND_NAME"))+String.valueOf(obj.get("SNAME")));
            }else {
                e.setCell(17, String.valueOf(obj.get("SSHORT_NAME")));
            }
        }
        e.downloadExcel(request, response, "商品列表-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }
}
