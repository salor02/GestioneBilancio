package fileManager;

import bilancioUtil.Bilancio;
import java.io.*;

public abstract class FileManager {
    protected File file;

    public FileManager(File file){
        this.file = file;
    }

    public abstract void save(Bilancio bilancio) throws FileNotFoundException, IOException;
}
