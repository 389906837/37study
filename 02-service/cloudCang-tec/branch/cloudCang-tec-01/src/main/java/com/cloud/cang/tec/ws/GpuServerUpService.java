package com.cloud.cang.tec.ws;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.sys.ScheduleConf;
import com.cloud.cang.server.JobDto;
import com.cloud.cang.server.MgrJobDto;
import com.cloud.cang.tec.quartz.utils.TaskService;
import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import com.cloud.cang.tec.sys.service.ScheduleConfService;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * GPU服务器
 *
 * @author 严凌峰
 */
@RestController
@RequestMapping("/gpuServerUpService")
@RegisterRestResource
public class GpuServerUpService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private ScheduleConfService scheduleConfService;

    private static final Logger logger = LoggerFactory.getLogger(GpuServerUpService.class);


    /**
     * 模型升级
     *
     * @param jobDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addModelUpJob", method = RequestMethod.POST)
    public ResponseVo<String> addModelUpJob(@RequestBody JobDto jobDto) {
        logger.debug("添加模型定时升级定时任务服务开始...");
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            // 校验基础参数

            if (null == jobDto) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("jobDto对象不能为空");
            }
            if (StringUtil.isBlank(jobDto.getJobName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("job名称不能为空");
            }
            if (null == jobDto.getTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("定时任务依据时间不能为空");
            }
            // 定时任务

            String time = DateUtils.dateToString(jobDto.getTime(), "ss mm HH dd MM ? yyyy");
            ScheduleConf bo = new ScheduleConf();
            bo.setId("effective#" + jobDto.getJobName());
            bo.setModuleName("gpu服务器模型升级");
            bo.setModuleCode("confirmReceipt#" + jobDto.getJobName());
            bo.setSystemName("服务器中心");
            bo.setSystemCode(3);
            bo.setStatus(1);
            bo.setCron(time);
            bo.setSpringId("upgradeJobService");
            bo.setConcurrent(1);
            bo.setMethodName("upModelJob");
            bo.setRemark("gpu服务器模型升级");
            //添加一个任务
            scheduleConfService.insert(bo);
            QuartzJobBean job = new QuartzJobBean();
            job.setJobId(String.valueOf(bo.getId()));
            job.setJobName(bo.getModuleCode());
            job.setJobGroup(bo.getSystemCode() + "");
            job.setJobStatus(bo.getStatus() + "");//初始状态
            job.setCronExpression(bo.getCron());
            job.setSpringId(bo.getSpringId());
            job.setIsConcurrent(bo.getConcurrent() + "");
            job.setJobClass(bo.getClassName());
            job.setMethodName(bo.getMethodName());
            job.setDescription(bo.getSystemName() + "->" + bo.getModuleName() + "->" + bo.getInterfaceInfo());
            taskService.addJob(job);
            responseVo.setData("添加自动确认收货定时任务成功");
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("添加模型定时升级定时任务服务异常");
            logger.error("添加模型定时升级定时任务服务异常：", e);
        }
        logger.debug("添加模型定时升级定时任务服务结束......");
        return responseVo;
    }

    /**
     * 后台升级视觉识别模型，视觉服务，升级APK定时任务
     *
     * @param mgrJobDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addMgrDeviceUpgradeJob", method = RequestMethod.POST)
    public ResponseVo<String> addMgrDeviceUpgradeJob(@RequestBody MgrJobDto mgrJobDto) {
        logger.debug("添加后台升级视觉识别模型，视觉服务，升级APK定时任务开始...");
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            // 校验基础参数
            if (null == mgrJobDto) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("mgrJobDto对象不能为空");
            }
            if (StringUtil.isBlank(mgrJobDto.getJobName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("mgrJobDto名称不能为空");
            }
            if (null == mgrJobDto.getTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("定时任务依据时间不能为空");
            }
            if (null == mgrJobDto.getParamType()) { // 10=APK升级 20=视觉库升级 30=视觉服务升级
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("定时任务类型不能为空");
            }
            Integer itype = mgrJobDto.getParamType();

            // 定时任务
            String time = DateUtils.dateToString(mgrJobDto.getTime(), "ss mm HH dd MM ? yyyy");
            ScheduleConf bo = new ScheduleConf();
            bo.setId("effective#" + mgrJobDto.getJobName());
            // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级
            if (itype == 10) {
                bo.setModuleName("视觉服务升级");
                bo.setRemark("视觉服务升级");
            } else if (itype == 20) {
                bo.setModuleName("视觉库升级");
                bo.setRemark("视觉库升级");
            } else if (itype == 30) {
                bo.setModuleName("客户端APK升级");
                bo.setRemark("客户端APK升级");
            }
            bo.setSystemName("云管理平台");
            bo.setModuleCode("confirmReceipt#" + mgrJobDto.getJobName());
            bo.setSystemCode(3);
            bo.setStatus(1);
            bo.setCron(time);
            bo.setSpringId("upgradeJobService");    // service  UpgradeJobService.class
            bo.setConcurrent(1);
            bo.setMethodName("mgrDeviceUpgrade");     // method

            //添加一个任务
            scheduleConfService.insert(bo);
            QuartzJobBean job = new QuartzJobBean();
            job.setJobId(String.valueOf(bo.getId()));
            job.setJobName(bo.getModuleCode());
            job.setJobGroup(bo.getSystemCode() + "");
            job.setJobStatus(bo.getStatus() + "");//初始状态
            job.setCronExpression(bo.getCron());
            job.setSpringId(bo.getSpringId());
            job.setIsConcurrent(bo.getConcurrent() + "");
            job.setJobClass(bo.getClassName());
            job.setMethodName(bo.getMethodName());
            job.setDescription(bo.getSystemName() + "->" + bo.getModuleName() + "->" + bo.getInterfaceInfo());
            taskService.addJob(job);
            responseVo.setData("添加模型定时升级定时任务成功");
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("添加模型定时升级定时任务服务异常");
            logger.error("添加模型定时升级定时任务服务异常：", e);
        }
        logger.debug("添加模型定时升级定时任务服务结束......");
        return responseVo;
    }

}
