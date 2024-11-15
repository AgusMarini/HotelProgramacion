package Clases;

import java.time.LocalDate;
import java.util.List;

import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONObject;

public class Reserva implements Jsonable {
    private int dniPasajero;
    private int numeroHabitacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Reserva( int dniPasajero, int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {

        this.dniPasajero = dniPasajero;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    @Override
    public String toString() {
        return "Reserva:\n" +
                "idPasajero:" + dniPasajero + '\n' +
                "FechaInicio:" + fechaInicio + '\n' +
                "FechaFin:" + fechaFin + '\n' +
                "NumeroHabitacion:" + numeroHabitacion + '\n' ;
    }
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("idPasajero", dniPasajero);
        json.put("fechaInicio", fechaInicio.toString());
        json.put("fechaFin", fechaFin.toString());
        json.put("numeroHabitacion", numeroHabitacion);

        return json;
    }
}

