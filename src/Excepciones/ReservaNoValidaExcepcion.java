package Excepciones;

public class ReservaNoValidaExcepcion extends RuntimeException {
    public ReservaNoValidaExcepcion() {
        super("Error: reserva no valida");
    }
}
