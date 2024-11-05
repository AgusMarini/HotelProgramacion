import java.util.ArrayList;

public class Recepcionista extends Usuario{
    private ArrayList<Habitacion> habitaciones;

    public Recepcionista(String nombre, String dni) {
        super(nombre, dni);
        this.habitaciones=new ArrayList<>();
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public void controlarDisponibilidad() {
        System.out.println("Habitaciones disponibles:");
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                System.out.println("Habitación " + habitacion.getNumero());
            }
        }
    }
    public static void realizarCheckIn(String dniPasajero) {
        Pasajero pasajero = Hotel.buscarPasajeroPorDni(dniPasajero);
        if (pasajero != null) {
            Reserva reserva = Hotel.buscarReservaPorDni(dniPasajero);
            if (reserva != null) {
                Habitacion habitacionDisponible = Hotel.buscarHabitacionDisponiblePorTipo(reserva.getTipoHabitacion());
                if (habitacionDisponible != null) {
                    habitacionDisponible.realizarCheckIn(pasajero);
                    System.out.println("Check-In realizado en la habitación " + habitacionDisponible.getNumero());
                } else {
                    System.out.println("No hay habitaciones disponibles del tipo reservado.");
                }
            } else {
                System.out.println("El pasajero no tiene una reserva.");
            }
        } else {
            System.out.println("Pasajero no encontrado.");
        }
    }

    public void realizarCheckOut(int numeroHabitacion) {
        Habitacion habitacion = Hotel.buscarHabitacionPorNumero(numeroHabitacion);
        if (habitacion != null && habitacion.realizarCheckOut()) {
            System.out.println("Check-Out realizado en la habitación " + habitacion.getNumero());
        } else {
            System.out.println("Error: La habitación no está ocupada o no se encontró.");
        }
    }

    public void listarHabitacionesOcupadas() {
        System.out.println("Habitaciones Ocupadas:");
        for (Habitacion habitacion : Hotel.listarHabitacionesNoDisponibles()) {
            if (habitacion.getEstado() == EstadoHabitacion.OCUPADA) {
                System.out.println("Habitación " + habitacion.getNumero() + " - Tipo: " + habitacion.getTipo());
            }
        }
    }
    @Override
    public void mostrarInfo() {
        System.out.println("Recepcionista: " + getNombre());
    }
}
