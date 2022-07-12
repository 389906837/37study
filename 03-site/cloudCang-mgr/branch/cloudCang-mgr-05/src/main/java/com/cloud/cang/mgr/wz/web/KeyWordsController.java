package com.cloud.cang.mgr.wz.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.wz.service.KeyWordsService;
import com.cloud.cang.mgr.wz.vo.KeyWordsVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.wz.KeyWords;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
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
 * @description 网站关键字
 * @author ChangTanchang
 * @time 2018-03-29 09:17:15
 * @fileName KeyWordsController.java
 * @version 1.0
 */
@Controller
@RequestMapping("/keyWords")
public class KeyWordsController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(KeyWordsController.class);

    @Autowired
    private KeyWordsService keyWordsService;

    @Autowired
    private OperatorService operatorService;

    // 返回查询列表页
    @RequestMapping("/list")
    public String list() {
        return "wz/keyWords-list";
    }

    /**
     * 查询网页关键字
     * @param paramPage
     * @param keyWordsVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<KeyWords>> queryKeyWords(ParamPage paramPage, KeyWordsVo keyWordsVo) {
        PageListVo<List<KeyWords>> pageListVo = new PageListVo<List<KeyWords>>();
        Page<KeyWords> page = new Page<KeyWords>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        keyWordsVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            keyWordsVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = keyWordsService.selectKeyWordsAll(page, keyWordsVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 跳转到编辑页面
     * @param modelMap
     * @param kid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap,String kid){
        try {
                KeyWords keyWords = keyWordsService.selectByPrimaryKey(kid);
                if (keyWords == null){
                    keyWords = new KeyWords();
                }
                modelMap.put("keyWords", keyWords);
            return "wz/keyWords-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}",e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 保存/修改关键字
     * @param keyWords
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<KeyWords> save(KeyWords keyWords) {
        // 如果ID为空就添加
        if (StringUtils.isBlank(keyWords.getId())) {
            keyWords.setSpageNo(CoreUtils.newCode(EntityTables.WZ_KEY_WORDS));
            keyWords.setIdelFlag(0);
            keyWords.setTaddTime(DateUtils.getCurrentDateTime());
            keyWords.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            keyWords.setTupdateTime(DateUtils.getCurrentDateTime());
            keyWords.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            keyWordsService.insert(keyWords);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.add.keyword.name",null)+"{0},"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{1}", keyWords.getSpageName(), keyWords.getSpageNo());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            KeyWords oldKeyWords = keyWordsService.selectByPrimaryKey(keyWords.getId());
            keyWords.setSpageNo(oldKeyWords.getSpageNo());
            keyWords.setIdelFlag(0);
            keyWords.setTupdateTime(DateUtils.getCurrentDateTime());
            keyWords.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            keyWordsService.updateBySelective(keyWords);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.modify.keyword.name",null)+"{0},"+MessageSourceUtils.getMessageByKey("main.code",null)+"{1}", keyWords.getSpageName(), keyWords.getSpageNo());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

        }
        return ResponseVo.getSuccessResponse(keyWords);
    }

    /**
     * 根据ID数据删除网站关键字
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            keyWordsService.delete(checkboxId);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.system.delete.error",null));
    }
}