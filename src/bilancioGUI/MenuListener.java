package bilancioGUI;

import javax.naming.NameNotFoundException;
import javax.print.PrintException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;

import javax.swing.event.*;
import bilancioUtil.*;
import fileManager.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

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
        FileManager fileManager;

        if(e.getActionCommand().equals("Salva")){
            JFileChooser chooser = new JFileChooser();
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
                    System.out.println(chooser.getSelectedFile());
                    fileManager = new BinaryManager(chooser.getSelectedFile());
                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile salvare il file");
                }
            }
        }

        if(e.getActionCommand().equals("Carica")){
            JFileChooser chooser = new JFileChooser();
            int retrival = chooser.showOpenDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    System.out.println(chooser.getSelectedFile());
                    BinaryManager fileUploader = new BinaryManager(chooser.getSelectedFile());
                    
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
    
        if(e.getActionCommand().equals("Esporta CSV")){
            JFileChooser chooser = new JFileChooser();
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
                    System.out.println(chooser.getSelectedFile());
                    fileManager = new CSVManager(chooser.getSelectedFile());
                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile salvare il file" + ex.getMessage());
                }
            }
        }

        if(e.getActionCommand().equals("Esporta TXT")){
            JFileChooser chooser = new JFileChooser();
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
                    System.out.println(chooser.getSelectedFile());
                    fileManager = new TXTManager(chooser.getSelectedFile());
                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile salvare il file");
                }
            }
        }

        if(e.getActionCommand().equals("Esporta ODS")){
            JFileChooser chooser = new JFileChooser();
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
                    System.out.println(chooser.getSelectedFile());
                    fileManager = new ODSManager(chooser.getSelectedFile());
                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    System.out.println("[ERROR] Impossibile salvare il file");
                }
            }
        }

        if(e.getActionCommand().equals("Stampa")){
            try{
                tablePanel.table.print();
            }
            catch(PrinterException ex){
                System.out.println("[ERROR] Errore nella fase di stampa" + ex.getMessage());
            }
        }
    }
}
