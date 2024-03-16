/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peptidosTest;

import Class.ErrorCorrector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Liuben
 */
public class ErrorCorrectorTets {

    ErrorCorrector ec = new ErrorCorrector();

    public ErrorCorrectorTets() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void Error2H() {
        String suggestExpression = ec.suggestExpression("2H");
        assertEquals("H2", suggestExpression);
    }

    @Test
    public void Error2HC() {
        String suggestExpression = ec.suggestExpression("2HC");
        assertEquals("H2C", suggestExpression);
    }

    @Test
    public void ErrorHH() {
        String suggestExpression = ec.suggestExpression("HH");
        assertEquals("H2", suggestExpression);
    }

    @Test
    public void ErrorHHH() {
        String suggestExpression = ec.suggestExpression("HHH");
        assertEquals("H3", suggestExpression);
    }

    @Test
    public void ErrorHHHCCC() {
        String suggestExpression = ec.suggestExpression("HHHCCC");
        assertEquals("H3C3", suggestExpression);
    }

    @Test
    public void Error2HHHCCC() {
        String suggestExpression = ec.suggestExpression("2HHHCCC");
        assertEquals("H5C3", suggestExpression);
    }

    @Test
    public void Error12H() {
        String suggestExpression = ec.suggestExpression("12H");
        assertEquals("H12", suggestExpression);
    }

    @Test
    public void Error12H5C() {
        String suggestExpression = ec.suggestExpression("12H5C");
        assertEquals("H17C", suggestExpression);
    }

}
