import Exceptions.AccesoNoAutorizadoException;

public abstract class Usuario {
    private String nombre;
    private String dni;
    private String contrasena;

    public Usuario(String nombre, String dni,String contrasena) {
        this.nombre = nombre;
        this.dni = dni;
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }
    public boolean verificarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }
    public abstract void verificarPermisos() throws AccesoNoAutorizadoException;
    public abstract void mostrarInfo();


    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }

}

