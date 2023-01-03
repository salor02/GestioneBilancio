package bilancioGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Questa classe si occupa di inizializzare il pannello delle operazioni, contenente il pannello dei
 * bottoni e il pannello del filtro per data
 */
public class OperationPanel extends JPanel{
    public OperationPanel(){
        super();

        //DEFINIZIONE PANNELLO BOTTONI
        JPanel btnPanel = new JPanel();
        JButton deleteLine = new JButton("Elimina riga selezionata");
        JButton addLine = new JButton("Aggiungi nuova riga");

        btnPanel.setLayout(new GridLayout(2,1,0,10)); //dispone i bottoni in colonna specificando un vgap (spazio verticale)

        btnPanel.add(deleteLine);
        btnPanel.add(addLine);

        //DEFINIZIONE PANNELLO CONTENITORE
        this.add(btnPanel);

        this.setBorder(BorderFactory.createTitledBorder("Operazioni"));

    }
}
