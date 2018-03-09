package com.mz.common.util;

import com.mz.web.component.QuartzJob;
import com.mz.web.constant.SystemConstant;
import com.mz.web.model.QuartzJobEntity;
import org.apache.log4j.Logger;
import org.quartz.*;

import java.util.Date;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

public class QuartzUtil {
    private static Logger logger = Logger.getLogger(QuartzUtil.class);
    private static Scheduler scheduler = (Scheduler) SpringContextUtil.getBean("scheduler");
    public static final String JOB_NAME = "Job_";
    public static final String JOB_ROUTE_NAME = "Job_Route_";
    public static final String JOB_GROUP_NAME = "jobGroupName";
    public static final String TRIGGER_NAME = "Trigger_";
    public static final String TRIGGER_ROUTE_NAME = "Trigger_Route_";
    public static final String TRIGGER_GROUP_NAME = "triggerGroupName";

    /**
     * 获取触发器key
     *
     * @param jobId
     * @return
     */
    public static TriggerKey getTriggerKey(Integer jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取jobKey
     *
     * @param jobId
     * @return
     */
    public static JobKey getJobKey(Integer jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     *
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            logger.error("获取定时任务CronTrigger出现异常", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     *
     * @param scheduleJob
     */
    public static void createScheduleJob(QuartzJobEntity quartzJobEntity) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity(getJobKey(quartzJobEntity.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJobEntity.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(quartzJobEntity.getJobId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(QuartzJobEntity.JOB_PARAM_KEY, quartzJobEntity);

            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (quartzJobEntity.getStatus() == SystemConstant.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(quartzJobEntity.getJobId());
            }
        } catch (SchedulerException e) {
            logger.error("创建定时任务失败", e);
        }
    }

    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param sched:调度器
     * @param jobClass:任务
     * @param time:时间设置，CronExpression表达式
     */
    public static void addJob(Scheduler sched, Class jobClass, String time) {
        addJob(sched, jobClass,time,JOB_NAME,JOB_GROUP_NAME,TRIGGER_NAME,TRIGGER_GROUP_NAME,null);
    }

    /**
     * @Description: 添加一个定时任务
     * @param sched:调度器
     * @param jobClass:任务
     * @param time:时间设置，CronExpression表达式
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     * @param triggerName:触发器名
     * @param triggerGroupName:触发器组名
     */
    public static void addJob(Scheduler sched, Class jobClass, String time,
                              String jobName, String jobGroupName, String triggerName, String triggerGroupName, Map param) {
        if (sched == null) {
            sched = scheduler;
        }
        JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        if (param != null) {
            job.getJobDataMap().put(QuartzJobEntity.JOB_PARAM_KEY, param);
        }
        CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName)
                .withSchedule(cronSchedule(time)).build();
        try {
            // 返回为 null 添加失败
            Date ft = sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("创建定时任务失败 - " + jobName,e);
        }
    }

    /**
     * @Description: 定义一个任务之后进行触发设定(使用默认的任务组名，触发器名，触发器组名)
     * @param sched:调度器
     * @param time
     */

    public static void addJObLaterUse(Scheduler sched, Class jobClass, String time) {

        addJObLaterUse(sched,jobClass,time,JOB_NAME,JOB_GROUP_NAME);
    }

    /**
     * @Description: 定义一个任务之后进行触发设定
     * @param sched:调度器
     * @param time
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     */
    public static void addJObLaterUse(Scheduler sched, Class jobClass, String time,
                                      String jobName,String jobGroupName) {

        JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably().build();

        try {
            sched.addJob(job, false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 对已存储的任务进行scheduling(使用默认的任务组名，触发器名，触发器组名)
     * @param sched:调度器
     * @param time
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     */
    public static void schedulingStoredJOb(Scheduler sched, Class jobClass, String time) {
        schedulingStoredJOb(sched,jobClass,time,JOB_NAME,JOB_GROUP_NAME,TRIGGER_NAME,TRIGGER_GROUP_NAME);
    }

    /**
     * @Description: 对已存储的任务进行scheduling
     * @param sched:调度器
     * @param time
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     */
    public static void schedulingStoredJOb(Scheduler sched, Class jobClass, String time,
                                           String jobName,String jobGroupName,String triggerName, String triggerGroupName) {
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).startNow()
                .forJob(jobKey(jobName,jobGroupName))
                .build();
        try {
            sched.scheduleJob(trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param sched:调度器
     * @param time
     */
    public static void modifyJobTime(Scheduler sched, String time) {
        modifyJobTime(sched, TRIGGER_NAME, TRIGGER_GROUP_NAME, time);
    }

    /**
     * @Description: 修改一个任务的触发时间
     * @param sched:调度器
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    public static Date modifyJobTime(Scheduler sched, String triggerName, String triggerGroupName, String time) {
        if (sched == null) {
            sched = scheduler;
        }
        Trigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time)).startNow().build();
        try {
            return sched.rescheduleJob(triggerKey(triggerName, triggerGroupName), trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 修改一个任务(使用默认的任务组名，任务名)
     * @param sched:调度器
     */
    public static void modifyJob(Scheduler sched, Class jobClass) {
        modifyJob(sched,jobClass,JOB_NAME,JOB_GROUP_NAME);
    }

    /**
     * @Description: 修改一个任务
     * @param sched:调度器
     * @param jobName:任务名
     * @param jobGroupName:任务组名
     */
    public static void modifyJob(Scheduler sched, Class jobClass, String jobName,String jobGroupName) {
        JobDetail job1 = newJob(jobClass).withIdentity(jobName,jobGroupName).build();
        try {
            sched.addJob(job1, true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 删除一个任务的的trigger
     * @param sched:调度器
     * @param triggerName
     * @param triggerGroupName
     */
    public static void unschedulingJob(Scheduler sched,String triggerName, String triggerGroupName) {
        try {
            sched.unscheduleJob(triggerKey(triggerName,triggerGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 移除一个任务，以及任务的所有trigger
     * @param sched:调度器
     * @param jobName
     */
    public static void removeJob(String jobName,String jobGroupName) {
        try {
            scheduler.deleteJob(jobKey(jobName,jobGroupName));
            logger.info("移除一个任务 - jobName - " + jobName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description:启动所有定时任务
     * @param sched:调度器
     */
    public static void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     * @param sched:调度器
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                //未传参或false：不等待执行完成便结束；true：等待任务执行完才结束
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停任务
     *
     * @param jobId
     */
    public static boolean pauseJob(Integer jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
            return true;
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
            return false;
        }
    }

    /**
     * 恢复任务
     *
     * @param jobId
     */
    public static void resumeJob(Integer jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            logger.error("恢复定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobId
     */
    public static void deleteScheduleJob(Integer jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败", e);
        }
    }
}
