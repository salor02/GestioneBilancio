package fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bilancioUtil.Bilancio;

/**
 * Gestisce il salvataggio del bilancio su File sfruttando il polimorfismo
 */
public abstract class FileManager {
    /**
     * File su cui operare
     */
    protected File file;

    /**
     * Imposta il file su cui operare
     * @param file file su cui operare
     */
    public FileManager(File file){
        this.file = file;
    }

    /**
     * Gestisce il salvataggio del bilancio su file
     * @param bilancio bilancio da salvare
     * @throws FileNotFoundException sollevata se non viene trovato il file specificato
     * @throws IOException sollevata se c'è stato qualche errore in fase di I/O
     */
    public abstract void save(Bilancio bilancio) throws FileNotFoundException, IOException;
}
