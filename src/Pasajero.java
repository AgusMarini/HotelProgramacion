import Exceptions.AccesoNoAutorizadoException;

public class Pasajero extends Usuario{
private String origen;
private String domicilio;
private TipoHabitacion tipo;


    public Pasajero(String nombre, String dni,String origen, String domicilio,TipoHabitacion tipo) {
        super(nombre, dni,"");
        this.origen=origen;
        this.domicilio=domicilio;
        this.tipo=tipo;

    }



    public String getOrigen() {
        return origen;
    }

    public String getDomicilio() {
        return domicilio;
    }


    public TipoHabitacion getTipo() {
        return tipo;
    }

    @Override
    public void verificarPermisos() throws AccesoNoAutorizadoException {

    }

    @Override
    public void mostrarInfo() {
        System.out.println("Pasajero: "+getNombre()+"Origen: "+origen);

    }
    @Override
    public String toString() {
        return "Pasajero{" +
                "nombre='" + getNombre() + '\'' +
                ", dni='" + getDni() + '\'' +
                ", origen='" + origen + '\'' +
                ", domicilio='" + domicilio + '\'' +

                '}';
    }

}
