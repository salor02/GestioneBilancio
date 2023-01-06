package bilancioGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.naming.NameNotFoundException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;

import bilancioUtil.Bilancio;

/**
 * Gestisce tutte le azioni inviate da componenti presenti nel pannello TablePanel
 */
public class TableListener implements ActionListener, ListSelectionListener{
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
    public TableListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.bilancio = mainPanel.bilancio;
        this.tablePanel = mainPanel.tablePanel;

        this.tablePanel.table.getSelectionModel().addListSelectionListener(this);
        this.tablePanel.addVoce.addActionListener(this); 
        this.tablePanel.deleteVoce.addActionListener(this); 
        this.tablePanel.updateVoce.addActionListener(this); 
        this.tablePanel.submitVoce.addActionListener(this);
    }

    /**
     * Listener dedicato ad eventi di tipo ActionEvent
     */
    public void actionPerformed(ActionEvent e){

        //se pulsante premuto è submitVoce 
        if(e.getSource() == tablePanel.submitVoce){

            //preleva i dati inseriti nei campi di testo
            String date = tablePanel.inputDate.getText();
            String desc = tablePanel.inputDesc.getText();
            String amount = tablePanel.inputAmount.getText();

            try{
                //se modalità "aggiungi"
                if(tablePanel.addVoce.isSelected()){
                    bilancio.addVoce(date, desc, amount);
                    System.out.println("[SUCCESS] Nuova voce inserita nel bilancio");
                }
                //se modalità "elimina"
                if(tablePanel.deleteVoce.isSelected()){
                    bilancio.deleteVoce(date, desc, amount);
                    System.out.println("[SUCCESS] Voce eliminata dal bilancio correttamente");
                }
                //se modalità "modifica"
                if(tablePanel.updateVoce.isSelected()){
                    bilancio.updateVoce(tablePanel.selDate, tablePanel.selDesc, tablePanel.selAmount, date, desc, amount);
                    System.out.println("[SUCCESS] Voce modificata correttamente");
                }
                
                //riattiva tutti i campi di testo e ritorna in modalità "aggiungi"
                tablePanel.switchInputText(true);
                tablePanel.inputDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
                tablePanel.inputDesc.setText("");
                tablePanel.inputAmount.setText("");
                tablePanel.addVoce.setSelected(true);

                //aggiornamento resoconto bilancio e aggiornamento tabella
                mainPanel.tableUpdate(bilancio);
                mainPanel.resocontoUpdate(bilancio);
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                System.out.println("[ERROR] " + ex.getMessage());
            }
            /*NB: eccezione non necessaria siccome verrebbe sollevata in caso una riga selezionata non si trovi
            effettivamente nella tabella, una situazione che non dovrebbe mai succedere */
            catch(NameNotFoundException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                System.out.println("[ERROR] " + ex.getMessage());
            }
        }

        //entra in modalità "aggiunta"
        if(e.getSource() == tablePanel.addVoce){
            System.out.println("[OPTION] Aggiungi voce");

            tablePanel.switchInputText(true);

            tablePanel.inputDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
            tablePanel.inputDesc.setText("");
            tablePanel.inputAmount.setText("");
        }

        //entra in modalità "elimina" o "modifica"
        if(e.getSource() == tablePanel.deleteVoce || e.getSource() == tablePanel.updateVoce){

            tablePanel.inputDate.setText(tablePanel.selDate);
            tablePanel.inputDesc.setText(tablePanel.selDesc);
            tablePanel.inputAmount.setText(tablePanel.selAmount);

            //se si è in modalità "elimina" tutti i campi vengono bloccati per prevenirne la modifica e conseguenti bug
            if(e.getSource() == tablePanel.deleteVoce){
                System.out.println("[OPTION] Elimina voce");

                tablePanel.switchInputText(false);
            }
            else{
                System.out.println("[OPTION] Modifica voce");

                tablePanel.switchInputText(true);
            }
        }
    }

    /**
     * gestisce le azioni effettuate sulla tabella (selezione)
     * @param e evento catturato
     */
    public void valueChanged(ListSelectionEvent e){

        //controlla che la selezione sia finita
        if(!e.getValueIsAdjusting()){
            
            int row = tablePanel.table.getSelectedRow();

            //se è stata selezionata una riga
            if(row >= 0){
                //abilita i radio button per attivare le modalità "elimina" e "modifica"
                tablePanel.deleteVoce.setEnabled(true);
                tablePanel.updateVoce.setEnabled(true);
                
                //preleva i valori selezionati
                tablePanel.selDate = tablePanel.table.getValueAt(row, 0).toString();
                tablePanel.selDesc = tablePanel.table.getValueAt(row, 1).toString();
                tablePanel.selAmount = tablePanel.table.getValueAt(row, 2).toString();

                //se si è gia in modalità "elimina" o "modifica" aggiorna i campi di testo secondo la nuova selezione
                if(tablePanel.deleteVoce.isSelected() || tablePanel.updateVoce.isSelected()){
                    tablePanel.inputDate.setText(tablePanel.selDate);
                    tablePanel.inputDesc.setText(tablePanel.selDesc);
                    tablePanel.inputAmount.setText(tablePanel.selAmount);
                }
            }
            //se è stata deselezionata una riga o comunque non ci sono righe selezionate
            else{
                //disattiva i radio button di "elimina" e "modifica"
                tablePanel.deleteVoce.setEnabled(false);
                tablePanel.updateVoce.setEnabled(false);
            }
            
        }
    }


}
