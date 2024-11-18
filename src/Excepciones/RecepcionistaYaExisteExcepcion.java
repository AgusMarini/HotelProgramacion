package Excepciones;

public class RecepcionistaYaExisteExcepcion extends RuntimeException {
    public RecepcionistaYaExisteExcepcion() {
        super("Error: Ya existe este nombre de usuario");
    }
}
