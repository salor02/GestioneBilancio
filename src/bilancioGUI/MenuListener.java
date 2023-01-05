package bilancioGUI;

import bilancioUtil.*;
import fileManager.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;

public class MenuListener implements ActionListener{
    private TablePanel tablePanel;
    private MainPanel mainPanel;
    private JMenu fileMenu;
    private Bilancio bilancio;

    public MenuListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.bilancio = mainPanel.bilancio;
        this.tablePanel = mainPanel.tablePanel;
        this.fileMenu = mainPanel.fileMenu;

        for(int i = 0; i < this.fileMenu.getItemCount(); i++){
            fileMenu.getItem(i).addActionListener(this);
        }
    }

    public void actionPerformed(ActionEvent e){
        System.out.println("[MENU][OPTION] " + e.getActionCommand());
        FileManager fileManager = null;

        String command = e.getActionCommand();
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir"), "sav"));

        //tutti i comandi che riguardano il salvataggio/esportazione
        if(command.equals("Salva") || command.equals("Esporta CSV") || command.equals("Esporta TXT") || command.equals("Esporta ODS")){

            chooser.setSelectedFile(new File("bilancio"));
            int retrival = chooser.showSaveDialog(null);
            
            if (retrival == JFileChooser.APPROVE_OPTION) {
                if(chooser.getSelectedFile().exists()){
                    int response = JOptionPane.showConfirmDialog(null, "Sovrascrivere file?", "File selezionato già esistente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response != JOptionPane.YES_OPTION) {
                        System.out.println("[ALERT] Operazione annullata (file già esistente)");
                        return;
                    } 
                }
                try {
                    File selectedFile = chooser.getSelectedFile();
                    System.out.println("[SUCCESS] File selezionato: " + selectedFile);

                    //polimorfismo in tutto il suo splendore
                    if(command.equals("Salva")) fileManager = new BinaryManager(selectedFile);
                    if(command.equals("Esporta CSV")) fileManager = new CSVManager(selectedFile);
                    if(command.equals("Esporta TXT")) fileManager = new TXTManager(selectedFile);
                    if(command.equals("Esporta ODS")) fileManager = new ODSManager(selectedFile);

                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile salvare il file");
                }
            }
        }

        //comando per caricare il bilancio da file di salvataggio
        if(e.getActionCommand().equals("Carica")){
            int retrival = chooser.showOpenDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = chooser.getSelectedFile();
                    System.out.println("[SUCCESS] File selezionato: " + selectedFile);
                    BinaryManager fileUploader = new BinaryManager(selectedFile);
                    
                    bilancio.setListaMovimenti(fileUploader.upload().getListaMovimenti());
                    mainPanel.tableUpdate(bilancio);
                    mainPanel.resocontoUpdate(bilancio);

                    System.out.println("[SUCCESS] Bilancio caricato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile caricare il bilancio");
                }
            }
        }
    
        //comando per stampare il bilancio
        if(e.getActionCommand().equals("Stampa")){
            try{
                if(tablePanel.table.print()){
                    System.out.println("[SUCCESS] Tabella stampata correttamente");
                }
                else{
                    System.out.println("[ALERT] Operazione di stampa annullata");
                }
                
            }
            catch(PrinterException ex){
                System.out.println("[ERROR] Errore nella fase di stampa");
            }
        }
    }
}
