/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import java.util.ArrayList;
import java.util.List;
import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;

/**
 *
 * @author aliyasineser
 */
public class Median {

    public static Image invoke(Image source, int kernelSize) {

        int[] list = new int[kernelSize * kernelSize];
        Image result = source.newInstance(false);
        Image operandImage = new ByteImage(result.getXDim() + kernelSize - 1, result.getYDim() + kernelSize - 1, result.getCDim());

        operandImage.fill(true);
        for (int i = 0; i < result.getXDim(); i++) {
            for (int j = 0; j < result.getYDim(); j++) {
                for (int k = 0; k < result.getCDim(); k++) {
                    operandImage.setXYCByte(i + (kernelSize - 1) / 2, j + (kernelSize - 1) / 2,k, source.getXYCByte(i, j,k));
                }

            }
        }
        int xdim = operandImage.getXDim();
        int ydim = operandImage.getYDim();
        for (int x = 0; x < xdim - kernelSize; x++) {
            for (int y = 0; y < ydim - kernelSize; y++) {
                for (int i = 0; i < operandImage.getCDim(); i++) {
                    for (int xMask = 0; xMask < kernelSize; xMask++) {
                        for (int yMask = 0; yMask < kernelSize; yMask++) {
                            list[xMask * kernelSize + yMask] = operandImage.getXYCByte(x + xMask, y + yMask,i);
                        }
                    }
                    bubbleSort(list);

                    result.setXYCByte(x, y, i,list[(kernelSize * kernelSize - 1) / 2]);

                }

            }
        }
        //Display2D.invoke(result, "after");
        return result;
    }

    // An optimized version of Bubble Sort
    private static void bubbleSort(int arr[]) {
        int i, j;
        boolean swapped;
        for (i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // IF no two elements were swapped by inner loop, then break
            if (swapped == false) {
                break;
            }
        }

    }

}
