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

    public static Image invoke(Image source, int kernelSize, double sigma) {
        Image result = null;
        double[][] kernel = createKernel(kernelSize, sigma);
        double[][] kernelLaplacian = {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
        double[][] kernelLaplacian1 = {{0,-1,0},{-1,4,-1},{0,-1,0}};
        double[][] kernelLaplacian2 = {{1,-2,1},{-2,4,-2},{1,-2,1}};
        
        result = CrossCorrelation.invoke(source, kernelLaplacian1);
        Display2D.invoke(result,"after");
        return result;
    }

    private static double[][] createKernel(int kernelSize, double sigma) {
        double[][] kernelCoef = new double[kernelSize][kernelSize];
        
        for (int i = -1*((kernelSize-1)/2); i <= ((kernelSize-1)/2); i++) {
            for (int j = -1*((kernelSize-1)/2); j <= ((kernelSize-1)/2); j++) {
                kernelCoef[i+((kernelSize-1)/2)][j+((kernelSize-1)/2)] = (-1.0/(Math.PI*Math.pow(sigma,4)))*
                        (1.0-(i*i+j*j)/(2*sigma*sigma))*Math.exp((-1.0*(i*i+j*j)/(2*sigma*sigma)));
            }
        }
        
        /*
        
        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                System.err.print(kernelCoef[i][j] + "     ");
                
            }
            System.err.println(""); 
                 
        }*/
        
        return kernelCoef;
    }

}
