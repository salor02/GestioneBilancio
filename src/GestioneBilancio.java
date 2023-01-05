import javax.swing.*;

import bilancioGUI.*;
import bilancioUtil.*;
import autoSave.*;

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

        //AVVIO THREAD PER SALVATAGGIO AUTOMATICO
        AutoSave autoSave = new AutoSave(30 * 1000, bilancio);
        autoSave.start(); 
        
        frame.setVisible(true);

        frame.add(mainPanel);
        
        frame.pack();
    }
}
