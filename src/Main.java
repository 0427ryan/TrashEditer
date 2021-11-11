
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import static javafx.stage.FileChooser.ExtensionFilter;
import static javafx.scene.control.ButtonBar.ButtonData;

import java.io.File;

import ui.*;
import controller.Controller;

public class Main extends Application {

    //private Interpreter interpreter;

    @Override
    public void start(Stage stage) throws Exception {
        View view = new View();
        Controller controller = new Controller(view, stage);
        Scene scene = new Scene(view, 800, 600);

        stage.setScene(scene);
        stage.setTitle("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    

}