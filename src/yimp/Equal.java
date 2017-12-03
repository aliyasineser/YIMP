/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.Image;

/**
 *
 * @author aliyasineser
 */
public class Equal {
    
    public static boolean invoke(Image source, Image dest){
        
        if(source.getXDim() != dest.getXDim() || source.getYDim() != dest.getYDim())
            return false;
    
        int xdim = source.getXDim();
        int ydim = source.getYDim();
        for (int i = 0; i < xdim; i++) {
            for (int j = 0; j < ydim; j++) {
                if(source.getXYByte(i, j) != dest.getXYByte(i, j))
                    return false;
            }
        }
        
        
        return true;
    }
    
}
