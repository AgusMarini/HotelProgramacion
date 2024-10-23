import java.util.ArrayList;
import java.util.List;

public class RegistroHotel<T> {
    private List<T> elementos;

    public RegistroHotel() {
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(T elemento) {
        elementos.add(elemento);
    }

    public List<T> obtenerElementos() {
        return elementos;
    }
}
