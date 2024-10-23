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
    private List<Habitacion> habitaciones;
    private List<Pasajero> pasajeros;
    private List<Reserva> reservas;

    public Hotel(String nombre) {
        this.nombre = nombre;
        this.habitaciones = new ArrayList<>();
        this.pasajeros = new ArrayList<>();
        this.reservas = new ArrayList<>();

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

    private void inicializarHabitacionesPredeterminadas() {
        habitaciones.add(new Habitacion(101, TipoHabitacion.SIMPLE));
        habitaciones.add(new Habitacion(102, TipoHabitacion.DOBLE));
        habitaciones.add(new Habitacion(103, TipoHabitacion.SUITE));
        habitaciones.add(new Habitacion(104, TipoHabitacion.SIMPLE));
        habitaciones.add(new Habitacion(105, TipoHabitacion.DOBLE));
    }

    // Método que verifica si alguna habitación puede cambiar a DISPONIBLE
    public void verificarHabitacionesParaCambiarADisponible() {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.puedeCambiarADisponible()) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                System.out.println("La habitación " + habitacion.getNumero() + " ahora está DISPONIBLE.");
            }
        }
    }

    // Métodos para agregar pasajeros
    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.add(pasajero);
        guardarDatosPasajeros();
    }

    // Método para listar habitaciones disponibles
    public List<Habitacion> listarHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                disponibles.add(h);
            }
        }
        return disponibles;
    }

    // Método para listar habitaciones no disponibles
    public List<Habitacion> listarHabitacionesNoDisponibles() {
        List<Habitacion> noDisponibles = new ArrayList<>();
        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.LIMPIEZA || h.getEstado() == EstadoHabitacion.REPARACION || h.getEstado() == EstadoHabitacion.DESINFECCION || h.getEstado() == EstadoHabitacion.OCUPADA) {
                noDisponibles.add(h);
            }
        }
        return noDisponibles;
    }

    // Método para buscar una habitación disponible según su tipo
    public Habitacion buscarHabitacionDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equals(tipoHabitacion) && habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                return habitacion;
            }
        }
        return null;
    }

    // Método para buscar una habitación por su número
    public Habitacion buscarHabitacionPorNumero(int numero) {
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }

    // Método para buscar un pasajero por su DNI
    public Pasajero buscarPasajeroPorDni(String dni) {
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
            // Se crea la reserva con la habitación, incluyendo su número
            Reserva nuevaReserva = new Reserva(pasajero, tipoHabitacion, cantidadPasajeros, serviciosAdicionales, habitacionDisponible.getNumero());
            habitacionDisponible.setEstado(EstadoHabitacion.OCUPADA); // Marca la habitación como ocupada
            agregarReserva(nuevaReserva); // Agrega la reserva a la lista de reservas
            return true;
        } else {
            System.out.println("No hay habitaciones disponibles de ese tipo.");
            return false;
        }
    }

    // Método para cancelar la reserva
    public boolean cancelarReserva(String dniPasajero) {
        Reserva reserva = buscarReservaPorDni(dniPasajero);
        if (reserva != null) {
            reservas.remove(reserva);
            Habitacion habitacion = buscarHabitacionPorTipo(reserva.getTipoHabitacion());
            if (habitacion != null) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE); // Marca la habitación como disponible
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

    public Reserva buscarReservaPorDni(String dni) {
        for (Reserva reserva : reservas) {
            if (reserva.getPasajero().getDni().equals(dni)) {
                return reserva;
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
