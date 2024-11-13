import java.time.LocalDate;
import java.util.List;

public class Reserva {
    private Pasajero pasajero;
    private TipoHabitacion tipoHabitacion;
    private List<String> serviciosAdicionales;
    private int cantidadPasajeros;
    private int numeroHabitacion;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;

    public Reserva(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales, int numeroHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida) {
        this.pasajero = pasajero;
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadPasajeros = cantidadPasajeros;
        this.serviciosAdicionales = serviciosAdicionales;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaIngreso=fechaIngreso;
        this.fechaSalida=fechaSalida;
    }

    public Reserva(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, int numeroHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida) {
        this.pasajero = pasajero;
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadPasajeros = cantidadPasajeros;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaIngreso=fechaIngreso;
        this.fechaSalida=fechaSalida;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }
    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public List<String> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
}

