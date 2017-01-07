
package splitter;

import exception.MessageException;
import frames.Messages;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Split {
    
    protected final static int BLOCK_SIZE = 4096;
    
    private final File inputFile;
    private final long totalBytes;
    private final File outputDir;
    private File[] outputFiles;
    
    public Split(File file) throws MessageException {
        this.inputFile = file;
        this.totalBytes = file.length();
        this.outputDir = new File(inputFile.getAbsolutePath()+".split");
    }
    
    private File[] getUuidFiles(int num){
        File[] uuidFiles = new File[num];
        for(int i=0; i<num; i++) {
            String uuid = UUID.randomUUID().toString();
            uuidFiles[i] = new File(outputDir.getAbsolutePath()+"/"+uuid);
        }
        return uuidFiles;
    }
        
    public void goSplit(int numParts) throws MessageException, FileNotFoundException, IOException {
        if(numParts < 2) {
            throw new MessageException("Number of parts is minor than 2");
        }
        
        long bytesPerPart = totalBytes / numParts;
        byte[] buffer = new byte[BLOCK_SIZE];
        
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(inputFile);
            this.outputFiles = this.getUuidFiles(numParts);
            for(File outputFile : outputFiles){
                os = new FileOutputStream(outputFile);
                long bytes = 0;
                while(bytes < bytesPerPart) {
                    int length = is.read(buffer);
                    if(length > 0) {
                        os.write(buffer, 0, length);
                        bytes += BLOCK_SIZE;
                    } else {
                        System.err.println("WARNING: Finish prematurely ?");
                        break;
                    }
                }
                os.close();
            }
            is.close();
            writeInfo();
        } finally {
            is.close();
            os.close();
        }
    }
    
    private void writeInfo() throws IOException {
        File fileInfo = new File(outputDir.getAbsolutePath()+"/info");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fileInfo));
            bw.write(inputFile.getName()+"\n");
            for(File file : outputFiles) {
                bw.write(file.getName()+"\n");
            }
            bw.close();
        } finally {
            bw.close();
        }
    }

}
