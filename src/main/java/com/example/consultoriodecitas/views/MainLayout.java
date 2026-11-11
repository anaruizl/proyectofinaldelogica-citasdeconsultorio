package com.example.consultoriodecitas.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        Image logo = new Image("images/logo.svg", "Logo del consultorio");
        logo.setHeight("32px");

        Span titulo = new Span("Consultorio de citas");
        titulo.addClassName("titulo-app");
        addToNavbar(new DrawerToggle(), logo, titulo);

        VerticalLayout menu = new VerticalLayout(
                new RouterLink("Inicio", InicioView.class),
                new RouterLink("Pacientes", PacientesView.class),
                new RouterLink("Agendar cita", AgendarcitasView.class),
                new RouterLink("Citas", CitasView.class),
                new RouterLink("Agenda semanal", AgendaSemanalView.class));
        menu.addClassName("menu-lateral");
        addToDrawer(menu);
    }
}