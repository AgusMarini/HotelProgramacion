package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorReservas extends GestorColeccion<Reserva>{
    // Verificar si una habitación está disponible en un rango de fechas
    public boolean estaHabitacionDisponible(int numeroHabitacion, LocalDate inicio, LocalDate fin) {
        for (Reserva reserva : obtenerTodos()) {
            if (reserva.getNumeroHabitacion() == numeroHabitacion && reserva.seSolapaCon(inicio, fin)) {
                return false; // Si hay un solapamiento, la habitación no está disponible
            }
        }
        return true; // La habitación está disponible si no hay solapamientos
    }

    // Buscar reservas por DNI del pasajero
    public List<Reserva> buscarPorDni(int dniPasajero) {
        return obtenerTodos().stream()
                .filter(reserva -> reserva.getDniPasajero() == dniPasajero)
                .collect(Collectors.toList());
    }

    // Buscar reservas por número de la habitación
    public List<Reserva> buscarPorHabitacion(int numeroHabitacion) {
        return obtenerTodos().stream()
                .filter(reserva -> reserva.getNumeroHabitacion() == numeroHabitacion)
                .collect(Collectors.toList());
    }

    // Buscar reservas activas en una fecha específica
    public List<Reserva> buscarPorFecha(LocalDate fecha) {
        return obtenerTodos().stream()
                .filter(reserva -> !fecha.isBefore(reserva.getFechaInicio()) &&
                        !fecha.isAfter(reserva.getFechaFin()))
                .collect(Collectors.toList());
    }
    // Método para buscar una reserva específica por habitación, pasajero y fecha
    public Reserva buscarReserva(int numeroHabitacion, int dniPasajero, LocalDate fecha) {
        for (Reserva reserva : obtenerTodos()) {
            if (reserva.getNumeroHabitacion() == numeroHabitacion &&
                    reserva.getDniPasajero() == dniPasajero &&
                    !fecha.isBefore(reserva.getFechaInicio()) &&
                    !fecha.isAfter(reserva.getFechaFin())) {
                return reserva; // Se encontró una reserva válida
            }
        }
        return null; // No se encontró una reserva
    }

    // Eliminar una reserva específica por habitación y fechas
    public boolean eliminarReserva(int numeroHabitacion, LocalDate inicio, LocalDate fin) {
        return obtenerTodos().removeIf(reserva ->
                reserva.getNumeroHabitacion() == numeroHabitacion &&
                        reserva.getFechaInicio().equals(inicio) &&
                        reserva.getFechaFin().equals(fin));
    }



}
