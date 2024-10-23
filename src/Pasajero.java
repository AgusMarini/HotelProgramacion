public class Pasajero extends Usuario{
private String origen;
private String domicilio;
private TipoHabitacion tipo;

    public Pasajero(String nombre, String dni,String origen, String domicilio,TipoHabitacion tipo) {
        super(nombre, dni);
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
    public void mostrarInfo() {
        System.out.println("Pasajero: "+getNombre()+"Origen: "+origen);

    }
}
