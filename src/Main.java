import Exceptions.HabitacionNoDisponibleException;
import Exceptions.FechaInvalidaException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("Hotel Las Palmas");
        Administrador admin = new Administrador("Administrador", "12345678", "admin123");
        Recepcionista recepcionista = new Recepcionista("Recepcionista", "87654321", "recep456");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Seleccione su rol ---");
            System.out.println("1. Administrador");
            System.out.println("2. Recepcionista");
            System.out.println("3. Cancelar Reserva");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int rol = scanner.nextInt();
            scanner.nextLine();

            switch (rol) {
                case 1:
                    if (autenticarUsuario(scanner, admin)) {
                        menuAdministrador(scanner, hotel, admin);
                    } else {
                        System.out.println("Contraseña incorrecta. Acceso denegado.");
                    }
                    break;
                case 2:
                    if (autenticarUsuario(scanner, recepcionista)) {
                        menuRecepcionista(scanner, hotel, recepcionista);
                    } else {
                        System.out.println("Contraseña incorrecta. Acceso denegado.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el DNI del pasajero para cancelar la reserva: ");
                    String dniPasajero = scanner.nextLine();
                    if (hotel.cancelarReserva(dniPasajero)) {
                        System.out.println("Reserva cancelada exitosamente.");
                    } else {
                        System.out.println("No se pudo cancelar la reserva. Verifique el DNI proporcionado.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static boolean autenticarUsuario(Scanner scanner, Autenticable usuario) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();
        return usuario.autenticar(nombreUsuario, contrasena);
    }

    private static void menuAdministrador(Scanner scanner, Hotel hotel, Administrador admin) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú de Gestión de Hotel (Administrador) ---");
            System.out.println("1. Agregar habitación");
            System.out.println("2. Eliminar habitación");
            System.out.println("3. Listar habitaciones");
            System.out.println("4. Salir al menú principal");
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

                    System.out.print("Ingrese el costo de la habitación: ");
                    double costo = scanner.nextDouble();
                    scanner.nextLine();

                    Habitacion habitacion = new Habitacion(numero, tipoHabitacion, costo);
                    admin.agregarHabitacion(hotel, habitacion);
                    System.out.println("Habitación agregada exitosamente.");
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
                    for (Habitacion hab : hotel.obtenerHabitacionesDisponibles()) {
                        System.out.println("Habitación " + hab.getNumero() + " - Tipo: " + hab.getTipo() + " - Estado: " + hab.getEstado() + " - Costo: " + hab.getCosto());
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
            System.out.println("\n--- Menú de Gestión de Hotel (Recepcionista) ---");
            System.out.println("1. Realizar Check-In");
            System.out.println("2. Realizar Check-Out");
            System.out.println("3. Listar Habitaciones Ocupadas");
            System.out.println("4. Listar Habitaciones Disponibles");
            System.out.println("5. Agregar reserva del Pasajero");
            System.out.println("6. Controlar Disponibilidad de Habitaciones");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    realizarCheckIn(scanner, hotel, recepcionista);
                    break;
                case 2:
                    realizarCheckOut(scanner, recepcionista);
                    break;
                case 3:
                    listarHabitacionesNoDisponibles(hotel);
                    break;
                case 4:
                    listarHabitacionesDisponibles(hotel);
                    break;
                case 5:
                    agregarReserva(scanner, hotel);
                    break;
                case 6:
                    recepcionista.controlarDisponibilidad();
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

    private static void realizarCheckIn(Scanner scanner, Hotel hotel, Recepcionista recepcionista) {
        System.out.print("Ingrese el DNI del pasajero: ");
        String dniCheckIn = scanner.nextLine();
        Pasajero pasajero = hotel.buscarPasajeroPorDNI(dniCheckIn);
        if (pasajero == null) {
            System.out.println("No se encontró un pasajero con el DNI proporcionado.");
            return;
        }
        Habitacion habitacionCheckIn = Hotel.buscarHabitacionPorTipo(pasajero.getTipo());
        if (habitacionCheckIn != null) {
            try {
                recepcionista.realizarCheckIn(habitacionCheckIn, pasajero);
                System.out.println("Check-In realizado con éxito en la habitación " + habitacionCheckIn.getNumero());
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No hay habitaciones disponibles del tipo " + pasajero.getTipo() + ".");
        }
    }

    private static void realizarCheckOut(Scanner scanner, Recepcionista recepcionista) {
        System.out.print("Ingrese el número de habitación para Check-Out: ");
        int numeroHabitacionCheckOut = scanner.nextInt();
        scanner.nextLine();
        Habitacion habitacionCheckOut = Hotel.buscarHabitacionPorNumero(numeroHabitacionCheckOut);
        if (habitacionCheckOut != null) {
            try {
                recepcionista.realizarCheckOut(habitacionCheckOut);
                System.out.println("Check-Out realizado con éxito.");
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No se encontró una habitación con el número proporcionado.");
        }
    }

    private static void listarHabitacionesNoDisponibles(Hotel hotel) {
        System.out.println("Habitaciones No Disponibles:");
        for (Habitacion hab : hotel.obtenerHabitacionesDisponibles()) {
            if (!hab.estaDisponible()) {
                System.out.println("Habitación " + hab.getNumero() + " - Estado: " + hab.getEstado());
            }
        }
    }

    private static void listarHabitacionesDisponibles(Hotel hotel) {
        System.out.println("Habitaciones Disponibles:");
        for (Habitacion hab : hotel.obtenerHabitacionesDisponibles()) {
            System.out.println("Habitación " + hab.getNumero() + " - Tipo: " + hab.getTipo());
        }
    }

    private static void agregarReserva(Scanner scanner, Hotel hotel) {
        System.out.print("Ingrese el nombre del pasajero: ");
        String nombrePasajero = scanner.nextLine();
        System.out.print("Ingrese el DNI del pasajero: ");
        String dniPasajero = scanner.nextLine();
        System.out.print("Ingrese el origen del pasajero: ");
        String origenPasajero = scanner.nextLine();
        System.out.print("Ingrese el domicilio del pasajero: ");
        String domicilioPasajero = scanner.nextLine();

        // Seleccionar el tipo de habitación
        TipoHabitacion tipoHabitacion = null;
        while (tipoHabitacion == null) {
            System.out.println("Seleccione el tipo de habitación para el pasajero:");
            System.out.println("1. SIMPLE");
            System.out.println("2. DOBLE");
            System.out.println("3. SUITE");
            int tipoHabitacionOpcion = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        // Solicitar la cantidad de personas
        System.out.print("Ingrese la cantidad de personas que se quedarán: ");
        int cantidadPersonas = scanner.nextInt();
        scanner.nextLine();

        // Solicitar las fechas de la reserva
        System.out.print("Ingrese la fecha de inicio de la reserva (YYYY-MM-DD): ");
        LocalDate fechaInicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Ingrese la fecha de fin de la reserva (YYYY-MM-DD): ");
        LocalDate fechaFin = LocalDate.parse(scanner.nextLine());
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        // Calcular el costo total
        double costoPorDia = hotel.obtenerCostoPorTipoHabitacion(tipoHabitacion);
        double costoTotal = costoPorDia * dias;
        System.out.println("El costo total de la estadía es: $" + costoTotal);

        // Crear el pasajero y la reserva sin asignar la habitación
        Pasajero nuevoPasajero = new Pasajero(nombrePasajero, dniPasajero, origenPasajero, domicilioPasajero, tipoHabitacion);
        hotel.registrarPasajero(nuevoPasajero);
        List<String> serviciosAdicionales = new ArrayList<>();

        // Aquí se crea la reserva sin asignar una habitación específica
        hotel.crearReserva(nuevoPasajero, tipoHabitacion, cantidadPersonas, serviciosAdicionales, fechaInicio, fechaFin);

        System.out.println("Reserva creada exitosamente. La habitación se asignará al realizar el Check-In.");
    }

}
