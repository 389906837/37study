package com.cloud.cang.model.mq;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** (MQ_MESSAGE) **/
public class Message extends GenericEntity  {

	private static final long serialVersionUID = 1L;

	/**/
	private String id;
	/**/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 消息状态 
	10=新建
	20=发送成功
	30=发送失败
	40=消费成功
	41=消费成功,业务处理失败
	50=消费失败:消费超限，进入死信队列 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 内容 */
	private String scontent;
	
	public String getScontent(){
		return scontent;
	}
	
	public void setScontent(String scontent){
		this.scontent= scontent;
	}
	/* 发送端ip */
	private String sfromIp;
	
	public String getSfromIp(){
		return sfromIp;
	}
	
	public void setSfromIp(String sfromIp){
		this.sfromIp= sfromIp;
	}
	/* 队列名称 */
	private String squeueName;
	
	public String getSqueueName(){
		return squeueName;
	}
	
	public void setSqueueName(String squeueName){
		this.squeueName= squeueName;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 消费端ip */
	private String stoIp;
	
	public String getStoIp(){
		return stoIp;
	}
	
	public void setStoIp(String stoIp){
		this.stoIp= stoIp;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 更新时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

	 public enum StatusEnum{
		CREATE(10,"新建"),
		SEND_SUC(20,"发送成功"),
		SEND_FAIL(30,"发送失败"),
		CONSUMER_SUC(40,"消费成功"),
		CONSUMER_SUC_BIZ_FAIL(41,"消费成功,业务处理失败"),
		CONSUMER_FAIL_DEAD(50,"消费失败:消费超限，进入死信队列");

		private int code;
		private String desc;
		StatusEnum(int code,String desc){
			this.code = code;
			this.desc = desc;
		}
		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}

