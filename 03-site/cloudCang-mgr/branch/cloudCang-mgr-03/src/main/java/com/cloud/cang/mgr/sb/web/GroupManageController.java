package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.domain.GroupManageDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.GroupManageService;
import com.cloud.cang.mgr.sb.vo.GroupManageVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @ClassName GroupManageController
 * @Description 设备分组管理controller
 * @Author zengzexiong
 * @Date 2018年1月24日15:10:20
 */
@Controller
@RequestMapping("/device")
public class GroupManageController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(GroupManageController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    DeviceInfoService deviceInfoService;//0.设备基础信息

    @Autowired
    GroupManageService groupManageService;//6.设备分组管理

     /* ----------6.设备分组管理开始 ----------*/

    /**
     * 设备分组信息列表
     *
     * @return
     */
    @RequestMapping("/groupManage/list")
    public String groupManageList() {
        return "sb/groupManage/groupManage-list";
    }

    /**
     * 设备分组信息列表数据
     * @param groupManageVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/groupManage/queryData")
    @ResponseBody
    public PageListVo<List<GroupManageDomain>> queryData(GroupManageVo groupManageVo, ParamPage paramPage) {
        PageListVo<List<GroupManageDomain>> pageListVo = new PageListVo<List<GroupManageDomain>>();//返回的页面对象
        Page<GroupManageDomain> page = new Page<GroupManageDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (groupManageVo == null) {
            groupManageVo = new GroupManageVo();
        }

        //生成查询条件
   /*     Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 150); //150 设备分组列表
        groupManageVo.setQueryCondition(queryCondition);
*/

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            groupManageVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = groupManageService.selectPageByDomainWhere(page, groupManageVo);


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
     * 设备分组信息列表数据
     * @param groupManageVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/groupManage/queryMerchantGroupData")
    @ResponseBody
    public PageListVo<List<GroupManageDomain>> queryMerchantGroupData(GroupManageVo groupManageVo, ParamPage paramPage) {
        PageListVo<List<GroupManageDomain>> pageListVo = new PageListVo<List<GroupManageDomain>>();//返回的页面对象
        Page<GroupManageDomain> page = new Page<GroupManageDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (groupManageVo == null) {
            groupManageVo = new GroupManageVo();
        }
        String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
        groupManageVo.setSmerchantId(merchantId);
        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            groupManageVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = groupManageService.selectPageByDomainWhere(page, groupManageVo);


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
     * 增加设备分组按钮跳转页面
     * @param map
     * @return
     */
    @RequestMapping("/groupManage/toAdd")
    public String groupManageToAdd(ModelMap map) {
        //new 对象
        GroupManage groupManage = new GroupManage();//设备分组表
        map.put("groupManage", groupManage);
        return "sb/groupManage/groupManage-toAdd";
    }

    /**
     * 增加设备分组信息
     * @param groupManage
     * @return
     */
    @RequestMapping("/groupManage/add")
    @Transactional
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<GroupManage> groupManageAdd(GroupManage groupManage) {
        String sgroupName = groupManage.getSgroupName();//组名
        try {
            if (StringUtils.isNotBlank(sgroupName)) {
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID); // 商户ID
                String merchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE); // 商户编号
                //设置属性
                if (StringUtils.isNotBlank(merchantId)) {
                    groupManage.setSmerchantId(merchantId);
                }
                if (StringUtils.isNotBlank(merchantCode)) {
                    groupManage.setSmerchantCode(merchantCode);
                }
                groupManage.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
                groupManage.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                groupManage.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
                groupManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
                //插入数据到设备分组表
                groupManageService.insert(groupManage);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.add.device.group"), groupManage.getSgroupName());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                logger.info("新增设备分组：{}",groupManage.getId());
                return ResponseVo.getSuccessResponse(groupManage);
            }
        } catch (ServiceException e) {
            logger.error("增加设备分组信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("增加设备分组信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.groupmanage.add.error"));
    }

    /**
     * 删除设备分组信息
     * @param checkboxId
     * @return
     */
    @RequestMapping("/groupManage/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody ResponseVo<String> groupManageDelete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                groupManageService.deleteByGroupId(checkboxId);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.delete.device.group"), "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                logger.info("删除设备分组：{}",checkboxId.toString());
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            logger.error("删除设备分组信息出现异常！", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.groupmanage.delete.error"));
    }

    /**
     * 设备分组编辑按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/groupManage/toEdit")
    public String groupManagetoEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                GroupManage groupManage = groupManageService.selectByPrimaryKey(sid);
                if (groupManage != null) {
                    modelMap.put("groupManage", groupManage);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.to.edit.device.group"), groupManage.getSgroupName());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/groupManage/groupManage-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备分组信息
     * @param groupManage
     * @return
     */
    @RequestMapping("/groupManage/edit")
    public @ResponseBody ResponseVo<GroupManage> groupManageEdit(GroupManage groupManage) {
        try {
            String id = groupManage.getId();
            String groupName = groupManage.getSgroupName();
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(groupName)) {
                groupManage.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                groupManage.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
                /*  修改数据入库 SB_GROUP_MANAGE   */
                groupManageService.updateBySelectiveVo(groupManage);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.edit.device.group"), groupName);
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse(groupManage);
            }
        } catch (ServiceException e) {
            logger.error("编辑设备分组信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑设备分组信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("sb.groupmanage.edit.service.error"));
    }

    @RequestMapping("/groupManage/toView")
    public String groupManagetoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                GroupManage groupManage = groupManageService.selectByPrimaryKey(sid);
                if (groupManage != null) {
                    map.put("groupManage", groupManage);
                    //操作日志
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.query.device.group"), groupManage.getSgroupName());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/groupManage/groupManage-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 检验选中设备中是否包含非当前商户下的设备
     * 检验选中设备设备状态是否正常
     * @return
     */
    @RequestMapping("/info/checkDevice")
    public @ResponseBody ResponseVo<String> checkDevice(String pid) {
        try {
            String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
            if (StringUtils.isNotEmpty(pid) && StringUtils.isNotBlank(merchantId)) {
                String[] ids = pid.split(",");
                return deviceInfoService.checkDeviceAttribution(ids, merchantId);
            }
        } catch (ServiceException e) {
            logger.error("查询城市信息异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("查询城市信息异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("common.param.error"));
    }

    /* ----------6.设备分组管理结束 ----------*/

}
