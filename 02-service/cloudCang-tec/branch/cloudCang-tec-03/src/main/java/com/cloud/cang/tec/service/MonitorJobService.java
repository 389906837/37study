/**
 * 
 */
package com.cloud.cang.tec.service;

import java.util.HashMap;
import java.util.Map;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.tec.monitor.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;



/**
 * @author zhouhong
 * 监控异常任务中心
 */
@Service(value="monitorJobService")
public class MonitorJobService {

	private static final Logger logger = LoggerFactory.getLogger(MonitorJobService.class);
	@Autowired
    MonitorService monitorService;
	/**
	 * 注册数据提醒
	 */
	public void registerAlert() {
		String tmp="当天用户注册人数为：{%s} 其中投资人数为：{%s} 今天首投人数为：{%s}";
		logger.info("注册数据提醒任务执行==>");
		String sql="select count(1) regist_num,count(case when t.first_investment_date is not null then 1  end) invest_num from hy_member_info t where trunc(t.dregisterdate)=trunc(sysdate)";
		String investCountSql="select count(1) count from hy_member_info t where trunc(t.first_investment_date)=trunc(sysdate)";
		Map<String,Object> registinfo=this.monitorService.executeSql(sql);//当天注册人数，及投资人数 
		if(registinfo==null){
			logger.info("注册数据提醒任务执行==>没有查询到数据");
		}else{
			Integer investCount=this.monitorService.queryCountBySql(investCountSql);
			
			String content=String.format(tmp, registinfo.get("REGIST_NUM").toString(),registinfo.get("INVEST_NUM").toString(),investCount.toString());
			sendMessageToInner("TPE0060", "monitor_register_alert", content);
		}
		logger.info("注册数据提醒任务执行==>完成");
	}
	
	public void registerWarnForIp(){
		logger.info("同一ip注册数预警任务执行==>");
		String thresholdValue= GrpParaUtil.getValue("SP000104", "ip_register_num");
		if(thresholdValue==null)thresholdValue="0";
		Integer val=Integer.parseInt(thresholdValue);
		String sql="select count(1) count_ from (select count(1)  from hy_member_info t where trunc(t.dregisterdate)=trunc(sysdate) group by t.suser_ip having(count(1))>"+val+")";
		
		Integer investCount=this.monitorService.queryCountBySql(sql);
		if(investCount>0){
			sendMessageToInner("TPE0061", "monitor_register_ip_warn", "当前存在同一ip注册用户数>"+val+"的异常会员信息");
		}else{
			logger.info("同一ip注册数预警任务执行==>没有异常数据");
		}
		logger.info("同一ip注册数预警任务执行==>完成");
	}

	/**
     * 发送短信给内部人
     * @param templateMainCode 模板编号
     * @param purviewCode 权限码
     * @param memo 发送的消息
     * @return
     */
    private void sendMessageToInner(String templateMainCode,String purviewCode,String memo) {
        InnerMessageDto innerMessageDto = new InnerMessageDto();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("content", memo);
        innerMessageDto.setContentParam(param);
        innerMessageDto.setTemplateCode(templateMainCode);
        innerMessageDto.setPurviewCode(purviewCode);
        try {
        	RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
			invoke.setRequestObj(innerMessageDto); // post 参数
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
			@SuppressWarnings("unchecked")
			ResponseVo<String> responseVo1 = (ResponseVo<String>) invoke.invoke();
			logger.info("监控任务发送短信：{}", responseVo1);
        } catch (Exception e) {
            logger.error("监控任务发送短信失败{}",e);
        }
    }
}
