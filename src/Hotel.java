import Exceptions.DatosCargadosInvalidosException;
import Exceptions.FechaInvalidaException;
import Exceptions.ReservaNoEncontradaException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Hotel {
    private String nombreHotel;
    private static Map<Integer, Habitacion> habitacionesMap;
    private static List<Pasajero> listaPasajeros;
    private static List<Reserva> listaReservas;
    private List<ServicioAdicional> listaServicios;

    public Hotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
        habitacionesMap = new HashMap<>();
        listaPasajeros = new ArrayList<>();
        listaReservas = new ArrayList<>();
        listaServicios = new ArrayList<>();

        cargarHabitacionesIniciales();
        inicializarServicios();
        cargarDatosPasajeros();

        // Hilo de verificación para el estado de las habitaciones
        Thread verificador = new Thread(() -> {
            while (true) {
                try {
                    actualizarEstadoHabitaciones();
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        verificador.setDaemon(true);
        verificador.start();
    }
    public static List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void agregarNuevaHabitacion(Habitacion habitacion) {
        if (habitacionesMap.containsKey(habitacion.getNumero())) {
            throw new IllegalArgumentException("Error: El número de habitación ya existe.");
        }
        habitacionesMap.put(habitacion.getNumero(), habitacion);
        System.out.println("Habitación " + habitacion.getNumero() + " agregada exitosamente.");
    }

    public void eliminarHabitacion(int numeroHabitacion) {
        if (habitacionesMap.remove(numeroHabitacion) != null) {
            System.out.println("Habitación eliminada: " + numeroHabitacion);
        } else {
            System.out.println("No se encontró una habitación con el número proporcionado.");
        }
    }

    private void cargarHabitacionesIniciales() {
        habitacionesMap.put(201, new Habitacion(201, TipoHabitacion.SIMPLE, 150));
        habitacionesMap.put(202, new Habitacion(202, TipoHabitacion.DOBLE, 250));
        habitacionesMap.put(203, new Habitacion(203, TipoHabitacion.SUITE, 350));
        habitacionesMap.put(204, new Habitacion(204, TipoHabitacion.SIMPLE, 150));
        habitacionesMap.put(205, new Habitacion(205, TipoHabitacion.DOBLE, 250));
    }

    public void actualizarEstadoHabitaciones() {
        for (Habitacion habitacion : habitacionesMap.values()) {
            if (habitacion.puedeCambiarADisponible()) { // Usamos el método corregido
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                System.out.println("La habitación " + habitacion.getNumero() + " ha cambiado a disponible.");
            }
        }
    }

    public void registrarPasajero(Pasajero nuevoPasajero) {
        listaPasajeros.add(nuevoPasajero);
        guardarPasajerosEnArchivo();
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion habitacion : habitacionesMap.values()) {
            if (habitacion.estaDisponible()) {
                disponibles.add(habitacion);
            }
        }
        return disponibles;
    }
    public double obtenerCostoPorTipoHabitacion(TipoHabitacion tipo) {
        for (Habitacion habitacion : habitacionesMap.values()) {
            if (habitacion.getTipo() == tipo) {
                return habitacion.getCosto();
            }
        }
        throw new IllegalArgumentException("No se encontró una habitación con el tipo especificado.");
    }


    public static Habitacion buscarHabitacionPorNumero(int numero) {
        return habitacionesMap.get(numero);
    }

    public static Pasajero buscarPasajeroPorDNI(String dni) {
        for (Pasajero pasajero : listaPasajeros) {
            if (pasajero.getDni().equals(dni)) {
                return pasajero;
            }
        }
        return null;
    }
    private boolean esFechaDisponibleParaTipo(TipoHabitacion tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        for (Habitacion habitacion : habitacionesMap.values()) {
            if (habitacion.getTipo() == tipo && habitacion.estaDisponible()) {
                if (esFechaDisponible(habitacion, fechaInicio, fechaFin)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean crearReserva(Pasajero pasajero, TipoHabitacion tipo, int cantidadPersonas, List<String> serviciosExtras, LocalDate fechaInicio, LocalDate fechaFin) throws FechaInvalidaException {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new FechaInvalidaException("La fecha de fin no puede ser anterior a la de inicio.");
        }

        // Verificar si hay habitaciones disponibles del tipo solicitado y si las fechas no se superponen
        if (esFechaDisponibleParaTipo(tipo, fechaInicio, fechaFin)) {
            Reserva reserva = new Reserva(pasajero, tipo, cantidadPersonas, serviciosExtras, -1, fechaInicio, fechaFin); // Usar -1 para indicar que aún no se ha asignado una habitación
            listaReservas.add(reserva);
            guardarReservasEnArchivo();
            System.out.println("Reserva creada exitosamente. La habitación se asignará al realizar el Check-In.");
            return true;
        } else {
            System.out.println("No hay habitaciones disponibles para el tipo solicitado en las fechas proporcionadas.");
            return false;
        }
    }


    public boolean cancelarReserva(String dniPasajero) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getPasajero().getDni().equals(dniPasajero)) {
                listaReservas.remove(reserva);
                Habitacion habitacion = buscarHabitacionPorNumero(reserva.getNumeroHabitacion());
                if (habitacion != null) {
                    habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                }
                System.out.println("Reserva cancelada para el pasajero con DNI: " + dniPasajero);
                guardarReservasEnArchivo(); // Guardar cambios en archivo
                return true;
            }
        }
        System.out.println("No se encontró una reserva para el pasajero con DNI: " + dniPasajero);
        return false;
    }
    public boolean esFechaDisponible(Habitacion habitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getNumeroHabitacion() == habitacion.getNumero()) {
                // Verificar si las fechas se superponen
                if (!(fechaFin.isBefore(reserva.getFechaInicio()) || fechaInicio.isAfter(reserva.getFechaFin()))) {
                    return false; // Las fechas se superponen, no está disponible
                }
            }
        }
        return true; // Las fechas no se superponen, está disponible
    }


    public static Habitacion buscarHabitacionPorTipo(TipoHabitacion tipo) {
        for (Habitacion habitacion : habitacionesMap.values()) {
            if (habitacion.getTipo() == tipo && habitacion.estaDisponible()) {
                return habitacion;
            }
        }
        return null;
    }

    private void inicializarServicios() {
        List<String> horariosSpa = List.of("10:00 AM", "12:00 PM", "02:00 PM", "04:00 PM");
        List<String> horariosRestaurante = List.of("08:00 PM", "09:00 PM", "10:00 PM");

        listaServicios.add(new ServicioAdicional("Spa", horariosSpa, 50.0));
        listaServicios.add(new ServicioAdicional("Restaurante", horariosRestaurante, 30.0));
        listaServicios.add(new ServicioAdicional("Gimnasio", List.of("6:00 AM", "8:00 AM", "6:00 PM"), 25.0));
        listaServicios.add(new ServicioAdicional("Desayuno", List.of("7:00 AM", "9:00 AM", "11:00 AM"), 15.0));
    }

    public ServicioAdicional obtenerServicioPorNombre(String nombre) {
        for (ServicioAdicional servicio : listaServicios) {
            if (servicio.getNombre().equalsIgnoreCase(nombre)) {
                return servicio;
            }
        }
        return null;
    }

    private void cargarDatosPasajeros() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("pasajeros.json")));
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPasajero = jsonArray.getJSONObject(i);
                String nombre = jsonPasajero.getString("nombre");
                String dni = jsonPasajero.getString("dni");
                String origen = jsonPasajero.getString("origen");
                String domicilio = jsonPasajero.getString("domicilio");
                TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(jsonPasajero.getString("tipo"));
                Pasajero pasajero = new Pasajero(nombre, dni, origen, domicilio, tipoHabitacion);
                listaPasajeros.add(pasajero);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarPasajerosEnArchivo() {
        JSONArray jsonArray = new JSONArray();
        for (Pasajero pasajero : listaPasajeros) {
            JSONObject jsonPasajero = new JSONObject();
            jsonPasajero.put("nombre", pasajero.getNombre());
            jsonPasajero.put("dni", pasajero.getDni());
            jsonPasajero.put("origen", pasajero.getOrigen());
            jsonPasajero.put("domicilio", pasajero.getDomicilio());
            jsonPasajero.put("tipo", pasajero.getTipo().toString());
            jsonArray.put(jsonPasajero);
        }
        try (FileWriter file = new FileWriter("pasajeros.json")) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarReservasEnArchivo() {
        JSONArray jsonArray = new JSONArray();
        for (Reserva reserva : listaReservas) {
            JSONObject jsonReserva = new JSONObject();
            jsonReserva.put("nombre", reserva.getPasajero().getNombre());
            jsonReserva.put("dni", reserva.getPasajero().getDni());
            jsonReserva.put("tipoHabitacion", reserva.getTipoHabitacion().toString());
            jsonReserva.put("cantidadPasajeros", reserva.getCantidadPasajeros());
            jsonReserva.put("serviciosExtras", new JSONArray(reserva.getServiciosAdicionales()));
            jsonArray.put(jsonReserva);
        }
        try (FileWriter file = new FileWriter("reservas.json")) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombreHotel='" + nombreHotel + '\'' +
                ", totalHabitaciones=" + habitacionesMap.size() +
                ", totalPasajeros=" + listaPasajeros.size() +
                ", totalReservas=" + listaReservas.size() +
                '}';
    }
}
