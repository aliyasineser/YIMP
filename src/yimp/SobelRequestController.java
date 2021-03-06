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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aliyasineser
 */
public class SobelRequestController implements Initializable {

    
    public ChoiceBox operatorChoiceBox;
    public Button calculateButton;
    SobelBundle paramBundle;
    public AnchorPane pane;
    
    public static void showRequestBox(SobelBundle bundle, URL url){
        try{
            Stage requestWindow = new Stage();
            requestWindow.initModality(Modality.APPLICATION_MODAL);
            requestWindow.setTitle("Sobel Parameters");
            Parent errorLayout = FXMLLoader.load(url , bundle);
            
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
        paramBundle = (SobelBundle)rb;
        operatorChoiceBox.getItems().add("Gradient Magnitude");
        operatorChoiceBox.getItems().add("Top Sobel(Vertical)");
        operatorChoiceBox.getItems().add("Bottom Sobel");
        operatorChoiceBox.getItems().add("Right Sobel(Horizontal)");
        operatorChoiceBox.getItems().add("Left Sobel");
    }
    
    
    public void calculate(){
        
        if(operatorChoiceBox.getSelectionModel().isEmpty())
            ErrorBoxController.showErrorBox("Error", "Selection Error", "Non of the operators are selected."
            , this.getClass().getResource("ErrorBox.fxml"));
        else{
            paramBundle.setObject( "operatorChoice", operatorChoiceBox.getSelectionModel().getSelectedItem().toString());
            closeWindow();
        }
    }
    
    
    public void closeWindow() {

        ((Stage) (pane.getScene().getWindow())).close();
    }
    
}
