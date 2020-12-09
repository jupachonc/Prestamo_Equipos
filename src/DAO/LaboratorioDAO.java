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
            String sql = "INSERT INTO laboratorio (`Nombre`, `Telefono`, `Ubicacion`) Values(\" "
                       + lab.getNombre() + "\", \"" + lab.getTelefono() + "\", \"" + lab.getUbicacion() + "\");";
            resultSet = statement.executeUpdate(sql);
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

        return resultSet > 0;
    }

    public ArrayList<Laboratorio> getLabs() {
        ArrayList<Laboratorio> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM active_labs;";
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

    public ArrayList<Laboratorio> getLabsReserve() {
        ArrayList<Laboratorio> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM lab_prestamo;";
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
    
    public ArrayList<MacroCategoria> getMCats(int LabID) {
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
                        resultSet.getString("Descripción"), resultSet.getInt("LaboratorioID")));
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
                    + "(`LaboratorioID`, `AdministradorDocumento`) Values( %s , %s);",
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
                    + "WHERE LaboratorioID = %s AND AdministradorDocumento = %s;",
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

    public boolean disableLab(Laboratorio lab) {
        int resultSet;

        try {
            resultSet = -1;
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = String.format("UPDATE laboratorio SET Estado = 2 WHERE ID = %s;", lab.getID());
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

    public ArrayList<Laboratorio> getLabsperAdmin(Usuario usr) {
        ArrayList<Laboratorio> labs = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM active_labs, laboratorio_administrador "
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

    public ArrayList<Categoria> getCats(MacroCategoria Mcat) {
        ArrayList<Categoria> cats = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM categoria "
                    + "WHERE MacroCategoriaID = " + Mcat.getID() + ";";

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {

                int catID = resultSet.getInt("ID");
                int catMax = resultSet.getInt("CantidadMax");
                String nombre = resultSet.getString("Nombre");
                String desc = resultSet.getString("Descripción");

                int amount = maxCatAmount(catID);

                cats.add(new Categoria(catID, catMax, amount, nombre, desc));
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
        return cats;

    }

    public int maxCatAmount(int catID) {
        int amount = 0;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT COUNT(*) as amount FROM elemento "
                    + "WHERE CategoriaID = " + catID + " AND EstadoElemento = 1;";

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {
                amount = resultSet.getInt("amount");
            }

            sql = "SELECT Cantidad FROM reservas,categoria_reservas "
                    + "WHERE ID = ReservasID AND CategoriaID = " + catID + " AND EstadoReserva = 0 AND DATE(TiempoDeReserva) = DATE(NOW());";

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {
                amount -= resultSet.getInt("Cantidad");
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
        return amount;
    }

    public ArrayList<Elemento> getElements(Categoria cat) {
        ArrayList<Elemento> elmts = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM active_elements elemento, categoria "
                    + "WHERE CategoriaID = categoria.ID AND CategoriaID =" + cat.getID();

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {

                elmts.add(new Elemento(resultSet.getInt("elemento.ID"), resultSet.getString("elemento.Nombre"),
                        resultSet.getString("elemento.Descripción"), resultSet.getInt("EstadoElemento")));
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
        return elmts;

    }

    public ArrayList<Elemento> getElementsA(Categoria cat) {
        ArrayList<Elemento> elmts = new ArrayList<>();

        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM elemento, categoria "
                    + "WHERE CategoriaID = categoria.ID AND CategoriaID =" + cat.getID();

            resultSet = statement.executeQuery(sql);
            resultSet.beforeFirst();
            while (resultSet.next()) {

                elmts.add(new Elemento(resultSet.getInt("elemento.ID"), resultSet.getString("elemento.Nombre"),
                        resultSet.getString("elemento.Descripción"), resultSet.getInt("EstadoElemento")));
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
        return elmts;

    }

}
