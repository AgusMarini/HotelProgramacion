import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombre;
    private static List<Habitacion> habitaciones;
    /*
    private static List<Pasajero> pasajeros;
    private static List<Reserva> reservas;
    private static List<Reserva> reservasHabitaciones;
    private List<Clases.Recepcionista.ServicioAdicional> serviciosAdicionales;
    */

    public Hotel(String nombre) {
        this.nombre = nombre;
        habitaciones = new ArrayList<>();
        /*
        pasajeros = new ArrayList<>();
        reservas = new ArrayList<>();
        serviciosAdicionales = new ArrayList<>();
        */
        inicializarHabitacionesPredeterminadas();
        inicializarServiciosPredeterminados();
        cargarDatosPasajeros();

        // Hilo para verificar las habitaciones cada 60 segundos
        Thread verificadorHabitaciones = new Thread(() -> {
            while (true) {
                try {
                    verificarHabitacionesParaCambiarADisponible();
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        verificadorHabitaciones.setDaemon(true);
        verificadorHabitaciones.start();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == habitacion.getNumero()) {
                System.out.println("Error: Ya existe una habitación con el número " + habitacion.getNumero());
                return;
            }
        }
        habitaciones.add(habitacion);
        System.out.println("Habitación " + habitacion.getNumero() + " agregada al inventario.");
    }

    public void eliminarHabitacion(int numero) {
        habitaciones.removeIf(h -> h.getNumero() == numero);
        System.out.println("Habitación " + numero + " eliminada del inventario.");
    }

    private void inicializarHabitacionesPredeterminadas() {
        habitaciones.add(new Habitacion(101, TipoHabitacion.SIMPLE));
        habitaciones.add(new Habitacion(102, TipoHabitacion.DOBLE));
        habitaciones.add(new Habitacion(103, TipoHabitacion.SUITE));
        habitaciones.add(new Habitacion(104, TipoHabitacion.SIMPLE));
        habitaciones.add(new Habitacion(105, TipoHabitacion.DOBLE));
    }

    public void verificarHabitacionesParaCambiarADisponible() {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.puedeCambiarADisponible()) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

            }
        }
    }

}
