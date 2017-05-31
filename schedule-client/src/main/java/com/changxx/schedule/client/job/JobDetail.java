package com.changxx.schedule.client.job;

import com.changxx.schedule.constant.Constant;
import com.changxx.schedule.constant.TaskType;
import com.changxx.schedule.curator.CuratorSupport;
import com.changxx.schedule.curator.LockExecute;
import org.apache.zookeeper.CreateMode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * JobDetail
 *
 * @author changxiangxiang
 * @date 2017/5/22
 */
public class JobDetail implements Job {

    private static final Logger log = LoggerFactory.getLogger(CuratorSupport.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String taskId = context.getJobDetail().getKey().getName();
        ScheduleTask scheduleTask = JobManager.getTask(taskId);
        JobDetail.checkForDoTask(scheduleTask, Constant.FIRE_TYPE_CRON, context.getScheduledFireTime());
    }

    public static void checkForDoTask(ScheduleTask scheduleTask, int fireType, Date runTime) {
        //1.执行方式校验，执行方式-都不执行 则直接返回
        if (scheduleTask.getTaskType() == null || Constant.RUN_ON_NONE == scheduleTask.getTaskType()) {
            return;
        }

        //2.在zk上创建任务节点/schedule/{group}/task/{taskId}/cron/{runtime}
        try {
            JobDetail.createFireTimeNode(scheduleTask.getTaskId(), runTime, fireType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (scheduleTask.getTaskType() == Constant.RUN_ON_ALL) {
            // 在所有机器执行，直接执行
            JobDetail.doTaskWithOutLock(scheduleTask, fireType, runTime);
        } else if (scheduleTask.getTaskType() == Constant.RUN_ON_ASSIGN) {
            // 执行方式-指定机器上全部执行
            if (scheduleTask.getHostNames().contains(Constant.LOCAL_HOSTNAME)) {
                // 在执行机器列表中，直接执行
                JobDetail.doTaskWithOutLock(scheduleTask, fireType, runTime);
            }
        } else if (scheduleTask.getTaskType() == Constant.RUN_ON_ASSIGN_ONE) {
            // 执行方式-指定机器上只有一台执行，抢锁执行
            if (scheduleTask.getHostNames().contains(Constant.LOCAL_HOSTNAME)) {
                sleep();
                JobDetail.doTaskWithLock(scheduleTask, fireType, runTime);
            }
        } else if (scheduleTask.getTaskType() == Constant.RUN_ON_ONE) {
            // 执行方式-所有的机器上只有一台执行，抢锁执行
            sleep();
            JobDetail.doTaskWithLock(scheduleTask, fireType, runTime);
        }
    }


    /**
     * 在zk上创建任务节点/schedule/group/task/taskId/cron/firetime
     *
     * @param taskId   任务ID
     * @param runTime  执行时间
     * @param fireType 执行方式 0-手动，1-定时，2-启动
     */
    public static boolean createFireTimeNode(String taskId, Date runTime, int fireType) throws Exception {
        String firePath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK + Constant.NODE_SEPARATE + taskId + Constant.getFirePath(fireType);
        String zkPath = firePath + Constant.NODE_SEPARATE + runTime.getTime();
        if (!CuratorSupport.checkExists(firePath)) {
            CuratorSupport.create(firePath);
        }

        if (!CuratorSupport.checkExists(zkPath)) {
            CuratorSupport.create(zkPath, Constant.TASK_INIT, CreateMode.PERSISTENT);
        }
        return true;
    }

    private static void doTaskWithOutLock(ScheduleTask task, int fireType, Date runTime) {
        String root = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK
                + Constant.NODE_SEPARATE + task.getTaskId() + Constant.getFirePath(fireType) + Constant.NODE_SEPARATE + runTime.getTime();
        try {
            if (CuratorSupport.checkExists(root)) {
                JobDetail.doJob(task.getTaskId(), fireType, runTime);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doTaskWithLock(ScheduleTask task, int fireType, Date runTime) {
        // 任务节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/locks
        String root = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK
                + Constant.NODE_SEPARATE + task.getTaskId() + Constant.getFirePath(fireType) + Constant.NODE_SEPARATE + runTime.getTime();
        String lockPath = root + Constant.NODE_LOCKS;
        try {
            if (CuratorSupport.checkExists(root)) {
                CuratorSupport.acquireLock(lockPath, task.getTaskId(), runTime.getTime(), new LockExecute() {
                    @Override
                    public void execute(Object... objects) {
                        String taskId = (String) objects[0];
                        int fireType = (int) objects[1];
                        Date runTime = (Date) objects[2];
                        JobDetail.doJob(taskId, fireType, runTime);
                    }
                }, task.getTaskId(), fireType, runTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doJob(String taskId, int fireType, Date runTime) {
        ScheduleTask scheduleTask = JobManager.getTask(taskId);
        log.info("taskId 执行, taskId={}, fireType={}, cron={}, taskType={}", taskId, fireType, scheduleTask.getCronExpression(), TaskType.getByCode(scheduleTask.getTaskType()).getDesc());
    }

    /**
     * 延迟随机 0 100 200 300 400 500ms
     */
    private static void sleep() {
        // 在执行机器列表中，抢锁执行
        int time = ThreadLocalRandom.current().nextInt(6) % 6 * 100;
        // 延迟随机 0 100 200 300 400 500ms 进行抢锁
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
