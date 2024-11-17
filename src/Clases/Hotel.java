package Clases;

import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Excepciones.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hotel {
    private String nombre;
    private  GestorColeccion<Habitacion> habitaciones;
    private  GestorColeccion<Pasajero> pasajeros;
    private  GestorReservas reservas;
    private Administrador administrador;
    private GestorColeccion<Recepcionista> recepcionistas;
    private static final int MAX_RECEPCIONISTAS = 5;
    //private GestorColeccion<ServicioAdicional> serviciosAdicionales;



    public Hotel(String nombre, Administrador administrador) {
        this.nombre = nombre;
        this.habitaciones = new GestorColeccion<>();
        this.reservas = new GestorReservas();
        this.pasajeros = new GestorColeccion<>();
        this.administrador = administrador;
        this.recepcionistas = new GestorColeccion<>();

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
/**             MANEJO    RECEPCIONISTAS    MEDIANTE ADMIN       */

    /** Métodos para gestionar recepcionistas */
    public boolean agregarRecepcionista(Recepcionista recepcionista) throws RecepcionistaYaExisteExcepcion{
        boolean agregado = false;
        if (recepExiste(recepcionista.getNombreUsuario())){
            throw new RecepcionistaYaExisteExcepcion();
        } else {
            if (recepcionistas.obtenerTodos().size() >= MAX_RECEPCIONISTAS) {
                System.out.println("No se pueden agregar más recepcionistas. Límite alcanzado.");
            } else {
                recepcionistas.agregarElemento(recepcionista);
                System.out.println("Recepcionista agregado exitosamente.");
                agregado = true;
            }
        }
        return agregado;
    }

    public boolean eliminarRecepcionista(String nombreUsuario) throws RecepcionistaNoExiste, ListaVaciaExcepcion{
        boolean eliminado = false;
        if(!recepExiste(nombreUsuario)){
            throw new RecepcionistaNoExiste();
        } else if(recepcionistas.estaVacia()){
            throw new ListaVaciaExcepcion();
        } else {
            for (Recepcionista r : recepcionistas.obtenerTodos()) {
                if (r.getNombreUsuario().equals(nombreUsuario) ) {
                    recepcionistas.eliminarElemento(r);
                    System.out.println("Recepcionista eliminado exitosamente.");
                    eliminado = true;
                }
            }
        }

        return eliminado;
    }

    public boolean recepExiste(String nombreUsuario){
        for(Recepcionista r : recepcionistas.obtenerTodos()){
            if (r.getNombreUsuario().equals(nombreUsuario)){
                return true;
            }
        }
        return false;
    }

    public Recepcionista buscarPorNombreUsuarioRecepcionista(String nombreUsuario) throws RecepcionistaNoExiste{
        if (!recepExiste(nombreUsuario)){
            throw new RecepcionistaNoExiste();
        } else {
            for (Recepcionista r : recepcionistas.obtenerTodos()){
                if (r.getNombreUsuario().equals(nombreUsuario)){
                    return r;
                }
            }
        }
        return null;
    }

    public GestorColeccion<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    // Método para autenticar recepcionistas
    public boolean autenticarRecepcionista(String nombreUsuario, String contrasena) {
        boolean autenticado =false;
        try {
            Recepcionista recepcionista = buscarPorNombreUsuarioRecepcionista(nombreUsuario);
            recepcionista.autenticar(nombreUsuario, contrasena);
            autenticado = true;
        } catch (RecepcionistaNoExiste e) {
            System.out.println("Error: " + e.getMessage());
        }
        return autenticado;
    }



    /**              AGREGAR Y ELIMINAR HABITACIONES   */

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


    /** LISTAR HABITACIONES POR SU ESTADO/TIPO/NUMERO */


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
        boolean existe = false;
        for (Habitacion h : habitaciones.obtenerTodos()) {
            if(h.getNumero() == numero) {
                existe = true;
            }
        }
        return existe;
    }



    /**CHECK IN*/

    public boolean realizarCheckIn(int numeroHabitacion, int dniPasajero) throws HabitacionNoExisteExcepcion, ReservaNoValidaExcepcion {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        boolean checkInrealizado = false;
        if (habitacion == null) {
            throw new HabitacionNoExisteExcepcion();
        } else {
            LocalDate hoy = LocalDate.now();
            Reserva reserva = reservas.buscarReserva(numeroHabitacion, dniPasajero, hoy);
            if (reserva == null) {
                throw new ReservaNoValidaExcepcion();
            } else {
                if (habitacion.getEstado() == EstadoHabitacion.RESERVADA) {
                    habitacion.setEstado(EstadoHabitacion.OCUPADA);
                    habitacion.setDniOcupante(dniPasajero);
                    System.out.println("Check-in realizado para el pasajero con DNI: " + dniPasajero);
                    checkInrealizado = true;
                } else {
                    System.out.println("La habitación NO ESTÁ en estado RESERVADA.");
                }
            }
        }
        return checkInrealizado;
    }



    /**CHECK OUT*/

    public boolean realizarCheckOut(int numeroHabitacion, int dniPasajero) throws HabitacionNoExisteExcepcion {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        boolean checkInrealizado = false;

        if (habitacion == null) {
            throw new HabitacionNoExisteExcepcion();
        } else {
            if (habitacion.getEstado() == EstadoHabitacion.OCUPADA && habitacion.getDniOcupante() == dniPasajero) {
                habitacion.setEstado(EstadoHabitacion.LIMPIEZA);
                habitacion.setDniOcupante(0); // Liberar el DNI del ocupante
                System.out.println("Check-out realizado para el pasajero con DNI: " + dniPasajero);
                checkInrealizado = true;
            } else {
                System.out.println("Error: La habitación no está ocupada por el pasajero con DNI proporcionado.");
            }
        }
        return checkInrealizado;

    }

    /** INICIAR HABITACIONES AUTOMATICAMENTE  */

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




    /**          PASAJEROS            */

    public void agregarPasajero(String nomre, String apellido, int dni, String origen, String domicilio) {
        Pasajero p = new Pasajero(nomre, apellido,dni, origen, domicilio);
        pasajeros.agregarElemento(p);

    }
    public boolean eliminarPasajero(int dniPasajero) throws PasajeroNoExisteExcepcion{
        boolean eliminado = false;
        Pasajero p = buscarPasajeroPorDni(dniPasajero);
        if (!pasajeroExiste(p)){
            throw new PasajeroNoExisteExcepcion();
        } else {
            pasajeros.eliminarElemento(p);
            return true;
        }
    }

    public StringBuilder pasajerosToString(){
        StringBuilder sb = new StringBuilder();
        for(Pasajero p : pasajeros.obtenerTodos()){
            sb.append(p.toString());
        }
        return sb;
    }

    public Pasajero buscarPasajeroPorDni(int dni) {
        for (Pasajero p : pasajeros.obtenerTodos()) {
            if (p.getDni() == dni) {
                return p;
            }
        }
        return null;
    }
    public boolean pasajeroExiste(Pasajero p){
        for (Pasajero pasajero : pasajeros.obtenerTodos()){
            if (pasajero.equals(p)){
                return true;
            }
        }
        return false;
    }
    public StringBuilder historialReservasToStringMedianteDni(int dniPasajero){
        Pasajero pasajero = buscarPasajeroPorDni(dniPasajero);
        StringBuilder sb = new StringBuilder();
        sb.append(pasajero.historiaReservasToString());
        return sb;
    }




    /**           AGREGAR Y CANCELAR RESERVAS               */


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


        // Buscar al pasajero correspondiente y agregar la reserva a su historial
        Pasajero pasajero = buscarPasajeroPorDni(dniPasajero);
        if (pasajero != null) {
            pasajero.agregarReservaAlHistorial(nuevaReserva);
            System.out.println("Reserva agregada al historial del pasajero con DNI: " + dniPasajero);
        } else {
            System.out.println("Error: Pasajero no encontrado. La reserva solo estará registrada globalmente.");
        }

        System.out.println("Reserva creada para el pasajero con DNI: " + dniPasajero + " en la habitación " + numeroHabitacion);

        return true;
    }

    // Método para cancelar una reserva desde la clase Hotel
    public boolean cancelarReserva(int dniPasajero, int numeroHabitacion, LocalDate inicio, LocalDate fin) {
        boolean cancelada = reservas.eliminarReserva(numeroHabitacion, inicio, fin);
        if (cancelada) {
            System.out.println("Reserva cancelada exitosamente para la habitación " + numeroHabitacion);
        } else {
            System.out.println("No se encontró ninguna reserva que coincida con los criterios especificados.");
        }
        // Buscar al pasajero correspondiente y agregar la reserva a su historial
        Pasajero pasajero = buscarPasajeroPorDni(dniPasajero);
        if (pasajero != null) {
            pasajero.eliminarReservaDelHistorial(buscarReservaPorDni(dniPasajero));
            System.out.println("Reserva eliminada al historial del pasajero con DNI: " + dniPasajero);
        } else {
            System.out.println("Error: Pasajero no encontrado.No se pudo eliminar la reserva del historial del pasajero");
        }

        return cancelada;
    }

    /**   BUSCAR RESERVAS POR DNI       */

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


    /**               MOSTRAR      LISTAS             */

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

    public StringBuilder listaHabitacionesToString() throws ListaVaciaExcepcion { //NUNCA VA A ESTAR VACIA YA QUE SE GENERAN UNAS POR DEFECTO O SI? :)
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
        if (reservas.estaVacia()){
            sb.append("Error");
            throw new ListaVaciaExcepcion();
        } else {
            for(Reserva r : reservas.obtenerTodos()){
                sb.append(r.toString());
            }
        }
        return sb;
    }
    public StringBuilder listaRecepcionistasToString() throws ListaVaciaExcepcion {
        StringBuilder sb = new StringBuilder();
        if (recepcionistas.estaVacia()){
            sb.append("Error");
            throw new ListaVaciaExcepcion();
        } else {
            for(Recepcionista r : recepcionistas.obtenerTodos()){
                sb.append(r.toString());
            }
        }
        return sb;
    }

    /**             LISTAS        A         JSON      */


    public  JSONArray listaPasajerosToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pasajero pasajero : pasajeros.obtenerTodos()) {
            JSONObject jsonPasajero = pasajero.toJson();
            jsonArray.put(jsonPasajero);
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

    public JSONArray listaRecepcionistasToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Recepcionista recepcionista : recepcionistas.obtenerTodos()) {
            jsonArray.put(recepcionista.toJson());
        }
        return jsonArray;
    }

    /**         CARGAR  ARCHIVO  JSON  CON   TODAS   LAS    LISTAS    */


    public void cargarArchivo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);

        JSONArray habitacionesArray = listaHabitacionesToJson();

        jsonObject.put("habitaciones", habitacionesArray);

        JSONArray pasajerosArray = listaPasajerosToJson();

        jsonObject.put("pasajeros", pasajerosArray);

        JSONArray reservasArray = listaReservaToJson();

        jsonObject.put("reservas", reservasArray);

        JSONArray recepcionistasArray = listaRecepcionistasToJson(); // Lista de recepcionistas
        jsonObject.put("recepcionistas", recepcionistasArray);


        JSONUtiles.uploadJSON(jsonObject, "Hotel");
    }

    /**  METODO PARA CARGAR DESDE EL ARCHIVO JSON AL BUFFER */
    public void cargarDesdeArchivo() {
        String contenido = JSONUtiles.downloadJSON("Hotel"); // Leer el archivo JSON
        if (contenido.isEmpty()) {
            System.out.println("No se encontró un archivo JSON existente.");
            return;
        }

        JSONObject jsonObject = new JSONObject(contenido);

        // Cargar nombre del hotel
        this.nombre = jsonObject.getString("nombre");

        // Cargar habitaciones
        JSONArray habitacionesArray = jsonObject.getJSONArray("habitaciones");
        for (int i = 0; i < habitacionesArray.length(); i++) {
            JSONObject habitacionJson = habitacionesArray.getJSONObject(i);
            Habitacion habitacion = Habitacion.fromJson(habitacionJson);
            this.habitaciones.agregarElemento(habitacion);
        }

        // Cargar pasajeros
        JSONArray pasajerosArray = jsonObject.getJSONArray("pasajeros");
        for (int i = 0; i < pasajerosArray.length(); i++) {
            JSONObject pasajeroJson = pasajerosArray.getJSONObject(i);
            Pasajero pasajero = Pasajero.fromJson(pasajeroJson);
            this.pasajeros.agregarElemento(pasajero);
        }

        // Cargar reservas
        JSONArray reservasArray = jsonObject.getJSONArray("reservas");
        for (int i = 0; i < reservasArray.length(); i++) {
            JSONObject reservaJson = reservasArray.getJSONObject(i);
            Reserva reserva = Reserva.fromJson(reservaJson);
            this.reservas.agregarElemento(reserva);
        }

        // Cargar recepcionistas
        JSONArray recepcionistasArray = jsonObject.getJSONArray("recepcionistas");
        for (int i = 0; i < recepcionistasArray.length(); i++) {
            JSONObject recepcionistaJson = recepcionistasArray.getJSONObject(i);
            Recepcionista recepcionista = Recepcionista.fromJson(recepcionistaJson);
            this.recepcionistas.agregarElemento(recepcionista);
        }

        System.out.println("Datos cargados exitosamente desde el archivo JSON.");
    }
}
