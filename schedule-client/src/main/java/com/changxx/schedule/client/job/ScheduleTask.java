package com.changxx.schedule.client.job;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTask implements Serializable {

    private static final long serialVersionUID = -6590176107112228725L;

    /**
     * 任务id，全局唯一
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务所属组
     */
    private String taskGroup;

    /**
     * 任务执行的服务名称，对应spring bean
     */
    private String serviceName;

    /**
     * 定时任务执行策略
     */
    private String cronExpression;

    /**
     * 任务状态：0表示正常，1表示暂停，2表示删除, 3.待修改, 4,审批中
     */
    private Integer state;

    /**
     * 任务类型：0表示都不执行，1表示所有的机器上只有一台执行，2表示所有机器都执行，3表示在指定机器上全部执行，4表示只有一台指定的机器执行
     */
    private Integer taskType;

    /**
     * 指定的机器主机名，如果有多个，用“,”分隔
     */
    private String hostName;

    /**
     * 排除的机器主机名，如果有多个，用","分隔
     */
    private String blacklistForHostname;

    /**
     * 任务描述
     */
    private String description;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getBlacklistForHostname() {
        return blacklistForHostname;
    }

    public void setBlacklistForHostname(String blacklistForHostname) {
        this.blacklistForHostname = blacklistForHostname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHostNames() {
        List<String> hosts = new ArrayList<String>();
        if(StringUtils.isNotBlank(this.hostName)){
            String[] s = this.hostName.split(",");
            for(String i : s){
                hosts.add(i);
            }
        }
        return hosts;
    }

    @Override
    public String toString() {
        return "ScheduleTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskGroup='" + taskGroup + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", state=" + state +
                ", taskType=" + taskType +
                ", hostName='" + hostName + '\'' +
                ", blacklistForHostname='" + blacklistForHostname + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
