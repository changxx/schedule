package com.changxx.schedule.client.job;

import com.changxx.schedule.constant.Constant;
import com.changxx.schedule.constant.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 手动触发的TASK线程，由ThreadPool线程池管理
 */
public class ManualTaskExcutor extends Thread {

    private static final Logger log = LoggerFactory.getLogger(ManualTaskExcutor.class);

    private ManualTask manualTask;

    public ManualTaskExcutor(ManualTask manualTask) {
        this.manualTask = manualTask;
    }

    public void run() {

        try {
            long start = System.currentTimeMillis();
            ScheduleTask scheduleTask = JobManager.getTask(manualTask.getTaskId());
            String result = "";
            if (manualTask.getServers() != null && !manualTask.getServers().isEmpty() && manualTask.getServers().contains(Constant.LOCAL_HOSTNAME)) {
                //指定机器立即执行
                log.info("taskId 执行, taskId={}, fireType={}, cron={}, taskType={}", scheduleTask.getTaskId(), Constant.TASK_LOG_FIRETYPE_MANUAL, scheduleTask.getCronExpression(), TaskType.getByCode(scheduleTask.getTaskType()).getDesc());
            } else {
                //基本的执行流程
                JobDetail.checkForDoTask(scheduleTask, Constant.FIRE_TYPE_INVOKE, new Date(manualTask.getTime()));
            }
            long end = System.currentTimeMillis();
            log.info("Success to fire Job[" + scheduleTask.getTaskName() + "] on manual, result={" + result + "}, cost_time=" + (end - start) + "ms");

        } catch (Exception e) {
            log.error("[DO_MANUAL_ERROR]Exception occurs while firing Job[" + manualTask.getTaskId() + "] on manual", e);
        }
    }

}
