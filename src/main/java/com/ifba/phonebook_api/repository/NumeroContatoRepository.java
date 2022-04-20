package com.ifba.phonebook_api.repository;

import com.ifba.phonebook_api.model.NumeroContato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumeroContatoRepository extends JpaRepository<NumeroContato, Long> {
    
}
