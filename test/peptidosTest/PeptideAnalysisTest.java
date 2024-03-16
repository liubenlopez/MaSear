package peptidosTest;

import Class.PeptideAnalysis;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeptideAnalysisTest {

    private PeptideAnalysis pa;

    public PeptideAnalysisTest() {
    }

    @Before
    public void setUp() {
        String[] sequence = new String[]{"A", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        String[] Estruc2D = new String[]{"H", "H", "H", "H", "C", "S", "S", "S", "H", "H", "H"};
        pa = new PeptideAnalysis(sequence, Estruc2D);
    }

    //*****************************  getSequence con devolución sin solapar  *****************************
    @Test
    public void testEliceAlfa() {
        List<String> alfaSequence = pa.getSecuence("H", 0);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDE");
        alfaSequenceResult.add("JKL");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testEliceAlfa1() {
        List<String> alfaSequence = pa.getSecuence("H2", 0);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("AC");
        alfaSequenceResult.add("DE");
        alfaSequenceResult.add("JK");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testEliceBeta() {
        List<String> betaSequence = pa.getSecuence("S2", 0);
        List<String> betaSequenceResult = new ArrayList<String>();
        betaSequenceResult.add("GH");
        assertEquals(betaSequenceResult, betaSequence);
    }

    @Test
    public void testVuelta() {
        List<String> vueltaSequence = pa.getSecuence("C", 0);
        List<String> vueltaSequenceResult = new ArrayList<String>();
        vueltaSequenceResult.add("F");
        assertEquals(vueltaSequenceResult, vueltaSequence);
    }

    @Test
    public void testSequenceSize2Vuelta() {
        List<String> alfaSequence = pa.getSecuence("C2", 0);
        List<String> alfaSequenceResult = new ArrayList<String>();
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceOnlyOneNumber() {//pica la cadena en trozos del tamaño del número sin solapar hasta donde alcance
        List<String> alfaSequence = pa.getSecuence("9", 0);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDEFGHIJ");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    //*****************************  getSequence con devolución solapada *****************************
    @Test
    public void testSequenceOnlyOneNumberSolapado() {//pica la cadena en trozos del tamaño del número solapando a partir de n
       List<String> alfaSequence = pa.getSecuence("6", 3);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDEFG");
        alfaSequenceResult.add("EFGHIJ");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize2EliceAlfa() {
        List<String> alfaSequence = pa.getSecuence("H2", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("AC");
        alfaSequenceResult.add("CD");
        alfaSequenceResult.add("DE");
        alfaSequenceResult.add("JK");
        alfaSequenceResult.add("KL");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize3EliceAlfa() {
        List<String> alfaSequence = pa.getSecuence("H3", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACD");
        alfaSequenceResult.add("CDE");
        alfaSequenceResult.add("JKL");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize4EliceAlfa() {
       List<String> alfaSequence = pa.getSecuence("H4", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDE");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize5EliceAlfa() {
        List<String> alfaSequence = pa.getSecuence("H5", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testEliceAlfaWithNOutRange() {//coge n como 0 si es mayor que la secuencia
        List<String> alfaSequence = pa.getSecuence("H2", 100);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("AC");
        alfaSequenceResult.add("DE");
        alfaSequenceResult.add("JK");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testEliceAlfaWithNOutRange1() {
        List<String> alfaSequence = pa.getSecuence("H2", -100);//coge n como 0 si es negativo
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("AC");
        alfaSequenceResult.add("DE");
        alfaSequenceResult.add("JK");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize2EliceBeta() {
        List<String> alfaSequence = pa.getSecuence("S2", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("GH");
        alfaSequenceResult.add("HI");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize3EliceBeta() {
        List<String> alfaSequence = pa.getSecuence("S3", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("GHI");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceSize1Vuelta() {
         List<String> alfaSequence = pa.getSecuence("C1", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("F");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceExpression() {
        List<String> alfaSequence = pa.getSecuence("HCS", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDEFGHI");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceExpression1() {
        List<String> alfaSequence = pa.getSecuence("H2CS1", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("DEFG");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceOnlySpace() {//la devulve completa
        List<String> alfaSequence = pa.getSecuence(" ", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        alfaSequenceResult.add("ACDEFGHIJKL");
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    //***************************** Otras pruebas a getSequence *****************************
    @Test
    public void testSequenceInvalid() {//este test realmente es responsabilidad del ExpressionValidator pero esta para ver que pasa en la práctica
       List<String> alfaSequence = pa.getSecuence("H22CS1", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceInvalid1() {//este test realmente es responsabilidad del ExpressionValidator pero esta para ver que pasa en la práctica
        List<String> alfaSequence = pa.getSecuence("1H2CS1", 1);
        List<String> alfaSequenceResult = new ArrayList<String>();
        assertEquals(alfaSequenceResult, alfaSequence);
    }

    @Test
    public void testSequenceWithAminocidComplit() {//test con dependencias
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("JKL");
        List<String> aminoAcids = new ArrayList<String>();
        aminoAcids.add("D");
        List<String> aminoAcidUnwanted = new ArrayList<String>();
        aminoAcidUnwanted.add("L");
        List<String> Sequence = pa.getSecuence("H3", 1);
        List<String> SequenceFitrada = pa.matchAminoAcids(Sequence, aminoAcids, aminoAcidUnwanted);
        List<String> Result = new ArrayList<String>();
        Result.add("ACD");
        Result.add("CDE");
        assertEquals(Result, SequenceFitrada);
    }

    @Test
    public void testSolapado() {//test con dependencias
        String[] sequence = new String[]{"A", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        String[] Estruc2D = new String[]{"H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H"};
        PeptideAnalysis p = new PeptideAnalysis(sequence, Estruc2D);
        List<String> Sequence = p.getSecuence("H4", 3);
        List<String> Result = new ArrayList<String>();
        Result.add("ACDE");
        Result.add("EFGH");
        Result.add("HIJK");
        assertEquals(Result, Sequence);
    }

    @Test
    public void testSolapado1() {//test con dependencias
        String[] sequence = new String[]{"A", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
        String[] Estruc2D = new String[]{"H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H"};
        PeptideAnalysis p = new PeptideAnalysis(sequence, Estruc2D);
        List<String> Sequence = p.getSecuence("H4", 0);
        List<String> Result = new ArrayList<String>();
        Result.add("ACDE");
        Result.add("FGHI");
        assertEquals(Result, Sequence);
    }

    //***************************** getSequence con Aminocidos *****************************
    @Test
    public void testSequenceWithoutAminocid() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("JKL");
        List<String> aminoAcids = new ArrayList<String>();
        List<String> aminoAcidUnwanted = new ArrayList<String>();
        aminoAcidUnwanted.add("A");
        List<String> Sequence = pa.matchAminoAcids(Subsequence, aminoAcids, aminoAcidUnwanted);
        List<String> Result = new ArrayList<String>();
        Result.add("JKL");
        assertEquals(Result, Sequence);
    }

    @Test
    public void testSequenceWithoutAminocid1() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("JKL");
        List<String> aminoAcids = new ArrayList<String>();
        List<String> aminoAcidUnwanted = new ArrayList<String>();
        aminoAcidUnwanted.add("A");
        aminoAcidUnwanted.add("J");
        List<String> Sequence = pa.matchAminoAcids(Subsequence, aminoAcids, aminoAcidUnwanted);
        List<String> Result = new ArrayList<String>();
        assertEquals(Result, Sequence);
    }

    @Test
    public void testSequenceWithAndWithAminocid() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("JKL");
        Subsequence.add("MNH");
        List<String> aminoAcids = new ArrayList<String>();
        aminoAcids.add("A");
        aminoAcids.add("N");
        List<String> aminoAcidUnwanted = new ArrayList<String>();
        List<String> Sequence = pa.matchAminoAcids(Subsequence, aminoAcids, aminoAcidUnwanted);
        List<String> Result = new ArrayList<String>();
        Result.add("ACDE");
        Result.add("MNH");
        assertEquals(Result, Sequence);
    }

    @Test
    public void testSequenceWithAndWithoutAminocid() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("JKL");
        Subsequence.add("MNH");
        List<String> aminoAcids = new ArrayList<String>();
        aminoAcids.add("J");
        List<String> aminoAcidUnwanted = new ArrayList<String>();
        aminoAcidUnwanted.add("A");
        List<String> Sequence = pa.matchAminoAcids(Subsequence, aminoAcids, aminoAcidUnwanted);
        List<String> Result = new ArrayList<String>();
        Result.add("JKL");
        assertEquals(Result, Sequence);
    }
    //***************************** getSequence con propiedades *****************************

    @Test
    public void SequenceWithHidrofobicityTrue() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("PG");
        String property = "Hidrofobic";
        List<String> sequence = pa.matchHidrofobicity(Subsequence,property);
        List<String> result = new ArrayList<String>();
        result.add("ACDE");
        assertEquals(result,sequence);
    }

    @Test
    public void SequenceWithHidrofobicityTrue1() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("ACDE");
        Subsequence.add("PGA");
        String property = "Hidrofobic";
        List<String> sequence = pa.matchHidrofobicity(Subsequence,property);
        List<String> result = new ArrayList<String>();
        result.add("ACDE");
        result.add("PGA");
        assertEquals(result,sequence);
    }

    @Test
    public void SequenceWithHidrofobicityFalse() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("AAA");
        Subsequence.add("PGA");
        String property = "NoHidrofobic";
        List<String> sequence = pa.matchHidrofobicity(Subsequence,property);
        List<String> result = new ArrayList<String>();
        result.add("PGA");
        assertEquals(result,sequence);
    }

    @Test
    public void SequenceWithHidrofobicityFalse1() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("AAA");
        Subsequence.add("AAA");
        String property = "NoHidrofobic";
        List<String> sequence = pa.matchHidrofobicity(Subsequence,property);
        List<String> result = new ArrayList<String>();
        assertEquals(result,sequence);
    }

    @Test
    public void SequenceWithHidrofobicityNoDefinition() {
        List<String> Subsequence = new ArrayList<String>();
        Subsequence.add("AGA");
        Subsequence.add("GAA");
        String property = "?";
        List<String> sequence = pa.matchHidrofobicity(Subsequence,property);
        List<String> result = new ArrayList<String>();//tienen que se desconocidos
        assertEquals(result,sequence);
    }
}
