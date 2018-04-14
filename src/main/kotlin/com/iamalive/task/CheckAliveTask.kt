package com.iamalive.task

import com.iamalive.schedule.Schedule
import com.iamalive.schedule.UrlStatusEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import javax.validation.constraints.NotNull


@Component
open class CheckAliveTask

@Autowired
constructor(private val schedule: Schedule) : Runnable {

    companion object {
        private val log = LoggerFactory.getILoggerFactory().getLogger(CheckAliveTask.toString())
    }

    /**
     * Check status of every url in current schedule.
     */
    override fun run() {
        log.info("current delay is " + schedule.delay)
        schedule.urls.keys.parallelStream().forEach { url ->
            schedule.urls[url] = checkStatus(url)
        }
    }

    private fun checkStatus(@NotNull url: String): UrlStatusEnum {
        var result: UrlStatusEnum
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.readTimeout = 5000
            result = if (connection.responseCode in 200..399)
                    UrlStatusEnum.AVAILABLE
                else
                    UrlStatusEnum.UNAVAILABLE
        } catch (exception: UnknownHostException) {
            log.info("can't reach address $url")
            result = UrlStatusEnum.UNAVAILABLE
        } catch (exception: IOException) {
            log.error(exception.message)
            result = UrlStatusEnum.NOT_CHECKED
        }
        return result
    }

}
