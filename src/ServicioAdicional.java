import java.util.ArrayList;
import java.util.List;

public class ServicioAdicional {
    private String nombre;
    private List<String> horariosDisponibles;
    private List<String> reservas;

    public ServicioAdicional(String nombre, List<String> horariosDisponibles) {
        this.nombre = nombre;
        this.horariosDisponibles = horariosDisponibles;
        this.reservas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public boolean reservarTurno(String horario) {
        if (horariosDisponibles.contains(horario) && !reservas.contains(horario)) {
            reservas.add(horario);
            System.out.println("Turno reservado para " + nombre + " en el horario: " + horario);
            return true;
        }
        System.out.println("El horario no está disponible para " + nombre);
        return false;
    }

    public void cancelarReserva(String horario) {
        reservas.remove(horario);
        System.out.println("Reserva cancelada para " + nombre + " en el horario: " + horario);
    }
}
