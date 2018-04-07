package com.iamalive.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UrlList implements Serializable {

  List<String> urls;

}
