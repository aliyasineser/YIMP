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
public class CrossCorrelation {
    /**
     * Applies Cross correlation on an image.
     * @param source
     * @param mask
     * @return 
     */
    public static Image invoke(Image source, double[][] mask) {
        Image result = source.newInstance(false);
        Image operandImage = new ByteImage(result.getXDim() + mask.length - 1, result.getYDim() + mask.length - 1, result.getCDim());

        operandImage.fill(true);
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                for (int k = 0; k < result.getCDim(); k++) {
                    operandImage.setXYCByte(i + (mask.length - 1) / 2, j + (mask.length - 1) / 2, k,source.getXYCByte(i, j,k));
                }
            }
        }
        int xdim = operandImage.getXDim();
        int ydim = operandImage.getYDim();
        for (int y = 0; y < ydim - mask.length; y++) {
            for (int x = 0; x < xdim - mask.length; x++) {
                for (int i = 0; i < result.getCDim(); i++) {

                    double sum = 0;
                    for (int yMask = 0; yMask < mask.length; yMask++) {
                        for (int xMask = 0; xMask < mask.length; xMask++) {

                            sum += operandImage.getXYCDouble(x + xMask, y + yMask,i) * mask[xMask][yMask];

                        }
                    }

                    result.setXYCDouble(x, y,i, sum);
                    sum = 0;
                }
            }
        }
        return result;
    }

}
