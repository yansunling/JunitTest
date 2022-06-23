package com.dy.test.mq;

import com.yd.utils.mq.ChannelMessage;

import java.sql.Timestamp;
import java.util.Arrays;

public class TASK_JOB_INFO extends ChannelMessage {

	private static final long serialVersionUID = 1L;

	private String beanName;

	private String targetClassName;

	private String systemId;

	private String cronExpression;

	private boolean isExecuted;

	private boolean isCover;


	private String msg;

	private String job_status;

	private String logPath;

	private Timestamp start_time;

	private Timestamp end_time;

	private String etcdKey;

	private String[] registerBeans;

	private String logId;

	private boolean isOnce;

	private String jobDesc;

	private String userId;

	private String authorize;


	/**
	 *执行时长限制
	 */
	private Integer time_limit;
	/**
	 *失败重试次数
	 */
	private Integer retry_time;



	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public boolean getIsExecuted() {
		return isExecuted;
	}

	public void setIsExecuted(boolean executed) {
		isExecuted = executed;
	}

	public boolean getIsCover() {
		return isCover;
	}

	public void setIsCover(boolean cover) {
		isCover = cover;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJob_status() {
		return job_status;
	}

	public void setJob_status(String job_status) {
		this.job_status = job_status;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getEtcdKey() {
		return etcdKey;
	}

	public void setEtcdKey(String etcdKey) {
		this.etcdKey = etcdKey;
	}

	public String[] getRegisterBeans() {
		return registerBeans;
	}

	public void setRegisterBeans(String[] registerBeans) {
		this.registerBeans = registerBeans;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public boolean getIsOnce() {
		return isOnce;
	}

	public void setIsOnce(boolean once) {
		isOnce = once;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(Integer time_limit) {
		this.time_limit = time_limit;
	}

	public Integer getRetry_time() {
		return retry_time;
	}

	public void setRetry_time(Integer retry_time) {
		this.retry_time = retry_time;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	@Override
	public String toString() {
		return "TASK_JOB_INFO{" +
				"beanName='" + beanName + '\'' +
				", targetClassName='" + targetClassName + '\'' +
				", systemId='" + systemId + '\'' +
				", cronExpression='" + cronExpression + '\'' +
				", isExecuted=" + isExecuted +
				", isCover=" + isCover +
				", msg='" + msg + '\'' +
				", job_status='" + job_status + '\'' +
				", logPath='" + logPath + '\'' +
				", start_time=" + start_time +
				", end_time=" + end_time +
				", etcdKey='" + etcdKey + '\'' +
				", registerBeans=" + Arrays.toString(registerBeans) +
				", logId='" + logId + '\'' +
				", isOnce=" + isOnce +
				", jobDesc='" + jobDesc + '\'' +
				", userId='" + userId + '\'' +
				", time_limit=" + time_limit +
				", retry_time=" + retry_time +
				'}';
	}
}