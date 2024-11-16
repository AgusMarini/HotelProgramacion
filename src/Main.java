import Clases.*;
import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Enums.TipoUsuario;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.HabitacionNoExisteExcepcion;
import Excepciones.HbitacionYaExisteExcepcion;
import Excepciones.ListaVaciaExcepcion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Administrador admin = new Administrador("Juan", "Maniago", 43456838, "123456");


        Recepcionista recepcionista = new Recepcionista("Tecla", "Farias", 44444444, );
        Hotel hotel = new Hotel("Hotel Maravilla", admin, recepcionista);


        boolean salir = false;
        while (!salir) {
            System.out.println("Seleccione su rol:");
            System.out.println("1. Clases.Administrador");
            System.out.println("2. Clases.Recepcionista");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int rol = scanner.nextInt();
            scanner.nextLine();

            switch (rol) {
                case 1:
                    menuAdministrador(scanner, hotel, admin);
                    break;
                case 2:
                    menuRecepcionista(scanner, hotel, recepcionista);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void menuAdministrador(Scanner scanner, Hotel hotel, Administrador admin) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú de Gestión de Clases.Hotel como Clases.Administrador");
            System.out.println("1. Agregar habitación");
            System.out.println("2. Eliminar habitación");
            System.out.println("3. Listar habitaciones");
            System.out.println("4. Salir al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        System.out.print("Ingrese el número de la nueva habitación: ");
                        int numero = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Ingrese el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                        String tipo = scanner.nextLine().toUpperCase();
                        TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipo);
                        hotel.agregarHabitacion(numero, tipoHabitacion);
                    }
                    catch (HbitacionYaExisteExcepcion e){
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 2:
                    try {
                        System.out.print("Ingrese el número de la habitación a eliminar: ");
                        int numeroEliminar = scanner.nextInt();
                        scanner.nextLine();
                        hotel.eliminarHabitacion(numeroEliminar);
                    } catch (HabitacionNoExisteExcepcion e) {
                        System.out.println(e.getMessage());
                    }

                    // Usar método del administrador
                    break;
                case 3:
                    try{
                        System.out.println("Lista de habitaciones:\n" + hotel.listaHabitacionesToString());
                    } catch (ListaVaciaExcepcion e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

    private static void menuRecepcionista(Scanner scanner, Hotel hotel, Recepcionista recepcionista) {
        boolean salir = false;
        while (!salir) {
            System.out.println("Menú de Gestión de Clases.Hotel como Clases.Recepcionista");
            System.out.println("1. Realizar Check-In");
            System.out.println("2. Realizar Check-Out");
            System.out.println("3. Listar Habitaciones Ocupadas");
            System.out.println("4. Listar Habitaciones Disponibles");
            System.out.println("5. Agregar reserva del Clases.Pasajero");
            System.out.println("6. Controlar Disponibilidad de Habitaciones");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el DNI del pasajero: ");
                    String dniCheckIn = scanner.nextLine();

                    // Usar método del recepcionista
                    break;
                case 2:
                    System.out.print("Ingrese el número de habitación para Check-Out: ");
                    int numeroHabitacionCheckOut = scanner.nextInt();
                    scanner.nextLine();
                    recepcionista.realizarCheckOut(numeroHabitacionCheckOut); // Usar método del recepcionista
                    break;
                case 3:
                    System.out.println("Habitaciones No Disponibles:");
                    for (Habitacion hab : hotel.listarHabitacionesNoDisponibles()) {
                        System.out.println("Habitación " + hab.getNumero() + " no está disponible (" + hab.getEstado() + ").");
                    }
                    break;
                case 4:
                    System.out.println("Habitaciones Disponibles:");
                    for (Habitacion hab : hotel.listarHabitacionesDisponibles()) {
                        System.out.println("Habitación " + hab.getNumero() + " - Tipo: " + hab.getTipo());
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el nombre del pasajero: ");
                    String nombrePasajero = scanner.nextLine();
                    System.out.print("Ingrese el DNI del pasajero: ");
                    String dniPasajero = scanner.nextLine();
                    System.out.print("Ingrese el origen del pasajero: ");
                    String origenPasajero = scanner.nextLine();
                    System.out.print("Ingrese el domicilio del pasajero: ");
                    String domicilioPasajero = scanner.nextLine();

                    System.out.println("Seleccione el tipo de habitación para el pasajero:");
                    System.out.println("1. SIMPLE");
                    System.out.println("2. DOBLE");
                    System.out.println("3. SUITE");
                    int tipoHabitacionOpcion = scanner.nextInt();
                    scanner.nextLine();

                    TipoHabitacion tipoHabitacion;
                    switch (tipoHabitacionOpcion) {
                        case 1:
                            tipoHabitacion = TipoHabitacion.SIMPLE;
                            break;
                        case 2:
                            tipoHabitacion = TipoHabitacion.DOBLE;
                            break;
                        case 3:
                            tipoHabitacion = TipoHabitacion.SUITE;
                            break;
                        default:
                            System.out.println("Opción no válida. Se asignará el tipo SIMPLE por defecto.");
                            tipoHabitacion = TipoHabitacion.SIMPLE;
                    }

                    Pasajero nuevoPasajero = new Pasajero(nombrePasajero, dniPasajero, origenPasajero, domicilioPasajero, tipoHabitacion);
                    hotel.agregarPasajero(nuevoPasajero);
                    System.out.println("Clases.Pasajero agregado exitosamente.");

                    System.out.print("Ingrese la cantidad de pasajeros para la reserva: ");
                    int cantidadPasajeros = scanner.nextInt();
                    scanner.nextLine();

                    List<String> serviciosAdicionales = new ArrayList<>();
                    System.out.println("¿Desea agregar servicios adicionales? (S/N): ");
                    String opcionServicios = scanner.nextLine().toUpperCase();

                    if (opcionServicios.equals("S")) {
                        boolean agregarMasServicios = true;
                        while (agregarMasServicios) {
                            System.out.println("Seleccione el servicio adicional que desea reservar:");
                            System.out.println("1. Spa");
                            System.out.println("2. Restaurante");
                            int opcionServicio = scanner.nextInt();
                            scanner.nextLine();

                            String nombreServicio = opcionServicio == 1 ? "Spa" : "Restaurante";
                            ServicioAdicional servicio = hotel.buscarServicio(nombreServicio);
                            if (servicio != null) {
                                List<String> horarios = servicio.getHorariosDisponibles();
                                for (int i = 0; i < horarios.size(); i++) {
                                    System.out.println((i + 1) + ". " + horarios.get(i));
                                }
                                int horarioSeleccionado = scanner.nextInt() - 1;
                                scanner.nextLine();
                                if (horarioSeleccionado >= 0 && horarioSeleccionado < horarios.size()) {
                                    String horario = horarios.get(horarioSeleccionado);
                                    serviciosAdicionales.add(nombreServicio + " a las " + horario);
                                    servicio.reservarTurno(horario);
                                    System.out.println("Clases.Reserva realizada para " + nombreServicio + " en el horario " + horario);
                                } else {
                                    System.out.println("Horario no válido.");
                                }
                            } else {
                                System.out.println("Servicio no disponible.");
                            }

                            System.out.println("¿Desea agregar otro servicio adicional? (S/N): ");
                            String respuesta = scanner.nextLine().toUpperCase();
                            agregarMasServicios = respuesta.equals("S");
                        }
                    }

                    boolean reservaExitosa = hotel.reservarHabitacion(nuevoPasajero, tipoHabitacion, cantidadPasajeros, serviciosAdicionales);
                    if (reservaExitosa) {
                        System.out.println("Clases.Reserva de habitación realizada exitosamente.");
                    } else {
                        System.out.println("No se pudo realizar la reserva de la habitación. No hay habitaciones disponibles del tipo seleccionado.");
                    }
                    break;
                case 6:
                    recepcionista.controlarDisponibilidad();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }
    // CORREGIR FUNCION FROMJSON
    /*
    public static Hotel fromJson() throws JSONException {
        String contenidoLeido = JSONUtiles.downloadJSON("Hotel");
        JSONObject hotelJSON = new JSONObject(contenidoLeido);
        String nombreHotel = hotelJSON.getString("nombre");

        // creamos la instacia del hotel que mas tarde vamos a retornar
        Hotel mihotel = new Hotel();

        JSONArray habitacionesJSON = hotelJSON.getJSONArray("habitaciones");
        JSONObject habitacionJSON;

        for (int i = 0; i < habitacionesJSON.length(); i++) {
            // Obtener el objeto JSON de cada habitación
            habitacionJSON = habitacionesJSON.getJSONObject(i);

            // Extraer los valores de cada campo
            int numero = habitacionJSON.getInt("numero");

            // Convertir el tipo y estado de la habitación a partir de las cadenas en el JSON
            TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(habitacionJSON.getString("tipoHabitacion"));
            EstadoHabitacion estadoHabitacion = EstadoHabitacion.valueOf(habitacionJSON.getString("estadoHabitacion"));

            mihotel.crearHabitacionNueva(numero, tipoHabitacion, estadoHabitacion);
        }

        JSONArray pasajerosJSON = hotelJSON.getJSONArray("pasajeros");
        JSONObject pasajeroJSON;

        for (int i = 0; i < pasajerosJSON.length(); i++) {
            // Obtener el objeto JSON de cada pasajero
            pasajeroJSON = pasajerosJSON.getJSONObject(i);

            // Extraer los valores de cada campo
            int id = pasajeroJSON.getInt("id");
            String nombre = pasajeroJSON.getString("nombre");
            String apellido = pasajeroJSON.getString("apellido");
            String dni = pasajeroJSON.getString("dni");
            String origen = pasajeroJSON.getString("origen");
            String domicilioOrigen = pasajeroJSON.getString("domicilioOrigen");

            // Crear la instancia de Pasajero con los valores obtenidos
            Pasajero pasajero = new Pasajero(nombre, apellido, dni, origen, domicilioOrigen);
            mihotel.getPasajeros().agregar(pasajero.getId(), pasajero);
        }

        JSONArray reservasJSON = hotelJSON.getJSONArray("reservas");
        JSONObject reservas_Habitacion;
        JSONArray reservasJSONaux;
        JSONObject reserva;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < reservasJSON.length(); i++) {
            reservas_Habitacion = reservasJSON.getJSONObject(i);
            reservasJSONaux = reservas_Habitacion.getJSONArray("reservasAsignadas");

            for (int o = 0; o < reservasJSONaux.length(); o++) {
                reserva = reservasJSONaux.getJSONObject(o);
                String descripcion = reserva.getString("descripcion");
                EstadoReserva estado = EstadoReserva.valueOf(reserva.getString("estado"));
                LocalDate fechaInicio = LocalDate.parse(reserva.getString("fechaInicio"), formatter);
                LocalDate fechaFin = LocalDate.parse(reserva.getString("fechaFin"), formatter);
                int numeroHabitacion = reserva.getInt("numeroHabitacion");
                int idPasajero = reserva.getInt("idPasajero");

                if (idPasajero != 6) {
                    mihotel.generarReserva(numeroHabitacion, idPasajero, fechaInicio, fechaFin, descripcion);
                } else {
                    mihotel.generarReservaMantenimiento(numeroHabitacion, idPasajero, fechaInicio, fechaFin, descripcion);
                }
            }
        }

        JSONArray usuariosJSON = hotelJSON.getJSONArray("usuarios");
        JSONObject usuarioLeido;
        for (int i = 0; i < usuariosJSON.length(); i++) {
            usuarioLeido = usuariosJSON.getJSONObject(i);

            // Verificamos el tipo de usuario para decidir si es ADMINISTRADOR o CONSERJE
            String tipoUsuarioStr = usuarioLeido.getString("tipoUsuario");
            TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioStr);  // Convierte el string en el enum correspondiente

            if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
                // Si es Administrador, deserializamos también la contraseña
                String dni = usuarioLeido.getString("dni");
                String nombre = usuarioLeido.getString("nombre");
                String contraseña = usuarioLeido.getString("contraseña");
                Administrador admin = new Administrador(dni, nombre, contraseña);
                mihotel.getUsuarios().agregar(admin.getId(), admin);
            } else if (tipoUsuario == TipoUsuario.CONSERJE) {
                // Si es Conserje, solo necesitamos dni y nombre
                String dni = usuarioLeido.getString("dni");
                String nombre = usuarioLeido.getString("nombre");
                Conserje conserje = new Conserje(dni, nombre);
                mihotel.getUsuarios().agregar(conserje.getId(), conserje);
            }
        }
        return mihotel;
    }

     */
}
