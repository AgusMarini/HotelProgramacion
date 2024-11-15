package Clases;

import Enums.TipoHabitacion;
import Interfaces.Jsonable;
import org.json.JSONObject;

public class Pasajero extends Persona{

    private String origen;
    private String domicilio;

    public Pasajero(String nombre, String apellido, int dni, String origen, String domicilio) {
        super(nombre, apellido, dni);
        this.origen = origen;
        this.domicilio = domicilio;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDomicilio() {
        return domicilio;
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
