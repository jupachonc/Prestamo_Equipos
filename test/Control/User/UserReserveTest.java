package Control.User;
import Control.LoginController;
import DAO.LaboratorioDAO;
import DAO.ReservasDAO;
import Entidad.Categoria;
import Entidad.Laboratorio;
import Entidad.Usuario;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserReserveTest {
    
    public UserReserveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testMakeReserve() {

        Usuario tUser = new Usuario();
        ObservableList<Categoria> elems = FXCollections.observableArrayList(new ArrayList<>());
        int ID = 0;
        
        //Prueba 1
        tUser.setDocumento(56497812);
        assertEquals(VerificarReserva(tUser, elems, ID), "Usuario con reserva");
        
        //Prueba 2
        tUser.setDocumento(1000000000);
        assertEquals(VerificarReserva(tUser, elems, ID), "Sin elementos en reserva");

        //Prueba 3
        elems.add(new Categoria(1, 2, 2, "Com1", "Desc"));
        ID = 1;
        assertEquals(VerificarReserva(tUser, elems, ID), "Reserva Correcta");
    }
       
    private String VerificarReserva(Usuario t, ObservableList<Categoria> reserveElems, int LabID){
        if(!new ReservasDAO().checkReservasUser(t)){
            return "Usuario con reserva";
        }
        else{
            if(reserveElems.isEmpty()) {
                return "Sin elementos en reserva";
            }
            else {
                if (new ReservasDAO().makeReserve(reserveElems, LabID, t.getDocumento())) {
                    return "Reserva Correcta";
                }
                else {
                    return "Error en MySQL";
                }
            }
        }
    }
}
