package com.iamalive.schedule;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class Schedule {

  public static final Integer MAX_DELAY = 24 * 60;

  public static final Integer MIN_DELAY = 5;

  private ConcurrentHashMap<String, UrlStatusEnum> urls = new ConcurrentHashMap<>();

  private Integer delay = 5;

  /**
   * Add url to schedule if it's a valid url.
   * @param url url string value
   */
  public void addUrl(final String url) {
    if (isUrlValid(url)) {
      urls.put(url, UrlStatusEnum.NOT_CHECKED);
    }
  }

  /**
   * Remove url from schedule if such url exists.
   * @param url url string value
   */
  public void removeUrl(final String url) {
    urls.remove(url);
  }

  /**
   * Set refresh delay if new value is between MIN_DELAY and MAX_DELAY.
   * @param delay delay in seconds
   */
  public void setDelay(final Integer delay) {
    if (delay <= MAX_DELAY && delay >= MIN_DELAY) {
      this.delay = delay;
    }
  }

  private boolean isUrlValid(final String url) {
    boolean valid = true;
    try {
      new URL(url);
    } catch (MalformedURLException exception) {
      valid = false;
    }
    return valid;
  }

}
