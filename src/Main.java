import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("Hotel");
        Pasajero pasajero1 = new Pasajero("Juan", "12345678", "Ciudad A", "Calle Falsa 123", TipoHabitacion.SIMPLE);
        Pasajero pasajero2 = new Pasajero("Maria", "87654321", "Ciudad B", "Avenida Siempre Viva 742", TipoHabitacion.DOBLE);
        boolean salir = false;

        while (!salir) {

            System.out.println("Menú de Gestión de Hotel");

            System.out.println("1. Realizar Check-In");
            System.out.println("2. Realizar Check-Out");
            System.out.println("3. Listar Habitaciones Ocupadas");
            System.out.println("4. Listar Habitaciones Disponibles");
            System.out.println("5. Agregar reserva del Pasajero");// solo q realizo la reserva

            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {


                case 1:
                    System.out.print("Ingrese el DNI del pasajero: ");
                    String dniCheckIn = scanner.nextLine();
                    Pasajero pasajeroCheckIn = hotel.buscarPasajeroPorDni(dniCheckIn);

                    if (pasajeroCheckIn != null) {
                        
                        Reserva reserva = hotel.buscarReservaPorDni(dniCheckIn);

                        if (reserva != null) {

                            System.out.println("El pasajero tiene una reserva en una habitación tipo: " + reserva.getTipoHabitacion());


                            Habitacion habitacionDisponible = hotel.buscarHabitacionDisponiblePorTipo(reserva.getTipoHabitacion());

                            if (habitacionDisponible != null) {
                                habitacionDisponible.realizarCheckIn(pasajeroCheckIn);
                                System.out.println("Check-In realizado correctamente en la habitación " + habitacionDisponible.getNumero());
                            } else {
                                System.out.println("No hay habitaciones disponibles del tipo reservado.");
                            }
                        } else {

                            System.out.print("Ingrese el número de habitación: ");
                            int numeroHabitacionCheckIn = scanner.nextInt();
                            scanner.nextLine();
                            Habitacion habitacionCheckIn = hotel.buscarHabitacionPorNumero(numeroHabitacionCheckIn);

                            if (habitacionCheckIn != null) {
                                habitacionCheckIn.realizarCheckIn(pasajeroCheckIn);
                                System.out.println("Check-In realizado correctamente!");
                            } else {
                                System.out.println("Habitación no encontrada.");
                            }
                        }
                    } else {
                        System.out.println("Pasajero no encontrado.");
                    }
                    break;


                case 2:

                    System.out.print("Ingrese el número de habitación para Check-Out: ");
                    int numeroHabitacionCheckOut = scanner.nextInt();
                    scanner.nextLine();
                    Habitacion habitacionCheckOut = hotel.buscarHabitacionPorNumero(numeroHabitacionCheckOut);

                    if (habitacionCheckOut != null) {

                        habitacionCheckOut.puedeOcupar();
                        System.out.println("Check-Out realizado correctamente!");


                        System.out.println("Seleccione el estado de la habitación después del Check-Out:");
                        System.out.println("1. LIMPIEZA");
                        System.out.println("2. REPARACION");
                        System.out.println("3. DESINFECCION");
                        System.out.println("4. DISPONIBLE");

                        int opcionEstado = scanner.nextInt();
                        scanner.nextLine();


                        switch (opcionEstado) {
                            case 1:
                                habitacionCheckOut.setEstado(EstadoHabitacion.LIMPIEZA);
                                System.out.println("La habitación se ha marcado como en LIMPIEZA.");
                                break;
                            case 2:
                                habitacionCheckOut.setEstado(EstadoHabitacion.REPARACION);
                                System.out.println("La habitación se ha marcado como en REPARACION.");
                                break;
                            case 3:
                                habitacionCheckOut.setEstado(EstadoHabitacion.DESINFECCION);
                                System.out.println("La habitación se ha marcado como en DESINFECCION.");
                                break;
                            case 4:
                                habitacionCheckOut.setEstado(EstadoHabitacion.DISPONIBLE);
                                System.out.println("La habitación se ha marcado como DISPONIBLE.");
                                break;
                            default:
                                System.out.println("Opción no válida. La habitación se marcará como DISPONIBLE por defecto.");
                                habitacionCheckOut.setEstado(EstadoHabitacion.DISPONIBLE);
                        }



                    } else {
                        System.out.println("Habitación no encontrada.");
                    }
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
                    // Agregar nuevo pasajero
                    System.out.print("Ingrese el nombre del pasajero: ");
                    String nombrePasajero = scanner.nextLine();
                    System.out.print("Ingrese el DNI del pasajero: ");
                    String dniPasajero = scanner.nextLine();
                    System.out.print("Ingrese el origen del pasajero: ");
                    String origenPasajero = scanner.nextLine();
                    System.out.print("Ingrese el domicilio del pasajero: ");
                    String domicilioPasajero = scanner.nextLine();

                    // Seleccionar el tipo de habitación
                    System.out.println("Seleccione el tipo de habitación para el pasajero:");
                    System.out.println("1. SIMPLE");
                    System.out.println("2. DOBLE");
                    System.out.println("3. SUITE");
                    int tipoHabitacionOpcion = scanner.nextInt();
                    scanner.nextLine();  // Consumir la nueva línea

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

// Crear el pasajero con el tipo de habitación
                    Pasajero nuevoPasajero = new Pasajero(nombrePasajero, dniPasajero, origenPasajero, domicilioPasajero, tipoHabitacion);
                    hotel.agregarPasajero(nuevoPasajero);
                    System.out.println("Pasajero agregado exitosamente.");

                    System.out.print("Ingrese la cantidad de pasajeros para la reserva: ");
                    int cantidadPasajeros = scanner.nextInt();
                    scanner.nextLine();

                    // Opción para agregar servicios adicionales
                    List<String> serviciosAdicionales = new ArrayList<>();
                    System.out.println("¿Desea agregar servicios adicionales? (Spa, Restaurante, etc.) (S/N): ");
                    String opcionServicios = scanner.nextLine().toUpperCase();

                    if (opcionServicios.equals("S")) {
                        System.out.print("Ingrese los servicios adicionales separados por comas: ");
                        String servicios = scanner.nextLine();
                        String[] serviciosArray = servicios.split(",");
                        for (String servicio : serviciosArray) {
                            serviciosAdicionales.add(servicio.trim());
                        }
                    }

                    // Realizar la reserva
                    boolean reservaExitosa = hotel.reservarHabitacion(nuevoPasajero, tipoHabitacion, cantidadPasajeros, serviciosAdicionales);
                    if (reservaExitosa) {
                        System.out.println("Reserva realizada exitosamente.");
                    } else {
                        System.out.println("No se pudo realizar la reserva. No hay habitaciones disponibles del tipo seleccionado.");
                    }
                    break;

                    case 6:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }

        scanner.close();
    }
}
