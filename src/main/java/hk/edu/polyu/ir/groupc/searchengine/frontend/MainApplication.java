package hk.edu.polyu.ir.groupc.searchengine.frontend;

/**
 * Created by beenotung on 11/8/15.
 */

import comm.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.log_;

public class MainApplication extends Application {
    public static ThreadGroup threadGroup = new ThreadGroup("MainApplication thread group");
    static String title = "IR 2015 GroupC";
    private static MainApplication instance;
    private Stage mStage;

    public static MainApplication getInstance() {
        if (instance == null) throw new IllegalStateException("MainApplication has not been created");
        return instance;
    }

    public static void main(String[] args) {
        log_("MainApplication start");

        log_("Pre-loading");
        // any?

        log_("Launching GUI");
        /* this method call is blocking, will enter event driven mode */
        launch(args);

        log_("MainApplication closing");
        Utils.terminate(instance.threadGroup, false);

        log_("MainApplication end");
    }

    public static Scene getScene() {
        if (instance == null)
            throw new IllegalStateException("Main Application has not been created");
        return instance.getStage().getScene();
    }

    /*
    * This is method is blocking
    * */
    @Deprecated
    public void run() {
        launch();
    }

    public Stage getStage() {
        if (mStage == null) throw new IllegalStateException("stage has not been created in MainApplication");
        return mStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

//        Scene scene = new Scene(root, 800, 600);
        Scene scene = new Scene(root);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        mStage = stage;
    }
}
