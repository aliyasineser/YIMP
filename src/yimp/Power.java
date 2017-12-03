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

    public static Image invoke(Image sourceImage, double power) {
        Image result = sourceImage.newInstance(false);

        double max[] = new double[result.getCDim()];
        double min[] = new double[result.getCDim()];

        for (int i = 0; i < result.getCDim(); i++) {
            max[i] = Double.MIN_VALUE;
            min[i] = Double.MAX_VALUE;
        }

        for (int i = 0; i < sourceImage.getXDim(); i++) {
            for (int j = 0; j < sourceImage.getYDim(); j++) {
                for (int k = 0; k < result.getCDim(); k++) {
                    double temp = sourceImage.getXYCDouble(i, j, k);
                    if (max[k] < temp) {
                        max[k] = temp;
                    }
                    if (temp < min[k]) {
                        min[k] = temp;
                    }
                    result.setXYCDouble(i, j, k, Math.pow(temp, power));

                }

            }
        }

        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                for (int k = 0; k < result.getCDim(); k++) {
                    double temp = result.getXYCDouble(i, j,k);
                    result.setXYCDouble(i, j, k, ((temp - min[k]) / (max[k] - min[k])));
                }
            }
        }

        //Display2D.invoke(result);
        return result;
    }

}
