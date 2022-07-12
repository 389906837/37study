package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.domain.ParameterGroupDetailDomain;
import com.cloud.cang.core.sys.service.ParameterGroupDetailService;
import com.cloud.cang.core.sys.service.ParametergroupService;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.domain.MerchantInfoDomain;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.ObjectUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据字典参数明细管理
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/paramGroupDetail/{groupNo}")
public class ParamGroupDetailController {
    
    @Autowired
    ParameterGroupDetailService parameterGroupDetailService;
    
    @Autowired
    ParametergroupService parametergroupService;
    private static final Logger log = LoggerFactory.getLogger(ParamGroupDetailController.class);

    /**
     * 数据字典参数管理
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView paramGroup(@PathVariable String groupNo) {
        Parametergroup parametergroup= parametergroupService.selectByGroupNo(groupNo);
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("groupNo", groupNo);
        map.put("sremark", parametergroup.getSremark().replace("\n", "    "));
        map.put("bisModify", parametergroup.getBisModify());
        return new ModelAndView("sys/parametergroupDetail/parametergroupDetail-list",map);
    }
    
    
    /**
     * 查询数据字典参数明细
     * @return
     */
    @RequestMapping("/queryParamGroupDetail")
    public @ResponseBody
    PageListVo<List<ParameterGroupDetail>> queryParamGroupDetail(@PathVariable String groupNo, ParameterGroupDetailDomain  parameterGroupDetailDomain, ParamPage paramPage) {
        PageListVo<List<ParameterGroupDetail>> pageListVo = new PageListVo<List<ParameterGroupDetail>>();
        Page<ParameterGroupDetail> page = new Page<ParameterGroupDetail>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            parameterGroupDetailDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page= parameterGroupDetailService.selectByQueryItems(groupNo,parameterGroupDetailDomain,page);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid,@PathVariable String groupNo) {
        try{
        ParameterGroupDetail parameterGroupDetail = parameterGroupDetailService.selectByPrimaryKey(sid);
        if (parameterGroupDetail == null) {
            parameterGroupDetail = new ParameterGroupDetail();
        }
        map.put("parameterGroupDetail", parameterGroupDetail);
        return "sys/parametergroupDetail/parametergroupDetail-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }
    /**
     * 修改增加数据字典参数明细
     *
     * @return
     */
    @RequestMapping("/save")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<ParameterGroupDetail> save(ParameterGroupDetail parameterGroupDetail,@PathVariable String groupNo) {
        // 执行新增
        if (StringUtils.isBlank(parameterGroupDetail.getId())) {

            Parametergroup parametergroup= parametergroupService.selectByGroupNo(groupNo);
            parameterGroupDetail.setSgroupid(parametergroup.getId());
            ParameterGroupDetail detail=parameterGroupDetailService.selectByExist(parameterGroupDetail);
            if (detail!=null){
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("明细名称已存！");
            }
            parameterGroupDetailService.insert(parameterGroupDetail);
            //操作日志
           /* String logText = MessageFormat.format("新增菜单，编号{0}", menu.getSmenuNo());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);*/
            return ResponseVo.getSuccessResponse();
        } else// 执行修改
        {
            Parametergroup parametergroup= parametergroupService.selectByGroupNo(groupNo);
            parameterGroupDetail.setSgroupid(parametergroup.getId());
            //添加或者修改时，要做 【参数名称】、【参数值】 的唯一性校验，不能重复
            ParameterGroupDetail detail=parameterGroupDetailService.selectByExist(parameterGroupDetail);
            if (detail!=null){
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("明细名称已存！");
            }
            parameterGroupDetailService.updateByPrimaryKey(parameterGroupDetail);
            return ResponseVo.getSuccessResponse();
        }
    }

        /**
         * 批量删除数据字典参数明细
         * @param checkboxId
         * @return
         */
        @SuppressWarnings("unchecked")
        @RequestMapping("/delete")
        public @ResponseBody ResponseVo delParamGroupDetail(@PathVariable String groupNo,String[] checkboxId) {
            parameterGroupDetailService.batchDeleteByIds(checkboxId);
            return ResponseVo.getSuccessResponse();
        }
    
}
