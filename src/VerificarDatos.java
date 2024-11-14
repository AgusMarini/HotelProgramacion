import Exceptions.FechaInvalidaException;
import Exceptions.HabitacionNoDisponibleException;
import Exceptions.NullNameException;

import java.time.LocalDate;

public class VerificarDatos {
    public static void verificarNombre(String nombre) throws NullNameException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NullNameException("El nombre no puede ser nulo o vacío.");
        }
    }
    public void verificarFechas(LocalDate fechaEntrada, LocalDate fechaSalida) throws FechaInvalidaException {
        if (fechaSalida.isBefore(fechaEntrada)) {
            throw new FechaInvalidaException("La fecha de salida no puede ser anterior a la fecha de entrada.");
        }
    }

    public static void verificarHabitacionDisponible(Habitacion habitacion) throws HabitacionNoDisponibleException {
        if (!habitacion.estaDisponible()) {
            throw new HabitacionNoDisponibleException("La habitación no está disponible para la reserva.");
        }
    }
}
