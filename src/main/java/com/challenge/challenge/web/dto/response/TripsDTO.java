package com.challenge.challenge.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TripsDTO(@JsonProperty("pickup_zone") String pickupZone, @JsonProperty("dropoff_zone") String dropoffZone,
                       @JsonProperty("pickup_date") String pickupDate, @JsonProperty("dropoff_date") String dropoffDate) {

}
