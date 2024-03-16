package Class;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Liuben
 */
public class ErrorCorrector {

    int length = 10000;//no se trabajara en secuencias de proteinas con valores mucho mayores de cien

    public ErrorCorrector() {
    }

    public ErrorCorrector(int length) {
        this.length = length;
    }

    public String suggestExpression(String expression) {
        try {
            int l = Integer.parseInt(expression);
            if (l > length) {
                return String.valueOf(length);
            }
        } catch (Exception e) {
        }
        for (int i = 0; i < expression.length(); i++) {
            Pattern p = Pattern.compile(expression.charAt(i) + "{2,}");//si tiene dos motivos seguidos
            Matcher m = p.matcher(expression);
            try {
                Integer.parseInt(String.valueOf(expression.charAt(i)));
            } catch (Exception e) {
                if (m.find()) {
                    int s = m.end() - m.start();
                    expression = expression.substring(0, m.start()) + expression.charAt(i) + s + expression.substring(m.end(), expression.length());
                    i = m.start() + 1;
                }
            }
        }
        Pattern p = Pattern.compile("^\\d{1,}");//si comienza con un numero la expresion
        Matcher m = p.matcher(expression);
        if (m.find()) {
            try {
                int end = m.end();
                int parseInt0 = Integer.parseInt(String.valueOf(expression.substring(0, m.end())));
                int parseInt2 = Integer.parseInt(String.valueOf(expression.charAt(m.end() + 1)));
                int sum = parseInt2 + parseInt0;
                return String.valueOf(expression.charAt(m.end())) + sum + expression.substring(m.end()+2);
            } catch (Exception e) {
                return String.valueOf(expression.charAt(m.end())) + String.valueOf(expression.substring(0, m.end())) + expression.substring(m.end()+1);
            }
        }
        return expression;
    }
}
