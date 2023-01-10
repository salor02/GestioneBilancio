import autoSave.AutoSave;
import bilancioGUI.DefaultFrame;
import bilancioGUI.MainPanel;
import bilancioUtil.Bilancio;

/**
 * Classe principale del programma, avvio tutto il necessario
 */
public class GestioneBilancio {
    public static void main(String[] args) throws Exception {
        System.out.println("\tGesioneBilancio is running...");
        System.out.println("\t----------------------------\t\n");

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
        frame.setResizable(false);
    }
}
