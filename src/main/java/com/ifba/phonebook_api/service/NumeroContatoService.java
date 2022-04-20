package com.ifba.phonebook_api.service;

import java.util.List;

import com.ifba.phonebook_api.model.NumeroContato;
import com.ifba.phonebook_api.repository.NumeroContatoRepository;
import com.ifba.phonebook_api.requests.NumeroContatoRequestIn;
import com.ifba.phonebook_api.requests.NumeroContatoRequestOut;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NumeroContatoService {
    private final NumeroContatoRepository numeroContatoRepository;

    public NumeroContato findByIdOrThrowNotFoundRequestException(Long id) {
        return numeroContatoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Número não encontrado"));
    }

    public NumeroContatoRequestOut save(NumeroContatoRequestIn numeroContatoPostRequestBody) {
        NumeroContato numeroContato = NumeroContato.builder()
            .numero(numeroContatoPostRequestBody.getNumero())
            .categoria(numeroContatoPostRequestBody.getCategoria())
            .build();

        numeroContatoRepository.save(numeroContato);
        return new NumeroContatoRequestOut(numeroContato);
    }

    public NumeroContatoRequestOut detail(Long id) {
        return new NumeroContatoRequestOut(findByIdOrThrowNotFoundRequestException(id));
    }

    public List<NumeroContatoRequestOut> listAll() {
        List<NumeroContato> numerosContatos = numeroContatoRepository.findAll();
        return NumeroContatoRequestOut.converte(numerosContatos);
    }

    public void replace(Long id, NumeroContatoRequestIn numeroContatoPutRequestBody) {
        NumeroContato numeroContatoSaved = findByIdOrThrowNotFoundRequestException(id);
        NumeroContato numeroContato = NumeroContato.builder()
                        .id(numeroContatoSaved.getId())
                        .numero(numeroContatoPutRequestBody.getNumero())
                        .categoria(numeroContatoPutRequestBody.getCategoria())
                        .build();
        
        numeroContatoRepository.save(numeroContato);
    }

    public void delete(Long id) {
        numeroContatoRepository.delete(findByIdOrThrowNotFoundRequestException(id));
    }
}
