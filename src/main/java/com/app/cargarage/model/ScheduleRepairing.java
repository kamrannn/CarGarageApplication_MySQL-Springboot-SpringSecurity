package com.app.cargarage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ScheduleRepairing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("schedule_repairing_id")
    private long id;
    @JsonProperty("repairing_start_time")
    private LocalDateTime startDateTime;
    @JsonProperty("repairing_end_time")
    private LocalDateTime endDateTime;

    @OneToOne(targetEntity = Car.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Car car;
}
