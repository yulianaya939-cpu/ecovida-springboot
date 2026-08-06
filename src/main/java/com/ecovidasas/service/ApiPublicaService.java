package com.ecovidasas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
    Servicio encargado de consumir
    una API pública externa.

    Para esta evidencia se utiliza
    JSONPlaceholder.
*/
@Service
public class ApiPublicaService {

    /*
        URL de la API pública.
    */
    private final String URL_API =
            "https://jsonplaceholder.typicode.com/users";

    /*
        Consume la API pública
        y devuelve la información
        obtenida.
    */
    public String obtenerUsuarios() {

        System.out.println("======================================");
        System.out.println("Consumiendo API pública...");
        System.out.println("URL: " + URL_API);

        RestTemplate restTemplate = new RestTemplate();

        String respuesta = restTemplate.getForObject(
                URL_API,
                String.class
        );

        System.out.println("Respuesta recibida correctamente.");
        System.out.println("======================================");

        return respuesta;

    }

}