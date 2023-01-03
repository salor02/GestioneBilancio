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

    /**
     * Aggiorna la tabella
     */
    public void tableUpdate(Bilancio bilancio){
        this.setRowCount(0); //elimina tutte le voci presenti nella tabella

        //ripopola la tabella
        for(int i = 0; i < bilancio.getLenght(); i++){
            this.addRow(bilancio.getVoce(i).toObjectArray());
        }


        System.out.println("[SUCCESS] Tabella aggiornata");
    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return false;
    }

}
