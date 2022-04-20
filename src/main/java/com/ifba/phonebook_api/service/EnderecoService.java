package com.ifba.phonebook_api.service;

import java.util.List;

import com.ifba.phonebook_api.model.Endereco;
import com.ifba.phonebook_api.repository.EnderecoRepository;
import com.ifba.phonebook_api.requests.EnderecoPostRequestBody;
import com.ifba.phonebook_api.requests.EnderecoPutRequestBody;
import com.ifba.phonebook_api.requests.EnderecoRequestOut;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public Endereco findByIdOrThrowNotFoundRequestException(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não Encontrado"));
    }

    public EnderecoRequestOut save(EnderecoPostRequestBody enderecoPostRequestBody) {
        Endereco endereco = Endereco.builder()
                    .pais(enderecoPostRequestBody.getPais())
                    .estado(enderecoPostRequestBody.getEstado())
                    .cep(enderecoPostRequestBody.getCep())
                    .build();

        enderecoRepository.save(endereco);
        return new EnderecoRequestOut(endereco);
    }


    public EnderecoRequestOut detail(Long id) {
        return new EnderecoRequestOut(findByIdOrThrowNotFoundRequestException(id));
    }

    public List<EnderecoRequestOut>  listAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return EnderecoRequestOut.converte(enderecos);
    }

    public void replace(Long id, EnderecoPutRequestBody enderecoPutRequestBody) {
        Endereco enderecoSaved = findByIdOrThrowNotFoundRequestException(id);
        Endereco endereco = Endereco.builder()
                    .id(enderecoSaved.getId())
                    .cep(enderecoPutRequestBody.getCep())
                    .estado(enderecoPutRequestBody.getEstado())
                    .pais(enderecoPutRequestBody.getPais())
                    .build();

        enderecoRepository.save(endereco);
    }

    public void delete(Long id) {
        enderecoRepository.delete(findByIdOrThrowNotFoundRequestException(id));
    }
}
