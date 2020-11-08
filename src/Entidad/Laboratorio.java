package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Laboratorio extends RecursiveTreeObject<Laboratorio>{
    private int ID;
    private String Nombre;
    private int Telefono;
    private String Ubicacion;

    public Laboratorio(int ID, String Nombre, int Telefono, String Ubicacion) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Ubicacion = Ubicacion;
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
        this.Nombre = Nombre;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Ubicacion;
    }
    
    

    
    
    
    
    
}
