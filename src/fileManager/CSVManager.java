package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import bilancioUtil.Bilancio;
import bilancioUtil.VoceBilancio;

/**
 * Gestisce il salvataggio del bilancio su File CSV
 */
public class CSVManager extends FileManager{

    /**
     * Imposta il file su cui operare
     * @param file file su cui operare
     */
    public CSVManager(File file){
        super(file);
    }

    @Override
    public void save(Bilancio bilancio) throws IOException{

        //csv write è praticamente una classe filtro
        CSVWriter writer = new CSVWriter(new FileWriter(this.file));
  
        //imposta gli header
        String[] header = { "Data", "Descrizione", "Ammontare" };
        writer.writeNext(header);
  
        //scrive effettivamente il bilancio 
        for(VoceBilancio voce: bilancio.getListaMovimenti()){
            writer.writeNext(voce.toStringArray());
        }
  
        //chiude la connessione
        writer.close();
    }
}
