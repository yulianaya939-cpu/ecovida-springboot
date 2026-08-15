package com.ecovidasas.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/*
    Entidad que representa una recolección
    de residuos dentro del sistema Eco Vida.

    Una recolección permite registrar el proceso
    mediante el cual se recoge un residuo asociado
    a un cliente.
*/
@Entity
@Table(name = "recoleccion")
public class Recoleccion {

    /*
        Identificador único de la recolección.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Fecha en la que se realiza
        o se programa la recolección.
    */
    @Column(nullable = false)
    private LocalDate fecha;

    /*
        Departamento donde se realiza
        la recolección.

        Este dato posteriormente podrá
        ser seleccionado utilizando
        la API pública externa.
    */
    @Column(nullable = false)
    private String departamento;

    /*
        Municipio donde se realiza
        la recolección.
    */
    @Column(nullable = false)
    private String municipio;

    /*
        Estado actual de la recolección.

        Ejemplos:
        - Pendiente
        - Programada
        - Realizada
        - Cancelada
    */
    @Column(nullable = false)
    private String estado;

    /*
        Cliente que solicita o tiene asociada
        la recolección.
    */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /*
        Residuo que será recogido
        durante la recolección.
    */
    @ManyToOne
    @JoinColumn(name = "residuo_id", nullable = false)
    private Residuo residuo;

    /*
        Constructor vacío requerido por JPA.
    */
    public Recoleccion() {
    }

    /*
        Getters y setters.
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Residuo getResiduo() {
        return residuo;
    }

    public void setResiduo(Residuo residuo) {
        this.residuo = residuo;
    }
}