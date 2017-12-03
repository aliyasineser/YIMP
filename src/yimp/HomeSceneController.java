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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vpt.Image;
import vpt.algorithms.io.Load;

/**
 * Every function in the controller are the same in the base. 
 * Some of them needs an image, and they take additional image.
 * Most of them shows dialog boxes and takes parameters they need.
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
        
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                rootImg = new UImage(this.getClass().getResourceAsStream("/Assets/root.png"));
            } else {
                rootImg = new UImage(this.getClass().getResourceAsStream("/Assets/root.png"));
            }
        } catch (NullPointerException e) {
            
        }
        
        
        

        root = new TreeItem<>(rootImg);
        tree.setRoot(root);
        nameMap = new HashMap<>();

        imageView.fitHeightProperty().bind(vbox.heightProperty());
        imageView.fitWidthProperty().bind(vbox.widthProperty());
      
        
        
        
    }
    /**
     * Opens the file. Allowed formats are jpg, jpeg and png. Otherwise there may be a problem.
     * @return
     * @throws Exception 
     */
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
    /**
     * Laplacian operation.
     */
    public void Laplacian() {
        Image result;

        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        result = Laplacian.invoke(img);
        nameMap.put(result, nameMap.get(img) + "_L");
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

    }
    /**
     * Gaussian operation.
     */
    public void Gaussian() {
        Image result;
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params, "Gaussian Parameters",this.getClass().getResource("GaussianRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!params.handleGetObject("kernelSize").equals("") && !params.handleGetObject("sigma").equals("")) {
            result = Gaussian.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
            nameMap.put(result, nameMap.get(img) + "_G" + (Integer) params.handleGetObject("kernelSize") + "_" + (Double) params.handleGetObject("sigma"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }

    }
    /**
     * Mean operation.
     */
    public void Mean() {
        Image result;
        MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters, "Mean operation parameters",this.getClass().getResource("MeanRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!parameters.handleGetObject("kernelSize").equals("")) {
            result = Mean.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
            nameMap.put(result, nameMap.get(img) + "_M" + (Integer) parameters.handleGetObject("kernelSize"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Median operation.
     */
    public void Median() {
        Image result;
        MeanBundle parameters = new MeanBundle();
        MeanRequestController.showRequestBox(parameters, "Median operation parameters",this.getClass().getResource("MeanRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!parameters.handleGetObject("kernelSize").equals("")) {
            result = Median.invoke(img, (Integer) parameters.handleGetObject("kernelSize"));
            nameMap.put(result, nameMap.get(img) + "_MD" + (Integer) parameters.handleGetObject("kernelSize"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Sobel operation.
     */
    public void Sobel() {
        Image result;
        SobelBundle operators = new SobelBundle();
        SobelRequestController.showRequestBox(operators,this.getClass().getResource("SobelRequest.fxml")); // request
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue()); // get the selected image
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("operatorChoice").equals("")) { // Control
            result = Sobel.invoke(img, operators.handleGetObject("operatorChoice").toString());
            nameMap.put(result, nameMap.get(img) + "_S" + operators.handleGetObject("operatorChoice"));
            // save the images and made it selected.
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }

    }
    /**
     * In every treeview action, image gets change according to position of the mouse.
     */
    public void changeImage() {
        if (!tree.getSelectionModel().isEmpty()) {
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getValue());
        }
    }
    /**
     * Open file function operations. Formats can be arranged in here.
     * @param fileChooser 
     */
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
    /**
     * Erosion operation.
     */
    public void Erosion() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Erosion operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Erosion.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_E" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Dilation operation
     */
    public void Dilation() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Dilation operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Dilation.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_D" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Opening.
     */
    public void Opening() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Opening operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Opening.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_O" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Closing operation.
     */
    public void Closing() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Closing operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = Closing.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_C" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Closing by reconstruction operation.
     */
    public void ClosingByReconstruction() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Closing by reconstruction operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ClosingByReconstruction.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_CBR" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Opening by reconstruction operation.
     */
    public void OpeningByReconstruction() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Opening by reconstruction operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = OpeningByReconstruction.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_OBR" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Reconstruction by dilation operation.
     */
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
        KernelSeRequestController.showRequestBox(operators, "Reconstruction by dilation operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ReconstructionByDilation.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_RBD" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Reconstruction by erosion operation.
     */
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
        KernelSeRequestController.showRequestBox(operators, "Reconstruction by erosion operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = ReconstructionByErosion.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_RBE" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Geodesic Dilation operation.
     */
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
        KernelSeRequestController.showRequestBox(operators, "Geodesic dilation operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = GeodesicDilation.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_GD" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Geodesic Erosion operation.
     */
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
        KernelSeRequestController.showRequestBox(operators, "Geodesic erosion operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = GeodesicErosion.invoke(img, operand, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_GE" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

        }
    }
    /**
     * Unsharp Masking operation.
     */
    public void Unsharp() {
        Image result;
        GaussianBundle params = new GaussianBundle();
        GaussianRequestController.showRequestBox(params, "Unsharp Parameters",this.getClass().getResource("GaussianRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!params.handleGetObject("kernelSize").equals("") && !params.handleGetObject("sigma").equals("")) {
            result = Unsharp.invoke(img, (Integer) params.handleGetObject("kernelSize"), (Double) params.handleGetObject("sigma"));
            nameMap.put(result, nameMap.get(img) + "_U" + (Integer) params.handleGetObject("kernelSize") + "_" + (Double) params.handleGetObject("sigma"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }

    }
    /**
     * Morphological Gradient operation.
     */
    public void MorphologicalGradient() {
        Image result;
        KernelSeBundle operators = new KernelSeBundle();
        KernelSeRequestController.showRequestBox(operators, "Morphological  operation parameters",this.getClass().getResource("KernelSeRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());
        if (!operators.handleGetObject("SE").equals("")) {
            result = MorphologicalGradient.invoke(img, (int) operators.handleGetObject("kernelSize"), (String) operators.handleGetObject("SE"));
            nameMap.put(result, nameMap.get(img) + "_MG" + operators.handleGetObject("kernelSize") + "_" + operators.handleGetObject("SE"));
            tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
            imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
        }
    }
    /**
     * Equal check operation.
     */
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
            ErrorBoxController.showErrorBox("Equal Operation", "Equal", "Images are identical.", this.getClass().getResource("ErrorBox.fxml"));
        } else {
            ErrorBoxController.showErrorBox("Equal Operation", "Not Equal", "Images are not identical.", this.getClass().getResource("ErrorBox.fxml"));
        }

    }
    /**
     * Addition operation.
     */
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

        if (img.getXDim() != operand.getXDim() || img.getYDim() != operand.getYDim() || img.getCDim() != operand.getCDim()) {
            ErrorBoxController.showErrorBox("Addition Error", "Different image sizes.", 
                    "Source and destination images must have the same size.", this.getClass().getResource("ErrorBox.fxml"));
            nameMap.remove(img);
            return;
        }
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        result = Addition.invoke(img, operand);
        nameMap.put(result, nameMap.get(img) + "_A" + nameMap.get(operand));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
    }
    /**
     * Substraction operation.
     */
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

        if (img.getXDim() != operand.getXDim() || img.getYDim() != operand.getYDim() || img.getCDim() != operand.getCDim()) {
            ErrorBoxController.showErrorBox("Substraction Error", "Different image sizes.",
                    "Source and destination images must have the same size.", this.getClass().getResource("ErrorBox.fxml"));
            nameMap.remove(img);
            return;
        }
        nameMap.put(operand, getNameByUrl(file.getAbsolutePath()));

        result = Substraction.invoke(img, operand);
        nameMap.put(result, nameMap.get(img) + "_Sub_" + nameMap.get(operand));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());
    }
    /**
     * Inversion operation.
     */
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
        DoubleRequestController.showRequestBox(operators,this.getClass().getResource("DoubleRequest.fxml"));
        Image img = IMG2VPT.invoke(tree.getSelectionModel().getSelectedItem().getValue());
        nameMap.put(img, tree.getSelectionModel().getSelectedItem().getValue().getFileName());

        result = Power.invoke(img, (double) operators.handleGetObject("value"));
        nameMap.put(result, nameMap.get(img) + "_Pow" + operators.handleGetObject("value"));
        tree.getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<>(VPT2IMG.invoke(result, nameMap.get(result))));
        imageView.setImage(tree.getSelectionModel().getSelectedItem().getChildren().get(tree.getSelectionModel().getSelectedItem().getChildren().size() - 1).getValue());

    }
    /**
     * get the image name in the file url.
     * @param url
     * @return 
     */
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

    public void about(){
        AboutBoxController.showBox("About", "Yeser Image Processing Tool", 
                "By Ali Yasin Eser, 2017\nOpen file: File -> Open file\nUse filters:"+
                        " Choose an Image -> Right click it or use Filters section to choose the filter -> See the results.",
                this.getClass().getResource("AboutBox.fxml"));
    }
    
    /**
     * Exiting from Program.
     */
    public void closeProgram() {
        YIMP.removeTempDir();
        Platform.exit();
    }

}
