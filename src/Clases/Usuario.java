package Clases;

import Enums.TipoUsuario;
import Interfaces.Autenticable;
import Interfaces.Jsonable;
import org.json.JSONObject;

public abstract class Usuario extends Persona {
    private String nombreUsuario;
    private String contrasena;
    private TipoUsuario tipoUsuario;

    public Usuario(String nombre, String apellido, int dni, TipoUsuario tipoUsuario, String contrasena) {
        super(nombre, apellido, dni);
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.nombreUsuario = nombre + apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dni", getDni());
        jsonObject.put("nombre", getNombre());
        jsonObject.put("apellido", getApellido());
        jsonObject.put("nombreUsuario", getNombreUsuario());
        jsonObject.put("contrasena", getContrasena());
        jsonObject.put("tipoUsuario", tipoUsuario.toString()); // Convierte el TipoUsuario a su nombre
        return jsonObject;
    }

}

