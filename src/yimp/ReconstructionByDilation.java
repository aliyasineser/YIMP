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
public class ReconstructionByDilation {

    public static Image invoke(Image source, Image mask, int kernelSize, String SE) {
        Image result=null;
        
        Image temp;
        Image current = source.newInstance(true);
        do{
            temp = current.newInstance(true);
            current = GeodesicDilation.invoke(temp, mask, kernelSize, SE);            
        }while(!Equal.invoke(current, temp));
        result = current;
        Display2D.invoke(result, "geodil");
        return result;
    }

}
