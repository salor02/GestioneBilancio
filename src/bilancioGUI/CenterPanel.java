package bilancioGUI;

import javax.naming.NameNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import bilancioUtil.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Questa classe si occupa di inizializzare il pannello centrale, contenente il pannello della
 * tabella del bilancio e il pannello del resoconto del bilancio
 */
public class CenterPanel extends JPanel implements ActionListener, ListSelectionListener{
    private JButton submitVoce;
    private JRadioButton addVoce, deleteVoce, updateVoce;
    private JTextField inputDate, inputDesc, inputAmount;
    private JLabel resultLabel, totalValue;

    private JTable table;
    private String selDate, selDesc, selAmount;
    private BilancioTableModel dataModel;
    private Bilancio bilancio;

    public CenterPanel(Bilancio bilancio){
        super();
        this.bilancio = bilancio;

        //DEFINIZIONE PANNELLO TABELLA BILANCIO
        JPanel tablePanel = new JPanel(); 

        dataModel = new BilancioTableModel();
        table = new JTable(dataModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //permette di selezionare solo una voce
        table.getSelectionModel().addListSelectionListener(this);
        
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

        addVoce.addActionListener(this); opsPanel.add(addVoce);
        deleteVoce.addActionListener(this); opsPanel.add(deleteVoce);
        updateVoce.addActionListener(this); opsPanel.add(updateVoce);

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
        inputDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))); //valore di defalt
        inputDesc = new JTextField();
        inputAmount = new JTextField();

        opsPanel.add(inputDate);
        opsPanel.add(inputDesc);
        opsPanel.add(inputAmount);

        submitVoce = new JButton("Invia");
        submitVoce.addActionListener(this);
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
     * @param flag True si vuole attivare i campi di testo, false altrimenti
     */
    private void switchInputText(boolean flag){
        inputDate.setEditable(flag);
        inputDesc.setEditable(flag);
        inputAmount.setEditable(flag);
    }

    /**
     * Gestisce le azioni dei pulsanti del pannello
     * @param e evento catturato
     */
    public void actionPerformed(ActionEvent e){
        //se pulsante premuto è submitVoce 
        if(e.getSource() == submitVoce){

            //preleva i dati inseriti nei campi di testo
            String date = inputDate.getText();
            String desc = inputDesc.getText();
            String amount = inputAmount.getText();

            try{
                //se modalità "aggiungi"
                if(addVoce.isSelected()){
                    bilancio.addVoce(date, desc, amount);
                    dataModel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Nuova voce inserita nel bilancio");
                }
                //se modalità "elimina"
                if(deleteVoce.isSelected()){
                    bilancio.deleteVoce(date, desc, amount);
                    dataModel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Voce eliminata dal bilancio correttamente");
                }
                //se modalità "modifica"
                if(updateVoce.isSelected()){
                    bilancio.updateVoce(selDate, selDesc, selAmount, date, desc, amount);
                    dataModel.tableUpdate(bilancio);
                    System.out.println("[SUCCESS] Voce modificata correttamente");
                }
                
                //riattiva tutti i campi di testo e ritorna in modalità "aggiungi"
                this.switchInputText(true);
                inputDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
                inputDesc.setText("");
                inputAmount.setText("");
                addVoce.setSelected(true);

                //aggiornamento resoconto bilancio
                totalValue.setText("€ " + bilancio.totalValue());
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
        if(e.getSource() == addVoce){
            System.out.println("[OPTION] Aggiungi voce");

            this.switchInputText(true);

            inputDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
            inputDesc.setText("");
            inputAmount.setText("");
        }

        //entra in modalità "elimina" o "modifica"
        if(e.getSource() == deleteVoce || e.getSource() == updateVoce){

            inputDate.setText(selDate);
            inputDesc.setText(selDesc);
            inputAmount.setText(selAmount);

            //se si è in modalità "elimina" tutti i campi vengono bloccati per prevenirne la modifica e conseguenti bug
            if(e.getSource() == deleteVoce){
                System.out.println("[OPTION] Elimina voce");

                this.switchInputText(false);
            }
            else{
                System.out.println("[OPTION] Modifica voce");

                this.switchInputText(true);
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
            //System.out.println(table.getSelectedRow() + e.toString());
            
            int row = table.getSelectedRow();

            //se è stata selezionata una riga
            if(row >= 0){
                //abilita i radio button per attivare le modalità "elimina" e "modifica"
                deleteVoce.setEnabled(true);
                updateVoce.setEnabled(true);
                
                //preleva i valori selezionati
                selDate = table.getValueAt(row, 0).toString();
                selDesc = table.getValueAt(row, 1).toString();
                selAmount = table.getValueAt(row, 2).toString();

                //se si è gia in modalità "elimina" o "modifica" aggiorna i campi di testo secondo la nuova selezione
                if(deleteVoce.isSelected() || updateVoce.isSelected()){
                    inputDate.setText(selDate);
                    inputDesc.setText(selDesc);
                    inputAmount.setText(selAmount);
                }
            }
            //se è stata deselezionata una riga o comunque non ci sono righe selezionate
            else{
                //disattiva i radio button di "elimina" e "modifica"
                deleteVoce.setEnabled(false);
                updateVoce.setEnabled(false);
            }
            
        }
    }
}
