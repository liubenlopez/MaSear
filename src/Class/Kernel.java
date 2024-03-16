/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author Liuben
 */
public class Kernel {

    private String dirData = "";
    private PDB pdb = null;
    String inDataBase = null;
    ArrayList<Object[]> PDBLoaded = new ArrayList<Object[]>();
    String dirDB = "";
    String nameDB = "";
    String connectionString = "";
    int totalPath = 0;
    String noInserted = "";
    ArrayList<String> subsequences = new ArrayList<String>();//estos dos son para el analicis de peptidos
    ArrayList<String> idProtein = new ArrayList<String>();
    ArrayList<String> subsequencesExpression = new ArrayList<String>();

    public Kernel() {
    }

    public Kernel(String connectionString) {
        this.connectionString = connectionString;
    }

    public Kernel(String dirDB, String nameDB) {
        this.dirDB = dirDB;
        this.nameDB = nameDB;
        connectionString = "jdbc:sqlite:" + dirDB + "/" + nameDB + ".db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] LoadPDBPathArray(String dir) {
        String[] fileList = null;
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle("Select proteins get");
        jfc.setCurrentDirectory(new File(dir));
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = jfc.getSelectedFile().getAbsolutePath();
            setPath(path, "path/AbsolutePaht.txt");
            fileList = new File(jfc.getSelectedFile().toString()).list();
            if (fileList == null) {
                fileList = new String[jfc.getSelectedFiles().length];
                int i = 0;
                for (File f : jfc.getSelectedFiles()) {//creo un f de tipo file que va ha coger una de las entradas de jfc
                    fileList[i] = f.getAbsolutePath();
                    i++;
                }
            }
            totalPath = fileList.length;
            return fileList;
        }
        return fileList;
    }

    public boolean DataBasePDBSet(Object[] dataVector) throws ClassNotFoundException {
        Object[] array = new Object[dataVector.length];
        for (int i = 0; i < dataVector.length; i++) {
            if (dataVector[i].equals("NULL")) {
                array[i] = "";
            } else {
                if (dataVector[i].toString().contains("'")) {
                    array[i] = dataVector[i].toString().replace("'", "^");//esto es para cuando se encuentre con una molecula como esta: DNA (5'-D(*AP*GP*CP*GP*TP*GP*GP*GP*CP*AP*C)-3') me cambie los valores a ^ y no cierre en codigo SQL
                } else {
                    array[i] = dataVector[i];
                }
            }
        }
        String PDBName = "'" + array[0] + "'";
        String ProteinName = "'" + array[1] + "'";
        String Molecule = "'" + array[2] + "'";
        String Length = "'" + array[3] + "'";
        String Chains = "'" + array[4] + "'";
        String HeteroAtoms = "'" + array[5] + "'";
        String SolventAtoms = "'" + array[6] + "'";
        String ExperimentalMethod = "'" + array[7] + "'";
        String ResolutionRangeLow = "'" + array[8] + "'";
        String ResolutionRangeHigh = "'" + array[9] + "'";
        String ScientificName = "'" + array[10] + "'";
        String CommonOrganism = "'" + array[11] + "'";
        String Completeness = "'" + array[12] + "'";
        String Reflections = "'" + array[13] + "'";
        String NucleisAcidAtoms = "'" + array[14] + "'";
        String AbsolutePath = "'" + array[15] + "'";
        String Structure1D = "'" + array[16] + "'";
        String Structure2D = "'" + array[17] + "'";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet IDEM = stat.executeQuery("SELECT IDExpType FROM ExperimentType WHERE TypeEM =" + ExperimentalMethod);
            String IDExpType = "'" + getIDExpType(IDEM, ExperimentalMethod) + "'";
            ResultSet IDCO = stat.executeQuery("SELECT IDCommOrg FROM CommonOrganism WHERE TypeCO =" + CommonOrganism);
            String IDCommOrg = "'" + getIDCommOrg(IDCO, CommonOrganism) + "'";
            stat.executeUpdate("INSERT INTO Proteins VALUES (" + PDBName + "," + ProteinName + "," + Molecule + "," + Length + ", " + Chains + "," + HeteroAtoms + "," + SolventAtoms + "," + IDExpType + "," + ResolutionRangeLow + "," + ResolutionRangeHigh + "," + ScientificName + "," + IDCommOrg + "," + Completeness + "," + Reflections + "," + NucleisAcidAtoms + "," + AbsolutePath + "," + Structure1D + "," + Structure2D + ");");
            stat.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean CreateDBWithTables() {/*Para probar si esta la tabla creada la tabla Proteins*/
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE Proteins('PDBName' TEXT PRIMARY KEY  NOT NULL,'ProteinName' TEXT,'Molecule' TEXT,'Length' INTEGER,'Chains' TEXT,'HeteroAtoms' INTEGER,'SolventAtoms' TEXT,'ExperimentalMethod' INTEGER,'ResolutionRangeLow' REAL,'ResolutionRangeHigh' REAL,'ScientificName' TEXT,'CommonOrganism' TEXT,'Completeness' REAL,'Reflections' TEXT,'NucleisAcidAtoms' REAL,'AbsolutePath' TEXT,'Structure1D' TEXT,'Structure2D' TEXT);");
            stat.executeUpdate("CREATE TABLE ExperimentType('IDExpType' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,'TypeEM' TEXT);");
            stat.executeUpdate("CREATE TABLE CommonOrganism('IDCommOrg' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,'TypeCO' TEXT);");
            stat.executeUpdate("INSERT INTO CommonOrganism ('IDCommOrg','TypeCO') VALUES ('-1','');");//para poder comparar con los que no tienen common organism
            stat.executeUpdate("CREATE TABLE Subsequences ('PDBID' TEXT NOT NULL,'Expression' TEXT NOT NULL , 'Subsequences' TEXT NOT NULL, PRIMARY KEY ('PDBID', 'Expression'));");
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean CreateDBWithTables(String path_name) {/*Para probar si esta la tabla creada la tabla Proteins*/
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + path_name);
            java.sql.Statement stat = conn.createStatement();
            stat.executeUpdate("CREATE TABLE Proteins('PDBName' TEXT PRIMARY KEY  NOT NULL,'ProteinName' TEXT,'Molecule' TEXT,'Length' INTEGER,'Chains' TEXT,'HeteroAtoms' INTEGER,'SolventAtoms' TEXT,'ExperimentalMethod' INTEGER,'ResolutionRangeLow' REAL,'ResolutionRangeHigh' REAL,'ScientificName' TEXT,'CommonOrganism' TEXT,'Completeness' REAL,'Reflections' TEXT,'NucleisAcidAtoms' REAL,'AbsolutePath' TEXT,'Structure1D' TEXT,'Structure2D' TEXT);");
            stat.executeUpdate("CREATE TABLE ExperimentType('IDExpType' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,'TypeEM' TEXT);");
            stat.executeUpdate("CREATE TABLE CommonOrganism('IDCommOrg' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,'TypeCO' TEXT);");
            stat.executeUpdate("CREATE TABLE Subsequences ('PDBID' TEXT NOT NULL,'Expression' TEXT NOT NULL , 'Subsequences' TEXT NOT NULL, PRIMARY KEY ('PDBID', 'Expression'));");
            stat.executeUpdate("INSERT INTO CommonOrganism ('IDCommOrg','TypeCO') VALUES ('-1','');");//para poder comparar con los que no tienen common organism
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean DeletePDBInDataBase(String PDB) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            stat.execute("DELETE FROM Proteins WHERE PDBName = " + "'" + PDB + "'");
            stat.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getAbsolutePathOfDataBase(String PDBNameId) {
        try {
            String PDBName = "'" + PDBNameId.toUpperCase() + "'";
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT AbsolutePath FROM Proteins WHERE PDBName = " + PDBName);
            String dir = result.getString("AbsolutePath");
            stat.close();
            conn.close();
            return dir;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void SavePDBInFile(String Sourse, String Target) {
        try {
            File file = new File(Sourse);
            if (!file.exists()) {
                file.mkdir();
            }
            FileChannel in = (new FileInputStream(new File(Sourse))).getChannel();
            FileChannel out = (new FileOutputStream(new File(Target))).getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }

    public ArrayList<String> getPDBTarget(ArrayList<String> Pahts) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select file to proteins set");
        jfc.setCurrentDirectory(new File(getPath("path/OutPaht.txt")));
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showOpenDialog(null);
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < Pahts.size(); i++) {
            String s = Pahts.get(i).replace("\\", " ");
            String[] array = s.split(" ");
            String name = array[array.length - 1];
            result.add(jfc.getSelectedFile().getAbsolutePath() + "\\" + name);
        }
        setPath(jfc.getSelectedFile().getAbsolutePath(), "path/OutPaht.txt");
        return result;
    }

    public String getPath(String dirI) {
        String path = "";
        try {
            FileInputStream dir = new FileInputStream(dirI);
            ObjectInputStream ois = new ObjectInputStream(dir);
            path = (String) ois.readObject();
            ois.close();
            dir.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("error");
        } catch (FileNotFoundException ex) {
            System.out.println("error");
        } catch (IOException ex) {
            System.out.println("error");
        }
        return path;
    }

    public void setPath(String path, String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(path);
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Object[]> Searcher(Object Identifyer) {
        String identy = "'" + (String) Identifyer + "'";
        String mayuscula = identy.toUpperCase();
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("SELECT PDBName,ProteinName,Molecule,Length,Chains,HeteroAtoms,SolventAtoms,TypeEM,ResolutionRangeLow,ResolutionRangeHigh,ScientificName,TypeCO,Completeness,Reflections,NucleisAcidAtoms,AbsolutePath,Structure1D,Structure2D FROM Proteins,ExperimentType,CommonOrganism WHERE PDBName=" + mayuscula + " AND IDExpType = ExperimentalMethod AND IDCommOrg = CommonOrganism");
            while (resultSet.next()) {
                Object[] row = new Object[18];
                for (int j = 1; j <= row.length; j++) {
                    try {
                        if (resultSet.getString(j).contains("^")) {
                            row[j - 1] = resultSet.getString(j).replace("^", "'");//porque yo guardo con ^ en vez de '
                        } else {
                            row[j - 1] = resultSet.getString(j);
                        }
                    } catch (Exception e) {
                    }
                }
                result.add(row);
            }
            stat.close();
            conn.close();
            return result;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public ArrayList<Object[]> Searcher(Object[] array) {
        inDataBase = "";
        PDBLoaded.clear();
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            String selector = Selector((String[]) array);
            ResultSet resultSet = stat.executeQuery("SELECT PDBName,ProteinName,Molecule,Length,Chains,HeteroAtoms,SolventAtoms,TypeEM,ResolutionRangeLow,ResolutionRangeHigh,ScientificName,TypeCO,Completeness,Reflections,NucleisAcidAtoms,AbsolutePath,Structure1D,Structure2D FROM Proteins,ExperimentType,CommonOrganism WHERE " + selector + " AND IDExpType = ExperimentalMethod AND IDCommOrg = CommonOrganism");
            while (resultSet.next()) {
                Object[] row = new Object[18];
                for (int j = 1; j <= row.length; j++) {
                    try {
                        if (resultSet.getString(j).contains("^")) {
                            row[j - 1] = resultSet.getString(j).replace("^", "'");//porque yo guardo con ^ en vez de '
                        } else {
                            row[j - 1] = resultSet.getString(j);
                        }
                    } catch (Exception e) {
                    }
                }
                result.add(row);
            }
            stat.close();
            conn.close();
            return result;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String Selector(String[] properties) {
        String selected = "";
        if (properties[0] != null) {
            selected += "PDBName= '" + properties[0] + "' AND ";
        }
        if (properties[1] != null) {
            selected += "ProteinName='" + properties[1] + "' AND ";
        }
        if (properties[2] != null) {
            selected += "Molecule='" + properties[2] + "' AND ";
        }
        if (properties[3] != null) {
            String i = properties[3];
            String[] split = i.split("&");
            if (split[1].equals("-1")) {
                selected += "Length > " + split[0] + " AND ";
            } else {
                selected += "Length > " + split[0] + " AND Length < " + split[1] + " AND ";
            }
        }
        if (properties[4] != null) {
            selected += "Chains= '" + properties[4] + "' AND ";
        }
        if (properties[5] != null) {
            selected += "HeteroAtoms > " + properties[5] + " AND HeteroAtoms != ''  AND ";
        }
        if (properties[6] != null) {
            selected += "SolventAtoms = '" + properties[6] + "' AND ";
        }
        if (properties[7] != null) {
            String expM = properties[7];
            String[] split = expM.split(";");
            selected += "(";
            for (int i = 0; i < split.length; i++) {
                selected += "TypeEM = '" + split[i] + "' OR ";
            }
            selected = selected.substring(0, selected.length() - 4) + ")" + " AND ";
        }
        if (properties[8] != null) {
            String i = properties[8];
            selected += "ResolutionRangeLow >= " + i.replace(",", ".") + " AND ResolutionRangeLow != ''  AND ";
        }
        if (properties[9] != null) {
            String i = properties[9];
            if (!properties[9].equals("-1")) {
                selected += "ResolutionRangeHigh <=" + i.replace(",", ".") + " AND ResolutionRangeHigh != ''  AND ";
            }
        }
        if (properties[10] != null) {
            selected += "ScientificName = '" + properties[10] + "' AND ";
        }
        if (properties[11] != null) {
            String CO = properties[11];
            String[] split = CO.split(";");
            selected += "(";
            for (int i = 0; i < split.length; i++) {
                selected += "TypeCO = '" + split[i] + "' OR ";
            }
            selected = selected.substring(0, selected.length() - 4) + ")" + " AND ";
        }
        if (properties[12] != null) {
            selected += "Completeness = '" + properties[11] + "' AND ";
        }
        if (properties[13] != null) {
            selected += "Reflections = '" + properties[13] + "' AND ";
        }
        if (properties[14] != null) {
            selected += "NucleisAcidAtoms >" + properties[14] + " AND NucleisAcidAtoms != '' AND ";
        }
        return selected.substring(0, selected.length() - 5);
    }

    public boolean existDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            stat.executeQuery("SELECT * FROM ExperimentType WHERE ID = ''");
            stat.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<String> getExperimentalMethods() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("SELECT * FROM ExperimentType");
            while (resultSet.next()) {
                String row = resultSet.getString(2).replace("^", "'");
                result.add(row);
            }
            stat.close();
            conn.close();
            return result;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void setExperimentalMethod(String EM) {
        if (!EM.isEmpty()) {
            try {
                String ExpMethod = "'" + EM + "'";
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                if (!isExpMethod(ExpMethod)) {
                    stat.executeUpdate("INSERT INTO ExperimentType ('TypeEM') VALUES (" + ExpMethod + ");");
                }
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void setCommonOrganism(String CO) {
        if (!CO.isEmpty()) {
            try {
                String CommOrg = "'" + CO + "'";
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                if (!isCommOrg(CommOrg)) {
                    stat.executeUpdate("INSERT INTO CommonOrganism ('TypeCO') VALUES (" + CommOrg + ");");
                }
                stat.close();
                conn.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isResultEmpty(ResultSet result) {
        try {
            result.getBoolean("IDExpType");//esto es solo para controlar que este devolviendo algo porque si no me devuelve nada me explota en el otro metodo y no termina
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            result.getBoolean("IDCommOrg");//esto es solo para controlar que este devolviendo algo porque si no me devuelve nada me explota en el otro metodo y no termina
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private String getIDExpType(ResultSet IDEM, String ExperimentalMethod) {
        String ID = "-1";
        if (isResultEmpty(IDEM)) {
            try {
                setExperimentalMethod(ExperimentalMethod.substring(1, ExperimentalMethod.length() - 1));
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                ResultSet executeQuery = stat.executeQuery("SELECT IDExpType FROM ExperimentType WHERE TypeEM =" + ExperimentalMethod + "");
                ID = executeQuery.getString("IDExpType");
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ID = IDEM.getString("IDExpType");
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ID;
    }

    private String getIDCommOrg(ResultSet IDCO, String CommonOrganism) {
        String ID = "-1";
        if (isResultEmpty(IDCO)) {
            try {
                setCommonOrganism(CommonOrganism.substring(1, CommonOrganism.length() - 1));
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                ResultSet executeQuery = stat.executeQuery("SELECT IDCommOrg FROM CommonOrganism WHERE TypeCO =" + CommonOrganism + "");
                ID = executeQuery.getString("IDCommOrg");
                stat.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ID = IDCO.getString("IDCommOrg");
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ID;
    }

    private boolean isExpMethod(String ExpMethod) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet executeQuery = stat.executeQuery("SELECT * FROM ExperimentType WHERE TypeEM = " + ExpMethod);
            String retusl = executeQuery.getString("TypeEM");
            stat.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }
        return false;
    }

    private boolean isCommOrg(String CommOrg) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet executeQuery = stat.executeQuery("SELECT * FROM CommonOrganism WHERE TypeCO = " + CommOrg);
            String retusl = executeQuery.getString("TypeCO");
            stat.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }
        return false;
    }

    public void DataBasePDBSet(JProgressBar jProgressBar1, JLabel LoadingProteins, JLabel jLabelCancelLoad) {//le paso el progressbar solo para poder incrementar la barra, debo hacerlo de otra manera
        dirData = getPath("path/AbsolutePaht.txt");//busco en el fichero la ultima direccion indexada
        String[] PDBLoadedPath = LoadPDBPathArray(dirData);//guardo todas las direcciones absolutas que encontro el JFileChooser
        inDataBase = "";//borro los que tiene cargados de la busqueda anterior
        PDBLoaded.clear();
        if (PDBLoadedPath != null) {
            LoadingProteins.setVisible(true);
            jLabelCancelLoad.setEnabled(true);
            for (int i = 0; i < PDBLoadedPath.length; i++) {
                jProgressBar1.setValue((PDBLoaded.size() + 1) * 100 / totalPath);
                pdb = new PDB(new PDBFileHandler(PDBLoadedPath[i]), "CA", "A");
                try {
                    Object[] row = new Object[]{pdb.PDBId(), pdb.ProteinClassification(), pdb.Molecule(), pdb.Length(), pdb.chainsList, pdb.HeteroAtoms(), pdb.SolventAtoms(), pdb.ExperimentalMethod(), pdb.ResolutionRangeHigh(), pdb.ResolutionRangeLow(), pdb.ScientificName(), pdb.CommonOrganism(), pdb.Completeness(), pdb.Reflections(), pdb.NucleicAcidAtoms(), PDBLoadedPath[i], pdb.get1DSequence(), pdb.get2DSequence()};
                    if (DataBasePDBSet(row)) {
                        PDBLoaded.add(row);
                    } else {
                        inDataBase += pdb.PDBId() + " ";
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    noInserted += PDBLoadedPath[i].substring(PDBLoadedPath[i].lastIndexOf("\\")) + " ";
                }
            }
        }
        LoadingProteins.setVisible(false);
        jLabelCancelLoad.setEnabled(false);
        jProgressBar1.setValue(0);
    }

    private Object[][] listToArray(ArrayList<Object[]> PDBLoaded) {
        Object[][] result = null;
        try {
            result = new Object[PDBLoaded.size()][PDBLoaded.get(0).length];
            for (int i = 0; i < PDBLoaded.size(); i++) {
                System.arraycopy(PDBLoaded.get(i), 0, result[i], 0, 5);
            }
        } catch (Exception e) {
        }
        return result;

    }

    public Object[][] getModelByRows() {
        return listToArray(PDBLoaded);
    }

    public ArrayList<Object[]> getPDBLoaded() {
        return PDBLoaded;
    }

    public String getInDataBase() {
        return inDataBase;
    }

    public void splitTextBySemicolonForSearch(String text) {
        String[] idSplit = text.split(";");
        inDataBase = "";
        PDBLoaded.clear();
        for (int i = 0; i < idSplit.length; i++) {
            ArrayList<Object[]> Searcher = Searcher(idSplit[i]);
            if (Searcher.isEmpty() && !idSplit[i].equals("")) {
                inDataBase += idSplit[i] + ";";
            }
            for (int j = 0; j < Searcher.size(); j++) {
                PDBLoaded.add(Searcher.get(j));
            }
        }
    }

    public void SearcherAvanced(String[] array) {
        PDBLoaded = Searcher(array);
    }

    public ArrayList<String> getCommonOrgenism() {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("SELECT * FROM CommonOrganism");
            resultSet.next();//esto para que se salte el primero que es -1,""
            while (resultSet.next()) {
                String row = resultSet.getString(2).replace("^", "'");
                result.add(row);
            }
            stat.close();
            conn.close();
            return result;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public String getNameDB() {
        return nameDB;
    }

    public String getDirDB() {
        return dirDB;
    }

    public String getPathOfExistDB() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Select data base...");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public String getTarget() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setDialogTitle("Set name to new data base finished in *.db or *.sqlite...");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }

    public boolean getSubsequence(String PDBName, String Expression) {
        String PDBID = "";
        String Express = "";
        String subseq = "";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
            java.sql.Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("SELECT * FROM Subsequences WHERE PDBID = '" + PDBName + "' AND Expression = '" + Expression + "'");
            PDBID = resultSet.getString("PDBID");
            Express = resultSet.getString("Expression");
            subseq = resultSet.getString("Subsequences");
            stat.close();
            conn.close();
        } catch (SQLException ex) {
            return false;
        } catch (ClassNotFoundException ex) {
            return false;
        }

        subseq = subseq.replaceAll("\\[", "");
        String[] split = subseq.split("\\]");
        subsequences.addAll(Arrays.asList(split));
        Express = Express.replaceAll("\\[", "");
        String[] split1 = Express.split("\\]");
        subsequencesExpression.addAll(Arrays.asList(split1));
        for(int i=0;i<subsequences.size();i++){
            idProtein.add(PDBID);
        }
        return true;
    }

    public boolean saveSubsequence(String PDBID, String subsequencesExpression, String subsequence) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn;
            try {
                conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                stat.executeUpdate("INSERT INTO Subsequences ('PDBID','Expression','Subsequences') VALUES ('" + PDBID + "'," + "'" + subsequencesExpression + "'" + ",'" + subsequence + "');");
                stat.close();
                conn.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conn = DriverManager.getConnection(connectionString.isEmpty() ? "jdbc:sqlite:database/DataBase.db" : connectionString);
                java.sql.Statement stat = conn.createStatement();
                stat.executeUpdate("UPDATE Subsequences SET Subsequences = '" + subsequence + "' WHERE PDBID = '" + PDBID + "'");
                stat.close();
                conn.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }


        return false;
    }

    public Object[][] getModelByColumns(int selected) {
        Object[] properties = PDBLoaded.get(selected);
        Object[] names = new Object[]{"PDBName", "Classification", "Molecule", "Length", "Chains", "HeteroAtoms", "SolventAtoms", "Experimental Method", "ResolutionRangeLow", "ResolutionRangeHigh", "ScientificName", "Common Organism", "Completeness", "Reflections", "NucleisAcidAtoms", "Inserted from", "Sequence", "Structure2D"};
        Object[][] result = new Object[names.length][2];
        for (int i = 0; i < names.length; i++) {
            result[i][0] = names[i];
            result[i][1] = properties[i];
        }
        return result;
    }

    public int getTotalPath() {
        return totalPath;
    }

    public Object[][] getSubsequencesModel() {
        Object[][] result = new Object[subsequences.size()][3];
        for (int j = 0; j < subsequences.size(); j++) {
            result[j][0] = idProtein.get(j);
            result[j][1] = subsequences.get(j);
            result[j][2] = subsequencesExpression.get(j);
        }
        return result;
    }

    public Object[][] getSubsequencesModel(String text, int i, int[] selectedRows, int selectedRowCount, JTextField jExpression) {
        subsequences.clear();
        idProtein.clear();
        subsequencesExpression.clear();
        for (int j = 0; j < selectedRowCount; j++) {
            Object[] get = getPDBLoaded().get(selectedRows[j]);
            PeptideAnalysis pa = new PeptideAnalysis((String) get[16], (String) get[17]);
            ArrayList<String> sequences = (ArrayList<String>) pa.getSecuence(text, i);
            ArrayList<String> expression = pa.getExpressions();
            for (int k = 0; k < sequences.size(); k++) {
                idProtein.add((String) get[0]);
            }
            subsequences.addAll(sequences);
            subsequencesExpression.addAll(expression);
        }
        Object[][] result = new Object[subsequences.size()][3];
        for (int j = 0; j < subsequences.size(); j++) {
            result[j][0] = idProtein.get(j);
            result[j][1] = subsequences.get(j);
            result[j][2] = subsequencesExpression.get(j);
        }
        return result;
    }

    public Object[][] applyFilter(String text, int i, int[] selectedRows, int selectedRowCount, String text0, String text1, Object selectedItem, JTextField jExpression) {
        subsequences.clear();
        idProtein.clear();
        for (int j = 0; j < selectedRowCount; j++) {
            Object[] get = getPDBLoaded().get(selectedRows[j]);
            PeptideAnalysis pa = new PeptideAnalysis((String) get[16], (String) get[17]);
            ArrayList<String> sequences = (ArrayList<String>) pa.getSecuence(text, i);
            if (!text0.equals("") || !text1.equals("")) {
                ArrayList<String> DAAlist = new ArrayList<String>();
                if (!text0.equals("")) {
                    String[] split = text0.toUpperCase().split(";");
                    DAAlist.addAll(Arrays.asList(split));
                }
                ArrayList<String> UAAlist = new ArrayList<String>();
                if (!text1.equals("")) {
                    String[] split1 = text1.toUpperCase().split(";");
                    UAAlist.addAll(Arrays.asList(split1));
                }
                sequences = (ArrayList<String>) pa.matchAminoAcids(sequences, DAAlist, UAAlist);
            }
            if (selectedItem.equals(1)) {
                sequences = (ArrayList<String>) pa.matchHidrofobicity(sequences, "NoHidrofobic");
            }
            if (selectedItem.equals(2)) {
                sequences = (ArrayList<String>) pa.matchHidrofobicity(sequences, "Hidrofobic");
            }
            if (selectedItem.equals(3)) {
                sequences = (ArrayList<String>) pa.matchHidrofobicity(sequences, "?");
            }
            for (int k = 0; k < sequences.size(); k++) {
                idProtein.add((String) get[0]);
            }
            subsequences.addAll(sequences);
        }

        Object[][] result = new Object[subsequences.size()][2];
        for (int j = 0; j < subsequences.size(); j++) {
            result[j][0] = idProtein.get(j);
            result[j][1] = subsequences.get(j);
        }
        return result;
    }

    public boolean saveSubsequence(String text) {
        boolean result = false;
        for (int j = 0; j < subsequences.size(); j++) {
            String subS = "";
            while (j != subsequences.size()) {
                if (j + 1 == subsequences.size()) {
                    subS += "[" + subsequences.get(j) + "]";
                } else {
                    if (idProtein.get(j).equals(idProtein.get(j + 1))) {
                        subS += "[" + subsequences.get(j) + "]";
                    }
                }
                j++;
            }
            result = saveSubsequence(idProtein.get(j - 1), text, subS);
        }
        return result;
    }

    public boolean saveSubsequenceInFile() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select file to save");
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showOpenDialog(null);
        String subS = idProtein.get(0);
        for (int i = 0; i < subsequences.size(); i++) {
            subS += "[" + subsequences.get(i) + "] ";
            if (i + 1 < subsequences.size() && !idProtein.get(i).equals(idProtein.get(i + 1))) {
                subS += " " + idProtein.get(i + 1);
            }
        }
        try {
            PrintWriter pw = new PrintWriter(jfc.getSelectedFile().getAbsolutePath().endsWith(".txt") ? jfc.getSelectedFile().getAbsolutePath() : jfc.getSelectedFile().getAbsolutePath() + "\\Subsequeces.txt");
            pw.println(subS);
            pw.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Kernel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String correctError(String text, int[] selectedRows, JTextField jExpression) {
        int min = 1000000;
        for (int i = 0; i < selectedRows.length; i++) {
            Object[] get = getPDBLoaded().get(selectedRows[i]);
            if (min > ((String) get[16]).length()) {
                min = ((String) get[16]).length();
            }
        }
        ErrorCorrector ec = new ErrorCorrector(min);
        String textCorrected = ec.suggestExpression(text);
        if (!text.equals(textCorrected)) {
            int select = JOptionPane.showConfirmDialog(null, "The expression is not valid. Maybe want to say: " + textCorrected, "", 0);
            if (select == 0) {
                jExpression.setText(textCorrected);
            }
            return textCorrected;
        }
        return text;
    }

    public boolean loadSubsequences(String text, int[] selectedRows) {
        subsequences.clear();
        idProtein.clear();
        subsequencesExpression.clear();
        boolean result = false;
        for (int i = 0; i < selectedRows.length; i++) {
            result = getSubsequence((String) PDBLoaded.get(selectedRows[i])[0], text);
        }
        return result;
    }

}
