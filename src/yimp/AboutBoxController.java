/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aliyasineser
 */
public class AboutBoxController implements Initializable {

    public GridPane gridWindow;
    public ImageView image;
    public Label messageLabel;
    public Label detailsLabel;
    public Button okButton;

    public static void showBox(String windowTitle, String title, String message, URL url) {

        try {
            Stage errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.setTitle(windowTitle);

            Parent errorLayout = FXMLLoader.load(url, new ErrorBundle(title, message));
            Scene scene = new Scene(errorLayout);
            errorWindow.setScene(scene);
            errorWindow.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messageLabel.setText(rb.getString("title"));
        detailsLabel.setText(rb.getString("message"));
        //enter'a basildiginda hata mesaji ekrani kapanir
        okButton.setOnKeyPressed((KeyEvent key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                okButton.fire();
            }
        });

        try {
            if (System.getProperty("os.name").contains("Windows")) {
                image.setImage(new Image(this.getClass().getResourceAsStream("/Assets/yimp.png")));
            } else {
                image.setImage(new Image(this.getClass().getResourceAsStream("/Assets/yimp.png")));
            }
        } catch (NullPointerException e) {
            
        }

    }

    public void closeWindow() {

        ((Stage) (gridWindow.getScene().getWindow())).close();
    }
}
