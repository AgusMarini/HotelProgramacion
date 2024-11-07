import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("Hotel");
        Administrador admin = new Administrador("NombreAdmin", "12345678");


        Pasajero pasajero1 = new Pasajero("Juan", "12345678", "Ciudad A", "Calle Falsa 123", TipoHabitacion.SIMPLE);
        Pasajero pasajero2 = new Pasajero("Maria", "87654321", "Ciudad B", "Avenida Siempre Viva 742", TipoHabitacion.DOBLE);
        boolean salir = false;
        while (!salir) {
            System.out.println("Seleccione su rol:");
            System.out.println("1. Administrador");
            System.out.println("2. Recepcionista");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int rol = scanner.nextInt();
            scanner.nextLine();

            switch (rol) {
                case 1:
                    menuAdministrador(scanner, hotel, admin);
                    break;
                case 2:
                    menuRecepcionista(scanner, hotel);
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
            System.out.println("\nMenú de Gestión de Hotel como Administrador");
            System.out.println("1. Agregar habitación");
            System.out.println("2. Eliminar habitación");
            System.out.println("3. Listar habitaciones");
            System.out.println("4. Modificar estado de habitación");
            System.out.println("5. Salir al menú principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número de la nueva habitación: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese el tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                    String tipo = scanner.nextLine().toUpperCase();
                    TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipo);
                    Habitacion habitacion = new Habitacion(numero, tipoHabitacion);
                    admin.agregarHabitacion(hotel, habitacion);
                    break;

                case 2:
                    System.out.print("Ingrese el número de la habitación a eliminar: ");
                    int numeroEliminar = scanner.nextInt();
                    scanner.nextLine();
                    hotel.eliminarHabitacion(numeroEliminar);
                    System.out.println("Habitación eliminada exitosamente.");
                    break;
                case 3:
                    System.out.println("Lista de habitaciones:");
                    for (Habitacion hab : hotel.listarHabitaciones()) {
                        System.out.println("Habitación " + hab.getNumero() + " - Tipo: " + hab.getTipo() + " - Estado: " + hab.getEstado());
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el número de la habitación a modificar: ");
                    int numeroHabitacion = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Seleccione el nuevo estado:");
                    System.out.println("1. LIMPIEZA");
                    System.out.println("2. REPARACION");
                    System.out.println("3. DESINFECCION");
                    System.out.println("4. DISPONIBLE");
                    int estadoOpcion = scanner.nextInt();
                    scanner.nextLine();

                    EstadoHabitacion nuevoEstado;
                    switch (estadoOpcion) {
                        case 1:
                            nuevoEstado = EstadoHabitacion.LIMPIEZA;
                            break;
                        case 2:
                            nuevoEstado = EstadoHabitacion.REPARACION;
                            break;
                        case 3:
                            nuevoEstado = EstadoHabitacion.DESINFECCION;
                            break;
                        case 4:
                            nuevoEstado = EstadoHabitacion.DISPONIBLE;
                            break;
                        default:
                            System.out.println("Opción no válida. Se mantendrá el estado actual.");
                            return;
                    }

                    admin.modificarHabitacion(numeroHabitacion, nuevoEstado);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
            }
        }


    private static void menuRecepcionista(Scanner scanner, Hotel hotel) {
        boolean salir = false;
        while (!salir) {
            System.out.println("Menú de Gestión de Hotel como Recepcionista");

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
                    Recepcionista.realizarCheckIn(dniCheckIn);
                    break;

                case 2:
                    System.out.print("Ingrese el número de habitación para Check-Out: ");
                    int numeroHabitacionCheckOut = scanner.nextInt();
                    scanner.nextLine();
                    Recepcionista.realizarCheckOut(numeroHabitacionCheckOut);
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
                    System.out.println("Pasajero agregado exitosamente.");

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

                            System.out.println("Seleccione el horario para " + nombreServicio + ": ");
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
                                    System.out.println("Reserva realizada para " + nombreServicio + " en el horario " + horario);
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
                        System.out.println("Reserva de habitación realizada exitosamente.");
                    } else {
                        System.out.println("No se pudo realizar la reserva de la habitación. No hay habitaciones disponibles del tipo seleccionado.");
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
