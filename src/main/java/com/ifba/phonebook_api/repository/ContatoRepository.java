package com.ifba.phonebook_api.repository;

import com.ifba.phonebook_api.model.Contato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository  extends JpaRepository<Contato, Long>{
    
}
