package hk.edu.polyu.ir.groupc.searchengine.view;/**
 * Created by beenotung on 11/8/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    static String title = "IR 2015 GroupC";

//    public static void main(String[] args) {
//        launch(args);
//    }

    /* this method should be called once only */
    public void startUI() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
