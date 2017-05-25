package com.changxx.schedule.curator;

import com.changxx.schedule.constant.Constant;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * client启动向ZK注册(临时节点)，控制中心会从注册的client列表中分配可以执行定时任务的机器
 */
public class ServerRegister {

    private static final Logger log = LoggerFactory.getLogger(CuratorSupport.class);

    /**
     * 向zk注册服务机器名
     * 注册临时节点:/kschedule/{group}/servers/{hostName}
     */
    public static void register() {
        String zkPath1 = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2;
        if (!CuratorSupport.checkExists(zkPath1)) {
            CuratorSupport.create(zkPath1);
        }

        String zkPath2 = zkPath1 + Constant.NODE_SERVERS;
        if (!CuratorSupport.checkExists(zkPath2)) {
            CuratorSupport.create(zkPath2);
        }

        String zkPath3 = zkPath2 + Constant.NODE_SEPARATE + Constant.LOCAL_HOSTNAME;
        if (CuratorSupport.checkExists(zkPath3)) {
            CuratorSupport.delete(zkPath3);
            CuratorSupport.create(zkPath3, Constant.LOCAL_HOSTNAME, CreateMode.EPHEMERAL);
            log.info("register server success[update]. path=" + zkPath3);
        } else {
            CuratorSupport.create(zkPath3, Constant.LOCAL_HOSTNAME, CreateMode.EPHEMERAL);
            log.info("register server success[init]. path=" + zkPath3);
        }
    }

}
