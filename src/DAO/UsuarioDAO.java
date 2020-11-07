/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidad.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;

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
        try {
            resultSet = -1;
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql;
            if (object.getType() == 0) {
                sql = "INSERT INTO estudiante( `Nombre`, `Apellido`,`Documento`,`Email`,`Password`) VALUES (\""
                        + object.getNombre() + "\", \"" + object.getApellido() + "\", "
                        + object.getDocumento() + ",\"" + object.getEmail() + "\", \""
                        + DigestUtils.sha256Hex(object.getContrasena()) + "\")";
            } else {
                if (existente(object)) {
                    sql = "UPDATE administrador set Password ='" + DigestUtils.sha256Hex(object.getContrasena())
                            + "', Estado = 1 " + "Where Documento = " + object.getDocumento() + ";";;
                } else {
                    sql = "INSERT INTO administrador ( `Nombre`, `Apellido`,`Documento`,`Email`,`Password`, `Estado`) VALUES (\""
                            + object.getNombre() + "\", \"" + object.getApellido() + "\", "
                            + object.getDocumento() + ",\"" + object.getEmail() + "\", \""
                            + DigestUtils.sha256Hex(object.getContrasena()) + "\", 1)";
                }
            }
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

    public boolean existente(Usuario object) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql = "SELECT * FROM estudiante WHERE Documento = " + object.getDocumento()
                    + " OR Email = \"" + object.getEmail() + "\";";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            } else {
                sql = "SELECT * FROM administrador WHERE Documento = " + object.getDocumento()
                        + " OR Email = \"" + object.getEmail() + "\";";
                if (resultSet.next()) {
                    return true;
                }
            }
            return false;
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

    public boolean reactivar(Usuario object) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql = "SELECT * FROM administrador WHERE (Documento = " + object.getDocumento()
                    + " OR Email = \"" + object.getEmail() + "\")  AND Estado = 0;";
            System.out.println(sql);
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            return false;
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

    public Usuario leer(String usr, String pss) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Usuario usuario = null;
        try {
            resultSet = null;
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM estudiante "
                    + "WHERE Email = '" + usr
                    + "' AND Password ='" + DigestUtils.sha256Hex(pss) + "'");
            if (resultSet.next()) {
                usuario = new Usuario(resultSet.getString("Nombre"), resultSet.getString("Apellido"),
                        resultSet.getInt("Documento"), resultSet.getString("Email"),
                        resultSet.getString("Password"), 0);
            } else {
                resultSet = statement.executeQuery("SELECT * FROM administrador "
                        + "WHERE Email = '" + usr
                        + "' AND Password ='" + DigestUtils.sha256Hex(pss) + "' AND Estado = 1;");
                if (resultSet.next()) {
                    usuario = new Usuario(resultSet.getString("Nombre"), resultSet.getString("Apellido"),
                            resultSet.getInt("Documento"), resultSet.getString("Email"),
                            resultSet.getString("Password"), 1);
                    if (usuario.getEmail().equals("soporteing_fibog@unal.edu.co")) {
                        usuario.setType(2);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en SQL" + ex);
            return null;
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {

            }
        }
        return usuario;

    }

    public boolean changePassword(Usuario usuario, String newpss) {
        Connection connection = null;
        Statement statement = null;
        int resultSet;
        try {
            resultSet = -1;
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql;
            if (usuario.getType() == 0) {
                sql = "UPDATE estudiante set Password ='" + DigestUtils.sha256Hex(newpss)
                        + "' Where Documento = " + usuario.getDocumento() + ";";
            } else {
                sql = "UPDATE administrador set Password ='" + DigestUtils.sha256Hex(newpss)
                        + "' Where Documento = " + usuario.getDocumento() + ";";
            }
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

    public ArrayList<Usuario> getAdmins() {
        ArrayList<Usuario> admins = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWD);
            statement = connection.createStatement();
            String sql = "SELECT * FROM administrador Where Estado = 1 AND Email != \"soporteing_fibog@unal.edu.co\"";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();

            while (resultSet.next()) {
                admins.add(new Usuario(resultSet.getString("Nombre"), resultSet.getString("Apellido"),
                        resultSet.getInt("Documento"), resultSet.getString("Email"),
                        resultSet.getString("Password"), 1));
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
        return admins;
    }

}
