package Clases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONObject;

public class Habitacion implements Jsonable {
    private int numero;
    private EstadoHabitacion estado;
    private TipoHabitacion tipo;
    private int dniOcupante;
    private String motivoNoDisponibilidad;

    public Habitacion(int numero, TipoHabitacion tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.motivoNoDisponibilidad = "";
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
    }
    public String getMotivoNoDisponibilidad() {
        return motivoNoDisponibilidad;
    }

    // Getter y Setter para dniOcupante
    public int getDniOcupante() {
        return dniOcupante;
    }

    public void setDniOcupante(int dniOcupante) {
        this.dniOcupante = dniOcupante;
    }

    public void cambiarEstadoHabitacion (EstadoHabitacion estadoAcambiar){
        if (estado == estadoAcambiar){
            System.out.println("La habitacion ya se encuentra en este estado...");
        } else {
            setEstado(estadoAcambiar);
        }
    }


    @Override
    public String toString(){
        return "Numero:" + numero +'\n'+
                "TipoHabitacion:" + tipo +'\n' +
                "EstadoHabitacion:" + estado;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return numero == that.numero;
    }

    // Método para convertir Clases.Habitacion a JSON
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero", numero);
        jsonObject.put("tipo", tipo);
        jsonObject.put("estado", estado);
        return jsonObject;
    }
}
