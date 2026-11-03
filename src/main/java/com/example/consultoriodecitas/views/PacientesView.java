package com.example.consultoriodecitas.views;

import com.example.consultoriodecitas.model.Paciente;
import com.example.consultoriodecitas.service.consultorioServicios;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "pacientes", layout = MainLayout.class)
@PageTitle("Pacientes")
public class PacientesView extends VerticalLayout {

    private final consultorioServicios servicio;

    private final TextField Documento = new TextField("Documento");
    private final TextField Nombre = new TextField("Nombre");
    private final TextField Telefono = new TextField("Teléfono");
    private final DatePicker Nacimiento = new DatePicker("Fecha de Nacimiento");
    private final Grid<Paciente> Tabla = new Grid<>(Paciente.class, false);

    public PacientesView(consultorioServicios servicio) {
        this.servicio = servicio;

        H2 tituloForm = new H2("Registrar Paciente");
        Button guardar = new Button("Guardar", e -> GuardarPaciente());
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        guardar.addClassName("boton-guardar");

        FormLayout formulario = new FormLayout(Documento, Nombre, Telefono, Nacimiento);
        formulario.addClassName("Tarjeta");

        Tabla.addColumn(Paciente::getDocumento).setHeader("Documento");
        Tabla.addColumn(Paciente::getNombre).setHeader("Nombre");
        Tabla.addColumn(Paciente::getTelefono).setHeader("Teléfono");
        Tabla.addColumn(Paciente::getFechaDeNacimiento).setHeader("Nacimiento");
        Tabla.addClassName("tabla-datos");

        add(tituloForm, formulario, guardar, new H2("Pacientes Registrados"), Tabla);
        actualizarTabla();
    }

    private void GuardarPaciente() {
        try {
            servicio.RegistrarPaciente(Documento.getValue(), Nombre.getValue(), Telefono.getValue(),
                    Nacimiento.getValue());
            Notification.show("Paciente registrado correctamente");
            LimpiarFormulario();
            actualizarTabla();
        } catch (IllegalArgumentException ex) {
            Notification n = Notification.show(ex.getMessage(), 4000, Notification.Position.MIDDLE);
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);

        }
    }

    private void LimpiarFormulario() {
        Documento.clear();
        Nombre.clear();
        Telefono.clear();
        Nacimiento.clear();
    }

    private void actualizarTabla() {
        Tabla.setItems(servicio.listarPacientes());
    }

}
