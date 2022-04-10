package com.app.cargarage.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity = RepairOperations.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id", referencedColumnName = "id")
    List<RepairOperations> repairOperationsList = new ArrayList<>();

    @ManyToMany(targetEntity = Part.class, fetch = FetchType.LAZY)
    List<Part> partsList = new ArrayList<>();

}
