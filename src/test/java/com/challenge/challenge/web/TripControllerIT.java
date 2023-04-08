package com.challenge.challenge.web;

import static org.hamcrest.Matchers.hasItems;
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
class TripControllerIT {

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
        .title("Steinway")
        .locationId(1L)
        .build();
    zoneRepository.save(zone);
    Trip trip = Trip.builder()
        .type(TripType.YELLOW)
        .dropoffZone(zone)
        .pickupZone(zone)
        .pickupDate(LocalDate.parse("1900-01-12"))
        .dropoffDate(LocalDate.parse("1900-01-12"))
        .build();
    tripRepository.save(trip);
  }

  @Test
  @DisplayName("Get yellow trips")
  void getYellowTrips_whenProvidedDateAndPageAndSortCriteria_successful() throws Exception {

    restMockMvc
        .perform(get("/list-yellow?date=1900-01-12&page=0&size=10&sort=pickupZone_title").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.tripsPage.content", hasSize(1)))
        .andExpect(jsonPath("$.tripsPage.content.[*].pickupDate").value(hasItems("1900-01-12")))
        .andExpect(jsonPath("$.tripsPage.content.[*].dropoffDate").value(hasItems("1900-01-12")));
  }
}
