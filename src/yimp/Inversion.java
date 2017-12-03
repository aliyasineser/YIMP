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
public class Inversion {
 
     public static Image invoke(Image source) {
         Image result = source.newInstance(false);
         int xdim = source.getXDim();
         int ydim = source.getYDim();
         for (int i = 0; i < xdim; i++) {
             for (int j = 0; j < ydim; j++) {
                 for (int k = 0; k < source.getCDim(); k++) {
                     result.setXYCByte(i, j, k,255 - source.getXYCByte(i, j,k) );
                 }
                 
             }
         }
         
         return result;
      }
    
    
}
