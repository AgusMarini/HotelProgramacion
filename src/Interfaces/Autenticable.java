package Interfaces;

public interface Autenticable {
    boolean autenticar(String usuario, String contrasena);
    void cambiarContrasena(String nuevaContrasena);
}
