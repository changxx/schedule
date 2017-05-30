package com.changxx.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * ScheduleJobDetail
 *
 * @author changxiangxiang
 * @date 2017/5/30
 */
public class ScheduleJobDetail implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(context.getTrigger().getJobKey().getName());
    }

}
