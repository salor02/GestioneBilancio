package bilancioGUI;

import javax.swing.table.DefaultTableModel;
import bilancioUtil.*;

public class BilancioTableModel extends DefaultTableModel{
    private static final String[] COLUMN_NAMES = {"Data","Descrizione","Ammontare"};
    
    /**
     * Invoca il costruttore della classe padre che permette di costuire una tabella che ha 
     * colonne nominate con l'array di String COLUMN_NAMES e n righe (in questo caso zero)
     */
    public BilancioTableModel(){
        super(COLUMN_NAMES, 0);
    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return false;
    }

}
