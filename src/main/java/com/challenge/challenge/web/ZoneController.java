package com.challenge.challenge.web;

import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.web.api.TopZonesApi;
import com.challenge.challenge.web.api.ZoneTripsApi;
import com.challenge.challenge.web.api.dto.TopZonesDTO;
import com.challenge.challenge.web.api.dto.ZoneTripsDTO;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ZoneController implements TopZonesApi, ZoneTripsApi {

  private final TaxiTripsService taxiTripsService;

  @Override
  public ResponseEntity<TopZonesDTO> getTopZones(@RequestParam(value = "order") String order) {
    log.debug("Handling get top 5 zones for param \"{}\"", order);
    TopZonesDTO topZonesDTO = taxiTripsService.getTopZones(order);
    return new ResponseEntity<>(topZonesDTO, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ZoneTripsDTO> getZoneTrips(@RequestParam(value = "zone") String zone,
      @RequestParam(value = "date") String date) {
    log.debug("Handling get zone trips for params \"{}\" and \"{}\"", zone, date);
    ZoneTripsDTO zoneTrips = taxiTripsService.getZoneTrips(Long.parseLong(zone), LocalDate.parse(date));
    return new ResponseEntity<>(zoneTrips, HttpStatus.OK);
  }
}
