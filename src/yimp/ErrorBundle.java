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
public class ErrorBundle extends ResourceBundle {

    private String title;
    private String message;

    public ErrorBundle(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void setObject(String key, String value){
         if (key.equals("title")) {
            title = value;
        }
        if (key.equals("message")) {
            message = value;
        }
    }
    
    public Object handleGetObject(String key) {
        if (key.equals("title")) {
            return title;
        }
        if (key.equals("message")) {
            return message;
        }
        

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("title", "message"));
    }
}
