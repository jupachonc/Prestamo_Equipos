package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import Entidad.Usuario;

public class Laboratorio extends RecursiveTreeObject<Laboratorio>{
    private int ID;
    private String Nombre;
    private String Telefono;
    private String Ubicacion;

    public Laboratorio(int ID, String Nombre, String Telefono, String Ubicacion) {
        this.ID = ID;
        this.Nombre = Usuario.ucFirst(Nombre);
        this.Telefono = Telefono;
        this.Ubicacion = Usuario.ucFirst(Ubicacion);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Usuario.ucFirst(Nombre);
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Usuario.ucFirst(Ubicacion);
    }
    
    

    
    
    
    
    
}
