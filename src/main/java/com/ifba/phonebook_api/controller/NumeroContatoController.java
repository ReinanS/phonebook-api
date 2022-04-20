package com.ifba.phonebook_api.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.ifba.phonebook_api.requests.NumeroContatoRequestIn;
import com.ifba.phonebook_api.requests.NumeroContatoRequestOut;
import com.ifba.phonebook_api.service.NumeroContatoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/numerosContatos")
public class NumeroContatoController {
    final NumeroContatoService numeroContatoService;

    @ApiOperation(value = "Retorna todos os números de contatos")
    @GetMapping
    public ResponseEntity<List<NumeroContatoRequestOut>> listAll() {
        List<NumeroContatoRequestOut> numerosContatos = numeroContatoService.listAll();
        return new ResponseEntity<List<NumeroContatoRequestOut>>(numerosContatos, HttpStatus.OK);
    }

    @ApiOperation(value = "Salva um número de contato")
    @PostMapping
    public ResponseEntity<NumeroContatoRequestOut> save(@Valid @RequestBody NumeroContatoRequestIn numero) {
        NumeroContatoRequestOut numeroContatoRequestOut = numeroContatoService.save(numero);
        return new ResponseEntity<NumeroContatoRequestOut>(numeroContatoRequestOut, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna um número de contato por id")
    @GetMapping("/{id}")
    public ResponseEntity<NumeroContatoRequestOut> detail(@PathVariable Long id) {
        NumeroContatoRequestOut numeroContato = numeroContatoService.detail(id);
        return new ResponseEntity<NumeroContatoRequestOut>(numeroContato, HttpStatus.OK);
    }

    @ApiOperation(value = "Atualiza um número de contato")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizar(@Valid @PathVariable @RequestBody Long id, NumeroContatoRequestIn numeroContatoRequestIn) {
        numeroContatoService.replace(id, numeroContatoRequestIn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Deleta um número de contato")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        numeroContatoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
