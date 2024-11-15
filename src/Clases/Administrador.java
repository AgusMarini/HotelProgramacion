package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoUsuario;
import Interfaces.Autenticable;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Administrador extends Usuario implements Autenticable {
    private String contrasena;

    public Administrador(String nombre,String apellido, String dni, String contrasena) {
        super(nombre, dni, apellido, TipoUsuario.ADMINISTRADOR);
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Contraseña: '" + contrasena + '\'' +
                ", Dni:'" + getDni() + '\'' +
                ", Nombre:'" + getNombre() + '\'' +
                ", TipoUsuario:" + getTipoUsuario();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson(); // Obtiene el JSON del método de la clase base
        jsonObject.put("contraseña", contrasena); // Añade la contraseña
        return jsonObject;
    }

    /*
    public Habitacion agregarHabitacion(){

    }
    /*
    public void modificarHabitacion(int numeroHabitacion, EstadoHabitacion nuevoEstado) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numeroHabitacion) {
                habitacion.setEstado(nuevoEstado);
                System.out.println("Estado de la habitación " + numeroHabitacion + " actualizado a " + nuevoEstado);
                return;
            }
        }
        System.out.println("Habitación no encontrada.");
    }


    public void eliminarHabitacion(int numeroHabitacion) {
        habitaciones.removeIf(h -> h.getNumero() == numeroHabitacion);
        System.out.println("Habitación eliminada: " + numeroHabitacion);
    }
    */

    @Override
    public boolean autenticar(String usuario, String contrasena) {
        return getNombre().equals(usuario) && this.contrasena.equals(contrasena);
    }


    @Override
    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena;
        System.out.println("Contraseña actualizada correctamente.");
    }
}
