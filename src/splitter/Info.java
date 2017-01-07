
package splitter;

import exception.MessageException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Info {
    
    // pueden ser publicas porque son finales (no se pueden modificar fuera del constructor)
    public final File infoFile;
    public final File[] mergeFiles;
    public final File outputFile;
    
    public Info(File infoFile) throws MessageException {
        // lectura del archivo info
        this.infoFile = infoFile;
        ArrayList<String> lines = null;
        try {
            lines = getLines();
        } catch (Exception ex) {
            throw new MessageException("Cannot read info file");
        }
        // archivo de salida
        outputFile = new File(infoFile.getParentFile().getParentFile()+"/"+lines.get(0));
        // comprobar que existen todas las partes
        mergeFiles = new File[lines.size()-1];
        for(int i=1; i<lines.size(); i++) {
            File file = new File(infoFile.getParentFile()+"/"+lines.get(i));
            if(!file.exists()) {
                throw new MessageException("Part file "+file.getName()+" not found");
            } else {
                mergeFiles[i-1] = file;
            }
        }
    }
    
    private ArrayList<String> getLines() throws Exception {
        ArrayList<String> lines = new ArrayList();
        BufferedReader br = null;
        try {        
            br = new BufferedReader(new FileReader(infoFile));
            String line;
            while((line=br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } finally {
            br.close();
        }
        return lines;
    }
    
}
