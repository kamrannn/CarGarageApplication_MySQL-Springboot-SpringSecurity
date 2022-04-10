package com.app.cargarage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Builder
public class CarDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JsonIgnore
    private String documentType;
    private String documentName;
    @JsonIgnore
    @Lob
    private byte[] document;

    public CarDocument() {
    }

    public CarDocument(String documentType, String documentName, byte[] document) {
        this.documentType = documentType;
        this.documentName = documentName;
        this.document = document;
    }
}
