package com.cloud.cang.mgr.sp.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.BrandInfoDomain;
import com.cloud.cang.mgr.sp.service.*;
import com.cloud.cang.mgr.sp.vo.BrandInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sys.Operator;
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
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName BrandInfoController
 * @Description 商品品牌管理controller
 * @Author zengzexiong
 * @Date 2018年6月3日14:01:19
 */
@Controller
@RequestMapping("/commodity")
public class BrandInfoController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(BrandInfoController.class);


    @Autowired
    private MerchantInfoService merchantInfoService;//商户信息表

    @Autowired
    private OperatorService operatorService;//操作员信息表

    @Autowired
    private BrandInfoService brandInfoService;//商品品牌


 /* ----------1.设备商品品牌开始 ----------*/

    /**
     * 商品品牌列表
     *
     * @return
     */
    @RequestMapping("/brandInfo/list")
    public String brandInfoList() {
        return "sp/brand/brandInfo-list";
    }


    /**
     * 查询商品品牌列表数据
     *
     * @param brandInfoVo 初始化页面对象
     * @param paramPage   初始化分页对象
     * @return
     */
    @RequestMapping("/brandInfo/queryData")
    @ResponseBody
    public PageListVo<List<BrandInfoDomain>> queryData(BrandInfoVo brandInfoVo, ParamPage paramPage) {
        PageListVo<List<BrandInfoDomain>> pageListVo = new PageListVo<List<BrandInfoDomain>>();//返回的页面对象
        Page<BrandInfoDomain> page = new Page<BrandInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == brandInfoVo) {
            brandInfoVo = new BrandInfoVo();
        }
        brandInfoVo.setIdelFlag(0);//是否删除：0否，1是

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        brandInfoVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            brandInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = brandInfoService.selectPageByDomainWhere(page, brandInfoVo);


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
     * 添加商品品牌按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/brandInfo/toAdd")
    public String brandToAdd(ModelMap map) {
        //new 对象
        BrandInfo brandInfo = new BrandInfo();
        map.put("brandInfo", brandInfo);
        return "sp/brand/brandInfo-toAdd";
    }

    /**
     * 添加商品品牌
     *
     * @param brandInfo
     * @return
     */
    @RequestMapping("/brandInfo/add")
    @ResponseBody
    public ResponseVo<BrandInfo> commodityAdd(@RequestParam(value = "file", required = false) MultipartFile brandLogo, BrandInfo brandInfo) {
        logger.debug("<=== 添加商品品牌开始===>");
        try {
            String brandName = brandInfo.getSname();
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
            if (StringUtils.isBlank(brandName)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.brand.name.empty",null));
            }
            // 查询品牌名是否存在
            BrandInfo brandInfoVo = new BrandInfo();
            brandInfoVo.setSname(brandName);
            brandInfoVo.setIdelFlag(0);
            List<BrandInfo> brandInfoList = brandInfoService.selectByEntityWhere(brandInfoVo);
            if (CollectionUtils.isNotEmpty(brandInfoList)) {
                return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.brand.name.exist",null));
            }

            // 设置品牌属性
            if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {
                brandInfo.setSmerchantId(merchantId);//商户ID
                brandInfo.setSmerchantCode(merchantCode);//商户编号
            }
            if (null != brandLogo && brandLogo.getSize() > 0) {//图片上传
                String url = uploadHome(brandLogo, "brand");
                if (StringUtils.isNotBlank(url)) {
                    brandInfo.setSlogo(url);//图片路径
                }
            }
            brandInfo.setIdelFlag(0);/* 是否删除0否1是 */
            brandInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            brandInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人人
            brandInfo.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            brandInfo.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            //插入数据到商品品牌信息表
            brandInfoService.insert(brandInfo);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.add.brand.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", brandInfo.getSname());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.info("添加商品品牌结束：{}", brandInfo.getId());
            return ResponseVo.getSuccessResponse(brandInfo);
        } catch (ServiceException e) {
            logger.error("添加商品品牌信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加商品品牌信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.add.brand.error",null));
    }

    /**
     * 删除商品品牌信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/brandInfo/delete")
    @ResponseBody
    public ResponseVo<String> brandInfoDelete(String[] checkboxId) {
        logger.debug("删除商品品牌信息开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID); // 商户缓存
                // 如果有商品正在使用此品牌，不能删除
                ResponseVo<String> responseVo = brandInfoService.batchLogicDelByIds(checkboxId, merchantId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.delete.brand.log",null), "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除商品品牌信息结束");
                return responseVo;
            }
        } catch (ServiceException e) {
            logger.info("品牌被视觉商品使用，无法删除");
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("删除商品品牌信息出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.delete.brand.error",null));
    }

    /**
     * 商品品牌编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/brandInfo/toEdit")
    public String brandInfotoEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品品牌信息
                BrandInfo brandInfo = brandInfoService.selectByPrimaryKey(sid);
                if (null != brandInfo) {
                    map.put("brandInfo", brandInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.modify.brand.log",null), brandInfo.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("商品品牌编辑按钮结束");
                    return "sp/brand/brandInfo-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑商品品牌信息
     *
     * @param brandInfo
     * @return
     */
    @RequestMapping("/brandInfo/Edit")
    public @ResponseBody
    ResponseVo<BrandInfo> brandInfotoEdit(@RequestParam(value = "file", required = false) MultipartFile brandLogo, BrandInfo brandInfo) {
        logger.debug("编辑商品品牌信息开始");
        String sid = brandInfo.getId();
        String brandName = brandInfo.getSname();
        if (StringUtils.isNotBlank(sid) && StringUtils.isNotBlank(brandInfo.getSname())) {
            try {
                // 查询品牌名是否存在
                BrandInfo brandInfoVo = new BrandInfo();
                brandInfoVo.setSname(brandName);
                brandInfoVo.setIdelFlag(0);
                List<BrandInfo> brandInfoList = brandInfoService.selectByEntityWhere(brandInfoVo);
                if (CollectionUtils.isNotEmpty(brandInfoList)) {
                    return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.brand.name.exist",null));
                }


                // 修改数据入商品品牌信息表
                brandInfoService.updateAndSynchToCommodity(brandInfo, brandLogo);
                //操作日志
                String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.edit.brand.log",null)+"，"+ MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", brandInfo.getSname());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("编辑商品品牌信息结束", brandInfo.getId());
                return ResponseVo.getSuccessResponse(brandInfo);
            } catch (ServiceException e) {
                logger.error("编辑商品品牌信息出现ServiceException异常：{}", e);
            } catch (Exception e) {
                logger.error("编辑商品品牌信息出现Exception异常：{}", e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.modify.brand.error",null));
    }

    /**
     * 查看商品品牌详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/brandInfo/view")
    public String brandInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品品牌信息
                BrandInfo brandInfo = brandInfoService.selectByPrimaryKey(sid);
                if (null != brandInfo) {
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(brandInfo.getSmerchantId());
                    map.put("brandInfo", brandInfo);
                    map.put("merchantInfo", merchantInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.view.brand.log",null)+"，"+MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", brandInfo.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看商品品牌详情页面结束", brandInfo.getId());
                    return "sp/brand/brandInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /* ----------1.商品品牌结束 ----------*/


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
