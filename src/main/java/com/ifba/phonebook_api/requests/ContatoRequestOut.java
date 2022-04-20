package com.ifba.phonebook_api.requests;

import java.util.List;
import java.util.stream.Collectors;

import com.ifba.phonebook_api.model.Contato;
import com.ifba.phonebook_api.model.Endereco;
import com.ifba.phonebook_api.model.NumeroContato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContatoRequestOut {
    private Long id;
    private String nome;
    private String email;
    private Endereco endereco;
    private List<NumeroContato> numeroContato;

    public ContatoRequestOut(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.endereco = contato.getEndereco();
        this.numeroContato = contato.getNumeroContato();
    }

    public static List<ContatoRequestOut> converte(List<Contato> lista) {
        return lista.stream().map(ContatoRequestOut::new).collect(Collectors.toList());
    }
}
