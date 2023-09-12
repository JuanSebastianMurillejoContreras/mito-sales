package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(IngressDetailPK.class)
public class IngressDetail {

    @Id
    private Ingress ingress;

    @Id
    private Product product;


    @Column(nullable = false)
    private Integer quantity;

    @Column(columnDefinition = "decimal(6,2)", nullable = false)
    private double cost;


}
