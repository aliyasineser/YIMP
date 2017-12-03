/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;
import vpt.algorithms.display.Display2D;
import static yimp.Gaussian.Calculate;

/**
 *
 * @author aliyasineser
 */
public class Unsharp {
    
     public static Image invoke(Image source, int length, double sigma) {
         
        Image result = Gaussian.invoke(source, length, sigma);
        result = Substraction.invoke(source, result);
        result = Addition.invoke(source, result);
        //Display2D.invoke(result,"after");
        return result;
    }
    
    
}
