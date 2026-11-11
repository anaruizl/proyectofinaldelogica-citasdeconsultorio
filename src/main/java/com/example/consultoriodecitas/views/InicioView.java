package com.example.consultoriodecitas.views;

import com.example.consultoriodecitas.model.EstadoCita;
import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.service.consultorioServicios;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Inicio")
public class InicioView extends VerticalLayout {

    private final consultorioServicios servicio;

    public InicioView(consultorioServicios servicio) {
        this.servicio = servicio;
        addClassName("pagina");
        setSpacing(false);
        add(crearPortada(), crearSobreElProyecto(), crearPasos(), crearNumeros(), crearMedicos());
    }

    // ---------- Portada con imagen ----------

    private Div crearPortada() {
        H1 titulo = new H1("Consultorio de citas");
        titulo.addClassName("portada-titulo");

        RouterLink agendar = new RouterLink("Agendar una cita", AgendarcitasView.class);
        agendar.addClassNames("boton", "boton-principal");
        RouterLink agenda = new RouterLink("Ver agenda semanal", AgendaSemanalView.class);
        agenda.addClassNames("boton", "boton-suave");

        Div botones = new Div(agendar, agenda);
        botones.addClassName("portada-botones");

        Div textos = new Div(titulo,
                texto("Un lugar sencillo para registrar pacientes, agendar citas y ver la agenda semanal de cada médico.",
                        "portada-texto"),
                botones);

        Div imagen = new Div();
        imagen.addClassName("portada-imagen");

        Div portada = new Div(textos, imagen);
        portada.addClassName("portada");
        return portada;
    }

    

    private Component crearSobreElProyecto() {
        Div info = new Div(
                crearInfo("¿Qué es?",
                        "Una aplicación web para llevar el control de los pacientes y de las citas con cada médico, "
                                + "en un solo lugar y sin papeles."),
                crearInfo("¿Para qué se creó?",
                        "Es el proyecto final de Lógica de Programación. Su fin es aplicar lo aprendido "
                                + "(validaciones, listas, archivos y estructuras de control) a un caso real: "
                                + "evitar cruces de horario y conservar el historial de citas."));
        info.addClassName("info");
        return seccion("Sobre el proyecto", info);
    }

    private Div crearInfo(String titulo, String descripcion) {
        Div tarjeta = new Div(texto(titulo, "info-titulo"), texto(descripcion, "info-texto"));
        tarjeta.addClassName("info-tarjeta");
        return tarjeta;
    }

    

    private Component crearPasos() {
        Div pasos = new Div(
                crearPaso("1", "Registra al paciente",
                        "Guarda su documento, nombre, teléfono y fecha de nacimiento."),
                crearPaso("2", "Elige médico y hora",
                        "Selecciona un horario libre, en franjas de 30 minutos."),
                crearPaso("3", "Consulta o cancela",
                        "Revisa el estado de las citas y libera el horario si hace falta."));
        pasos.addClassName("pasos");
        return seccion("Cómo funciona", pasos);
    }

    private Div crearPaso(String numero, String titulo, String descripcion) {
        Div textos = new Div(texto(titulo, "paso-titulo"), texto(descripcion, "paso-texto"));
        Div paso = new Div(texto(numero, "paso-numero"), textos);
        paso.addClassName("paso");
        return paso;
    }

    

    private Component crearNumeros() {
        long programadas = servicio.listarCitas().stream()
                .filter(c -> c.getEstado() == EstadoCita.PROGRAMADA).count();

        Div numeros = new Div(
                crearNumero("Pacientes", servicio.listarPacientes().size()),
                crearNumero("Médicos", servicio.listarMedicos().size()),
                crearNumero("Citas programadas", programadas));
        numeros.addClassName("numeros");
        return seccion("El consultorio en números", numeros);
    }

    private Div crearNumero(String etiqueta, long valor) {
        Div tarjeta = new Div(texto(String.valueOf(valor), "numero-valor"), texto(etiqueta, "numero-etiqueta"));
        tarjeta.addClassName("numero-tarjeta");
        return tarjeta;
    }

    

    private Component crearMedicos() {
        Div lista = new Div();
        lista.addClassName("medicos");
        for (Medico m : servicio.listarMedicos()) {
            Div tarjeta = new Div(
                    texto(iniciales(m.getNombre()), "medico-avatar"),
                    new Div(texto(m.getNombre(), "medico-nombre"),
                            texto(m.getEspecialidad(), "medico-especialidad")));
            tarjeta.addClassName("medico");
            lista.add(tarjeta);
        }
        return seccion("Nuestro equipo médico", lista);
    }

    

    private VerticalLayout seccion(String titulo, Component contenido) {
        VerticalLayout seccion = new VerticalLayout(new H2(titulo), contenido);
        seccion.setPadding(false);
        seccion.setSpacing(false);
        seccion.addClassName("seccion");
        return seccion;
    }

    private Div texto(String contenido, String clase) {
        Div div = new Div(contenido);
        div.addClassName(clase);
        return div;
    }

    private String iniciales(String nombre) {
        StringBuilder resultado = new StringBuilder();
        for (String parte : nombre.trim().split("\\s+")) {
            if (parte.isEmpty() || parte.endsWith(".")) {
                continue; // salta "Dr." y "Dra."
            }
            resultado.append(Character.toUpperCase(parte.charAt(0)));
            if (resultado.length() == 2) {
                break;
            }
        }
        return resultado.toString();
    }
}