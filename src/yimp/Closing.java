/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;
import vpt.algorithms.display.Display2D;

/**
 * Closing operator
 * @author aliyasineser
 */
public class Closing {
    
    /**
     * Applies closing operator on an image.
     * @param source
     * @param kernelSize
     * @param SE structural element
     * @return 
     */
    public static Image invoke(Image source, int kernelSize, String SE){
        Image result=null;
        
        Image dilated = Dilation.invoke(source, kernelSize, SE);
        result = Erosion.invoke(dilated, kernelSize, SE);
        
        //Display2D.invoke(result,"after");
        
        return result;
    }
    
    
}
