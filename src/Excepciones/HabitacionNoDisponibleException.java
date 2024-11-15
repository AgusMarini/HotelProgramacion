package Excepciones;

public class HabitacionNoDisponibleException extends Exception {
    public HabitacionNoDisponibleException() {
        super("Esta habitacion no esta disponible");
    }
}