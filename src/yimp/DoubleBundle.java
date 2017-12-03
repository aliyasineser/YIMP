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
 * @author aliyasineser
 */
public class DoubleBundle extends ResourceBundle{
    
    
    private Double value;

    public DoubleBundle() {
    }

    public DoubleBundle(Double value) {
        this.value = value;
    }

    
    
    
    public void setObject(String key, Double value){
         if (key.equals("value")) {
            this.value = value;
        }
       
    }
    
    

    public Double handleGetObject(String key) {
        if (key.equals("value")) {
            return value;
        }
    
        

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("value"));
    }
}
