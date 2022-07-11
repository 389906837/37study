package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.WebUtils;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.model.sys.ScheduleConf;
import com.cloud.cang.server.GpuServerServicesDefine;
import com.cloud.cang.server.JobDto;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.op.service.InterfaceAcceptService;
import com.cloud.cang.tec.quartz.utils.TaskService;
import com.cloud.cang.tec.quartz.vo.QuartzJobBean;
import com.cloud.cang.tec.sys.service.ScheduleConfService;
import com.cloud.cang.tec.ws.InterfaceAcceptCallbackService;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 平台接口业务受理信息回调定时任务
 *
 * @author 严凌峰
 * @version 1.0
 */
@Service(value = "interfaceAcceptJobService")
public class InterfaceAcceptJobService {

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;
    @Autowired
    private ScheduleConfService scheduleConfService;
    @Autowired
    private TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(InterfaceAcceptJobService.class);


    public void interfaceAcceptCallback(final String id) {
        logger.info("平台接口业务受理信息回调定时任务开始...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    String msg = saveInterfaceAcceptCallbackJob(id);
                    return msg;
                } catch (Exception e) {
                    logger.error("模型升级定时任务失败", e);
                    return "模型升级定时任务失败";
                }
            }
        }, TecConstants.INTERFACE_ACCEPT_CALLBACK, true);
    }

    public String saveInterfaceAcceptCallbackJob(String id) {
        try {
            InterfaceAccept interfaceAccept = interfaceAcceptService.selectByPrimaryKey(id);
            if (null == interfaceAccept) {
                logger.info("失败,平台接口业务受理信息不存在");
                return "失败,平台接口业务受理信息不存在";
            }
            if (interfaceAccept.getIisCallbackSuccess() == 1 || interfaceAccept.getIcallbackNum() >= Integer.valueOf(BizParaUtil.get("interface_accept_callback_maxnum"))) {
                //回调次数大于限制次数 不再回调
                logger.info("失败,回调次数大于限制次数,不再回调");
                return "失败,回调次数大于限制次数,不再回调";
            }
            //回调地址
            String notifyUrl = interfaceAccept.getScallbackAddress();
            //返回加密后的数据
            Map<String, String> map = new HashMap();
            String encryptCallbackBody = interfaceAccept.getScallbackEncryData();
            map.put("backData", encryptCallbackBody);
            String status = WebUtils.doGet(notifyUrl, map);
            //回调成功
            if (status.equals("SUCCESS")) {
                InterfaceAccept temp = new InterfaceAccept();
                temp.setId(interfaceAccept.getId());
                temp.setIisCallback(1);
                temp.setIisCallbackSuccess(1);
                temp.setTcallbackSuccessTime(DateUtils.getCurrentDateTime());
                temp.setIcallbackNum(interfaceAccept.getIcallbackNum() + 1);
                interfaceAcceptService.updateBySelective(temp);
            } else {
                //回调失败
                InterfaceAccept temp = new InterfaceAccept();
                temp.setId(interfaceAccept.getId());
                temp.setIisCallback(1);
                temp.setIisCallbackSuccess(0);
                temp.setIcallbackNum(interfaceAccept.getIcallbackNum() + 1);
                interfaceAcceptService.updateBySelective(temp);
                String isSendMessage = BizParaUtil.get("interface_accept_callback_maxnum");
                if (interfaceAccept.getIisCallbackSuccess() == 1 || interfaceAccept.getIcallbackNum() >= Integer.valueOf(isSendMessage)) {
                    //回调次数大于限制次数 不再回调
                    logger.info("失败,回调次数大于限制次数,不再回调:{}", interfaceAccept.getId());
                    return "失败,回调次数大于限制次数,不再回调";
                }
                //定时任务再次回调
                //定时更新
                Date time = ComputationTimes(temp.getIcallbackNum());
                if (null != temp) {
                    JobDto jobDto = new JobDto();
                    jobDto.setJobName(interfaceAccept.getId());
                    jobDto.setTime(time);
                    ResponseVo responseVo = this.upCallbackFailJob(jobDto);
                } else {
                    logger.info("数据字典配置不足，回调失败：{}", temp.getId());
                    return "失败,数据字典配置不足,回调失败";
                }
            }
            //判断回调返回的数据
            return "成功";
        } catch (Exception e) {
            logger.error("平台接口业务受理信息回调异常,平台接口业务受理信息ID：{}", id);
            return "平台接口业务受理信息回调失败！";
        }
    }

    /**
     * 修改定时任务
     *
     * @param jobDto
     * @return
     */
    private ResponseVo upCallbackFailJob(JobDto jobDto) {
        logger.debug("修改平台接口业务受理信息回调失败定时任务服务开始...");
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            // 定时任务
            String time = DateUtils.dateToString(jobDto.getTime(), "ss mm HH dd MM ? yyyy");
            ScheduleConf bo = new ScheduleConf();
            bo.setId("effective#" + jobDto.getJobName());
            bo.setCron(time);
            //修改一个任务
            scheduleConfService.updateBySelective(bo);
            bo = scheduleConfService.selectByPrimaryKey(bo.getId());
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
            responseVo.setData("修改平台接口业务受理信息回调失败再处理定时任务成功");
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("修改平台接口业务受理信息回调失败再处理定时任务服务异常");
            logger.error("修改平台接口业务受理信息回调失败再处理定时任务服务异常：", e);
        }
        logger.debug("修改平台接口业务受理信息回调失败再处理定时任务服务结束......");
        return responseVo;
    }

    /**
     * 计算发送时间
     *
     * @param num
     * @return
     */
    private Date ComputationTimes(int num) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowTime = Calendar.getInstance();
        Date time = null;
        String temp = GrpParaUtil.getValue("SP000164", "callback_" + num);
        switch (num) {
            case 1:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 2:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 3:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 4:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 5:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            case 6:
                nowTime.add(Calendar.MINUTE, Integer.valueOf(temp));
                time = nowTime.getTime();
                break;
            default:
                break;
        }
        return time;
    }
}

