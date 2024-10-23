import java.util.List;

public class Reserva {
    private Pasajero pasajero;
    private TipoHabitacion tipoHabitacion;
    private List<String> serviciosAdicionales;
    private int cantidadPasajeros;
    private int numeroHabitacion;


    public Reserva(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales, int numeroHabitacion) {
        this.pasajero = pasajero;
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadPasajeros = cantidadPasajeros;
        this.serviciosAdicionales = serviciosAdicionales;
        this.numeroHabitacion = numeroHabitacion; // Almacenar el número de la habitación
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
}

