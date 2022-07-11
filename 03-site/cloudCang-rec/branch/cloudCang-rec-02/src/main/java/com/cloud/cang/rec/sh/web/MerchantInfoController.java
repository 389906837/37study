package com.cloud.cang.rec.sh.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.sh.domain.MerchantInfoDomain;
import com.cloud.cang.rec.sh.service.MerchantInfoService;
import com.cloud.cang.rec.sh.vo.MerchantInfoVo;
import com.cloud.cang.rec.sh.vo.SelectMerchantVo;
import com.cloud.cang.rec.sys.service.OperatorService;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName MerchantInfoController
 * @Description 商户Controller
 * @Author yanlingfeng
 * @Date 2018年9月3日10:00:00
 */
@Controller
@RequestMapping("/merchantInfo")
public class MerchantInfoController {
    @Autowired
    OperatorService operatorService;
    @Autowired
    MerchantInfoService merchantInfoService;
    private static final Logger log = LoggerFactory.getLogger(MerchantInfoController.class);

    @RequestMapping("selectMerchant")
    public String selectMerchant() {
        return "sh/merchantInfo/merchantInfo-select-list";
    }

    /**
     * 选择商户
     *
     * @param:
     */
    @RequestMapping("/selectMultipleMerchant")
    public String selectMultipleMerchant(String commodityIds, String commodityCodes, ModelMap map) {
        try {
            log.debug("选择商户页面:{}", commodityIds);
            //签约状态map
            Map<Integer, String> icompanyStatusMap = BizTypeDefinitionEnum.IcompanyStatus.getValue();
            map.put("icompanyStatusMap", icompanyStatusMap);
            if (StringUtil.isNotBlank(commodityIds)) {
                map.put("commodityIds", commodityIds + ",");
                map.put("commodityCodes", commodityCodes + ",");
                map.put("selectNums", commodityIds.split(",").length);//选择个数
            }
            return "sh/merchantInfo/merchantInfo-selectMultiple-list";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 商户管理列表数据
     *
     * @param paramPage 参数用于初始化分页大小
     * @return selectMerchantInfo 选择已签约商户使用
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<MerchantInfoVo>> queryData(MerchantInfoDomain merchantInfoDomain, ParamPage paramPage, String selectMerchantInfo) {

        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 10);
        if ("true".equals(selectMerchantInfo)) {
            merchantInfoDomain.setIstatus(20);
        }
        merchantInfoDomain.setCondition(sql);
        PageListVo<List<MerchantInfoVo>> pageListVo = new PageListVo<List<MerchantInfoVo>>();
        Page<MerchantInfoVo> page = new Page<MerchantInfoVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        merchantInfoDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            merchantInfoDomain.setOrderStr("shi." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = merchantInfoService.selectPageByDomainWhere(page, merchantInfoDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 获取商户信息
     *
     * @param: merchantCodes 商户Code集合
     */
    @RequestMapping("/getMerchantByIds")
    @ResponseBody
    ResponseVo<List<SelectMerchantVo>> getMerchantByIds(String[] merchantIds) {
        List<SelectMerchantVo> sddList = null;
        if (null != merchantIds) {
            sddList = new ArrayList<SelectMerchantVo>();
            MerchantInfo temp = null;
            SelectMerchantVo sdd = null;
            for (String id : merchantIds) {//循环设备ID
                temp = merchantInfoService.selectByPrimaryKey(id);
                if (null != temp) {
                    sdd = new SelectMerchantVo();
                    sdd.setMerchantId(temp.getId());
                    sdd.setMerchantCode(temp.getScode());
                    sdd.setMerchantName(temp.getSname());
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }
}
