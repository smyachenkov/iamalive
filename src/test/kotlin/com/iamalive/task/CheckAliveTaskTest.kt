package com.iamalive.task

import com.iamalive.HttpUrlStreamHandler
import com.iamalive.schedule.Schedule
import com.iamalive.schedule.UrlStatusEnum
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import java.net.HttpURLConnection
import java.net.URL
import java.net.URLStreamHandlerFactory

import org.mockito.BDDMockito.*
import org.junit.Assert.*

class CheckAliveTaskTest {

    companion object {

        private var httpUrlStreamHandler: HttpUrlStreamHandler? = null

        @BeforeClass
        @JvmStatic
        fun setupURLStreamHandlerFactory() {
            val urlStreamHandlerFactory = mock(URLStreamHandlerFactory::class.java)
            URL.setURLStreamHandlerFactory(urlStreamHandlerFactory)
            httpUrlStreamHandler = HttpUrlStreamHandler()
            given(urlStreamHandlerFactory.createURLStreamHandler("http")).willReturn(httpUrlStreamHandler)
        }
    }

    @Before
    fun reset() {
        httpUrlStreamHandler?.resetConnections()
    }

    @Test
    @Throws(Exception::class)
    fun checksAllUrls() {
        val schedule = Schedule()
        mockUrlWithCode(schedule, "http://www.google.com", 200)
        mockUrlWithCode(schedule, "http://www.yandex.ru", 404)
        val task = CheckAliveTask(schedule)
        task.run()
        schedule.urls.keys.forEach { url ->
            assertNotEquals("Url must be checked after task is complete",
                    UrlStatusEnum.NOT_CHECKED, schedule.urls[url])
        }
    }

    @Test
    @Throws(Exception::class)
    fun marksUrlsWithCorrectCodes() {
        val schedule = Schedule()
        val available = "http://www.google.com"
        val unavailable = "http://www.yandex.ru"
        mockUrlWithCode(schedule, available, 200)
        mockUrlWithCode(schedule, unavailable, 404)
        val task = CheckAliveTask(schedule)
        task.run()
        assertEquals("available url must be marked with available status",
                UrlStatusEnum.AVAILABLE, schedule.urls[available])
        assertEquals("unavailable url must be marked with unavailable status",
                UrlStatusEnum.UNAVAILABLE, schedule.urls[unavailable])
    }

    @Throws(Exception::class)
    private fun mockUrlWithCode(schedule: Schedule, url: String, code: Int) {
        schedule.addUrl(url)
        val urlConnection = mock(HttpURLConnection::class.java)
        `when`(urlConnection.responseCode).thenReturn(code)
        httpUrlStreamHandler?.addConnection(URL(url), urlConnection)
    }

}
