package ui;

import static javafx.scene.control.ButtonType.*;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class NewFilePrompt extends DialogPane {

    public NewFilePrompt(){
        this.setWidth(400);
        this.setHeight(300);
        this.getButtonTypes().add(CANCEL);
        this.getButtonTypes().add(OK);
        TextField input = new TextField();
        input.setText("untitled.cs");
        this.setContent(input);
    }

    public String getInput(){
        return ((TextField)this.getContent()).getText();
    }
    
    
}