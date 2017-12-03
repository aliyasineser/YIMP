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
    /**
     * Applies sobel vertically/horizontally/both according to the given parameter.
     * @param source source Image.
     * @param operator selected Sobel operator
     * @return 
     */
    public static Image invoke(Image source, String operator){
        
        Image result = source.newInstance(false);
        double[][] kernelRight= {{-1,0,1},{-2,0,2},{-1,0,1}}; // right filter
        double[][] kernelLeft= {{1,0,-1},{2,0,-2},{1,0,-1}}; // left filter
        double[][] kernelTop = {{1,2,1},{0,0,0},{-1,-2,-1}}; // top filter
        double[][] kernelBottom = {{-1,-2,-1},{0,0,0},{1,2,1}}; // bottom filter
        switch(operator){
            case("Top Sobel(Vertical)"):
                result =  CrossCorrelation.invoke(source, kernelTop);
                break;
            case("Right Sobel(Horizontal)"):
                result =  CrossCorrelation.invoke(source, kernelRight);
                break;
            case("Bottom Sobel"):
                result =  CrossCorrelation.invoke(source, kernelBottom);
                break;
            case("Left Sobel"):
                result =  CrossCorrelation.invoke(source, kernelLeft);
                break;
            case("Gradient Magnitude"):
                // Magntude can be compute with different formula
                // This formula is computationally fast.
                Image vertical = CrossCorrelation.invoke(source, kernelTop);
                Image horizontal = CrossCorrelation.invoke(source, kernelRight);
                try {
                    result = Addition.invoke(vertical, horizontal);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
        
        return result;
    }
    
}
