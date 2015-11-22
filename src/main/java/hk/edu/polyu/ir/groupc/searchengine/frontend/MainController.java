package hk.edu.polyu.ir.groupc.searchengine.frontend;

import comm.AlertUtils;
import hk.edu.polyu.ir.groupc.searchengine.Debug;
import hk.edu.polyu.ir.groupc.searchengine.Launcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;

/**
 * Created by beenotung on 11/9/15.
 */
public class MainController extends MainControllerSkeleton {
    private static MainController instance;

    int resultId = 0;

    void setDataPath(String dataPath) {
        text_data_path.setText(dataPath);
        new Thread(MainApplication.getInstance().threadGroup, "setDataPathThread") {
            @Override
            public void run() {
                try {
                    Debug.log("set data path to <" + dataPath + ">", true, false);
                    launcher = new Launcher() {
                        {
                            filePath(dataPath + "/file.txt");
                            termIndexPath(dataPath + "/term_index.txt");
                            postPath(dataPath + "/post1.txt");
                            stopPath(dataPath + "/estop.lst");
                            judgeRobustPath(dataPath + "/judgerobust");
//                    queryPath(dataPath + "/queryT");
//                    queryPath(dataPath + "/queryTDN");
//                    RESULT_FILE(dataPath + "/result.txt");
                        }
                    };
                    launcher.init();
                    statusDone("set Data Path");
                } catch (comm.exception.RichFileNotFoundException e) {
                    String msg = "Please make sure you have <" + e.path + ">";
                    AlertUtils.error("Error", "Failed to init index", msg);
                    Debug.logMainStatus(msg);
                    statusMinor("set Data Path");
                }
            }
        }.start();
    }

    Launcher launcher;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        if (instance == null) throw new IllegalStateException("MainController has not been created");
        return instance;
    }

    public static void statusMain(String msg) {
//        Debug.log_(msg);
        Platform.runLater(() -> {
            getInstance().setLeftStatus(msg);
        });
    }

    public static void statusMinor(String msg) {
//        Debug.log_(msg);
        Platform.runLater(() -> {
            getInstance().setRightStatus(msg);
        });
    }

    public static void statusReset() {
        getInstance().resetStatus();
    }

    public static String getMajorStatus() {
        return getInstance().label_left_status.getText();
    }

    public static void statusDone() {
        Platform.runLater(() -> {
            statusDone(getInstance().label_left_status.getText());
        });
    }

    public static void statusDone(String lastAction) {
        Platform.runLater(() -> {
            getInstance().doneStatus(lastAction);
        });
    }

    void setLeftStatus(String msg) {
        label_left_status.setText(msg);
    }

    void setRightStatus(String msg) {
        label_right_status.setText(msg);
    }

    @Override
    void set_data_path(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Set Data Path");
//        fileChooser.showOpenDialog(MainApplication.getInstance().getStage());
//        fileChooser.showOpenMultipleDialog(MainApplication.getInstance().getStage());
//        fileChooser.showSaveDialog(MainApplication.getInstance().getStage());
//        try {
//            Desktop.getDesktop().open(new File("res/"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Set Data Path");
        File directory = directoryChooser.showDialog(MainApplication.getInstance().getStage());
        if (directory != null)
            setDataPath(directory.getPath());
    }

    /*@Override
    public void import_file(ActionEvent event) {
        String action = "import file";
        statusMain(action);
        String filename = text_file.getText();
        try {
            DocFileFactory.load(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }*/

    void doneStatus(String lastAction) {
        setLeftStatus("done");
        setRightStatus(lastAction);
    }

    void resetStatus() {
        setLeftStatus("");
        setRightStatus("");
    }

    /*@Override
    public void build_index(ActionEvent event) {
        String action = "build post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermIndexFactory.build(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }*/

    /*@Override
    public void import_post(ActionEvent event) {
        String action = "import post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermIndexFactory.build(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }*/

    /*@Override
    public void export_index(ActionEvent event) {
        String action = "export post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermIndexFactory.getTermIndex().writeToFile(filename);
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }*/
}
