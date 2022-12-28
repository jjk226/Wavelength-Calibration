package org.example;

import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WavelengthCalibration {

    public static void main(String[] args) throws IOException {
        System.out.println(">> start program");

        JFileChooser j = new JFileChooser("C:/Users/ECI/oceanview/");
        j.setDialogTitle("Spectrometer Wavelength Calibration");

        j.showOpenDialog(null);
        String url = j.getSelectedFile().toString();

        System.out.println(">> text file location: " + url);

//        String url = "C:/Users/jamekim/oceanview/USB2U361721__0__10-40-28-719.txt";
        File file = new File(url);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        ArrayList<Wavelength> wavelengths = parseData(bufferedReader);
        CalibrationData calibrationData = new CalibrationData(wavelengths);

        double[][] maxCalibrationReference = calibrationData.calculateMaxCalibrationReference();
        double[] wavelengthsBeforeCalibration = calibrationData.calculateWavelengthsBeforeCalibration();

        PixelCalculator pixelCalculator = new PixelCalculator(maxCalibrationReference, wavelengthsBeforeCalibration);
        double[] trueWavelengths = calibrationData.getTrueWavelengths();

        InputStream inp = new FileInputStream("temp.xlsm");
        XSSFWorkbook wb = new XSSFWorkbook(inp);
        XSSFSheet sheet = wb.getSheetAt(0);
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);



        XSSFRow header = sheet.createRow(0);
        XSSFCell headerCell = header.createCell(0);
        headerCell.setCellValue("true wavelength");

        headerCell = header.createCell(1);
        headerCell.setCellValue("pixel");

        headerCell = header.createCell(2);
        headerCell.setCellValue("pixel ^ 2");

        headerCell = header.createCell(3);
        headerCell.setCellValue("pixel ^ 3");

        for (int i = 0; i < pixelCalculator.getPixelLength(); i++) {
            XSSFRow row = sheet.createRow(i + 1);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue(trueWavelengths[i]);

            cell = row.createCell(1);
            cell.setCellValue(pixelCalculator.getPixels()[i]);

            cell = row.createCell(2);
            cell.setCellValue(pixelCalculator.getPixelsSquared()[i]);

            cell = row.createCell(3);
            cell.setCellValue(pixelCalculator.getPixelsCubed()[i]);
        }


        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("temp.xlsm")) {
            wb.write(fileOut);
        }

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println(">> saving to location: " + s);
        Desktop.getDesktop().open(new File(currentRelativePath + "temp.xlsm"));



    }

    public static ArrayList<Wavelength> parseData(BufferedReader bufferedReader) throws IOException {
        ArrayList<Wavelength> wavelengths = new ArrayList<>();
        String line = "";
        int pixel = 1;

        //skip first 13 lines of the text file
        for (int i = 0; i < 14; i++) {
            bufferedReader.readLine();
        }

        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split("\\s+");

            float w = Float.parseFloat(data[0]);
            float cr = Float.parseFloat(data[1]);

            Wavelength wavelength = new Wavelength(pixel++, w, cr);
            System.out.println(wavelength);
            wavelengths.add(wavelength);

        }

        bufferedReader.close();
        return wavelengths;
    }

//

}





