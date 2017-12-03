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
public class Dilation {

    public static Image invoke(Image source, int kernelSize, String SE) {
        Image result = source.newInstance(false);

        boolean[][] kernel = StructuralElement.createKernel(kernelSize, SE);

        Image operandImage = new ByteImage(result.getXDim() + kernelSize - 1, result.getYDim() + kernelSize - 1);

        operandImage.fill(false);
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                operandImage.setXYByte(i + (kernelSize - 1) / 2, j + (kernelSize - 1) / 2, source.getXYByte(i, j));
            }
        }
        int xdim = operandImage.getXDim();
        int ydim = operandImage.getYDim();
        for (int x = 0; x < xdim - kernelSize; x++) {
            for (int y = 0; y < ydim - kernelSize; y++) {
                int max = Integer.MIN_VALUE;
                for (int xMask = 0; xMask < kernelSize; xMask++) {
                    for (int yMask = 0; yMask < kernelSize; yMask++) {
                        if (kernel[xMask][yMask] == true && max < operandImage.getXYByte(x + xMask, y + yMask)) {
                            max = operandImage.getXYByte(x + xMask, y + yMask);
                        }
                    }
                }

                result.setXYByte(x, y, max);

            }
        }

        //Display2D.invoke(result, "dil");

        return result;
    }

}
