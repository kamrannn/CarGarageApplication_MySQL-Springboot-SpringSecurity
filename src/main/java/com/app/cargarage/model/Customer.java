package com.app.cargarage.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("customer_id")
    private long id;
    @JsonProperty("customer_surname")
    private String surname;
    @JsonProperty("customer_phone_number")
    private String phoneNumber;
    @JsonProperty("customer_address")
    private String address;

    @OneToMany(targetEntity = Car.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonManagedReference
    List<Car> carList = new ArrayList<>();
}
