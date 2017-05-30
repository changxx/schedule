package com.changxx.schedule.pool;

import java.util.concurrent.*;

/**
 * ThreadPool
 *
 * @author changxiangxiang
 * @date 2017/5/28
 */
public class ThreadPool {

    private ExecutorService exec;

    private static volatile ThreadPool instance;

    /**
     * 执行任务线程池
     */
    private ThreadPool() {
        exec = new ThreadPoolExecutor(20, 100, 5 * 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public static ThreadPool getInstance() {

        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = new ThreadPool();
                }
            }
        }

        return instance;
    }

    public void exec(Runnable command) {
        //判断活跃线程数
        int count = ((ThreadPoolExecutor) exec).getActiveCount();
        exec.execute(command);
    }

    public <T> Future<T> submit(Callable<T> command) {

        return exec.submit(command);
    }

    public void shutdown() {

        exec.shutdown();
    }

    public void shutdownNow() {

        exec.shutdownNow();
    }

    public boolean isTerminated() {

        return exec.isTerminated();
    }

    class TestRunner implements Callable<String> {

        public String call() {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            return "OK";
        }
    }

}
