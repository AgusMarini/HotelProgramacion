package Clases;

import Enums.TipoUsuario;
import Interfaces.Autenticable;
import Interfaces.Jsonable;
import org.json.JSONObject;

public abstract class Usuario implements Jsonable{
    private String nombreUsuario;
    private String contrasena;
    private TipoUsuario tipoUsuario;

    public Usuario(TipoUsuario tipoUsuario, String contrasena, String nombreUsuario) {
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.nombreUsuario = nombreUsuario;
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

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombreUsuario", getNombreUsuario());
        jsonObject.put("contrasena", getContrasena());
        jsonObject.put("tipoUsuario", tipoUsuario.toString()); // Convierte el TipoUsuario a su nombre
        return jsonObject;
    }

}

