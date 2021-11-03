package ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.SplitPane;
import static javafx.geometry.Orientation.VERTICAL;

import java.io.IOException;
import java.io.File;

import interpreter.*;

public class EditAndRunPane extends VBox{

    private Interpreter interpreter;
    private Editer editer;
    private Console console;

    public EditAndRunPane(){
        this.editer = new Editer();
        this.console = new Console();
        SplitPane splitPane = new SplitPane();

        console.getRunButton().setOnAction(e -> {
            console.clearOutput();
            console.appendLine("Start");
            setInterpreter(new Interpreter(console, editer.getLines()));
            if(interpreter.execute() == 0){
                setInterpreter(null);
            }
        });

        console.getEnterButton().setOnAction(e -> {
            if(interpreter != null){
                interpreter.setUserInput(console.getInputText());
                if(interpreter.execute() == 0){
                    setInterpreter(null);
                }
            }
        });

        splitPane.setOrientation(VERTICAL);
        splitPane.prefHeightProperty().bind(this.heightProperty());
        splitPane.getItems().addAll(editer.getPane(), console);
        splitPane.setDividerPositions(0.8);

        this.newFile("untitled.cs");
        this.getChildren().addAll(splitPane);

    }

    public void openFile(String fileName){
        try{
            this.editer.open(fileName);
        }
        catch(IOException e){
            console.clearOutput();
            console.appendLine("Something wrong happen when opening the file named" + fileName + ", open failed");
        }
    }

    public void openFile(File file){
        try{
            this.editer.open(file);
        }
        catch(IOException e){
            console.clearOutput();
            console.appendLine("Something wrong happen when opening the file named" + file.getName() + ", open failed");
        }
    }

    public void newFile(String fileName){
        this.editer.newFile(fileName);
    }

    public void saveCurrent(){
        this.editer.saveCurrent();
    }

    public void setInterpreter(Interpreter i){
        this.interpreter = i;
    }
    
}