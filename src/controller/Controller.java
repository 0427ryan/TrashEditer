package controller;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.Tab;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import static javafx.stage.FileChooser.ExtensionFilter;

import ui.View;
import interpreter.*;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.TreeMap;

public class Controller {
    
    private TreeMap<String, File> tabToFile = new TreeMap<>();

    private  Interpreter interpreter;

    public Controller(View view, Stage stage){
        view.getRunButton().setOnAction( e ->{
            if(this.interpreter == null){
                this.interpreter = new Interpreter(view.getOutput(), view.getCurrentContent());
                this.interpreter.execute();
                if(this.interpreter.isFinish()){
                    this.setInterpreter(null);
                }
            }
        });

        view.getEnterButton().setOnAction(e->{
            if(this.interpreter != null){
                this.interpreter.setUserInput(view.getInput());
                this.interpreter.execute();
                if(this.interpreter.isFinish()){
                    this.setInterpreter(null);
                }
            }
        });

        MenuItem create = new MenuItem("New file");
        MenuItem open = new MenuItem("Open");
        MenuItem saveas = new MenuItem("Save As");
        MenuItem save = new MenuItem("Save");

        create.setOnAction(e ->{
            view.createTab("untitled.cs", "");
        });

        open.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Cs Files", "*.cs"),
            new ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                    StringBuilder builder = new StringBuilder();
                    while(reader.ready()){
                        builder.append((char)reader.read());
                    }
                    view.createTab(file.getName(), builder.toString());
                    this.tabToFile.put(view.getEditingTab().getText(), file);
                }catch (IOException ex) {ex.printStackTrace();}
            }
        });

        saveas.setOnAction(e -> {
            if(view.getEditingTab() != null){
                this.saveas(view, stage);
            } 
        });

        save.setOnAction( e->{
            if(view.getEditingTab() != null) {
                if(this.tabToFile.containsKey(view.getEditingTab().getText())){
                    try(FileWriter writer = new FileWriter(tabToFile.get(view.getEditingTab().getText()))){
                        writer.append(view.getCurrentContent());
                    }catch (IOException ex) {}
                }
                else{
                    this.saveas(view, stage);
                }
            }
        });
        Menu menu = new Menu("File");
        menu.getItems().addAll(create, open, save, saveas);

        view.getMenuBar().getMenus().add(menu);
    }

    public void saveas(View view, Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Cs Files", "*.cs"),
        new ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            //file.create();
            try(FileWriter writer = new FileWriter(file)){
                writer.append(view.getCurrentContent());
                this.tabToFile.put(view.getEditingTab().getText(), file);
                view.getEditingTab().setText(file.getName());
            }catch (IOException ex) {ex.printStackTrace();}
        }
    }

    public void setInterpreter(Interpreter i){
        this.interpreter = i;
    } 
    public Interpreter getInterpreter(){
        return interpreter;
    }   
}
