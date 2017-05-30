package com.changxx.schedule.client.job;

import java.util.List;

/**
 * ManualTask
 *
 * @author changxiangxiang
 * @date 2017/5/28
 */
public class ManualTask {

    private String taskId;

    private long time;

    private List<String> servers;

    @Override
    public String toString() {
        return "ManualTask{" +
                "taskId='" + taskId + '\'' +
                ", time=" + time +
                ", servers=" + servers +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }
}
