import java.util.ArrayList;
import java.util.List;

public class ServicioAdicional {
    private String nombre;
    private List<String> horariosDisponibles;
    private List<String> reservas;
    private double costo;

    public ServicioAdicional(String nombre, List<String> horariosDisponibles, double costo) {
        this.nombre = nombre;
        this.horariosDisponibles = horariosDisponibles;
        this.reservas = new ArrayList<>();
        this.costo = costo;
    }

    // Getters y setters
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

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{" +
                "nombre='" + nombre + '\'' +
                ", costo=" + costo +
                ", horariosDisponibles=" + horariosDisponibles +
                '}';
    }


    public static List<ServicioAdicional> inicializarServiciosPredeterminados() {

        List<String> horariosSpa = new ArrayList<>();
        horariosSpa.add("10:00 AM - 12:00 PM");
        horariosSpa.add("2:00 PM - 4:00 PM");


        List<String> horariosRestaurante = new ArrayList<>();
        horariosRestaurante.add("1:00 PM - 3:00 PM");
        horariosRestaurante.add("7:00 PM - 9:00 PM");


        List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
        serviciosAdicionales.add(new ServicioAdicional("Spa", horariosSpa, 50.0));
        serviciosAdicionales.add(new ServicioAdicional("Restaurante", horariosRestaurante, 30.0));

        return serviciosAdicionales;
    }
}
