package fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import bilancioGUI.BilancioTableModel;
import bilancioUtil.Bilancio;

/**
 * Gestisce il salvataggio del bilancio su File ODS
 */
public class ODSManager extends FileManager{

    /**
     * Imposta il file su cui operare
     * @param file file su cui operare
     */
    public ODSManager(File file){
        super(file);
    }

    @Override
    public void save(Bilancio bilancio) throws IOException, FileNotFoundException{
        BilancioTableModel model = new BilancioTableModel();

        //popola la tabella
        for(int i = 0; i < bilancio.getLenght(); i++){
            model.addRow(bilancio.getVoce(i).toStringArray());  
        }

        //aggiunge la tabella al foglio di lavoro
        SpreadSheet.createEmpty(model).saveAs(file);
    }
}
