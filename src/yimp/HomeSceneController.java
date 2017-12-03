/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vpt.ByteImage;
import vpt.DoubleImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.frequential.FFT;
import vpt.algorithms.io.Load;
import vpt.algorithms.io.Save;

/**
 *
 * @author aliyasineser
 */
public class HomeSceneController implements Initializable {

    private Stage window;
    private final Desktop desktop = Desktop.getDesktop();
    public BorderPane borderPane;
    public ImageView imageView;
    public TreeView<UImage> tree;
    public UImage rootImg;
    public ScrollPane scroll;
    public TreeItem<UImage> root;
    HashMap<Image, String> nameMap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (System.getProperty("os.name").contains("Windows")) {
            rootImg = new UImage("file:src\\Assets\\root.png");
        } else {
            rootImg = new UImage("file:src/Assets/root.png");
        }

        root = new TreeItem<>(rootImg);
        tree.setRoot(root);
        nameMap = new HashMap<>();

        //tree.setPrefSize(scroll.getWidth(), scroll.getHeight());
    }

    public boolean openFile() throws Exception {
        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return false; // path secimi iptal edildi
        }

        UImage chosenImage = new UImage("file:" + file.getCanonicalPath());
        imageView.setImage(chosenImage);
        root.getChildren().add(new TreeItem<>(chosenImage));

        //Display2D.invoke(IMG2VPT.invoke(chosenImage), "hehehehehheh before");
        //Dilation.invoke(IMG2VPT.invoke(chosenImage), 51, "Square")
        //OpeningByReconstruction.invoke( IMG2VPT.invoke(chosenImage), 3, "Square");
        /*
        //sFastFourierTransform.test1D();
        Image vptimg = IMG2VPT.invoke(chosenImage);
        ComplexNumber[][] imgs = FastFourierTransform.imageFFT(vptimg);
        Display2D.invoke(FastFourierTransform.magnitude(imgs, vptimg.getXDim(), vptimg.getYDim()),"fft");
        
        
        ComplexNumber[][] imgsi = FastFourierTransform.imageIFFT(imgs);
        Display2D.invoke(FastFourierTransform.magnitude(imgsi, vptimg.getXDim(), vptimg.getYDim()),"ifft");
         */
        return true;
    }

    public void Gaussian() {
        Image result;
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params);
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!params.handleGetObject("kernelSize").equals("") && !params.handleGetObject("sigma").equals("")) {
            result = Gaussian.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
            nameMap.put(result, nameMap.get(img) + "_G" + (Integer) params.handleGetObject("kernelSize") + "_" + (Double) params.handleGetObject("sigma"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        }

    }

    public void Mean() {
        Image result;
        MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters);
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!parameters.handleGetObject("kernelSize").equals("")) {
            result = Mean.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
            nameMap.put(result, nameMap.get(img) + "_M" + (Integer) parameters.handleGetObject("kernelSize"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        }
    }

    public void Sobel() {
        Image result;
        SobelBundle operators = new SobelBundle();
        SobelRequestController.showRequestBox(operators);
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("operatorChoice").equals("")) {
            result = Sobel.invoke(img, operators.handleGetObject("operatorChoice").toString());
            nameMap.put(result, nameMap.get(img) + "_S" + operators.handleGetObject("operatorChoice"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        }

    }

    public void changeImage() {
        if (!tree.getSelectionModel().isEmpty()) {
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getValue());
        }
    }

    private static void configureFileChooserOpen(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"), "/Desktop"));

        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All files (*)", "*.*");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.jpeg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");

        fileChooser.getExtensionFilters().add(allFilter);
        fileChooser.getExtensionFilters().add(jpgFilter);
        fileChooser.getExtensionFilters().add(jpegFilter);
        fileChooser.getExtensionFilters().add(pngFilter);
    }

    public void Erosion() {
        Image result;
        ErosionBundle operators = new ErosionBundle();
        ErosionRequestController.showRequestBox(operators);
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Erosion.invoke(img, (int)operators.handleGetObject("kernelSize"), (String)operators.handleGetObject("SE") );
            nameMap.put(result, nameMap.get(img) + "_S" + operators.handleGetObject("operatorChoice"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        }
    }

    /**
     * Exiting from Program.
     */
    public void closeProgram() {
        YIMP.removeTempDir();
        Platform.exit();
    }

}
