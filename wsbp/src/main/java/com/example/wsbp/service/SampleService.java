package com.example.wsbp.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SampleService implements ISampleService {

  @Override
  public String makeCurrentHMS() {
    LocalDateTime now = LocalDateTime.now();
    String str = now.getHour()
      + ":" + now.getMinute()
      + ":" + now.getSecond();
    return str;
  }

  @Override
  public int makeRandInt() {
    Random rand = new Random();
    int n = rand.nextInt(10);
    return n;
  }
}
