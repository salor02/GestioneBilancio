import javax.swing.*;
import java.awt.*;

import bilancioGUI.*;
import bilancioUtil.*;

public class GestioneBilancio {
    public static void main(String[] args) throws Exception {
        System.out.println("GesioneBilancio is running...");

        //INIZIALIZZAZIONE BILANCIO
        Bilancio bilancio = new Bilancio();
        System.out.println("[SUCCESS] Bilancio creato!");

        //DEFINIZIONE PANNELLO PRINCIPALE CONTENITORE
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new OperationPanel(), BorderLayout.LINE_START);
        mainPanel.add(new CenterPanel(bilancio), BorderLayout.CENTER);

        //DEFINIZIONE FRAME
        DefaultFrame frame = new DefaultFrame("Gesione Bilancio");
        frame.setVisible(true);

        frame.add(mainPanel);
        
        frame.pack();
    }
}
