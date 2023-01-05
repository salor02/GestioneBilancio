package bilancioGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Questa classe si occupa di inizializzare il pannello delle operazioni, contenente il pannello dei
 * bottoni e il pannello del filtro per data
 */
public class FilterPanel extends JPanel{
    protected JTextField fromDate, toDate, period, searchText;
    protected JButton submitFilter, submitPeriod, submitSearch;
    protected JRadioButton dayFilter, monthFilter, yearFilter;
    protected JLabel patternPeriod;

    public FilterPanel(){
        super();

        //DEFINIZIONE PANNELLO FILTRO DATA
        JPanel datePanel = new JPanel();

        datePanel.setLayout(new GridLayout(2,3)); //dispone i bottoni in colonna specificando un vgap (spazio verticale)

        datePanel.add(new JLabel("Inizio", SwingConstants.CENTER));
        datePanel.add(new JLabel("Fine", SwingConstants.CENTER));
        datePanel.add(new JLabel("")); //per creare spazio vuoto

        fromDate = new JTextField(10);
        toDate = new JTextField(10);
        submitFilter = new JButton("Filtra");
        
        datePanel.add(fromDate);
        datePanel.add(toDate);
        datePanel.add(submitFilter);

        datePanel.setBorder(BorderFactory.createTitledBorder("Filtra per data esatta"));

        //DEFINIZIONE PANNELLO FILTRA PER PERIODO
        JPanel periodPanel = new JPanel();

        periodPanel.setLayout(new GridLayout(2,3));

        dayFilter = new JRadioButton("Giorno");
        monthFilter = new JRadioButton("Mese");
        yearFilter = new JRadioButton("Anno");
        ButtonGroup grp = new ButtonGroup();
        grp.add(dayFilter); grp.add(monthFilter); grp.add(yearFilter);

        periodPanel.add(dayFilter);
        periodPanel.add(monthFilter);
        periodPanel.add(yearFilter);

        dayFilter.setSelected(true);

        patternPeriod = new JLabel("dd/mm/yyyy", SwingConstants.CENTER);
        patternPeriod.setForeground(Color.GRAY);
        period = new JTextField(10);
        submitPeriod = new JButton("Filtra");

        periodPanel.add(patternPeriod);
        periodPanel.add(period);
        periodPanel.add(submitPeriod);

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

        this.add(datePanel);
        this.add(periodPanel);
        this.add(searchPanel);
        this.add(new JPanel());


    }
}
