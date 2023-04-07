package com.challenge.challenge.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ZoneTripsDTO(@JsonProperty("zone") String zone, @JsonProperty("date") String date,
                           @JsonProperty("pu") long pickupTotal, @JsonProperty("do") long dropoffTotal) {

}
