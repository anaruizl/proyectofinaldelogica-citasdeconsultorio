package com.example.consultoriodecitas.views;

import java.time.format.DateTimeFormatter;

import com.example.consultoriodecitas.model.Cita;
import com.example.consultoriodecitas.model.EstadoCita;
import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;
import com.example.consultoriodecitas.service.consultorioServicios;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route (value = "citas", layout = MainLayout.class)
@PageTitle ("Citas")
public class CitasView extends VerticalLayout{
    
    private static final DateTimeFormatter Formato = DateTimeFormatter.ofPattern("DD/MM/YYY   HH:MM");

    private final consultorioServicios servicio;
    private final Grid<Cita> Tabla = new Grid<>(Cita.class, false);

    public CitasView(consultorioServicios servicio) {
        this.servicio = servicio;

        Tabla.addColumn (Cita::getId).setHeader("N°").setAutoWidth(true);
        Tabla.addColumn(c -> servicio.BuscarPaciente(c.getDocumentoPaciente()).map(Paciente::getNombre).orElse(c.getDocumentoPaciente())).setHeader("Paciente");
        Tabla.addColumn(c -> servicio.BuscarMedico(c.getIdMedico()).map(Medico::toString).orElse("DESCONOCIDO")).setHeader("Medico");
        Tabla.addColumn(c -> c.getFechaHora().format(Formato)).setHeader("Fecha y Hora");
        Tabla.addColumn(c -> c.getEstado().name()).setHeader("Estado");
        Tabla.addComponentColumn(this::crearbotonCancelar).setHeader("Acciones");
        Tabla.addClassName("tabla-datos");

        add(new H2("Citas"), Tabla);
        actualizarTabla();

    }
    private Button crearbotonCancelar(Cita cita) {
        Button Cancelar = new Button("Cancelar", e -> CancelarCita(cita));
        Cancelar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        Cancelar.setEnabled(cita.getEstado() == EstadoCita.PROGRAMADA);
        return Cancelar;
    }

    private void CancelarCita(Cita cita) {
        try {
            servicio.CancelarCita(cita.getId());
            Notification.show("Cita cancelada");
            actualizarTabla();
        } catch (IllegalArgumentException ex) {
            Notification n = Notification.show(ex.getMessage(), 4000, Notification.Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
    private void actualizarTabla() {
        Tabla.setItems(servicio.listarCitas());
    }

}
