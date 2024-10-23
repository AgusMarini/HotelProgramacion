import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Habitacion implements Reservable, Checkable, Ocupable {
    private int numero;
    private EstadoHabitacion estado;
    private TipoHabitacion tipo;
    private Pasajero ocupante;
    private List<String> reservas;
    private String motivoNoDisponibilidad;
    private LocalDateTime ultimaModificacionEstado;

    public Habitacion(int numero, TipoHabitacion tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.motivoNoDisponibilidad = "";
        this.reservas = new ArrayList<>();
        this.ultimaModificacionEstado = LocalDateTime.now();
    }

    // Método para cambiar el estado a disponible si han pasado 60 segundos
    public boolean puedeCambiarADisponible() {
        if (estado == EstadoHabitacion.LIMPIEZA || estado == EstadoHabitacion.REPARACION || estado == EstadoHabitacion.DESINFECCION) {
            LocalDateTime ahora = LocalDateTime.now();
            return ultimaModificacionEstado.plusSeconds(60).isBefore(ahora);
        }
        return false;
    }

    @Override
    public boolean reservar(String fechaInicio, String fechaFin, String tipo) {
        if (estado == EstadoHabitacion.DISPONIBLE) {
            reservas.add(fechaInicio + " a " + fechaFin);
            estado = EstadoHabitacion.RESERVADA;
            ultimaModificacionEstado = LocalDateTime.now(); // Actualiza el tiempo de modificación
            return true;
        }
        return false;
    }

    // Métodos de Checkable
    @Override
    public boolean realizarCheckIn(Pasajero pasajero) {
        if (this.estado == EstadoHabitacion.RESERVADA || this.estado == EstadoHabitacion.DISPONIBLE) {
            this.ocupante = pasajero;
            this.estado = EstadoHabitacion.OCUPADA;
            ultimaModificacionEstado = LocalDateTime.now();
            return true;
        }
        return false;
    }



    @Override
    public boolean realizarCheckOut() {
        if (this.estado == EstadoHabitacion.OCUPADA) {
            this.ocupante = null;
            this.estado = EstadoHabitacion.LIMPIEZA; // O el estado que corresponda después del check-out
            ultimaModificacionEstado = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // Métodos de Reservable
    @Override
    public boolean cancelarReserva() {
        if (estado == EstadoHabitacion.RESERVADA) {
            reservas.clear();
            estado = EstadoHabitacion.DISPONIBLE;
            ultimaModificacionEstado = LocalDateTime.now();
            return true;
        }
        return false;
    }

    @Override
    public boolean estaDisponible(String fechaInicio, String fechaFin, String tipo, TipoHabitacion tipoHabitacion) {
        if (this.estado == EstadoHabitacion.DISPONIBLE && this.tipo == tipoHabitacion) {
            return true;
        }
        return false;
    }

    // Método de Ocupable
    @Override
    public boolean puedeOcupar() {
        return this.estado == EstadoHabitacion.DISPONIBLE;
    }

    // Getters y Setters
    @Override
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
    public boolean reservar(Pasajero pasajero, String fechaInicio, String fechaFin, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales) {
        return false;
    }

    @Override
    public boolean cancelarReserva(Pasajero pasajero) {
        return false;
    }

    @Override
    public boolean estaDisponible(String fechaInicio, String fechaFin, TipoHabitacion tipoHabitacion) {
        return false;
    }
}
