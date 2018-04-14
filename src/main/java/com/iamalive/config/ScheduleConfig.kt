package com.iamalive.config

import com.iamalive.schedule.Schedule
import com.iamalive.task.CheckAliveTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
@EnableScheduling
open class ScheduleConfig

@Autowired
constructor(private val schedule: Schedule,
            private val checkAliveTask: CheckAliveTask) : SchedulingConfigurer {

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler())
        taskRegistrar.addTriggerTask(
            checkAliveTask,
            Trigger { ctx ->
                val next = GregorianCalendar()
                next.time = ctx.lastActualExecutionTime() ?: Date()
                next.add(Calendar.SECOND, schedule.delay)
                next.time
            }
        )
    }

    @Bean(destroyMethod = "shutdown")
    open fun taskScheduler(): Executor {
        return Executors.newScheduledThreadPool(100)
    }

}
