package com.example.consultoriodecitas.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;

public class AlmacendelosArchivos {
    private static final Path CARPETA = Path.of("datos");
    private static final Path PACIENTES = CARPETA.resolve("Pacientes.txt");
    private static final Path MEDICOS = CARPETA.resolve("Medicos.txt");
    private static final Path CITAS = CARPETA.resolve("Citas.txt");

    public boolean ElprimerArranque() {
        return !Files.exists(PACIENTES) && !Files.exists(MEDICOS) && !Files.exists(CITAS);
    }

    public List<Paciente> leerPacientes() {
        List<Paciente> lista = new ArrayList<>();
        for (String[] c : leerCampos(PACIENTES)) {
            lista.add(new Paciente(c[0], c[1], c[2], LocalDate.parse(c[3])));

        }
        return lista;
    }
    public void GuardarMedicos(List<Medico> medicos) {
        List<String> lineas = new ArrayList<>();
        for (Medico m : Medicos) {
            lineas.add(String.join(";", String.valueOf(m.getId()), m.getNombre()), m.getEspecialidad());

        }
        escribir(MEDICOS, lineas);
    }

    
}
