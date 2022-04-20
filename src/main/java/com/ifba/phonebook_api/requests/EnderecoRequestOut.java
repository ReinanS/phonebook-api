package com.ifba.phonebook_api.requests;

import java.util.List;
import java.util.stream.Collectors;

import com.ifba.phonebook_api.model.Endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoRequestOut {
    private Long id;
    private String cep;
    private String estado;
    private String pais;


    public EnderecoRequestOut(Endereco endereco) {
        this.id = endereco.getId();
        this.cep = endereco.getCep();
        this.estado = endereco.getEstado();
        this.pais = endereco.getPais();
    }

    public static List<EnderecoRequestOut> converte(List<Endereco> lista) {
        return lista.stream().map(EnderecoRequestOut::new).collect(Collectors.toList());
    }
}
