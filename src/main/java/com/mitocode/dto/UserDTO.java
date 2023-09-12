package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDTO {

    private Integer idUser;

    //Para tomar en cuenta tanto en lectura como escritura solamente los atributos considerados
    @JsonIncludeProperties(value = {"idRole", "nameRole"})
    @NotNull
    private RoleDTO role;

    @JsonProperty(value = "user_name")//Para comunicarme con un externo y conservar el código Jaba
    // Es muy util cuando estas integrando sistemas. Cuando el programador o hay un lenguaje con otro estandart
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String userName;

    //@JsonIgnore Sirve para ignorar el campo tanto para lectura como para escritura.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Leer atributo y no mostrarlo
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY) // Mostrar el atributo y no usarlo en el momento de entrada
    @Size(min = 10, max = 60)
    private String password;

    private boolean enabled;

}
