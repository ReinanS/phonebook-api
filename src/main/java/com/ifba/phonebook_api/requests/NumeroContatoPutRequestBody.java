package com.ifba.phonebook_api.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.ifba.phonebook_api.model.Categoria;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NumeroContatoPutRequestBody {
    
    @Min(1)
    Long id;
    
    @NotBlank
    private String numero;

    private Categoria categoria;
}
