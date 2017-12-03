/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

/**
 *
 * @author aliyasineser
 */
public class StructuralElement {
    
    public static boolean[][] createKernel(int kernelSize, String SE){
        boolean[][] kernel = new boolean[kernelSize][kernelSize];
        
        switch(SE){
            case("Plus"):
                for (int i = 0; i < kernelSize; i++) {
                    kernel[i][(kernelSize-1)/2] = true;
                    kernel[(kernelSize-1)/2][i] = true;
                }
                break;
            case("Square"):
                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        kernel[i][j]=true;
                    }
                }
                break;
            case("Fat Plus"):
                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        kernel[i][j]=true;
                    }
                }
                kernel[0][kernelSize-1]=false;
                kernel[kernelSize-1][0]=false;
                kernel[kernelSize-1][kernelSize-1]=false;
                kernel[0][0]=false;
                break;
            default:break;
        }
        
        /* Kernel Debug
        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                System.err.print(kernel[i][j]+" ");
            }
            System.err.println("");
        }
        */
        return kernel;
    }
    
}
