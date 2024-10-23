import java.util.List;

public interface Reservable {
    boolean reservar(String fechaInicio, String fechaFin, String tipo);

    // Métodos de Reservable
    boolean cancelarReserva();

    boolean estaDisponible(String fechaInicio, String fechaFin, String tipo, TipoHabitacion tipoHabitacion);

    boolean reservar(Pasajero pasajero, String fechaInicio, String fechaFin, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales);
    boolean cancelarReserva(Pasajero pasajero);
    boolean estaDisponible(String fechaInicio, String fechaFin, TipoHabitacion tipoHabitacion);
}
