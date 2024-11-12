import java.util.ArrayList;
import java.util.Scanner;

public class Recepcionista extends Usuario {
    private ArrayList<Habitacion> habitaciones;

    public Recepcionista(String nombre, String dni) {
        super(nombre, dni);
        this.habitaciones = new ArrayList<>();
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

    // Método estático para realizar Check-Out
    public static void realizarCheckOut(int numeroHabitacion) {
        Scanner scanner = new Scanner(System.in);
        Habitacion habitacion = Hotel.buscarHabitacionPorNumero(numeroHabitacion);

        if (habitacion != null) {
            if (habitacion.realizarCheckOut()) {
                System.out.println("Check-Out realizado correctamente!");
                System.out.println("Seleccione el estado de la habitación después del Check-Out:");
                System.out.println("1. LIMPIEZA");
                System.out.println("2. DISPONIBLE");

                int opcionEstado = scanner.nextInt();
                scanner.nextLine();

                switch (opcionEstado) {
                    case 1:
                        habitacion.setEstado(EstadoHabitacion.LIMPIEZA);
                        System.out.println("La habitación se ha marcado como en LIMPIEZA.");
                        break;
                    case 2:
                        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                        System.out.println("La habitación se ha marcado como DISPONIBLE.");
                        break;
                    default:
                        System.out.println("Opción no válida. La habitación se marcará como DISPONIBLE por defecto.");
                        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                        break;
                }
            } else {
                System.out.println("La habitación no está ocupada o no se pudo realizar el Check-Out.");
            }
        } else {
            System.out.println("Habitación no encontrada.");
        }
    }



    @Override
    public void mostrarInfo() {
        System.out.println("Recepcionista: " + getNombre());
    }
}
