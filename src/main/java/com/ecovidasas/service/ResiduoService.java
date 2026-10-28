package com.ecovidasas.service;

import com.ecovidasas.entity.Residuo;
import com.ecovidasas.repository.RecoleccionRepository;
import com.ecovidasas.repository.ResiduoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*
    Servicio encargado de gestionar
    las operaciones relacionadas con
    los residuos de Eco Vida.
*/
@Service
public class ResiduoService {

    @Autowired
    private ResiduoRepository residuoRepository;

    /*
        Repositorio de recolecciones.

        Se utiliza para comprobar si un residuo
        está asociado a alguna recolección antes
        de intentar eliminarlo.
    */
    @Autowired
    private RecoleccionRepository recoleccionRepository;

    /*
        Obtiene todos los residuos
        registrados en la base de datos.
    */
    public List<Residuo> listarResiduos() {
        return residuoRepository.findAll();
    }

    /*
        Busca un residuo utilizando
        su identificador.
    */
    public Residuo obtenerResiduoPorId(Long id) {
        return residuoRepository.findById(id).orElse(null);
    }

    /*
        Guarda un nuevo residuo
        o actualiza uno existente.
    */
    public Residuo guardarResiduo(Residuo residuo) {
        return residuoRepository.save(residuo);
    }

    /*
        Elimina un residuo por su ID.

        Antes de eliminarlo se comprueba si existe
        alguna recolección asociada al residuo.

        Esto protege la integridad referencial
        de la base de datos y evita eliminar un
        residuo que todavía está siendo utilizado
        por información histórica u operativa.
    */
    public void eliminarResiduo(Long id) {

        /*
            Primero verificamos que el residuo
            exista en la base de datos.
        */
        if (!residuoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No existe un residuo con el ID " + id
            );
        }

        /*
            Comprobamos si alguna recolección
            utiliza este residuo.
        */
        boolean tieneRecolecciones =
                recoleccionRepository.existsByResiduo_Id(id);

        /*
            Si existe una recolección asociada,
            no permitimos eliminar el residuo.

            HTTP 409 Conflict indica que la operación
            entra en conflicto con el estado actual
            de los datos.
        */
        if (tieneRecolecciones) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el residuo porque está asociado a una o más recolecciones."
            );
        }

        /*
            Si no tiene recolecciones asociadas,
            podemos eliminarlo normalmente.
        */
        residuoRepository.deleteById(id);
    }
}