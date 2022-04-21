package com.ifba.phonebook_api.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnderecoPutRequestBody {

    @Min(1)
    private Long id;

    @NotBlank
    private String pais;

    @NotBlank
    private String estado;

    @NotBlank
    private String cep;
}
