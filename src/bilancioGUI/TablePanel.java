package bilancioGUI;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 * Questa classe si occupa di inizializzare il pannello della tabella, contenente il pannello della
 * tabella del bilancio e il pannello del resoconto del bilancio e il pannello per aggiungere/eliminare/modificare una voce
 */
public class TablePanel extends JPanel{
    /**
     * btn submit del pannello per aggiungere/eliminare/modificare una voce
     */
    protected JButton submitVoce;

    /**
     * permette di selezionare una delle modalità
     */
    protected JRadioButton addVoce, deleteVoce, updateVoce;

    /**
     * campo di testo dove inserire i dati della nuova voce
     */
    protected JTextField inputDate, inputDesc, inputAmount;

    /**
     * label contenuta nel pannello del resoconto
     */
    protected JLabel resultLabel, totalValue;

    /**
     * tabella del bilancio
     */
    protected JTable table;

    /**
     * valori attualmente selezionati nella tabella
     */
    protected String selDate, selDesc, selAmount;

    /**
     * modello della tabella implementata
     */
    protected BilancioTableModel dataModel;

    /**
     * Costruisce il pannello
     */
    public TablePanel(){
        super();

        //DEFINIZIONE PANNELLO TABELLA BILANCIO
        JPanel tablePanel = new JPanel(); 

        dataModel = new BilancioTableModel();
        table = new JTable(dataModel);

        //imposta il renderer personalizzato per ogni colonna
        for(int i = 0; i < table.getColumnModel().getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(new SearchRenderer());
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //permette di selezionare solo una voce
        
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        tablePanel.setBorder(BorderFactory.createTitledBorder("Tabella movimenti"));

        //DEFINIZIONE PANNELLO RESOCONTO BILANCIO
        JPanel totalPanel = new JPanel();

        JLabel todayDateLabel = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), SwingConstants.CENTER);
        resultLabel = new JLabel("PARI", SwingConstants.CENTER);
        totalValue = new JLabel("€ 0", SwingConstants.CENTER);

        totalPanel.setLayout(new GridLayout(1,3));

        totalPanel.add(todayDateLabel);
        totalPanel.add(resultLabel);
        totalPanel.add(totalValue);

        totalPanel.setBorder(BorderFactory.createTitledBorder("Resoconto"));

        //DEFINIZIONE PANNELLO OPERAZIONI BILANCIO
        JPanel opsPanel = new JPanel();

        opsPanel.setLayout(new GridLayout(3,4));

        //1 riga -> scelta operazione da effettuare
        opsPanel.add(new JLabel("Scegli operazione: ", SwingConstants.LEFT));

        addVoce = new JRadioButton("Aggiungi");
        deleteVoce = new JRadioButton("Elimina");
        updateVoce = new JRadioButton("Modifica");
        ButtonGroup grp = new ButtonGroup();
        grp.add(addVoce); grp.add(deleteVoce); grp.add(updateVoce);

        opsPanel.add(addVoce);
        opsPanel.add(deleteVoce);
        opsPanel.add(updateVoce);

        //all'avvio si disattivano i button che richiedono la selezione di una riga e si seleziona quello per aggiungere
        deleteVoce.setEnabled(false);
        updateVoce.setEnabled(false);
        addVoce.setSelected(true);
        
        //2 riga -> label dei campi di testo
        opsPanel.add(new JLabel("Data", SwingConstants.CENTER));
        opsPanel.add(new JLabel("Descrizione", SwingConstants.CENTER));
        opsPanel.add(new JLabel("Ammontare", SwingConstants.CENTER));
        opsPanel.add(new JLabel("")); //per creare una cella vuota

        //3 riga -> inserimento campi di testo e button submit
        inputDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))); //valore di default
        inputDesc = new JTextField();
        inputAmount = new JTextField();

        opsPanel.add(inputDate);
        opsPanel.add(inputDesc);
        opsPanel.add(inputAmount);

        submitVoce = new JButton("Invia");
        opsPanel.add(submitVoce);

        opsPanel.setBorder(BorderFactory.createTitledBorder("Operazioni su movimenti"));

        //DEFINIZIONE PANNELLO CONTENITORE
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); //i panneli figli sono messi in colonna

        this.add(tablePanel);
        this.add(totalPanel);
        this.add(opsPanel);
    }

    /**
     * Attiva e disattiva i 3 campi di testo che sono presenti all'interno del pannello che permette
     * di aggiungere, modificare o eliminare voci
     * @param flag true si vuole attivare i campi di testo, false altrimenti
     */
    protected void switchInputText(boolean flag){
        inputDate.setEditable(flag);
        inputDesc.setEditable(flag);
        inputAmount.setEditable(flag);
    }

}
