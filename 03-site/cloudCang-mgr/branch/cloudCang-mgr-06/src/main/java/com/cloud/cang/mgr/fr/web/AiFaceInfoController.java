package com.cloud.cang.mgr.fr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.fr.domain.FaceRegisterInfoDomain;
import com.cloud.cang.mgr.fr.service.FaceRegisterInfoService;
import com.cloud.cang.mgr.fr.vo.FaceRegisterInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
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

import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @ClassName AiFaceInfoController
 * @Description AI人脸信息管理controller
 * @Author zengzexiong
 * @Date 2018年8月2日14:35:11
 */
@Controller
@RequestMapping("/aiFaceInfo")
public class AiFaceInfoController {

    private static final Logger logger = LoggerFactory.getLogger(AiFaceInfoController.class);


    @Autowired
    FaceRegisterInfoService faceRegisterInfoService;

    @Autowired
    OperatorService operatorService;

    /**
     * AI设备注册信息列表
     * @return
     */
    @RequestMapping("/list")
    public String aiFaceInforList() {
        return "fr/aiFaceInfo/aiFaceInfo-list";
    }


    /**
     * 人脸注册信息列表数据
     *
     * @param faceRegisterInfoVo 初始化页面对象
     * @param paramPage        初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<FaceRegisterInfoDomain>> aiFaceInfoQueryData(FaceRegisterInfoVo faceRegisterInfoVo, ParamPage paramPage) {

        PageListVo<List<FaceRegisterInfoDomain>> pageListVo = new PageListVo<List<FaceRegisterInfoDomain>>();//返回的页面对象
        Page<FaceRegisterInfoDomain> page = new Page<FaceRegisterInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        faceRegisterInfoVo.setIdelFlag(0);/* 是否删除0否1是 */


        if (null == faceRegisterInfoVo) {
            faceRegisterInfoVo = new FaceRegisterInfoVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        faceRegisterInfoVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            faceRegisterInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = faceRegisterInfoService.selectPageByDomainWhere(page, faceRegisterInfoVo);


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
     * 查看商品品牌详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toView")
    public String aiFaceInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该商品品牌信息
                FaceRegisterInfo faceRegisterInfo= faceRegisterInfoService.selectByPrimaryKey(sid);
                if (null != faceRegisterInfo) {
                    map.put("faceRegisterInfo", faceRegisterInfo);
                    String logText4 = MessageFormat.format("查看人脸信息，人脸编号{0}", faceRegisterInfo.getSfaceCode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看查看人脸信息页面结束，人脸编号", faceRegisterInfo.getSfaceCode());
                    return "fr/aiFaceInfo/aiFaceInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 删除设备信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                ResponseVo responseVo = faceRegisterInfoService.deleteFaceInfo(checkboxId);
                //操作日志
                String logText = MessageFormat.format("删除人脸注册信息，人脸编号：{0}", responseVo.getData().toString());
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                return responseVo;
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常删除人脸注册信息失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除人脸注册信息异常！");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除人脸注册信息失败！");
    }


}
