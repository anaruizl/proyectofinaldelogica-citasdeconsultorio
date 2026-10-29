package com.example.consultoriodecitas.model;

public class Medico {
    private int Id;
    private String Nombre;
    private String Especialidad;

    public Medico(int Id, String Nombre, String Especialidad) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Especialidad = Especialidad;

    }

    public int getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getEspecialidad() {
        return Especialidad;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;

    }

    public void setEspecialidad(String Especialidad) {
        this.Especialidad = Especialidad;
    }

    @Override
    public String toString() {
        return Nombre + " - " + Especialidad;
    }
}
