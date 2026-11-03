package com.example.consultoriodecitas.views;

import java.time.Duration;
import java.time.LocalDateTime;

import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;
import com.example.consultoriodecitas.service.consultorioServicios;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route (value = "agendar", layout = MainLayout.class)
@PageTitle ("Agendar cita")
public class AgendarcitasView extends VerticalLayout {
    
    private final consultorioServicios servicio;

    private final ComboBox<Paciente> Paciente = new ComboBox<>("Paciente");
    private final ComboBox<Medico> Medico = new ComboBox<>("Médico");
    private final DateTimePicker FechaHora = new DateTimePicker("Fecha y Hora");

    public  AgendarcitasView(consultorioServicios servicio) {
        this.servicio = servicio;

        Paciente.setItems(servicio.listarPacientes());
        Paciente.setPlaceholder("Selecciona un paciente");
        Medico.setItems(servicio.listarMedicos());
        Medico.setPlaceholder("Selecciona un médico");

        FechaHora.setMin(LocalDateTime.now());
        FechaHora.setStep(Duration.ofMinutes(30));

        FormLayout Formulario = new FormLayout(Paciente, Medico, FechaHora);
        Formulario.addClassName("Tarjeta");

        Button Agendar = new Button("Agendar Cita", e -> AgendarCita());
        Agendar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Agendar.addClassName("boton-guardar");

        add(new H2("Agendar Cita"), Formulario, Agendar);
    }

    private void AgendarCita() {
        try {
            if (Paciente.getValue() == null || Medico.getValue() == null) {
                throw new IllegalArgumentException("Debes seleccionar un paciente y un médico.");
                
            }
            servicio.AgendarCita(Paciente.getValue().getDocumento(), Medico.getValue().getId(), FechaHora.getValue());
            Notification.show("Cita agendada correctamente");
            Paciente.clear();
            Medico.clear();
            FechaHora.clear();

        } catch (IllegalArgumentException ex) {
            Notification n = Notification.show(ex.getMessage(), 4000, Notification.Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
