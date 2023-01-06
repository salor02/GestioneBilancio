package fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bilancioUtil.Bilancio;

/**
 * Gestisce il salvataggio del bilancio su File binari
 */
public class BinaryManager extends FileManager{

    /**
     * Imposta il file su cui operare
     * @param file file su cui operare
     */
    public BinaryManager(File file){
        super(file);
    }

    @Override
    public void save(Bilancio bilancio) throws FileNotFoundException, IOException{
        //sorgente
        FileOutputStream f = new FileOutputStream(this.file);

        //filtro
        ObjectOutputStream os = new ObjectOutputStream(f);

        os.writeObject(bilancio);
        os.close();
    }

    /**
     * Carica un bilancio dal file
     * @return bilancio caricato da file
     * @throws FileNotFoundException sollevata se non viene trovato il file specificato
     * @throws IOException sollevata se c'è stato qualche errore in fase di I/O
     * @throws ClassNotFoundException sollevata se non viene trovata la classe Bilancio
     */
    public Bilancio upload()  throws FileNotFoundException, IOException, ClassNotFoundException{
        //sorgente
        FileInputStream f = new FileInputStream(this.file);

        //filtro
        ObjectInputStream is = new ObjectInputStream(f);

        Bilancio bilancioUploaded = null;
        bilancioUploaded = (Bilancio) is.readObject();
        is.close();

        return bilancioUploaded;
    }
}
