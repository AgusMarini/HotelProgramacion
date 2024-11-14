package Exceptions;

public class PagoNoValidoException extends RuntimeException {
    public PagoNoValidoException(String message) {
        super(message);
    }
}
