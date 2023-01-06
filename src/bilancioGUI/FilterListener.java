package bilancioGUI;

import javax.swing.JOptionPane;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import bilancioUtil.Bilancio;


/**
 * Questa classe si occupa di gestire tutti gli eventi provenienti dal pannello filtro
 */
public class FilterListener implements ActionListener{

    /**
     * pannello filtro
     */
    private FilterPanel filterPanel;

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
     * variabile contenente l'ultima parola cercata
     */
    private String searchedWord;

    /**
     * lista contenente tutti i risultati della ricerca
     */
    private LinkedList<Point> listaRisultatiRicerca;

    /**
     * Inizializza tutti gli attributi della classe e tutti i listener
     * @param mainPanel il pannello principale tramite il quale è possibile accedere a tutti gli elementi
     */
    public FilterListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.filterPanel = mainPanel.filterPanel;
        this.tablePanel = mainPanel.tablePanel;
        this.bilancio = mainPanel.bilancio;

        this.searchedWord = "";
        this.listaRisultatiRicerca = new LinkedList<Point>();

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

        //se non è stato premuto il button per modalità custom o se non è selezionato vengono nascoste le label dedicate
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

            //applica effettivamente il filtro
            try{
                Bilancio bilancioFiltrato;
                bilancioFiltrato = bilancio.dateFilter(fromDate, toDate);
                mainPanel.tableUpdate(bilancioFiltrato);
                mainPanel.resocontoUpdate(bilancioFiltrato);
                System.out.println("[SUCCESS] Bilancio filtrato correttamente");    
            }
            catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                System.out.println("[ERROR] " + ex.getMessage());
            }
        }
    
        //se premuto button annulla filtro
        if(e.getSource() == filterPanel.filterOFF){
            mainPanel.tableUpdate(bilancio);
            mainPanel.resocontoUpdate(bilancio);
            System.out.println("[SUCCESS] Filtro annullato"); 
        }

        //PANNELLO RICERCA
        if(e.getSource() == filterPanel.submitSearch){

            //se la parola cercata è diversa dall'ultima cercata bisogna effettuare una nuova ricerca
            if(!searchedWord.equals(filterPanel.searchText.getText())){

                searchedWord = filterPanel.searchText.getText();
                System.out.println("[SUCCESS] Ricerca avviata per '" + searchedWord + "'");

                //ricerca parola nella TABELLA (non nella lista) questo perchè sarà utile capire la posizione nella tabella per evidenziare le celle
                for(int i = 0; i < tablePanel.table.getRowCount(); i++){
                    for(int j = 0; j < tablePanel.table.getColumnCount(); j++){
                        if(tablePanel.table.getValueAt(i, j).toString().contains(searchedWord)){
                            System.out.println("[SUCCESS] Trovata corrispondenza nella cella (" + i + "," + j + ")");
                            this.listaRisultatiRicerca.add(new Point(i,j)); //inserisce nella lista sottoforma di point perchè è utile per memorizzare coppia di valori
                        }
                    }
                }
            }

            //se la lista dei risultati ha ancora qualcosa dentro li evidenzia
            if(listaRisultatiRicerca.size() > 0){
                Point next = listaRisultatiRicerca.remove();
                tablePanel.dataModel.setHighlight((int)next.getX(), (int)next.getY());
                System.out.println("[SUCCESS] Evidenziata occorrenza di '" + searchedWord + "' (" + (int)next.getX() + "," + (int)next.getY() + ")");  
            }//se la lista non ha più risultati elimina ogni evidenziatura
            else{
                tablePanel.dataModel.setHighlight(-1, -1);
                System.out.println("[ALERT] Nessun risultato trovato o occorrenze terminate");
            }

            //necessario per applicare le evidenziature 
            tablePanel.table.repaint();    
        }
    }

   
}
