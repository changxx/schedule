package com.changxx.schedule.console;

import com.changxx.schedule.client.job.ScheduleTask;
import com.changxx.schedule.constant.Constant;
import org.junit.Test;

/**
 * TaskServiceTest
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class TaskServiceTest {

    @Test
    public void test_add() {
        ScheduleTask task = new ScheduleTask();
        task.setTaskId("task-test10");
        task.setCronExpression("0/10 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ALL);
        boolean flag = TaskService.addToZk(task);
        System.out.println(flag);
    }

    @Test
    public void test_update() {
        ScheduleTask task = new ScheduleTask();
        task.setTaskId("task-test10");
        task.setCronExpression("0/10 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ALL);
        boolean flag = TaskService.updateZk(task);
        System.out.println(flag);
    }

}
