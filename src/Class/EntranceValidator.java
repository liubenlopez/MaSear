/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Liuben
 */
public class EntranceValidator {

    private String exp;
    private int n;
    private int secuenceLength = 0;
    private String expressionResult;
    private int errorPosition;

    public EntranceValidator(String expression, int concatenatingLength) {
        this.exp = expression;
        this.n = concatenatingLength;
    }

    public EntranceValidator(String expression, int concatenatingLength, int secuenceLength) {
        this.exp = expression;
        this.n = concatenatingLength;
        this.secuenceLength = secuenceLength;
    }

    public String validatedExpression() {
        char[] characters = exp.toCharArray();
        String result = "";
        Pattern pattern = Pattern.compile("^\\d+$|(^\\d+[A-Z])|\\s");
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) {//Si la expresión no es evaluable (números al comienzo de la exp), es solo un número o es vacia entoces hago una devolución excepcional
            return exceptionalExpression(exp);
        }
        for (int i = 0; i < characters.length; i++) {
            try {
                Integer.valueOf(String.valueOf(characters[i+1]));//esto lo tomo como condicion para saber si el que biene es entero o esta fuera de rango
                result += characters[i] + "{" + characters[i+1];
                for (int j = i+1; j < characters.length; j++) {
                    try {
                        Integer.valueOf(String.valueOf(characters[j + 1]));//esto lo tomo como condicion para saber si el que biene es entero o esta fuera de rango
                        result += characters[j + 1];
                        i = j+1;
                    } catch (Exception e) {
                        result += "}";
                        i = j;
                        j = characters.length;
                    }
                }
            } catch (Exception e) {
                result += characters[i] + "+";
                try {
                    if(characters[i] == characters[i+1]){//no es evaluable una expresion con dos motivos iguales seguidos
                        errorPosition = i;
                        expressionResult = result;
                        return "";
                    }
                } catch (Exception ex) {
                }
            }
        }
        return result;
    }

    private String exceptionalExpression(String exp) {
        if (exp.equals(" ")) {//Si la exp es nula se coge toda la secuencia completa
            return ".+";
        }
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(exp);
        if (matcher.find()) {//Si la exp es solo un número pica la cadena en trozos de esa longitud
            return ".{" + exp + "}";
        }
        return "";//Si la exp comienza con números o tiene dos caracteres repetidos no es evaluable
    }

    public int validatedN() {
        return (n < 0 || n > secuenceLength) ? 0 : n;
    }

    public String getExpressionResult() {
        return expressionResult;
    }

    public String getExp() {
        return exp;
    }

    public int getN() {
        return n;
    }

    public int getSecuenceLength() {
        return secuenceLength;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
    
}
