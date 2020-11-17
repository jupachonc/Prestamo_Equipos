/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.SU;

import Control.ValidarRegistro;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MAdminsControllerTest {

    public MAdminsControllerTest() {
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
    public void testRegistro(){
    
        ValidarRegistro validar = new ValidarRegistro();
        
        //PRIMERO HAY QUE REALIZAR EL TEST UNICAMENTE DEL CODIGO COMENTADO.
        //LUEGO COMENTARLO COMO ESTÁ, Y AHÍ SI HACER EL TESTEO DE TODO
        
//        String nombre = ("Jose");
//        String apellido = ("Beltran");
//        int Documento = 77083769;
//        String strEmail = ("jbeltran@unal.edu.co"); 
//        String contrasena = ("123456");
//        String reContrasena = ("123456");
//        assertEquals(validar.verificarRegistro(nombre,apellido,Documento,strEmail,contrasena,reContrasena, 1),("Usuario registrado"));

        String nombre1 = ("Jose");
        String apellido1 = ("Beltran");
        int Documento1 = 77083769;
        String strEmail1 = ("jbeltran@unal.edu.co"); 
        String contrasena1 = ("123456");
        String reContrasena1 = ("123457");
        assertEquals(validar.verificarRegistro(nombre1,apellido1,Documento1,strEmail1,contrasena1,reContrasena1, 1),("Las contraseñas no conciden"));
       
        String nombre2 = ("Jose");
        String apellido2 = ("Beltran");
        int Documento2 = 77083769;
        String strEmail2 = ("jbeltran@unal.edu.co"); 
        String contrasena2 = ("123");
        String reContrasena2 = ("123");
        assertEquals(validar.verificarRegistro(nombre2,apellido2,Documento2,strEmail2,contrasena2,reContrasena2, 1),("Longitud contrasena incorrecta"));
       
        String nombre3 = ("Jose");
        String apellido3 = ("Beltran");
        int Documento3 = 77083769;
        String strEmail3 = ("jbeltran-unal.edu.co"); 
        String contrasena3 = ("123456");
        String reContrasena3 = ("123456");
        assertEquals(validar.verificarRegistro(nombre3,apellido3,Documento3,strEmail3,contrasena3,reContrasena3, 1),("E-mail inválido"));
       
        String nombre4 = ("Jose");
        String apellido4 = ("Beltran");
        int Documento4 = 1;
        String strEmail4 = ("jbeltran@unal.edu.co"); 
        String contrasena4 = ("123456");
        String reContrasena4 = ("123456");
        assertEquals(validar.verificarRegistro(nombre4,apellido4,Documento4,strEmail4,contrasena4,reContrasena4, 1),("Documento inválido"));
       
        String nombre5 = ("Jose");
        String apellido5 = ("Be");
        int Documento5 = 77083769;
        String strEmail5 = ("jbeltran@unal.edu.co"); 
        String contrasena5 = ("123456");
        String reContrasena5 = ("123456");
        assertEquals(validar.verificarRegistro(nombre5,apellido5,Documento5,strEmail5,contrasena5,reContrasena5, 1),("Longitud apellido incorrecta"));
       
        String nombre6 = ("Jo");
        String apellido6 = ("Beltran");
        int Documento6 = 77083769;
        String strEmail6 = ("jbeltran@unal.edu.co"); 
        String contrasena6 = ("123456");
        String reContrasena6 = ("123456");
        assertEquals(validar.verificarRegistro(nombre6,apellido6,Documento6,strEmail6,contrasena6,reContrasena6, 1),("Longitud nombre incorrecta"));
       
  }
}
