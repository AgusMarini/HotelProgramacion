package Excepciones;

public class PasajeroExistenteExcepcion extends RuntimeException {
    public PasajeroExistenteExcepcion() {
        super("Error: este pasajero ya ha sido agregado");
    }
}
