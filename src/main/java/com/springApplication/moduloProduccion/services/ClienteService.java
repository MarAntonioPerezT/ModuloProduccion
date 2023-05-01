package com.springApplication.moduloProduccion.services;


import com.springApplication.moduloProduccion.models.Cliente;
import com.springApplication.moduloProduccion.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<String> findAllClientesByName(){
        return clienteRepository.findAllClientesByNombre();
    }

    public List<Cliente> findAllClientes(){
        return clienteRepository.findAll();
    }

    public Cliente findClienteByNombre(String nombre){
        return clienteRepository.findByNombre(nombre);
    }
}
