package Excepciones;

public class HabitacionNoExisteExcepcion extends RuntimeException {
    public HabitacionNoExisteExcepcion() {
        super("Error: la habitacion no existe");
    }
}
