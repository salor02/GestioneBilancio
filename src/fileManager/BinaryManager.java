package fileManager;

import java.io.*;
import bilancioUtil.Bilancio;

public class BinaryManager extends FileManager{

    public BinaryManager(File file){
        super(file);
    }

    public void save(Bilancio bilancio) throws FileNotFoundException, IOException{
        FileOutputStream f = new FileOutputStream(this.file);
        ObjectOutputStream os = new ObjectOutputStream(f);

        os.writeObject(bilancio);
        os.close();
    }

    public Bilancio upload() throws Exception{
        FileInputStream f = new FileInputStream(this.file);
        ObjectInputStream is = new ObjectInputStream(f);

        Bilancio bilancioUploaded = null;
        bilancioUploaded = (Bilancio) is.readObject();
        is.close();

        return bilancioUploaded;
    }
}
