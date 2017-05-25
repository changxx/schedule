package com.changxx.schedule;

import com.changxx.schedule.client.job.JobManager;
import com.changxx.schedule.client.job.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ScheduleClientFactory
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class ScheduleClientFactory {

    private static final Logger log = LoggerFactory.getLogger(ScheduleClientFactory.class);

    /**
     * 刷新本地task
     */
    public static void refreshTask() {
        // 从zookeeper中加载最新的task配置信息
        List<ScheduleTask> zkTaskList = JobManager.getAllTaskItemFromZK();
        log.info("从zookeeper中加载最新的task配置信息 size={}", zkTaskList.size());

        // 目前调度的任务信息
        List<ScheduleTask> scheduleTasks = JobManager.getAllTaskItemFromSchedule();
        log.info("目前调度的任务信息 size={}", scheduleTasks.size());

        for (ScheduleTask scheduleTask : scheduleTasks) {
            JobManager.deleteJob(scheduleTask);
        }

        for (ScheduleTask scheduleTask : zkTaskList) {
            JobManager.addNewJob(scheduleTask);
        }

        JobManager.refreshTask(zkTaskList);
        log.info("refreshTask 结束");
    }

}
