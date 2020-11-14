package DAO;

import Entidad.Categoria;
import Entidad.Elemento;
import Entidad.Laboratorio;
import Entidad.MacroCategoria;
import Entidad.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    
    public ArrayList<MacroCategoria> getCats(int LabID) {
        ArrayList<MacroCategoria> Mcats = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM macrocategoria Where LaboratorioID = " + LabID + ";";
            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();

            while (resultSet.next()) {
                Mcats.add(new MacroCategoria(resultSet.getInt("ID"), resultSet.getString("Nombre"),
                        resultSet.getString("Descripción")));
            }
            
            for(int i = 0;i < Mcats.size(); i++){
                ArrayList<Categoria> cats = new ArrayList<>();
                sql = "SELECT * FROM categoria Where MacroCategoriaID = " + Mcats.get(i).getID() + ";";
                resultSet = statement.executeQuery(sql);
                resultSet.beforeFirst();

                while (resultSet.next()) {
                    cats.add(new Categoria(resultSet.getInt("ID"), resultSet.getInt("CantidadMax"),resultSet.getString("Nombre"),
                            resultSet.getString("Descripción")));
                }
                
                for(int j = 0;j < cats.size(); j++){
                    ArrayList<Elemento> elems = new ArrayList<>();
                    sql = "SELECT * FROM elemento Where CategoriaID = " + cats.get(j).getID() + ";";
                    resultSet = statement.executeQuery(sql);
                    resultSet.beforeFirst();

                    while (resultSet.next()) {
                        elems.add(new Elemento(resultSet.getInt("ID"),resultSet.getString("Nombre"),
                            resultSet.getString("Descripción"), resultSet.getInt("EstadoElemento")));
                    }
                
                    cats.get(i).eleList = elems;
                }
                
                Mcats.get(i).catList = cats;
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
        return Mcats;
    }

    public ArrayList<Usuario> getAdminsinLab(Laboratorio lab) {
        ArrayList<Usuario> admins = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM laboratorio_administrador, active_admins "
                    + "WHERE AdministradorDocumento = Documento "
                    + "AND LaboratorioID = " + lab.getID() + ";";
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

    public ArrayList<Usuario> getAdminsOutLab(Laboratorio lab) {
        ArrayList<Usuario> admins = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM (SELECT Documento as DOC FROM "
                    + "laboratorio_administrador, active_admins WHERE AdministradorDocumento = Documento AND LaboratorioID ="
                    + lab.getID() + ") IL RIGHT JOIN active_admins ADM"
                    + " ON IL.DOC = ADM.Documento WHERE IL.DOC IS NULL;";
            System.out.println(sql);
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

    public boolean addAdmintoLab(Usuario usr, Laboratorio lab) {

        int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = String.format("INSERT INTO laboratorio_administrador "
                    + "(`IDLaboratorio`, `AdministradorDocumento`) Values( %s , %s);",
                    lab.getID(), usr.getDocumento());
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

    public boolean removeAdminfromLab(Usuario usr, Laboratorio lab) {

        int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = String.format("DELETE FROM laboratorio_administrador "
                    + "WHERE IDLaboratorio = %s AND AdministradorDocumento = %s;",
                    lab.getID(), usr.getDocumento()) ;
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

    public boolean disableLab(Laboratorio lab) {
        int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = String.format("UPDATE laboratorio SET Estado = 0 WHERE ID = %s;", lab.getID());
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
    
    public boolean enableLab(Laboratorio lab) {
        int resultSet;

        
        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = String.format("UPDATE laboratorio SET Estado = 1 WHERE ID = %s;", lab.getID());
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
    
    public ArrayList<Laboratorio> getLabsperAdmin(Usuario usr){
        ArrayList<Laboratorio> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM laboratorio, laboratorio_administrador "
                    + "Where ID = LaboratorioID AND AdministradorDocumento =" + usr.getDocumento() + ";";
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
}
