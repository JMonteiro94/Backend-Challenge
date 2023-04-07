package com.challenge.challenge.web;

import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.web.dto.response.TripsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TripController {

  private final TaxiTripsService taxiTripsService;

  @Operation(summary = "Get yellow trips by pagination, sort and date")
  @ApiResponse(responseCode = "200", description = "Returns list of trips.",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = TripsDTO.class)))
  @GetMapping(value = "/list-yellow")
  public ResponseEntity<Page<TripsDTO>> getYellowTrips(@RequestParam("date") String date, Pageable pageable) {
    log.debug("Handling get yellow trips for param \"{}\"", date);
    Page<TripsDTO> yellowTrips = taxiTripsService.getYellowTrips(LocalDate.parse(date), pageable);
    return new ResponseEntity<>(yellowTrips, HttpStatus.OK);
  }
}