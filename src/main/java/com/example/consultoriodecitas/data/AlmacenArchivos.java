package com.example.consultoriodecitas.data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.consultoriodecitas.model.Cita;
import com.example.consultoriodecitas.model.EstadoCita;
import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;

public class AlmacenArchivos {
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
        for (Medico m : medicos) {
            lineas.add(String.join(";", String.valueOf(m.getId()), m.getNombre(), m.getEspecialidad()));

        }
        escribir(MEDICOS, lineas);
    }

    public List<Cita> leerCitas() {
        List<Cita> lista = new ArrayList<>();
        for (String[] c : leerCampos(CITAS)) {
            lista.add(new Cita(Integer.parseInt(c[0]), c[1], Integer.parseInt(c[2]),
                    LocalDateTime.parse(c[3]), EstadoCita.valueOf(c[4])));
        }
        return lista;
    }

    public void guardarCitas(List<Cita> citas) {
        List<String> lineas = new ArrayList<>();
        for (Cita c : citas) {
            lineas.add(String.join(";", String.valueOf(c.getId()), c.getDocumentoPaciente(),
                    String.valueOf(c.getIdMedico()), c.getFechaHora().toString(),
                    c.getEstado().name()));
        }
        escribir(CITAS, lineas);
    }

    private List<String[]> leerCampos(Path archivo) {
        List<String[]> resultado = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return resultado;

        }
        try {
            for (String linea : Files.readAllLines(archivo)) {
                if (!linea.isBlank()) {
                    resultado.add(linea.split(";", -1));

                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + archivo, e);
        }
        return resultado;
    }

    private void escribir(Path archivo, List<String> lineas) {
        try {
            Files.createDirectories(CARPETA);
            Files.write(archivo, lineas);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir " + archivo, e);
        }
    }
}
