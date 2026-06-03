package com.ecovidasas.service;

import com.ecovidasas.entity.Cliente;
import com.ecovidasas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // LISTAR
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // BUSCAR POR ID
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    // GUARDAR
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // ELIMINAR
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
