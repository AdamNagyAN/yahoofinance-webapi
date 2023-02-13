package com.adamnagyan.yahoofinancewebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class YahoofinanceWebapiApplication {

  public static void main(String[] args)  {
    SpringApplication.run(YahoofinanceWebapiApplication.class, args);
  }

}
