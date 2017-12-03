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
         if(source.getXDim() != dest.getXDim() || source.getYDim() != dest.getYDim())
             return null;
         
         for (int i = 0; i < xdim; i++) {
             for (int j = 0; j < ydim; j++) {
                 int temp = source.getXYByte(i, j) + dest.getXYByte(i, j);
                 if(temp >255)temp=255;
                 result.setXYByte(i, j, temp );
             }
         }
         
         return result;
      }
}
