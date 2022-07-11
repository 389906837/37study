package com.cloud.cang.tec.ws;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.sys.ScheduleConf;
import com.cloud.cang.server.JobDto;
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
 * 平台接口业务受理信息回调失败处理
 *
 * @author 严凌峰
 */
@RestController
@RequestMapping("/interfaceAcceptCallbackService")
@RegisterRestResource
public class InterfaceAcceptCallbackService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ScheduleConfService scheduleConfService;


    private static final Logger logger = LoggerFactory.getLogger(InterfaceAcceptCallbackService.class);


    /**
     * 平台接口业务受理信息回调失败
     *
     * @param jobDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addCallbackFailJob", method = RequestMethod.POST)
    public ResponseVo<String> addCallbackFailJob(@RequestBody JobDto jobDto) {
        logger.debug("添加平台接口业务受理信息回调失败定时任务服务开始...");
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
            bo.setModuleName("平台接口业务受理信息回调失败再处理");
            bo.setModuleCode("confirmReceipt#" + jobDto.getJobName());
            bo.setSystemName("服务器中心");
            bo.setSystemCode(3);
            bo.setStatus(1);
            bo.setCron(time);
            bo.setSpringId("interfaceAcceptJobService");
            bo.setConcurrent(1);
            bo.setMethodName("interfaceAcceptCallback");
            bo.setRemark("平台接口业务受理信息回调失败再处理");
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
            responseVo.setData("添加平台接口业务受理信息回调失败再处理定时任务成功");
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("添加平台接口业务受理信息回调失败再处理定时任务服务异常");
            logger.error("添加平台接口业务受理信息回调失败再处理定时任务服务异常：", e);
        }
        logger.debug("添加平台接口业务受理信息回调失败再处理定时任务服务结束......");
        return responseVo;
    }

}