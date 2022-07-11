package com.cloud.cang.tec.quartz.vo;


import com.cloud.cang.generic.GenericEntity;

/** (QRTZ_TRIGGERS) **/
public class Triggers extends GenericEntity {

	private static final long serialVersionUID = 1L;
	
	private String moduleName;
	/**/
	private String schedName;
	/**/
	public String getSchedName(){
		return schedName;
	}
	
	public void setSchedName(String schedName){
		 this.schedName= schedName;
	}
	/* 表达式 */
	private String cron;
	
	public String getCron(){
		return cron;
	}
	
	public void setCron(String cron){
		this.cron= cron;
	}
	/*  */
	private String calendarName;
	
	public String getCalendarName(){
		return calendarName;
	}
	
	public void setCalendarName(String calendarName){
		this.calendarName= calendarName;
	}
	/*  */
	private String description;
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description= description;
	}
	/*  */
	private Long endTime;
	
	public Long getEndTime(){
		return endTime;
	}
	
	public void setEndTime(Long endTime){
		this.endTime= endTime;
	}
	/*  */
	private byte[] jobData;
	
	public byte[] getJobData(){
		return jobData;
	}
	
	public void setJobData(byte[] jobData){
		this.jobData= jobData;
	}
	/*  */
	private String jobGroup;
	
	public String getJobGroup(){
		return jobGroup;
	}
	
	public void setJobGroup(String jobGroup){
		this.jobGroup= jobGroup;
	}
	/*  */
	private String jobName;
	
	public String getJobName(){
		return jobName;
	}
	
	public void setJobName(String jobName){
		this.jobName= jobName;
	}
	/*  */
	private Integer misfireInstr;
	
	public Integer getMisfireInstr(){
		return misfireInstr;
	}
	
	public void setMisfireInstr(Integer misfireInstr){
		this.misfireInstr= misfireInstr;
	}
	/*  */
	private Long nextFireTime;
	
	public Long getNextFireTime(){
		return nextFireTime;
	}
	
	public void setNextFireTime(Long nextFireTime){
		this.nextFireTime= nextFireTime;
	}
	/*  */
	private Long prevFireTime;
	
	public Long getPrevFireTime(){
		return prevFireTime;
	}
	
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setPrevFireTime(Long prevFireTime){
		this.prevFireTime= prevFireTime;
	}
	/*  */
	private Integer priority;
	
	public Integer getPriority(){
		return priority;
	}
	
	public void setPriority(Integer priority){
		this.priority= priority;
	}
	/*  */
	private Long startTime;
	
	public Long getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Long startTime){
		this.startTime= startTime;
	}
	/*  */
	private String triggerGroup;
	
	public String getTriggerGroup(){
		return triggerGroup;
	}
	
	public void setTriggerGroup(String triggerGroup){
		this.triggerGroup= triggerGroup;
	}
	/*  */
	private String triggerName;
	
	public String getTriggerName(){
		return triggerName;
	}
	
	public void setTriggerName(String triggerName){
		this.triggerName= triggerName;
	}
	/*  */
	private String triggerState;
	
	public String getTriggerState(){
		return triggerState;
	}
	
	public void setTriggerState(String triggerState){
		this.triggerState= triggerState;
	}
	/*  */
	private String triggerType;
	
	public String getTriggerType(){
		return triggerType;
	}
	
	public void setTriggerType(String triggerType){
		this.triggerType= triggerType;
	}

		
}