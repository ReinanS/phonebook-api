package com.ifba.phonebook_api.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.ifba.phonebook_api.requests.EnderecoPostRequestBody;
import com.ifba.phonebook_api.requests.EnderecoPutRequestBody;
import com.ifba.phonebook_api.requests.EnderecoRequestOut;
import com.ifba.phonebook_api.service.EnderecoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    final EnderecoService enderecoService;

    @ApiOperation(value = "Retorna todos os enderecos")
    @GetMapping
    public ResponseEntity<List<EnderecoRequestOut>> listAll() {
        List<EnderecoRequestOut> enderecos = enderecoService.listAll();
        return new ResponseEntity<List<EnderecoRequestOut>>(enderecos, HttpStatus.OK);

    }

    @ApiOperation(value = "Salva um endereço")
    @PostMapping
    public ResponseEntity<EnderecoRequestOut> save(@Valid @RequestBody EnderecoPostRequestBody endereco) {
        EnderecoRequestOut enderecoRequestOut = enderecoService.save(endereco);
        return new ResponseEntity<EnderecoRequestOut>(enderecoRequestOut, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna um endereço por id")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoRequestOut> detail(@PathVariable Long id) {
        EnderecoRequestOut endereco = enderecoService.detail(id);
        return new ResponseEntity<EnderecoRequestOut>(endereco, HttpStatus.OK);
    }

    @ApiOperation(value = "Atualiza um endereco")
    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizar(@Valid @RequestBody EnderecoPutRequestBody enderecoPutRequestBody) {
        enderecoService.replace(enderecoPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Deleta um endereco por id")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}