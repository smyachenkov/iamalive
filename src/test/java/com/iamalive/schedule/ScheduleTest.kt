package com.iamalive.schedule

import org.junit.Test

import org.junit.Assert.*

class ScheduleTest {

    @Test
    fun addsValidUrl() {
        val valid = "http://www.yandex.ru"
        val schedule = Schedule()
        schedule.addUrl(valid)
        assertEquals("valid url must be present in schedule", 1, schedule.urls.size.toLong())
    }

    @Test
    fun ignoresInvalidUrl() {
        val invalid = "httpwwwyandexru"
        val schedule = Schedule()
        schedule.addUrl(invalid)
        assertEquals("invalid url can't be present in schedule", 0, schedule.urls.size.toLong())
    }

    @Test
    fun removesUrlByName() {
        val url = "http://www.yandex.ru"
        val schedule = Schedule()
        schedule.addUrl(url)
        schedule.removeUrl(url)
        assertEquals("can't remove existing url by name", 0, schedule.urls.size.toLong())
    }

    @Test
    fun setsValidDelay() {
        val valid = 60
        val schedule = Schedule()
        schedule.delay = valid
        assertEquals("can't set valid delay", valid, schedule.delay)
    }

    @Test
    fun ignoresInvalidDelay() {
        val big = Schedule.MAX_DELAY + 1
        val small = Schedule.MIN_DELAY - 1
        val schedule = Schedule()
        val before = schedule.delay
        schedule.delay = big
        assertEquals("delay can't be changed to be longer than allowed", before, schedule.delay)
        schedule.delay = small
        assertEquals("delay can't be changed to be shorter than allowed", before, schedule.delay)
    }

}
