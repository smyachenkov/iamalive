package com.iamalive.task;

import com.iamalive.schedule.Schedule;
import com.iamalive.schedule.UrlStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import javax.validation.constraints.NotNull;


@Component
@Slf4j
public class CheckAliveTask implements Runnable {

  private Schedule schedule;

  @Autowired
  public CheckAliveTask(Schedule schedule) {
    this.schedule = schedule;
  }

  /**
   * Check status of every url in current schedule.
   */
  public void run() {
    log.info("current delay is " + schedule.getDelay());
    schedule.getUrls().keySet().parallelStream().forEach(
        url -> schedule.getUrls().put(url, checkStatus(url))
    );
  }

  private UrlStatusEnum checkStatus(@NotNull final String url) {
    UrlStatusEnum result;
    try {
      HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
      connection.setReadTimeout(5000);
      int responseCode = connection.getResponseCode();
      result = (200 <= responseCode && responseCode <= 399)
          ? UrlStatusEnum.AVAILABLE
          : UrlStatusEnum.UNAVAILABLE;
    } catch (UnknownHostException exception) {
      log.info("can't reach address " + url);
      result = UrlStatusEnum.UNAVAILABLE;
    } catch (IOException exception) {
      log.error(exception.getMessage());
      result = UrlStatusEnum.NOT_CHECKED;
    }
    return result;
  }


}
