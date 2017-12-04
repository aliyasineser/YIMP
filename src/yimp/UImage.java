/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import com.sun.deploy.util.SystemUtils;
import java.io.InputStream;
import javafx.scene.image.Image;
import sun.plugin2.util.SystemUtil;

/**
 * Located image class.
 * @author aliyasineser
 */
public class UImage extends Image {

    private String url;
    private String name;

    public String getFileName() {
        return name;
    }
    
    private String getNameByUrl(){
        
        int lastIndex;
        
        if(System.getProperty("os.name").contains("Windows") )
            lastIndex = url.lastIndexOf("\\");
        else 
            lastIndex = url.lastIndexOf('/');
        
        String withExt = url.substring(lastIndex+1);
        
        lastIndex = withExt.lastIndexOf('.');
        return withExt.substring(0, lastIndex);
    }
    
    public String getURL() {
        return url;
    }

    
    public UImage(String url) {
        super(url);
        this.url = url;
        this.name = getNameByUrl();
    }

    public UImage(String url, boolean backgroundLoading) {
        super(url, backgroundLoading);
        this.url = url;
        this.name = getNameByUrl();
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        this.url = url;
        this.name = getNameByUrl();
    }

    public UImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth, backgroundLoading);
        this.url = url;
        this.name = getNameByUrl();
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
