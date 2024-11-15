package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.HabitacionNoExisteExcepcion;
import Excepciones.HbitacionYaExisteExcepcion;
import Excepciones.ListaVaciaExcepcion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombre;
    private  GestorColeccion<Habitacion> habitaciones;
    private  GestorColeccion<Pasajero> pasajeros;
    private  GestorReservas reservas;
    private Administrador administrador;
    private Recepcionista recepcionista;
    //private GestorColeccion<ServicioAdicional> serviciosAdicionales;



    public Hotel(String nombre, Administrador administrador, Recepcionista recepcionista) {
        this.nombre = nombre;
        this.habitaciones = new GestorColeccion<>();
        this.reservas = new GestorReservas();
        this.pasajeros = new GestorColeccion<>();
        this.administrador = administrador;
        this.recepcionista = recepcionista;


        inicializarHabitacionesPredeterminadas();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("administrador", administrador.toJson());

        jsonObject.put("recepcionista", recepcionista.toJson());

        JSONUtiles.uploadJSON(jsonObject, "Hotel");
        cargarArchivo();
/*
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
        */

    }

    public boolean agregarHabitacion(int numero, TipoHabitacion tipoHabitacion) throws HbitacionYaExisteExcepcion {
        boolean habitacionAgregada = false;
       if(habitacionExiste(numero) == true){
           throw new HbitacionYaExisteExcepcion();
       } else {
           habitaciones.agregarElemento(new Habitacion(numero, tipoHabitacion));
           System.out.println("Habitación " + numero + " agregada al inventario.");
           habitacionAgregada = true;
       }
        return habitacionAgregada;
    }

    public void eliminarHabitacion(int numero) throws HabitacionNoExisteExcepcion {
        if(habitacionExiste(numero) == false){
            throw new HabitacionNoExisteExcepcion();
        } else {
            Habitacion habitacion = buscarHabitacionPorNumero(numero);
            habitaciones.eliminarElemento(habitacion);
            System.out.println("Habitación " + numero + " eliminada del inventario.");
        }
    }

    private void inicializarHabitacionesPredeterminadas() {
        habitaciones.agregarElemento(new Habitacion(101, TipoHabitacion.SIMPLE));
        habitaciones.agregarElemento(new Habitacion(102, TipoHabitacion.DOBLE));
        habitaciones.agregarElemento(new Habitacion(103, TipoHabitacion.SUITE));
        habitaciones.agregarElemento(new Habitacion(104, TipoHabitacion.SIMPLE));
        habitaciones.agregarElemento(new Habitacion(105, TipoHabitacion.DOBLE));
    }
