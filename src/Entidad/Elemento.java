package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

/*
Estados
0 - Inhabilitado
1 - Disponible
2 - En Préstamo
3 - En Mantenimiento
*/

public class Elemento extends RecursiveTreeObject<Elemento> { 
    private int ID;
    private String nombre, descripción, catName;
    private EstadoElemento Estado;
    
   enum EstadoElemento{
        INHABILITADO(0, "Inhabilitado"),
        DISPONIBLE(1, "Disponible"),
        PRESTAMO(2, "Prestamo"),
        MANTENIMIENTO(3, "Mantenimiento");
        
        private final int ID;
        private final String nombre;
        
        EstadoElemento(int ID, String nombre){
            this.ID = ID;
            this.nombre = nombre;
        }
        
        public int getID(){
            return ID;
        }
        
        public static EstadoElemento getFromID(int ID){
            for(EstadoElemento i : values()){
                if(i.ID == ID){
                    return i;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    public int getID() {
        return ID;
    }

    public Elemento(int ID, String nombre, String descripción, int estado) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripción = descripción;
        this.Estado = EstadoElemento.getFromID(estado);
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

    public String getEstado() {
        return Estado.toString();
    }
    public int getEstadoElemento() {
        return Estado.getID();
    }

    public void setEstado(int estado) {
        this.Estado = EstadoElemento.getFromID(estado);
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
    
}
