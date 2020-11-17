/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.SU;

import Control.ValidarRegistro;
import DAO.LaboratorioDAO;
import Entidad.Laboratorio;
import Entidad.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MAssignTest {

    public MAssignTest() {
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
    public void testAssignLab() {

        LaboratorioDAO labDAO = new LaboratorioDAO();

        Laboratorio lab0 = new Laboratorio(3, "Laboratorio Prueba 1", "8888888", "Edificio 403");
        Usuario adm0 = new Usuario("Jose", "Beltran", 77083769, "jbeltran@unal.edu.co", "123456", 1);
        assertEquals(labDAO.addAdmintoLab(adm0, lab0), true);

        Laboratorio lab1 = new Laboratorio(4, "Laboratorio Prueba 2", "8888888", "Edificio 403");
        Usuario adm1 = new Usuario("Jose", "Beltran", 77083769, "jbeltran@unal.edu.co", "123456", 1);
        assertEquals(labDAO.addAdmintoLab(adm1, lab1), false);

        Laboratorio lab2 = new Laboratorio(3, "Laboratorio Prueba 1", "8888888", "Edificio 403");
        Usuario adm2 = new Usuario("Andrés", "Beltran", 700000, "abeltran@unal.edu.co", "123456", 1);
        assertEquals(labDAO.addAdmintoLab(adm2, lab2), false);

    }
}
