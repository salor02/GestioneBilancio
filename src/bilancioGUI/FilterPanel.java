package bilancioGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Si occupa di inizializzare il pannello delle operazioni, contenente il pannello del filtro per data e quello per la ricerca 
 */
public class FilterPanel extends JPanel{
    /**
     * campo di testo utile ai filtri
     */
    protected JTextField dateA, dateB, searchText;

    /**
     * bottone di invio
     */
    protected JButton submitPeriod, submitSearch, filterOFF;

    /**
     * permette di selezionare una modalità di filtro
     */
    protected JRadioButton dayFilter, monthFilter, yearFilter, customFilter;

    /**
     * label per data
     */
    protected JLabel startDateLabel, endDateLabel;

    /**
     * Inizializza il panello
     */
    public FilterPanel(){
        super();

        //DEFINIZIONE PANNELLO FILTRA PER PERIODO
        JPanel periodPanel = new JPanel();

        periodPanel.setLayout(new GridLayout(3,4));

        //1 riga -> scelta modalità filtro
        dayFilter = new JRadioButton("Giorno");
        monthFilter = new JRadioButton("Mese");
        yearFilter = new JRadioButton("Anno");
        customFilter = new JRadioButton("Personalizzato");
        ButtonGroup grp = new ButtonGroup();
        grp.add(dayFilter); grp.add(monthFilter); grp.add(yearFilter); grp.add(customFilter);

        periodPanel.add(dayFilter);
        periodPanel.add(monthFilter);
        periodPanel.add(yearFilter);
        periodPanel.add(customFilter);

        dayFilter.setSelected(true);

        //2 riga -> dedicata solo alle label che compaiono in caso di filtro personalizzato, altrimenti riga vuota
        startDateLabel = new JLabel("Inizio", SwingConstants.CENTER);
        startDateLabel.setVisible(false);
        periodPanel.add(startDateLabel);

        endDateLabel = new JLabel("Fine", SwingConstants.CENTER);
        endDateLabel.setVisible(false);
        periodPanel.add(endDateLabel);

        periodPanel.add(new JLabel("")); //per creare spazio vuoto
        periodPanel.add(new JLabel("")); //per creare spazio vuoto

        //3 riga -> dedicata ai campi di inserimento e ai bottono invia o annulla
        dateA = new JTextField("dd/mm/yyyy"); //date A funge sia da "placeholder" sia da vero e proprio campo nel caso di filtro personalizzato
        dateA.setEditable(false);
        dateB = new JTextField(10);
        submitPeriod = new JButton("Filtra");
        filterOFF = new JButton("Annulla");

        periodPanel.add(dateA);
        periodPanel.add(dateB);
        periodPanel.add(submitPeriod);
        periodPanel.add(filterOFF);

        periodPanel.setBorder(BorderFactory.createTitledBorder("Filtra per periodo"));

        //DEFINIZIONE PANNELLO RICERCA CAMPO
        JPanel searchPanel = new JPanel();

        searchPanel.setLayout(new GridLayout(1,3));

        searchText = new JTextField(15);
        submitSearch = new JButton("Cerca");

        searchPanel.add(new JLabel("Inserire valore"));
        searchPanel.add(searchText);
        searchPanel.add(submitSearch);

        searchPanel.setBorder(BorderFactory.createTitledBorder("Ricerca valore"));

        //DEFINIZIONE PANNELLO CONTENITORE
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //i panneli figli sono messi in colonna

        this.add(periodPanel);
        this.add(searchPanel);
    }
}
