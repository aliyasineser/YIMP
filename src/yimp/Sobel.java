/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.arithmetic.Addition;
import vpt.algorithms.display.Display2D;

/**
 *
 * @author aliyasineser
 */
public class Sobel {
    
    public static Image invoke(Image source, String operator){
        Image result = source.newInstance(false);
        double[][] kernelhorizontal= {{-1,0,1},{-2,0,2},{-1,0,1}};
        double[][] kernelvertical = {{1,2,1},{0,0,0},{-1,-2,-1}};
        switch(operator){
            case("Vertical"):
                result =  CrossCorrelation.invoke(source, kernelvertical);
                break;
            case("Horizontal"):
                result =  CrossCorrelation.invoke(source, kernelhorizontal);
                break;
            case("Gradient Magnitude"):
                Image vertical = CrossCorrelation.invoke(source, kernelvertical);
                Image horizontal = CrossCorrelation.invoke(source, kernelhorizontal);
                try {
                    result = Addition.invoke(vertical, horizontal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                break;
            default:
                break;

        }
        Display2D.invoke(result, "after");
        
        return result;
    }
    
}
