package com.changxx.schedule.curator;

import com.changxx.schedule.ScheduleClientFactory;
import com.changxx.schedule.constant.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ZK节点监听器
 */
public class CommonListener {

    private static final Logger log = LoggerFactory.getLogger(CommonListener.class);

    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    /**
     * 监听任务节点下子节点的变化情况
     * 监听路径:/kschedule/{group}/task
     */
    public static void tasksListener() {
        String zkPath2 = Constant.NODE_SEPARATE + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK;
        if (!CuratorSupport.checkExists(zkPath2)) {
            CuratorSupport.create(zkPath2);
        }
        final PathChildrenCache childrenCache = new PathChildrenCache(CuratorSupport.client, zkPath2, false);
        try {
            childrenCache.start(StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            log.info("[Task Listener] 初始化监听任务列表节点异常，有可能导致改client无法更新任务列表。", e);
        }
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (PathChildrenCacheEvent.Type.CHILD_ADDED == event.getType()
                        || PathChildrenCacheEvent.Type.CHILD_UPDATED == event.getType()
                        || PathChildrenCacheEvent.Type.CHILD_REMOVED == event.getType()) {
                    log.info("[Task Listener]监听任务列表发生变化，更新本地任务列表。" + event.getType());
                    // 节点变化，更新任务列表
                    ScheduleClientFactory.refreshTask();
                }
            }
        }, pool);
    }

}
