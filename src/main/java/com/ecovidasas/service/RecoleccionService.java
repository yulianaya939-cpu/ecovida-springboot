package com.ecovidasas.service;

import com.ecovidasas.entity.Recoleccion;

import java.util.List;

/*
    Servicio que define las operaciones
    disponibles para gestionar las
    recolecciones de Eco Vida.
*/
public interface RecoleccionService {

    /*
        Consulta todas las recolecciones.
    */
    List<Recoleccion> listarTodas();

    /*
        Busca una recolección por su ID.
    */
    Recoleccion buscarPorId(Long id);

    /*
        Guarda una nueva recolección
        o actualiza una existente.
    */
    Recoleccion guardar(Recoleccion recoleccion);

    /*
        Elimina una recolección
        utilizando su identificador.
    */
    void eliminar(Long id);
}