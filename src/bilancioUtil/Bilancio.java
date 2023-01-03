package bilancioUtil;

import java.util.LinkedList;

import javax.naming.NameNotFoundException;


/**
 * Questa classe si occupa della gestione del bilancio e di tutte le sue voci,
 * le quali sono memorizzate in una LinkedList dato che ha costo computazionale costante
 * per gli inserimenti in testa e le cancellazioni, operazioni molto frequenti in un bilancio.
 */
public class Bilancio {
    private LinkedList<VoceBilancio> listaMovimenti;

    /**
     * Inizializza lista che andrà a contenere i movimenti
     */
    public Bilancio(){
        this.listaMovimenti = new LinkedList<VoceBilancio>();
    }

    /**
     * Utile per cercare una voce all'interno del bilancio
     * @param voce oggetto da cercare
     * @return indice della voce trovata, in caso nessuna voce venga trovata restituisce -1
     */
    private int indexOf(VoceBilancio voce){
        //scorre tutta la lista per controllare se la voce è presente
        for(int i = 0; i < listaMovimenti.size(); i++){
            if(voce.equals(listaMovimenti.get(i))){
                return i;
            }
        }
        return -1;
    }

    /**
     * Restituisce la lunghezza della lista
     * @return lunghezza lista
     */
    public int getLenght(){
        return listaMovimenti.size();
    }

    /**
     * 
     * @param index indice della voce da restituire
     * @return voce della lista di indice passato come parametro
     */
    public VoceBilancio getVoce(int index){
        //se l'index non esiste ritorna un oggetto vuoto
        if(index < 0 || index > listaMovimenti.size()-1) return null;

        return listaMovimenti.get(index);
    }

    /**
     * Aggiunge una voce alla lista dei movimenti
     * @param date data in formato stringa
     * @param desc descrizione movimento
     * @param amount ammontare movimento
     * @throws IllegalArgumentException per segnalare la presenza di parametri errati
     */
    public void addVoce(String date, String desc, String amount) throws IllegalArgumentException{
        //il controllo sui dati inseriti viene effettuato a livello di vocebilancio
        listaMovimenti.add(new VoceBilancio(date, desc, amount));
    }

    /**
     * Rimuove una voce dalla lista dei movimenti
     * @param date data in formato stringa
     * @param desc descrizione movimento
     * @param amount ammontare movimento
     * @throws IllegalArgumentException per segnalare la presenza di parametri errati
     * @throws NameNotFoundException generata se non viene trovata nessuna corrispondenza 
     */
    public void deleteVoce(String date, String desc, String amount) throws IllegalArgumentException, NameNotFoundException{
        VoceBilancio toDelete = new VoceBilancio(date, desc, amount);

        int toDeleteIndex = indexOf(toDelete);
        if(toDeleteIndex >= 0){
            listaMovimenti.remove(toDeleteIndex);
        }
        else{
            throw new NameNotFoundException("Voce inserita non esistente");
        }
    }

    /**
     * Modifica la voce avente i parametri passati assegnando i nuovi parametri passati 
     * @param oldDate vecchia data in formato stringa
     * @param oldDesc vecchia descrizione
     * @param oldAmount vecchio ammontare
     * @param newDate nuova data in formato stringa
     * @param newDesc nuova descrizione
     * @param newAmount nuovo ammontare
     * @throws IllegalArgumentException per segnalare la presenza di parametri errati
     * @throws NameNotFoundException generata se non viene trovata nessuna corrispondenza
     */
    public void updateVoce(String oldDate, String oldDesc, String oldAmount, String newDate, String newDesc, String newAmount) throws IllegalArgumentException, NameNotFoundException{
        VoceBilancio toUpdate = new VoceBilancio(oldDate, oldDesc, oldAmount);
        VoceBilancio newVoce = new VoceBilancio(newDate, newDesc, newAmount);

        int toUpdateIndex = indexOf(toUpdate);
        if(toUpdateIndex >= 0){
            listaMovimenti.set(toUpdateIndex, newVoce);
        }
        else{
            throw new NameNotFoundException("Voce inserita non esistente");
        }
    }
  
    /**
     * 
     * @return Valore totale del bilancio
     */
    public double totalValue(){
        double sum = 0;

        for(VoceBilancio voce: listaMovimenti){
            sum += voce.getAmount();
        }

        return sum;
    }
}
