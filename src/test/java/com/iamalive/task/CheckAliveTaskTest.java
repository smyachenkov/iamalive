package com.iamalive.task;

import com.iamalive.HttpUrlStreamHandler;
import com.iamalive.schedule.Schedule;
import com.iamalive.schedule.UrlStatusEnum;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

public class CheckAliveTaskTest {

  private static HttpUrlStreamHandler httpUrlStreamHandler;

  @BeforeClass
  public static void setupURLStreamHandlerFactory() {
    URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);
    URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
    httpUrlStreamHandler = new HttpUrlStreamHandler();
    given(urlStreamHandlerFactory.createURLStreamHandler("http")).willReturn(httpUrlStreamHandler);
  }

  @Before
  public void reset() {
    httpUrlStreamHandler.resetConnections();
  }

  @Test
  public void checksAllUrls() throws Exception {
    final Schedule schedule = new Schedule();
    mockUrlWithCode(schedule, "http://www.google.com", 200);
    mockUrlWithCode(schedule, "http://www.yandex.ru", 404);
    final CheckAliveTask task = spy(new CheckAliveTask(schedule));
    task.run();
    schedule.getUrls().keySet().forEach(
        url -> assertNotEquals("Url must be checked after task is complete",
            UrlStatusEnum.NOT_CHECKED, schedule.getUrls().get(url))
    );
  }

  @Test
  public void marksUrlsWithCorrectCodes() throws Exception {
    final Schedule schedule = new Schedule();
    final String available = "http://www.google.com";
    final String unavailable = "http://www.yandex.ru";
    mockUrlWithCode(schedule, available, 200);
    mockUrlWithCode(schedule, unavailable, 404);
    final CheckAliveTask task = spy(new CheckAliveTask(schedule));
    task.run();
    assertEquals("available url must be marked with available status",
        UrlStatusEnum.AVAILABLE, schedule.getUrls().get(available));
    assertEquals("unavailable url must be marked with unavailable status",
        UrlStatusEnum.UNAVAILABLE, schedule.getUrls().get(unavailable));
  }

  private void mockUrlWithCode(final Schedule schedule, final String url, final Integer code) throws Exception {
    schedule.addUrl(url);
    HttpURLConnection urlConnection = mock(HttpURLConnection.class);
    when(urlConnection.getResponseCode()).thenReturn(code);
    httpUrlStreamHandler.addConnection(new URL(url), urlConnection);
  }

}