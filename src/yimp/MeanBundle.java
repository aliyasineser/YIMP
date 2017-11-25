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
public class MeanBundle extends ResourceBundle {

    private Integer kernelSize;

    public MeanBundle() {
    }

    public MeanBundle(Integer kernelSize) {
        this.kernelSize = kernelSize;
    }

    
    
    
    public void setObject(String key, Number value){
         if (key.equals("kernelSize")) {
            kernelSize = (Integer)value;
        }
       
    }
    
    

    public Object handleGetObject(String key) {
        if (key.equals("kernelSize")) {
            return kernelSize;
        }
    
        

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("kernelSize"));
    }
}
