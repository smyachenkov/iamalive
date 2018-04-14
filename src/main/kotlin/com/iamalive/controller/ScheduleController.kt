package com.iamalive.controller

import com.iamalive.dto.ScheduleDto
import com.iamalive.schedule.Schedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schedule", produces = arrayOf("application/json"))
class ScheduleController

@Autowired
constructor(private val schedule: Schedule) {

    /**
     * Check current status.
     * @return schedule with list of urls and their status
     */
    @GetMapping("/status")
    @ResponseBody
    fun status(): Schedule {
        return this.schedule
    }

    /**
     * Update status check delay.
     * @param scheduleDto json Schedule entity with present delay
     * @return schedule with list of urls and their status
     */
    @PostMapping("/set_delay")
    @ResponseBody
    fun setDelay(@RequestBody scheduleDto: ScheduleDto): Schedule {
        if (scheduleDto.delay !== null) {
            this.schedule.delay = scheduleDto.delay
        }
        return this.schedule
    }

    /**
     * Add list of urls to current schedule.
     * @param scheduleDto json Schedule entity with present list of urls
     * @return schedule with list of urls and their status
     */
    @PostMapping("/add_url")
    @ResponseBody
    fun addUrl(@RequestBody scheduleDto: ScheduleDto): Schedule {
        scheduleDto.urls?.forEach {
            url -> this.schedule.addUrl(url.trim { it <= ' ' })
        }
        return this.schedule
    }

    /**
     * Remove list of urls from current schedule.
     * @param scheduleDto json Schedule with present list of urls
     * @return schedule with list of urls and their status
     */
    @PostMapping("/delete_url")
    @ResponseBody
    fun deleteUrl(@RequestBody scheduleDto: ScheduleDto): Schedule {
        scheduleDto.urls?.forEach {
            url -> this.schedule.removeUrl(url.trim { it <= ' ' })
        }
        return this.schedule
    }

    /**
     * Completely overwrites current schedule with new data.
     * @param scheduleDto json Schedule entity with all fields present
     * @return schedule with list of urls and their status
     */
    @PostMapping("/set_schedule")
    @ResponseBody
    fun setSchedule(@RequestBody scheduleDto: ScheduleDto): Schedule {
        if (scheduleDto.delay != null && scheduleDto.urls != null) {
            this.schedule.urls.clear()
            scheduleDto.urls.forEach {
                it -> this.schedule.addUrl(it)
            }
            this.schedule.delay = scheduleDto.delay
        }
        return this.schedule
    }

}
