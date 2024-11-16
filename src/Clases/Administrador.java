package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Enums.TipoUsuario;
import Interfaces.Autenticable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Administrador extends Usuario implements Autenticable {
    private String permisosEspeciales;

    public Administrador(String nombre, String apellido, int dni, String contrasena, String permisosEspeciales) {
        super(nombre, apellido, dni,    TipoUsuario.ADMINISTRADOR, contrasena);
        this.permisosEspeciales = permisosEspeciales;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return this.getNombreUsuario().equals(usuario) && this.getContrasena().equals(contrasena);
    }

    @Override
    public String toString() {
        return "Contraseña: '" + getContrasena() + '\'' +
                ", Dni:'" + getDni() + '\'' +
                ", Nombre:'" + getNombre() + '\'' +
                ", TipoUsuario:" + getTipoUsuario();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson(); // Obtiene el JSON del método de la clase base
        jsonObject.put("contrasena", getContrasena()); // Añade la contraseña
        return jsonObject;
    }

    public static Habitacion crearHabitacion(int numero, TipoHabitacion tipoHabitacion) {
        // Crear la nueva habitación

        Habitacion nuevaHabitacion = new Habitacion(numero, tipoHabitacion);

        return nuevaHabitacion;
    }


/*
    @Override
    public void cambiarContrasena(String nuevaContrasena) {
        this.getContrasena() = nuevaContrasena;
        System.out.println("Contraseña actualizada correctamente.");
    }

 */
}
