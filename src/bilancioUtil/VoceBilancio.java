package bilancioUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.io.Serializable;
import java.time.DateTimeException;


/**
 * Ogni oggetto di questa classe rappresenta semplicemente una voce del bilancio
 */
public class VoceBilancio implements Serializable{
    private LocalDate date;
    private String desc;
    private double amount;

    private static final long serialVersionUID = 1L;

    /**
     * @param date Data in formato stringa "dd/mm/yyyy"
     * @param desc Descrizione movimento
     * @param amount Ammontare movimento
     * 
     * Questo costruttore prende in ingresso una data formattata come stringa e 
     * successivamente la converte in un oggetto Date
     */
    public VoceBilancio(String date, String desc, String amount) throws IllegalArgumentException{

        //verifica che tutti i campi abbiano una stringa valida
        if(date.isEmpty() || desc.isEmpty() || amount.isEmpty()) 
            throw new IllegalArgumentException("Input non completo");

        //verifica che la stringa inserita nel campo ammontare sia effettivamente un numero
        try{
            this.amount = Double.parseDouble(amount);
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
     * Questo costruttore prende in ingresso un oggetto Date per cui non ha bisogno di 
     * svolgere nessun'altra operazione
     * @param date Data sottoforma di oggetto Date
     * @param desc Descrizione movimento
     * @param amount Ammontare movimento
     */
    public VoceBilancio(LocalDate date, String desc, double amount){
        this.date = date;
        this.desc = desc;
        this.amount = amount;
    }

    /**
     * 
     * @return campo "data"
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * 
     * @return campo "descrizione"
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 
     * @return campo "ammontare"
     */
    public double getAmount() {
        return amount;
    }

    /**
     * 
     * @return oggetto voce sottoforma di oggetto (utile per inserire voce in tabella)
     */
    public Object[] toObjectArray(){
        return new Object[]{this.date.format(Bilancio.dtf), this.desc, this.amount};
    }

    public String toString(){
        return this.date.format(Bilancio.dtf) + '\t' + this.desc + '\t' + this.amount;
    }

    public String[] toStringArray(){
        return new String[]{this.date.format(Bilancio.dtf), this.desc, Double.toString(this.amount)};
    }

    @Override
    public boolean equals(Object obj){
        //se oggetto nullo o non è instanza di vocebilancio
        if(obj == null || !(obj instanceof VoceBilancio)) return false;

        //casting esplicito
        VoceBilancio voce = (VoceBilancio) obj;

        //verifica uguaglianza attributi
        if(this.desc.equals(voce.getDesc()) && this.amount == voce.getAmount() && this.date.equals(voce.getDate()))
            return true;
        return false;
    }

}