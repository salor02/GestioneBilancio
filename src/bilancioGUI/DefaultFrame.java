package bilancioGUI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Classe che specializza la classe padre JFrame e definisce il menu in alto
 */
public class DefaultFrame extends JFrame{
    /**
     * Offset X dal punto di ancoraggio
     */
    private static final int DEFAULT_X_OFFSET = 100;

    /**
     * Offset Y dal punto di ancoraggio
     */
    private static final int DEFAULT_Y_OFFSET = 100;

    /**
     * Larghezza predefinita
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * Altezza predefinita
     */
    private static final int DEFAULT_HEIGHT = 800;

    /**
     * Titolo del menu "file"
     */
    private final String FILE_MENU_TITLE = "File";

    /**
     * Items del menu "file"
     */
    private final String[] FILE_MENU_ITEMS = {"Salva", "Carica", "Esporta CSV", "Esporta TXT", "Esporta ODS", "Stampa"};

    /**
     * Menu file
     */
    private JMenu fileMenu;

    /**
     * Inizializza un frame dal titolo dato come parametro e gestisce le dimensioni
     * secondo i parametri specificati all'interno della classe. Inoltre inserisce anche il menu in alto
     * @param title Stringa contenente il titolo della finestra
     */
    public DefaultFrame(String title){
        super(title);

        setBounds(DEFAULT_X_OFFSET, DEFAULT_Y_OFFSET, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //definizione barra menu in alto
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu(FILE_MENU_TITLE);
        for(String item: FILE_MENU_ITEMS){
            JMenuItem menuItem = new JMenuItem(item);
            fileMenu.add(menuItem);
        }
        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    /**
     * Restituisce il riferimento al menu inizializzato
     * @return menu
     */
    public JMenu getMenu(){
        return this.fileMenu;
    }
}
