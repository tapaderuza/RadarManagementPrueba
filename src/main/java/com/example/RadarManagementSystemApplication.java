package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RadarManagementSystemApplication {

  private static final Logger logger = LoggerFactory.getLogger(RadarManagementSystemApplication.class);

  public static void main(String[] args) {
    logger.info("Starting RadarManagementSystemApplication");
    SpringApplication.run(RadarManagementSystemApplication.class, args);
  }
}


