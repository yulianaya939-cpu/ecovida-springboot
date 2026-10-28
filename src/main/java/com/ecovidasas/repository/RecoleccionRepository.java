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

    /*
        Comprueba si existe alguna recolección
        asociada a un residuo determinado.

        Spring Data JPA genera automáticamente
        la consulta a partir del nombre del método.

        Se utiliza para evitar eliminar un residuo
        que todavía está siendo utilizado por una
        recolección.
    */
    boolean existsByResiduo_Id(Long residuoId);
}