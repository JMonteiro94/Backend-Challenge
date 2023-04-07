package com.challenge.challenge.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

@Builder
public record TopZonesDTO(@JsonProperty("top_zones") List<TopZoneDTO> topZones) {
}
