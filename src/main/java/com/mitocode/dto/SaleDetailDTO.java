package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//Aquellos campos que esten marcados como null en la respuesta no los muestra
public class SaleDetailDTO {

    @JsonBackReference
    private SaleDTO sale;

    @JsonIncludeProperties(value = {"idProduct"})
    @NotNull
    private ProductDTO product;

    @Min(value = 1)
    private short quantity;

    @Min(value = 1)
    private double salePrice;

    @Min(value = 0)
    private double discount;
}
