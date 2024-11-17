package Clases;

import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONArray;
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
        return "\nPasajero: \n" +
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return getDni() == pasajero.getDni();
    }
    public static Pasajero fromJson(JSONObject json) {
        String nombre = json.getString("nombre");
        String apellido = json.getString("apellido");
        int dni = json.getInt("dni");
        String origen = json.getString("origen");
        String domicilio = json.getString("domicilio");

        // Crear instancia del pasajero
        Pasajero pasajero = new Pasajero(nombre, apellido, dni, origen, domicilio);

        // Cargar historial de reservas
        JSONArray reservasJson = json.optJSONArray("historialReservas");
        if (reservasJson != null) {
            List<Reserva> historialReservas = new ArrayList<>();
            for (int i = 0; i < reservasJson.length(); i++) {
                historialReservas.add(Reserva.fromJson(reservasJson.getJSONObject(i)));
            }
            pasajero.setHistorialReservas(historialReservas);
        }
        return pasajero;
    }

    public void setHistorialReservas(List<Reserva> historialReservas) {
        this.historialReservas = historialReservas;
    }



}
