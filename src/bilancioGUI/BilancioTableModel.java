package bilancioGUI;

import javax.swing.table.DefaultTableModel;
import bilancioUtil.*;

public class BilancioTableModel extends DefaultTableModel{
    private static final String[] COLUMN_NAMES = {"Data","Descrizione","Ammontare"};
    private int rowHighlighted, colHighlighted;
    
    /**
     * Invoca il costruttore della classe padre che permette di costuire una tabella che ha 
     * colonne nominate con l'array di String COLUMN_NAMES e n righe (in questo caso zero)
     */
    public BilancioTableModel(){
        super(COLUMN_NAMES, 0);
        this.rowHighlighted = -1;
        this.colHighlighted = -1;
    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return false;
    }

    public boolean isHighlighted(int row, int column){
        if(row == rowHighlighted && column == colHighlighted) return true;
        return false;
    }

    public void setHighlight(int row, int column){
        this.rowHighlighted = row;
        this.colHighlighted = column;
    }

}
