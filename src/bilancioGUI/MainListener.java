package bilancioGUI;

import javax.naming.NameNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import bilancioUtil.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;


/**
 * Questa classe si occupa di gestire tutti gli eventi provenienti dal pannello principale (e quindi dai sottopanelli), 
 * in questo modo è possibile avere una corretta interazione tra vari componenti presenti in pannelli diversi.
 */
public class MainListener implements ActionListener, ListSelectionListener{
    private TablePanel tablePanel;
    private FilterPanel filterPanel;
    private Bilancio bilancio;
    private MainPanel mainPanel;

    /**
     * Inizializza il listener mantenendo un riferimento ai pannelli passati come parametro
     * @param tablePanel pannello contenente la tabella e tutte le operazioni su di essa eseguibili
     * @param filterPanel pannello che permette di filtrare i dati mostrati in tabella
     * @param bilancio oggetto bilancio che contiene tutti i dati su cui si sta lavorando
     */
    public MainListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.tablePanel = mainPanel.tablePanel;
        this.filterPanel = mainPanel.filterPanel;
        this.bilancio = mainPanel.bilancio;

        //AGGIUNGE IL LISTENER AGLI ELEMENTI DI TABLEPANEL
        this.tablePanel.table.getSelectionModel().addListSelectionListener(this);
        this.tablePanel.addVoce.addActionListener(this); 
        this.tablePanel.deleteVoce.addActionListener(this); 
        this.tablePanel.updateVoce.addActionListener(this); 
        this.tablePanel.submitVoce.addActionListener(this);

        //AGGIUNGE IL LISTENER AGLI ELEMENTI DI FILTERPANEL
        this.filterPanel.submitFilter.addActionListener(this);
        this.filterPanel.dayFilter.addActionListener(this);
        this.filterPanel.monthFilter.addActionListener(this);
        this.filterPanel.yearFilter.addActionListener(this);
        this.filterPanel.submitPeriod.addActionListener(this);
        this.filterPanel.submitSearch.addActionListener(this);
    }

   
    
    /**
     * Gestisce le azioni dei pulsanti del pannello
     * @param e evento catturato
     */
    public void actionPerformed(ActionEvent e){
        //PANNELLO TABLE

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
                    mainPanel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Nuova voce inserita nel bilancio");
                }
                //se modalità "elimina"
                if(tablePanel.deleteVoce.isSelected()){
                    bilancio.deleteVoce(date, desc, amount);
                    mainPanel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Voce eliminata dal bilancio correttamente");
                }
                //se modalità "modifica"
                if(tablePanel.updateVoce.isSelected()){
                    bilancio.updateVoce(tablePanel.selDate, tablePanel.selDesc, tablePanel.selAmount, date, desc, amount);
                    mainPanel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Voce modificata correttamente");
                }
                
                //riattiva tutti i campi di testo e ritorna in modalità "aggiungi"
                tablePanel.switchInputText(true);
                tablePanel.inputDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
                tablePanel.inputDesc.setText("");
                tablePanel.inputAmount.setText("");
                tablePanel.addVoce.setSelected(true);

                //aggiornamento resoconto bilancio
                mainPanel.resocontoUpdate(bilancio);
            }
            catch(IllegalArgumentException ex){
                System.out.println("[ERROR] " + ex.getMessage());
            }
            /*NB: eccezione non necessaria siccome verrebbe sollevata in caso una riga selezionata non si trovi
            effettivamente nella tabella, una situazione che non dovrebbe mai succedere */
            catch(NameNotFoundException ex){
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
        
        //PANNELLO FILTRO

        //se il pulsante premuto è submitFilter
        if(e.getSource() == filterPanel.submitFilter){

            try{
                Bilancio bilancioFiltrato;
                bilancioFiltrato = bilancio.dateFilter(filterPanel.fromDate.getText(), filterPanel.toDate.getText());
                mainPanel.tableUpdate(bilancioFiltrato);
                mainPanel.resocontoUpdate(bilancioFiltrato);
                System.out.println("[SUCCESS] Bilancio filtrato correttamente");    
            }
            catch(IllegalArgumentException ex){
                System.out.println("[ERROR] " + ex.getMessage());
            }
        }

        //se premuto radio button giorno
        if(e.getSource() == filterPanel.dayFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo giorno");
            filterPanel.patternPeriod.setText("dd/mm/yyyy");
        }

        //se premuto radio button mese
        if(e.getSource() == filterPanel.monthFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo mese");
            filterPanel.patternPeriod.setText("mm/yyyy");
        }

        //se premuto radio button anno
        if(e.getSource() == filterPanel.yearFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo anno");
            filterPanel.patternPeriod.setText("yyyy");
        }

        //se premuto button submitPeriod
        if(e.getSource() == filterPanel.submitPeriod){
            String fromDate, toDate;
            fromDate = toDate = "";

            //controlla in quale modalità si è

            //se modalità filtro per giorno singolo
            if(filterPanel.dayFilter.isSelected()){
                fromDate = filterPanel.period.getText();
                toDate = fromDate;
            }

            //se modalità filtro per mese singolo
            if(filterPanel.monthFilter.isSelected()){
                fromDate = "01/" + filterPanel.period.getText();

                //calcola il mese e deduce quanti giorni ha (tiene conto anche di anno bisestile)
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                try{
                    LocalDate temp = LocalDate.parse(fromDate, dtf);
                    toDate = temp.getMonth().length(temp.isLeapYear()) + "/" + filterPanel.period.getText();
                }
                catch(DateTimeException ex){
                    //vuoto perchè l'errore viene comunque riconosciuto dopo da dateFilter()
                }

            }

            //se modalità filtro per anno singolo
            if(filterPanel.yearFilter.isSelected()){
                fromDate = "01/01/" + filterPanel.period.getText();
                toDate = "31/12/" + filterPanel.period.getText();

                System.out.println(fromDate + " " + toDate);
            }

            try{
                Bilancio bilancioFiltrato;
                bilancioFiltrato = bilancio.dateFilter(fromDate, toDate);
                mainPanel.tableUpdate(bilancioFiltrato);
                mainPanel.resocontoUpdate(bilancioFiltrato);
                System.out.println("[SUCCESS] Bilancio filtrato correttamente");    
            }
            catch(IllegalArgumentException ex){
                System.out.println("[ERROR] " + ex.getMessage());
            }
        }
    
        //PANNELLO RICERCA
        if(e.getSource() == filterPanel.submitSearch){
            String toSearch = filterPanel.searchText.getText();

            System.out.println("[SUCCESS] Ricerca avviata per '" + toSearch + "'");

        }
    }

    /**
     * gestisce le azioni effettuate sulla tabella (selezione)
     * @param e evento catturato
     */
    public void valueChanged(ListSelectionEvent e){

        //controlla che la selezione sia finita
        if(!e.getValueIsAdjusting()){
            //System.out.println(table.getSelectedRow() + e.toString());
            
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
