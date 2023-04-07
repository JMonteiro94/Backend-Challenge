package com.challenge.challenge.service.helper;

import com.challenge.challenge.domain.enums.TripType;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParsedTrip {

  private LocalDate pickupDate;
  private LocalDate dropoffDate;
  private Long pickupZoneId;
  private Long dropoffZoneId;
  private TripType type;
}
