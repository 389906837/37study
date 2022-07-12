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
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
 * @Description 商品分类管理controller
 * @Author zengzexiong
 * @Date 2018年6月3日13:37:17
 */
@Controller
@RequestMapping("/commodity")
public class CategoryController extends GenericController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private MerchantInfoService merchantInfoService;//商户信息表

    @Autowired
    private OperatorService operatorService;//操作员信息表

    @Autowired
    private CategoryService categoryService;//商品分类



    /* ----------3.商品分类开始 ----------*/

    /**
     * 商品分类列表
     *
     * @return
     */
    @RequestMapping("/category/list")
    public String categoryList() {
        return "sp/category/category-leftList";
    }

    /**
     * 左侧树形菜单
     *
     * @return
     */
    @RequestMapping("/category/getCategoryMenu")
    public @ResponseBody
    List<CategoryMenuTreeVo> getAllMenu() {
        // 获取数据字典配置主类名称
        Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        List<CategoryMenuTreeVo> rootNodes = new ArrayList<>();

        List<CategoryTreeVo> mainList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(parameterGroupDetailList) && StringUtils.isNotBlank(merchantId)) {   // 添加主分类tree
            for (ParameterGroupDetail param : parameterGroupDetailList) {
                CategoryTreeVo main = new CategoryTreeVo();
                main.setName(param.getSname());
                main.setId(param.getSvalue());
                mainList.add(main);
            }
        }

        List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(merchantId);   // 商户所有可用大类
        if (CollectionUtils.isNotEmpty(mainList)) {
            for (CategoryTreeVo mainVo : mainList) {    // 遍历主分类
                CategoryMenuTreeVo mainTree = new CategoryMenuTreeVo();

                mainTree.setText(mainVo.getName());
                mainTree.setIisParent("1");
                mainTree.setId(mainVo.getId());
                mainTree.setSparentId("");
                mainTree.setHref(mainVo.getId());

                if (CollectionUtils.isNotEmpty(bigCategoryList)) {
                    for (Category big : bigCategoryList) {  // 遍历大类,将该大类添加到主分类节点上
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
     * 加载左侧菜单
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
     * 商品分类分页查询
     * @param paramPage 分页
     * @param categoryId 父类ID
     * @param sparentId 父节点
     * @return
     */
    @RequestMapping("/getCategoryByCategoryId")
    public @ResponseBody
    PageListVo<List<CategoryDomain>> getPurviewByMenuId(ParamPage paramPage, String categoryId, String sparentId) {
        PageListVo<List<CategoryDomain>> pageListVo = new PageListVo<List<CategoryDomain>>();
        /*if (StringUtils.isBlank(sparentId) && StringUtils.isBlank(categoryId)) {
            return pageListVo;
        }*/
        Page<CategoryDomain> page = new Page<CategoryDomain>();             //新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        CategoryVo categoryVo = new CategoryVo();
        if (StringUtils.isBlank(sparentId)) {                         // 按商品主分类编号查询
            if (StringUtil.isNotBlank(categoryId)) {
                categoryVo.setScategoryCode(categoryId);
            } else {
                categoryVo.setIisParent(1);
            }
        } else {// 按商品父类ID查询
            categoryVo.setSparentId(categoryId);
        }
        categoryVo.setIdelFlag(0);                                          //是否删除：0否，1是
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        categoryVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            categoryVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = categoryService.selectPageByDomainWhere(page, categoryVo);

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
     * 商品分类列表数据
     *
     * @param categoryVo 初始化页面对象
     * @param paramPage  初始化分页对象
     * @return
     */
    @RequestMapping("/category/queryData")
    @ResponseBody
    public PageListVo<List<CategoryDomain>> queryCategoryData(CategoryVo categoryVo, ParamPage paramPage) {
        PageListVo<List<CategoryDomain>> pageListVo = new PageListVo<List<CategoryDomain>>();//返回的页面对象
        Page<CategoryDomain> page = new Page<CategoryDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == categoryVo) {
            categoryVo = new CategoryVo();
        }
        categoryVo.setIdelFlag(0);//是否删除：0否，1是

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        categoryVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            categoryVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = categoryService.selectPageByDomainWhere(page, categoryVo);


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
     * 添加商品分类信息按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/category/toAdd")
    public String categoryToAdd(ModelMap map, String param) {
        //new 对象
        MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        String merchantId = merchantInfo.getId();
        if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(param)) {
            Category category = new Category();


            // 判断是主分类还是父分类
            String[] paramArray = param.split("-//-");

            if (paramArray.length == 1) { // 当前为主分类，此时增加父类
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
            } else if (paramArray.length == 2) { // 当前为父分类，此时增加子类
                String one = paramArray[0];
                String two = paramArray[1];
                List<Category> categoryList = categoryService.selectBigCategoryByMerchantId(merchantId);//查询所有父分类
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
     * 查询父分类集合
     *
     * @return
     */
    @RequestMapping("/category/getParentCategory")
    public @ResponseBody
    ResponseVo<Set<ParameterGroupDetail>> getPid() {
        logger.debug("查询父分类集合开始");
        try {
            Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
            if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
                return ResponseVo.getSuccessResponse(parameterGroupDetailList);
            }
        } catch (Exception e) {
            logger.error("查询父分类集合信息出现Exception异常：{}",e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("main.query.parent.error",null));
    }


    /**
     * 添加商品分类信息
     *
     * @param sicon
     * @param category
     * @return
     */
    @RequestMapping("/category/add")
    @ResponseBody
    public ResponseVo<Category> categoryAdd(@RequestParam(value = "file", required = false) MultipartFile sicon, Category category) {
        logger.debug("添加商品分类信息开始");
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            String merchantId = merchantInfo.getId();
            String merchantCode = merchantInfo.getScode();
            if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {
                category.setSmerchantId(merchantId);//商户ID
                category.setSmerchantCode(merchantCode);//商户编号
            }
            if (null != sicon && sicon.getSize() > 0) {//小图标上传
                String url = uploadHome(sicon, "brand");
                if (StringUtils.isNotBlank(url)) {
                    category.setSicon(url);//图片路径
                }
            }

            //sparentId字段为空，则该分类为父类
            String parentName = category.getSparentId();
            if (StringUtils.isNotBlank(parentName)) {   //子类
                // 小类增加编号
                String smallCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//商品小类编号
                if (StringUtils.isBlank(smallCode)) {
                    logger.error("生成小类编号出错，"+ MessageSourceUtils.getMessageByKey("main.merchant.number",null)+"：{0}，父类ID：{1}", merchantCode, parentName);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sp.category.add.small.error"));
                }
                category.setScode(smallCode);
                category.setIisParent(0);
            } else {    // 父类
                category.setIisParent(1);
                String mainCategory = category.getScategoryName();
                if (StringUtils.isNotBlank(mainCategory)) {
                    category.setScategoryCode(mainCategory.split("_")[0]);
                    category.setScategoryName(mainCategory.split("_")[1]);
                }
                // 大类增加编号
                String baiCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//商品大类编号
                if (StringUtils.isBlank(baiCode)) {
                    logger.error("生成大类编号出错，"+MessageSourceUtils.getMessageByKey("main.merchant.number",null)+"：{0}", merchantCode);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sp.category.add.big.error"));
                }
                category.setScode(baiCode);
            }
            category.setItype(10);  // 10通用，20商户
            category.setIdelFlag(0);/* 是否删除0否1是 */
            category.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            category.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
            category.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            category.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            //插入数据到商品分类信息表
            categoryService.insert(category);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.add.category.log",null), category.getSname());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.debug("添加商品分类信息结束", category.getId());
            return ResponseVo.getSuccessResponse(category);
        } catch (ServiceException e) {
            logger.error("添加商品分类信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("添加商品分类信息出现Exception异常：{}",e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.add.category.error",null));
    }

    /**
     * 删除商品分类信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/category/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> categoryDelete(String[] checkboxId) {
        logger.debug("删除商品分类信息开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                categoryService.batchLogicDelByIds(checkboxId, merchantId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.delete.category.log",null), "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除商品分类信息结束", checkboxId);
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("删除商品分类信息出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.delete.category.error",null));
    }


    /**
     * 商品分类信息编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/category/toEdit")
    public String categoryToEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品分类信息
                Category category = categoryService.selectByPrimaryKey(sid);
                if (null != category) {
                    MerchantInfo merchantInfo = merchantInfoService.selectOne(category.getSmerchantId());//商户信息
                    List<Category> bigCategoryList = categoryService.selectBigCategoryByMerchantId(category.getSmerchantId());//大类集合

                    // 数据字典配置主类名称
                    Set<ParameterGroupDetail> parameterGroupDetailList = GrpParaUtil.get(GrpParaUtil.MAIN_COMMODITY_CATEGORY);
                    if (CollectionUtils.isNotEmpty(parameterGroupDetailList)) {
                        map.put("dic", parameterGroupDetailList);
                    }

                    map.put("category", category);
                    map.put("bigCategoryList", bigCategoryList);
                    map.put("merchantInfo", merchantInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.modify.category.log",null), category.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("商品分类信息编辑按钮结束", category.getId());
                    return "sp/category/category-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑商品分类信息
     *
     * @param sicon
     * @param category
     * @return
     */
    @RequestMapping("/category/Edit")
    public @ResponseBody
    ResponseVo<Category> categoryEdit(@RequestParam(value = "file", required = false) MultipartFile sicon, Category category) {
        logger.debug("编辑商品分类信息开始");
        String sid = category.getId();
        String operater = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
        Date timeStamp = DateUtils.getCurrentDateTime();
        String merchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
        if (StringUtils.isNotBlank(sid)) {
            try {

                // 20180827为兼容老版本不存在小类编号问题
                Category categoryDomain = categoryService.selectByPrimaryKey(sid);
                if (null == categoryDomain) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.edit.category.error",null));
                }
                String smallCategoryCode = categoryDomain.getScode();
                Integer isParent = categoryDomain.getIisParent();
                if (null != isParent && 1 != isParent && StringUtils.isBlank(smallCategoryCode)) {  // 如果为空生成编号
                    String smallCode = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//商品小类编号
                    if (StringUtils.isBlank(smallCode)) {
                        logger.error("生成小类编号出错，"+MessageSourceUtils.getMessageByKey("main.merchant.number",null)+"：{0}，操作员：{1}", merchantCode, operater);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sp.category.edit.generating.code.error"));
                    }
                    category.setScode(smallCode);
                }

                if (null != sicon && sicon.getSize() > 0) {//小图标上传
                    String url = uploadHome(sicon, "brand");
                    if (StringUtils.isNotBlank(url)) {
                        category.setSicon(url);//图片路径
                    }
                }

                category.setTupdateTime(timeStamp);//修改时间
                category.setSupdateUser(operater);//修改人
                // 修改数据入商品分类信息表
                categoryService.updateBySelectiveVo(category);
                //操作日志
                String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.edit.category.log",null), categoryDomain.getSname());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("编辑商品分类信息结束", category.getId());
                return ResponseVo.getSuccessResponse(category);
            } catch (ServiceException e) {
                logger.error("编辑商品分类信息出现ServiceException异常：{}",e);
            } catch (Exception e) {
                logger.error("编辑商品分类信息出现Exception异常：{}",e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.edit.category.error",null));
    }

    /**
     * 查看商品分类信息详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/category/view")
    public String categoryView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品分类信息
                Category category = categoryService.selectByPrimaryKey(sid);
                if (null != category) {
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(category.getSmerchantId());//查询商户信息
                    Category categoryParent = categoryService.selectByPrimaryKey(category.getSparentId());//查询父ID信息
                    if (null != category.getSparentId()) {
                        Category category1 = categoryService.selectByPrimaryKey(category.getSparentId());
                        if (null != category1) {
                            category.setScategoryName(category1.getScategoryName());
                        }
                    }
                    map.put("category", category);
                    map.put("merchantInfo", merchantInfo);
                    map.put("categoryParent", categoryParent);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.view.category.log",null), category.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看商品分类信息详情页面结束", category.getId());
                    return "sp/category/category-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /* ----------3.商品分类结束 ----------*/

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


    /**
     * 初始化商品分类信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/category/initCategory")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> initCategory(String[] checkboxId) {
        logger.debug("删除商品分类信息开始");
        try {
            return categoryService.initCategory();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("删除商品分类信息出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.delete.category.error",null));
    }
}
