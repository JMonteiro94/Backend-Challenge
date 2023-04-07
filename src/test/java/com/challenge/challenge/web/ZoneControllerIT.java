package com.challenge.challenge.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.Zone;
import com.challenge.challenge.domain.enums.TripType;
import com.challenge.challenge.repository.TripRepository;
import com.challenge.challenge.repository.ZoneRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class ZoneControllerIT {

  @Autowired
  private ZoneRepository zoneRepository;

  @Autowired
  private TripRepository tripRepository;

  @Autowired
  private MockMvc restMockMvc;

  @BeforeEach
  @Transactional
  public void setup() {
    Zone zone = Zone.builder()
        .title("test")
        .locationId(77777L)
        .build();
    zoneRepository.save(zone);
    Trip trip = Trip.builder()
        .type(TripType.YELLOW)
        .dropoffZone(zone)
        .pickupZone(zone)
        .pickupDate(LocalDate.parse("2018-01-12"))
        .dropoffDate(LocalDate.parse("2018-01-12"))
        .build();
    tripRepository.save(trip);
  }

  @Test
  @DisplayName("Get top five zones sorted by dropoffs")
  void getTopFiveZones_whenOrderDropoffProvided_successful() throws Exception {

    restMockMvc
        .perform(get("/top-zones?order=dropoffs").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.top_zones", hasSize(5)));
  }

  @Test
  @DisplayName("Get zone trips summary by zone id and date")
  void getZoneTripsSummary_whenZoneIdExistsAndDateExists_successful() throws Exception {

    restMockMvc
        .perform(get("/zone-trips?zone=77777&date=2018-01-12").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.zone").value("test"))
        .andExpect(jsonPath("$.date").value("2018-01-12"))
        .andExpect(jsonPath("$.pu").value(1))
        .andExpect(jsonPath("$.do").value(1));
  }
}
