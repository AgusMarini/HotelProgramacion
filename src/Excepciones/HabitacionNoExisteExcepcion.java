package Excepciones;

public class HabitacionNoExisteExcepcion extends RuntimeException {
  public HabitacionNoExisteExcepcion(String message) {
    super(message);
  }
}
