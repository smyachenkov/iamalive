package com.iamalive.schedule

import org.springframework.stereotype.Component

import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

@Component
class Schedule {

    companion object {
        const val MAX_DELAY = 24 * 60
        const val MIN_DELAY = 5
    }

    val urls = ConcurrentHashMap<String, UrlStatusEnum>()

    var delay: Int = 5
        set(delay) {
            if (delay in MIN_DELAY..MAX_DELAY) {
                field = delay
            }
        }

    /**
     * Add url to schedule if it's a valid url.
     * @param url url string value
     */
    fun addUrl(url: String) {
        if (isUrlValid(url)) {
            urls[url] = UrlStatusEnum.NOT_CHECKED
        }
    }

    /**
     * Remove url from schedule if such url exists.
     * @param url url string value
     */
    fun removeUrl(url: String) {
        urls.remove(url)
    }

    private fun isUrlValid(url: String): Boolean {
        var valid = true
        try {
            URL(url)
        } catch (exception: MalformedURLException) {
            valid = false
        }
        return valid
    }
}
