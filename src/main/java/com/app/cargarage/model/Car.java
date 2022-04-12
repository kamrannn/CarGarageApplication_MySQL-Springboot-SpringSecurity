package com.app.cargarage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String licensePlate;

    @OneToOne
    CarDocument carDocument;

    @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonBackReference
    Customer customer;
}
