package ui;

import javafx.scene.layout.*;
import javafx.scene.control.*;

import static javafx.geometry.Orientation.VERTICAL;

public class View extends VBox{
    
    private MenuBar menubar;
    private Button runbt;
    private Button enterbt;

    private TextArea output; 
    private TabPane editerPane;
    private TextField input;

    public View(){
        this.menubar = new MenuBar();
        this.runbt = new Button("run");
        this.enterbt = new Button("enter");

        this.output = new TextArea();
        this.editerPane = new TabPane();
        this.input = new TextField();

        editerPane.setMinHeight(300);
        output.setEditable(false);
        ScrollPane outputPane = new ScrollPane(output);
        outputPane.setFitToHeight(true);
        outputPane.setFitToWidth(true);
        outputPane.prefHeightProperty().bind(this.widthProperty());

        HBox bottom = new HBox(input, enterbt, runbt);
        this.enterbt.setPrefWidth(100);
        this.runbt.setPrefWidth(100);
        this.input.prefWidthProperty().bind(bottom.widthProperty().subtract(200));

        SplitPane pane = new SplitPane(editerPane, new VBox(outputPane, bottom));
        pane.setDividerPositions(0.6);
        pane.prefHeightProperty().bind(this.heightProperty());
        pane.setOrientation(VERTICAL);
        this.getChildren().addAll(menubar, pane);
    }

    public void createTab(String title, String content){
        TextArea area = new TextArea();
        area.setText(content);
        ScrollPane pane = new ScrollPane(area);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(pane);
        this.editerPane.getTabs().add(tab);
    }

    public String getCurrentContent(){
        ScrollPane pane = ((ScrollPane)this.getEditingTab().getContent());
        return ((TextArea)pane.getContent()).getText();
    }

    public MenuBar getMenuBar(){
        return this.menubar;
    }

    public Tab getEditingTab(){
        return this.editerPane.getSelectionModel().getSelectedItem();
    }

    public Button getRunButton(){
        return this.runbt;
    }

    public Button getEnterButton(){
        return this.enterbt;
    }

    public TextArea getOutput(){
        return output;
    }

    public TabPane getEditerPane(){
        return this.editerPane;
    }

    public String getInput(){
        return this.input.getText();
    }
    
}