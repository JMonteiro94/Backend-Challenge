package com.challenge.challenge.repository;

import com.challenge.challenge.domain.Zone;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

  Optional<Zone> findByLocationId(long id);
}
