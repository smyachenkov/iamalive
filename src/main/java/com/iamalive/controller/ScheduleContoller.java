package com.iamalive.controller;

import com.iamalive.dto.UrlList;
import com.iamalive.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    return schedule;
  }

  /**
   * Update status check delay.
   * @param delay seconds
   * @return schedule with list of urls and their status
   */
  @PostMapping("/set_delay/{delay}")
  @ResponseBody
  public Schedule setDelay(@PathVariable("delay") Integer delay) {
    schedule.setDelay(delay);
    return schedule;
  }

  /**
   * Add list of urls to current schedule.
   * @param urls json array of urls
   * @return schedule with list of urls and their status
   */
  @PostMapping(value = "/add_url")
  @ResponseBody
  public Schedule addUrl(@RequestBody UrlList urls) {
    urls.getUrls().forEach(url -> schedule.addUrl(url.trim()));
    return schedule;
  }

  /**
   * Remove list of urls from current schedule.
   * @param urls json array of urls
   * @return schedule with list of urls and their status
   */
  @PostMapping(value = "/delete_url")
  @ResponseBody
  public Schedule deleteUrl(@RequestBody UrlList urls) {
    urls.getUrls().forEach(url -> schedule.removeUrl(url.trim()));
    return schedule;
  }

}
