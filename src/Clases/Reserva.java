
package Clases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONObject;

public class Reserva implements Jsonable {
    private int dniPasajero;
    private int numeroHabitacion;
    private TipoHabitacion tipoHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double precioTotal;

    public Reserva( int dniPasajero, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal) {

        this.dniPasajero = dniPasajero;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
    }
    // Constructor auxiliar sin precioTotal (se calcula después)
    public Reserva(int dniPasajero, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.dniPasajero = dniPasajero;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public double calcularPrecioTotal(TipoHabitacion tipoHabitacion) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return dias * tipoHabitacion.getPrecioPorDia();
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getDniPasajero() {
        return dniPasajero;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }


    public LocalDate getFechaFin() {
        return fechaFin;
    }

    // Método para verificar solapamiento de fechas
    public boolean seSolapaCon(LocalDate inicio, LocalDate fin) {
        return !(fin.isBefore(fechaInicio) || inicio.isAfter(fechaFin));
    }

    // Método fromJson para deserializar una reserva
    public static Reserva fromJson(JSONObject json, Hotel hotel) {
        int dniPasajero = json.getInt("dniPasajero");
        int numeroHabitacion = json.getInt("numeroHabitacion");
        LocalDate fechaInicio = LocalDate.parse(json.getString("fechaInicio"), DateTimeFormatter.ISO_DATE);
        LocalDate fechaFin = LocalDate.parse(json.getString("fechaFin"), DateTimeFormatter.ISO_DATE);

        // Buscar tipo de habitación en el hotel
        Habitacion habitacion = hotel.buscarHabitacionPorNumero(numeroHabitacion);

        TipoHabitacion tipoHabitacion = habitacion.getTipo();

        // Calcular precio total
        double precioTotal = ChronoUnit.DAYS.between(fechaInicio, fechaFin) * tipoHabitacion.getPrecioPorDia();

        return new Reserva(dniPasajero, numeroHabitacion, fechaInicio, fechaFin, precioTotal);
    }

    @Override
    public String toString() {
        return "\nReserva:\n" +
                "idPasajero:" + dniPasajero + '\n' +
                "FechaInicio:" + fechaInicio + '\n' +
                "FechaFin:" + fechaFin + '\n' +
                "NumeroHabitacion:" + numeroHabitacion + '\n' +
                ", precioTotal=" + precioTotal + '}';
    }
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("idPasajero", dniPasajero);
        json.put("fechaInicio", fechaInicio.toString());
        json.put("fechaFin", fechaFin.toString());
        json.put("numeroHabitacion", numeroHabitacion);
        json.put("precioTotal", precioTotal);
        return json;
    }
}
