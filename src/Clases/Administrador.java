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

    public Administrador(String nombreUsuario, String contrasena, String permisosEspeciales) {
        super(TipoUsuario.ADMINISTRADOR, contrasena, nombreUsuario);
        this.permisosEspeciales = permisosEspeciales;
    }

    public String getPermisosEspeciales() {
        return permisosEspeciales;
    }

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return this.getNombreUsuario().equals(usuario) && this.getContrasena().equals(contrasena);
    }

    @Override
    public String toString() {
        return "Contraseña: '" + getContrasena() + '\'' +
                ", NombreUsuario: '" + getNombreUsuario() + '\'' +
                ", TipoUsuario:" + getTipoUsuario();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson(); // Obtiene el JSON del método de la clase base
        jsonObject.put("permisosEspeciales", getPermisosEspeciales()); // Añade la contraseña
        return jsonObject;
    }

    /**   SIN USAR     */

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
