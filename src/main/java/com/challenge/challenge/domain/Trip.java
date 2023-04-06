package com.challenge.challenge.domain;

import com.challenge.challenge.domain.enums.TripType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
public class Trip implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pickupDate", nullable = false)
    private LocalDate pickupDate;

    @Column(name = "dropoffDate", nullable = false)
    private LocalDate dropoffDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TripType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pickupZone_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Zone pickupZone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dropoffZone_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Zone dropoffZone;
}
