package Excepciones;

public class PasajeroNoExisteExcepcion extends RuntimeException {
    public PasajeroNoExisteExcepcion() {
        super("ERROR: Este pasajero no existe en la lista");
    }
}
