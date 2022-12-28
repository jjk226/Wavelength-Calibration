package org.example;

public class PixelCalculator{

    private double[][] maxCalReferenceAndPixel;
    private double[] wavelengthsBeforeCalibration;
    private double[] pixels;
    private double[] pixelsSquared;
    private double[] pixelsCubed;
    private int pixelLength;

    public PixelCalculator(double[][] maxCalReferenceAndPixel, double[] wavelengthsBeforeCalibration) {
        this.maxCalReferenceAndPixel = maxCalReferenceAndPixel;
        this.wavelengthsBeforeCalibration = wavelengthsBeforeCalibration;

        this.pixels = new double[maxCalReferenceAndPixel.length];
        this.pixelsSquared = new double[maxCalReferenceAndPixel.length];
        this.pixelsCubed = new double[maxCalReferenceAndPixel.length];
        this.pixelLength = maxCalReferenceAndPixel.length;



        this.calculatePixels();

    }

    private void calculatePixels() {
        for (int i = 0; i < maxCalReferenceAndPixel.length; i++) {
            pixels[i] = maxCalReferenceAndPixel[i][1];
            pixelsSquared[i] = Math.pow(maxCalReferenceAndPixel[i][1], 2);
            pixelsCubed[i] = Math.pow(maxCalReferenceAndPixel[i][1], 3);
        }

        System.out.println("pixels" + "   " + "pixels^2" + "   " + "pixels^3");
        for (int i = 0; i < pixels.length; i++) {
            System.out.println(pixels[i] + "     " + pixelsSquared[i] + "    " + pixelsCubed[i]);
        }
    }

    public double[] getPixels() {
        return pixels;
    }


    public double[] getPixelsSquared() {
        return pixelsSquared;
    }


    public double[] getPixelsCubed() {
        return pixelsCubed;
    }

    public int getPixelLength() {
        return this.pixelLength;
    }

    public double[] getWavelengthsBeforeCalibration() {
        return this.wavelengthsBeforeCalibration;
    }


}
