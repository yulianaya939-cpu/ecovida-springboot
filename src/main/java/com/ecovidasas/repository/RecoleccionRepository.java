package com.ecovidasas.repository;

import com.ecovidasas.entity.Recoleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
    Repositorio encargado de realizar
    las operaciones de acceso a datos
    para la entidad Recoleccion.
*/
@Repository
public interface RecoleccionRepository extends JpaRepository<Recoleccion, Long> {

}