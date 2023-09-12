package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private Integer idCategory;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)//Cantidad de caracteres
    private String nameofCategory;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String descriptionCategory;

    //@NotNull Los primitivos nucna reciben un null
    private boolean enabledCategory;

    /*
    @Max(99)
    @Min(1)
    @Positive
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "[0-9]")
    private String special;

    Para conocer más anotaciones busca en internet jakarta.validation.constraints

    */
}
