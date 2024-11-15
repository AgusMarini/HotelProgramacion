package Clases;

import Enums.TipoUsuario;
import Interfaces.Jsonable;
import org.json.JSONObject;

public abstract class Usuario extends Persona{

    private TipoUsuario tipoUsuario;

    public Usuario(String nombre, String apellido, int dni, TipoUsuario tipoUsuario) {
        super(nombre, apellido, dni);
        this.tipoUsuario = tipoUsuario;
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
        jsonObject.put("tipoUsuario", tipoUsuario.toString()); // Convierte el TipoUsuario a su nombre
        return jsonObject;
    }

}

