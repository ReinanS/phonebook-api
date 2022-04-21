package com.ifba.phonebook_api.service;

import java.util.ArrayList;
import java.util.List;

import com.ifba.phonebook_api.model.Contato;
import com.ifba.phonebook_api.model.Endereco;
import com.ifba.phonebook_api.model.NumeroContato;
import com.ifba.phonebook_api.repository.ContatoRepository;
import com.ifba.phonebook_api.requests.ContatoPutRequestBody;
import com.ifba.phonebook_api.requests.ContatoPostRequestBody;
import com.ifba.phonebook_api.requests.ContatoRequestOut;
import com.ifba.phonebook_api.requests.EnderecoPostRequestBody;
import com.ifba.phonebook_api.requests.EnderecoRequestOut;
import com.ifba.phonebook_api.requests.NumeroContatoPostRequestBody;
import com.ifba.phonebook_api.requests.NumeroContatoRequestOut;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final EnderecoService enderecoService;
    private final NumeroContatoService numeroContatoService;
    

    private Contato findByIdOrThrowNotFoundRequestException(Long id) {
        return contatoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contato não encontrado"));
    }

    public ContatoRequestOut save(ContatoPostRequestBody contatoRequestIn) throws Exception {

        List<NumeroContato> numerosContatos = saveContatos(contatoRequestIn.getNumerosContato());
        Endereco endereco = saveEndereco(contatoRequestIn.getEndereco());
        
        Contato contato = Contato.builder()
                    .nome(contatoRequestIn.getNome())
                    .email(contatoRequestIn.getEmail())
                    .numeroContato(numerosContatos)
                    .endereco(endereco)
                    .build();

        contatoRepository.save(contato);
        return new ContatoRequestOut(contato);
    }

    public List<ContatoRequestOut> listAll() {
        List<Contato> contatos = contatoRepository.findAll();
        return ContatoRequestOut.converte(contatos);
    }

    public ContatoRequestOut detail(Long id) {
        return new ContatoRequestOut(findByIdOrThrowNotFoundRequestException(id));
    }

    public void replace(ContatoPutRequestBody contatoPutRequestBody) {
        
        // Substitue os contatos
        enderecoService.replace(contatoPutRequestBody.getEndereco());

         // Substitue os contatos
        contatoPutRequestBody.getNumerosContatos().forEach(nContato -> {
            numeroContatoService.replace(nContato);
        });

        // Substitue os contatos
        Contato contatoSaved = findByIdOrThrowNotFoundRequestException(contatoPutRequestBody.getId());

        Contato contato = Contato.builder()
                .id(contatoSaved.getId())
                .nome(contatoPutRequestBody.getNome())
                .email(contatoPutRequestBody.getEmail())
                .endereco(contatoSaved.getEndereco())
                .numeroContato(contatoSaved.getNumeroContato())
                .build();

        contatoRepository.save(contato);
    }

    public void delete(Long id) {
        contatoRepository.delete(findByIdOrThrowNotFoundRequestException(id));
    }

    private Endereco saveEndereco(EnderecoPostRequestBody enderecoPostRequestBody) {
        EnderecoRequestOut enderecoOut =
        enderecoService.save(enderecoPostRequestBody);

        Endereco enderecoContato = Endereco.builder()
                    .id(enderecoOut.getId())
                    .cep(enderecoOut.getCep())
                    .estado(enderecoOut.getEstado())
                    .pais(enderecoOut.getPais())
                    .build();

        return enderecoContato;
    }


    private List<NumeroContato> saveContatos(List<NumeroContatoPostRequestBody> numsContatosBody) throws Exception {
        List<NumeroContato> numContatos = new ArrayList<>();

        if(numsContatosBody.size() > 3) {
            throw new Exception("Contato deve ter no máximo 3 números");
        }

        numsContatosBody.forEach(num -> {
            NumeroContatoRequestOut numOut = numeroContatoService.save(num);

            NumeroContato numeroContato = NumeroContato.builder()
                            .id(numOut.getId())
                            .numero(numOut.getNumero())
                            .categoria(numOut.getCategoria())
                            .build();

            numContatos.add(numeroContato);
        });    
        
        return numContatos;
    }
}
