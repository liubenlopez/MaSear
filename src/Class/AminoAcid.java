package Class;



public class AminoAcid {

    private String[] codeAA0 = {"Alanina", "Arginina", "Asparagina", "Ácido aspártico", "Cisteína", "Glutamina", "Ácido glutámico", "Glicina", "Histidina", "Isoleucina", "Leucina", "Lisina", "Metionina", "Fenilalanina", "Prolina", "Serina", "Treonina", "Triptófano", "Tirosina", "Valina"};
    private String[] codeAA1 = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};
    private String[] codeAA3 = {"ALA", "ARG", "ASN", "ASP", "CYS", "GLN", "GLU", "GLY", "HIS", "ILE", "LEU", "LYS", "MET", "PHE", "PRO", "SER", "THR", "TRP", "TYR", "VAL"};
    private String[] hidrofobicidad = {"Hidrofobic", "?", "?", "?", "Hidrofobic", "?", "?", "NoHidrofobic", "?", "Hidrofobic", "Hidrofobic", "?", "Hidrofobic", "Hidrofobic", "NoHidrofobic", "?", "?", "Hidrofobic", "?", "Hidrofobic"};
    private String[] polaridad = {"NoPolar", "Basic+", "Polar", "Acid-", "NoPolar", "Polar", "Acid-", "NoPolar", "Basic+", "NoPolar", "NoPolar", "Basic+", "NoPolar", "NoPolar", "NoPolar", "Polar", "Polar", "NoPolar", "Polar", "NoPolar"};
    private String[] couples = {"AA", "AR", "AN", "AD", "AC", "AQ", "AE", "AG", "AH", "AI", "AL", "AK", "AM", "AF", "AP", "AS", "AT", "AW", "AY", "AV",
        "RR", "RN", "RD", "RC", "RQ", "RE", "RG", "RH", "RI", "RL", "RK", "RM", "RF", "RP", "RS", "RT", "RW", "RY", "RV", "NN", "ND", "NC", "NQ", "NE", "NG", "NH",
        "NI", "NL", "NK", "NM", "NF", "NP", "NS", "NT", "NW", "NY", "NV", "DD", "DC", "DQ", "DE", "DG", "DH", "DI", "DL", "DK", "DM", "DF", "DP", "DS", "DT", "DW",
        "DY", "DV", "CC", "CQ", "CE", "CG", "CH", "CI", "CL", "CK", "CM", "CF", "CP", "CS", "CT", "CW", "CY", "CV", "QQ", "QE", "QG", "QH", "QI", "QL", "QK", "QM",
        "QF", "QP", "QS", "QT", "QW", "QY", "QV", "EE", "EG", "EH", "EI", "EL", "EK", "EM", "EF", "EP", "ES", "ET", "EW", "EY", "EV", "GG", "GH", "GI", "GL", "GK",
        "GM", "GF", "GP", "GS", "GT", "GW", "GY", "GV", "HH", "HI", "HL", "HK", "HM", "HF", "HP", "HS", "HT", "HW", "HY", "HV", "II", "IL", "IK", "IM", "IF", "IP",
        "IS", "IT", "IW", "IY", "IV", "LL", "LK", "LM", "LF", "LP", "LS", "LT", "LW", "LY", "LV", "KK", "KM", "KF", "KP", "KS", "KT", "KW", "KY", "KV", "MM", "MF",
        "MP", "MS", "MT", "MW", "MY", "MV", "FF", "FP", "FS", "FT", "FW", "FY", "FV", "PP", "PS", "PT", "PW", "PY", "PV", "SS", "ST", "SW", "SY", "SV", "TT", "TW",
        "TY", "TV", "WW", "WY", "WV", "YY", "YV", "VV"};
    private int[] H = {3, 10, 4, 3, 3, 6, 4, 1, 6, 9, 9, 10, 7, 2, 6, 3, 5, 3, 3, 7};
    private int[] C = {1, 4, 2, 2, 1, 3, 3, 0, 4, 4, 4, 4, 3, 7, 3, 1, 2, 9, 7, 3};
    private int[] O = {0, 0, 1, 2, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0};
    private int[] S = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
    private int[] N = {0, 2, 1, 0, 0, 1, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0};
    private float[] Nm = {13f, 26f, 17f, 16f, 14f, 20f, 19f, 10f, 20f, 22f, 22f, 24f, 20f, 23f, 17f, 14f, 17f, 27f, 24f, 19f};
    private float[] MM = {89f, 174f, 132f, 133f, 121f, 146f, 147f, 75f, 155f, 131f, 131f, 146f, 149f, 165f, 115f, 105f, 119f, 204f, 181f, 117f};
    private float[] VOL = {88.6f, 173.4f, 114.1f, 111.1f, 108.5f, 143.8f, 138.4f, 60.1f, 153.2f, 166.7f, 166.7f, 168.6f, 162.9f, 189.9f, 112.7f, 89.0f, 116.1f, 227.8f, 193.6f, 140.0f};
    private float[] z1 = {0.07f, 2.88f, 3.22f, 3.64f, 0.71f, 3.08f, 2.18f, 2.23f, 2.41f, -4.44f, -4.19f, 2.84f, -2.49f, -4.92f, -1.22f, 1.96f, 0.92f, -4.75f, -1.39f, -2.69f};
    private float[] z2 = {-1.73f, 2.52f, 1.45f, 1.13f, -0.97f, 0.39f, 0.53f, -5.36f, 1.74f, -1.68f, -1.03f, 1.41f, -0.27f, 1.30f, 0.88f, -1.63f, -2.09f, 3.65f, 2.32f, -2.53f};
    private float[] z3 = {0.09f, -3.44f, 0.84f, 2.36f, 4.13f, -0.07f, -1.14f, 0.30f, 1.11f, -1.03f, -0.98f, -3.14f, -0.41f, 0.45f, 2.23f, 0.57f, -1.40f, 0.85f, 0.01f, -1.29f};
    private float[] ECI = {0.05f, 1.69f, 1.31f, 1.25f, 0.15f, 1.31f, 1.36f, 0.02f, 0.56f, 0.09f, 0.01f, 0.53f, 0.34f, 0.14f, 0.16f, 0.56f, 0.65f, 1.08f, 0.72f, 0.07f};
    private float[] ISA = {62.90f, 52.98f, 17.87f, 18.46f, 78.51f, 30.19f, 19.53f, 19.93f, 87.38f, 149.77f, 154.35f, 102.78f, 132.22f, 189.42f, 122.35f, 19.75f, 59.44f, 179.16f, 132.16f, 120.91f};
    private float[] HWS = {-0.5f, 3f, 0.2f, 3f, -1f, 0.2f, 3f, 0f, -0.5f, -1.8f, -1.8f, 3f, -1.3f, -2.5f, 0f, 0.3f, -0.4f, -3.4f, -2f, 3f, -1.5f};
    private float[] KDS = {1.8f, -4.5f, -3.5f, -3.5f, 2.5f, -3.5f, -3.5f, -0.4f, -3.2f, 4.5f, 3.8f, -3.9f, 1.9f, 2.8f, -1.6f, -0.8f, -0.7f, -0.9f, -1.3f, 4.2f};
    private float[] PI = {6.01f, 10.76f, 5.41f, 2.77f, 5.07f, 3.22f, 5.65f, 5.97f, 7.59f, 6.02f, 5.98f, 9.74f, 5.74f, 5.48f, 6.48f, 5.68f, 5.87f, 5.89f, 5.66f, 5.97f};
    private float[] PA = {1.29f, 0.96f, 0.90f, 1.04f, 1.11f, 1.44f, 1.27f, 0.56f, 1.22f, 0.97f, 1.30f, 1.23f, 1.47f, 1.07f, 0.52f, 0.82f, 0.82f, 0.99f, 0.72f, 0.91f};
    private float[] PB = {0.90f, 0.99f, 0.76f, 0.72f, 0.74f, 0.75f, 0.80f, 0.92f, 1.08f, 1.45f, 1.02f, 0.77f, 0.97f, 1.32f, 0.64f, 0.95f, 1.21f, 1.14f, 1.25f, 1.49f};
    private float[] PT = {0.78f, 0.88f, 1.28f, 1.41f, 0.80f, 1.00f, 0.97f, 1.64f, 0.69f, 0.51f, 0.59f, 0.96f, 0.39f, 0.58f, 1.91f, 1.33f, 1.03f, 0.75f, 1.05f, 0.47f};
    private float[] Lp = {19.20f, 17.80f, 21.72f, 17.14f, 18.83f, 18.55f, 17.31f, 19.48f, 13.97f, 20.76f, 17.65f, 17.05f, 17.88f, 16.81f, 18.55f, 18.91f, 17.15f, 20.94f, 16.86f, 17.88f};
    private float[] Ep = {-77.85f, 108.86f, -55.42f, 47.89f, 160.13f, 134.68f, 53.27f, -148.03f, -4.57f, -104.8f, -148.5f, 47.61f, 46.37f, 47.67f, 169.73f, 30.24f, 46.04f, 178.69f, 49.11f, -106.5f};
    private float[] Hp = {-433.66f, -403.21f, -466.91f, -518.10f, -425.69f, -479.54f, -531.69f, -420.86f, -378.92f, -449.27f, -448.27f, -446.97f, -435.34f, -376.77f, -422.17f, -479.75f, -483.37f, -365.49f, -446.32f, -434.3f};
    private int position = 0;

    public AminoAcid() {
        position = 0;
    }

    public AminoAcid(String aA) {
        for (int i = 0; i < 20; i++) {
            if (codeAA0[i].equals(aA)) {
                position = i;
                break;
            }
            if (codeAA1[i].equals(aA)) {
                position = i;
                break;
            }
            if (codeAA3[i].equals(aA)) {
                position = i;
                break;
            }
        }
    }

    public AminoAcid(int pos) {
        position = pos;
    }

    public int getPosition() {
        return position;
    }

    public int getCouplePosition(String aa1, String aa2) {
        int pos = 0;
        String s1 = aa1 + aa2;
        String s2 = aa2 + aa1;
        for (String s : couples) {
            if (s.equals(s1) || s.equals(s2)) {
                return pos;
            }
            pos++;
        }
        return pos;
    }

    public String getCouple(int pos) {
        return couples[pos];
    }

    // Convert aminoacid code
    public String getCompleteName() {
        return convertAACode(0);
    }

    public String getOneLetterName() {
        return convertAACode(1);
    }

    public String getThreeLettersName() {
        return convertAACode(3);
    }

    private String convertAACode(int codeType) {
        String name = "";
        switch (codeType) {
            case 0:
                name = codeAA0[position];
                break;
            case 1:
                name = codeAA1[position];
                break;
            case 3:
                name = codeAA3[position];
                break;
        }
        return name;
    }

    // Get aminoacid hidrofobic property
    public String getHidrofobicity() {
        return hidrofobicidad[position];
    }

    // Get aminoacid polarization property
    public String getPolarity() {
        return polaridad[position];
    }

    // Get aminoacid chemistry property "carbon, oxygen, sulfur, nitrogen"
    public int getHidrogen() {
        return H[position];
    }

    public int getCarbon() {
        return C[position];
    }

    public int getOxygen() {
        return O[position];
    }

    public int getSulfur() {
        return S[position];
    }

    public int getNitrogen() {
        return N[position];
    }

    // Get aminoacid real properties
    public float getNm() {
        return Nm[position];
    }

    public float getMM() {
        return MM[position];
    }

    public float getVOL() {
        return VOL[position];
    }

    public float getZ1() {
        return z1[position];
    }

    public float getZ2() {
        return z2[position];
    }

    public float getZ3() {
        return z3[position];
    }

    public float getECI() {
        return ECI[position];
    }

    public float getISA() {
        return ISA[position];
    }

    public float getHWS() {
        return HWS[position];
    }

    public float getKDS() {
        return KDS[position];
    }

    public float getPI() {
        return PI[position];
    }

    public float getPA() {
        return PA[position];
    }

    public float getPB() {
        return PB[position];
    }

    public float getPT() {
        return PT[position];
    }

    public float getLp() {
        return Lp[position];
    }

    public float getEp() {
        return Ep[position];
    }

    public float getHp() {
        return Hp[position];
    }

    public String getCodeAA1(int i){
        return codeAA1[i];
    }

}
