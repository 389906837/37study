package com.cloud.cang.tec.quartz.vo;

import java.io.Serializable;
import java.util.Date;

public class QuartzJobBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1777176016531067347L;
	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";

	/** 任务id */
	private String jobId;

	/** 任务名称 */
	private String jobName;

	/** 任务分组，任务名称+组名称应该是唯一的 */
	private String jobGroup;

	/** 任务初始状态 0禁用 1启用 2删除 */
	private String jobStatus;

	/** 任务是否有状态（并发） */
	private String isConcurrent = "1";

	/** 任务运行时间表达式 */
	private String cronExpression;

	/** 任务描述 */
	private String description;

	/** 任务调用类在spring中注册的bean id，如果spingId不为空，则按springId查找 */
	private String springId;

	/** 任务调用类名，包名+类名，通过类反射调用 ，如果spingId为空，则按jobClass查找 */
	private String jobClass;

	/** 任务调用的方法名 */
	private String methodName;

	/** 启动时间 */
	private Date startTime;

	/** 前一次运行时间 */
	private Date previousTime;

	/** 下次运行时间 */
	private Date nextTime;

	/**
	 * 任务类型  1 系统任务 2 监控任务 3订单任务
	 */
	private Integer ijobType;

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the isConcurrent
	 */
	public String getIsConcurrent() {
		return isConcurrent;
	}

	/**
	 * @param isConcurrent the isConcurrent to set
	 */
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the springId
	 */
	public String getSpringId() {
		return springId;
	}

	/**
	 * @param springId the springId to set
	 */
	public void setSpringId(String springId) {
		this.springId = springId;
	}

	/**
	 * @return the jobClass
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass the jobClass to set
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the previousTime
	 */
	public Date getPreviousTime() {
		return previousTime;
	}

	/**
	 * @param previousTime the previousTime to set
	 */
	public void setPreviousTime(Date previousTime) {
		this.previousTime = previousTime;
	}

	/**
	 * @return the nextTime
	 */
	public Date getNextTime() {
		return nextTime;
	}

	/**
	 * @param nextTime the nextTime to set
	 */
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public Integer getIjobType() {
		return ijobType;
	}

	public void setIjobType(Integer ijobType) {
		this.ijobType = ijobType;
	}

	@Override
	public String toString() {
		return "QuartzJobBean{" +
				"jobId='" + jobId + '\'' +
				", jobName='" + jobName + '\'' +
				", jobGroup='" + jobGroup + '\'' +
				", jobStatus='" + jobStatus + '\'' +
				", isConcurrent='" + isConcurrent + '\'' +
				", cronExpression='" + cronExpression + '\'' +
				", description='" + description + '\'' +
				", springId='" + springId + '\'' +
				", jobClass='" + jobClass + '\'' +
				", methodName='" + methodName + '\'' +
				", startTime=" + startTime +
				", previousTime=" + previousTime +
				", nextTime=" + nextTime +
				", ijobType=" + ijobType +
				'}';
	}
}
