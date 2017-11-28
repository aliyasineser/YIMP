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
import java.util.Set;
import java.util.ResourceBundle;

/**
 *
 * @author aliyasineser
 */
public class SobelBundle extends ResourceBundle{
    
    private String operatorChoice;

    public SobelBundle() {
    }

    public SobelBundle(String operatorChoice) {
        this.operatorChoice = operatorChoice;
    }

    
    
    
    public void setObject(String key, String value){
         if (key.equals("operatorChoice")) {
            operatorChoice = value;
        }
       
    }
    
    

    public Object handleGetObject(String key) {
        if (key.equals("operatorChoice")) {
            return operatorChoice;
        }
    
        

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("operatorChoice"));
    }
}
