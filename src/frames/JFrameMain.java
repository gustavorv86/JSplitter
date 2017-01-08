
package frames;

import java.io.File;
import javax.swing.JFileChooser;
import exception.MessageException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import splitter.Info;
import splitter.Merge;
import splitter.Split;

public class JFrameMain extends javax.swing.JFrame {

    private static final String UUID_REGEX = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";
    
    private static final JFileChooser CHOOSER = new JFileChooser();
    static {
        CHOOSER.setMultiSelectionEnabled(false);
    };
    
    private File splitFile = null;
    private File infoFile = null;
    
    public JFrameMain() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
    }

    private void splitBrowse(){
        CHOOSER.setSelectedFile(null);
        if(CHOOSER.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.splitFile = CHOOSER.getSelectedFile();
            this.jTextFieldSplitFile.setText(splitFile.getAbsolutePath());
            editPartSize();
        }
    }
    
    private void editPartSize() {
        if(splitFile != null && splitFile.exists()) {
            long totalBytes = splitFile.length();
            int numParts = (int) jSpinnerParts.getValue();
            float partSize = totalBytes / numParts;
            
            String units = "Bytes";
            if(partSize > 1024) {
                partSize /= 1024;
                units = "KBytes";
            }
            if(partSize > 1024) {
                partSize /= 1024;
                units = "MBytes";
            }
            if(partSize > 1024) {
                partSize /= 1024;
                units = "GBytes";
            }
            this.jTextFieldSize.setText(String.format("%.2f", partSize)+" "+units);
        }
    }
    
    private void splitFile(){
        // no se ha seleccionado ningun fichero de entrada
        if(splitFile == null) {
            Messages.showInfo("Please, select a file");
            return;
        }
        // el fichero de entrada no existe
        if(!splitFile.exists()) {
            Messages.showError("File "+splitFile.getName()+" does not exists");
            return;
        }
        // no se puede crear el directorio de salida
        File dir = new File(splitFile.getAbsolutePath()+".split");
        try {
            dir.mkdir();
        } catch (SecurityException se) {
            Messages.showError("Cannot make directory "+dir.getAbsolutePath());
        }
        // el directorio de salida no esta vacio
        File[] list = dir.listFiles();
        if(list.length != 0) {
            if(!Messages.showOption("Overwrite", "The output folder is not empty. Overwrite?")) {
                return;
            }
            // borrar el directorio de salida
            for(File file : list) {
                file.delete();
            }
        }
        // dividir el fichero en partes
        try {
            Split split = new Split(splitFile);
            int numParts = (int) jSpinnerParts.getValue();
            split.goSplit(numParts);
            Messages.showInfo(" File splitted successfully :) ");
        } catch(MessageException ex) {
            Messages.showError(ex.getMessage());
        } catch(Exception ex) {
            Messages.showError(" Cannot split file :( ");
        }
    }
    
    private void mergeBrowse(){
        CHOOSER.setSelectedFile(new File("info"));
        if(CHOOSER.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = CHOOSER.getSelectedFile();
            try {
                showProperties(selectedFile);
                infoFile = selectedFile;
                jTextFieldInfoFile.setText(infoFile.getAbsolutePath());
            } catch (MessageException ex) {
                Messages.showError(ex.getMessage());
            } catch (Exception ex) {
                Messages.showError("Cannot read info file");
            }
        }
    }
    
    private void showProperties(File file) throws Exception {
        if(!file.exists()) {
            throw new MessageException("Info file does not exists");
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String mergeFile = "";
        int count = 0;
        while((line = br.readLine()) != null) {
            if(count == 0) {
                mergeFile = line;
            } else {
                if(!line.matches(UUID_REGEX)) {
                    br.close();
                    throw new MessageException("Invalid info file");
                } 
            }
            count++;
        }
        br.close();
        jTextFieldMergeFile.setText(mergeFile);
        jTextFieldMergeParts.setText((count-1)+"");
    }
    
    private void mergeFile(){
        // no se ha seleccionado ningun archivo info
        if(infoFile == null) {
            Messages.showInfo("Please, select a file");
            return;
        }
        // lectura del archivo info
        Info info;
        try {
             info = new Info(infoFile);
        } catch(MessageException ex) {
            Messages.showError(ex.getMessage());
            return;
        }
        // comprobamos si existe el archivo de salida
        if(info.outputFile.exists()) {
            if(!Messages.showOption("Overwrite", "The output file already exists. Overwrite?")) {
                return;
            }
        }
                
        try {
            Merge merge = new Merge(info);
            merge.goMerge();
            Messages.showInfo(" File merged successfully :) ");
        } catch(MessageException ex) {
            Messages.showError(ex.getMessage());
        } catch(Exception ex) {
            Messages.showError(" Cannot merge files :( ");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelSplit = new javax.swing.JPanel();
        jPanelMethod = new javax.swing.JPanel();
        jSpinnerParts = new javax.swing.JSpinner();
        jTextFieldSize = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelSelectFiles = new javax.swing.JPanel();
        jTextFieldSplitFile = new javax.swing.JTextField();
        jButtonSplitBrowse = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanelMerge = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanelSelectFiles1 = new javax.swing.JPanel();
        jTextFieldInfoFile = new javax.swing.JTextField();
        jButtonMergeBrowse = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldMergeFile = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldMergeParts = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JSplitter");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        jPanelSplit.setBackground(new java.awt.Color(255, 255, 255));

        jPanelMethod.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMethod.setBorder(javax.swing.BorderFactory.createTitledBorder("Split method"));
        jPanelMethod.setOpaque(false);

        jSpinnerParts.setModel(new javax.swing.SpinnerNumberModel(2, 2, null, 1));
        jSpinnerParts.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerPartsStateChanged(evt);
            }
        });

        jTextFieldSize.setEditable(false);

        jLabel3.setText("Part size:");

        jLabel4.setText("Number of parts:");

        javax.swing.GroupLayout jPanelMethodLayout = new javax.swing.GroupLayout(jPanelMethod);
        jPanelMethod.setLayout(jPanelMethodLayout);
        jPanelMethodLayout.setHorizontalGroup(
            jPanelMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMethodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelMethodLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinnerParts, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelMethodLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldSize)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMethodLayout.setVerticalGroup(
            jPanelMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMethodLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerParts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanelMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap())
        );

        jPanelSelectFiles.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSelectFiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Select file to split"));
        jPanelSelectFiles.setOpaque(false);

        jTextFieldSplitFile.setEditable(false);

        jButtonSplitBrowse.setText("Browse...");
        jButtonSplitBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSplitBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSelectFilesLayout = new javax.swing.GroupLayout(jPanelSelectFiles);
        jPanelSelectFiles.setLayout(jPanelSelectFilesLayout);
        jPanelSelectFilesLayout.setHorizontalGroup(
            jPanelSelectFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldSplitFile, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSplitBrowse)
                .addContainerGap())
        );
        jPanelSelectFilesLayout.setVerticalGroup(
            jPanelSelectFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectFilesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSelectFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSplitFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSplitBrowse))
                .addContainerGap())
        );

        jButton2.setText("Split");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSplitLayout = new javax.swing.GroupLayout(jPanelSplit);
        jPanelSplit.setLayout(jPanelSplitLayout);
        jPanelSplitLayout.setHorizontalGroup(
            jPanelSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSplitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSelectFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelSplitLayout.createSequentialGroup()
                        .addComponent(jPanelMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanelSplitLayout.setVerticalGroup(
            jPanelSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSplitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSelectFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jTabbedPane.addTab("Split", jPanelSplit);

        jPanelMerge.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setText("Merge");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanelSelectFiles1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSelectFiles1.setBorder(javax.swing.BorderFactory.createTitledBorder("Select info file"));
        jPanelSelectFiles1.setOpaque(false);

        jTextFieldInfoFile.setEditable(false);

        jButtonMergeBrowse.setText("Browse...");
        jButtonMergeBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMergeBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSelectFiles1Layout = new javax.swing.GroupLayout(jPanelSelectFiles1);
        jPanelSelectFiles1.setLayout(jPanelSelectFiles1Layout);
        jPanelSelectFiles1Layout.setHorizontalGroup(
            jPanelSelectFiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectFiles1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldInfoFile, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButtonMergeBrowse)
                .addContainerGap())
        );
        jPanelSelectFiles1Layout.setVerticalGroup(
            jPanelSelectFiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectFiles1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSelectFiles1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldInfoFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonMergeBrowse))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
        jPanel1.setOpaque(false);

        jLabel1.setText("File:");

        jTextFieldMergeFile.setEditable(false);

        jLabel2.setText("Number of parts:");

        jTextFieldMergeParts.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldMergeFile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldMergeParts, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldMergeFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldMergeParts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelMergeLayout = new javax.swing.GroupLayout(jPanelMerge);
        jPanelMerge.setLayout(jPanelMergeLayout);
        jPanelMergeLayout.setHorizontalGroup(
            jPanelMergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMergeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMergeLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addComponent(jPanelSelectFiles1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelMergeLayout.setVerticalGroup(
            jPanelMergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMergeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSelectFiles1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMergeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMergeLayout.createSequentialGroup()
                        .addGap(0, 83, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane.addTab("Merge", jPanelMerge);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSplitBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSplitBrowseActionPerformed
        splitBrowse();
    }//GEN-LAST:event_jButtonSplitBrowseActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        splitFile();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonMergeBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMergeBrowseActionPerformed
        mergeBrowse();
    }//GEN-LAST:event_jButtonMergeBrowseActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        mergeFile();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jSpinnerPartsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerPartsStateChanged
        editPartSize();
    }//GEN-LAST:event_jSpinnerPartsStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonMergeBrowse;
    private javax.swing.JButton jButtonSplitBrowse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMerge;
    private javax.swing.JPanel jPanelMethod;
    private javax.swing.JPanel jPanelSelectFiles;
    private javax.swing.JPanel jPanelSelectFiles1;
    private javax.swing.JPanel jPanelSplit;
    private javax.swing.JSpinner jSpinnerParts;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldInfoFile;
    private javax.swing.JTextField jTextFieldMergeFile;
    private javax.swing.JTextField jTextFieldMergeParts;
    private javax.swing.JTextField jTextFieldSize;
    private javax.swing.JTextField jTextFieldSplitFile;
    // End of variables declaration//GEN-END:variables
}
