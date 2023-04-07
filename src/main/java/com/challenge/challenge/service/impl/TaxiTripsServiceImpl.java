package com.challenge.challenge.service.impl;

import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.Zone;
import com.challenge.challenge.domain.enums.TripType;
import com.challenge.challenge.repository.TripRepository;
import com.challenge.challenge.repository.ZoneRepository;
import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.utils.FileReaderUtil;
import com.challenge.challenge.web.dto.response.TopZoneDTO;
import com.challenge.challenge.web.dto.response.TopZonesDTO;
import com.challenge.challenge.web.dto.response.TripsDTO;
import com.challenge.challenge.web.dto.response.ZoneTripsDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class TaxiTripsServiceImpl implements TaxiTripsService {

  private final ZoneRepository zoneRepository;
  private final TripRepository tripRepository;

  @Override
  public void loadData() {
    log.info("Loading csv files...");
    HashMap<Long, Zone> zonesMap = loadZones();
    loadTripsFile(zonesMap);
    log.info("Finished loading csv files...");
  }

  private void loadTripsFile(HashMap<Long, Zone> zonesMap){
    loadGreenTripsData(zonesMap);
    loadYellowTripsData(zonesMap);
  }

  private void loadYellowTripsData(HashMap<Long, Zone> zonesMap) {
    String greenTrips = FileReaderUtil.getFilePath("yellow_tripdata_2018-01_01-15.csv");
    List<String> tripsLines = FileReaderUtil.readFile(greenTrips);
    saveTrips(tripsLines, zonesMap, TripType.YELLOW, 7, 8);
  }

  private void loadGreenTripsData(HashMap<Long, Zone> zonesMap) {
    String greenTrips = FileReaderUtil.getFilePath("green_tripdata_2018-01_01-15.csv");
    List<String> tripsLines = FileReaderUtil.readFile(greenTrips);
    saveTrips(tripsLines, zonesMap, TripType.GREEN, 5, 6);
  }

  private void saveTrips(List<String> tripsLines, HashMap<Long, Zone> zonesMap, TripType type,
      int pickupZoneIndex, int dropoffZoneIndex) {
    tripsLines.forEach(zoneLine -> {
      String[] splitString = zoneLine.split(",");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
      LocalDate pickupDate = LocalDate.parse(splitString[1], formatter);
      LocalDate dropoffDate = LocalDate.parse(splitString[2], formatter);
      Zone pickupZone = zonesMap.get(Long.parseLong(splitString[pickupZoneIndex]));
      Zone dropoffZone = zonesMap.get(Long.parseLong(splitString[dropoffZoneIndex]));
      Trip trip = Trip.builder()
          .pickupDate(pickupDate)
          .dropoffDate(dropoffDate)
          .pickupZone(pickupZone)
          .dropoffZone(dropoffZone)
          .type(type)
          .build();
        tripRepository.save(trip);
    });
  }

  private HashMap<Long, Zone> loadZones(){
    String zonesFilesPath = FileReaderUtil.getFilePath("zones.csv");
    List<String> zonesLines = FileReaderUtil.readFile(zonesFilesPath);
    return saveZones(zonesLines);
  }

  private HashMap<Long, Zone> saveZones(List<String> zones){
    HashMap<Long, Zone> zonesMap = new HashMap<>();
    zones.forEach(zoneLine -> {
      String[] splitString = zoneLine.split(",");
      long locationId = Long.parseLong(splitString[0]);
      Zone zone = Zone.builder()
          .locationId(locationId)
          .title(splitString[2].replace("\"", ""))
          .build();
      zoneRepository.save(zone);
      zonesMap.put(zone.getLocationId(), zone);
    });
    return zonesMap;
  }

  @Override
  @Transactional(readOnly = true)
  public TopZonesDTO getTopZones(String order) {
    ArrayList<TopZoneDTO> topZoneDTOS = new ArrayList<>();
    List<Zone> zones = zoneRepository.findAll();
    zones.forEach(zone -> {
      TopZoneDTO topZoneDTO = TopZoneDTO.builder()
          .zone(zone.getTitle())
          .dropoffTotal(zone.getDropoffTrips().size())
          .pickupTotal(zone.getPickupTrips().size())
          .build();
      topZoneDTOS.add(topZoneDTO);
    });

    List<TopZoneDTO> topFiveZonesSorted = topZoneDTOS.stream()
        .sorted(((o1, o2) -> {
          if("dropoffs".equals(order)){
            return Math.toIntExact(o2.dropoffTotal() - o1.dropoffTotal());
          }
          return Math.toIntExact(o2.pickupTotal() - o1.pickupTotal());
        }))
        .limit(5)
        .toList();
    return TopZonesDTO.builder()
        .topZones(topFiveZonesSorted)
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public ZoneTripsDTO getZoneTrips(long id, LocalDate date) {
    Optional<Zone> zoneOptional = zoneRepository.findByLocationId(id);
    if(zoneOptional.isPresent()){
      Zone zone = zoneOptional.get();
      int pickupsTripsOnDate = zone.getPickupTrips().stream()
          .filter(z -> date.isEqual(z.getPickupDate()))
          .toList().size();
      int dropoffsTripsOnDate = zone.getDropoffTrips().stream()
          .filter(z -> date.isEqual(z.getDropoffDate()))
          .toList().size();
      return ZoneTripsDTO.builder()
          .zone(zone.getTitle())
          .date(date.toString())
          .pickupTotal(pickupsTripsOnDate)
          .dropoffTotal(dropoffsTripsOnDate)
          .build();
    }

    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TripsDTO> getYellowTrips(LocalDate date, Pageable pageable) {
    return tripRepository.findAllByTypeIsAndDropoffDateOrPickupDate(TripType.YELLOW, date, date, pageable)
        .map(trip -> TripsDTO.builder()
            .dropoffDate(trip.getDropoffDate().toString())
            .pickupDate(trip.getPickupDate().toString())
            .dropoffZone(trip.getDropoffZone().getTitle())
            .pickupZone(trip.getPickupZone().getTitle())
            .build());
  }
}
