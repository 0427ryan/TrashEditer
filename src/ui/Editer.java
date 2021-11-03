package ui;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.LinkedList;
import java.io.*;

public class Editer {
    
    private TabPane pane;

    public Editer(){
        this.pane = new TabPane();
    }

    public void open(String fileName) throws IOException{
        this.open(new File(fileName));
    }

    public void open(File file) throws IOException{
        StringBuilder content = new StringBuilder("");
        FileReader reader = new FileReader(file);
        while(reader.ready()){
            content.append((char)reader.read());
        }
        reader.close();
        FileTab tab = new FileTab();
        tab.setText(file.getName());
        tab.setFile(file);
        tab.setContent(new TextArea(content.toString()));
        this.pane.getTabs().add(tab);
    }

    public void saveCurrent(){
        try{
            FileTab tab = (FileTab)(pane.getSelectionModel().getSelectedItem());
            if(tab.getFile().exists()){
                tab.getFile().delete();
            }
            tab.getFile().createNewFile();
            FileWriter writer = new FileWriter(tab.getFile());
            String s = ((TextArea)tab.getContent()).getText();
            writer.append(s);
            writer.close();
            System.out.println(tab.getFile() + "file saved");
        }catch(IOException e){
            System.out.println("save error");
        }
    }

    public String getLines(){
        Tab tab = pane.getSelectionModel().getSelectedItem();
        return ((TextArea)tab.getContent()).getText();
    }

    public void newFile(String fileName){
        FileTab tab = new FileTab();
        tab.setFile(new File(System.getProperty("user.dir") + "/" + fileName));
        tab.setText(fileName);
        tab.setContent(new TextArea());
        System.out.println(tab.getFile() + " create");
        this.pane.getTabs().add(tab);
    }

    public TabPane getPane(){
        return this.pane;
    }
    
}