package fileManager;

import java.io.*;
import bilancioUtil.Bilancio;
import bilancioUtil.VoceBilancio;

public class TXTManager extends FileManager{
    public TXTManager(File file){
        super(file);
    }

    public void save(Bilancio bilancio) throws IOException{
        FileWriter fout = new FileWriter(this.file);

        for(VoceBilancio voce: bilancio.getListaMovimenti()){
            fout.write(voce.toString());
            fout.write('\n');
        }

        fout.close();
    }
}
