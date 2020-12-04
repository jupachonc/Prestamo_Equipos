package Entidad;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Timestamp;

public class Reserva extends RecursiveTreeObject<Reserva>{
    private int ID;
    private int EstadoReserva;
    private Timestamp tiempoReserva;

    public Reserva(int ID, int EstadoReserva, Timestamp tiempoReserva) {
        this.ID = ID;
        this.EstadoReserva = EstadoReserva;
        this.tiempoReserva = tiempoReserva;
    }

    public int getID() {
        return ID;
    }

    public int getEstadoReserva() {
        return EstadoReserva;
    }

    public Timestamp getTiempoReserva() {
        return tiempoReserva;
    }
}
