package hk.edu.polyu.ir.groupc.searchengine.frontend;

import comm.AlertUtils;
import hk.edu.polyu.ir.groupc.searchengine.Debug;
import hk.edu.polyu.ir.groupc.searchengine.model.DocFileFactory;
import hk.edu.polyu.ir.groupc.searchengine.model.TermInfoFactory;
import javafx.event.ActionEvent;

import java.io.File;

/**
 * Created by beenotung on 11/9/15.
 */
public class MainController extends MainControllerSkeleton {
    @Override
    void import_file(ActionEvent event) {
        Debug.log("import file");
        String filename = text_file.getText();
        try {
            DocFileFactory.load(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
    }

    @Override
    void build_index(ActionEvent event) {
        Debug.log("build post");
        String filename = text_post.getText();
        try {
            TermInfoFactory.build(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
    }

    @Override
    void import_post(ActionEvent event) {
        Debug.log("import post");
        String filename = text_post.getText();
        try {
            TermInfoFactory.load(new File(filename));
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
    }

    @Override
    void export_index(ActionEvent event) {
        Debug.log("export post");
        String filename = text_post.getText();
        try {
            TermInfoFactory.getTermIndex().toFile(filename);
        } catch (Exception e) {
            Debug.exception(e);
            AlertUtils.error_(e.toString());
        }
    }
}
