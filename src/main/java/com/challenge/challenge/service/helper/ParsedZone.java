package com.challenge.challenge.service.helper;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParsedZone {

  private long locationId;

  private String title;
}
