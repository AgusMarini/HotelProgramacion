import java.util.ArrayList;
import java.util.HashMap;

public class Administrador extends Usuario implements Autenticable {
    private ArrayList<Habitacion> habitaciones;
    private HashMap<String, Usuario> usuarios;
    private ArrayList<ServicioAdicional> serviciosExtras;

    public Administrador(String nombre, String dni, String contrasena) {
        super(nombre, dni, contrasena);
        this.habitaciones = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.serviciosExtras = new ArrayList<>();
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Administrador: " + getNombre());
    }

    @Override
    public void verificarPermisos() {
        // Los administradores siempre tienen acceso
        System.out.println("Acceso concedido al administrador.");
    }

    public void agregarHabitacion(Hotel hotel, Habitacion habitacion) {
        hotel.agregarNuevaHabitacion(habitacion);
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
        // Usar el nombre y la contraseña para autenticar
        return getNombre().equals(usuario) && getContrasena().equals(contrasena);
    }

    @Override
    public void cambiarContrasena(String nuevaContrasena) {
        setContrasena(nuevaContrasena); // Usar el método de la clase `Usuario` si existe
        System.out.println("Contraseña actualizada correctamente.");
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nombre='" + getNombre() + '\'' +
                ", dni='" + getDni() + '\'' +
                '}';
    }
}
