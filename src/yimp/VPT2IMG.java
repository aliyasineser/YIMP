/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.algorithms.io.Save;


/**
 *
 * @author aliyasineser
 */
public class VPT2IMG {
    
    public static UImage invoke(vpt.Image source, String name) {
        UImage result = null;
        String fullPath;

        if (System.getProperty("os.name").contains("Windows")) {
            fullPath = System.getProperty("user.dir") + "\\YimpTemp\\" + name + ".png";
        } else {
            fullPath = System.getProperty("user.dir") + "/YimpTemp/" + name + ".png";
        }

        System.out.println(fullPath);
        
        Save.invoke(source, fullPath);
        result = new UImage("file:" + fullPath);
        return result;
    }

}
