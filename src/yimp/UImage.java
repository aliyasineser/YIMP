/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.io.InputStream;
import javafx.scene.image.Image;

/**
 *
 * @author aliyasineser
 */
public class UImage extends Image{

    private String url;
    
     public String getURL() {
        return url;
    }
    
    public UImage(String url) {
        super(url);
        this.url = url;
    }

    public UImage(String url, boolean backgroundLoading) {
        super(url, backgroundLoading);
        this.url = url;
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        this.url = url;
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth, backgroundLoading);
    }

    public UImage(InputStream is) {
        super(is);
    }

    public UImage(InputStream is, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(is, requestedWidth, requestedHeight, preserveRatio, smooth);
    }
    
    
    
}
