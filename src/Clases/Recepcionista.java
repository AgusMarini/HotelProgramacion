package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoUsuario;
import Interfaces.Autenticable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Recepcionista extends Usuario implements Autenticable {

    private int horarioTrabajo;

    public Recepcionista(String contrasena, String nombreUsuario, int horarioTrabajo) {
        super(TipoUsuario.RECEPECIONISTA, contrasena, nombreUsuario);
        this.horarioTrabajo = horarioTrabajo;
    }
    public Recepcionista(String contrasena, String nombreUsuario) {
        super(TipoUsuario.RECEPECIONISTA, contrasena, nombreUsuario);
    }

    public int getHorarioTrabajo() {
        return horarioTrabajo;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return this.getNombreUsuario().equals(usuario) && this.getContrasena().equals(contrasena);
    }

    @Override
    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombreUsuario", getNombreUsuario());
        jsonObject.put("contrasena", getContrasena());
        jsonObject.put("horarioTrabajo", horarioTrabajo);
        return jsonObject;
    }
    @Override
    public String toString() {
        return "\nRecepcionista {" +
                "  \nnombreUsuario = " + getNombreUsuario() +
                ", \nhorarioTrabajo=" + horarioTrabajo +
                '}';
    }

    // Método fromJson para deserializar un objeto Recepcionista desde un JSONObject
    public static Recepcionista fromJson(JSONObject json) {
        String nombreUsuario = json.getString("nombreUsuario");
        String contrasena = json.getString("contrasena");
        int horarioTrabajo = json.getInt("horarioTrabajo");

        return new Recepcionista(contrasena,nombreUsuario, horarioTrabajo);
    }

}
