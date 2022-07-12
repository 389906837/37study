package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.CameraConfigDomain;
import com.cloud.cang.mgr.sb.service.CameraConfigService;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.vo.CameraConfigVo;
import com.cloud.cang.model.sb.CameraConfig;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @ClassName CameraConfigController
 * @Description 设备摄像头 ip,port,sn 配置
 * @Author zengzexiong
 * @Date 2019年2月22日14:57:14
 */
@Controller
@RequestMapping("/cameraConfig")
public class CameraConfigController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(CameraConfigController.class);

    @Autowired
    CameraConfigService cameraConfigService;

    @Autowired
    DeviceInfoService deviceInfoService;

    /**
     * 设备摄像头 ip,port,sn 配置记录列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list(String sdeviceId, ModelMap map) {
        if (StringUtils.isNotBlank(sdeviceId)) {
            map.put("sdeviceId", sdeviceId);
            return "sb/camera/deviceCamera-list";
        }
        return ExceptionUtil.to404();
    }

    /**
     * 设备摄像头 ip,port,sn 配置记录列表分页查询
     *
     * @param paramPage
     * @param cameraConfigVo
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<CameraConfigDomain>> queryCameraData(ParamPage paramPage, CameraConfigVo cameraConfigVo) {
        logger.debug("<===分页查询设备摄像头 ip,port,sn 配置记录开始===>");
        PageListVo<List<CameraConfigDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CameraConfigDomain>>();
        Page<CameraConfigDomain> page = new Page<CameraConfigDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == cameraConfigVo) {
            cameraConfigVo = new CameraConfigVo();
        }
        cameraConfigVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            cameraConfigVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = cameraConfigService.selectPageByDomainWhere(page, cameraConfigVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询设备摄像头 ip,port,sn 配置记录结束===>", pageListVo);
        return pageListVo;
    }

    /**
     * 添加信息
     *
     * @param sdeviceId 设备ID
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(String sdeviceId, ModelMap map) {
        if (StringUtils.isNotBlank(sdeviceId)) {
            map.put("sdeviceId", sdeviceId);
            return "sb/camera/deviceCamera-toAdd";
        }
        return ExceptionUtil.to404();
    }


    /**
     * 添加网络摄像头信息
     *
     * @param cameraConfig
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<CameraConfig> add(CameraConfig cameraConfig) {
        try {
            String sip = cameraConfig.getSip();
            String sdeviceId = cameraConfig.getSdeviceId();
            String sport = cameraConfig.getSport();
            String sserialNumber = cameraConfig.getSserialNumber();
            if (StringUtils.isBlank(sip)) {
                logger.error("IP为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("IP不能为空");
            }
            if (StringUtils.isBlank(sport)) {
                logger.error("端口号为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("端口号不能为空");
            }
            if (StringUtils.isBlank(sserialNumber)) {
                logger.error("序列号为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("序列号不能为空");
            }
            if (StringUtils.isBlank(sdeviceId)) {
                logger.error("设备信息为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("设备信息不能为空");
            }
            cameraConfig.setSupdateUser(SessionUserUtils.getSessionUserName());
            cameraConfig.setSaddUser(SessionUserUtils.getSessionUserName());
            cameraConfig.setTaddTime(DateUtils.getCurrentDateTime());
            cameraConfig.setTupdateTime(DateUtils.getCurrentDateTime());
            ResponseVo<CameraConfig> responseVo = cameraConfigService.insertCameraConfig(cameraConfig);
            String config = "IP：" + cameraConfig.getSip() + "端口号" + cameraConfig.getSport() + "序列号：" + cameraConfig.getSserialNumber();
            //操作日志
            String logText = MessageFormat.format("新增网络摄像头信息，", config);
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            return responseVo;
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("添加网络摄像头信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("添加网络摄像头信息出错");
    }

    /**
     * 编辑网络摄像头信息
     *
     * @param sdeviceId 设备ID
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(String sdeviceId, String sid, ModelMap map) {
        if (StringUtils.isNotBlank(sdeviceId) && StringUtils.isNotBlank(sid)) {
            CameraConfig cameraConfig = cameraConfigService.selectByPrimaryKey(sid);
            if (null != cameraConfig) {
                map.put("cameraConfig", cameraConfig);
                map.put("sdeviceId", sdeviceId);
                return "sb/camera/deviceCamera-toEdit";
            }
        }
        return ExceptionUtil.to404();
    }

    /**
     * 编辑网络摄像头信息
     *
     * @param cameraConfig
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseVo<CameraConfig> edit(CameraConfig cameraConfig) {
        try {
            String sip = cameraConfig.getSip();
            String sdeviceId = cameraConfig.getSdeviceId();
            String sport = cameraConfig.getSport();
            String sserialNumber = cameraConfig.getSserialNumber();
            if (StringUtils.isBlank(sip)) {
                logger.error("IP为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("IP不能为空");
            }
            if (StringUtils.isBlank(sport)) {
                logger.error("端口号为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("端口号不能为空");
            }
            if (StringUtils.isBlank(sserialNumber)) {
                logger.error("序列号为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("序列号不能为空");
            }
            if (StringUtils.isBlank(sdeviceId)) {
                logger.error("设备信息为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("设备信息不能为空");
            }
            cameraConfig.setSupdateUser(SessionUserUtils.getSessionUserName());
            cameraConfig.setTupdateTime(DateUtils.getCurrentDateTime());
            cameraConfigService.updateBySelectiveVo(cameraConfig);
            String config = "IP：" + cameraConfig.getSip() + "端口号" + cameraConfig.getSport() + "序列号：" + cameraConfig.getSserialNumber();
            //操作日志
            String logText = MessageFormat.format("编辑网络摄像头信息，", config);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("编辑网络摄像头信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("编辑网络摄像头信息出错");
    }

    /**
     * 编辑网络摄像头信息
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(String sid, ModelMap map) {
        if (StringUtils.isNotBlank(sid)) {
            CameraConfig cameraConfig = cameraConfigService.selectByPrimaryKey(sid);
            map.put("cameraConfig", cameraConfig);
            return "sb/camera/deviceCamera-view";
        }
        return ExceptionUtil.to404();
    }

    /**
     * 删除信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                cameraConfigService.delete(checkboxId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format("删除信息", "");
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                return ResponseVo.getSuccessResponse();
            }
        } catch (ServiceException e) {
            logger.error("删除摄像头信息失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("删除摄像头信息失败");
        } catch (Exception e) {
            logger.error("删除摄像头信息失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("删除摄像头信息异常！");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除摄像头信息失败！");
    }
}
