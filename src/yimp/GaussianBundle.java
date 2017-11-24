/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 * @author aliya
 */
public class GaussianBundle extends ResourceBundle {

    private Integer kernelSize;
    private Double sigma;

    public GaussianBundle(int kernelSize, Double sigma) {
        this.kernelSize = kernelSize;
        this.sigma = sigma;
    }

    public GaussianBundle() {
        this.kernelSize = 0;
        this.sigma = new Double(0);
    }

    
    
    public void setObject(String key, Number value){
         if (key.equals("kernelSize")) {
            kernelSize = (Integer)value;
        }
        if (key.equals("sigma")) {
            sigma = (Double)value;
        }
    }
    
    

    public Object handleGetObject(String key) {
        if (key.equals("kernelSize")) {
            return kernelSize;
        }
        if (key.equals("sigma")) {
            return sigma;
        }
        

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("kernelSize", "sigma"));
    }
}
