package autoSave;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import bilancioUtil.Bilancio;
import fileManager.BinaryManager;
import fileManager.FileManager;

/**
 * Gestisce il salvataggio automatico del bilancio su un file temporaneo
 */
public class AutoSave extends Thread{
    /**
     * Tempo in ms tra un salvataggio e l'altro
     */
    private long sleepTime;

    /**
     * bilancio da salare su file
     */
    private Bilancio bilancio;

    /**
     * Inizializza il thread con sleepTime e il bilancio da salvare
     * @param sleepTime tempo in ms tra un salvataggio e l'altro
     * @param bilancio bilancio da salvare
     */
    public AutoSave(long sleepTime, Bilancio bilancio){
        this.sleepTime = sleepTime;
        this.bilancio = bilancio;
    }

    @Override
    /**
     * Metodo che racchiude tutte le operazioni effettivamente svolte dal thread
     */
    public void run(){
        System.out.println("[SUCCESS] Thread AutoSave avviato");

        //definisce il percorso assoluto del file su cui salvare il bilancio
        File savDir = new File(System.getProperty("user.dir"), "sav");
        File tmpFile = new File(savDir, "salvataggio_auto");
        FileManager fileManager = new BinaryManager(tmpFile);

        //ciclo infinito
        while(true){
            try{
                Thread.sleep(sleepTime);
                
                //se il bilancio non ha movimenti non ha senso salvarlo
                if(bilancio.getLenght() == 0){
                    System.out.println("[ALERT][AUTOSAVE] File non salvato: bilancio vuoto");
                    continue;
                }
                fileManager.save(bilancio);
                System.out.println("[SUCCESS][AUTOSAVE] File salvato correttamente");
            }
            catch(InterruptedException ex){
                System.out.println("[ERROR][AUTOSAVE] Thread AutoSave terminato per un errore");
                return;
            }
            catch(FileNotFoundException ex){
                System.out.println("[ERROR][AUTOSAVE] File di salvataggio non trovato");
            }
            catch(IOException ex){
                System.out.println("[ERROR][AUTOSAVE] Problema nella scrittura del file di salvataggio");
            }
        }
    }
    
}
