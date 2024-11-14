import java.util.ArrayList;
import java.util.HashMap;

public class Administrador extends Usuario implements Autenticable {
    private ArrayList<Habitacion> habitaciones;
    private HashMap<String, Usuario> usuarios;
    private ArrayList<ServicioAdicional> serviciosExtras;
    private String contrasena;

    public Administrador(String nombre, String dni) {
        super(nombre, dni);
        this.habitaciones = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.serviciosExtras = new ArrayList<>();
        this.contrasena = contrasena;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Administrador: " + getNombre());
    }

    public void agregarHabitacion(Hotel hotel, Habitacion habitacion) {
        hotel.agregarHabitacion(habitacion);
    }


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
