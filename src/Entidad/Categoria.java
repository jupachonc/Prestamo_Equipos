
package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;

    public class Categoria extends RecursiveTreeObject<Categoria>{
        private int ID;
        private int CantidadLibre;
        private int CantidadMax;
        private String nombre;
        private String descripción;
        public ArrayList<Entidad.Elemento> eleList = new ArrayList<>();

        public int getCantidadLibre() {
            return CantidadLibre;
        }
        
        public Categoria(int ID, int CantidadMax, int CantidadLibre, String nombre, String descripción) {
            this.ID = ID;
            this.CantidadMax = CantidadMax;
            this.CantidadLibre = CantidadLibre;
            this.nombre = nombre;
            this.descripción = descripción;
        }
        
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
