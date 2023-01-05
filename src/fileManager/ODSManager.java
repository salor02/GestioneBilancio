package fileManager;

import java.io.*;
import bilancioUtil.*;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import bilancioGUI.BilancioTableModel;

public class ODSManager extends FileManager{
    public ODSManager(File file){
        super(file);
    }

    public void save(Bilancio bilancio) throws IOException, FileNotFoundException{
        BilancioTableModel model = new BilancioTableModel();

        //popola la tabella
        for(int i = 0; i < bilancio.getLenght(); i++){
            model.addRow(bilancio.getVoce(i).toObjectArray());  
        }

        SpreadSheet.createEmpty(model).saveAs(file);
    }
}
