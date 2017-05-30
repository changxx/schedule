package com.changxx.schedule.console;

import com.changxx.schedule.client.job.ManualTask;
import com.changxx.schedule.client.job.ScheduleTask;
import com.changxx.schedule.constant.Constant;
import com.changxx.schedule.curator.CuratorSupport;
import com.changxx.schedule.util.FastJsonUtil;
import org.apache.zookeeper.CreateMode;

/**
 * TaskService
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class TaskService {

    /**
     * 添加zk
     * /root/{group}/task/{taskId}
     */
    public static boolean addToZk(ScheduleTask task) {
        String zkPath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2;
        if (!CuratorSupport.checkExists(zkPath)) {
            CuratorSupport.create(zkPath, "", CreateMode.PERSISTENT);
        }
        String serversPath = zkPath + Constant.NODE_SERVERS;
        if (!CuratorSupport.checkExists(serversPath)) {
            CuratorSupport.create(serversPath, "", CreateMode.PERSISTENT);
        }
        zkPath = zkPath + Constant.NODE_TASK;
        if (!CuratorSupport.checkExists(zkPath)) {
            CuratorSupport.create(zkPath, "", CreateMode.PERSISTENT);
        }
        zkPath = zkPath + Constant.NODE_SEPARATE + task.getTaskId();
        String content = FastJsonUtil.toJSONString(task);
        CuratorSupport.create(zkPath, content, CreateMode.PERSISTENT);
        // /root/{group}/task/{taskId}/manual
        CuratorSupport.create(zkPath + Constant.NODE_TASK_MANUAL, "", CreateMode.PERSISTENT);
        return true;
    }

    /**
     * 修改zk
     * /root/{group}/task/{taskId}
     */
    public static boolean updateZk(ScheduleTask task) {
        String zkPath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2;
        zkPath = zkPath + Constant.NODE_TASK;
        zkPath = zkPath + Constant.NODE_SEPARATE + task.getTaskId();
        CuratorSupport.update(zkPath, FastJsonUtil.toJSONString(task));
        return true;
    }

    /**
     * 删除
     * /root/{group}/task/{taskId}
     */
    public static boolean deleteZk(ScheduleTask task) {
        String zkPath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2;
        zkPath = zkPath + Constant.NODE_TASK;
        zkPath = zkPath + Constant.NODE_SEPARATE + task.getTaskId();
        CuratorSupport.deleteWithChildren(zkPath);
        return true;
    }

    /**
     * 删除
     * 路径:/root/{group}/task/{taskId}/manual
     */
    public static boolean manualExecute(ManualTask task) {
        String zkPath = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2;
        zkPath = zkPath + Constant.NODE_TASK;
        zkPath = zkPath + Constant.NODE_SEPARATE + task.getTaskId();
        zkPath = zkPath + Constant.NODE_TASK_MANUAL;
        CuratorSupport.update(zkPath, FastJsonUtil.toJSONString(task));
        return true;
    }

}
