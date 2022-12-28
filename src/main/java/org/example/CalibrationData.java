package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalibrationData {
    //range
    private double range = 2.7110;
    // declare true wavelengths
    private final double[] trueWavelengths = {253.652, 296.7280, 302.1500, 313.1550, 365.0150, 404.6560, 435.8330, 546.0740, 696.5430, 738.3930, 750.3870, 763.5110, 772.3760};
    private double[][] maxCalReferenceAndPixel;
    private double[] wavelengthsBeforeCalibration;
    ArrayList<Wavelength> wavelengths;
    public CalibrationData(ArrayList<Wavelength> wavelengths) {
        this.wavelengths = wavelengths;
        this.maxCalReferenceAndPixel = new double[13][2];
        this.wavelengthsBeforeCalibration = new double[13];
    }
    public double[][] calculateMaxCalibrationReference() {
        if (wavelengths == null) {
            System.out.println("no wavelength data");
            return null;
        }

        //iterate through wavelength data to obtain largest calibration reference and the associated pixel
        for (Wavelength w: this.wavelengths) {
            for (int i = 0; i < 13; i++) {
                double trueWavelength = trueWavelengths[i];
                double actualWavelength = w.getWavelength();
                double calReference = w.getCalibrationReference();

                if (trueWavelength - range < actualWavelength && trueWavelength + range > actualWavelength) {
                    if (calReference >= maxCalReferenceAndPixel[i][0] && calReference > this.trueWavelengths[i]) {
                        maxCalReferenceAndPixel[i][0] = calReference;
                        maxCalReferenceAndPixel[i][1] = w.getPixel();
                   }
                }

            }
        }
        //check for out of range values
        for (int i = 0; i < maxCalReferenceAndPixel.length; i++) {

            System.out.println("max calibration reference: " + maxCalReferenceAndPixel[i][0] + "  " + "pixel: " + maxCalReferenceAndPixel[i][1]);
            if (maxCalReferenceAndPixel[i][0] <= trueWavelengths[i]) {
                        String message = "Reference values out of range. Calibration Reference: " + maxCalReferenceAndPixel[i][0];
                        JFrame frame = new JFrame();
                        JButton restartButton = new JButton("Restart");
                        restartButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("restarting application");
                                System.exit(2);
                            }
                        });
                        frame.add(restartButton);

                        JOptionPane.showOptionDialog(frame, message, "Restart", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, new Object[]{restartButton, "End Program"}, null);
                        System.exit(1);
            }
        }
//        for (double[] values : maxCalReferenceAndPixel) {
//            System.out.println("max calibration reference: " + values[0] + "  " + "pixel: " + values[1]);
//        }


        return maxCalReferenceAndPixel;
    }

    public double[] calculateWavelengthsBeforeCalibration() {
        int pixel = 0;
        for (int i = 0; i < 13; i++) {

            for (int j = pixel; j < this.wavelengths.size(); j++) {
                if (wavelengths.get(j).getCalibrationReference() == maxCalReferenceAndPixel[i][0]) {
                    this.wavelengthsBeforeCalibration[i] = wavelengths.get(j).getWavelength();
                    pixel = wavelengths.get(j).getPixel();
                    break;
                }

            }


        }

        System.out.println("------Wavelengths before calibration-------");
        for (double w : wavelengthsBeforeCalibration) {
            System.out.println(w);
        }

        return wavelengthsBeforeCalibration;
    }

    public double[] getTrueWavelengths() {
        return this.trueWavelengths;
    }


}
