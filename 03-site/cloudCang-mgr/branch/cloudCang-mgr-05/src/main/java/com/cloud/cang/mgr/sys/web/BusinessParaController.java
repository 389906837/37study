package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.domain.BusinessParameterDomain;
import com.cloud.cang.core.sys.service.BusinessParameterService;
import com.cloud.cang.core.sys.vo.BusinessParameterVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.model.sys.BusinessParameter;
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

import java.text.MessageFormat;
import java.util.List;

/**
 * 运营参数管理控制层
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/businessPara")
public class BusinessParaController {

    private static final Logger log = LoggerFactory.getLogger(BusinessParaController.class);

    @Autowired
    BusinessParameterService businessParameterService;


    @RequestMapping("/list")
    public String list() {
        return "sys/businessPara/businessPara-list";
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            BusinessParameter businessParameter = businessParameterService.selectByKey(sid);
            if (businessParameter == null) {
                businessParameter = new BusinessParameterDomain();
            }
            map.put("businessParameter", businessParameter);
            return "sys/businessPara/businessPara-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 查询运营参数
     *
     * @return
     */
    @RequestMapping("/query")
    public @ResponseBody
    PageListVo<List<BusinessParameter>> query(ParamPage paramPage, BusinessParameterVo businessParameterDomain) {
        PageListVo<List<BusinessParameter>> pageListVo = new PageListVo<List<BusinessParameter>>();
        Page<BusinessParameter> page = new Page<BusinessParameter>();

        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            businessParameterDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = businessParameterService.queryPage(page, businessParameterDomain);

        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 批量删除运营参数
     *
     * @param checkboxId
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            businessParameterService.batchDeleteByIds(checkboxId);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.delete.business.para",null)+"{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("批量删除运营参数异常：{}",e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.system.error",null));
        }
    }

    /**
     * 保存或修改运营参数
     *
     * @param businessParameter
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<BusinessParameter> save(BusinessParameter businessParameter) {
        try {
            // 如果id为空就就添加
            if (StringUtils.isBlank(businessParameter.getId())) {
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.add.business.para",null)+"，"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", businessParameter.getId());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                businessParameterService.insert(businessParameter);
            } else {// 修改
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.modify.business.para",null)+"，"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", businessParameter.getId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                businessParameterService.updateByPrimaryKey(businessParameter);
            }
        } catch (Exception ex) {
            log.error("保存或修改运营参数异常：{}",ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.save.business.para.error",null));
        }
        return ResponseVo.getSuccessResponse(businessParameter);
    }
}
