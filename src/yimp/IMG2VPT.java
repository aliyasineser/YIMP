/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.DoubleImage;
import vpt.Image;
import vpt.algorithms.io.Load;

/**
 *
 * @author aliyasineser
 */
public class IMG2VPT {

    public static Image invoke(UImage source) {
        return Load.invoke(source.getURL().substring(source.getURL().indexOf(':')+1) );
    }

}
