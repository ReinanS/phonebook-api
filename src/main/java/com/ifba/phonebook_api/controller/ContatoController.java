package com.ifba.phonebook_api.controller;

import java.util.List;

import javax.validation.Valid;

import com.ifba.phonebook_api.requests.ContatoRequestIn;
import com.ifba.phonebook_api.requests.ContatoRequestOut;
import com.ifba.phonebook_api.service.ContatoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contatos")
public class ContatoController {

    final ContatoService contatoService;

    @ApiOperation(value = "Retorna todos os contatos")
    @GetMapping
    public ResponseEntity<List<ContatoRequestOut>> listAll() {
        List<ContatoRequestOut> contatos = contatoService.listAll();
        return new ResponseEntity<List<ContatoRequestOut>>(contatos, HttpStatus.OK);
    }

    @ApiOperation(value = "Salva um contato")
    @PostMapping
    public ResponseEntity<ContatoRequestOut> save(@Valid @RequestBody ContatoRequestIn contatoRequestIn) throws Exception {
        ContatoRequestOut contato = contatoService.save(contatoRequestIn);
        return new ResponseEntity<ContatoRequestOut>(contato, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retorna um contato por id")
    @GetMapping("/{id}")
    public ResponseEntity<ContatoRequestOut> detail(@PathVariable Long id) {
        ContatoRequestOut contato = contatoService.detail(id);
        return new ResponseEntity<ContatoRequestOut>(contato, HttpStatus.OK);
    }

}
