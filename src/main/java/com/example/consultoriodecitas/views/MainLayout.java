package com.example.consultoriodecitas.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin.Minus.Vertical;

public class MainLayout extends AppLayout {
    public MainLayout() {
        H1 titulo = new H1("CONSULTORIO DE CITAS");
        titulo.addClassName("titulo-app");
        addToNavbar(new DrawerToggle(), titulo);

        VerticalLayout menu = new VerticalLayout(
            new RouterLink("Inicio", InicioView.class), new RouterLink("Pacientes", PacientesView.class));
            menu.addClassName("menu-lateral");
            addToDrawer(menu);
        
    }
}
