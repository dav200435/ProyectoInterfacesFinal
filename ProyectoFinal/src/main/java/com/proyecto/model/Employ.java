package com.proyecto.model;

public class Employ {
    private int idEmpleado;
    private String nombre;
    private String cargo;
    private String fechaContratacion;

    // Constructor, getters y setters
    public Employ(int idEmpleado, String nombre, String cargo, String fechaContratacion) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.fechaContratacion = fechaContratacion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }
}
