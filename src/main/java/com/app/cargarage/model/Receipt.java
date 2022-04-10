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
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;

    @OneToMany(targetEntity = RepairOperations.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<RepairOperations> repairOperationsList = new ArrayList<>();

    @OneToOne
    Customer customer;
}
