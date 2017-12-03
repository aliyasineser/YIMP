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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    public TreeItem<UImage> root;
    public Pane pane;
    public HBox hbox;
    public VBox vbox;
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

        imageView.fitHeightProperty().bind(vbox.heightProperty());
        imageView.fitWidthProperty().bind(vbox.widthProperty());
      
        
        
        
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

        root.setExpanded(true);

        tree.getSelectionModel().select(root.getChildren().size() - 1);

       
        return true;
    }

    public void Laplacian() {
        Image result;

        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        result = Laplacian.invoke(img);
        nameMap.put(result, nameMap.get(img) + "_L");
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

    }

    public void Gaussian() {
        Image result;
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params, "Gaussian Parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!params.handleGetObject("kernelSize").equals("") && !params.handleGetObject("sigma").equals("")) {
            result = Gaussian.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
            nameMap.put(result, nameMap.get(img) + "_G" + (Integer) params.handleGetObject("kernelSize") + "_" + (Double) params.handleGetObject("sigma"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }

    }

    public void Mean() {
        Image result;
        MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters, "Mean operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!parameters.handleGetObject("kernelSize").equals("")) {
            result = Mean.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
            nameMap.put(result, nameMap.get(img) + "_M" + (Integer) parameters.handleGetObject("kernelSize"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }

    public void Median() {
        Image result;
        MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters, "Median operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!parameters.handleGetObject("kernelSize").equals("")) {
            result = Median.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
            nameMap.put(result, nameMap.get(img) + "_MD" + (Integer) parameters.handleGetObject("kernelSize"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
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
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
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
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Erosion operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Erosion.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_E" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }

    public void Dilation() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Dilation operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Dilation.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_D" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }

    public void Opening() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Opening operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Opening.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_O" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }

    public void Closing() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Closing operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Closing.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_C" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void ClosingByReconstruction() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Closing by reconstruction operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ClosingByReconstruction.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_CBR" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void OpeningByReconstruction() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Opening by reconstruction operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = OpeningByReconstruction.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_OBR" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void ReconstructionByDilation() {
        Image result;

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image operand = Load.invoke(file.getAbsolutePath());
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Reconstruction by dilation operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ReconstructionByDilation.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_RBD" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void ReconstructionByErosion() {
        Image result;

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image operand = Load.invoke(file.getAbsolutePath());
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Reconstruction by erosion operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ReconstructionByErosion.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_RBE" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void GeodesicDilation() {
        Image result;

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image operand = Load.invoke(file.getAbsolutePath());
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Geodesic dilation operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = GeodesicDilation.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_GD" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void GeodesicErosion() {
        Image result;

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image operand = Load.invoke(file.getAbsolutePath());
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Geodesic erosion operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = GeodesicErosion.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_GE" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }

    public void Unsharp() {
        Image result;
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params, "Unsharp Parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!params.handleGetObject("kernelSize").equals("") && !params.handleGetObject("sigma").equals("")) {
            result = Unsharp.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
            nameMap.put(result, nameMap.get(img) + "_U" + (Integer) params.handleGetObject("kernelSize") + "_" + (Double) params.handleGetObject("sigma"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }

    }

    public void MorphologicalGradient() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Morphological  operation parameters");
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = MorphologicalGradient.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_MG" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }

    public void Equal() {

        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image operand = Load.invoke(file.getAbsolutePath());
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        if (Equal.invoke(img, operand)) {
            ErrorBoxController.showErrorBox("Equal Operation", "Equal", "Images are identical.");
        } else {
            ErrorBoxController.showErrorBox("Equal Operation", "Not Equal", "Images are not identical.");
        }

    }

    public void Addition() {
        Image result;
        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        Image operand = Load.invoke(file.getAbsolutePath());

        if (img.getXDim() != operand.getXDim() || img.getYDim() != operand.getYDim()) {
            ErrorBoxController.showErrorBox("Addition Error", "Different image sizes.", "Source and destination images must have the same size.");
            nameMap.remove(img);
            return;
        }
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        result = Addition.invoke(img, operand);
        nameMap.put(result, nameMap.get(img) + "_A" + nameMap.get(operand));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
    }

    public void Substraction() {
        Image result;
        FileChooser fileChooser = new FileChooser();
        configureFileChooserOpen(fileChooser);
        File file = fileChooser.showOpenDialog(window);

        if (file == null) {
            return; // path secimi iptal edildi
        }

        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        Image operand = Load.invoke(file.getAbsolutePath());

        if (img.getXDim() != operand.getXDim() || img.getYDim() != operand.getYDim()) {
            ErrorBoxController.showErrorBox("Substraction Error", "Different image sizes.", "Source and destination images must have the same size.");
            nameMap.remove(img);
            return;
        }
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        result = Substraction.invoke(img, operand);
        nameMap.put(result, nameMap.get(img) + "_Sub_" + nameMap.get(operand));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
    }

    public void Inversion() {

        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        Image result = Inversion.invoke(img);
        nameMap.put(result, nameMap.get(img) + "_Inv");
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
    }

    /**
     * Power operation.
     */
    public void Power() {

        Image result;
        DoubleBundle operators = new DoubleBundle();
        DoubleRequestController.showRequestBox(operators);
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        result = Power.invoke(img, (double) operators.handleGetObject("value"));
        nameMap.put(result, nameMap.get(img) + "_Pow" + operators.handleGetObject("value"));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

    }

    public static String getNameByUrl(String url) {

        int lastIndex;

        if (System.getProperty("os.name").contains("Windows")) {
            lastIndex = url.lastIndexOf("\\");
        } else {
            lastIndex = url.lastIndexOf('/');
        }

        String withExt = url.substring(lastIndex + 1);

        lastIndex = withExt.lastIndexOf('.');
        return withExt.substring(0, lastIndex);
    }

    /**
     * Exiting from Program.
     */
    public void closeProgram() {
        YIMP.removeTempDir();
        Platform.exit();
    }

}
