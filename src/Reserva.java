import java.time.LocalDate;
import java.util.List;

public class Reserva {
    private Pasajero pasajero;
    private TipoHabitacion tipoHabitacion;
    private List<String> serviciosAdicionales;
    private int cantidadPasajeros;
    private int numeroHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;



    public Reserva(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.pasajero = pasajero;
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadPasajeros = cantidadPasajeros;
        this.serviciosAdicionales = serviciosAdicionales;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaInicio = this.fechaInicio;
        this.fechaFin = this.fechaFin;
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

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public List<String> getServiciosAdicionales() {
        return serviciosAdicionales;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    @Override
    public String toString() {
        return "Reserva{" +
                "pasajero=" + pasajero +
                ", tipoHabitacion=" + tipoHabitacion +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }

}

