package yimp;


import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aliyasineser
 */
public class Gaussian {

    public static double[][] Calculate(int length, double weight) {
        double[][] Kernel = new double[length][];
        for (int i = 0; i < length; i++) {
            Kernel[i] = new double[length];
        }
        double sumTotal = 0;

        int kernelRadius = length / 2;
        double distance = 0;

        double calculatedEuler = 1.0
                / (2.0 * Math.PI * Math.pow(weight, 2));

        for (int filterY = -kernelRadius;
                filterY <= kernelRadius; filterY++) {
            for (int filterX = -kernelRadius;
                    filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX)
                        + (filterY * filterY))
                        / (2 * (weight * weight));

                Kernel[filterY + kernelRadius][filterX + kernelRadius]
                        = calculatedEuler * Math.exp(-distance);

                sumTotal += Kernel[filterY + kernelRadius][filterX + kernelRadius];
            }
        }

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                Kernel[y][x] = Kernel[y][x]
                        * (1.0 / sumTotal);
            }
        }

        return Kernel;
    }

  
    public static Image invoke(Image source, int length, double sigma) {
        
        double[][] kernelCoef = Calculate(length, sigma);
        
        Image result = CrossCorrelation.invoke(source, kernelCoef);
        
        Display2D.invoke(result,"after");
        return result;
    }

}
