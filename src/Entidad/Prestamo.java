package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Prestamo extends RecursiveTreeObject<Prestamo> {
    private final int ID;
    private final String tiempoReserva;
    private final EstadoPrestamo estado;
    private final String comentarios;
    private String Lab;
    public ArrayList<Categoria> catList;
    
    enum EstadoPrestamo{
        RESERVA(-1, "Reservado"),
        PRESTADO(0, "Prestado"),
        DEV_COMPLETO(1, "Devolución Completa"),
        DEV_INCOMPLETO(2, "Devolución Incompleta"),
        DEV_MANTENIMIENTO(3, "Devolución por Mantenimiento");
        
        private final int ID;
        private final String nombre;
        
        EstadoPrestamo(int ID, String nombre){
            this.ID = ID;
            this.nombre = nombre;
        }
        
        public int getID(){
            return ID;
        }
        
        public static EstadoPrestamo getFromID(int ID){
            for(EstadoPrestamo i : values()){
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

    public Prestamo(int ID, int estado, String tiempoReserva, String comentarios, String Lab) {
        this.ID = ID;
        this.estado = EstadoPrestamo.getFromID(estado);
        this.tiempoReserva = tiempoReserva;
        this.comentarios = comentarios;
        this.Lab = Lab;
    }

    public int getID() {
        return ID;
    }
    
    public String getLab(){
        return Lab;
    }
    
    public String getComentarios(){
        return comentarios;
    }
    
    public String getEstado() {
        return estado.toString();
    }

    public int getEstadoPrestamo() {
        return estado.getID();
    }

    public String getTiempoReserva() {
        return tiempoReserva;
    }
}
