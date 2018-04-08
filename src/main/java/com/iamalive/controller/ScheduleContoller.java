package com.iamalive.controller;

import com.iamalive.dto.ScheduleDto;
import com.iamalive.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/schedule", produces = "application/json")
public class ScheduleContoller {

  private Schedule schedule;

  @Autowired
  public ScheduleContoller(Schedule schedule) {
    this.schedule = schedule;
  }

  /**
   * Check current status.
   * @return schedule with list of urls and their status
   */
  @GetMapping("/status")
  @ResponseBody
  public Schedule status() {
    return this.schedule;
  }

  /**
   * Update status check delay.
   * @param scheduleDto json Schedule entity with present delay
   * @return schedule with list of urls and their status
   */
  @PostMapping("/set_delay")
  @ResponseBody
  public Schedule setDelay(@RequestBody ScheduleDto scheduleDto) {
    Optional.ofNullable(scheduleDto.getDelay()).ifPresent(
        d -> this.schedule.setDelay(d)
    );
    return this.schedule;
  }

  /**
   * Add list of urls to current schedule.
   * @param scheduleDto json Schedule entity with present list of urls
   * @return schedule with list of urls and their status
   */
  @PostMapping(value = "/add_url")
  @ResponseBody
  public Schedule addUrl(@RequestBody ScheduleDto scheduleDto) {
    Optional.ofNullable(scheduleDto.getUrls()).ifPresent(
        urls -> urls.forEach(url -> this.schedule.addUrl(url.trim()))
    );
    return this.schedule;
  }

  /**
   * Remove list of urls from current schedule.
   * @param scheduleDto json Schedule with present list of urls
   * @return schedule with list of urls and their status
   */
  @PostMapping(value = "/delete_url")
  @ResponseBody
  public Schedule deleteUrl(@RequestBody ScheduleDto scheduleDto) {
    Optional.ofNullable(scheduleDto.getUrls()).ifPresent(
        urls -> urls.forEach(url -> this.schedule.removeUrl(url.trim()))
    );
    return this.schedule;
  }

  /**
   * Completely overwrites current schedule with new data.
   * @param scheduleDto json Schedule entity with all fields present
   * @return schedule with list of urls and their status
   */
  @PostMapping(value = "/set_schedule")
  @ResponseBody
  public Schedule setSchedule(@RequestBody ScheduleDto scheduleDto) {
    Optional.ofNullable(scheduleDto.getDelay()).ifPresent(
        delay -> Optional.ofNullable(scheduleDto.getUrls()).ifPresent(
            urls -> {
              this.schedule.getUrls().clear();
              urls.forEach(url -> this.schedule.addUrl(url));
              this.schedule.setDelay(delay);
            }
        )
    );
    return this.schedule;
  }

}
