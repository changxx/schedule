package com.changxx.schedule.constant;

import com.changxx.schedule.util.IpUtil;
import org.springframework.context.ApplicationContext;


public final class Constant {

    public static final String DIGEST = "kschedule";
    public static final String OLD_QUARTZ_VERSION = "1";
    public static final String ROOT = "kschedule";
    public static final String SERVERS_LOAD = "serversLoad";
    public static final String METHOD_SPLIT_SIGN = ";"; // 方法以及方法签名分隔符

    public static final String JOB_FULL_NAME = "com.netease.kaola.kschedule.client.job.ScheduleJob";
    public static final String JOB_SHORT_NAME = "ScheduleJob";

    public static final String CLASS_SUFFIX = ".class";
    public static final String HOSTNAME_SUFFIX = ".server.163.org";

    public static final int RUN_ON_NONE = 0;// 执行方式-都不执行
    public static final int RUN_ON_ONE = 1;// 执行方式-所有的机器上只有一台执行
    public static final int RUN_ON_ALL = 2;// 执行方式-所有机器都执行
    public static final int RUN_ON_ASSIGN = 3;// 执行方式-指定机器上全部执行
    public static final int RUN_ON_ASSIGN_ONE = 4;// 执行方式-指定机器上只有一台全部执行
    public static final int RUN_ON_SHARDING = 5;// 执行方式-分片执行

    public static final int USE_CPU_LOAD_YES = 1;//使用CPU负载作为机器分配依据
    public static final int USE_CPU_LOAD_NO = 0;//不使用CPU负载作为机器分配的依据

    public static ApplicationContext applicationContext;

    public static final int FIRE_ON_STARTUP = 1;// 启动执行标识

    public static final String ZK_CONNECT_STRING = "zookeeper.connect.url";// 资源配置-zk连接地址
    public static final String ZK_CONNECT_TIMEOUT = "zookeeper.connect.timeout";// 资源配置-zk连接超时时间
    public static final String ZK_SESSION_TIMEOUT = "zookeeper.session.timeout";// 资源配置-zksession超时时间
    public static final String KSCHEDULE_GROUP_NAME = "group.name";// 资源配置-分组信息
    public static final String KSCHEDULE_GROUP_NAME_2 = "changxx";// 资源配置-分组信息
    public static final String KSCHEDULE_EXCLUSIVE_LOCK = "exclusive.lock";// 是否使用互斥锁
    public static final String QZ_THREADPOOL_COUNT = "org.quartz.threadPool.threadCount";// 资源配置-quartz线程数
    public static final String QZ_THREADPOOL_CLASS = "org.quartz.threadPool.class";// 资源配置-quartz 线程池类
    public static final String QZ_THREADPOOL_PRIORITY = "org.quartz.threadPool.threadPriority";// 资源配置-quartz线程优先级
    public static final String KS_QUARTZ_VERSION = "quartz.version";// 资源配置-quartz版本
    public static final String CORE_POOL_SIZE = "core.pool.size";// 资源配置-quartz版本
    public static final String MAX_POOL_SIZE = "max.pool.size";// 资源配置-quartz版本
    public static final String MAX_THREAD_WAIT = "max.thread.wait";// 资源配置-quartz版本
    public static final String MAX_WAIT_SECONDS = "max.wait.seconds";// 资源配置-quartz版本

    public static final int ZK_DEFAULT_TIME_OUT = 3000;// zk连接默认等待时间
    public static final int ZK_DEFAULT_SESSION_TIME_OUT = 10000;// zk默认session等待时间
    public static final String CURRENT_QUARTZ_VERSION = "2";// 当前quartz版本

    public static final String TASK_INIT = "init";// 正在执行中
    public static final String TASK_DOING = "doing";// 正在执行中
    public static final String TASK_DONE = "done";// 已经执行完成

    public static String LOCAL_HOSTNAME = IpUtil.getLocalHostName();// 机器名

    public static final int OTHER_MACHINE_WAIT_TIME = 500;// 其他机器注册结点等待时间：500ms

    public static final int FIRE_TYPE_CRON = 0;// 调用方式-定时执行
    public static final int FIRE_TYPE_INVOKE = 1;// 调用方式-手动调用
    public static final int FIRE_TYPE_START = 2;// 调用方式-启动调用
    public static final int FIRE_TYPE_RPC = 3;// 调用方式-RPC调用

    public static int THREAD_POOL_CORE_SIZE = 20;// 线程池最少线程数
    public static int THREAD_POOL_MAX_SIZE = 200;// 最大线程数
    public static int THREAD_MAX_THREAD_WAIT = 1000;// 最大线程等待数  modify wangjn 这个值设置过大会导致超过corePoolSize的线程无法执行
    public static int THREAD_POOL_WAIT_SECONDS = 5 * 60;// 最长等待时间

    public static final String RESULT_DESC = "result_desc"; // 结果返回描述
    public static final String RESULT_ERRORMSG = "result_err_msg"; // 结果返回异常
    public static final String RESULT_CODE = "result_code"; // 结果返回编码
    public static final int SUCCESS_CODE = 200; //操作成功
    public static final int ZK_ERROR_CODE = 401; // zk错误
    public static final int TASK_ERROR_CODE = 402;// 任务本身抛错

