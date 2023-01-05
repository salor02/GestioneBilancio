package autoSave;

import bilancioUtil.*;
import fileManager.*;

import java.io.*;

public class AutoSave extends Thread{
    private long sleepTime;
    private Bilancio bilancio;

    public AutoSave(long sleepTime, Bilancio bilancio){
        this.sleepTime = sleepTime;
        this.bilancio = bilancio;
    }

    @Override
    public void run(){
        System.out.println("[SUCCESS] Thread AutoSave avviato");

        File savDir = new File(System.getProperty("user.dir"), "sav");
        File tmpFile = new File(savDir, "salvataggio_auto");
        FileManager fileManager = new BinaryManager(tmpFile);

        while(true){
            try{
                Thread.sleep(sleepTime);
                
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
