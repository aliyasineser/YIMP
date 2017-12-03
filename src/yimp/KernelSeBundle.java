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
public class KernelSeBundle extends ResourceBundle {

    private Integer kernelSize;
    private String SE;

    public KernelSeBundle(int kernelSize, String SE) {
        this.kernelSize = kernelSize;
        this.SE = SE;
    }

    public KernelSeBundle() {
        this.kernelSize = 0;
        this.SE = "";
    }

    public void setObject(String key, String value) {
        if (key.equals("kernelSize")) {
            kernelSize =  Integer.valueOf(value);
        }
        if (key.equals("SE")) {
            SE =  value;
        }
    }

    public Object handleGetObject(String key) {
        if (key.equals("kernelSize")) {
            return kernelSize;
        }
        if (key.equals("SE")) {
            return SE;
        }

        return null;
    }

    public Enumeration<String> getKeys() {
        return Collections.enumeration(keySet());
    }

    // Overrides handleKeySet() so that the getKeys() implementation
    // can rely on the keySet() value.
    protected Set<String> handleKeySet() {
        return new HashSet<String>(Arrays.asList("kernelSize", "SE"));
    }
}
