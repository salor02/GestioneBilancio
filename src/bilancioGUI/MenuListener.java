package bilancioGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import bilancioUtil.Bilancio;
import fileManager.BinaryManager;
import fileManager.CSVManager;
import fileManager.FileManager;
import fileManager.ODSManager;
import fileManager.TXTManager;

public class MenuListener implements ActionListener{
    /**
     * riferimento a menu file in alto
     */
    private JMenu fileMenu;

    /**
     * pannello tabella
     */
    private TablePanel tablePanel;

    /**
     * pannello principale
     */
    private MainPanel mainPanel;

    /**
     * bilancio su cui operare
     */
    private Bilancio bilancio;

    /**
     * Inizializza tutti gli attributi della classe e tutti i listener
     * @param mainPanel il pannello principale tramite il quale è possibile accedere a tutti gli elementi
     */
    public MenuListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.bilancio = mainPanel.bilancio;
        this.tablePanel = mainPanel.tablePanel;
        this.fileMenu = mainPanel.fileMenu;

        for(int i = 0; i < this.fileMenu.getItemCount(); i++){
            fileMenu.getItem(i).addActionListener(this);
        }
    }

    /**
     * Gestisce le azioni dei pulsanti del menu
     * @param e evento catturato
     */
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        System.out.println("[MENU][OPTION] " + command);
        FileManager fileManager = null;

        //definisce un filechooser con posizione nella cartella predefinita "sav"
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir"), "sav"));

        //tutti i comandi che riguardano il salvataggio/esportazione
        if(command.equals("Salva") || command.equals("Esporta CSV") || command.equals("Esporta TXT") || command.equals("Esporta ODS")){

            chooser.setSelectedFile(new File("bilancio")); //imposta "bilancio" come nome predefinito
            int retrival = chooser.showSaveDialog(null);
            
            //se operazione approvata
            if (retrival == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                //se il file esiste già chiede di sovrascriverlo
                if(selectedFile.exists()){
                    int response = JOptionPane.showConfirmDialog(null, "Sovrascrivere file?", "File selezionato già esistente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    //se si sceglie di non sovrascrivere viene annullata l'operazione
                    if (response != JOptionPane.YES_OPTION) {
                        System.out.println("[ALERT] Operazione annullata (file già esistente)");
                        return;
                    } 
                }
                try {
                    System.out.println("[SUCCESS] File selezionato: " + selectedFile);
                    
                    //polimorfismo in tutto il suo splendore
                    if(command.equals("Salva")) fileManager = new BinaryManager(selectedFile);
                    if(command.equals("Esporta CSV")) fileManager = new CSVManager(selectedFile);
                    if(command.equals("Esporta TXT")) fileManager = new TXTManager(selectedFile);
                    if(command.equals("Esporta ODS")) fileManager = new ODSManager(selectedFile);

                    //salva il bilancio su file
                    fileManager.save(bilancio);

                    System.out.println("[SUCCESS] File salvato correttamente");
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Impossibile salvare il file", "Errore", JOptionPane.ERROR_MESSAGE);
                    System.out.println("[ERROR] Impossibile salvare il file\n" + ex.getMessage());
                }
            }
        }

        //comando per caricare il bilancio da file di salvataggio
        if(e.getActionCommand().equals("Carica")){
            int retrival = chooser.showOpenDialog(null);

            //se operazione approvata
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = chooser.getSelectedFile();
                    System.out.println("[SUCCESS] File selezionato: " + selectedFile);
                    BinaryManager fileUploader = new BinaryManager(selectedFile);
                    
                    //carica il bilancio da file binario e aggiora la lista movimenti del bilancio attuale con quella del bilancio caricato
                    bilancio.setListaMovimenti(fileUploader.upload().getListaMovimenti());

                    mainPanel.tableUpdate(bilancio);
                    mainPanel.resocontoUpdate(bilancio);

                    System.out.println("[SUCCESS] Bilancio caricato correttamente");
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Impossibile caricare il bilancio", "Errore", JOptionPane.ERROR_MESSAGE);
                    System.out.println("[ERROR] Impossibile caricare il bilancio\n" + ex.getMessage());
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
                JOptionPane.showMessageDialog(null, "Errore nella fase di stampa", "Errore", JOptionPane.ERROR_MESSAGE);
                System.out.println("[ERROR] Errore nella fase di stampa\n" + ex.getMessage());
            }
        }
    }
}
