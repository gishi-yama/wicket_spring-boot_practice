package com.example.wsbp;

import com.giffing.wicket.spring.boot.starter.app.WicketBootStandardWebApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WSBPApplication extends WicketBootStandardWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WSBPApplication.class, args);
  }

}
