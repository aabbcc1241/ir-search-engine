/**
 * Sample Skeleton for 'MainWindow.fxml' Controller Class
 */

package hk.edu.polyu.ir.groupc.searchengine.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MainControllerSkeleton {

    @FXML // ResourceBundle that was given to the FXMLLoader
    protected ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    protected URL location;

    @FXML // fx:id="text_data_path"
    protected TextField text_data_path; // Value injected by FXMLLoader

    @FXML // fx:id="text_query_file"
    protected TextField text_query_file; // Value injected by FXMLLoader

    @FXML // fx:id="text_result_path"
    protected TextField text_result_path; // Value injected by FXMLLoader

    @FXML // fx:id="combo_model"
    protected ComboBox<?> combo_model; // Value injected by FXMLLoader

    @FXML // fx:id="label_left_status"
    protected Label label_left_status; // Value injected by FXMLLoader

    @FXML // fx:id="x3"
    protected Font x3; // Value injected by FXMLLoader

    @FXML // fx:id="label_right_status"
    protected Label label_right_status; // Value injected by FXMLLoader

    @FXML
    void set_data_path(ActionEvent event) {

    }

    @FXML
    void set_query_file(ActionEvent event) {

    }

    @FXML
    void set_result_path(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert text_data_path != null : "fx:id=\"text_data_path\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert text_query_file != null : "fx:id=\"text_query_file\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert text_result_path != null : "fx:id=\"text_result_path\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert combo_model != null : "fx:id=\"combo_model\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert label_left_status != null : "fx:id=\"label_left_status\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert label_right_status != null : "fx:id=\"label_right_status\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }
}

