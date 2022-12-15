package org.example;

public class Wavelength {

    private int pixel;
    private float wavelength;
    private float calibrationReference;

    public Wavelength(int pixel, float wavelength, float calibrationReference) {
        this.pixel = pixel;
        this.wavelength = wavelength;
        this.calibrationReference = calibrationReference;
    }

    public int getPixel() {
        return pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public float getWavelength() {
        return wavelength;
    }

    public void setWavelength(float wavelength) {
        this.wavelength = wavelength;
    }

    public float getCalibrationReference() {
        return calibrationReference;
    }

    public void setCalibrationReference(float calibrationReference) {
        this.calibrationReference = calibrationReference;
    }

    @Override
    public String toString() {
        return "Wavelength{" +
                "pixel=" + pixel +
                ", wavelength=" + wavelength +
                ", calibrationReference=" + calibrationReference +
                '}';
    }
}
