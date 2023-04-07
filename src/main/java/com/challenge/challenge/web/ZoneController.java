package com.challenge.challenge.web;

import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.web.dto.response.TopZonesDTO;
import com.challenge.challenge.web.dto.response.ZoneTripsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class ZoneController {

  private final TaxiTripsService taxiTripsService;

  @Operation(summary = "Get top 5 zones by order")
  @ApiResponse(responseCode = "200", description = "Returns list of top zones.",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = TopZonesDTO.class)))
  @GetMapping(value = "/top-zones")
  public ResponseEntity<TopZonesDTO> getTopZones(@RequestParam(value = "order") String order) {
    log.debug("Handling get top 5 zones for param \"{}\"", order);
    TopZonesDTO topZonesDTO = taxiTripsService.getTopZones(order);
    return new ResponseEntity<>(topZonesDTO, HttpStatus.OK);
  }

  @Operation(summary = "Get zone trips summary by zone and date")
  @ApiResponse(responseCode = "200", description = "Returns zone trip summary.",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ZoneTripsDTO.class)))
  @GetMapping(value = "/zone-trips")
  public ResponseEntity<ZoneTripsDTO> getZoneTrips(@RequestParam(value = "zone") String zone,
      @RequestParam(value = "date") String date) {
    log.debug("Handling get zone trips for params \"{}\" and \"{}\"", zone, date);
    ZoneTripsDTO zoneTrips = taxiTripsService.getZoneTrips(Long.parseLong(zone), LocalDate.parse(date));
    return new ResponseEntity<>(zoneTrips, HttpStatus.OK);
  }
}
