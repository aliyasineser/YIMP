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
public class GeodesicErosion {

    public static Image invoke(Image source, Image mask, int kernelSize, String SE) {
        Image result = Erosion.invoke(source, kernelSize, SE);
        int xdim = source.getXDim();
        int ydim = source.getYDim();
        for (int i = 0; i < xdim; i++) {
            for (int j = 0; j < ydim; j++) {

                if (mask.getXYByte(i, j) > result.getXYByte(i, j)) {
                    result.setXYByte(i, j, mask.getXYByte(i, j));
                }
            }
        }
        //Display2D.invoke(result, "geodil");
        return result;
    }

}
