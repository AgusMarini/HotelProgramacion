package Clases;

import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Persona{

    private String origen;
    private String domicilio;
    private List<Reserva> historialReservas; // Lista de reservas asociadas al pasajero

    public Pasajero(String nombre, String apellido, int dni, String origen, String domicilio) {
        super(nombre, apellido, dni);
        this.origen = origen;
        this.domicilio = domicilio;
        this.historialReservas = new ArrayList<>();
    }

    public String getOrigen() {
        return origen;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public List<Reserva> getHistorialReservas() {
        return new ArrayList<>(historialReservas); // Devuelve una copia para evitar modificaciones externas
    }

    public void agregarReservaAlHistorial(Reserva reserva) {
        historialReservas.add(reserva);
    }

    public void eliminarReservaDelHistorial(Reserva reserva) {
        historialReservas.remove(reserva);
    }

    public StringBuilder historiaReservasToString(){
        StringBuilder sb = new StringBuilder();
        for (Reserva reserva : historialReservas){
            sb.append(reserva.toString());
        }
        return sb;
    }

    public String toString(){
        return "Pasajero: \n" +
                "  Dni: " + getDni() +
                "  Nombre: " + getNombre() +
                "  Apellido: " + getApellido() +
                "  Origen: " + origen +
                "  Domicilio: " + domicilio;
    }

    // Sobrescribe el método toJson para incluir los atributos de Clases.Usuario y Clases.Pasajero
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson(); // Obtiene los atributos de Clases.Usuario
        jsonObject.put("origen", origen);
        jsonObject.put("domicilio", domicilio);
        return jsonObject;
    }
/*
    // Método estático para crear un Clases.Pasajero desde un JSON
    public static Pasajero fromJson(JSONObject jsonObject) {
        // Extrae los atributos comunes de Clases.Usuario
        String nombre = jsonObject.getString("nombre");
        String dni = jsonObject.getString("dni");

        // Extrae los atributos específicos de Clases.Pasajero
        String origen = jsonObject.getString("origen");
        String domicilio = jsonObject.getString("domicilio");

        // Crea y retorna una instancia de Clases.Pasajero con los datos completos
        return new Pasajero(nombre, dni, origen, domicilio);
    }
 */
}
