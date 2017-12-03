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
public class Substraction {
    
     public static Image invoke(Image source, Image dest) {
         Image result = source.newInstance(false);
         
         // Can be exception also, todo.
         if(source.getXDim() != dest.getXDim() || source.getYDim() != dest.getYDim())
             return null;
         
         for (int i = 0; i < source.getXDim(); i++) {
             for (int j = 0; j < source.getYDim(); j++) {
                 int temp = source.getXYByte(i, j) - dest.getXYByte(i, j);
                 if(temp < 0 )temp=0;
                 result.setXYByte(i, j, temp );
             }
         }
         
         return result;
      }
    
    
}
