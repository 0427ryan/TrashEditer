
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import static javafx.stage.FileChooser.ExtensionFilter;
import static javafx.scene.control.ButtonBar.ButtonData;

import java.io.File;

import ui.*;
import interpreter.*;

public class Main extends Application {

    private Interpreter interpreter;

    @Override
    public void start(Stage stage) throws Exception {

        EditAndRunPane editer = new EditAndRunPane();

        Button newbt = new Button("new");
        Button openbt = new Button("open");
        Button savebt = new Button("save");

        newbt.setOnAction(e->{
            Dialog prompt = new Dialog();
            prompt.setTitle("Please enter file name");
            NewFilePrompt pane = new NewFilePrompt();
            prompt.setDialogPane(pane);
            prompt.showAndWait();
            System.out.println(prompt.getResult());
            if(((ButtonType)prompt.getResult()).getButtonData().equals(ButtonData.OK_DONE)){
                editer.newFile(pane.getInput());
            }
        });
        openbt.setOnAction(e-> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Code Files", "*.cs"),
                new ExtensionFilter("All Files", "*.*"));
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                editer.openFile(file);
            }
        });
        savebt.setOnAction(e->editer.saveCurrent());

        VBox sideBar = new VBox(newbt, openbt, savebt);
        newbt.setPrefWidth(200);
        openbt.setPrefWidth(200);
        savebt.setPrefWidth(200);
        sideBar.setMinWidth(200);

        BorderPane mainPane = new BorderPane();
        mainPane.setLeft(sideBar);
        mainPane.setCenter(editer);
        mainPane.setMinWidth(800);
        mainPane.setMinHeight(600);
        Scene scene = new Scene(mainPane, 800, 600);

        stage.setScene(scene);
        stage.setTitle("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public void setInterpreter(Interpreter i) {
        this.interpreter = i;
    }

    public static void main(String[] args) {
        launch(args);
    }

}