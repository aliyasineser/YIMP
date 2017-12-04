/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yimp;

/**
 * Class for fft. Complex numbers
 * @author aliyasineser
 */
class ComplexNumber {

    public final double real;
    public final double image;

    public ComplexNumber() {
        this(0, 0);
    }

    public ComplexNumber(double realVal, double imageVal) {
        this.real = realVal;
        this.image = imageVal;
    }

    public ComplexNumber addition(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.image + other.image);
    }

    public ComplexNumber substract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.image - other.image);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(this.real * other.real - this.image * other.image,
                this.real * other.image + this.image * other.real);
    }

    ComplexNumber conjugate() {
        return new ComplexNumber(this.real, -1.0 * this.image);
    }

    public ComplexNumber scale(double alpha) {
        return new ComplexNumber(alpha * real, alpha * image);
    }
    
    public double magnitude() {
        return Math.hypot(real, image);
    }

    public ComplexNumber reverse(){
        return new ComplexNumber(image, real);
    }
    
    // return a new object whose value is (this * alpha)
    ComplexNumber multiply(double alpha) {
        return new ComplexNumber(alpha * this.real, alpha * this.image);
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", real, image);
    }
}
