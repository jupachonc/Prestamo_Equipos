package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;

public class Laboratorio extends RecursiveTreeObject<Laboratorio>{ 
    private int ID;
    private String Nombre;
    private String Telefono;
    private String Ubicacion;
    public ArrayList<MacroCategoria> macroList = new ArrayList<>();

    public Laboratorio(int ID, String Nombre, String Telefono, String Ubicacion) {
        this.ID = ID;
        this.Nombre = Usuario.ucFirst(Nombre.trim());
        this.Telefono = (Telefono == null) ? "" : Telefono.trim();
        this.Ubicacion = (Ubicacion == null) ? "" : Ubicacion.trim();
    }
    public Laboratorio(String Nombre, String Telefono, String Ubicacion) {
        this.Nombre = Usuario.ucFirst(Nombre.trim());
        this.Telefono = (Telefono == null) ? "" : Telefono.trim();
        this.Ubicacion = (Ubicacion == null) ? "" : Ubicacion.trim();
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
