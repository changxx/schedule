package com.changxx.schedule.console;

import com.changxx.schedule.client.job.ManualTask;
import com.changxx.schedule.client.job.ScheduleTask;
import com.changxx.schedule.constant.Constant;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        task.setTaskId("task-test-one");
        task.setCronExpression("0/10 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ONE);
        boolean flag = TaskService.addToZk(task);
        System.out.println(flag);
    }

    @Test
    public void test_update() {
        ScheduleTask task = new ScheduleTask();
        task.setTaskId("task-test-one");
        task.setCronExpression("0/5 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ONE);
        boolean flag = TaskService.updateZk(task);
        System.out.println(flag);
    }

    @Test
    public void test_delete() {
        ScheduleTask task = new ScheduleTask();
        task.setTaskId("task-test7");
        task.setCronExpression("0/10 * * * * ? *");
        task.setTaskType(Constant.TASK_TYPE_ALL);
        boolean flag = TaskService.deleteZk(task);
        System.out.println(flag);
    }

    @Test
    public void test_manualExecute() {
        ManualTask task = new ManualTask();
        task.setTaskId("task-test-one");
        task.setTime(System.currentTimeMillis());
        task.setServers(new ArrayList<>(Arrays.asList(new String[]{""})));
        TaskService.manualExecute(task);
    }

}
