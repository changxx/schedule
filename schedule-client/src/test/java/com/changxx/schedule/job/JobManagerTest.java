package com.changxx.schedule.job;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Set;

/**
 * JobManagerTest
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class JobManagerTest {

    private static final Logger log = LoggerFactory.getLogger(JobManagerTest.class);

    private static Scheduler scheduler;

    @Test
    public void jobAdd() throws Exception {
        JobManagerTest.initScheduler();

        String taskId = "task4";
        String cron = "0/5 * * * * ? *";

        JobDetail job = JobBuilder.newJob(ScheduleJobDetail.class).withIdentity(taskId + "", Scheduler.DEFAULT_GROUP).build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(taskId + "").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
        scheduler.scheduleJob(job, trigger);
        log.info("[Deploy]Success to regist a new task, " + taskId);

        Set<TriggerKey> triggerKeyList = scheduler.getTriggerKeys(null);

        for (TriggerKey triggerKey : triggerKeyList) {
            Trigger triggerTemp = scheduler.getTrigger(triggerKey);
            System.out.print("task：" + trigger.getJobKey());
            if (triggerTemp instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) triggerTemp;
                System.out.println(" " + cronTrigger.getCronExpression());
            }
        }

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void initScheduler() {
        if (scheduler == null) {
            synchronized (Scheduler.class) {
                if (scheduler == null) {
                    try {
                        String threadCount = "10";
                        String threadPoolClass = "org.quartz.simpl.SimpleThreadPool";
                        String threadPriority = "5";
                        Properties properties = new Properties();
                        properties.put("org.quartz.threadPool.threadCount", threadCount);
                        properties.put("org.quartz.threadPool.class", threadPoolClass);
                        properties.put("org.quartz.threadPool.threadPriority", threadPriority);
                        scheduler = (new StdSchedulerFactory(properties)).getScheduler();
                        scheduler.start();
                    } catch (SchedulerException e) {
                        log.error("Can't instantiate scheduler, ", e);
                    }
                }
            }
        }
    }

}
