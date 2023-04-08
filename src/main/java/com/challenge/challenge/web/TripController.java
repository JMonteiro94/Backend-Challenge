package com.challenge.challenge.web;

import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.web.api.ListYellowApi;
import com.challenge.challenge.web.api.dto.TripsDTO;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TripController implements ListYellowApi {

  private final TaxiTripsService taxiTripsService;

  @Override
  public ResponseEntity<TripsDTO> getYellowTrips(@RequestParam("date") String date, Pageable pageable) {
    log.debug("Handling get yellow trips for param \"{}\"", date);
    TripsDTO yellowTrips = taxiTripsService.getYellowTrips(LocalDate.parse(date), pageable);
    return new ResponseEntity<>(yellowTrips, HttpStatus.OK);
  }
}