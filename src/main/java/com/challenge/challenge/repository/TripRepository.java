package com.challenge.challenge.repository;

import com.challenge.challenge.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long>  {


}
