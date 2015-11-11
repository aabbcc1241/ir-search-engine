package hk.edu.polyu.ir.groupc.searchengine.frontend;

/**
 * Created by beenotung on 11/8/15.
 */

import hk.edu.polyu.ir.groupc.searchengine.Debug;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;

import java.io.IOException;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.*;

public class MainApplication extends Application {
    static String title = "IR 2015 GroupC";

    public static void main(String[] args) {
        log("MainApplication start");

        log("Pre-loading");
        // any?

        log("Launching GUI");
        /* this method call is blocking, will enter event driven mode */
        launch(args);

        log("MainApplication end");
    }

    /*
    * This is method is blocking
    * */
    @Deprecated
    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

//        Scene scene = new Scene(root, 800, 600);
        Scene scene = new Scene(root);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
