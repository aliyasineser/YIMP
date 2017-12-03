/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;
import static yimp.Gaussian.Calculate;

/**
 *
 * @author aliyasineser
 */
public class Addition {

    public static Image invoke(Image source, Image dest) {
        Image result = source.newInstance(false);

        int xdim = source.getXDim();
        int ydim = source.getYDim();

        // Can be exception also, todo.
        if (source.getXDim() != dest.getXDim() || source.getYDim() != dest.getYDim() || source.getCDim() != dest.getCDim()) {
            return null;
        }

        for (int i = 0; i < xdim; i++) {
            for (int j = 0; j < ydim; j++) {
                for (int k = 0; k < source.getCDim(); k++) {
                    int temp = source.getXYCByte(i, j,k) + dest.getXYCByte(i, j,k);
                    if (temp > 255) {
                        temp = 255;
                    }

                    result.setXYCByte(i, j,k, temp);
                }

            }
        }

        return result;
    }
}
