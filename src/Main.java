import Clases.*;
import Enums.EstadoHabitacion;
import Enums.TipoHabitacion;
import Enums.TipoUsuario;
import Excepciones.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/** ANOTACIONES  */

//IMPLEMENTAR EXCEPCION PARA EL MENU EN CASO DE PONER UN STRING EN VEZ DE UN NUMERO (no es importante)
//PONER LAS OPCIONES PARA VER LAS LISTAS DE LOS PASAJEROS, RESERVAS, HABITACIONES UTILIZANDO LOS METODOS TOSTRING
// CHEQUEAR MANEJO DE DATOS CON EL ARCHIVO JSON (QUE QUEDE TODO GUARDADO AL FINALIZAR EL PROGRAMA, CREO QUE ESTO ESTA BIEN)
// HACER TESTING DEL MENU RECEPCIONISTA Y COMPLETARLO ANTES
// AGREGAR RECAUDACION TOTAL(YA SE AGREGO SOLO HAY QUE PONER LA OPCION EN EL MENU DE ADMIN PARA MOSTAR CUANTO SE VA RECAUDANDO)
//QUEDA IMPLEMENTAR EL TRY/CATCH EN EL MAIN SOLO DE LAS OPCIONES PARA AGREGAR Y CANCELAR RESERVAS
/**IMPORTANTE VER SI FUNCIONA EL TEMA DE LAS FECHAS Y DISPONIBILIDAD, TMB QUEDA POR REVISAR QUE HACER CON EL ESTADO LIMPIEZA
 * CON ESTO ULTIMO PROBEMOS IMPLEMENTAR LO QUE HIZO AGUS DEL TEMPORARIZADOR ESE */
