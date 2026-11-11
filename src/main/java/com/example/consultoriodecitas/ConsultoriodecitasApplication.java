package com.example.consultoriodecitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringBootApplication
@StyleSheet(Lumo.STYLESHEET)
@StyleSheet("styles.css")
public class ConsultoriodecitasApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(ConsultoriodecitasApplication.class, args);
    }
}