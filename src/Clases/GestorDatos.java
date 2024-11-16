package Clases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class GestorDatos {
    public static void guardarDatos(List<Habitacion> habitaciones, List<Reserva> reservas, List<Pasajero> pasajeros, String archivo) {
        JSONObject json = new JSONObject();

        // Guardar habitaciones
        JSONArray habitacionesJson = new JSONArray();
        for (Habitacion habitacion : habitaciones) {
            habitacionesJson.put(habitacion.toJson());
        }
        json.put("habitaciones", habitacionesJson);

        // Guardar reservas
        JSONArray reservasJson = new JSONArray();
        for (Reserva reserva : reservas) {
            reservasJson.put(reserva.toJson());
        }
        json.put("reservas", reservasJson);

        // Guardar pasajeros
        JSONArray pasajerosJson = new JSONArray();
        for (Pasajero pasajero : pasajeros) {
            pasajerosJson.put(pasajero.toJson());
        }
        json.put("pasajeros", pasajerosJson);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public static void cargarDatos(String archivo, List<Habitacion> habitaciones, List<Reserva> reservas, List<Pasajero> pasajeros) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea);
            }

            JSONObject json = new JSONObject(contenido.toString());

            // Cargar habitaciones
            JSONArray habitacionesJson = json.getJSONArray("habitaciones");
            for (int i = 0; i < habitacionesJson.length(); i++) {
                habitaciones.add(Habitacion.fromJson(habitacionesJson.getJSONObject(i)));
            }

            // Cargar reservas
            JSONArray reservasJson = json.getJSONArray("reservas");
            for (int i = 0; i < reservasJson.length(); i++) {
                reservas.add(Reserva.fromJson(reservasJson.getJSONObject(i)));
            }

            // Cargar pasajeros
            JSONArray pasajerosJson = json.getJSONArray("pasajeros");
            for (int i = 0; i < pasajerosJson.length(); i++) {
                pasajeros.add(Pasajero.fromJson(pasajerosJson.getJSONObject(i)));
            }

        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }
}
