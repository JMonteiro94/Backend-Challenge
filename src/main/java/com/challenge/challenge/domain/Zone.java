package com.challenge.challenge.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@ToString
@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zone")
public class Zone implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "locationId", nullable = false)
  private Long locationId;

  @Column(name = "title", nullable = false)
  private String title;

  @OneToMany(mappedBy = "pickupZone")
  @Exclude
  private List<Trip> pickupTrips;

  @OneToMany(mappedBy = "dropoffZone")
  @Exclude
  private List<Trip> dropoffTrips;

}
