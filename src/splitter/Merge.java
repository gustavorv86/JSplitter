
package splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Merge {

    private final Info info;

    public Merge(Info info) throws Exception {
        this.info = info;
    }
    
    public void goMerge() throws Exception {
        byte[] buffer = new byte[Split.BLOCK_SIZE];
        
        InputStream is = null;
        OutputStream os = null;
        try {
            os = new FileOutputStream(info.outputFile);
            for(File file : info.mergeFiles) {
                is = new FileInputStream(file);
                int bytes = 0;
                while((bytes=is.read(buffer)) > 0) {
                    os.write(buffer, 0, bytes);
                }
                is.close();
            }
            os.close();
        } finally {
            is.close();
            os.close();
        }
    }
    
}
