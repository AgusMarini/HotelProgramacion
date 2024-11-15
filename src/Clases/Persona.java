package Clases;

import Interfaces.Jsonable;
import org.json.JSONObject;

public abstract class Persona implements Jsonable {
    private String nombre;
    private String apellido;
    private int dni; // lo usamos como identificador unico

    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }
    @Override
    public String toString() {
        return "Persona\n" +
                "Dni:'" + dni + '\'' +
                "Nombre:'" + nombre + '\'' +
                "Apellido:'" + apellido + '\'';
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dni", dni);
        jsonObject.put("nombre", nombre);
        jsonObject.put("apellido", getApellido());
        return jsonObject;
    }
}
