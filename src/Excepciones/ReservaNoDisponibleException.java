package Excepciones;

public class ReservaNoDisponibleException extends Exception {
    public ReservaNoDisponibleException() {
        super("Esta reserva no esta disponible");
    }
}