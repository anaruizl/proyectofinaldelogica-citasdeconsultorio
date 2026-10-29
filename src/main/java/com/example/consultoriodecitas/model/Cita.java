package com.example.consultoriodecitas.model;

import java.time.LocalDateTime;

public class Cita {
    private int Id;
    private String DocumentoPaciente;
    private int IdMedico;
    private LocalDateTime FechaHora;
    private EstadodeCita Estado;

    public Cita(int Id, String DocumentoPaciente, int IdMedico, LocalDateTime FechaHora, EstadodeCita Estado) {
        this.Id = Id;
        this.DocumentoPaciente = DocumentoPaciente;
        this.IdMedico = IdMedico;
        this.FechaHora = FechaHora;
        this.Estado = Estado;
    }

    public int getId() {
        return Id;
    }

    public String getDocumentoPaciente() {
        return DocumentoPaciente;
    }

    public int getIdMedico() {
        return IdMedico;
    }

    public LocalDateTime getFechaHora() {
        return FechaHora;
    }

    public EstadodeCita getEstado() {
        return Estado;
    }

    public void setFechaHora(LocalDateTime FechaHora) {
        this.FechaHora = FechaHora;
    }

    public void setEstado(EstadodeCita Estado) {
        this.Estado = Estado;
    }
}
