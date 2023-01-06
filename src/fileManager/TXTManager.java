package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import bilancioUtil.Bilancio;
import bilancioUtil.VoceBilancio;

/**
 * Gestisce il salvataggio del bilancio su File TXT
 */
public class TXTManager extends FileManager{

    /**
     * Imposta il file su cui operare
     * @param file file su cui operare
     */
    public TXTManager(File file){
        super(file);
    }

    @Override
    public void save(Bilancio bilancio) throws IOException{
        //sorgente
        FileWriter fout = new FileWriter(this.file);

        //scrive le voci su file
        for(VoceBilancio voce: bilancio.getListaMovimenti()){
            fout.write(voce.toString());
            fout.write('\n');
        }

        //chiusura connessione
        fout.close();
    }
}
