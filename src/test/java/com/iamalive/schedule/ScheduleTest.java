package com.iamalive.schedule;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleTest {

  @Test
  public void addsValidUrl() {
    final String valid = "http://www.yandex.ru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(valid);
    assertEquals(1, schedule.getUrls().size());
  }

  @Test
  public void ignoresInvalidUrl() {
    final String invalid = "httpwwwyandexru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(invalid);
    assertEquals(0, schedule.getUrls().size());
  }

  @Test
  public void removesUrlByName() {
    final String url = "http://www.yandex.ru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(url);
    schedule.removeUrl(url);
    assertEquals(0, schedule.getUrls().size());
  }

  @Test
  public void setsValidDelay() {
    final Integer valid = 60;
    final Schedule schedule = new Schedule();
    schedule.setDelay(valid);
    assertEquals(valid, schedule.getDelay());
  }

  @Test
  public void ignoresInvalidDelay() {
    final Integer big = 24 * 60 * 10;
    final Integer small = 1;
    final Schedule schedule = new Schedule();
    final Integer before = schedule.getDelay();
    schedule.setDelay(big);
    assertEquals(before, schedule.getDelay());
    schedule.setDelay(small);
    assertEquals(before, schedule.getDelay());
  }

}