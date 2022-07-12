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
import com.cloud.cang.mgr.sp.domain.LabelInfoDomain;
import com.cloud.cang.mgr.sp.service.*;
import com.cloud.cang.mgr.sp.vo.LabelInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.LabelInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
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
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @ClassName CommodityController
 * @Description 商品管理controller
 * @Author zengzexiong
 * @Date 2018年6月3日14:01:24
 */
@Controller
@RequestMapping("/commodity")
public class LabelInfoController extends GenericController {
    private static final Logger logger = LoggerFactory.getLogger(LabelInfoController.class);


    @Autowired
    private MerchantInfoService merchantInfoService;//商户信息表

    @Autowired
    private OperatorService operatorService;//操作员信息表


    @Autowired
    private LabelInfoService labelInfoService;//商品标签


     /* ----------2.商品标签开始 ----------*/

    /**
     * 商品标签列表
     *
     * @return
     */
    @RequestMapping("/labelInfo/list")
    public String labelInfoList() {
        return "sp/label/labelInfo-list";
    }


    /**
     * 商品标签列表数据
     *
     * @param labelInfoVo 初始化页面对象
     * @param paramPage   初始化分页对象
     * @return
     */
    @RequestMapping("/labelInfo/queryData")
    @ResponseBody
    public PageListVo<List<LabelInfoDomain>> queryLabelData(LabelInfoVo labelInfoVo, ParamPage paramPage) {
        PageListVo<List<LabelInfoDomain>> pageListVo = new PageListVo<List<LabelInfoDomain>>();//返回的页面对象
        Page<LabelInfoDomain> page = new Page<LabelInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == labelInfoVo) {
            labelInfoVo = new LabelInfoVo();
        }
        labelInfoVo.setIdelFlag(0);//是否删除：0否，1是

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 60);//60 商品列表
        labelInfoVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            labelInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = labelInfoService.selectPageByDomainWhere(page, labelInfoVo);


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
     * 添加商品标签按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/labelInfo/toAdd")
    public String labelToAdd(ModelMap map) {
        //new 对象
        LabelInfo labelInfo = new LabelInfo();
        map.put("labelInfo", labelInfo);
        return "sp/label/labelInfo-toAdd";
    }

    /**
     * 查询父标签集合
     *
     * @param pid 标签ID（增加的时候没有，修改的时候有）
     * @return
     */
    @RequestMapping("/labelInfo/getParentLabel")
    public @ResponseBody
    ResponseVo<List<LabelInfo>> getParentLabel(String pid) {
        logger.debug("查询父标签集合开始");
        try {
            if (StringUtils.isNotBlank(pid)) {
                MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
                String merchantId = merchantInfo.getId();
                if (StringUtils.isNotBlank(merchantId)) {
                    List<LabelInfo> labelInfoList = labelInfoService.selectParentLabelByMerchantId(merchantId);//查询所有父标签
                    if ("add".equals(pid)) {
                        logger.debug("新增查询父标签集合结束");
                        return ResponseVo.getSuccessResponse(labelInfoList);
                    } else {
                        LabelInfo tempLabel = null;//临时标签对象
                        for (LabelInfo label : labelInfoList) {
                            if (label.getId().equals(pid)) {
                                tempLabel = label;
                            }
                        }
                        labelInfoList.remove(tempLabel);//修改时移除当前标签
                        logger.debug("修改查询父标签集合结束");
                        return ResponseVo.getSuccessResponse(labelInfoList);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询父标签集合出现Exception异常：{}", e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.label.parent.query",null));
    }


    /**
     * 添加商品标签
     *
     * @param labelInfo
     * @return
     */
    @RequestMapping("/labelInfo/add")
    @ResponseBody
    public ResponseVo<LabelInfo> labelAdd(LabelInfo labelInfo) {
        logger.debug("<=== 添加商品标签开始===>");
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectOne(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());//根据操作员查询对应商户信息
            String merchantId = merchantInfo.getId();//商户ID
            String merchantCode = merchantInfo.getScode();//商户编号
            if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {
                labelInfo.setSmerchantId(merchantId);
                labelInfo.setSmerchantCode(merchantCode);
            }
            labelInfo.setIdelFlag(0);/* 是否删除0否1是 */
            labelInfo.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            labelInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
            labelInfo.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            labelInfo.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            //插入数据到商品标签信息表
            labelInfoService.insert(labelInfo);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.add.label.log",null)+"，"+ MessageSourceUtils.getMessageByKey("main.name",null)+"{0}", labelInfo.getSname());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.info("添加商品标签结束：{}", labelInfo.getId());
            return ResponseVo.getSuccessResponse(labelInfo);
        } catch (ServiceException e) {
            logger.error("添加商品标签出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("添加商品标签出现Exception异常：{}", e);
        }
        return new ResponseVo<>(false, 111, MessageSourceUtils.getMessageByKey("spcon.add.label.error",null));
    }

    /**
     * 删除商品标签信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/labelInfo/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> labelDelete(String[] checkboxId) {
        logger.debug("删除商品标签信息开始");
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                labelInfoService.batchLogicDelByIds(checkboxId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.delete.label.log",null), "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.debug("删除商品标签信息结束");
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            logger.error("系统异常删除商品标签出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.delete.label.error",null));
    }


    /**
     * 商品标签编辑按钮
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/labelInfo/toEdit")
    public String labeltoEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品品牌信息
                LabelInfo labelInfo = labelInfoService.selectByPrimaryKey(sid);
                if (null != labelInfo) {
                    List<LabelInfo> labelInfoList = labelInfoService.selectParentLabelByMerchantId(labelInfo.getSmerchantId());//标签
                    map.put("labelInfo", labelInfo);
                    map.put("labelInfoList", labelInfoList);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.modify.label.log",null), labelInfo.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("商品标签编辑按钮结束", labelInfo.getId());
                    return "sp/label/labelInfo-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑商品标签信息
     *
     * @param labelInfo
     * @return
     */
    @RequestMapping("/labelInfo/Edit")
    public @ResponseBody
    ResponseVo<LabelInfo> labeltoEdit(LabelInfo labelInfo) {
        logger.debug("编辑商品标签信息开始");
        String sid = labelInfo.getId();
        if (StringUtils.isNotBlank(sid)) {
            try {
                labelInfo.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
                labelInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                // 修改数据入商品标签信息表
                labelInfoService.updateBySelectiveVo(labelInfo);
                //操作日志
                String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.edit.label.log",null), labelInfo.getSname());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("编辑商品标签信息结束", labelInfo.getId());
                return ResponseVo.getSuccessResponse(labelInfo);
            } catch (ServiceException e) {
                logger.error("编辑商品标签信息出现ServiceException异常：{}", e);
            } catch (Exception e) {
                logger.error("编辑商品标签信息出现Exception异常：{}", e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.edit.label.error",null));
    }


    /**
     * 查看商品标签页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/labelInfo/view")
    public String labelInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品标签信息
                LabelInfo labelInfo = labelInfoService.selectByPrimaryKey(sid);
                if (null != labelInfo) {
                    MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(labelInfo.getSmerchantId());
                    map.put("labelInfo", labelInfo);
                    map.put("merchantInfo", merchantInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("spcon.view.label.log",null), labelInfo.getSname());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看商品标签页面结束", labelInfo.getId());
                    return "sp/label/labelInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /* ----------2.商品标签结束 ----------*/




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
