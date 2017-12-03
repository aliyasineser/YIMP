/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aliyasineser
 */
public class GaussianRequestController implements Initializable {

    public TextField sigmaField;
    public TextField kernelSizeField;
    public Button calculateButton;
    GaussianBundle paramBundle;
    public AnchorPane pane;
    /**
     * Show the box
     * @param bundle info bundle
     * @param url 
     * @param title box title
     */
    public static void showRequestBox(GaussianBundle bundle,String title, URL url) {
        try {
            Stage requestWindow = new Stage();
            requestWindow.initModality(Modality.APPLICATION_MODAL);
            requestWindow.setTitle(title);
            Parent errorLayout = FXMLLoader.load(url, bundle);
            
            Scene scene = new Scene(errorLayout);
            requestWindow.setScene(scene);
            requestWindow.showAndWait();
        } catch (Exception ex) {

        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paramBundle = (GaussianBundle) rb;
        // force the field to be numeric only
        kernelSizeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    kernelSizeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        sigmaField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    sigmaField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    /**
     * control of infos
     */
    public void calculate() {
        if (kernelSizeField.getText().equals("") || sigmaField.getText().equals("")) {
            ErrorBoxController.showErrorBox("Error", "Empty Field Error", "All fields must be filled.", 
                    this.getClass().getResource("ErrorBox.fxml"));
        } else {
            int kSize = Integer.valueOf(kernelSizeField.getText());
            if (kSize % 2 == 0 || kSize <= 1) {
                ErrorBoxController.showErrorBox("Error", "Kernel Size Error", "Kernel size should be a positive odd number except one.", 
                        this.getClass().getResource("ErrorBox.fxml"));
            } else {
                paramBundle.setObject("kernelSize", Integer.valueOf(kernelSizeField.getText()));
                paramBundle.setObject("sigma", Double.valueOf(sigmaField.getText()));
                closeWindow();
            }
        }
    }

    /**
     * Closes the window
     */
    public void closeWindow() {

        ((Stage) (pane.getScene().getWindow())).close();
    }

}
