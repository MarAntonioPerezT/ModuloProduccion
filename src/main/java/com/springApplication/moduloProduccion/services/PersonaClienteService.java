package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.repositories.PersonaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaClienteService {

    @Autowired
    private PersonaClienteRepository repository;

    public PersonaClienteService(PersonaClienteRepository repository){
        this.repository = repository;
    }

    public List<String> findAllClientesByRFC(){
        return repository.findAllClientesRFC();
    }
}
