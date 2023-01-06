package bilancioGUI;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JPanel;

import bilancioUtil.Bilancio;

/**
 * Pannello principale del programma, racchiude tutti gli altri pannelli e ha anche un riferimento alla barra dei menu in alto. 
 * Si occupa principalmente di posizionare i sottopannelli al suo interno e di inizializzare i vari listener
 */
public class MainPanel extends JPanel{
    /**
     * panello dedicato alla tabella e alle operazioni di aggiunta/eliminazione/modifica
     */
    protected TablePanel tablePanel;

    /**
     * pannello dedicato ai filtri
     */
    protected FilterPanel filterPanel;

    /**
     * menu in alto
     */
    protected JMenu fileMenu;

    /**
     * bilancio su cui operare
     */
    protected Bilancio bilancio;

    /**
     * Inizializza il pannello
     * @param bilancio bilancio su cui operare
     * @param fileMenu riferimento alla barra dei menu in alto
     */
    public MainPanel(Bilancio bilancio, JMenu fileMenu){
        super();
        
        //i pannelli sono incolonnati
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.tablePanel = new TablePanel();
        this.filterPanel = new FilterPanel();
        this.fileMenu = fileMenu;
        this.bilancio = bilancio;

        //inizializzazione dei listener (uno diverso per ogni pannello e uno per la barra dei menu)
        FilterListener filterListener = new FilterListener(this);
        TableListener tableListener = new TableListener(this);
        MenuListener menuListener = new MenuListener(this);

        this.add(filterPanel);
        this.add(tablePanel);
    }

    /**
     * Aggiorna il resoconto della tabella
     * @param bilancio bilancio di cui calcolare il resoconto
     */
    protected void resocontoUpdate(Bilancio bilancio){
        tablePanel.totalValue.setText("€ " + String.format("%.2f",bilancio.totalValue()));

        //definizione della descrizione posta di fianco al totale
        if(bilancio.totalValue() > 0){
            tablePanel.resultLabel.setText("POSITIVO");
            tablePanel.resultLabel.setForeground(Color.GREEN.darker());
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
