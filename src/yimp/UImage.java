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
public class UImage extends Image {

    private String url;
    private String name;

    
    private String getNameBytUrl(){
        int lastIndex = url.lastIndexOf('/');
        return url.substring(lastIndex+1);
    }
    
    public String getURL() {
        return url;
    }

    public UImage() {
        super("file:src/Assets/root");
    }
    
    public UImage(String url) {
        super(url);
        this.url = url;
        this.name = getNameBytUrl();
    }

    public UImage(String url, boolean backgroundLoading) {
        super(url, backgroundLoading);
        this.url = url;
        this.name = getNameBytUrl();
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        this.url = url;
        this.name = getNameBytUrl();
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth, backgroundLoading);
        this.url = url;
        this.name = getNameBytUrl();
    }

    public UImage(InputStream is) {
        super(is);
    }

    public UImage(InputStream is, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(is, requestedWidth, requestedHeight, preserveRatio, smooth);
    }

    @Override
    public String toString() {
        return name;
    }

}
