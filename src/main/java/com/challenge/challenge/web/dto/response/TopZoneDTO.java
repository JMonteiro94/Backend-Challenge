package com.challenge.challenge.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TopZoneDTO(@JsonProperty("zone") String zone, @JsonProperty("pu_total") long pickupTotal,
                         @JsonProperty("do_total") long dropoffTotal) {

}
