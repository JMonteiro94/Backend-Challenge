package com.challenge.challenge.repository;

import com.challenge.challenge.domain.Trip;
import com.challenge.challenge.domain.enums.TripType;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long>  {

  Page<Trip> findAllByTypeIsAndDropoffDateOrPickupDate(TripType type, LocalDate dropoffDate, LocalDate pickupDate, Pageable pageable);
}