/*
    public void verificarHabitacionesParaCambiarADisponible() {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.puedeCambiarADisponible()) {
                habitacion.setEstado(EstadoHabitacion.DISPONIBLE);

            }
        }
    }
*/
    public void agregarPasajero(Pasajero pasajero) {
        pasajeros.agregarElemento(pasajero);

    }

    //HABITACIONES


    public List<Habitacion> listarHabitacionesDisponibles() {
        List<Habitacion> disponibles = new ArrayList<>();
        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                disponibles.add(h);
            }
        }
        return disponibles;
    }

    public List<Habitacion> listarHabitacionesNoDisponibles() {
        List<Habitacion> noDisponibles = new ArrayList<>();
        for (Habitacion h: habitaciones.obtenerTodos()) {
            if (h.getEstado() == EstadoHabitacion.LIMPIEZA || h.getEstado() == EstadoHabitacion.OCUPADA) {
                noDisponibles.add(h);
            }
        }
        return noDisponibles;
    }

    public Habitacion buscarHabitacionDisponiblePorTipo(TipoHabitacion tipoHabitacion) {
        for (Habitacion habitacion : habitaciones.obtenerTodos()) {
            if (habitacion.getTipo() == tipoHabitacion && habitacion.getEstado() == EstadoHabitacion.DISPONIBLE) {
                return habitacion;
            }
        }
        return null;
    }

    public Habitacion buscarHabitacionPorNumero(int numero) {
        for (Habitacion h : habitaciones.obtenerTodos()) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }
    public boolean habitacionExiste(int numero){
        boolean existe = false
        for (Habitacion h : habitaciones.obtenerTodos()) {
            if(h.getNumero() == numero) {
                existe = true;
            }
        }
        return existe;
    }
    public Pasajero buscarPasajeroPorDni(int dni) {
        for (Pasajero p : pasajeros.obtenerTodos()) {
            if (p.getDni() == dni) {
                return p;
            }
        }
        return null;
    }

    public boolean agregarReserva(int dniPasajero, int numeroHabitacion, LocalDate inicio, LocalDate fin) {
        // Verificar si la habitación existe
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        if (habitacion == null) {
            System.out.println("La habitación no existe.");
            return false;
        }
        // Verificar disponibilidad en el rango de fechas
        if (!reservas.estaHabitacionDisponible(numeroHabitacion, inicio, fin)) {
            System.out.println("La habitación no está disponible en las fechas solicitadas.");
            return false;
        }

        // Crear y agregar la reserva

        Reserva nuevaReserva = new Reserva(dniPasajero, numeroHabitacion, inicio, fin);
        reservas.agregarElemento(nuevaReserva);
        System.out.println("Reserva creada para el pasajero con DNI: " + dniPasajero + " en la habitación " + numeroHabitacion);
        return true;
    }

    // Método para cancelar una reserva desde la clase Hotel
    public boolean cancelarReserva(int numeroHabitacion, LocalDate inicio, LocalDate fin) {
        boolean cancelada = reservas.eliminarReserva(numeroHabitacion, inicio, fin);
        if (cancelada) {
            System.out.println("Reserva cancelada exitosamente para la habitación " + numeroHabitacion);
        } else {
            System.out.println("No se encontró ninguna reserva que coincida con los criterios especificados.");
        }
        return cancelada;
    }

    public Reserva buscarReservaPorDni(int dni) {
        for (Reserva reserva : reservas.obtenerTodos()) {
            if (reserva.getDniPasajero() == dni) {
                return reserva;
            }
        }
        return null;
    }
/*
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
*/

    public StringBuilder listaPasajerostoString() throws ListaVaciaExcepcion {
        StringBuilder sb = new StringBuilder();
        if (pasajeros.estaVacia()){
            sb.append("Error");
            throw new ListaVaciaExcepcion();
        } else {
            for(Pasajero p : pasajeros.obtenerTodos()){
                sb.append(p.toString());
            }
        }
        return sb;
    }

    public StringBuilder listaHabitacionesToString() throws ListaVaciaExcepcion { //NUNCA VA A ESTAR VACIA YA QUE SE GENERAN UNAS POR DEFECTO
        StringBuilder sb = new StringBuilder();
        if (habitaciones.estaVacia()){
            sb.append("Error");
            throw new ListaVaciaExcepcion();
        } else {
            for(Habitacion h : habitaciones.obtenerTodos()){
                sb.append(h.toString());
            }
        }
        return sb;
    }
    public StringBuilder listaReservasToString() throws ListaVaciaExcepcion {
        StringBuilder sb = new StringBuilder();
        if (habitaciones.estaVacia()){
            sb.append("Error");
            throw new ListaVaciaExcepcion();
        } else {
            for(Reserva r : reservas.obtenerTodos()){
                sb.append(r.toString());
            }
        }
        return sb;
    }

    public  JSONArray listaPasajerosToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pasajero pasajero : pasajeros.obtenerTodos()) {
            JSONObject jsonPasajero = pasajero.toJson();
        }
        return jsonArray;
    }

    public JSONArray listaReservaToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Reserva reserva : reservas.obtenerTodos()) {
            JSONObject jsonReserva = reserva.toJson();
            jsonArray.put(jsonReserva);
        }
        return jsonArray;
    }

    public JSONArray listaHabitacionesToJson(){
        JSONArray jsonArray = new JSONArray();
        for (Habitacion h: habitaciones.obtenerTodos()) {
            JSONObject jsonHabitacion = h.toJson();
            jsonArray.put(jsonHabitacion);
        }
        return jsonArray;
    }
    public void cargarArchivo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);

        JSONArray habitacionesArray = listaHabitacionesToJson();

        jsonObject.put("habitaciones", habitacionesArray);

        JSONArray pasajerosArray = listaPasajerosToJson();

        jsonObject.put("pasajeros", pasajerosArray);

        JSONArray reservasArray = listaReservaToJson();

        jsonObject.put("reservas", reservasArray);



        JSONUtiles.uploadJSON(jsonObject, "Hotel");

    }
}
