package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PDB {

    private List<String> loadedPDB = null;
    private int length = 0;
    private String pdbId = "";
    private String classification = "";
    private List<String> molecule = new ArrayList<String>();
    private String chains = "";
    private String heteroAtoms = "";
    private String solventAtoms = "";
    private String experiment = "";
    private int[] contSecAA = new int[20];
    private String resolutionRangeHigh = "";
    private String resolutionRangeLow = "";
    private String scientificName = "";
    private String commonOrganism = "";
    private String completeness = "";
    private String reflections = "";
    private String nucleisAcidAtoms = "";
    private String absolutePDBPath = "";
    private String selectedAtom = "CA";
    private String selectedChain = "A";
    List<String> chainsList = new ArrayList<String>();
    List<String> trustedChainsList = new ArrayList<String>();
    private List<String> atomsList = new ArrayList<String>();
    private List<String> secondaryStructureList = new ArrayList<String>();

    public PDB(PDBFileHandler fileHandler, String selectedAtom, String selectedChain) {
        absolutePDBPath = fileHandler.getAbsoluteFilePath();
        this.selectedAtom = (selectedAtom.equals("")) ? "CA" : selectedAtom.toUpperCase();
        this.selectedChain = (selectedChain.equals("")) ? "A" : selectedChain.toUpperCase();
        loadedPDB = fileHandler.LoadFile();
        computePDBStatistics();
        length = atomsList.size();
    }

    private void computePDBStatistics() {
        boolean endMDL = false;
        int seqNum = -1, num = 0;
        for (String line : loadedPDB) {
            if (line.startsWith("HEADER")) {
                pdbId = deleteFinalSpaces(line.substring(62, 79));
                classification = deleteFinalSpaces(line.substring(10, 49));
            }
            if ((line.startsWith("COMPND")) && (line.startsWith("MOLECULE", 11))) {
                molecule.add(deleteFinalSpaces(line.substring(21, 79)));
            }
            if ((line.startsWith("COMPND")) && (line.substring(11, 16).equals("CHAIN"))) {
                int ini = 18;
                while ((!line.substring(ini, (ini + 1)).equals(";")) && (!line.substring(ini, (ini + 1)).equals(" "))) {
                    chainsList.add(line.substring(ini, (ini + 1)));
                    ini += 3;
                }
            }
            if (line.startsWith("SOURCE") && line.contains("ORGANISM_SCIENTIFIC")) {
                scientificName = deleteFinalSpaces(line.substring(32, 79));
            }
            if (line.startsWith("SOURCE") && line.contains("ORGANISM_COMMON")) {
                commonOrganism = deleteFinalSpaces(line.substring(28, 79));
            }
            if (line.startsWith("EXPDTA    ")) {
                experiment = deleteFinalSpaces(line.substring(10, 79));
            }
            if (line.startsWith("REMARK   3   RESOLUTION RANGE HIGH (ANGSTROMS)")) {
                resolutionRangeHigh = deleteFinalSpaces(line.substring(49, 79));
            }
            if (line.startsWith("REMARK   3   RESOLUTION RANGE LOW  (ANGSTROMS)")) {
                resolutionRangeLow = deleteFinalSpaces(line.substring(49, 79));
            }
            if (line.startsWith("REMARK   3   COMPLETENESS (WORKING+TEST)   (%)")) {
                completeness = deleteFinalSpaces(line.substring(49, 79));
            }
            if (line.startsWith("REMARK   3   NUMBER OF REFLECTIONS")) {
                reflections = deleteFinalSpaces(line.substring(49, 79));
            }
            if (line.startsWith("REMARK   3   NUCLEIC ACID ATOMS")) {
                nucleisAcidAtoms = deleteFinalSpaces(line.substring(40, 54));
            }
            if (line.startsWith("REMARK   3   HETEROGEN ATOMS")) {
                heteroAtoms = deleteFinalSpaces(line.substring(40, 54));
            }
            if (line.startsWith("REMARK   3   SOLVENT ATOMS")) {
                solventAtoms = deleteFinalSpaces(line.substring(40, 54));
            }
            if (line.startsWith("SEQRES   1")) {
                try {
                    String AA = (String) line.subSequence(19, 22);
                    int i = (new AminoAcid(AA)).getPosition();
                    if (i >= 0 || i <= 19) {
                        trustedChainsList.add((String) line.subSequence(11, 12));
                    }
                } catch (Exception e) {
                }
            }
            endMDL = extractAtomsList(line, endMDL, seqNum, num);
            extractSecondaryStructureList(line);
            if (endMDL) {
                break;
            }
        }
    }

    private boolean extractAtomsList(String line, boolean endMDL, int seqNum, int num) {
        if (line.startsWith("ENDMDL") || line.startsWith("END")) {
            endMDL = true;
        }
        if (endMDL == false) {
            if (line.startsWith("ATOM") && validateSelectedChain(line)) {
                Scanner sc = new Scanner(line.substring(22, 26));
                num = sc.nextInt();
                if (num != seqNum && line.startsWith(selectedChain, 21) && (line.startsWith(selectedAtom, 13) || line.startsWith(selectedAtom, 12) || line.startsWith("CA  GLY", 13) || line.startsWith("CA  GLY", 12))) {
                    atomsList.add(line.substring(0, 54));
                    seqNum = num;
                    String s = line.substring(17, 20);
                    contSecAA[new AminoAcid(s).getPosition()]++;
                }
            }
        }
        return endMDL;
    }

    private boolean validateSelectedChain(String line) {
        String actualChain = line.substring(21, 22);
        for (String c : chainsList) {
            if (c.equals(selectedChain)) {
                return true;
            }
        }
        return false;
    }

    private void extractSecondaryStructureList(String line) {
        if ((line.startsWith("HELIX") && line.startsWith(selectedChain, 19)) || (line.startsWith("SHEET") && line.startsWith(selectedChain, 21))) {
            secondaryStructureList.add(line.substring(0, 76));
        }
    }

//    public char[][] getSecondaryStructureMap() {
//        List<String> secondaryStructure = secondaryStructureList;
//        int numCol = length;
//        char[][] secondaryStructureMap = new char[numCol][numCol];
//        for (int i = 0; i < numCol - 1; i++) {
//            for (int j = i + 1; j < numCol; j++) {
//                secondaryStructureMap[i][j] = ' ';
//                secondaryStructureMap[j][i] = ' ';
//            }
//        }
//        int coordIniFirst = 0;
//        int coordEndFirst = 0;
//        if (secondaryStructure != null) {
//            for (String s : secondaryStructure) {
//                String start = s.substring(0, 5);
//                int value;
//                int count;
//                Scanner sc = new Scanner(atomsList.get(0).substring(22, 26));
//                int firstValue = sc.nextInt() - 1;
//                if (start.equals("HELIX")) {
//                    try {
//                        sc = new Scanner(s.substring(21, 25));
//                        value = sc.nextInt() - firstValue;
//                        sc = new Scanner(s.substring(71, 76));
//                        count = sc.nextInt();
//                        int despl = 2;
//                        for (int i = 0; i < count; i++) {
//                            secondaryStructureMap[value + i][value + i + despl] = 'H';
//                        }
//                    } catch (Exception e) {
//                    }
//                } else {
//                    try {
//                        sc = new Scanner(s.substring(38, 40));
//                        if (sc.nextInt() != 0) {
//                            sc = new Scanner(s.substring(22, 26));
//                            int coordIniSecond = sc.nextInt() - firstValue;
//                            sc = new Scanner(s.substring(33, 37));
//                            int coordEndSecond = sc.nextInt() - firstValue;
//                            sc = new Scanner(s.substring(50, 54));
//                            int end = sc.nextInt() - firstValue;
//                            sc = new Scanner(s.substring(65, 69));
//                            int ini = sc.nextInt() - firstValue;
//                            while (end >= coordIniSecond && ini <= coordEndFirst) {
//                                if (end > ini) {
//                                    secondaryStructureMap[ini - 1][end - 1] = 'S';
//                                } else {
//                                    secondaryStructureMap[end - 1][ini - 1] = 'S';
//                                }
//                                end--;
//                                ini++;
//                            }
//                            coordIniFirst = coordIniSecond;
//                            coordEndFirst = coordEndSecond;
//                        } else {
//                            sc = new Scanner(s.substring(22, 26));
//                            coordIniFirst = sc.nextInt();
//                            sc = new Scanner(s.substring(33, 37));
//                            coordEndFirst = sc.nextInt();
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }
//        return secondaryStructureMap;
//    }

    public String get1DSequence() {
        String sequence = "";
        for (int i = 0; i < length; i++) {
            sequence += new AminoAcid(atomsList.get(i).substring(17, 20)).getOneLetterName();
        }
        return sequence;
    }

    public String get2DSequence() {
        List<String> atoms = secondaryStructureList;
        String[] sequence = new String[length];
        for (int i = 0; i < length; i++) {
            sequence[i] = "C";
        }
        Scanner sc1 = new Scanner(atomsList.get(0).substring(22, 26));
        Scanner sc2;
        int firstValue = sc1.nextInt() - 1;
        for (String s : atoms) {
            String motive = "H";
            if (s.startsWith("HELIX")) {
                sc1 = new Scanner(s.substring(21, 25));
                sc2 = new Scanner(s.substring(33, 37));
            } else {
                sc1 = new Scanner(s.substring(22, 26));
                sc2 = new Scanner(s.substring(33, 37));
                motive = "S";
            }
            int ini = sc1.nextInt() - firstValue;
            int end = sc2.nextInt() - firstValue;
            for (int j = ini; j <= end; j++) {
                sequence[j - 1] = motive;
            }
        }
        String result = "";
        for (int i = 0; i < length; i++) {
            result += sequence[i];
        }
        return result;
    }

    private String deleteFinalSpaces(String string) {
        while (string.endsWith(" ") || string.endsWith(";")) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    // <editor-fold desc="Properties...">
    public String getStringOfChains() {
        String chain = "";
        for (int c = 0; c < chainsList.size(); c++) {
            if (c != 0) {
                chain += ", ";
            }
            chain += chainsList.get(c);
        }
        return chain;
    }

    public String getSelectedChain() {
        return selectedChain;
    }

    public List<String> getPDBStatistics(String atom, String chain) {
        chains = getStringOfChains();
        List<String> fo = new ArrayList<String>();
        fo.add("PDB............................................ " + pdbId);
        fo.add("Classification................................. " + classification);
        for (String mol : molecule) {
            fo.add("Molecule...................................... " + mol);
        }
        fo.add("Chains........................................ " + chains);
        fo.add("Scientific name.............................. " + scientificName);
        fo.add("Common organism.......................... " + commonOrganism);
        fo.add("");
        fo.add("Experient..................................... " + experiment);
        fo.add("Resolution [range high] (angstroms)..... " + resolutionRangeHigh);
        fo.add("Resolution [range low] (angstroms)...... " + resolutionRangeLow);
        fo.add("Completeness [working + test] (%)...... " + completeness);
        fo.add("Number of reflections....................... " + reflections);
        fo.add("");
        fo.add("Estatistics:");
        fo.add("Nucleis acid atoms.......................... " + nucleisAcidAtoms);
        fo.add("Heterogen atoms........................... " + heteroAtoms);
        fo.add("Solvent atoms............................... " + solventAtoms);
        fo.add("");
        fo.add("Atoms:");
        for (int i = 0; i < 20; i++) {
            if (contSecAA[i] != 0) {
                fo.add(new AminoAcid(i).getThreeLettersName() + " (" + new AminoAcid(i).getOneLetterName() + ") : " + contSecAA[i]);
            }
        }
        fo.add("");
        fo.add("Total: " + length);
        return fo;
    }

    public List<String> getAtomsList() {
        return atomsList;
    }

    public List<String> getSecondaryStructure() {
        return secondaryStructureList;
    }

    public List<String> getLoadedPDB() {
        return loadedPDB;
    }

    public int Length() {
        return length;
    }

    public String PDBId() {
        return pdbId;
    }

    public String ProteinClassification() {
        return classification;
    }

    public List<String> Molecule() {
        return molecule;
    }

    public String HeteroAtoms() {
        return heteroAtoms;
    }

    public String SolventAtoms() {
        return solventAtoms;
    }

    public String ExperimentalMethod() {
        return experiment;
    }

    public String ResolutionRangeHigh() {
        return resolutionRangeHigh;
    }

    public String ResolutionRangeLow() {
        return resolutionRangeLow;
    }

    public String ScientificName() {
        return scientificName;
    }

    public String CommonOrganism() {
        return commonOrganism;
    }

    public String Completeness() {
        return completeness;
    }

    public String Reflections() {
        return reflections;
    }

    public String NucleicAcidAtoms() {
        return nucleisAcidAtoms;
    }

    public String AbsolutePDBPath() {
        return absolutePDBPath;
    }
// </editor-fold>
}
