/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Class;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liuben
 */
public class sequenceAligment{

    private String dirResult = "";
    private int threshold = 75;
    private List<String> myResults = new ArrayList<String>();
//
//    public void doAction(List<ContactMap> contactMapList) {
//        // Alignment sequences
//        try {
//            int cmCount = contactMapList.size();
//            for (int i = 0; i < cmCount - 1; i++) {
//                for (int j = i + 1; j < cmCount; j++) {
//                    SmithWaterman sw = new SmithWaterman(contactMapList.get(i), contactMapList.get(j));
//                    sw.alignmentSequences();
//                    if (sw.getAlignmentScore() >= threshold) {
//                        myResults.add(contactMapList.get(i).getProteinName() + "," + contactMapList.get(j).getProteinName() + ":" + sw.getAlignmentScore());
//                        String s1 = "";
//                        String s2 = "";
//                        for (int c = 0; c < sw.getMatchCoordSequenceA().size(); c++) {
//                            if (c != 0) {
//                                s1 += ",";
//                                s2 += ",";
//                            }
//                            s1 += "" + sw.getMatchCoordSequenceA().get(c);
//                            s2 += "" + sw.getMatchCoordSequenceB().get(c);
//                        }
//                        myResults.add(s1);
//                        myResults.add(s2);
//                        String seqA = sw.getSeqMatchA();
//                        String seqB = sw.getSeqMatchB();
//                        System.out.println();
//                        System.out.println(seqA + "\t-\t" + contactMapList.get(i).getProteinName());
//                        myResults.add("\n" + seqA + "\t-\t" + contactMapList.get(i).getProteinName());
//                        System.out.println(seqB + "\t-\t" + contactMapList.get(j).getProteinName() + " [" + sw.getAlignmentScore() + "%]");
//                        myResults.add("\n" + seqB + "\t-\t" + contactMapList.get(j).getProteinName() + " [" + sw.getAlignmentScore() + "%]");
//                        iAct = i;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Error: " + e);
//        }
//
//    }
//
//    public List<String> getResults() {
//        myResults.add("\nSequence Alignment successful");
//        return myResults;
//    }
//
//    public String Help() {
//        return "[ Alignment algorithm ]\n\n"
//                + "Implementation of \"Smith-Waterman\" algorithm.\n\n"
//                + "Parameters: Yes \n"
//                + "Identity thrsehold: value of proteins identiy.\n"
//                + "  0: \"Smith-Waterman\" local alignment algorithm.\n"
//                + "  1: \"Needleman-Wunsch\" algorithm.";
//    }
//
//    public void setParameters(String Parameters) {
//        // Get parameters
//        String[] parameters = Parameters.split(",");
//        dirResult = parameters[0];
//        if (parameters.length > 1) {
//            threshold = Integer.valueOf(parameters[1]);
//        }
//    }
//
//    public String[] getParametersList() {
//        String[] paramtersList = {"Result directory", "Identity thrsehold"};
//        return paramtersList;
//    }
//
//    public String[] getParametersValues() {
//        String[] paramtersValues = new String[2];
//        paramtersValues[0] = dirResult;
//        paramtersValues[1] = "" + threshold;
//        return paramtersValues;
//    }
//
//    public void needlemanWunschAlignment(ContactMap cmA, ContactMap cmB) {
//        // Algorithm initicalization
//        int lengthA = cmA.getSequenceLength();
//        int lengthB = cmB.getSequenceLength();
//        int d = -5;
//        int[][] S = new int[20][20];
//        int[][] F = new int[lengthA][lengthB];
//        for (int i = 0; i < lengthA; i++) {
//            F[i][0] = d * i;
//        }
//        for (int j = 0; j < lengthA; j++) {
//            F[0][j] = d * j;
//        }
//        for (int i = 1; i < lengthA; i++) {
//            for (int j = 1; j < lengthA; j++) {
//                int choice1 = F[i - 1][j - 1] + S[new AminoAcid(cmA.getSequence()[i]).getPosition()][new AminoAcid(cmB.getSequence()[j]).getPosition()];
//                int choice2 = F[i - 1][j] + d;
//                int choice3 = F[i][j - 1] + d;
//                F[i][j] = max(choice1, choice2, choice3);
//            }
//        }
//    }
//
//    private int max(int val1, int val2, int val3) {
//        int result = val1;
//        if (result < val2) {
//            result = val2;
//        }
//        if (result < val3) {
//            result = val3;
//        }
//        return result;
//    }
//
//    /**
//     * Class "Smith-Waterman" local alignment algorithm.
//     *
//     * Design Note: this class implements AminoAcids interface: a simple fix
//     * customized to amino acids, since that is all we deal with in this class
//     * Supporting both DNA and Aminoacids, will require a more general design.
//     */
//    private class SmithWaterman {
//
//        private final double scoreThreshold = 19.9;
//        private String[] str1;		// The first input string
//        private String[] str2;		// The second input String
//        private int length1, length2;	// The lengths of the input strings
//        private double[][] score;	// The score matrix. The true scores should be divided by the normalization factor.
//        static final double NORM_FACTOR = 1.0;  // The normalization factor
//        // The similarity function constants
//        static final int MATCH_SCORE = 10;
//        static final int MISMATCH_SCORE = -8;
//        static final int INDEL_SCORE = -9;
//        // Constants of directions.
//        static final int DR_LEFT = 1;   // 0001
//        static final int DR_UP = 2;   // 0010
//        static final int DR_DIAG = 4;   // 0100
//        static final int DR_ZERO = 8;   // 1000
//        // The directions pointing to the cells
//        private int[][] prevCells;
//        // Alignment pairs
//        private List<Integer> coordMatchSeqA = new ArrayList<Integer>();
//        private List<Integer> coordMatchSeqB = new ArrayList<Integer>();
//        private String seqMatchA = "";
//        private String seqMatchB = "";

//        private SmithWaterman(ContactMap cmA, ContactMap cmB) {
//            str1 = cmA.getSequence();
//            str2 = cmB.getSequence();
//            length1 = str1.length;
//            length2 = str2.length;
//            score = new double[length1 + 1][length2 + 1];
//            prevCells = new int[length1 + 1][length2 + 1];
//            buildMatrix();
//        }
//
//        private double similarity(int i, int j) {
//            if (i == 0 || j == 0) {
//                // it's a gap (indel)
//                return INDEL_SCORE;
//            }
//            return (str1[i - 1].equals(str2[j - 1])) ? MATCH_SCORE : MISMATCH_SCORE;
//        }
//
//        // Build the score matrix using dynamic programming.
//        private void buildMatrix() {
//            if (INDEL_SCORE >= 0) {
//                throw new Error("Indel score must be negative");
//            }
//            int i;                      // length of prefix substring of str1
//            int j;                      // length of prefix substring of str2
//            // Base case
//            score[0][0] = 0;
//            prevCells[0][0] = DR_ZERO;  // starting point
//            // The first row
//            for (i = 1; i <= length1; i++) {
//                score[i][0] = 0;
//                prevCells[i][0] = DR_ZERO;
//            }
//            // The first column
//            for (j = 1; j <= length2; j++) {
//                score[0][j] = 0;
//                prevCells[0][j] = DR_ZERO;
//            }
//            // The rest of the matrix
//            for (i = 1; i <= length1; i++) {
//                for (j = 1; j <= length2; j++) {
//                    double diagScore = score[i - 1][j - 1] + similarity(i, j);
//                    double upScore = score[i][j - 1] + similarity(0, j);
//                    double leftScore = score[i - 1][j] + similarity(i, 0);
//                    score[i][j] = Math.max(diagScore, Math.max(upScore, Math.max(leftScore, 0)));
//                    prevCells[i][j] = 0;
//                    // Find the directions that give the maximum scores.
//                    if (diagScore == score[i][j]) {
//                        prevCells[i][j] |= DR_DIAG;
//                    }
//                    if (leftScore == score[i][j]) {
//                        prevCells[i][j] |= DR_LEFT;
//                    }
//                    if (upScore == score[i][j]) {
//                        prevCells[i][j] |= DR_UP;
//                    }
//                    if (0 == score[i][j]) {
//                        prevCells[i][j] |= DR_ZERO;
//                    }
//                }
//            }
//        }
//
//        // Get the maximum value in the score matrix.
//        private double getMaxScore() {
//            double maxScore = 0;
//            // skip the first row and column
//            for (int i = 1; i <= length1; i++) {
//                for (int j = 1; j <= length2; j++) {
//                    if (score[i][j] > maxScore) {
//                        maxScore = score[i][j];
//                    }
//                }
//            }
//            return maxScore;
//        }
//
//        private void calculateSeqMatch() {
//            String seqResultA = "";
//            String seqResultB = "";
//            coordMatchSeqA.remove(coordMatchSeqA.size() - 1);
//            int firstPossA = coordMatchSeqA.get(0);
//            int lastPossA = coordMatchSeqA.get(coordMatchSeqA.size() - 1);
//            coordMatchSeqB.remove(coordMatchSeqB.size() - 1);
//            int firstPossB = coordMatchSeqB.get(0);
//            int lastPossB = coordMatchSeqB.get(coordMatchSeqB.size() - 1);
//            // Add initials gaps
//            if (firstPossA < firstPossB) {
//                for (int i = 0; i < firstPossB - firstPossA; i++) {
//                    seqResultA += "-";
//                }
//            } else {
//                for (int i = 0; i < firstPossA - firstPossB; i++) {
//                    seqResultB += "-";
//                }
//            }
//            // Add internal gaps
//            int j = 0;
//            for (int i = 0; i < str1.length; i++) {
//                if (i >= firstPossA && i <= lastPossA) {
//                    seqResultA += seqMatchA.charAt(j);
//                    if (seqMatchA.charAt(j) == '-') {
//                        i--;
//                    }
//                    j++;
//                } else {
//                    seqResultA += str1[i];
//                }
//            }
//            // Add internal gaps
//            j = 0;
//            for (int i = 0; i < str2.length; i++) {
//                if (i >= firstPossB && i <= lastPossB) {
//                    seqResultB += seqMatchB.charAt(j);
//                    if (seqMatchB.charAt(j) == '-') {
//                        i--;
//                    }
//                    j++;
//                } else {
//                    seqResultB += str2[i];
//                }
//            }
//            seqMatchA = seqResultA;
//            seqMatchB = seqResultB;
//        }
//
//        // Output the local alignments ending in the (i, j) cell.
//        private void makeAlignments(int i, int j, String aligned1, String aligned2, String seq1, String seq2) {
//            // We've reached the starting point, so print the alignments
//            if ((prevCells[i][j] & DR_ZERO) > 0) {
//                // Extract the alignments sequences
//                seqMatchA = aligned1;
//                seqMatchB = aligned2;
//                // Extract the alignments coordenates
//                String[] seq = seq1.split(",");
//                coordMatchSeqA.clear();
//                for (String s : seq) {
//                    s = (!s.equals("-")) ? s : "-1";
//                    coordMatchSeqA.add(Integer.valueOf(s));
//                }
//                seq = seq2.split(",");
//                coordMatchSeqB.clear();
//                for (String s : seq) {
//                    s = (!s.equals("-")) ? s : "-1";
//                    coordMatchSeqB.add(Integer.valueOf(s));
//                }
//                calculateSeqMatch();
//                return;
//            }
//            // Find out which directions to backtrack
//            if ((prevCells[i][j] & DR_LEFT) > 0) {
//                makeAlignments(i - 1, j, str1[i - 1] + aligned1, "-" + aligned2, (i - 1) + "," + seq1, "-" + "," + seq2);
//            }
//            if ((prevCells[i][j] & DR_UP) > 0) {
//                makeAlignments(i, j - 1, "-" + aligned1, str2[j - 1] + aligned2, "-" + "," + seq1, (j - 1) + "," + seq2);
//            }
//            if ((prevCells[i][j] & DR_DIAG) > 0) {
//                makeAlignments(i - 1, j - 1, str1[i - 1] + aligned1, str2[j - 1] + aligned2, (i - 1) + "," + seq1, (j - 1) + "," + seq2);
//            }
//        }
//
//        // Get the alignment score between the two input strings.
//        public double getAlignmentScore() {
//            int maxLength = (length1 > length2) ? length1 : length2;
//            return coordMatchSeqA.size() * 100 / maxLength;
////            return getMaxScore() / NORM_FACTOR;
//        }
//
//        // Print the dynmaic programming matrix
//        public void printDPMatrix() {
//            System.out.print("   ");
//            for (int j = 1; j <= length2; j++) {
//                System.out.print("   " + str2[j - 1]);
//            }
//            System.out.println();
//            for (int i = 0; i <= length1; i++) {
//                if (i > 0) {
//                    System.out.print(str1[i - 1] + " ");
//                } else {
//                    System.out.print("  ");
//                }
//                for (int j = 0; j <= length2; j++) {
//                    System.out.print(score[i][j] / NORM_FACTOR + " ");
//                }
//                System.out.println();
//            }
//        }
//
//        // Output the alignments coordenates for sequence A
//        public List<Integer> getMatchCoordSequenceA() {
//            coordMatchSeqA.remove(coordMatchSeqA.size() - 1);
//            return coordMatchSeqA;
//        }
//
//        // Output the alignments coordenates for sequence B
//        public List<Integer> getMatchCoordSequenceB() {
//            coordMatchSeqB.remove(coordMatchSeqB.size() - 1);
//            return coordMatchSeqB;
//        }
//
//        // Output the alignments for sequence A
//        public String getSeqMatchA() {
//            return seqMatchA;
//        }
//
//        // Output the alignments for sequence B
//        public String getSeqMatchB() {
//            return seqMatchB;
//        }
//
//        // Output the local alignments with the maximum score.
//        public void alignmentSequences() {
//            // Find the cell with the maximum score
//            double maxScore = getMaxScore();
//            // skip the first row and column
//            for (int i = 1; i <= length1; i++) {
//                for (int j = 1; j <= length2; j++) {
//                    if (score[i][j] == maxScore) {
//                        makeAlignments(i, j, "", "", "" + i, "" + j);
//                    }
//                }
//            }
//        }
//    }
}
