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

    public static Image invoke(Image source, double[][] mask) {
        Image result = source.newInstance(false);
        Image operandImage = new ByteImage(result.getXDim() + mask.length - 1, result.getYDim() + mask.length - 1);

        operandImage.fill(true);
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                operandImage.setXYByte(i + (mask.length - 1) / 2, j + (mask.length - 1) / 2, source.getXYByte(i, j));
            }
        }
        int xdim = operandImage.getXDim();
        int ydim = operandImage.getYDim();
        for (int y = 0; y < ydim - mask.length; y++) {
            for (int x = 0; x < xdim - mask.length; x++) {
                double sum = 0;
                for (int yMask = 0; yMask < mask.length; yMask++) {
                    for (int xMask = 0; xMask < mask.length; xMask++) {
                        sum += operandImage.getXYDouble(x + xMask, y + yMask) * mask[xMask][yMask];
                    }
                }

                result.setXYDouble(x, y, sum);
                sum = 0;
            }
        }
        return result;
    }

}
