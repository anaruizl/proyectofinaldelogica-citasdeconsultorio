package com.example.consultoriodecitas.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class InicioView extends VerticalLayout{
    
    public InicioView() {

        add(new H1("CONSULTORIO DE CITAS"));
    }
}
