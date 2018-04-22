package com.iamalive.controller

import com.iamalive.dto.ScheduleDto
import com.iamalive.schedule.Schedule
import org.junit.Test

import org.junit.Assert.*


class ScheduleControllerTest {

    @Test
    fun statusReturnsCurrentSchedule() {
        val schedule = Schedule()
        val controller = ScheduleController(schedule)
        assertEquals("status must return current Schedule",
                schedule, controller.status())
        schedule.delay = 10
        assertEquals("status must return current Schedule",
                schedule.delay, controller.status().delay)
    }


    @Test
    fun setDelayAcceptsNewValue() {
        val schedule = Schedule()
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(null, 30)
        controller.setDelay(dto)
        assertEquals("setDelay must accept new value",
                dto.delay, schedule.delay)
    }

    @Test
    fun setDelayIgnoresEmpty() {
        val schedule = Schedule()
        val before = schedule.delay
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(null, null)
        controller.setDelay(dto)
        assertEquals("setDelay must accept empty value",
                before, schedule.delay)
    }

    @Test
    fun addUrlAddsNewUrls() {
        val schedule = Schedule()
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(listOf("http://www.google.com", "http://www.yandex.ru"), null)
        controller.addUrl(dto)
        assertEquals("addUrl must save new list of valid urls",
                dto.urls!!.size.toLong(), schedule.urls.size.toLong())
    }

    @Test
    fun addUrlAcceptsEmptyList() {
        val schedule = Schedule()
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(listOf(), null)
        controller.addUrl(dto)
        assertEquals("addUrl must accept empty list",
                0, schedule.urls.size.toLong())
    }

    @Test
    fun deleteUrlAddsNewUrls() {
        val schedule = Schedule()
        val save = "http://www.google.com"
        val delete = "http://www.yandex.ru"
        schedule.addUrl(save)
        schedule.addUrl(delete)
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(listOf(delete), null)
        controller.deleteUrl(dto)
        assertEquals("deleteUrl must remove only one url",
                1, schedule.urls.size.toLong())
        assertEquals("deleteUrl must remove correct url",
                save, schedule.urls.keys().nextElement())
    }

    @Test
    fun deleteUrlEmptyList() {
        val schedule = Schedule()
        schedule.addUrl("http://www.google.com")
        schedule.addUrl("http://www.yandex.ru")
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(listOf(), null)
        controller.deleteUrl(dto)
        assertEquals("deleteUrl can't modify list if params are not present",
                2, schedule.urls.size.toLong())
    }

    @Test
    fun setScheduleOverwritesCurrentSchedule() {
        val oldUrl = "http://www.google.com"
        val oldDelay = 10
        val newUrl = "http://www.yandex.ru"
        val newDelay = 60
        val schedule = Schedule()
        schedule.delay = oldDelay
        schedule.addUrl(oldUrl)
        schedule.addUrl("http://www.google.com")
        val controller = ScheduleController(schedule)
        val dto = ScheduleDto(listOf(newUrl), newDelay)
        controller.setSchedule(dto)
        assertEquals("setSchedule must overwrite old schedule data",
                newUrl, schedule.urls.keys().nextElement())
        assertEquals("setSchedule must overwrite old schedule data",
                newDelay, schedule.delay)
    }

    @Test
    fun setScheduleMakesNoChangesIfAnyParamIsNotPresent() {
        val oldUrl = "http://www.google.com"
        val oldDelay = 10
        val schedule = Schedule()
        schedule.delay = oldDelay
        schedule.addUrl(oldUrl)
        schedule.addUrl("http://www.google.com")
        val controller = ScheduleController(schedule)
        controller.setSchedule(ScheduleDto(null, null))
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldUrl, schedule.urls.keys().nextElement())
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldDelay, schedule.delay)
        controller.setSchedule(ScheduleDto(null, 30))
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldUrl, schedule.urls.keys().nextElement())
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldDelay, schedule.delay)
        controller.setSchedule(ScheduleDto(listOf("http://www.yandex.ru"), null))
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldUrl, schedule.urls.keys().nextElement())
        assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
                oldDelay, schedule.delay)
    }

}
