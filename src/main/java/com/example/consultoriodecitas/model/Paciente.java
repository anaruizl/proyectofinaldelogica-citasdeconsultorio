package com.example.consultoriodecitas.model;

import java.time.LocalDate;

public class Paciente {
    private String Documento;
    private String Nombre;
    private String Telefono;
    private LocalDate FechaDeNacimiento;

    public Paciente(String Documento, String Nombre, String Telefono, LocalDate FechaDeNacimiento) {
        this.Documento = Documento;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.FechaDeNacimiento = FechaDeNacimiento;

    }

    public String getDocumento() {
        return Documento;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public LocalDate getFechaDeNacimiento() {
        return FechaDeNacimiento;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public void setFechaDeNacimiento(LocalDate FechaDeNacimiento) {
        this.FechaDeNacimiento = FechaDeNacimiento;
    }

    @Override
    public String toString() {
        return Nombre + "( " + Documento + " )";
    }
}
