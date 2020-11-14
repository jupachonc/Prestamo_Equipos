package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;

public class MacroCategoria extends RecursiveTreeObject<MacroCategoria> {
    private int ID;
    private String nombre;
    private String descripción;
    private ArrayList<Categoria> catList = new ArrayList<>();

    public int getID() {
        return ID;
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
    
    private class Categoria{
        private int ID;
        private int CantidadMax;
        private String nombre;
        private String descripción;

        public int getID() {
            return ID;
        }

        public int getCantidadMax() {
            return CantidadMax;
        }

        public void setCantidadMax(int CantidadMax) {
            this.CantidadMax = CantidadMax;
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
    }
}
