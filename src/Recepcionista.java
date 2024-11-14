import Exceptions.AccesoNoAutorizadoException;
import Exceptions.HabitacionNoDisponibleException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;

public class Recepcionista extends Usuario implements Autenticable {
    private ArrayList<Habitacion> habitaciones;
    private String contrasena;

    public Recepcionista(String nombre, String dni, String contrasena) {
        super(nombre, dni,contrasena);
        this.habitaciones = new ArrayList<>();
        this.contrasena = contrasena;
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public void controlarDisponibilidad() {
        System.out.println("Habitaciones disponibles:");
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
            return;
        }
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                System.out.println("Habitación " + habitacion.getNumero());
            }
        }
    }

    public boolean realizarCheckIn(Habitacion habitacion, Pasajero pasajero) throws HabitacionNoDisponibleException {
        LocalDate hoy = LocalDate.now(); // Fecha actual

        // Buscar la reserva correspondiente al pasajero y la habitación
        for (Reserva reserva : Hotel.getListaReservas()) {
            if (reserva.getPasajero().equals(pasajero) && reserva.getNumeroHabitacion() == habitacion.getNumero()) {
                // Verificar si la fecha actual coincide con la fecha de inicio de la reserva
                if (hoy.isEqual(reserva.getFechaInicio())) {
                    // Realizar el check-in si la fecha es correcta
                    habitacion.realizarCheckIn(pasajero);
                    System.out.println("Check-In realizado con éxito para la habitación " + habitacion.getNumero());
                    return true;
                } else {
                    System.out.println("No se puede realizar el Check-In: La fecha actual no coincide con la fecha de inicio de la reserva (" + reserva.getFechaInicio() + ").");
                    return false;
                }
            }
        }

        throw new HabitacionNoDisponibleException("No se encontró una reserva válida para este pasajero y habitación.");
    }




    public boolean realizarCheckOut(Habitacion habitacion) throws HabitacionNoDisponibleException {
        if (habitacion.getEstado() != EstadoHabitacion.OCUPADA) {
            throw new HabitacionNoDisponibleException("La habitación " + habitacion.getNumero() + " no está ocupada y no se puede realizar el check-out.");
        }
        return habitacion.realizarCheckOut();
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    @Override
    public void verificarPermisos() throws AccesoNoAutorizadoException {
        throw new AccesoNoAutorizadoException("Acceso denegado: el recepcionista no tiene permisos suficientes.");
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Recepcionista: " + getNombre());
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return getNombre().equals(usuario) && this.contrasena.equals(contrasena);
    }

    @Override
    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
        System.out.println("Contraseña actualizada correctamente.");
    }

    @Override
    public String toString() {
        return "Recepcionista{" +
                "nombre='" + getNombre() + '\'' +
                ", dni='" + getDni() + '\'' +
                '}';
    }
}
