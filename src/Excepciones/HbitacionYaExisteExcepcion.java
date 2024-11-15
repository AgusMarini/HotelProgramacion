package Excepciones;

public class HbitacionYaExisteExcepcion extends RuntimeException {
    public HbitacionYaExisteExcepcion() {
        super("Esta habitacion ya existe");
    }
}
