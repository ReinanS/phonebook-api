package com.ifba.phonebook_api.requests;

import java.util.List;
import java.util.stream.Collectors;

import com.ifba.phonebook_api.model.Contato;
import com.ifba.phonebook_api.model.Endereco;

import org.springframework.data.domain.Page;

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
    private List<NumeroContatoRequestOut> numerosContatos;

    public ContatoRequestOut(Contato contato) {
        this.id = contato.getId();
        this.nome = contato.getNome();
        this.email = contato.getEmail();
        this.endereco = contato.getEndereco();
        this.numerosContatos = NumeroContatoRequestOut.converte(contato.getNumeroContato());
    }

    public static List<ContatoRequestOut> converte(List<Contato> lista) {
        return lista.stream().map(ContatoRequestOut::new).collect(Collectors.toList());
    }

    public static Page<ContatoRequestOut> converteList(Page<Contato> page) {
        return page.map(ContatoRequestOut::new);
    }
}
