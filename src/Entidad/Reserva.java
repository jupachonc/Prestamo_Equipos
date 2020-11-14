package Entidad;

import java.sql.Timestamp;
import java.util.HashMap;

public class Reserva {
    private int ID;
    private int EstadoReserva;
    private Timestamp tiempoReserva;
    public HashMap<Categoria, Integer> listaPrestamo = new HashMap<>();

    public Reserva(int ID, int EstadoReserva, Timestamp tiempoReserva) {
        this.ID = ID;
        this.EstadoReserva = EstadoReserva;
        this.tiempoReserva = tiempoReserva;
    }
}
