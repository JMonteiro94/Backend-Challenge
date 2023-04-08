package com.challenge.challenge.service.helper;

import com.challenge.challenge.web.api.dto.TopZoneDTO;
import java.util.Comparator;

public class ZoneComparator implements Comparator<TopZoneDTO> {

  private final String order;

  public ZoneComparator(String order){
    this.order = order;
  }

  @Override
  public int compare(TopZoneDTO o1, TopZoneDTO o2) {
    if("dropoffs".equals(order)){
      return Math.toIntExact(o1.getDoTotal() - o2.getDoTotal());
    }
    return Math.toIntExact(o1.getPuTotal() - o2.getPuTotal());
  }
}
