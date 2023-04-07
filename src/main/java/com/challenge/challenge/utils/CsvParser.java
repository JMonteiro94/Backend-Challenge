package com.challenge.challenge.utils;

import com.challenge.challenge.domain.enums.TripType;
import com.challenge.challenge.service.helper.ParsedTrip;
import com.challenge.challenge.service.helper.ParsedZone;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CsvParser {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
  private static final int GREEN_PICKUP_ZONE_INDEX = 5;
  private static final int GREEN_DROPOFF_ZONE_INDEX = 6;
  private static final int YELLOW_PICKUP_ZONE_INDEX = 7;
  private static final int YELLOW_DROPOFF_ZONE_INDEX = 8;


  public static List<ParsedTrip> parseGreenTrips(List<String> tripsLines){
    return parseTrips(tripsLines, TripType.GREEN, GREEN_PICKUP_ZONE_INDEX, GREEN_DROPOFF_ZONE_INDEX);
  }

  public static List<ParsedTrip> parseYellowTrips(List<String> tripsLines){
    return parseTrips(tripsLines, TripType.YELLOW, YELLOW_PICKUP_ZONE_INDEX, YELLOW_DROPOFF_ZONE_INDEX);
  }

  private static List<ParsedTrip> parseTrips(List<String> tripsLines, TripType type, int pickupZoneIndex, int dropoffZoneIndex) {
    List<ParsedTrip> parsedTrips = new ArrayList<>();
    tripsLines.forEach(tripLine -> {
      String[] splitString = tripLine.split(",");
      LocalDate pickupDate = LocalDate.parse(splitString[1], formatter);
      LocalDate dropoffDate = LocalDate.parse(splitString[2], formatter);
      Long pickupZoneId = Long.parseLong(splitString[pickupZoneIndex]);
      Long dropoffZoneId = Long.parseLong(splitString[dropoffZoneIndex]);
      ParsedTrip parsedTrip = ParsedTrip.builder()
          .pickupDate(pickupDate)
          .dropoffDate(dropoffDate)
          .pickupZoneId(pickupZoneId)
          .dropoffZoneId(dropoffZoneId)
          .type(type)
          .build();
      parsedTrips.add(parsedTrip);
    });
    return parsedTrips;
  }

  public static List<ParsedZone> parsedZones(List<String> zonesLines){
    List<ParsedZone> parsedZones = new ArrayList<>();
    zonesLines.forEach(zoneLine -> {
      String[] splitString = zoneLine.split(",");
      long locationId = Long.parseLong(splitString[0]);
      String title = splitString[2].replace("\"", "");
      ParsedZone parsedZone = ParsedZone.builder()
          .locationId(locationId)
          .title(title)
          .build();
      parsedZones.add(parsedZone);
    });
    return parsedZones;
  }
}
