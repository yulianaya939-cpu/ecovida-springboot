package com.ecovidasas.service;

import com.ecovidasas.entity.Recoleccion;
import com.ecovidasas.repository.RecoleccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    Implementación del servicio encargado
    de gestionar las recolecciones de Eco Vida.
*/
@Service
public class RecoleccionServiceImpl implements RecoleccionService {

    @Autowired
    private RecoleccionRepository recoleccionRepository;

    /*
        Obtiene todas las recolecciones
        registradas en la base de datos.
    */
    @Override
    public List<Recoleccion> listarTodas() {
        return recoleccionRepository.findAll();
    }

    /*
        Busca una recolección utilizando
        su identificador.
    */
    @Override
    public Recoleccion buscarPorId(Long id) {
        return recoleccionRepository.findById(id).orElse(null);
    }

    /*
        Guarda una nueva recolección
        o actualiza una existente.
    */
    @Override
    public Recoleccion guardar(Recoleccion recoleccion) {
        return recoleccionRepository.save(recoleccion);
    }

    /*
        Elimina una recolección por su ID.
    */
    @Override
    public void eliminar(Long id) {
        recoleccionRepository.deleteById(id);
    }
}