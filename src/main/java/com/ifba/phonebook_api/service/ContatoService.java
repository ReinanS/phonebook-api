package com.ifba.phonebook_api.service;

import java.util.ArrayList;
import java.util.List;

import com.ifba.phonebook_api.model.Contato;
import com.ifba.phonebook_api.model.Endereco;
import com.ifba.phonebook_api.model.NumeroContato;
import com.ifba.phonebook_api.repository.ContatoRepository;
import com.ifba.phonebook_api.requests.ContatoPutRequestBody;
import com.ifba.phonebook_api.requests.ContatoRequestIn;
import com.ifba.phonebook_api.requests.ContatoRequestOut;
import com.ifba.phonebook_api.requests.EnderecoPostRequestBody;
import com.ifba.phonebook_api.requests.EnderecoRequestOut;
import com.ifba.phonebook_api.requests.NumeroContatoRequestIn;
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

    public ContatoRequestOut save(ContatoRequestIn contatoRequestIn) throws Exception {

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


    private List<NumeroContato> saveContatos(List<NumeroContatoRequestIn> numsContatosBody) throws Exception {
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

    public List<ContatoRequestOut> listAll() {
        List<Contato> contatos = contatoRepository.findAll();
        return ContatoRequestOut.converte(contatos);
    }

    public ContatoRequestOut detail(Long id) {
        return new ContatoRequestOut(findByIdOrThrowNotFoundRequestException(id));
    }


    // public void replace(Long id, ContatoPutRequestBody contatoPutRequestBody) {
    //     Contato contatoSaved = findByIdOrThrowNotFoundRequestException(id);
    //     Endereco enderecoSaved = contatoSaved.getEndereco();
    //     List<NumeroContato>  nContatosSaved = contatoSaved.getNumeroContato();

    //     enderecoService.replace(enderecoSaved.getId(), contatoPutRequestBody.getEnderecoPutRequestBody());
    //     Endereco enderecoReplaced = enderecoService.findByIdOrThrowNotFoundRequestException(enderecoSaved.getId());


    //     Contato contato = Contato.builder()
    //             .id(contatoSaved.getId())
    //             .nome(contatoPutRequestBody.getNome())
    //             .email(contatoPutRequestBody.getEmail())
    //             .endereco(enderecoReplaced)
    //             .numeroContato(numeroContato)
    //             .build();
    // }
}
