/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import Entidad.Usuario;
import DAO.UsuarioDAO;

/**
 *
 * @author edgar
 */
public class LoginControllerTest {
    
    
    
    public LoginControllerTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void Login() {
        UsuarioDAO dao = new UsuarioDAO();
        //PRIMERO HAY QUE REALIZAR EL [REGISTERCONTROLLERTEST]
        Usuario usuario = dao.leer("acasta@unal.edu.co","12345J");
        assertEquals(usuario,null);
        
        Usuario usuario1 = dao.leer("acasta@unal.edu.co","123456");
        assertEquals(usuario1.getType(),0);
        
        Usuario usuario2 = dao.leer("admin@unal.edu.co","admin123");
        assertEquals(usuario2.getType(),1);
        
        Usuario usuario3 = dao.leer("soporteing_fibog@unal.edu.co","soporte123");
        assertEquals(usuario3.getType(),2);
        
        Usuario usuario4 = dao.leer("admin@unal.edu.co","admin12");
        assertEquals(usuario4,null);
        
        Usuario usuario5 = dao.leer("soporteing_fibog@unal.edu.co","soporte12");
        assertEquals(usuario5,null);
    }
}
