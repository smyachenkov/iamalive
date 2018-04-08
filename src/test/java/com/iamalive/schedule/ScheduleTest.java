package com.iamalive.schedule;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleTest {

  @Test
  public void addsValidUrl() {
    final String valid = "http://www.yandex.ru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(valid);
    assertEquals("valid url must be present in schedule", 1, schedule.getUrls().size());
  }

  @Test
  public void ignoresInvalidUrl() {
    final String invalid = "httpwwwyandexru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(invalid);
    assertEquals("invalid url can't be present in schedule", 0, schedule.getUrls().size());
  }

  @Test
  public void removesUrlByName() {
    final String url = "http://www.yandex.ru";
    final Schedule schedule = new Schedule();
    schedule.addUrl(url);
    schedule.removeUrl(url);
    assertEquals("can't remove existing url by name",0, schedule.getUrls().size());
  }

  @Test
  public void setsValidDelay() {
    final Integer valid = 60;
    final Schedule schedule = new Schedule();
    schedule.setDelay(valid);
    assertEquals("can't set valid delay", valid, schedule.getDelay());
  }

  @Test
  public void ignoresInvalidDelay() {
    final Integer big = Schedule.MAX_DELAY + 1;
    final Integer small = Schedule.MIN_DELAY - 1;
    final Schedule schedule = new Schedule();
    final Integer before = schedule.getDelay();
    schedule.setDelay(big);
    assertEquals("delay can't be changed to be longer than allowed", before, schedule.getDelay());
    schedule.setDelay(small);
    assertEquals("delay can't be changed to be shorter than allowed", before, schedule.getDelay());
  }

}