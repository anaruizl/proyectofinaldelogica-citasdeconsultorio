package com.example.consultoriodecitas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.kotlin.com.intellij.openapi.components.Service;

import com.example.consultoriodecitas.data.AlmacenArchivos;
import com.example.consultoriodecitas.model.Cita;
import com.example.consultoriodecitas.model.Medico;
import com.example.consultoriodecitas.model.Paciente;

@Service
public class consultorioServicios {

    private final AlmacenArchivos almacen = new AlmacenArchivos();
    private final List<Paciente> pacientes;
    private final List<Medico> medicos;
    private final List<Cita> citas;

    public consultorioServicios() {
        if (almacen.ElprimerArranque()) {
            List<Medico> iniciales = new ArrayList<>();
            iniciales.add(new Medico(1, "Dra. Sandra Gallego", "Medicina General"));
            iniciales.add(new Medico(2, "Dra. Monica Zapata", "Pediatria"));
            iniciales.add(new Medico(3, "Dr. Yeferson Davila", "Odontología"));
            iniciales.add(new Medico(4, "Dr. Raul Humberto Escobar", "Ortopedia"));
            iniciales.add(new Medico(5, "Dra. Laura Gomez", "Ginecología"));
            iniciales.add(new Medico(6, "Dr. Carlos Rios", "Hematologia"));
            iniciales.add(new Medico(7, "Dra. Sofia Marin", "Dermatologia"));
            iniciales.add(new Medico(8, "Dr. Fabian Carmona", "Cardiología"));
            iniciales.add(new Medico(9, "Dra. Camila Tabares", "Gatroenteología"));

            almacen.GuardarMedicos(iniciales);

        }
        pacientes = almacen.leerPacientes();
        medicos = almacen.leerMedicos();
        citas = almacen.leerCitas();
    }

    public List<Paciente> BuscarPaciente(String Documento) {
        return new ArrayList<>(pacientes);
    }

    public Optional<Paciente> BuscarPaciente(String Documento) {
        return pacientes.stream().filter(p -> p.getDocumento().equals(Documento)).findFirst();

    }
    public void RegistrarPaciente(String Documento, String Nombre, String Telefono, LocalDate Nacimiento ) {
        validarTexto(Documento, "El documento");
        validarTexto(Nombre, "El nombre");
        validarTexto(Telefono, "El telefono");
        if (Nacimiento == null || Nacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no es valida.");

            
        }
        if (BuscarPaciente(Documento.trim()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un paciente con ese documento.");

            
        }
        pacientes.add(new Paciente(Documento.trim(), Nombre.trim(), Telefono.trim(), Nacimiento));
        almacen.GuardarPacientes(pacientes);
    }
}
