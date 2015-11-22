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

import java.io.IOException;

import static hk.edu.polyu.ir.groupc.searchengine.Debug.log_;

public class MainApplication extends Application {
    static String title = "IR 2015 GroupC";
    private static MainApplication instance;
    public ThreadGroup threadGroup = new ThreadGroup("MainApplication thread group");

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
        ThreadGroup threadGroup = instance.threadGroup;
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            thread.interrupt();
            while (thread.isAlive()) {
                Debug.logd("terminating " + thread.toString());
                thread.stop();
            }
        }
        threadGroup.destroy();
        log_("MainApplication end");
    }

    /*
    * This is method is blocking
    * */
    @Deprecated
    public void run() {
        launch();
    }

    private Stage mStage;

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
