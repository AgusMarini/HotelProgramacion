import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombre;
    private static List<Habitacion> habitaciones;
    private static List<Pasajero> pasajeros;
    private static List<Reserva> reservas;
    private List<ServicioAdicional> serviciosAdicionales;

    public Hotel(String nombre) {
        this.nombre = nombre;
        this.habitaciones = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();


        inicializarServiciosPredeterminados();
        inicializarServiciosPredeterminados();
        inicializarHabitacionesPredeterminadas();
        cargarDatosPasajeros();

        // verificar las habitaciones cada 60 segundos
        Thread verificadorHabitaciones = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        verificarHabitacionesParaCambiarADisponible();
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        verificadorHabitaciones.setDaemon(true);


        verificadorHabitaciones.start();
    }
    public  void agregarHabitacion(Habitacion habitacion) {

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
                System.out.println("La habitación " + habitacion.getNumero() + " ahora está DISPONIBLE.");
            }
        }
    }


    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
        guardarDatosPasajeros();
    }
    public List<Habitacion> listarHabitaciones() {
        return new ArrayList<>(habitaciones);
    }




    public List<Habitacion> listarHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                disponibles.add(h);
            }
        }
        return disponibles;
    }

    public static List<Habitacion> listarHabitacionesNoDisponibles() {
        List<Habitacion> noDisponibles = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.LIMPIEZA || h.getEstado() == EstadoHabitacion.REPARACION || h.getEstado() == EstadoHabitacion.DESINFECCION || h.getEstado() == EstadoHabitacion.OCUPADA) {
                noDisponibles.add(h);
            }
        }
        return noDisponibles;
    }

    public static Habitacion buscarHabitacionDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equals(tipoHabitacion) && habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                return habitacion;
            }
        }
        return null;
    }


    public static Habitacion buscarHabitacionPorNumero(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }


    public static Pasajero buscarPasajeroPorDni(String dni) {
        for (Pasajero p : pasajeros) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public boolean reservarHabitacion(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales) {
        Habitacion habitacionDisponible = buscarHabitacionDisponiblePorTipo(tipoHabitacion);

        if (habitacionDisponible != null) {
            try {
                habitacionDisponible.reservar(pasajero.getNombre(), pasajero.getNombre(), tipoHabitacion.toString());
                Reserva nuevaReserva = new Reserva(pasajero, tipoHabitacion, cantidadPasajeros, serviciosAdicionales, habitacionDisponible.getNumero());
                agregarReserva(nuevaReserva);
                return true;
            } catch (HabitacionNoDisponibleException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("No hay habitaciones disponibles de ese tipo.");
            return false;
        }
    }


    public boolean cancelarReserva(String dniPasajero) {
        Reserva reserva = buscarReservaPorDni(dniPasajero);
        if (reserva != null) {
            reservas.remove(reserva);
            Habitacion habitacion = buscarHabitacionPorTipo(reserva.getTipoHabitacion());
            if (habitacion != null) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
            }
            System.out.println("Reserva cancelada exitosamente.");
            return true;
        } else {
            System.out.println("No se encontró una reserva con el DNI proporcionado.");
            return false;
        }
    }

    private Habitacion buscarHabitacionPorTipo(TipoHabitacion tipoHabitacion) {
        for (Habitacion h : habitaciones) {
            if (h.getTipo() == tipoHabitacion) {
                return h;
            }
        }
        return null;
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
        guardarDatosReservas();
    }

    public static Reserva buscarReservaPorDni(String dni) {
        for (Reserva reserva : reservas) {
            if (reserva.getPasajero().getDni().equals(dni)) {
                return reserva;
            }
        }
        return null;
    }
    private void inicializarServiciosPredeterminados() {
        List<String> horariosSpa = List.of("10:00 AM", "12:00 PM", "02:00 PM", "04:00 PM");
        List<String> horariosRestaurante = List.of("08:00 PM", "09:00 PM", "10:00 PM");

        serviciosAdicionales.add(new ServicioAdicional("Spa", horariosSpa));
        serviciosAdicionales.add(new ServicioAdicional("Restaurante", horariosRestaurante));
    }

    public ServicioAdicional buscarServicio(String nombre) {
        for (ServicioAdicional servicio : serviciosAdicionales) {
            if (servicio.getNombre().equalsIgnoreCase(nombre)) {
                return servicio;
            }
        }
        return null;
    }

    public void guardarDatosPasajeros() {
        JSONArray jsonArray = new JSONArray();

        for (Pasajero pasajero : pasajeros) {
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

    public void guardarDatosReservas() {
        JSONArray jsonArray = new JSONArray();
        for (Reserva reserva : reservas) {
            JSONObject jsonReserva = new JSONObject();
            jsonReserva.put("nombre", reserva.getPasajero().getNombre());
            jsonReserva.put("dni", reserva.getPasajero().getDni());
            jsonReserva.put("tipoHabitacion", reserva.getTipoHabitacion().toString());
            jsonReserva.put("cantidadPasajeros", reserva.getCantidadPasajeros());
            jsonReserva.put("serviciosAdicionales", new JSONArray(reserva.getServiciosAdicionales()));
            jsonArray.put(jsonReserva);
        }

        try (FileWriter file = new FileWriter("reservas.json")) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatosPasajeros() {
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
                pasajeros.add(pasajero);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
