/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author aliyasineser
 */
public class YIMP extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
        
        
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        Scene scene = new Scene(root);
        stage.setOnCloseRequest((event) -> {
            removeTempDir();
        });
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dir = new File("./YimpTemp");
        dir.mkdir();
        launch(args);
        
        
    }
    
    public static void removeTempDir(){
        File dir = new File("./YimpTemp");
        File[] contents =  dir.listFiles();
        for (File content : contents) {
            content.delete();
        }
        dir.delete();
    }
    
    
}
