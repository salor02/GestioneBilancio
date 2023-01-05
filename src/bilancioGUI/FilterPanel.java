package bilancioGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Questa classe si occupa di inizializzare il pannello delle operazioni, contenente il pannello dei
 * bottoni e il pannello del filtro per data
 */
public class FilterPanel extends JPanel{
    protected JTextField dateA, dateB, searchText;
    protected JButton submitPeriod, submitSearch, filterOFF;
    protected JRadioButton dayFilter, monthFilter, yearFilter, customFilter;
    protected JLabel startDateLabel, endDateLabel;

    public FilterPanel(){
        super();

        //DEFINIZIONE PANNELLO FILTRA PER PERIODO
        JPanel periodPanel = new JPanel();

        periodPanel.setLayout(new GridLayout(3,4));

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

        startDateLabel = new JLabel("Inizio", SwingConstants.CENTER);
        startDateLabel.setVisible(false);
        periodPanel.add(startDateLabel);
        endDateLabel = new JLabel("Fine", SwingConstants.CENTER);
        endDateLabel.setVisible(false);
        periodPanel.add(endDateLabel);
        periodPanel.add(new JLabel("")); //per creare spazio vuoto
        periodPanel.add(new JLabel("")); //per creare spazio vuoto

        dateA = new JTextField("dd/mm/yyyy");
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
        this.add(new JPanel());


    }
}
