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

    public static Image invoke(Image source, int kernelSize) {
        Image result = source.newInstance(false);
        double[][] kernelCoef = new double[kernelSize][];
        for (int i = 0; i < kernelSize; i++) {
            kernelCoef[i] = new double[kernelSize];
        }

        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                kernelCoef[i][j] = 1.0 / (kernelCoef.length * kernelCoef.length);
            }
        }

        Image operandImage = new ByteImage(result.getXDim() + kernelCoef.length - 1, result.getYDim() + kernelCoef.length - 1);

        operandImage.fill(true);
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                operandImage.setXYByte(i + (kernelCoef.length - 1) / 2, j + (kernelCoef.length - 1) / 2, source.getXYByte(i, j));
            }
        }

        for (int y = 0; y < operandImage.getYDim() - kernelCoef.length; y++) {
            for (int x = 0; x < operandImage.getXDim() - kernelCoef.length; x++) {
                double sum = 0;
                for (int yMask = 0; yMask < kernelCoef.length; yMask++) {
                    for (int xMask = 0; xMask < kernelCoef.length; xMask++) {
                        sum += operandImage.getXYDouble(x + xMask, y + yMask) * kernelCoef[xMask][yMask];
                    }
                }

                result.setXYDouble(x, y, sum);
                sum = 0;
            }
        }
        Display2D.invoke(result, "after");
        return result;
    }

}
