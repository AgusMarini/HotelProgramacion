package Excepciones;

public class HbitacionYaExisteExcepcion extends RuntimeException {
  public HbitacionYaExisteExcepcion(String message) {
    super(message);
  }
}
