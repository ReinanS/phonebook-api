package com.ifba.phonebook_api.requests;

import java.util.List;
import java.util.stream.Collectors;

import com.ifba.phonebook_api.model.Categoria;
import com.ifba.phonebook_api.model.NumeroContato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NumeroContatoRequestOut {
    private Long id;
    private String numero;
    private Categoria categoria;

    public NumeroContatoRequestOut(NumeroContato numeroContato) {
        this.id = numeroContato.getId();
        this.numero = numeroContato.getNumero();
        this.categoria = numeroContato.getCategoria();
    }

    public static List<NumeroContatoRequestOut> converte(List<NumeroContato> lista) {
        return lista.stream().map(NumeroContatoRequestOut::new).collect(Collectors.toList());
    }
}
