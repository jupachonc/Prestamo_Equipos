package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends RecursiveTreeObject<Usuario> {

    private String nombre;
    private String apellido;
    private int documento;
    private String email;
    private String contrasena;
    private int type;
    
    /*
    Types
    0 User
    1 Admin
    2 SuperUser
    */

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        List nc = new ArrayList();
        for (String palabra : str.split(" ")) {
            nc.add(palabra.substring(0, 1).toUpperCase() + palabra.substring(1, palabra.length()).toLowerCase());
        }
        return String.join(" ", nc);
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = ucFirst(nombre);
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = ucFirst(apellido);
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Usuario(String nombre, String apellido, int documento, String email, String contrasena, int type) {
        this.nombre = ucFirst(nombre);
        this.apellido = ucFirst(apellido);
        this.documento = documento;
        this.email = email.toLowerCase();
        this.contrasena = contrasena;
        this.type = type;
    }

}
