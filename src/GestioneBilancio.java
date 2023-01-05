import javax.swing.*;

import bilancioGUI.*;
import bilancioUtil.*;

public class GestioneBilancio {
    public static void main(String[] args) throws Exception {
        System.out.println("GesioneBilancio is running...");

        //INIZIALIZZAZIONE BILANCIO
        Bilancio bilancio = new Bilancio();
        System.out.println("[SUCCESS] Bilancio creato!");

        //DEFINIZIONE FRAME
        DefaultFrame frame = new DefaultFrame("Gesione Bilancio");

        //DEFINIZIONE PANNELLO PRINCIPALE CONTENITORE
        MainPanel mainPanel = new MainPanel(bilancio, frame.getMenu());

        
        frame.setVisible(true);

        frame.add(mainPanel);
        
        frame.pack();
    }
}
