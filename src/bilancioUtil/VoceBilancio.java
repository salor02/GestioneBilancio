package bilancioUtil;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Ogni oggetto di questa classe rappresenta una voce singola del bilancio
 */
public class VoceBilancio implements Serializable{
    /**
     * Data in cui è stato effettuato il movimento
     */
    private LocalDate date;

    /**
     * Descrizione del movimento
     */
    private String desc;

    /**
     * Ammontare del movimento
     */
    private double amount;

    /**
     * Necessario per gestire la versione dell'oggetto serializzato (procedimento che avviene quando si salva su file binario)
     */
    private static final long serialVersionUID = 1L;

    /**
     * Questo costruttore prende in ingresso una data formattata come stringa e 
     * successivamente la converte in un oggetto Date
     * 
     * @param date Data in formato stringa "dd/mm/yyyy"
     * @param desc Descrizione movimento
     * @param amount Ammontare movimento
     * @exception IllegalArgumentException viene sollevata in caso i dati inseriti dovessero contenere qualche errore
     */
    public VoceBilancio(String date, String desc, String amount) throws IllegalArgumentException{

        //verifica che tutti i campi abbiano una stringa valida
        if(date.isEmpty() || desc.isEmpty() || amount.isEmpty()) 
            throw new IllegalArgumentException("Input non completo");

        //verifica che la stringa inserita nel campo ammontare sia effettivamente un numero
        try{
            this.amount = Double.parseDouble(amount.replace(",", "."));
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException("Ammontare inserito non valido");
        }

        //verifica che la data inserita sia valida
        try{
            this.date = LocalDate.parse(date, Bilancio.dtf);
        }
        catch(DateTimeException e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Data inserita non valida");
        }

        this.desc = desc;
    }

    /**
     * Restituisce il campo date
     * @return campo "data"
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Restituisce il campo descrizione
     * @return campo "descrizione"
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Restituisce il campo ammontare
     * @return campo "ammontare"
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Converte l'oggetto VoceBilancio in una Stringa
     * @return oggetto sottoforma di stringa
     */
    public String toString(){
        return this.date.format(Bilancio.dtf) + '\t' + this.desc + '\t' + this.amount;
    }

    /**
     * Converte l'oggetto VoceBilancio in un array di oggetti Stringa
     * @return array di oggetti Stringa
     */
    public String[] toStringArray(){
        return new String[]{this.date.format(Bilancio.dtf), this.desc, String.format("%.2f",this.amount)};
    }

    @Override
    /**
     * Override della funzione generica equals, permette di verificare se due voci sono uguali confrontando tutti i campi
     * @param obj oggetto da confrontare
     * @return true se le voci sono uguali, false altrimenti
     */
    public boolean equals(Object obj){
        //se oggetto nullo o non è instanza di vocebilancio
        if(obj == null || !(obj instanceof VoceBilancio)) return false;

        //casting esplicito
        VoceBilancio voce = (VoceBilancio) obj;

        //conversione a stringa per confrontare solo le prime 2 cifre decimali
        String amount1 = String.format("%.2f",this.amount);
        String amount2 = String.format("%.2f",voce.getAmount());

        //verifica uguaglianza attributi
        if(this.desc.equals(voce.getDesc()) && amount1.equals(amount2)  && this.date.equals(voce.getDate()))
            return true;
        return false;
    }
}