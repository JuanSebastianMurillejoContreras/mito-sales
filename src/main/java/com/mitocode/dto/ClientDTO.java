package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Empty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private Integer idClient;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String lastName;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 10)
    private String cardId;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String address;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String country;



}
