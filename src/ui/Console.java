package ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;

public class Console extends VBox{

    private Button enterButton;
    private Button runButton;
    private Button nextButton;
    private TextArea output;
    private TextField input;

    public Console() {
        output = new TextArea();
        input = new TextField();
        enterButton = new Button("enter");
        runButton = new Button("run");
        nextButton = new Button("next");

        output.editableProperty().set(false);
        ScrollPane outputPane = new ScrollPane(output);
        outputPane.setFitToWidth(true);
        outputPane.setFitToHeight(true);
        outputPane.prefHeightProperty().bind(this.heightProperty().subtract(input.heightProperty()));

        runButton.setPrefWidth(100);
        enterButton.setPrefWidth(100);

        input.setPromptText("This is input area");
        input.prefWidthProperty().bind(this.widthProperty().subtract(200));
        this.setFillWidth(true);

        this.getChildren().addAll(outputPane, new HBox(input, enterButton, runButton));
    }

    public Button getRunButton(){
        return this.runButton;
    }

    public Button getEnterButton(){
        return this.enterButton;
    }

    public String getInputText(){
        return this.input.getText();
    }

    public void appendOutput(String s){
        this.output.setText(s);
    }

    public void clearOutput(){
        this.output.setText("");
    }

    public void appendLine(String s){
        this.output.setText(this.output.getText() + s + '\n');
    }

}