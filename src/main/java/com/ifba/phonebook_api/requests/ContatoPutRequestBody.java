package com.ifba.phonebook_api.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContatoPutRequestBody {
    
    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    private EnderecoPutRequestBody enderecoPutRequestBody;

    private List<NumeroContatoPutRequestBody> numeroContatoPutRequestBodies;
}
