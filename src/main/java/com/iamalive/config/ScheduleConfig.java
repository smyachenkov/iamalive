package com.iamalive.config;

import com.iamalive.schedule.Schedule;
import com.iamalive.task.CheckAliveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

  private Schedule schedule;

  private CheckAliveTask checkAliveTask;

  @Autowired
  public ScheduleConfig(Schedule schedule, CheckAliveTask checkAliveTask) {
    this.schedule = schedule;
    this.checkAliveTask = checkAliveTask;
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskScheduler());
    taskRegistrar.addTriggerTask(
        checkAliveTask,
        ctx -> {
            Calendar next = new GregorianCalendar();
            next.setTime(Optional.ofNullable(ctx.lastActualExecutionTime()).orElse(new Date()));
            next.add(Calendar.SECOND, schedule.getDelay());
            return next.getTime();
        }
    );
  }

  @Bean(destroyMethod = "shutdown")
  public Executor taskScheduler() {
    return Executors.newScheduledThreadPool(100);
  }

}
