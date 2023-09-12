package com.mitocode.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity//(name = "CategoryEntity")
//@Table(name = "btl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCategory;//CamelCase -> lowerCamelCase | UpperCamelCase | BD: snake

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)//, columnDefinition = "nvarchar2")
    private String description;

    @Column(nullable = false) //No es necesario porque un primitivo siempre tiene un valor, se coloca por forma
    private boolean enable;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
