package Class;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author Liuben
 */
public class LoadThread extends Thread {

    Kernel k = null;
    JProgressBar jProgressBar1 = null;
    JLabel LoadingProteins = null;
    JLabel jLabelCancelLoad = null;

    public LoadThread(Kernel k, JProgressBar jProgressBar1, JLabel LoadingProteins, JLabel jLabelCancelLoad) {
        this.k = k;
        this.jProgressBar1 = jProgressBar1;
        this.LoadingProteins = LoadingProteins;
        this.jLabelCancelLoad = jLabelCancelLoad;
    }

    @Override
    public void run() {
        try {
            k.DataBasePDBSet(jProgressBar1, LoadingProteins, jLabelCancelLoad);
        } catch (Exception e) {
        }
        if (k.inDataBase.length() > 1) {
            JOptionPane.showMessageDialog(null, "Exist in database: " + k.inDataBase.replace("null ", ""));
        }
        if (k.noInserted.length() > 1) {
            JOptionPane.showMessageDialog(null, "These archives can not be inserted in the data base: " + k.noInserted.replace("null ", ""));
        }
    }
}
