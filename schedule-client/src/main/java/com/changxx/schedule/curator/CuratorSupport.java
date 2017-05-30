package com.changxx.schedule.curator;

import com.changxx.schedule.ScheduleClientFactory;
import com.changxx.schedule.client.job.JobManager;
import com.changxx.schedule.constant.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * CuratorSupport
 *
 * @author changxiangxiang
 * @date 2017/5/10
 */
public class CuratorSupport {

    private static final Logger log = LoggerFactory.getLogger(CuratorSupport.class);

    public static volatile CuratorFramework client = null;

    /**
     * Zookeeper info
     */
    private static final String ZK_ADDRESS = "127.0.0.1:2181";


    /**
     * 客户端连接zk初始化方法
     */
    public static CuratorFramework initZK() {
        String root = "schedule";
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().connectString(ZK_ADDRESS)
                .retryPolicy(new RetryOneTime(1000))
                .connectionTimeoutMs(1000)
                .sessionTimeoutMs(1000)
                .namespace(root);
        client = builder.build();
        client.start();
        boolean established = false;
        try {
            log.info("[CuratorFramework] connectting to kschedule zkclient......");
            client.blockUntilConnected();
            established = true;
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (established) {
            // 向zookeeper注册
            ServerRegister.register();

            // 监听任务节点下子节点的变化情况
            CommonListener.tasksListener();

            log.info("[CuratorFramework] init end.已连接到kschedule的zookeeper");

            // 启动scheduler
            try {
                if (!JobManager.getScheduler().isStarted()) {
                    JobManager.getScheduler().start();
                }
                log.info("[StartUp]startScheduler has been done.");
            } catch (Exception e) {
                log.error("[StartUp]startScheduler error .", e);
            }

            // 刷新任务
            ScheduleClientFactory.refreshTask();

            return client;
        } else {
            log.info("[CuratorFramework] init error.未连接到kschedule的zookeeper");
            CloseableUtils.closeQuietly(client);
            client = null;
        }
        return null;
    }

    private static void check() {
        if (null == client) {
            synchronized (CuratorSupport.class) {
                if (client == null) {
                    initZK();
                    return;
                }
            }
        }
    }

    public static boolean checkExists(final String znode) {
        check();
        try {
            return null != client.checkExists().forPath(znode);
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static String create(final String znode) {
        check();
        try {
            log.info("zk create node znode: {}", znode);
            return client.create().forPath(znode, "".getBytes("UTF-8"));
        } catch (final KeeperException.NodeExistsException ex) {
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String create(final String znode, final String data, final CreateMode createmode) {
        check();
        try {
            return client.create().withMode(createmode).forPath(znode, data.getBytes("UTF-8"));
        } catch (final KeeperException.NodeExistsException ex) {
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String getData(final String znode) {
        check();
        try {
            if (checkExists(znode)) {
                return new String(client.getData().forPath(znode), Charset.forName("UTF-8"));
            } else {
                return null;
            }
        } catch (final KeeperException.NoNodeException ex) {
            return null;
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static void update(final String znode, final String value) {
        check();
        try {
            client.inTransaction().check().forPath(znode).and().setData().forPath(znode, value.getBytes(Charset.forName("UTF-8"))).and().commit();
        } catch (final KeeperException.NoNodeException ex) {
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static void delete(final String znode) {
        check();
        try {
            if (null != client.checkExists().forPath(znode)) {
                client.delete().forPath(znode);
            }
        } catch (final KeeperException.NoNodeException ex) {
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static void deleteWithChildren(final String znode) {
        check();
        try {
            if (null != client.checkExists().forPath(znode)) {
                client.delete().deletingChildrenIfNeeded().forPath(znode);
            }
        } catch (final KeeperException.NoNodeException ex) {
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static List<String> getChildren(final String znode) {
        check();
        try {
            //首先判断该节点是否存在
            if (checkExists(znode)) {
                return client.getChildren().forPath(znode);
            } else {
                return null;
            }
            //CHECKSTYLE:OFF
        } catch (final Exception ex) {
            //CHECKSTYLE:ON
            throw new RuntimeException(ex);
        }
    }

    public static boolean acquireLock(final String znode, String taskId, long runTime, LockExecute lockExecute, Object... objects) {
        // zk上每次任务执行的监听结点：/cron/{group}/{taskId}/{runtime}
        String root = "/" + Constant.KSCHEDULE_GROUP_NAME_2 + Constant.NODE_TASK + "/" + taskId + "/" + runTime;
        InterProcessMutex lock = new InterProcessMutex(client, znode);
        try {
            lock.acquire();
            String taskStat = CuratorSupport.getData(root);
            log.info("获得锁, lockPath: {}, taskStat: {}", znode, taskStat);
            if (taskStat == null && checkExists(znode)) {
                CuratorSupport.create(root, Constant.TASK_DOING, CreateMode.PERSISTENT);
                lockExecute.execute(objects);
                deleteWithChildren(znode);
                CuratorSupport.update(root, Constant.TASK_DONE);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (lock.isAcquiredInThisProcess()) {
//                    log.info("释放锁, lockPath: {}", znode);
                    lock.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
