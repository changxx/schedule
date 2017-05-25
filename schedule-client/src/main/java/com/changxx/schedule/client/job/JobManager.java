package com.changxx.schedule.client.job;

import com.changxx.schedule.constant.Constant;
import com.changxx.schedule.curator.CuratorSupport;
import com.changxx.schedule.util.FastJsonUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * JobManager
 *
 * @author changxiangxiang
 * @date 2017/5/22
 */
public class JobManager {

    private static final Logger log = LoggerFactory.getLogger(JobManager.class);

    private static Scheduler scheduler;

    private static Map<String, ScheduleTask> taskMap = new HashMap();

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

    public static boolean addNewJob(ScheduleTask task) {
        JobManager.initScheduler();
        boolean flag = false;
        try {
            JobDetail job = JobBuilder.newJob(ScheduleJob.class).withIdentity(task.getTaskId() + "", Scheduler.DEFAULT_GROUP).build();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTaskId() + "").withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).build();
            scheduler.scheduleJob(job, trigger);
            flag = true;
            log.info("[Deploy]Success to regist a new task, " + task.toString());
        } catch (Exception e) {
            log.error("[DO_CRON_ERROR]Fail to start task system: Fail to deploying task, task=" + task.toString(), e);
        }
        return flag;
    }

    /**
     * 刷新本地task列表
     */
    public static void refreshTask(List<ScheduleTask> taskList) {
        for (ScheduleTask scheduleTask : taskList) {
            JobManager.taskMap.put(scheduleTask.getTaskId(), scheduleTask);
        }
    }

    public static void deleteJob(ScheduleTask task) {
        JobManager.initScheduler();
        try {
            JobKey jobKey = new JobKey(task.getTaskId(), Scheduler.DEFAULT_GROUP);
            boolean result = scheduler.deleteJob(jobKey);
            log.info("[Deploy]Success to delete an old task result={}, task={}", result, task);
        } catch (Exception e) {
            log.info("[Deploy]Error to delete an old task, " + task.toString());
        }
    }

    public static Scheduler getScheduler() {
        return JobManager.scheduler;
    }

    public static ScheduleTask getTask(String taskId) {
        return JobManager.taskMap.get(taskId);
    }

    public static List<ScheduleTask> getAllTaskItemFromSchedule() {
        return new ArrayList<>(taskMap.values());
    }

    /**
     * 获取所有任务配置
     */
    public static List<ScheduleTask> getAllTaskItemFromZK() {
        List<ScheduleTask> items = new ArrayList<>();
        String zkPath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK;
        try {
            List<String> taskIds = CuratorSupport.getChildren(zkPath);
            if (null != taskIds && taskIds.size() > 0) {
                for (String taskId : taskIds) {
                    String valueString = "";
                    try {
                        valueString = CuratorSupport.getData(zkPath + "/" + taskId);
                        ScheduleTask result = FastJsonUtil.parse(valueString, ScheduleTask.class);
                        items.add(result);
                    } catch (Exception e) {
                        log.error("[GET_TASK_ERROR]将zk中的获取的数据转成任务对象时出错.valueString:" + valueString, e);
                    }
                }
            } else {
                log.error("[GET_TASK_ERROR]从zk中获取任务列表为空.");
            }
        } catch (Exception e) {
            log.error("[GET_TASK_ERROR]从zk中获取任务列表失败.", e);
        }
        return items;
    }
}
