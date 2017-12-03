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
public class OpeningByReconstruction {

    public static Image invoke(Image source, int kernelSize, String SE) {
        Image result = null;

        Image eroded = Erosion.invoke(source, kernelSize, SE);
        result = ReconstructionByDilation.invoke(eroded, source, kernelSize, SE);
        
        //Display2D.invoke(result,"after");
        
        return result;
    }

}
