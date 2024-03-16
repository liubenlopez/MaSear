package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeptideAnalysis {

    String sequenceString = "";
    String estruc2DString = "";
    ArrayList<String> expression = new ArrayList<String>();

    public PeptideAnalysis(String[] sequence, String[] Estruc2D) {
        for (int i = 0; i < sequence.length; i++) {
            sequenceString += sequence[i];
            estruc2DString += Estruc2D[i];
        }
    }

    public PeptideAnalysis(String sequence, String Estruc2D) {
        sequenceString = sequence;
        estruc2DString = Estruc2D;
    }

    /**Devuelve las subcadenas cortadas según se especifique en secuencia y solapandolas n posiciones partiendo de la subcadena anterior.
     * Para H2 en una estructura2D HHHHCSSSHH se tomará HH.
     * Para S en una estructura2D HHHHCSSSHH se tomará SSS.
     * example1e: expression="H2CS" n=0 en la sequencia:ACDEFGHIJKL y estructura2D:HH[HHCSSS]HH
     * return DEFGHI;
     * example2: expression="H3" n=1 en la sequencia:ACDEFGHIJKLM y estructura2D:[HH(H]HH)CSSS[HHH].
     * return ACD,DEF,KLM;
     */
    public List<String> getSecuence(String expression, int n) {//Devulve las expressiones solapada a la logitud n
        List<String> Subsequence = new ArrayList<String>();
        EntranceValidator ev = new EntranceValidator(expression, n, estruc2DString.length());
        int concatenateSize = ev.validatedN();
        String validatedExpression = ev.validatedExpression();
        if (validatedExpression.equals("")) {//si la espresión no es valida la devuelve vacía
            return Subsequence;
        }
        Pattern pattern = Pattern.compile(validatedExpression);
        Matcher matcher = pattern.matcher(estruc2DString);
        int j = 0;
        for (int i = 0; i < sequenceString.length(); i += concatenateSize) {
            if (matcher.find(i)) {
                if (j != matcher.end()) {
                    Subsequence.add(sequenceString.substring(matcher.start(), matcher.end()));
                    this.expression.add(estruc2DString.substring(matcher.start(), matcher.end()));
                } else {
                    i = (concatenateSize == 0 ? matcher.end() : matcher.start());
                }
                j = matcher.end();
            } else {
                i++;
            }
        }
        return Subsequence;
    }

    public List<String> matchAminoAcids(List<String> Sequences, List<String> aminoAcidsDesired, List<String> aminoAcidUnwanted) {//este metétodo no debe ser public pero esta asi para testearlo
        List<String> Subsequence = new ArrayList<String>();
        if (!aminoAcidUnwanted.isEmpty()) {
            for (int j = 0; j < aminoAcidUnwanted.size(); j++) {//primaro elimino los que no quiero
                for (int i = 0; i < Sequences.size(); i++) {
                    Pattern pattern = Pattern.compile(aminoAcidUnwanted.get(j));
                    Matcher matcher = pattern.matcher(Sequences.get(i));
                    if (matcher.find()) {
                        Sequences.remove(i);
                        i--;
                    }
                }
            }
        }
        if (!aminoAcidsDesired.isEmpty()) {
            for (int i = 0; i < Sequences.size(); i++) {
                int n = 0;
                for (int j = 0; j < aminoAcidsDesired.size(); j++) {
                    Pattern pattern = Pattern.compile(aminoAcidsDesired.get(j));
                    Matcher matcher = pattern.matcher(Sequences.get(i));
                    if (matcher.find()) {
                        n++;
                    }
                }
                if (n > 0) {
                    Subsequence.add(Sequences.get(i));
                }

            }
        }
        return Subsequence.isEmpty() ? Sequences : Subsequence;
    }

    public List<String> matchHidrofobicity(List<String> sequence, String property) {
        List<String> Subsequence = new ArrayList<String>();
        AminoAcid aminoAcid;
        ArrayList<String> aminoAcidSelected = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            aminoAcid = new AminoAcid(i);
            if (aminoAcid.getHidrofobicity().equals(property)) {
                aminoAcidSelected.add(aminoAcid.getCodeAA1(i));
            }
        }
        for (int i = 0; i < aminoAcidSelected.size(); i++) {
            Pattern pattern = Pattern.compile(aminoAcidSelected.get(i));
            for (int j = 0; j < sequence.size(); j++) {
                Matcher matcher = pattern.matcher(sequence.get(j));
                if (matcher.find()) {
                    Subsequence.add(sequence.get(j));
                    sequence.remove(j);
                    j--;
                }
            }
        }
        return Subsequence;
    }

    ArrayList<String> getExpressions() {
        return expression;
    }
}
