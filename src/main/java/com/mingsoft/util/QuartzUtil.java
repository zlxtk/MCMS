/*   1:    */ package com.mingsoft.util;
/*   2:    */ 
/*   3:    */ import org.quartz.CronScheduleBuilder;
/*   4:    */ import org.quartz.CronTrigger;
/*   5:    */ import org.quartz.JobBuilder;
/*   6:    */ import org.quartz.JobDetail;
/*   7:    */ import org.quartz.JobKey;
/*   8:    */ import org.quartz.Scheduler;
/*   9:    */ import org.quartz.SchedulerFactory;
/*  10:    */ import org.quartz.Trigger;
/*  11:    */ import org.quartz.TriggerBuilder;
/*  12:    */ import org.quartz.TriggerKey;
/*  13:    */ import org.quartz.impl.StdSchedulerFactory;
/*  14:    */ 
/*  15:    */ public class QuartzUtil
/*  16:    */ {
/*  17: 45 */   private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
/*  18: 48 */   private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
/*  19: 49 */   private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
/*  20:    */   
/*  21:    */   public static void addJob(String jobName, Class cls, String time)
/*  22:    */   {
/*  23:    */     try
/*  24:    */     {
/*  25: 64 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  26: 65 */       JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
/*  27: 66 */       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME)).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
/*  28: 67 */       sched.scheduleJob(jobDetail, trigger);
/*  29: 69 */       if (!sched.isShutdown()) {
/*  30: 70 */         sched.start();
/*  31:    */       }
/*  32:    */     }
/*  33:    */     catch (Exception e)
/*  34:    */     {
/*  35: 73 */       throw new RuntimeException(e);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time)
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 96 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  44: 97 */       JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
/*  45: 98 */       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(triggerName, triggerGroupName)).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
/*  46: 99 */       sched.scheduleJob(jobDetail, trigger);
/*  47:    */     }
/*  48:    */     catch (Exception e)
/*  49:    */     {
/*  50:101 */       throw new RuntimeException(e);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void modifyJobTime(String jobName, String time)
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58:114 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  59:115 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
/*  60:116 */       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
/*  61:117 */       if (trigger == null) {
/*  62:118 */         return;
/*  63:    */       }
/*  64:120 */       String oldTime = trigger.getCronExpression();
/*  65:121 */       if (!oldTime.equalsIgnoreCase(time))
/*  66:    */       {
/*  67:122 */         JobKey job = JobKey.jobKey(jobName);
/*  68:123 */         JobDetail jobDetail = sched.getJobDetail(job);
/*  69:124 */         Class objJobClass = jobDetail.getJobClass();
/*  70:125 */         removeJob(jobName);
/*  71:126 */         addJob(jobName, objJobClass, time);
/*  72:    */       }
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:129 */       throw new RuntimeException(e);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void modifyJobTime(String triggerName, String triggerGroupName, String time)
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:142 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  85:143 */       TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
/*  86:144 */       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
/*  87:145 */       if (trigger == null) {
/*  88:146 */         return;
/*  89:    */       }
/*  90:148 */       String oldTime = trigger.getCronExpression();
/*  91:149 */       if (!oldTime.equalsIgnoreCase(time))
/*  92:    */       {
/*  93:150 */         trigger.getTriggerBuilder().startNow().withSchedule(CronScheduleBuilder.cronSchedule(time));
/*  94:    */         
/*  95:152 */         sched.resumeTrigger(triggerKey);
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:155 */       throw new RuntimeException(e);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void removeJob(String jobName)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:166 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 109:167 */       JobKey job = JobKey.jobKey(jobName, JOB_GROUP_NAME);
/* 110:168 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
/* 111:169 */       sched.pauseTrigger(triggerKey);
/* 112:170 */       sched.unscheduleJob(triggerKey);
/* 113:171 */       sched.deleteJob(job);
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:173 */       throw new RuntimeException(e);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName)
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:187 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 126:188 */       JobKey job = JobKey.jobKey(jobName, jobGroupName);
/* 127:189 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
/* 128:190 */       sched.pauseTrigger(triggerKey);
/* 129:191 */       sched.unscheduleJob(triggerKey);
/* 130:192 */       sched.deleteJob(job);
/* 131:    */     }
/* 132:    */     catch (Exception e)
/* 133:    */     {
/* 134:194 */       throw new RuntimeException(e);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void startJobs()
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:203 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 143:204 */       sched.start();
/* 144:    */     }
/* 145:    */     catch (Exception e)
/* 146:    */     {
/* 147:206 */       throw new RuntimeException(e);
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static void shutdownJobs()
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:216 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 156:217 */       if (!sched.isShutdown()) {
/* 157:218 */         sched.shutdown();
/* 158:    */       }
/* 159:    */     }
/* 160:    */     catch (Exception e)
/* 161:    */     {
/* 162:221 */       throw new RuntimeException(e);
/* 163:    */     }
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\Administrator\Desktop\ms\ms-util-1.0.1.jar
 * Qualified Name:     com.mingsoft.util.QuartzUtil
 * JD-Core Version:    0.7.0.1
 */