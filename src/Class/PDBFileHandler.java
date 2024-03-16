package Class;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.GZIPInputStream;

public class PDBFileHandler {

    private String file;

    public PDBFileHandler(String file) {
        this.file = file;
    }

    public String getAbsoluteFilePath() {
        return file;
    }

    public List<String> LoadFile() {
        List<String> loadedFile = new ArrayList<String>();
        try {
            loadedFile = new ArrayList<String>();
            if (file.toLowerCase().endsWith(".gz")) {
                loadedFile.addAll(loadPDBGZipFile());
            } else {
                loadedFile.addAll(loadPDBFile());
            }
        } catch (Exception e) {
        }
        return loadedFile;
    }

    private List<String> loadPDBFile() {
        List<String> loadedFile = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String line = "";
            boolean endMDL = false;
            while ((line = br.readLine()) != null && !endMDL) {
                if (line.startsWith("ENDMDL") || line.startsWith("END")) {
                    endMDL = true;
                }
                line = (Internationalization.convertCommaSeparator()) ? line.replace(".", ",") : line;
                loadedFile.add(line);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return loadedFile;
    }

    private List<String> loadPDBGZipFile() {
        List<String> loadedFile = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(new File(file));
            CheckedInputStream cis = new CheckedInputStream(fis, new Adler32());
            GZIPInputStream gis = new GZIPInputStream(cis);
            String line = "";
            int gzByte = -1;
            boolean endMDL = false;
            while ((gzByte = gis.read()) != -1 && !endMDL) {
                if (gzByte != 13 && gzByte != 10) {
                    line += (char) gzByte;
                } else {
                    if (line.startsWith("ENDMDL") || line.startsWith("END")) {
                        endMDL = true;
                    }
                    line = (Internationalization.convertCommaSeparator()) ? line.replace(".", ",") : line;
                    loadedFile.add(line);
                    line = "";
                }
            }
        } catch (IOException ex) {
        }
        return loadedFile;
    }
}
