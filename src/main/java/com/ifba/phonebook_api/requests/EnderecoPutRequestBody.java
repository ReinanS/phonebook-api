package com.ifba.phonebook_api.requests;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnderecoPutRequestBody {

    @NotBlank
    private String pais;

    @NotBlank
    private String estado;

    @NotBlank
    private String cep;
}
