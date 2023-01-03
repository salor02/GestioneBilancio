package bilancioGUI;

import javax.swing.*;

/**
 * Classe che specializza la classe padre JFrame
 */
public class DefaultFrame extends JFrame{
    private static final int DEFAULT_X_OFFSET = 100;
    private static final int DEFAULT_Y_OFFSET = 100;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private static final String FILE_MENU_TITLE = "File";
    private static final String[] FILE_MENU_ITEMS = {"Salva", "Carica", "Esporta", "Stampa"};

    /**
     * Il costruttore inizializza un frame dal titolo dato come parametro e gestisce le dimensioni
     * secondo i parametri specificati all'interno della classe. Inoltre inserisce anche il menu in alto
     * @param title Stringa contenente il titolo della finestra
     */
    public DefaultFrame(String title){
        super(title);
        setBounds(DEFAULT_X_OFFSET, DEFAULT_Y_OFFSET, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //definizione barra menu in alto
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(FILE_MENU_TITLE);
        for(String item: FILE_MENU_ITEMS){
            fileMenu.add(new JMenuItem(item));
        }
        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }
}
