package Clases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {

    private static final String FILE_HABITACIONES = "habitaciones.json";
    private static final String FILE_RESERVAS = "reservas.json";

    // Método para guardar una lista de habitaciones en un archivo JSON
    public static void guardarHabitaciones(List<Habitacion> habitaciones) {
        JSONArray jsonArray = new JSONArray();
        for (Habitacion habitacion : habitaciones) {
            jsonArray.put(habitacion.toJson());
        }
        uploadJSON(jsonArray, FILE_HABITACIONES);
    }

    // Método para cargar una lista de habitaciones desde un archivo JSON
    public static List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        String contenido = downloadJSON(FILE_HABITACIONES);
        if (!contenido.isEmpty()) {
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonHabitacion = jsonArray.getJSONObject(i);
                habitaciones.add(Habitacion.fromJson(jsonHabitacion));
            }
        }
        return habitaciones;
    }

    // Método para guardar una lista de reservas en un archivo JSON
    public static void guardarReservas(List<Reserva> reservas) {
        JSONArray jsonArray = new JSONArray();
        for (Reserva reserva : reservas) {
            jsonArray.put(reserva.toJson());
        }
        uploadJSON(jsonArray, FILE_RESERVAS);
    }

    // Método para cargar una lista de reservas desde un archivo JSON
    public static List<Reserva> cargarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String contenido = downloadJSON(FILE_RESERVAS);
        if (!contenido.isEmpty()) {
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonReserva = jsonArray.getJSONObject(i);
                reservas.add(Reserva.fromJson(jsonReserva));
            }
        }
        return reservas;
    }

    // Método auxiliar para escribir en un archivo JSON
    public static void uploadJSON(JSONArray jsonArray, String archive) {
        try (BufferedWriter salida = new BufferedWriter(new FileWriter(archive))) {
            salida.write(jsonArray.toString());
            salida.flush();
        } catch (IOException e) {
            System.out.println("Error al guardar en archivo JSON: " + e.getMessage());
        }
    }

    // Método auxiliar para leer desde un archivo JSON
    public static String downloadJSON(String archive) {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader entrada = new BufferedReader(new FileReader(archive))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                contenido.append(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
        return contenido.toString();
    }
}
