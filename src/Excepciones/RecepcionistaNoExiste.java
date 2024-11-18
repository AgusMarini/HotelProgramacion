package Excepciones;

public class RecepcionistaNoExiste extends RuntimeException {
    public RecepcionistaNoExiste() {
        super("Error: este recepcionista no existe en la lista");
    }
}
