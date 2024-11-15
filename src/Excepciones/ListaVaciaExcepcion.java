package Excepciones;

public class ListaVaciaExcepcion extends RuntimeException {
    public ListaVaciaExcepcion() {
        super("Error: esta lista esta vacia");
    }
}
