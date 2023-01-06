package bilancioGUI;

import javax.swing.table.DefaultTableModel;

/**
 * Classe che specializza DefaultTableModel, utile per gestire la tabella che contiene il bilancio
 */
public class BilancioTableModel extends DefaultTableModel{
    /**
     * Nomi delle colonne
     */
    private static final String[] COLUMN_NAMES = {"Data","Descrizione","Ammontare"};

    /**
     * Posizione della cella evidenziata (se presente)
     */
    private int rowHighlighted, colHighlighted;
    
    /**
     * Invoca il costruttore della classe padre che permette di costuire una tabella che ha 
     * colonne nominate con l'array di String COLUMN_NAMES e n righe (in questo caso zero). 
     * Pone inoltre la posizione della cella evidenziata a -1 cioè nessuna cella evidenziata
     */
    public BilancioTableModel(){
        super(COLUMN_NAMES, 0);
        this.rowHighlighted = -1;
        this.colHighlighted = -1;
    }

    @Override
    /**
     * Specifica se una cella è modificabile, in questo caso nessuna cella lo è
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Specifica se una cella è evidenziata
     * @param row posizione X della cella evidenziata
     * @param column posizione Y della cella evidenziata
     * @return true se la cella è evidenziata, false altrimenti
     */
    public boolean isHighlighted(int row, int column){
        if(row == rowHighlighted && column == colHighlighted) return true;
        return false;
    }

    /**
     * Imposta la posizione di una cella evidenziata
     * @param row posizione X della cella evidenziata
     * @param column posizione Y della cella evidenziata
     */
    public void setHighlight(int row, int column){
        this.rowHighlighted = row;
        this.colHighlighted = column;
    }
}
