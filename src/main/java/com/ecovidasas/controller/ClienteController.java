package com.ecovidasas.controller;

import com.ecovidasas.entity.Cliente;
import com.ecovidasas.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Permite que React (localhost:5173)
 * pueda consumir esta API.
 */
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODOS
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public Cliente buscarCliente(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    // CREAR CLIENTE
    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteService.guardarCliente(cliente);
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable Long id,
                                     @RequestBody Cliente cliente) {

        cliente.setId(id);

        return clienteService.guardarCliente(cliente);
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {

        clienteService.eliminarCliente(id);
    }
}