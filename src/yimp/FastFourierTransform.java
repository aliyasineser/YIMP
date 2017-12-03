/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

import vpt.ByteImage;
import vpt.DoubleImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;

/**
 *
 * @author aliyasineser
 */
public class FastFourierTransform {

    public static void test1D() {
        double[] input = {1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0};

        ComplexNumber[] cinput = new ComplexNumber[input.length];
        for (int i = 0; i < input.length; i++) {
            cinput[i] = new ComplexNumber(input[i], 0.0);
        }

        cinput = fft(cinput);

        System.out.println("Inputs:");
        for (double c : input) {
            System.out.print(c + " ");
        }
        System.out.println("");
        System.out.println("Results for fft:");
        for (ComplexNumber c : cinput) {
            System.out.println(c);
        }

        ComplexNumber[] coutput = ifft(cinput);

        //Math.sqrt(c.real *c.real   +    c.image * c.image)
        System.out.println("Results for ifft:");
        for (ComplexNumber c : coutput) {
            System.out.println(c.magnitude());
        }

    }

    public static ComplexNumber[][] imageFFT(Image source) {
        Image result = null;

        // image width and height must be power of 2. 
        // We can add complex zeros to handle that problem
        // find the nearest x value to satisfy that
        // 2^n >= width
        int maxX = 0;
        for (maxX = 0; Math.pow(2, maxX) < source.getXDim(); maxX++);
        // find the nearest y value to satisfy that
        // 2^n >= height
        int maxY = 0;
        for (maxY = 0; Math.pow(2, maxY) < source.getYDim(); maxY++);

        // complex matrix dimension creaiton
        int complexMaxX = (int) Math.pow(2, maxX);
        int complexMaxY = (int) Math.pow(2, maxY);
        System.err.println(complexMaxY);
        ComplexNumber[][] complexImage = new ComplexNumber[complexMaxX][complexMaxY];
        // image dimensions.
        int sourcexdim = source.getXDim();
        int sourceydim = source.getYDim();
        // if corresponding location is avaliable in the image, add it as complex number
        // otherwise, use zero padding.
        for (int i = 0; i < complexMaxX; i++) {
            for (int j = 0; j < complexMaxY; j++) {
                if (i < sourcexdim && j < sourceydim) {
                    complexImage[i][j] = new ComplexNumber(source.getXYDouble(i, j), 0);
                } else {
                    complexImage[i][j] = new ComplexNumber(0, 0);
                }
            }
        }

        // get the fft of the image.
        ComplexNumber[][] compRes = fft2D(complexImage);
        result = magnitude(compRes, sourcexdim, sourceydim);

        return compRes;
    }

    public static ComplexNumber[][] imageIFFT(ComplexNumber[][] source) {
        
        // get the fft of the image.
        ComplexNumber[][] compRes = ifft2D(source);
      

        return compRes;
    }

    /**
     * Computes 2D FFT of given
     *
     * @param source
     * @return
     */
    public static ComplexNumber[][] fft2D(ComplexNumber[][] source) {
        for (int k = 0; k < source.length; ++k) {
            source[k] = fft(source[k]);
        }
        ComplexNumber[][] appliedImg = transpose(source);
        for (int n = 0; n < appliedImg.length; ++n) {
            appliedImg[n] = fft(appliedImg[n]);
        }
        return transpose(appliedImg);

    }

    public static ComplexNumber[][] ifft2D(ComplexNumber[][] source) {
        for (int k = 0; k < source.length; k++) {
            source[k] = ifft(source[k]);
        }
        ComplexNumber[][] appliedImg = transpose(source);
        for (int n = 0; n < appliedImg.length; n++) {
            appliedImg[n] = ifft(appliedImg[n]);
        }
        return transpose(appliedImg);
    }

    private static ComplexNumber[][] transpose(ComplexNumber[][] a) {
        ComplexNumber[][] t = new ComplexNumber[a[0].length][a.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = a[j][i];
            }
        }
        return t;
    }

    private static int bitReverse(int n, int bits) {
        int reversedN = n;
        int count = bits - 1;

        n >>= 1;
        while (n > 0) {
            reversedN = (reversedN << 1) | (n & 1);
            count--;
            n >>= 1;
        }

        return ((reversedN << count) & ((1 << bits) - 1));
    }

    public static ComplexNumber[] fft(ComplexNumber[] buffer) {

        int bits = (int) (Math.log(buffer.length) / Math.log(2));
        for (int j = 1; j < buffer.length / 2; j++) {

            int swapPos = bitReverse(j, bits);
            ComplexNumber temp = buffer[j];
            buffer[j] = buffer[swapPos];
            buffer[swapPos] = temp;
        }

        for (int N = 2; N <= buffer.length; N <<= 1) {
            for (int i = 0; i < buffer.length; i += N) {
                for (int k = 0; k < N / 2; k++) {

                    int evenIndex = i + k;
                    int oddIndex = i + k + (N / 2);
                    ComplexNumber even = buffer[evenIndex];
                    ComplexNumber odd = buffer[oddIndex];

                    double term = (-2 * Math.PI * k) / (double) N;
                    ComplexNumber exp = (new ComplexNumber(Math.cos(term), Math.sin(term)).multiply(odd));

                    buffer[evenIndex] = even.addition(exp);
                    buffer[oddIndex] = even.substract(exp);
                }
            }
        }

        return buffer;
    }

    public static ComplexNumber[] ifft(ComplexNumber[] x) {
        int n = x.length;
        ComplexNumber[] y = new ComplexNumber[n];

        // take conjugate
        for (int i = 0; i < n; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        fft(y);

        // take conjugate again
        for (int i = 0; i < n; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by n
        for (int i = 0; i < n; i++) {
            y[i] = y[i].scale(1.0 / n);
        }

        return y;

    }

    public static Image magnitude(ComplexNumber[][] source, int xdim, int ydim) {
        Image img = new ByteImage(xdim, ydim);

        System.err.println("ifft [0][0]: " + source[0][0]);
        for (int i = 0; i < xdim; i++) {
            for (int j = 0; j < ydim; j++) {
                img.setXYByte(i, j, (int) source[i][j].magnitude());
            }
        }
        System.err.println("magnitude of ifft [0][0]: " + img.getXYByte(0, 0));

        return img;
    }

}
