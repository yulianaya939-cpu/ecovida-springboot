package com.ecovidasas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
    Servicio encargado de consultar
    información climática externa para
    apoyar la planificación de las
    recolecciones de Eco Vida.
*/
@Service
public class ClimaService {

    /*
        API pública externa Open-Meteo.

        Coordenadas de Cartagena, Bolívar.
        Se consulta la temperatura actual,
        precipitación y velocidad del viento.
    */
    private final String URL_API =
            "https://api.open-meteo.com/v1/forecast"
            + "?latitude=10.3910"
            + "&longitude=-75.4794"
            + "&current=temperature_2m,precipitation,wind_speed_10m"
            + "&timezone=America/Bogota";

    /*
        Consume la API pública externa
        y devuelve la información obtenida.
    */
    public String obtenerClima() {

        System.out.println(
                "Eco Vida - Consultando información climática externa..."
        );

        RestTemplate restTemplate = new RestTemplate();

        String respuesta = restTemplate.getForObject(
                URL_API,
                String.class
        );

        System.out.println(
                "Eco Vida - Información climática obtenida correctamente."
        );

        return respuesta;
    }
}