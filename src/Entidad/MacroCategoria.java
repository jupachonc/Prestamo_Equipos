package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacroCategoria extends RecursiveTreeObject<MacroCategoria> {

    private int ID;
    private String nombre;
    private String descripción;
    private int LaboratorioID;

    public ArrayList<Entidad.Categoria> catList = new ArrayList<>();
    
    public String validar(){
        
      if(nombre==null || !Pattern.compile("^.{3,255}$", Pattern.CASE_INSENSITIVE)
        .matcher(nombre).find()){
          return("Nombre inválido.");
      }
      if(descripción!=null && !descripción.equals("") && !Pattern.compile("^.{3,255}$", Pattern.CASE_INSENSITIVE)
        .matcher(descripción).find()){
          return("Descripción inválida.");
      }
      return("OK");
    }

    public MacroCategoria(int ID, String nombre, String descripción, int LaboratorioID) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripción = descripción;
        this.LaboratorioID = LaboratorioID;
    }

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
    public int getLaboratorioID() {
        return LaboratorioID;
    }

    public void setLaboratorioID(int LaboratorioID) {
        this.LaboratorioID = LaboratorioID;
    }
    

    private class Categoria {

        private int ID;
        private int CantidadMax;
        private String nombre;
        private String descripción;
        public ArrayList<Elemento> eleList = new ArrayList<>();

        public Categoria(int ID, int CantidadMax, String nombre, String descripción) {
            this.ID = ID;
            this.CantidadMax = CantidadMax;
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

        private class Elemento {

            private int ID;
            private String nombre;
            private String descripción;
            private int Estado;

            public Elemento(int ID, String nombre, String descripción, int Estado) {
                this.ID = ID;
                this.nombre = nombre;
                this.descripción = descripción;
                this.Estado = Estado;
            }

            public int getID() {
                return ID;
            }

            public int getCantidadMax() {
                return Estado;
            }

            public void setCantidadMax(int Estado) {
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
        }
    }

}
