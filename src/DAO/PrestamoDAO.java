/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author sebas
 */
public class PrestamoDAO {

    Connection connection;
    Statement statement;

    public int doPrestamo(ArrayList<Elemento> elements, Usuario usr, Usuario admin, String obs, int reserve) {
        ResultSet resultSet = null;
        int id = 0;
        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql;
            if (reserve == 0) {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Obsernaciones Préstamo:\n\"" + obs + ",\"" + new Timestamp(new Date().getTime()) + "\", null, null"
                        + "," + admin.getDocumento() + ");";

            } else {
                sql = "INSERT INTO prestamo (IDEstudiante, EstadoPrestamo, Comentarios, TiempoDeInicio, TiempoDeEntrega, ReservasID, AdministradorDocumento) "
                        + "VALUES (" + usr.getDocumento() + ", 0, "
                        + "\"Obsernaciones Préstamo:\n\"" + obs + ",\"" + new Timestamp(new Date().getTime()) + "\", null,"
                        + reserve + "," + admin.getDocumento() + ");";
            }
            statement.executeUpdate(sql);
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as ID");
            resultSet.next();
            id = resultSet.getInt("ID");

            for (Elemento e : elements) {
                String sql_ = "INSERT INTO prestamo_elemento (IDPrestamo, IDElemento) VALUES (" + id + "," + e.getID() + ");";
                statement.executeUpdate(sql_);
                String sql2 = "UPDATE elemento SET EstadoElemento = 2 WHERE ID =" + e.getID();
                statement.executeUpdate(sql2);
            }

            if (reserve != 0) {
                String sql3 = "UPDATE reservas SET EstadoReserva = 1 WHERE ID =" + reserve;
                statement.executeQuery(sql3);
            }

        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
        } finally {
            try {
                System.out.println("cerrando statement...");
                statement.close();
                System.out.println("cerrando conexión...");
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex);
            }
        }
        return id;

    }
}
