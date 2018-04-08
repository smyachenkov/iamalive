package com.iamalive.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ScheduleDto implements Serializable {

  private List<String> urls;

  private Integer delay;

}
