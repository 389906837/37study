package com.cloud.cang.mgr.hy.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.service.MbrPurviewService;
import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.MbrPurview;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 会员权限码管理
 * @author ChangTanchang
 * @time 2018-02-07 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
@Controller
@RequestMapping("/mbrPurview")
public class MbrPurviewController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(MbrPurviewController.class);

    // 注入serv类
    @Autowired
    private MbrPurviewService mbrPurviewService;
    
    @RequestMapping("/list")
    public String list() {
        return "hy/mbrPurview-list";
    }

    /**
     * 会员权限码列表数据
     * @param mbrPurviewVo
     * @param paramPage
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<MbrPurview>> queryDataPurview(ParamPage paramPage,MbrPurviewVo mbrPurviewVo) {
        PageListVo<List<MbrPurview>> pageListVo = new PageListVo<List<MbrPurview>>();
        Page<MbrPurview> page = new Page<MbrPurview>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            mbrPurviewVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = mbrPurviewService.selectPageAll(page, mbrPurviewVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 查出所有权限分配,已存在的根据角色标记
     * @param paramPage
     * @param mbrPurviewVo
     * @return
     */
    @RequestMapping("/queryDataByRole")
    public @ResponseBody ResponseVo<List<Map<String,String>>> queryAllDataByRole(ParamPage paramPage,MbrPurviewVo mbrPurviewVo){
        List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
        mapList = mbrPurviewService.selectAllByRole(mbrPurviewVo);
        return ResponseVo.getSuccessResponse(mapList.size(),mapList);
    }

    /**
     * 根据权限ID查询
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public @ResponseBody ResponseVo<MbrPurview> queryById(String id){
        MbrPurview mbrPurview = mbrPurviewService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(mbrPurview);
    }

    /**
     * 跳转添加/编辑页面
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String sid) {
        try {
            MbrPurview mbrPurview = mbrPurviewService.selectByPrimaryKey(sid);
            if (mbrPurview == null) {
                mbrPurview = new MbrPurview();
            }
            modelMap.put("mbrPurview", mbrPurview);
            return "hy/mbrPurview-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 添加/修改权限码
     * @param mbrPurview
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody ResponseVo<MbrPurview> save(MbrPurview mbrPurview){
        // 判断名称,URL,权限码不能重复
        MbrPurview mbr = mbrPurviewService.selectByExist(mbrPurview);
        if (mbr != null){
            if (mbr.getSpurCode().equals(mbrPurview.getSpurCode())){
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("权限码已存在！");
            }
            if (mbr.getSpurName().equals(mbrPurview.getSpurName())){
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("权限名称已存在！");
            }
            if (mbr.getSurlPath().equals(mbrPurview.getSurlPath())){
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("访问路径已存在");
            }
        }

        // 如果ID为空就添加
        if (StringUtils.isBlank(mbrPurview.getId())){
            mbrPurview.setSpurNo(CoreUtils.newCode(EntityTables.HY_MBR_PURVIEW));
            mbrPurview.setDaddDate(DateUtils.getCurrentDateTime());
            mbrPurview.setSpyName(StringUtil.getFullSpell(mbrPurview.getSpurName()));
            mbrPurview.setSjpName(StringUtil.getFirstSpell(mbrPurview.getSpurName()));
            mbrPurviewService.insert(mbrPurview);
            // 操作日志
            String logText= MessageFormat.format("新增会员权限码，名称{0},编号{1}",mbrPurview.getSpurName(),mbrPurview.getSpurNo() );
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        }else {
            // 修改
            MbrPurview oldmbrPurview = mbrPurviewService.selectByPrimaryKey(mbrPurview.getId());
            mbrPurview.setSpurNo(oldmbrPurview.getSpurNo());
            mbrPurview.setSpyName(StringUtil.getFullSpell(mbrPurview.getSpurName()));
            mbrPurview.setSjpName(StringUtil.getFirstSpell(mbrPurview.getSpurName()));
            mbrPurviewService.updateBySelective(mbrPurview);
            // 操作日志
            String logText=MessageFormat.format("修改会员权限码，名称{0},编号{1}",mbrPurview.getSpurName(),mbrPurview.getSpurNo() );
            LogUtil.addOPLog(LogUtil.AC_EDIT,logText, null);
        }
        return ResponseVo.getSuccessResponse(mbrPurview);
    }

    /**
     * 根据ID删除权限码
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody ResponseVo<String> delete(String [] checkboxId){
        mbrPurviewService.delete(checkboxId);
        return ResponseVo.getSuccessResponse();
    }
}
