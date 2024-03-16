/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peptidosTest;

import Class.EntranceValidator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Liuben
 */
public class EntranceValidatorTest {

    public EntranceValidatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void expressionValidatorWith2SequenceNumbers(){
        String exp = "22";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = ".{22}";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator(){
        String exp = "H";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H+";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorHH(){
        String exp = "HH";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator1(){
        String exp = "H3";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H{3}";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator2(){
        String exp = "H2CS";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H{2}C+S+";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator3(){
        String exp = "HC3";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H+C{3}";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator4(){
        String exp = "HCS";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H+C+S+";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidator5(){
        String exp = "3HCS";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWith2SequenceNumber1(){
        String exp = "HC33";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H+C{33}";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWith2SequenceNumber2(){
        String exp = "HC33S22H";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "H+C{33}S{22}H+";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWith2SequenceNumbers3(){
        String exp = "HC33SS";//2 caracteres iguales no son evaluables
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWithNullMotive(){
        String exp = " ";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = ".+";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWithNumberMotive(){
        String exp = "9";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = ".{9}";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWithNumberAndNullMotive(){
        String exp = "9 ";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "";
        assertEquals(expected,result);
    }

    @Test
    public void expressionValidatorWithNumberAndNullMotive1(){
        String exp = " 9";
        EntranceValidator ev = new EntranceValidator(exp,0);
        String result = ev.validatedExpression();
        String expected = "";
        assertEquals(expected,result);
    }

    @Test
    public void validatedN(){
        EntranceValidator ev = new EntranceValidator("",-1,10);//si n es mayor o menor que la longitud de la seq devulve 0
        int result = ev.validatedN();
        int expected = 0;
        assertEquals(expected,result);
    }

    @Test
    public void validatedN1(){
        EntranceValidator ev = new EntranceValidator("",12,10);//si n es mayor o menor que la longitud de la seq devulve 0
        int result = ev.validatedN();
        int expected = 0;
        assertEquals(expected,result);
    }

}
