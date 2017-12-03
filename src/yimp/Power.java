/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;
import vpt.algorithms.display.Display2D;

/**
 *
 * @author aliyasineser
 */
public class Power {
 
    public static Image invoke(Image sourceImage,double power){
        Image result = sourceImage.newInstance(false);
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < sourceImage.getXDim(); i++) {
            for (int j = 0; j < sourceImage.getYDim(); j++) {
                double temp = sourceImage.getXYDouble(i, j);
                if(max < temp) max = temp;
                if(temp < min) min = temp;
                result.setXYDouble(i, j, Math.pow(temp, power));
            }
        }
        
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                double temp = result.getXYDouble(i, j);
                result.setXYDouble(i, j, ((temp - min) / (max-min)) );
            }
        }
        
        Display2D.invoke(result);
        
        return result;
    }
    
    
}
