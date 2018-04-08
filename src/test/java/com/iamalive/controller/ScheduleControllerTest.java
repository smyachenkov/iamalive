package com.iamalive.controller;

import com.iamalive.dto.ScheduleDto;
import com.iamalive.schedule.Schedule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;


public class ScheduleControllerTest {


  @Test
  public void setDelayAcceptsNewValue() {
    final Schedule schedule = new Schedule();
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    dto.setDelay(30);
    controller.setDelay(dto);
    assertEquals("setDelay must accept new value",
        dto.getDelay(), schedule.getDelay());
  }

  @Test
  public void setDelayIgnoresEmpty() {
    final Schedule schedule = new Schedule();
    final Integer before = schedule.getDelay();
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    controller.setDelay(dto);
    assertEquals("setDelay must accept empty value",
        before, schedule.getDelay());
  }

  @Test
  public void addUrlAddsNewUrls() {
    final Schedule schedule = new Schedule();
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    dto.setUrls(Arrays.asList("http://www.google.com", "http://www.yandex.ru"));
    controller.addUrl(dto);
    assertEquals("addUrl must save new list of valid urls",
        dto.getUrls().size(), schedule.getUrls().size());
  }

  @Test
  public void addUrlAcceptsEmptyList() {
    final Schedule schedule = new Schedule();
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    controller.addUrl(dto);
    assertEquals("addUrl must accept empty list",
        0, schedule.getUrls().size());
  }

  @Test
  public void deleteUrlAddsNewUrls() {
    final Schedule schedule = new Schedule();
    final String save = "http://www.google.com";
    final String delete = "http://www.yandex.ru";
    schedule.addUrl(save);
    schedule.addUrl(delete);
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    dto.setUrls(Collections.singletonList(delete));
    controller.deleteUrl(dto);
    assertEquals("deleteUrl must remove only one url",
        1, schedule.getUrls().size());
    assertEquals("deleteUrl must remove correct url",
        save, schedule.getUrls().keys().nextElement());
  }

  @Test
  public void deleteUrlEmptyList() {
    final Schedule schedule = new Schedule();
    schedule.addUrl("http://www.google.com");
    schedule.addUrl("http://www.yandex.ru");
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    controller.deleteUrl(dto);
    assertEquals("deleteUrl can't modify list if params are not present",
        2, schedule.getUrls().size());
  }

  @Test
  public void setScheduleOverwritesCurrentSchedule() {
    final String oldUrl = "http://www.google.com";
    final Integer oldDelay = 10;
    final String newUrl = "http://www.yandex.ru";
    final Integer newDelay = 60;
    final Schedule schedule = new Schedule();
    schedule.setDelay(oldDelay);
    schedule.addUrl(oldUrl);
    schedule.addUrl("http://www.google.com");
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    dto.setUrls(Collections.singletonList(newUrl));
    dto.setDelay(newDelay);
    controller.setSchedule(dto);
    assertEquals("setSchedule must overwrite old schedule data",
        newUrl, schedule.getUrls().keys().nextElement());
    assertEquals("setSchedule must overwrite old schedule data",
        newDelay, schedule.getDelay());
  }

  @Test
  public void setScheduleMakesNoChangesIfAnyParamIsNotPresent() {
    final String oldUrl = "http://www.google.com";
    final Integer oldDelay = 10;
    final Schedule schedule = new Schedule();
    schedule.setDelay(oldDelay);
    schedule.addUrl(oldUrl);
    schedule.addUrl("http://www.google.com");
    final ScheduleController controller = new ScheduleController(schedule);
    final ScheduleDto dto = new ScheduleDto();
    controller.setSchedule(dto);
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldUrl, schedule.getUrls().keys().nextElement());
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldDelay, schedule.getDelay());
    dto.setDelay(30);
    controller.setSchedule(dto);
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldUrl, schedule.getUrls().keys().nextElement());
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldDelay, schedule.getDelay());
    dto.setDelay(null);
    dto.setUrls(Collections.singletonList("http://www.yandex.ru"));
    controller.setSchedule(dto);
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldUrl, schedule.getUrls().keys().nextElement());
    assertEquals("setSchedule must not overwrite old schedule data if any params are missing",
        oldDelay, schedule.getDelay());
  }

}