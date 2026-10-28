package com.ecovidasas.controller;

import com.ecovidasas.entity.Residuo;
import com.ecovidasas.service.ResiduoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    @Autowired
    private ResiduoService residuoService;


    // =========================================================
    // LISTAR RESIDUOS
    // =========================================================

    @GetMapping
    public List<Residuo> listarResiduos() {
        return residuoService.listarResiduos();
    }


    // =========================================================
    // BUSCAR RESIDUO POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Residuo> buscarResiduo(
            @PathVariable Long id) {

        Residuo residuo =
                residuoService.obtenerResiduoPorId(id);

        /*
         * Si el residuo no existe,
         * se devuelve 404 Not Found.
         */
        if (residuo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(residuo);
    }


    // =========================================================
    // REGISTRAR RESIDUO
    // =========================================================

    @PostMapping
    public Residuo guardarResiduo(
            @Valid @RequestBody Residuo residuo) {

        return residuoService.guardarResiduo(residuo);
    }


    // =========================================================
    // ACTUALIZAR RESIDUO
    // =========================================================

    @PutMapping("/{id}")
    public Residuo actualizarResiduo(
            @PathVariable Long id,
            @Valid @RequestBody Residuo residuo) {

        residuo.setId(id);

        return residuoService.guardarResiduo(residuo);
    }


    // =========================================================
    // ELIMINAR RESIDUO
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResiduo(
            @PathVariable Long id) {

        try {

            /*
             * Intentamos eliminar el residuo utilizando
             * la lógica de negocio definida en el servicio.
             */
            residuoService.eliminarResiduo(id);

            /*
             * Si la eliminación fue exitosa,
             * devolvemos HTTP 200 OK con un mensaje
             * que confirma la operación.
             */
            return ResponseEntity.ok(
                    Map.of(
                            "message",
                            "El residuo se eliminó correctamente."
                    )
            );

        } catch (org.springframework.web.server.ResponseStatusException e) {

            /*
             * Si el servicio detecta un conflicto,
             * por ejemplo, porque el residuo está asociado
             * a una recolección, devolvemos explícitamente
             * el código HTTP y un mensaje entendible.
             */
            if (e.getStatusCode() == HttpStatus.CONFLICT) {

                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(
                                Map.of(
                                        "message",
                                        "No se puede eliminar este residuo porque está asociado a una o más recolecciones."
                                )
                        );
            }

            /*
             * Si el servicio informa que el residuo
             * no existe, devolvemos 404.
             */
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(
                                Map.of(
                                        "message",
                                        "No existe un residuo con el ID " + id + "."
                                )
                        );
            }

            /*
             * Para cualquier otro ResponseStatusException,
             * conservamos el código HTTP correspondiente.
             */
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(
                            Map.of(
                                    "message",
                                    e.getReason() != null
                                            ? e.getReason()
                                            : "No fue posible realizar la operación."
                            )
                    );
        }
    }
}