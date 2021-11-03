package ui;

import javafx.scene.control.Tab;
import java.io.File;

public class FileTab extends Tab{
    
    private File file;

    public FileTab(){
        
    }

    public void setFile(File f){
        file = f;
    }

    public File getFile(){
        return file;
    }
}