import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    private int numero;
    private EstadoHabitacion estado;
    private TipoHabitacion tipo;
    private Pasajero ocupante;
    private List<String> reservas;
    private String motivoNoDisponibilidad;
    private LocalDateTime ultimaModificacionEstado;
    private double costo;

    public Habitacion(int numero, TipoHabitacion tipo, double costo) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.motivoNoDisponibilidad = "";
        this.reservas = new ArrayList<>();
        this.ultimaModificacionEstado = LocalDateTime.now();
        this.costo = costo;
    }
    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    // Método para cambiar el estado a disponible si han pasado 60 segundos
    public boolean puedeCambiarADisponible() {
        if (estado == EstadoHabitacion.LIMPIEZA) {
            LocalDateTime ahora = LocalDateTime.now();
            return ultimaModificacionEstado.plusSeconds(60).isBefore(ahora);
        }
        return false;
    }

    public boolean realizarCheckIn(Pasajero pasajero) {
        if (this.estado == EstadoHabitacion.RESERVADA || this.estado == EstadoHabitacion.DISPONIBLE) {
            this.ocupante = pasajero;
            this.estado = EstadoHabitacion.OCUPADA;
            ultimaModificacionEstado = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public boolean realizarCheckOut() {
        if (this.estado == EstadoHabitacion.OCUPADA) {
            this.ocupante = null;
            this.estado = EstadoHabitacion.LIMPIEZA;
            ultimaModificacionEstado = LocalDateTime.now();
            return true;
        }
        return false;
    }
    private double calcularCostoPorTipo(TipoHabitacion tipo) {
        switch (tipo) {
            case SIMPLE:
                return 100.0;
            case DOBLE:
                return 150.0;
            case SUITE:
                return 200.0;
            default:
                return 0.0;
        }
    }
    public boolean estaDisponible() {
        return estado == EstadoHabitacion.DISPONIBLE;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
        ultimaModificacionEstado = LocalDateTime.now();
    }

    public void setMotivoNoDisponibilidad(String motivo) {
        this.motivoNoDisponibilidad = motivo;
    }

    public String getMotivoNoDisponibilidad() {
        return motivoNoDisponibilidad;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", tipo=" + tipo +
                ", estado=" + estado +
                '}';
    }

}
