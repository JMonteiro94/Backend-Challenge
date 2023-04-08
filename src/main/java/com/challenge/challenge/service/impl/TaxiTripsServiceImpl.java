package com.challenge.challenge.service.impl;

import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.Zone;
import com.challenge.challenge.domain.enums.TripType;
import com.challenge.challenge.repository.TripRepository;
import com.challenge.challenge.repository.ZoneRepository;
import com.challenge.challenge.service.TaxiTripsService;
import com.challenge.challenge.service.helper.ParsedTrip;
import com.challenge.challenge.service.helper.ParsedZone;
import com.challenge.challenge.service.helper.ZoneComparator;
import com.challenge.challenge.utils.CsvParser;
import com.challenge.challenge.utils.FileReaderUtil;
import com.challenge.challenge.web.api.dto.TopZoneDTO;
import com.challenge.challenge.web.api.dto.TopZonesDTO;
import com.challenge.challenge.web.api.dto.TripsDTO;
import com.challenge.challenge.web.api.dto.ZoneTripsDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
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
    HashMap<Long, Zone> zonesMap = loadAndSaveZones();
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
    List<ParsedTrip> parsedTrips = CsvParser.parseYellowTrips(tripsLines);
    saveTrips(parsedTrips, zonesMap);

  }

  private void loadGreenTripsData(HashMap<Long, Zone> zonesMap) {
    String greenTrips = FileReaderUtil.getFilePath("green_tripdata_2018-01_01-15.csv");
    List<String> tripsLines = FileReaderUtil.readFile(greenTrips);
    List<ParsedTrip> parsedTrips = CsvParser.parseGreenTrips(tripsLines);
    saveTrips(parsedTrips, zonesMap);
  }

  private void saveTrips(List<ParsedTrip> parsedTrips, HashMap<Long, Zone> zonesMap) {
    parsedTrips.forEach(parsedTrip -> {
      Zone pickupZone = zonesMap.get(parsedTrip.getPickupZoneId());
      pickupZone.incrementPickupTotal();
      Zone dropoffZone = zonesMap.get(parsedTrip.getDropoffZoneId());
      dropoffZone.incrementDropoffTotal();
      Trip trip = Trip.builder()
          .pickupDate(parsedTrip.getPickupDate())
          .dropoffDate(parsedTrip.getDropoffDate())
          .pickupZone(pickupZone)
          .dropoffZone(dropoffZone)
          .type(parsedTrip.getType())
          .build();
        tripRepository.save(trip);
    });
  }

  private HashMap<Long, Zone> loadAndSaveZones(){
    String zonesFilesPath = FileReaderUtil.getFilePath("zones.csv");
    List<String> zonesLines = FileReaderUtil.readFile(zonesFilesPath);
    List<ParsedZone> parsedZones = CsvParser.parsedZones(zonesLines);
    return saveZones(parsedZones);
  }

  private HashMap<Long, Zone> saveZones(List<ParsedZone> parsedZones){
    HashMap<Long, Zone> zonesMap = new HashMap<>();
    parsedZones.forEach(parsedZone -> {
      Zone zone = Zone.builder()
          .locationId(parsedZone.getLocationId())
          .title(parsedZone.getTitle())
          .build();
      zoneRepository.save(zone);
      zonesMap.put(zone.getLocationId(), zone);
    });
    return zonesMap;
  }

  @Override
  @Transactional(readOnly = true)
  public TopZonesDTO getTopZones(String order) {
    PriorityQueue<TopZoneDTO> topFiveZonesQueue = new PriorityQueue<>(5, new ZoneComparator(order));
    List<Zone> zones = zoneRepository.findAll();
    zones.forEach(zone -> {
      TopZoneDTO topZoneDTO = TopZoneDTO.builder()
          .zone(zone.getTitle())
          .doTotal(zone.getDropoffTotal())
          .puTotal(zone.getPickupTotal())
          .build();
      topFiveZonesQueue.add(topZoneDTO);
      if(topFiveZonesQueue.size() > 5){
        topFiveZonesQueue.poll();
      }
    });

    List<TopZoneDTO> topFiveZones = new ArrayList<>(topFiveZonesQueue.stream().sorted(new ZoneComparator(order)).toList());
    Collections.reverse(topFiveZones);

    return TopZonesDTO.builder()
        .topZones(topFiveZones)
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public ZoneTripsDTO getZoneTrips(long id, LocalDate date) {
    Optional<Zone> zoneOptional = zoneRepository.findByLocationId(id);
    if(zoneOptional.isPresent()){
      Zone zone = zoneOptional.get();
      long pickupsTripsOnDate = zone.getPickupTrips().stream()
          .filter(z -> date.isEqual(z.getPickupDate()))
          .toList().size();
      long dropoffsTripsOnDate = zone.getDropoffTrips().stream()
          .filter(z -> date.isEqual(z.getDropoffDate()))
          .toList().size();
      return ZoneTripsDTO.builder()
          .zone(zone.getTitle())
          .date(date.toString())
          .pu(pickupsTripsOnDate)
          ._do(dropoffsTripsOnDate)
          .build();
    }

    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public TripsDTO getYellowTrips(LocalDate date, Pageable pageable) {
    Page<Trip> trips = tripRepository.findAllByTypeIsAndDropoffDateOrPickupDate(TripType.YELLOW, date, date, pageable);
    return TripsDTO.builder().tripsPage(trips).build();
  }
}
