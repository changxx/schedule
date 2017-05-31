package com.changxx.schedule.constant;

import com.changxx.schedule.util.IpUtil;


public final class Constant {

    public static final int RUN_ON_NONE = 0;// 执行方式-都不执行
    public static final int RUN_ON_ONE = 1;// 执行方式-所有的机器上只有一台执行
    public static final int RUN_ON_ALL = 2;// 执行方式-所有机器都执行
    public static final int RUN_ON_ASSIGN = 3;// 执行方式-指定机器上全部执行
    public static final int RUN_ON_ASSIGN_ONE = 4;// 执行方式-指定机器上只有一台全部执行

    public static final String KSCHEDULE_GROUP_NAME_2 = "changxx";// 资源配置-分组信息

    public static final String TASK_INIT = "init";// 正在执行中
    public static final String TASK_DOING = "doing";// 正在执行中
    public static final String TASK_DONE = "done";// 已经执行完成

    public static String LOCAL_HOSTNAME = IpUtil.getLocalHostName();// 机器名

    public static final int FIRE_TYPE_CRON = 0;// 调用方式-定时执行
    public static final int FIRE_TYPE_INVOKE = 1;// 调用方式-手动调用
    public static final int FIRE_TYPE_START = 2;// 调用方式-启动调用
    public static final int FIRE_TYPE_RPC = 3;// 调用方式-RPC调用

    public static final String NODE_SEPARATE = "/";//节点分隔符
    public static final String NODE_TASK = "/task";//任务节点 位置:/root/{group}/task
    public static final String NODE_CRON = "/cron";//任务节点 位置:/root/{group}/task/{taskId}/cron
    public static final String NODE_TASK_MANUAL = "/manual";// 任务节点下的手动触发节点 位置：/root/{group}/task/{taskId}/manual
    public static final String NODE_TASK_RPC = "/rpc";//任务节点下的RPC触发节点位置：/root/{group}/task/{taskId}/rpc
    public static final String NODE_SERVERS = "/servers";//服务器节点 位置:/root/{group}/servers
    public static final String NODE_TASK_STARTUP = "/startup";//任务节点下的启动触发节点 位置：/root/{group}/task/{taskId}/startup
    public static final String NODE_LOCKS = "/locks";//任务节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/locks
    public static final String NODE_RUNTIME = "/runtime";// zk上每次任务执行的监听结点：/cron/{group}/{taskId}/{runtime}
    public static final int TASK_TYPE_ONE = 1; //任务类型：表示所有的机器上只有一台执行
    public static final int TASK_TYPE_ALL = 2; //任务类型：表示所有机器都执行
    public static final int TASK_TYPE_SPECIFY_ALL = 3; // 任务类型：指定机器上全部执行
    public static final int TASK_TYPE_SPECIFY_ONE = 4; //任务类型：表示只有一台指定的机器执行

    public static final int TASK_LOG_FIRETYPE_CRON = 0; //任务触发类型：0表示定时触发
    public static final int TASK_LOG_FIRETYPE_MANUAL = 1; //任务触发类型：1表示手动触发

    public static String getFirePath(int fireType) {
        switch (fireType) {
            case Constant.FIRE_TYPE_CRON:
                return Constant.NODE_CRON;
            case Constant.FIRE_TYPE_INVOKE:
                return Constant.NODE_TASK_MANUAL;
            case Constant.FIRE_TYPE_START:
                return Constant.NODE_TASK_STARTUP;
            case Constant.FIRE_TYPE_RPC:
                return Constant.NODE_TASK_RPC;
            default:
                break;
        }
        return Constant.NODE_CRON;
    }


}
