package com.changxx.schedule;

import com.changxx.schedule.client.job.JobManager;
import com.changxx.schedule.curator.CuratorSupport;

/**
 * App
 *
 * @author changxiangxiang
 * @date 2017/5/24
 */
public class App {

    public static void main(String[] args) {
        JobManager.initScheduler();
        CuratorSupport.initZK();
    }

}
