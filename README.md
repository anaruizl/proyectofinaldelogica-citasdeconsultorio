# Consultorio de citas



## Descripción del proyecto

Aplicación web para gestionar las citas de un consultorio médico. Permite registrar pacientes, agendar citas con un médico, cancelarlas y consultar la agenda semanal de cada médico. La información se guarda en archivos de texto, así que se conserva al cerrar y volver a abrir la aplicación.

## Reglas que controla la aplicación:

- No se puede registrar dos veces el mismo documento de paciente.
- Los campos no pueden estar vacíos ni contener el símbolo `;`.
- La fecha de nacimiento no puede ser futura.
- La fecha y hora de la cita deben ser futuras y en franjas de 30 minutos (por ejemplo 08:00 o 08:30).
- Un médico no puede tener dos citas programadas a la misma hora.
- Una cita cancelada libera el horario y no se puede cancelar de nuevo.

## Tecnologías usadas

- Java 21
- Spring Boot
- Vaadin 25.
- Maven (incluido mediante el wrapper `mvnw`)
- CSS propio, cargado con `@StyleSheet`
- Archivos de texto para guardar los datos

## Cómo ejecutarlo

1. Clonar el repositorio y entrar a la carpeta del proyecto.
2. Ejecutar en la terminal:
   - Windows: `.\mvnw.cmd spring-boot:run`.
3. Esperar el mensaje `Started ConsultoriodecitasApplication`. La primera vez puede tardar varios minutos.
4. Abrir en el navegador: (http://localhost:8080)

Para detener la aplicación, presionar `Ctrl + C` en la terminal.

## Datos:
Ana Maria Ruiz Lopez.
11 de noviembre/2026.

## Manual de uso

El menú lateral (ícono de las tres rayas) da acceso a todas las pantallas.

### 1. Inicio
Pantalla de bienvenida de la aplicación.

![Inicio](docs/capturas/inicio.png)

### 2. Registrar un paciente
1. Ir a **Pacientes**.
2. Llenar documento, nombre, teléfono y fecha de nacimiento.
3. Pulsar **Guardar**. El paciente aparece en la tabla inferior.

Si el documento ya existe o falta un campo, aparece un mensaje rojo explicando el error.

![Pacientes](docs/capturas/pacientes.png)

### 3. Agendar una cita
1. Ir a **Agendar cita**.
2. Elegir paciente, médico y una fecha y hora futuras (en punto o y media).
3. Pulsar **Agendar cita**.

Si el médico ya tiene una cita a esa hora, la aplicación la rechaza.

![Agendar cita](docs/capturas/agendar.png)

### 4. Ver y cancelar citas
1. Ir a **Citas** para ver todas las citas con su estado.
2. Pulsar **Cancelar** en la cita deseada. El estado cambia a CANCELADA y el botón se desactiva.

![Citas](docs/capturas/citas.png)

## Bonificación: agenda semanal en matriz

La pantalla **Agenda semanal** muestra la semana de un médico como una cuadrícula: columnas de lunes a sábado y una fila por cada franja de 30 minutos (de 8:00 a 18:00). Está construida con layouts de Vaadin, igual que el tablero del Buscaminas.

**Cómo probarla:**

1. Registrar al menos un paciente en **Pacientes**.
2. Ir a **Agenda semanal** y elegir un médico en la lista. Aparece la cuadrícula.
3. Hacer clic en una celda verde ("Libre"). Se abre un formulario con el médico, la fecha y la hora ya cargados.
4. Elegir un paciente y pulsar **Agendar**. La celda pasa a azul y muestra el nombre del paciente.
5. Usar **Semana anterior** y **Semana siguiente** para moverse entre semanas.
6. Para comprobar que se libera el horario: cancelar esa cita en **Citas**, volver a la agenda y verificar que la celda quedó libre otra vez.

Las celdas grises son horas que ya pasaron y no se pueden seleccionar.

![Agenda semanal](docs/capturas/agenda.png)

