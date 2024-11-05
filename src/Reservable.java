import java.util.List;

public interface Reservable {
    boolean reservar(String fechaInicio, String fechaFin, String tipo) throws HabitacionNoDisponibleException;

    boolean cancelarReserva();

    boolean estaDisponible(String fechaInicio, String fechaFin, String tipo, TipoHabitacion tipoHabitacion);



}
