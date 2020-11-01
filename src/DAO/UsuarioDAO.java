/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import Entidad.Usuario;
import Control.LoginController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author nguzman
 */
public class UsuarioDAO {
    static final String DB_URL = "jdbc:mysql://loginapp.c6zrixw9dnvf.us-east-1.rds.amazonaws.com:3306/LoginApp?useSSL=false";
    static final String DB_DRV = "com.mysql.jdbc.Driver";
    static final String DB_USER = "admin";
    static final String DB_PASSWD = "H6TWCpw3RbKVhKWf4ZUa";
    
    public boolean crear(Usuario object) {
        Connection connection = null;
        Statement statement = null;
        int resultSet;
        Usuario usuario = new Usuario();
        LoginController LG = new LoginController();
        try {
            resultSet = -1;
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql = "INSERT INTO estudianteprestatario( `Nombre`, `Apellido`,`Documento`,`Email`,`Password`) VALUES (\""
                    + object.getNombre() + "\", \"" + object.getApellido() + "\", \"" + object.getDocumento() + "\",\"" + object.getEmail() + "\", \"" + object.getContrasena() + "\")";
            System.out.println(sql);
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
    public boolean leer(String usr, String pss) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            resultSet = null;
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM estudianteprestatario "
                    + "WHERE Email = '" + usr
                    + "' AND Password ='" + pss + "'");
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
            return false;
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
                return resultSet.next();
            } catch (SQLException ex) {

            }
        }

    }
}    