/** SI SE LES OCURREN OTRAS COSAS ANOTEN  POR DEBAJO !!!!!!!!!!!!*/
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Crear administrador y recepcionista predeterminados
        Administrador administrador = new Administrador("tecla", "123456", "Acceso-total");

        // Crear instancia del hotel con datos predeterminados
        Hotel hotel = new Hotel("Hotel Único", administrador);

        hotel.cargarDesdeArchivo(); //cargamos el objeto con los datos dentro del JSON

        // Menú principal
        while (true) {
            try{
                System.out.println("\n=== Sistema de Gestión Hotelera ===");
                System.out.println("1. Ingresar como Administrador");
                System.out.println("2. Ingresar como Recepcionista");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        if (autenticarAdmin(scanner, administrador)) {
                            menuAdministrador(hotel, scanner);
                        }
                        break;
                    case 2:
                        System.out.print("Ingrese su usuario: ");
                        String recepUsuario = scanner.nextLine();
                        System.out.print("Ingrese su contraseña: ");
                        String recepContrasena = scanner.nextLine();

                        if (hotel.autenticarRecepcionista(recepUsuario, recepContrasena)) {
                            System.out.println("Autenticación exitosa. Bienvenido, " + recepUsuario);
                            menuRecepcionista(hotel, scanner);
                        } else {
                            System.out.println("Usuario o contraseña incorrectos.");
                        }
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        hotel.cargarArchivo(); // Guardar datos en archivo JSON
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpia el buffer de entrada
            }

        }
    }

    // Método para autenticar admin
        private static boolean autenticarAdmin(Scanner scanner, Administrador usuario) {
        System.out.print("Ingrese su usuario: ");
        String inputUsuario = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String inputContrasena = scanner.nextLine();

        if (usuario.autenticar(inputUsuario, inputContrasena)) {
            System.out.println("Autenticación exitosa. Bienvenido/a " + usuario.getNombreUsuario());
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            return false;
        }
    }

    // Menú del Administrador
    private static void menuAdministrador(Hotel hotel, Scanner scanner) {
        int opcion = -1;
        do {
            try{
                System.out.println("\n=== Menú Administrador ===");
                System.out.println("1. Agregar habitación");
                System.out.println("2. Eliminar habitación");
                System.out.println("3. Listar habitaciones");
                System.out.println("4. Listar clientes");
                System.out.println("5. Eliminar cliente");
                System.out.println("6. Agregar recepcionista");
                System.out.println("7. Eliminar recepcionista");
                System.out.println("8. Listar recepcionistas");
                System.out.println("9. Ver recaudacion total hasta la fecha");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer


                switch (opcion) {
                    case 1:
                        try{
                            System.out.print("Ingrese número de habitación: ");
                            int numero = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese tipo de habitación (SIMPLE, DOBLE, SUITE): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            hotel.agregarHabitacion(numero, TipoHabitacion.valueOf(tipo));
                        }
                        catch (HbitacionYaExisteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        try{
                            System.out.print("Ingrese número de habitación a eliminar: ");
                            int numeroEliminar = scanner.nextInt();
                            hotel.eliminarHabitacion(numeroEliminar);
                        }
                            catch (HabitacionNoExisteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.println(hotel.listaHabitacionesToString());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.println(hotel.listaPasajerostoString());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        try{
                            System.out.print("Ingrese DNI del pasajero a eliminar: ");
                            int dniEliminar = scanner.nextInt();
                            hotel.eliminarPasajero(dniEliminar);
                        }
                        catch (PasajeroNoExisteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            System.out.print("Ingrese nombre usurio del recepcionista: ");
                            String nombreUsuario = scanner.nextLine();
                            System.out.print("Ingrese contraseña del recepcionista: ");
                            String contrasena = scanner.nextLine();
                            System.out.print("Ingrese horario de trabajo del recepcionista (turno): ");
                            int horarioTrabajo = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            Recepcionista nuevoRecepcionista = new Recepcionista(contrasena,nombreUsuario, horarioTrabajo);
                            hotel.agregarRecepcionista(nuevoRecepcionista);
                        } catch (RecepcionistaYaExisteExcepcion e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
                        try {
                            System.out.print("Ingrese el nombre de usuario del recepcionista a eliminar (nombre + apellido): ");
                            String nombreCompleto = scanner.nextLine();
                            hotel.eliminarRecepcionista(nombreCompleto);
                        }
                        catch (RecepcionistaNoExiste e){
                            System.out.println(e.getMessage());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }

                        break;
                    case 8:
                        try {
                            System.out.println(hotel.listaRecepcionistasToString());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }

                        break;
                    case 9:
                        System.out.println("\nLa recaudacion total actual es de $" + hotel.getRecaudacionTotal());
                        break;
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpia el buffer de entrada
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Menú del Recepcionista
    private static void menuRecepcionista(Hotel hotel, Scanner scanner) {
        int opcion = -1;
        do {
            try {
                System.out.println("\n=== Menú Recepcionista ===");
                System.out.println("1. Realizar check-in");
                System.out.println("2. Realizar check-out");
                System.out.println("3. Agregar cliente(antes de realizar reserva)");
                System.out.println("4. Ver habitaciones disponibles por fecha");
                System.out.println("5. Ver habitaciones disponibles ahora");
                System.out.println("6. Ver todas las habitaciones");
                System.out.println("7. Agregar reserva");
                System.out.println("8. Cancelar reserva");
                System.out.println("9. Ver todas las reservas(se eliminan las viejas dps de realizar el checkout)");
                System.out.println("10. Ver historial de reservas de un pasajero");
                System.out.println("0. Volver al menú principal");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        try{
                            System.out.print("Ingrese número de habitación: ");
                            int numeroCheckIn = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese DNI del pasajero: ");
                            int dniCheckIn = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese fecha de check-in (YYYY-MM-DD): ");
                            LocalDate hoy = LocalDate.parse(scanner.next());
                            hotel.realizarCheckIn(numeroCheckIn, dniCheckIn, hoy);
                        }
                        catch (HabitacionNoExisteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        catch (ReservaNoValidaExcepcion excepcion){
                            System.out.println(excepcion.getMessage());
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Por favor, intente nuevamente.");
                        }
                        break;
                    case 2:
                        try{
                            System.out.print("Ingrese número de habitación: ");
                            int numeroCheckOut = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese DNI del pasajero: ");
                            int dniCheckOut = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese fecha de salida (YYYY-MM-DD): ");
                            LocalDate salida = LocalDate.parse(scanner.next());
                            hotel.realizarCheckOut(numeroCheckOut, dniCheckOut, salida);
                        }
                        catch (HabitacionNoExisteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Por favor, intente nuevamente.");
                        }

                        break;
                    case 3:
                        try{
                            System.out.print("Ingrese nombre del pasajero: ");
                            String nombre = scanner.nextLine();
                            System.out.print("Ingrese apellido del pasajero: ");
                            String apellido = scanner.nextLine();
                            System.out.print("Ingrese DNI del pasajero: ");
                            int dni = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese origen del pasajero: ");
                            String origen = scanner.nextLine();
                            System.out.print("Ingrese domicilio del pasajero: ");
                            String domicilio = scanner.nextLine();

                            // Crear y agregar el pasajero al hotel

                            hotel.agregarPasajero(nombre, apellido, dni, origen, domicilio);
                            hotel.cargarArchivo(); // Guardar cambios

                            System.out.println("Pasajero agregado correctamente.");
                        }
                        catch (PasajeroExistenteExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.print("Ingrese fecha de inicio (YYYY-MM-DD): ");
                            LocalDate inicio = LocalDate.parse(scanner.next());
                            System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
                            LocalDate fin = LocalDate.parse(scanner.next());
                            System.out.println(hotel.listarHabitacionesDisponiblesPorFecha(inicio, fin));
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Por favor, intente nuevamente.");
                        }
                        break;
                    case 5:
                        try{
                            System.out.println(hotel.listarHabitacionesDisponiblesAhora());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        try {
                            System.out.println(hotel.listaHabitacionesToString());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
                        try{
                            System.out.print("Ingrese DNI del pasajero: ");
                            int dniReserva = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese número de habitación: ");
                            int numeroReserva = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese fecha de inicio (YYYY-MM-DD): ");
                            LocalDate inicio = LocalDate.parse(scanner.next());
                            System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
                            LocalDate fin = LocalDate.parse(scanner.next());
                            hotel.agregarReserva(dniReserva, numeroReserva, inicio, fin);
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Por favor, intente nuevamente.");
                        }

                        break;
                    case 8:
                        try {
                            System.out.print("Ingrese DNI del pasajero: ");
                            int dniCancel = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese número de habitación: ");
                            int numeroCancel = scanner.nextInt();
                            scanner.nextLine(); // Limpiar buffer
                            System.out.print("Ingrese fecha de inicio (YYYY-MM-DD): ");
                            LocalDate inicioCancel = LocalDate.parse(scanner.next());
                            System.out.print("Ingrese fecha de fin (YYYY-MM-DD): ");
                            LocalDate finCancel = LocalDate.parse(scanner.next());
                            hotel.cancelarReserva(dniCancel, numeroCancel, inicioCancel, finCancel);
                        }
                        catch (DateTimeParseException e) {
                            System.out.println("Formato de fecha inválido. Por favor, intente nuevamente.");
                        }

                        break;
                    case 9:
                        try{
                            System.out.println(hotel.listaReservasToString());
                        }
                        catch (ListaVaciaExcepcion e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 10:
                        System.out.print("Ingrese DNI del pasajero: ");
                        int dniHistorial = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer
                        System.out.println(hotel.historialReservasToStringMedianteDni(dniHistorial));
                        break;
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpia el buffer de entrada
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }
}

