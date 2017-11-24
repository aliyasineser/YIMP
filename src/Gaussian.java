
import vpt.Image;

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

    /*
    int W = 5;
    double kernel[W][W];
    double mean = W/2;
    double sum = 0.0; // For accumulating the kernel values
    for (int x = 0; x < W; ++x) 
        for (int y = 0; y < W; ++y) {
            kernel[x][y] = exp( -0.5 * (pow((x-mean)/sigma, 2.0) + pow((y-mean)/sigma,2.0)) )
                             / (2 * M_PI * sigma * sigma);

            // Accumulate the kernel values
            sum += kernel[x][y];
        }

    // Normalize the kernel
    for (int x = 0; x < W; ++x) 
        for (int y = 0; y < W; ++y)
            kernel[x][y] /= sum;
    */

    public static Image invoke(Image source, int length, double sigma) {
        Image result = source.newInstance(true);

        return result;
    }

}
