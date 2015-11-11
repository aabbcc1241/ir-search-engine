package hk.edu.polyu.ir.groupc.searchengine.frontend;

import comm.AlertUtils;
import hk.MainControllerSkeleton;
import hk.edu.polyu.ir.groupc.searchengine.Debug;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.DocFileFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.datasource.TermInfoFactory;
import javafx.event.ActionEvent;

import java.io.File;

/**
 * Created by beenotung on 11/9/15.
 */
public class MainController extends MainControllerSkeleton {
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        if (instance == null) throw new IllegalStateException("MainController has not been created");
        return instance;
    }

    public static void statusMain(String msg) {
        Debug.log(msg);
        getInstance().setLeftStatus(msg);
    }

    public static void statusMinor(String msg) {
        Debug.log(msg);
        getInstance().setRightStatus(msg);
    }

    public static void statusReset() {
        getInstance().resetStatus();
    }

    public static void statusDone(String lastAction) {
        getInstance().doneStatus(lastAction);
    }

    void setLeftStatus(String msg) {
        label_left_status.setText(msg);
    }

    void setRightStatus(String msg) {
        label_right_status.setText(msg);
    }

    @Override
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
    }

    void doneStatus(String lastAction) {
        setLeftStatus("done");
        setRightStatus(lastAction);
    }

    void resetStatus() {
        setLeftStatus("");
        setRightStatus("");
    }

    @Override
    public void build_index(ActionEvent event) {
        String action = "build post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermInfoFactory.build(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }

    @Override
    public void import_post(ActionEvent event) {
        String action = "import post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermInfoFactory.build(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }

    @Override
    public void export_index(ActionEvent event) {
        String action = "export post";
        statusMain(action);
        String filename = text_post.getText();
        try {
            TermInfoFactory.getTermIndex().writeToFile(filename);
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
        doneStatus(action);
    }
}