    public static final String NODE_SEPARATE = "/";//节点分隔符
    public static final String NODE_TASK = "/task";//任务节点 位置:/root/{group}/task
    public static final String NODE_CRON = "/cron";//任务节点 位置:/root/{group}/task/{taskId}/cron
    public static final String NODE_SERVICELIST = "/servicelist";//任务节点 位置:/root/{group}/task/{taskId}/servicelist
    public static final String NODE_TASK_MANUAL = "/manual";// 任务节点下的手动触发节点 位置：/root/{group}/task/{taskId}/manual
    public static final String NODE_TASK_RPC = "/rpc";//任务节点下的RPC触发节点位置：/root/{group}/task/{taskId}/rpc
    public static final String NODE_SERVERS = "/servers";//服务器节点 位置:/root/{group}/servers
    public static final String NODE_TASK_STARTUP = "/startup";//任务节点下的启动触发节点 位置：/root/{group}/task/{taskId}/startup
    public static final String NODE_TASK_RUNTIME = "/runtime";//任务节点下的启动触发节点 位置：/root/{group}/task/{taskId}/runtime
    public static final String NODE_LOCKS = "/locks";//任务节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/locks
    public static final String NODE_LOGS = "/logs";//任务节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/logs
    public static final String NODE_CONTEXTS = "/contexts";//分片分配信息节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/contexts
    public static final String NODE_SHARDING = "/sharding";//分片状态信息节点 位置:/root/{group}/task/{taskId}/{firetype}/{runtime}/sharding
    public static final String NODE_UNSCHEDULED_SERVERS = "/stopScheduledServers";//取消调度的服务器的节点信息：/root/{group}/stopScheduledServers
    public static final String NODE_SHARDING_LOCKS = "/shardingLock";//分片锁节点 位置:/root/{group}/task/{taskId}/shardingLock;

    public static final int TASK_STATE_RUNNING = 0; // 任务状态，0表示正常
    public static final int TASK_STATE_STOP = 1; //任务状态，1表示停止
    public static final int TASK_STATE_DELETE = 2;//任务状态：2表示删除
    public static final int TASK_STATE_SAVED = 3;//任务状态：2表示删除
    public static final int TASK_STATE_APPROVE = 4;//任务状态：2表示删除

    public static final int TASK_NEED_NOT_APPROVE = 0;//任务不需要审批
    public static final int TASK_NEED_APPROVE = 1;//任务需要审批

    public static final int TASK_EXCUTE_STRATEGY_NOMISS = 0; // 任务执行策略：0表示不遗漏
    public static final int TASK_EXCUTE_STRATEGY_NOREPEAT = 1; //任务执行策略：1表示不重复

    public static final int FIRE_ON_STARTUP_NO = 0; //启动是否执行：0表示不执行
    public static final int FIRE_ON_STARTUP_YES = 1; //启动是否执行：1表示执行

    public static final int TASK_TYPE_NONE = 0; // 任务类型：都不执行
    public static final int TASK_TYPE_ONE = 1; //任务类型：表示所有的机器上只有一台执行
    public static final int TASK_TYPE_ALL = 2; //任务类型：表示所有机器都执行
    public static final int TASK_TYPE_SPECIFY_ALL = 3; // 任务类型：指定机器上全部执行
    public static final int TASK_TYPE_SPECIFY_ONE = 4; //任务类型：表示只有一台指定的机器执行

    public static final int TASK_LOG_EXECUTESTATE_INIT = 0; // 任务执行状态：0表示初始化
    public static final int TASK_LOG_EXECUTESTATE_DOING = 1; // 任务执行状态：1表示执行中
    public static final int TASK_LOG_EXECUTESTATE_DONE = 2; // 任务执行状态：2表示执行成功
    public static final int TASK_LOG_EXECUTESTATE_DELETE = 3;//任务执行状态：3表示待删除

    public static final int TASK_LOG_FIRETYPE_CRON = 0; //任务触发类型：0表示定时触发
    public static final int TASK_LOG_FIRETYPE_MANUAL = 1; //任务触发类型：1表示手动触发
    public static final int TASK_LOG_FIRETYPE_STARTUP = 2; //任务触发类型：2表示启动触发

    public static final int OPERATE_SEE = 0; //查看操作
    public static final int OPERATE_EDIT = 1; //编辑操作
    public static final int OPERATE_START = 2; //启动操作
    public static final int OPERATE_STOP = 3; //暂停操作
    public static final int OPERATE_EXE_IMMEDIATELY = 4; //立即执行
    public static final int OPERATE_DELETE = 5; //删除操作
    public static final int OPERATE_ADD_TASK = 6; //添加任务操作
    public static final int OPERATE_ADD_SERVERLIST = 7; //添加机器
    public static final int OPERATE_ADD_SYSTEMINI = 8;//添加系统配置
    public static final int OPERATE_IMPORT_SERVER = 9;//导入注册机器
    public static final int OPERATE_APPROVE = 10; //审批通过操作
    public static final int OPERATE_REJECT = 11; //审批驳回操作

    public static final int TASK_EXE_RESULT_SUCCESS = 0; //任务执行成功
    public static final int TASK_EXE_RESULT_FAIL = 1; //任务执行失败
    public static final int TASK_EXE_RESULT_REPEAT = 2; //上一次任务正在执行,取消本次执行/该分片{}正在执行中,停止本次执行

    public static final int CHECK_TASK_DONE_ERROR = 1; //checkTaskDone error
    public static final int CHECK_TASK_FIRE_ERROR = 2; //checkTaskFire error
    public static final int COLLECT_TASK_LOG_ERROR = 3; //collectTaskLog error
    public static final int ASSIGN_TASK_ERROR = 4; //assignTask error


    //
    public static boolean HAS_CLIENT_TRACE = false; //assignTask error

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
