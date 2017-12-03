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
public class MeanRequestController implements Initializable {

    
    public TextField kernelSizeField;
    public Button calculateButton;
    MeanBundle paramBundle;
    public AnchorPane pane;
    
    public static void showRequestBox(MeanBundle bundle, String title, URL url){
        try{
            Stage requestWindow = new Stage();
            requestWindow.initModality(Modality.APPLICATION_MODAL);
            requestWindow.setTitle(title);
            Parent errorLayout = FXMLLoader.load(url, bundle);
            
            Scene scene = new Scene(errorLayout);
            requestWindow.setScene(scene);
            requestWindow.showAndWait();
        }
        catch(Exception ex){
        
        }
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paramBundle = (MeanBundle)rb;
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
    }
    
    
    public void calculate(){
        if (kernelSizeField.getText().equals("")) 
            ErrorBoxController.showErrorBox("Error", "Empty Field Error", "All fields must be filled.", this.getClass().getResource("ErrorBox.fxml"));
        else{
            int kSize = Integer.valueOf(kernelSizeField.getText()).intValue();
            if (kSize % 2 == 0 || kSize <= 1) {
                ErrorBoxController.showErrorBox("Error", "Kernel Size Error", "Kernel size should be a positive odd number except one.", this.getClass().getResource("ErrorBox.fxml"));
            }else{
                paramBundle.setObject( "kernelSize", Integer.valueOf(kernelSizeField.getText()) );
                closeWindow();
            }
        }
    }
    
    
    public void closeWindow() {

        ((Stage) (pane.getScene().getWindow())).close();
    }
    
}
