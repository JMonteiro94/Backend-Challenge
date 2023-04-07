package com.challenge.challenge.bootstrap;

import com.challenge.challenge.service.TaxiTripsService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TripsBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final TaxiTripsService taxiTripsService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    taxiTripsService.loadData();
  }
}
