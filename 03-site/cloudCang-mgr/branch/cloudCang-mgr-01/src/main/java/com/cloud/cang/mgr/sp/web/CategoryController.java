package com.cloud.cang.mgr.sp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.CategoryDomain;
import com.cloud.cang.mgr.sp.service.*;
import com.cloud.cang.mgr.sp.vo.CategoryMenuTreeVo;
import com.cloud.cang.mgr.sp.vo.CategoryTreeVo;
import com.cloud.cang.mgr.sp.vo.CategoryVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0
 * @ClassName CagegoryController
 * @Description ??????????????????controller
 * @Author zengzexiong
 * @Date 2018???6???3???13:37:17
 */
@Controller
@RequestMapping("/commodity")
public class CategoryController extends GenericController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private MerchantInfoService merchantInfoService;//???????????????

    @Autowired
    private OperatorService operatorService;//??????????????????

    @Autowired
    private CategoryService categoryService;//????????????



    /* ----------3.?????????????????? ----------*/

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping("/category/list")
    public String categoryList() {
        return "sp/category/category-leftList";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping("/category/getCategoryMenu")
    public @ResponseBody
    List<CategoryMenuTreeVo> getAllMenu() {
        // ????????????????????????????????????
        Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        List<CategoryMenuTreeVo> rootNodes = new ArrayList<>();

        List<CategoryTreeVo> mainList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(parameterGroupDetailList) && StringUtils.isNotBlank(merchantId)) {   // ???????????????tree
            for (ParameterGroupDetail param : parameterGroupDetailList) {
                CategoryTreeVo main = new CategoryTreeVo();
                main.setName(param.getSname());
                main.setId(param.getSvalue());
                mainList.add(main);
            }
        }

        List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(merchantId);   // ????????????????????????
        if (CollectionUtils.isNotEmpty(mainList)) {
            for (CategoryTreeVo mainVo : mainList) {    // ???????????????
                CategoryMenuTreeVo mainTree = new CategoryMenuTreeVo();

                mainTree.setText(mainVo.getName());
                mainTree.setIisParent("1");
                mainTree.setId(mainVo.getId());
                mainTree.setSparentId("");
                mainTree.setHref(mainVo.getId());

                if (CollectionUtils.isNotEmpty(bigCategoryList)) {
                    for (Category big : bigCategoryList) {  // ????????????,???????????????????????????????????????
                        if (big.getScategoryCode().equals(mainVo.getId())) {
                            CategoryMenuTreeVo bigTree = new CategoryMenuTreeVo();

                            bigTree.setSparentId(mainVo.getId());
                            bigTree.setHref(big.getId());
                            bigTree.setText(big.getSname());
                            bigTree.setIisParent("0");
                            bigTree.setId(big.getId());
                            mainTree.getNodes().add(bigTree);
                        }
                    }
                }
                rootNodes.add(mainTree);
            }
        }

        return rootNodes;
    }





    /**
     * ??????????????????
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping("/page")
    public String categoryPage(String categoryId,String sparentId, ModelMap map) {
        map.put("categoryId",categoryId);
        map.put("sparentId",sparentId);
        return "sp/category/category-list";
    }


    /**
     * ????????????????????????
     * @param paramPage ??????
     * @param categoryId ??????ID
     * @param sparentId ?????????
     * @return
     */
    @RequestMapping("/getCategoryByCategoryId")
    public @ResponseBody
    PageListVo<List<CategoryDomain>> getPurviewByMenuId(ParamPage paramPage, String categoryId, String sparentId) {
        PageListVo<List<CategoryDomain>> pageListVo = new PageListVo<List<CategoryDomain>>();
        /*if (StringUtils.isBlank(sparentId) && StringUtils.isBlank(categoryId)) {
            return pageListVo;
        }*/
        Page<CategoryDomain> page = new Page<CategoryDomain>();             //??????????????????
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        CategoryVo categoryVo = new CategoryVo();
        if (StringUtils.isBlank(sparentId)) {                         // ??????????????????????????????
            if (StringUtil.isNotBlank(categoryId)) {
                categoryVo.setScategoryCode(categoryId);
            } else {
                categoryVo.setIisParent(1);
            }
        } else {// ???????????????ID??????
            categoryVo.setSparentId(categoryId);
        }
        categoryVo.setIdelFlag(0);                                          //???????????????0??????1???
        //??????????????????
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 ????????????
        categoryVo.setQueryCondition(queryCondition);

        //???????????????????????? -> ??????ID????????????
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            categoryVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //????????????
        page = categoryService.selectPageByDomainWhere(page, categoryVo);

        //????????????????????????????????????List??????
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * ????????????????????????
     *
     * @param categoryVo ?????????????????????
     * @param paramPage  ?????????????????????
     * @return
     */
    @RequestMapping("/category/queryData")
    @ResponseBody
    public PageListVo<List<CategoryDomain>> queryCategoryData(CategoryVo categoryVo, ParamPage paramPage) {
        PageListVo<List<CategoryDomain>> pageListVo = new PageListVo<List<CategoryDomain>>();//?????????????????????
        Page<CategoryDomain> page = new Page<CategoryDomain>();//??????????????????
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == categoryVo) {
            categoryVo = new CategoryVo();
        }
        categoryVo.setIdelFlag(0);//???????????????0??????1???

        //??????????????????
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 ????????????
        categoryVo.setQueryCondition(queryCondition);

        //???????????????????????? -> ??????ID????????????
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            categoryVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //????????????
        page = categoryService.selectPageByDomainWhere(page, categoryVo);


        //????????????????????????????????????List??????
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * ??????????????????????????????
     *
     * @param map
     * @return
     */
    @RequestMapping("/category/toAdd")
    public String categoryToAdd(ModelMap map, String param) {
        //new ??????
        MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        String merchantId = merchantInfo.getId();
        if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(param)) {
            Category category = new Category();


            // ?????????????????????????????????
            String[] paramArray = param.split("-//-");

            if (paramArray.length == 1) { // ???????????????????????????????????????
                String one = paramArray[0];
                Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
                if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
                    for (ParameterGroupDetail p : parameterGroupDetailList) {
                        if (p.getSvalue().equals(one)) {
                            category.setScategoryName(p.getSvalue() + "_" + p.getSname());
                        }
                    }
                    map.put("category", category);
                    return "sp/category/category-toAddParent";
                }
            } else if (paramArray.length == 2) { // ???????????????????????????????????????
                String one = paramArray[0];
                String two = paramArray[1];
                List<Category> categoryList = categoryService.selectBigCategoryByMerchantId(merchantId);//?????????????????????
                if (CollectionUtils.isNotEmpty(categoryList)) {
                    category.setSparentId(one);
                    map.put("category", category);
                    return "sp/category/category-toAddChild";
                }
            }

        }
        return ExceptionUtil.to500();
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    @RequestMapping("/category/getParentCategory")
    public @ResponseBody
    ResponseVo<Set<ParameterGroupDetail>> getPid() {
        logger.debug("???????????????????????????");
        try {
            Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
            if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
                return ResponseVo.getSuccessResponse(parameterGroupDetailList);
            }
        } catch (Exception e) {
            logger.error("?????????????????????????????????Exception?????????{}",e);
        }
        return new ResponseVo<>(false, 111, "???????????????????????????");
    }


    /**
     * ????????????????????????
     *
     * @param sicon
     * @param category
     * @return
     */
    @RequestMapping("/category/add")
    @ResponseBody
    public ResponseVo<Category> categoryAdd(@RequestParam(value = "file", required = false) MultipartFile sicon, Category category) {
        logger.debug("??????????????????????????????");
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            String merchantId = merchantInfo.getId();
            String merchantCode = merchantInfo.getScode();
            if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {
                category.setSmerchantId(merchantId);//??????ID
                category.setSmerchantCode(merchantCode);//????????????
            }
            if (null != sicon && sicon.getSize() > 0) {//???????????????
                String url = uploadHome(sicon, "brand");
                if (StringUtils.isNotBlank(url)) {
                    category.setSicon(url);//????????????
                }
            }

            //sparentId????????????????????????????????????
            String parentName = category.getSparentId();
            if (StringUtils.isNotBlank(parentName)) {   //??????
                // ??????????????????
                String smallCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//??????????????????
                if (StringUtils.isBlank(smallCode)) {
                    logger.error("???????????????????????????????????????{0}?????????ID???{1}", merchantCode, parentName);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????");
                }
                category.setScode(smallCode);
                category.setIisParent(0);
            } else {    // ??????
                category.setIisParent(1);
                String mainCategory = category.getScategoryName();
                if (StringUtils.isNotBlank(mainCategory)) {
                    category.setScategoryCode(mainCategory.split("_")[0]);
                    category.setScategoryName(mainCategory.split("_")[1]);
                }
                // ??????????????????
                String baiCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//??????????????????
                if (StringUtils.isBlank(baiCode)) {
                    logger.error("???????????????????????????????????????{0}", merchantCode);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????");
                }
                category.setScode(baiCode);
            }
            category.setItype(10);  // 10?????????20??????
            category.setIdelFlag(0);/* ????????????0???1??? */
            category.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//?????????
            category.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//?????????
            category.setTaddTime(DateUtils.getCurrentDateTime());/* ???????????? */
            category.setTupdateTime(DateUtils.getCurrentDateTime());/* ???????????? */
            //????????????????????????????????????
            categoryService.insert(category);
            //????????????
            String logText = MessageFormat.format("???????????????????????????????????????{0}", category.getSname());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.debug("??????????????????????????????", category.getId());
            return ResponseVo.getSuccessResponse(category);
        } catch (ServiceException e) {
            logger.error("??????????????????????????????ServiceException?????????{}",e);
        } catch (Exception e) {
            logger.error("??????????????????????????????Exception?????????{}",e);
        }
        return new ResponseVo<>(false, 111, "??????????????????????????????");
    }

    /**
     * ????????????????????????
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/category/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> categoryDelete(String[] checkboxId) {
        logger.debug("??????????????????????????????");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                categoryService.batchLogicDelByIds(checkboxId, merchantId);//????????????
                //????????????
                String logText = MessageFormat.format("????????????????????????", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("??????????????????????????????", checkboxId);
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("??????????????????????????????ServiceException?????????{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????????????????");
    }


    /**
     * ??????????????????????????????
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/category/toEdit")
    public String categoryToEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //????????????????????????????????????
                Category category = categoryService.selectByPrimaryKey(sid);
                if (null != category) {
                    MerchantInfo merchantInfo = merchantInfoService.selectOne(category.getSmerchantId());//????????????
                    List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(category.getSmerchantId());//????????????

                    // ??????????????????????????????
                    Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
                    if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
                        map.put("dic", parameterGroupDetailList);
                    }

                    map.put("category", category);
                    map.put("bigCategoryList", bigCategoryList);
                    map.put("merchantInfo", merchantInfo);
                    String logText4 = MessageFormat.format("?????????????????????????????????????????????{0}", category.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("????????????????????????????????????", category.getId());
                    return "sp/category/category-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ????????????????????????
     *
     * @param sicon
     * @param category
     * @return
     */
    @RequestMapping("/category/Edit")
    public @ResponseBody
    ResponseVo<Category> categoryEdit(@RequestParam(value = "file", required = false) MultipartFile sicon, Category category) {
        logger.debug("??????????????????????????????");
        String sid = category.getId();
        String operater = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
        Date timeStamp = DateUtils.getCurrentDateTime();
        String merchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
        if (StringUtils.isNotBlank(sid)) {
            try {

                // 20180827?????????????????????????????????????????????
                Category categoryDomain = categoryService.selectByPrimaryKey(sid);
                if (null == categoryDomain) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????");
                }
                String smallCategoryCode = categoryDomain.getScode();
                Integer isParent = categoryDomain.getIisParent();
                if (null != isParent && 1 != isParent && StringUtils.isBlank(smallCategoryCode)) {  // ????????????????????????
                    String smallCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//??????????????????
                    if (StringUtils.isBlank(smallCode)) {
                        logger.error("???????????????????????????????????????{0}???????????????{1}", merchantCode, operater);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????????????????");
                    }
                    category.setScode(smallCode);
                }

                if (null != sicon && sicon.getSize() > 0) {//???????????????
                    String url = uploadHome(sicon, "brand");
                    if (StringUtils.isNotBlank(url)) {
                        category.setSicon(url);//????????????
                    }
                }

                category.setTupdateTime(timeStamp);//????????????
                category.setSupdateUser(operater);//?????????
                // ????????????????????????????????????
                categoryService.updateBySelectiveVo(category);
                //????????????
                String logText1 = MessageFormat.format("???????????????????????????????????????{0}", categoryDomain.getSname());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("??????????????????????????????", category.getId());
                return ResponseVo.getSuccessResponse(category);
            } catch (ServiceException e) {
                logger.error("??????????????????????????????ServiceException?????????{}",e);
            } catch (Exception e) {
                logger.error("??????????????????????????????Exception?????????{}",e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????");
    }

    /**
     * ????????????????????????????????????
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/category/view")
    public String categoryView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //????????????????????????????????????
                Category category = categoryService.selectByPrimaryKey(sid);
                if (null != category) {
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(category.getSmerchantId());//??????????????????
                    Category categoryParent = categoryService.selectByPrimaryKey(category.getSparentId());//?????????ID??????
                    if (null != category.getSparentId()) {
                        Category category1 = categoryService.selectByPrimaryKey(category.getSparentId());
                        if (null != category1) {
                            category.setScategoryName(category1.getScategoryName());
                        }
                    }
                    map.put("category", category);
                    map.put("merchantInfo", merchantInfo);
                    map.put("categoryParent", categoryParent);
                    String logText4 = MessageFormat.format("???????????????????????????????????????{0}", category.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("??????????????????????????????????????????", category.getId());
                    return "sp/category/category-view";
                }
            }
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }


    /* ----------3.?????????????????? ----------*/

    /**
     * ??????????????????
     *
     * @param file
     * @return
     */
    private String uploadHome(MultipartFile file, String pathName) {
        if (file == null) {
            throw new ServiceException("????????????????????????");
        }


        String filName = file.getOriginalFilename();//????????????
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//???????????????====>jpg|png|bmp
        //??????????????????
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("?????????????????????????????????");
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
            logger.error("??????????????????IOException?????????{}",e);
        }
        return null;
    }


    /**
     * ???????????????????????????
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/category/initCategory")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> initCategory(String[] checkboxId) {
        logger.debug("??????????????????????????????");
        try {
            return categoryService.initCategory();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("??????????????????????????????ServiceException?????????{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????????????????????????????");
    }
}
