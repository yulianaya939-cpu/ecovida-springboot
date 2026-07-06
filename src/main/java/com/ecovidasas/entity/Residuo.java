package com.ecovidasas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "residuo")
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String tipo;

    private Double cantidad;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    private String descripcion;

    /*
     Relación Muchos a Uno.
     Muchos residuos pueden pertenecer
     a un mismo cliente.
    */
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Residuo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*
     Obtiene el cliente asociado
     al residuo.
    */
    public Cliente getCliente() {
        return cliente;
    }

    /*
     Asigna un cliente al residuo.
    */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}