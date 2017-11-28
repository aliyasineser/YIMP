/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

/**
 *
 * @author aliyasineser
 */
public class HomeSceneController implements Initializable {

    private Stage window;
    private final Desktop desktop = Desktop.getDesktop();
    public BorderPane borderPane;
    public ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    public boolean openFile() throws Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return false; // path secimi iptal edildi
        }
        
        
        imageView.setImage(new UImage("file:"+file.getCanonicalPath()));
        //Display2D.invoke(img);
        /*MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters);
        Mean.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
        */
        
        /*
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params);
        Gaussian.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
        */
        /*
        SobelBundle operators = new SobelBundle();
        SobelRequestController.showRequestBox(operators);
        Sobel.invoke(img, operators.handleGetObject("operatorChoice").toString());
        */
        
        
        return true;
    }

    private static void configureFileChooserOpen(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*)", "*");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        fileChooser.getExtensionFilters().add(allFilter);
        fileChooser.getExtensionFilters().add(jpgFilter);
        fileChooser.getExtensionFilters().add(jpegFilter);
        fileChooser.getExtensionFilters().add(pngFilter);
    }

    /**
     * Exiting from Program.
     */
    public void closeProgram() {
        ((Stage) (borderPane.getScene().getWindow())).close();
    }

}
