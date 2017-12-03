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
public class Laplacian {

    public static Image invoke(Image source) {
        Image result = null;
        
        double[][] kernelLaplacian = {{0, 1,0},
                                      {1,-4,1},
                                      {0, 1,0}};
        
        
        result = CrossCorrelation.invoke(source, kernelLaplacian);
        return result;
    }

}
