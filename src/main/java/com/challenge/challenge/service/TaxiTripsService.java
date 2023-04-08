package com.challenge.challenge.service;

import com.challenge.challenge.web.api.dto.TopZonesDTO;
import com.challenge.challenge.web.api.dto.TripsDTO;
import com.challenge.challenge.web.api.dto.ZoneTripsDTO;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;

public interface TaxiTripsService {

  void loadData();

  TopZonesDTO getTopZones(String order);

  ZoneTripsDTO getZoneTrips(long zone, LocalDate date);

  TripsDTO getYellowTrips(LocalDate date, Pageable pageable);
}
