package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Integer idProduct;
    private Integer idCategory;
    private String nameProduct;
    private String descriptionProduct;
    private double priceProduct;
    private boolean enabledProduct;
}
