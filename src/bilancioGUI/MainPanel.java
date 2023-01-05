package bilancioGUI;

import bilancioUtil.*;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JMenu;

public class MainPanel extends JPanel{
    protected TablePanel tablePanel;
    protected FilterPanel filterPanel;
    protected JMenu fileMenu;
    protected Bilancio bilancio;

    public MainPanel(Bilancio bilancio, JMenu fileMenu){
        super();
        
        this.setLayout(new FlowLayout());

        this.tablePanel = new TablePanel();
        this.filterPanel = new FilterPanel();
        this.fileMenu = fileMenu;
        this.bilancio = bilancio;

        MainListener listener = new MainListener(this);
        MenuListener menuListener = new MenuListener(this);

        this.add(filterPanel);
        this.add(tablePanel);

    }

    protected void resocontoUpdate(Bilancio bilancio){
        tablePanel.totalValue.setText("€ " + bilancio.totalValue());
        if(bilancio.totalValue() > 0){
            tablePanel.resultLabel.setText("POSITIVO");
            tablePanel.resultLabel.setForeground(Color.GREEN);
        }else{
            if(bilancio.totalValue() < 0){
                tablePanel.resultLabel.setText("NEGATIVO");
                tablePanel.resultLabel.setForeground(Color.RED);
            }
            else{
                tablePanel.resultLabel.setText("PARI");
                tablePanel.resultLabel.setForeground(Color.BLACK);
            }
        }
    }

    /**
     * Aggiorna la tabella
     * @param bilancio oggetto bilancio da inserire nella tabella
     */
    protected void tableUpdate(Bilancio bilancio){
        BilancioTableModel dataModel;

        dataModel = (BilancioTableModel) tablePanel.table.getModel();
        dataModel.setRowCount(0); //elimina tutte le voci presenti nella tabella

        //ripopola la tabella
        for(int i = 0; i < bilancio.getLenght(); i++){
            dataModel.addRow(bilancio.getVoce(i).toObjectArray());  
        }

        System.out.println("[SUCCESS] Tabella aggiornata");
    }
}
