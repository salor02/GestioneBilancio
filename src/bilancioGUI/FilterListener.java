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
public class FilterListener implements ActionListener{
    private FilterPanel filterPanel;
    private Bilancio bilancio;
    private MainPanel mainPanel;

    /**
     * Inizializza il listener mantenendo un riferimento ai pannelli passati come parametro
     * @param tablePanel pannello contenente la tabella e tutte le operazioni su di essa eseguibili
     * @param filterPanel pannello che permette di filtrare i dati mostrati in tabella
     * @param bilancio oggetto bilancio che contiene tutti i dati su cui si sta lavorando
     */
    public FilterListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.filterPanel = mainPanel.filterPanel;
        this.bilancio = mainPanel.bilancio;

        this.filterPanel.dayFilter.addActionListener(this);
        this.filterPanel.monthFilter.addActionListener(this);
        this.filterPanel.yearFilter.addActionListener(this);
        this.filterPanel.customFilter.addActionListener(this);
        this.filterPanel.submitPeriod.addActionListener(this);
        this.filterPanel.submitSearch.addActionListener(this);
        this.filterPanel.filterOFF.addActionListener(this);
    }

   
    
    /**
     * Gestisce le azioni dei pulsanti del pannello
     * @param e evento catturato
     */
    public void actionPerformed(ActionEvent e){
        //PANNELLO FILTRA PER PERIODO

        if(e.getSource() != filterPanel.customFilter && !filterPanel.customFilter.isSelected()){
            filterPanel.dateA.setEditable(false);
            filterPanel.startDateLabel.setVisible(false);
            filterPanel.endDateLabel.setVisible(false);
        }

        //se premuto radio button giorno
        if(e.getSource() == filterPanel.dayFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo giorno");
            filterPanel.dateA.setText("dd/mm/yyyy");
        }

        //se premuto radio button mese
        if(e.getSource() == filterPanel.monthFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo mese");
            filterPanel.dateA.setText("mm/yyyy");
        }

        //se premuto radio button anno
        if(e.getSource() == filterPanel.yearFilter){
            System.out.println("[OPTION] Selezionato filtro per singolo anno");
            filterPanel.dateA.setText("yyyy");
        }

        //se premuto radio button date custom
        if(e.getSource() == filterPanel.customFilter){
            System.out.println("[OPTION] Selezionato filtro per intervallo di date personalizzato");
            filterPanel.dateA.setText("");
            filterPanel.dateA.setEditable(true);
            filterPanel.startDateLabel.setVisible(true);
            filterPanel.endDateLabel.setVisible(true);
        }

        //se premuto button submitPeriod
        if(e.getSource() == filterPanel.submitPeriod){
            String fromDate, toDate;
            fromDate = toDate = "";

            //controlla in quale modalità si è

            //se modalità filtro per giorno singolo
            if(filterPanel.dayFilter.isSelected()){
                fromDate = filterPanel.dateB.getText();
                toDate = fromDate;
            }

            //se modalità filtro per mese singolo
            if(filterPanel.monthFilter.isSelected()){
                fromDate = "01/" + filterPanel.dateB.getText();

                //calcola il mese e deduce quanti giorni ha (tiene conto anche di anno bisestile)
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                try{
                    LocalDate temp = LocalDate.parse(fromDate, dtf);
                    toDate = temp.getMonth().length(temp.isLeapYear()) + "/" + filterPanel.dateB.getText();
                }
                catch(DateTimeException ex){
                    //vuoto perchè l'errore viene comunque riconosciuto dopo da dateFilter()
                }

            }

            //se modalità filtro per anno singolo
            if(filterPanel.yearFilter.isSelected()){
                fromDate = "01/01/" + filterPanel.dateB.getText();
                toDate = "31/12/" + filterPanel.dateB.getText();
            }

            //se modalità filtro per periodo personalizzato
            if(filterPanel.customFilter.isSelected()){
                fromDate = filterPanel.dateA.getText();
                toDate = filterPanel.dateB.getText();
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
    
        //se premuto button annulla filtro
        if(e.getSource() == filterPanel.filterOFF){
            mainPanel.tableUpdate(bilancio);
            System.out.println("[SUCCESS] Filtro annullato"); 
        }

        //PANNELLO RICERCA
        if(e.getSource() == filterPanel.submitSearch){
            String toSearch = filterPanel.searchText.getText();

            System.out.println("[SUCCESS] Ricerca avviata per '" + toSearch + "'");

        }
    }

   
}
