package Class;

import java.util.Scanner;

public class Internationalization {

    static public boolean convertCommaSeparator() {
        boolean conversion = false;
        try {
            Scanner sc = new Scanner("0,0");
            Float f = sc.nextFloat();
            conversion = true;
        } catch (Exception e) {
        }
        return conversion;
    }
}
