package com.changxx.schedule.job;

import com.changxx.schedule.client.job.JobManager;
import com.changxx.schedule.client.job.ScheduleTask;
import com.changxx.schedule.constant.Constant;
import org.junit.Test;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import java.util.Set;

/**
 * JobManagerTest
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class JobManagerTest {

    @Test
    public void jobAdd() throws Exception {
        ScheduleTask task = new ScheduleTask();
        task.setTaskId("task-test4");
        task.setCronExpression("0/5 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ALL);
        boolean flag = JobManager.addNewJob(task);
        System.out.println(flag);

        Set<TriggerKey> triggerKeyList = JobManager.getScheduler().getTriggerKeys(null);

        for (TriggerKey triggerKey : triggerKeyList) {
            Trigger trigger = JobManager.getScheduler().getTrigger(triggerKey);
            System.out.print("task：" + trigger.getJobKey());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                System.out.println(" " + cronTrigger.getCronExpression());
            }
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

}
