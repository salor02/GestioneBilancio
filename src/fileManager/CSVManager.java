package fileManager;

import java.io.*;
import bilancioUtil.*;
import com.opencsv.CSVWriter;

public class CSVManager extends FileManager{
    public CSVManager(File file){
        super(file);
    }

    public void save(Bilancio bilancio) throws IOException{
        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(new FileWriter(this.file));
  
        // adding header to csv
        String[] header = { "Data", "Descrizione", "Ammontare" };
        writer.writeNext(header);
  
        // add data to csv
        for(VoceBilancio voce: bilancio.getListaMovimenti()){
            writer.writeNext(voce.toStringArray());
        }
  
        // closing writer connection
        writer.close();
    }
}
