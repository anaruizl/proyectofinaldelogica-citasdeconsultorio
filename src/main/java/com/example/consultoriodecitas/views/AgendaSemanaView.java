package com.example.consultoriodecitas.views;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import com.example.consultoriodecitas.model.Cita;
import com.example.consultoriodecitas.model.EstadoCita;
import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;
import com.example.consultoriodecitas.service.consultorioServicios;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "agenda", layout = MainLayout.class)
@PageTitle("Agenda semanal")
public class AgendaSemanaView extends VerticalLayout {

    private static final LocalTime INICIO = LocalTime.of(8, 0);
    private static final LocalTime FIN = LocalTime.of(18, 0);
    private static final String[] DIAS = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" };
    private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM");
    private static final DateTimeFormatter FORMATO_COMPLETO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final consultorioServicios servicio;
    private final ComboBox<Medico> medico = new ComboBox<>("Médico");
    private final Span rangoSemana = new Span();
    private final VerticalLayout matriz = new VerticalLayout();
    private LocalDate lunes;

    public AgendaSemanaView(consultorioServicios servicio) {
        this.servicio = servicio;

        LocalDate hoy = LocalDate.now();
        lunes = hoy.getDayOfWeek() == DayOfWeek.SUNDAY
                ? hoy.plusDays(1)
                : hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        medico.setItems(servicio.listarMedicos());
        medico.setPlaceholder("Selecciona un médico");
        medico.setWidth("320px");
        medico.addValueChangeListener(e -> construirMatriz());

        Button anterior = new Button("← Semana anterior", e -> {
            lunes = lunes.minusWeeks(1);
            construirMatriz();
        });
        Button siguiente = new Button("Semana siguiente →", e -> {
            lunes = lunes.plusWeeks(1);
            construirMatriz();
        });
        rangoSemana.addClassName("agenda-rango");

        HorizontalLayout barra = new HorizontalLayout(medico, anterior, rangoSemana, siguiente);
        barra.setAlignItems(FlexComponent.Alignment.END);
        barra.setWrap(true);

        matriz.setSpacing(false);
        matriz.setPadding(false);
        Div contenedor = new Div(matriz);
        contenedor.addClassName("agenda-scroll");

        add(new H2("Agenda semanal"), barra, contenedor);
        construirMatriz();
    }

    private void construirMatriz() {
        matriz.removeAll();
        rangoSemana.setText(lunes.format(FORMATO_DIA) + " al " + lunes.plusDays(5).format(FORMATO_DIA));

        Medico elegido = medico.getValue();
        if (elegido == null) {
            matriz.add(new Span("Selecciona un médico para ver su agenda."));
            return;
        }

        List<Cita> citas = servicio.listarCitas().stream()
                .filter(c -> c.getIdMedico() == elegido.getId()
                        && c.getEstado() == EstadoCita.PROGRAMADA)
                .toList();

        HorizontalLayout encabezado = crearFila();
        encabezado.add(crearCelda("", "celda-hora", "celda-encabezado"));
        for (int d = 0; d < DIAS.length; d++) {
            String titulo = DIAS[d] + " " + lunes.plusDays(d).format(FORMATO_DIA);
            encabezado.add(crearCelda(titulo, "celda-encabezado"));
        }
        matriz.add(encabezado);

        for (LocalTime hora = INICIO; hora.isBefore(FIN); hora = hora.plusMinutes(30)) {
            HorizontalLayout fila = crearFila();
            fila.add(crearCelda(hora.toString(), "celda-hora", "celda-encabezado"));

            for (int d = 0; d < DIAS.length; d++) {
                LocalDateTime fechaHora = LocalDateTime.of(lunes.plusDays(d), hora);
                Optional<Cita> ocupada = citas.stream()
                        .filter(c -> c.getFechaHora().equals(fechaHora))
                        .findFirst();

                if (ocupada.isPresent()) {
                    String nombre = servicio.BuscarPaciente(ocupada.get().getDocumentoPaciente())
                            .map(Paciente::getNombre)
                            .orElse(ocupada.get().getDocumentoPaciente());
                    fila.add(crearCelda(nombre, "celda-ocupada"));
                } else if (fechaHora.isBefore(LocalDateTime.now())) {
                    fila.add(crearCelda("", "celda-pasada"));
                } else {
                    Div libre = crearCelda("Libre", "celda-libre");
                    libre.addClickListener(e -> abrirFormulario(fechaHora));
                    fila.add(libre);
                }
            }
            matriz.add(fila);
        }
    }

    private void abrirFormulario(LocalDateTime fechaHora) {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Nueva cita");

        ComboBox<Paciente> paciente = new ComboBox<>("Paciente");
        paciente.setItems(servicio.listarPacientes());
        paciente.setPlaceholder("Selecciona un paciente");

        TextField campoMedico = new TextField("Médico");
        campoMedico.setValue(medico.getValue().toString());
        campoMedico.setReadOnly(true);

        TextField campoFecha = new TextField("Fecha y hora");
        campoFecha.setValue(fechaHora.format(FORMATO_COMPLETO));
        campoFecha.setReadOnly(true);

        VerticalLayout contenido = new VerticalLayout(paciente, campoMedico, campoFecha);
        contenido.setPadding(false);
        dialogo.add(contenido);

        Button guardar = new Button("Agendar", e -> {
            try {
                if (paciente.getValue() == null) {
                    throw new IllegalArgumentException("Debes seleccionar un paciente.");
                }
                servicio.AgendarCita(paciente.getValue().getDocumento(),
                        medico.getValue().getId(), fechaHora);
                Notification.show("Cita agendada correctamente");
                dialogo.close();
                construirMatriz();
            } catch (IllegalArgumentException ex) {
                Notification n = Notification.show(ex.getMessage(), 4000, Notification.Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelar = new Button("Cancelar", e -> dialogo.close());
        dialogo.getFooter().add(cancelar, guardar);

        dialogo.open();
    }

    private HorizontalLayout crearFila() {
        HorizontalLayout fila = new HorizontalLayout();
        fila.setSpacing(false);
        fila.setPadding(false);
        return fila;
    }

    private Div crearCelda(String texto, String... clases) {
        Div celda = new Div(texto);
        celda.addClassName("celda");
        celda.addClassNames(clases);
        return celda;
    }
}