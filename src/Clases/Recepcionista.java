package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoUsuario;
import Interfaces.Autenticable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Recepcionista extends Usuario implements Autenticable {

    private int horarioTrabajo;

    public Recepcionista(String contrasena, String nombreUsuario, int horarioTrabajo) {
        super(TipoUsuario.RECEPECIONISTA, contrasena, nombreUsuario);
        this.horarioTrabajo = horarioTrabajo;
    }
    public Recepcionista(String contrasena, String nombreUsuario) {
        super(TipoUsuario.RECEPECIONISTA, contrasena, nombreUsuario);
    }

    public int getHorarioTrabajo() {
        return horarioTrabajo;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return this.getNombre().equals(usuario) && this.getContrasena().equals(contrasena);
    }

    @Override
    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombreUsuario", getNombre());
        jsonObject.put("contrasena", getContrasena());
        jsonObject.put("horarioTrabajo", horarioTrabajo);
        return jsonObject;
    }
    @Override
    public String toString() {
        return "\nRecepcionista{" +
                "  nombreUsuario = " + getNombre() +
                ", horarioTrabajo=" + horarioTrabajo +
                '}';
    }

    // Método fromJson para deserializar un objeto Recepcionista desde un JSONObject
    public static Recepcionista fromJson(JSONObject json) {
        String nombreUsuario = json.getString("nombreUsuario");
        String contrasena = json.getString("contrasena");
        int horarioTrabajo = json.getInt("horarioTrabajo");

        return new Recepcionista(contrasena,nombreUsuario, horarioTrabajo);
    }

    /*
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


    public void realizarCheckIn(String dniPasajero) {
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
            System.out.println("Clases.Pasajero no encontrado.");
        }
    }


    public void realizarCheckOut(int numeroHabitacion) {
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
*/
}
