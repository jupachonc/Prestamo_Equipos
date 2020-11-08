/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidad.Laboratorio;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class LaboratorioDAO {

    Connection connection;
    Statement statement;

    public boolean createLab(Laboratorio lab) {
        int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO laboratorio (`Nombre`, `Telefono`, `Ubicacion`) Values(\""
                    + lab.getNombre() + "\"," + lab.getTelefono() + ", \"" + lab.getUbicacion() + "\");";
            resultSet = statement.executeUpdate(sql);
            return resultSet > 0;
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
            return false;
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
    }

    public ArrayList<Laboratorio> getLabs() {
        ArrayList<Laboratorio> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM laboratorio Where Estado = 1;";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();

            while (resultSet.next()) {
                labs.add(new Laboratorio(resultSet.getInt("ID"), resultSet.getString("Nombre"), 
                resultSet.getString("Telefono"), resultSet.getString("Ubicacion")));
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
        return labs;
    }
    
    public boolean disableLab(Laboratorio lab){
            int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE laboratorio SET Estado = 0 WHERE ID =" + lab.getID() + ";";
            resultSet = statement.executeUpdate(sql);
            return resultSet > 0;
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
            return false;
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
    }
}
