public class Administrador extends Usuario {

    public Administrador(String nombre, String dni) {
        super(nombre, dni);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Administrador: " + getNombre());
    }
}

