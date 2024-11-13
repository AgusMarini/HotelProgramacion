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
import java.util.Scanner;

public class Recepcionista extends Usuario {

    private static ArrayList<Habitacion> habitaciones;
    private static List<Pasajero> pasajeros;
    private static List<Reserva> reservas;
    private static List<Reserva> reservasHabitaciones;
    private static List<ServicioAdicional> serviciosAdicionales;

    public Recepcionista(String nombre, String dni) {
        super(nombre, dni);
        this.habitaciones = new ArrayList<>();
    }

    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public void realizarCheckIn(List<Pasajero> pasajeros){

    }

    public void realizarCheckIn(String dniPasajero) {
        Pasajero pasajero = buscarPasajeroPorDni(dniPasajero);
        if (pasajero != null) {
            Reserva reserva = buscarReservaPorDni(dniPasajero);
            if (reserva != null) {
                Habitacion habitacionDisponible = buscarHabitacionDisponiblePorTipo(reserva.getTipoHabitacion());
                if (habitacionDisponible != null) {
                    habitacionDisponible.realizarCheckIn(pasajero);
                    System.out.println("Check-In realizado en la habitación " + habitacionDisponible.getNumero());
                } else {
                    System.out.println("No hay habitaciones disponibles del tipo reservado.");
                }
            } else {
                System.out.println("El pasajero no tiene una reserva.");
            }
        } else {
            System.out.println("Pasajero no encontrado.");
        }
    }


    public void realizarCheckOut(int numeroHabitacion) {
        Scanner scanner = new Scanner(System.in);
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);

        if (habitacion != null) {
            if (habitacion.realizarCheckOut()) {
                System.out.println("Check-Out realizado correctamente!");
                System.out.println("Seleccione el estado de la habitación después del Check-Out:");
                System.out.println("1. LIMPIEZA");
                System.out.println("2. DISPONIBLE");

                int opcionEstado = scanner.nextInt();
                scanner.nextLine();

                switch (opcionEstado) {
                    case 1:
                        habitacion.setEstado(EstadoHabitacion.LIMPIEZA);
                        System.out.println("La habitación se ha marcado como en LIMPIEZA.");
                        break;
                    case 2:
                        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                        System.out.println("La habitación se ha marcado como DISPONIBLE.");
                        break;
                    default:
                        System.out.println("Opción no válida. La habitación se marcará como DISPONIBLE por defecto.");
                        habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
                        break;
                }
            } else {
                System.out.println("La habitación no está ocupada o no se pudo realizar el Check-Out.");
            }
        } else {
            System.out.println("Habitación no encontrada.");
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
            if (h.getEstado() == EstadoHabitacion.LIMPIEZA || h.getEstado() == EstadoHabitacion.OCUPADA) {
                noDisponibles.add(h);
            }
        }
        return noDisponibles;
    }

    public static Habitacion buscarHabitacionDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo() == tipoHabitacion && habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
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

    public int obtenerIndexPasajeroPorDni(String dni){
        int index=-1;
        for(int i=0; i<pasajeros.size();i++) {
            Pasajero p = pasajeros.get(i);
            if(p.getDni().equalsIgnoreCase(dni)){
                index=i;
            }
        }
        return index;
    }

    public int obtenerIndexHabitacionPorNumero(int numeroHabitacion){
        int index=-1;
        for(int i=0; i<habitaciones.size();i++) {
            Habitacion h = habitaciones.get(i);
            if(h.getNumero()==numeroHabitacion){
                index=i;
            }
        }
        return index;
    }

    public void controlarDisponibilidad() {
        System.out.println("Habitaciones disponibles:");
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
            return;
        }
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                System.out.println("Habitación " + habitacion.getNumero());
            }
        }
    }

    public boolean verificarDisponibilidad(int numeroHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        for (Reserva reserva : reservasHabitaciones) {
            // Verifica si el número de la habitación coincide
            if (reserva.getNumeroHabitacion() == numeroHabitacion) {
                // Comprueba si las fechas se solapan
                if ((fechaInicio.isBefore(reserva.getFechaSalida()) || fechaFin.isAfter(reserva.getFechaIngreso())) || (fechaInicio.isEqual(reserva.getFechaIngreso()))) {
                    // La habitación está reservada en el rango de fechas
                    return false;
                }
            }
        }
        // La habitación está disponible en el rango de fechas
        return true;
    }

    public String reservarHabitacion(String dni, int cantidadPasajeros, int numeroHabitacion, String tipoHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida) throws HabitacionNoDisponibleException {
        String retorno="";
        int indexHabitacion=obtenerIndexHabitacionPorNumero(numeroHabitacion);
        if (habitaciones.get(indexHabitacion).getEstado() == EstadoHabitacion.DISPONIBLE) {
            Reserva reserva = new Reserva(pasajeros.get(obtenerIndexPasajeroPorDni(dni)), TipoHabitacion.valueOf(tipoHabitacion), cantidadPasajeros, numeroHabitacion, fechaIngreso, fechaSalida);
            reservasHabitaciones.add(reserva);
            habitaciones.get(indexHabitacion).setUltimaModificacionEstado(LocalDateTime.now());
            retorno="La habitacion fue reservada con exito";
        } else {
            throw new HabitacionNoDisponibleException("La habitación " + numeroHabitacion + " no está disponible para reservar.");
        }
        return retorno;
    }

    public boolean reservarHabitacion(Pasajero pasajero, TipoHabitacion tipoHabitacion, int cantidadPasajeros, List<String> serviciosAdicionales, LocalDate fechaIngreso, LocalDate fechaSalida,) {
        Habitacion habitacionDisponible = buscarHabitacionDisponiblePorTipo(tipoHabitacion);

        if (habitacionDisponible != null) {
            habitacionDisponible.setEstado(EstadoHabitacion.RESERVADA);
            Reserva nuevaReserva = new Reserva(pasajero, tipoHabitacion, cantidadPasajeros, serviciosAdicionales, habitacionDisponible.getNumero(), fechaIngreso, fechaSalida);
            reservas.add(nuevaReserva);
            guardarDatosReservas();
            System.out.println("Habitación " + habitacionDisponible.getNumero() + " reservada con éxito.");
            return true;
        } else {
            System.out.println("No hay habitaciones disponibles de ese tipo.");
            return false;
        }
    }

    public boolean cancelarReserva(String dniPasajero) {
        Reserva reserva = buscarReservaPorDni(dniPasajero);
        if (reserva != null) {
            reservas.remove(reserva);
            Habitacion habitacion = buscarHabitacionPorNumero(reserva.getNumeroHabitacion());
            if (habitacion != null) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);
            }
            guardarDatosReservas();
            System.out.println("Reserva cancelada exitosamente.");
            return true;
        } else {
            System.out.println("No se encontró una reserva con el DNI proporcionado.");
            return false;
        }
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

    @Override
    public void mostrarInfo() {
        System.out.println("Recepcionista: " + getNombre());
    }
}
