
package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Elemento extends RecursiveTreeObject<Elemento> { 
    private int ID;
    private String nombre;
    private String descripción;
    private int Estado;

    public int getID() {
        return ID;
    }

    public Elemento(int ID, String nombre, String descripción, int Estado) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripción = descripción;
        this.Estado = Estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
        this.Estado = Estado;
    }

    
}
