package com.challenge.challenge.service;

import com.challenge.challenge.web.dto.response.TopZonesDTO;
import com.challenge.challenge.web.dto.response.TripsDTO;
import com.challenge.challenge.web.dto.response.ZoneTripsDTO;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaxiTripsService {

  void loadData();

  TopZonesDTO getTopZones(String order);

  ZoneTripsDTO getZoneTrips(long zone, LocalDate date);

  Page<TripsDTO> getYellowTrips(LocalDate date, Pageable pageable);
}
