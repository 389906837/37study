package com.cloud.cang.rec.op.web;

import com.alipay.api.internal.util.WebUtils;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.InterfaceAcceptDomain;
import com.cloud.cang.rec.op.service.InterfaceAcceptService;
import com.cloud.cang.rec.op.vo.InterfaceAcceptVo;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台接口业务受理信息表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/interfaceAccept")
public class InterfaceAcceptController {

    private static final Logger log = LoggerFactory.getLogger(InterfaceAcceptController.class);

    @Autowired
    private InterfaceAcceptService interfaceAcceptService;

    /**
     * 平台接口业务受理信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/interfaceAccept/interfaceAccept-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param interfaceAcceptDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<InterfaceAcceptVo>> queryFData(ParamPage paramPage, InterfaceAcceptDomain interfaceAcceptDomain) {
        PageListVo<List<InterfaceAcceptVo>> pageListVo = new PageListVo<List<InterfaceAcceptVo>>();

        Page<InterfaceAcceptVo> page = new Page<InterfaceAcceptVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        interfaceAcceptDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            interfaceAcceptDomain.setOrderStr("OIA." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = interfaceAcceptService.selectPageByDomainWhere(page, interfaceAcceptDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 删除平台接口业务受理信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            interfaceAcceptService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除平台接口业务受理信息，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除平台接口业务受理信息异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 主动通知
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/activeNotify")
    @ResponseBody
    public ResponseVo activeNotify(String checkboxId) throws Exception {
        try {
            return interfaceAcceptService.activeNotify(checkboxId);
        } catch (Exception e) {
            log.error("接口账户主动通知异常：{}",checkboxId);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("主动通知失败！");
        }
    }

    /**
     * 查看详情
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String view(String sid, ModelMap map) {
        try {
            InterfaceAccept interfaceAccept = interfaceAcceptService.selectByPrimaryKey(sid);
            map.put("interfaceAccept", interfaceAccept);
            return "op/interfaceAccept/interfaceAccept-view";
        } catch (Exception e) {
            log.error("查看平台接口业务受理信息详情异常：{}", e);
            return ExceptionUtil.to500();
        }
    }
}

