/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;

/**
 *
 * @author aliyasineser
 */
public class Mean {
    /**
     * Applies mean filter.
     * @param source
     * @param kernelSize
     * @return 
     */
    public static Image invoke(Image source, int kernelSize) {
        double[][] kernelCoef = new double[kernelSize][];
        for (int i = 0; i < kernelSize; i++) {
            kernelCoef[i] = new double[kernelSize];
        }

        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                kernelCoef[i][j] = 1.0 / (kernelCoef.length * kernelCoef.length);
            }
        }
        
        Image result = CrossCorrelation.invoke(source, kernelCoef);
        
        return result;
    }

}
