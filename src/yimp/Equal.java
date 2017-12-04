/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;

/**
 * Equal check class
 * @author aliyasineser
 */
public class Equal {
    /**
     * Checks that two images are identical to each other.
     * @param source
     * @param dest
     * @return 
     */
    public static boolean invoke(Image source, Image dest) {

        if (source.getXDim() != dest.getXDim() || source.getYDim() != dest.getYDim() || source.getCDim() != dest.getCDim()) {
            return false;
        }

        int xdim = source.getXDim();
        int ydim = source.getYDim();
        for (int i = 0; i < xdim; i++) {
            for (int j = 0; j < ydim; j++) {
                for (int k = 0; k < source.getCDim(); k++) {
                    if (source.getXYCByte(i, j,k) != dest.getXYCByte(i, j,k)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }

}
