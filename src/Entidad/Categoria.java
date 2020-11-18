
package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import java.util.regex.Pattern;

    public class Categoria extends RecursiveTreeObject<Categoria>{
        private int ID;
        private int CantidadLibre;
        private int CantidadMax;
        private String nombre;
        private String descripción;
        private int MacroCategoriaID;
        public ArrayList<Entidad.Elemento> eleList = new ArrayList<>();
        
        public String validar(){
        
            if(nombre==null || !Pattern.compile("^.{3,255}$", Pattern.CASE_INSENSITIVE)
                .matcher(nombre).find()){
                return("Nombre inválido.");
            }
            if(descripción!=null && !descripción.equals("") && !Pattern.compile("^.{3,255}$", Pattern.CASE_INSENSITIVE)
                .matcher(descripción).find()){
                return("Descripción inválida.");
            }
            if(!Pattern.compile("^[0-9]{1,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(Integer.toString(CantidadMax)).find()){
                return("Cantidad de elementos inválida.");
            }
            return("OK");
        }

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
        public int getMacroCategoriaID() {
            return MacroCategoriaID;
        }

        public void setMacroCategoriaID(int MacroCategoriaID) {
            this.MacroCategoriaID = MacroCategoriaID;
        }

    }
