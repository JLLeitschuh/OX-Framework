/**
 * ﻿Copyright (C) 2012-2014 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as publishedby the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.oxf.render.coverage;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;

import javax.media.jai.RasterFactory;

/**
 * @author <a href="mailto:broering@52north.org">Arne Broering</a>
 * 
 */
public class CoverageProcessor {

    public static final String WHITE_TO_BLACK = "WHITE_TO_BLACK";
    public static final String WHITE_TO_BLUE = "WHITE_TO_BLUE";
    public static final String YELLOW_TO_RED = "YELLOW_TO_RED";
    public static final String YELLOW_TO_BROWN = "YELLOW_TO_BROWN";
    
    private static final int ALPHA = 200;

    public Image createImage(DoubleCoverage doubleCoverage,
                             int numberOfClasses,
                             String colorPalette) {
        IntCoverage ic = classifyCoverage(doubleCoverage, numberOfClasses);
        return createImage(ic, numberOfClasses, colorPalette);
    }
    
    public IntCoverage classifyCoverage(DoubleCoverage doubleCoverage,
                                        int numberOfClasses) {
        double lowestValue = doubleCoverage.getMinValue();
        double highestValue = doubleCoverage.getMaxValue();
        
        IntCoverage intCoverage = new IntCoverage(doubleCoverage.getBoundingBox(),
                                                  doubleCoverage.getNumberOfColumns(),
                                                  doubleCoverage.getNumberOfRows());

        int grade = 1; // Hilfsvariable f�r die Klassifizierung
        int[][] grid = new int[doubleCoverage.getNumberOfRows()][doubleCoverage.getNumberOfColumns()];

        double valueStep = (highestValue - lowestValue) / (numberOfClasses - 2);
        for (int c = 0; c < doubleCoverage.getNumberOfColumns(); c++) {
            for (int r = 0; r < doubleCoverage.getNumberOfRows(); r++) {
                double i = lowestValue;
                grade = 1;
                while ( (i <= highestValue) && (doubleCoverage.getCellValue(r, c) >= i)) {
                    grid[r][c] = grade;
                    grade++;
                    i = i + valueStep;
                }
            }
        }

        intCoverage.setGrid(grid);
        return intCoverage;
    }

    public Image createImage(IntCoverage intCoverage, int numberOfClasses, String colorPalette) {

        // Bildgr��e gem�� der Rastergr��e festlegen
        int width = intCoverage.getNumberOfColumns();
        int height = intCoverage.getNumberOfRows();

        int[] imageDataSingleArray = new int[width * height];
        int count = 0;

        // Es ist wichtig, dass zuerst height und dann width durchgez�hlt wird.
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {

                int rasterValue = intCoverage.getCellValue(r, c);

                // Die R-,G- und B-Komponenten werden zu einem int-Wert vereint.
                imageDataSingleArray[count++] = computeCellColor(rasterValue,
                                                                 numberOfClasses,
                                                                 colorPalette);
            }
        }

        // Die Werte aus dem SingleArray in ein DataBuffer schreiben
        DataBufferInt dbuffer = new DataBufferInt(imageDataSingleArray, width * height);

        // Ein leeresBufferedImage mit der der gleichen Bildgr��e
        // wie unser Bild erstellen.
        BufferedImage bim = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Ein WritebleRaster erstellen, das das SampleModel des BufferedImage nutzt,
        // und dem Rasterdaten aus dem DataBuffer �bergeben werden.
        Raster raster = RasterFactory.createWritableRaster(bim.getSampleModel(),
                                                           dbuffer,
                                                           new Point(0, 0));

        // Die Daten aus dem Raster in das Bufferedimage einsetzen.
        // Wir haben ja die gleichen Dimensionen...
        bim.setData(raster);

        return bim;
    }

    public int computeCellColor(int cellValue, int numberOfClasses, String colorPalette) {
        // Variablen f�r die Formel zum Erstellen einer ColorRamp.
        // F�r jedes Band(Rot, Gr�n, Blau)gibt es einen Start- und Endwert.
        int startR;
        int endR;
        int startG;
        int endG;
        int startB;
        int endB;

        if (colorPalette.equals(YELLOW_TO_BROWN)) {
            startR = 255;
            endR = 100;
            startG = 255;
            endG = 0;
            startB = 128;
            endB = 0;
        }
        else if (colorPalette.equals(YELLOW_TO_RED)) {
            startR = 245;
            endR = 245;
            startG = 235;
            endG = 0;
            startB = 0;
            endB = 0;
        }
        else if (colorPalette.equals(WHITE_TO_BLUE)) {
            startR = 175;
            endR = 10;
            startG = 230;
            endG = 10;
            startB = 240;
            endB = 145;
        }
        else if (colorPalette.equals(WHITE_TO_BLACK)) {
            startR = 255;
            endR = 0;
            startG = 255;
            endG = 0;
            startB = 255;
            endB = 0;
        }
        else { // default: BLUE_TO_PINK
            startR = 24;
            endR = 245;
            startG = 230;
            endG = 0;
            startB = 245;
            endB = 245;
        }

        int rot = startR + (endR - startR) * cellValue / (numberOfClasses - 1);
        int gelb = startG + (endG - startG) * cellValue / (numberOfClasses - 1);
        int blau = startB + (endB - startB) * cellValue / (numberOfClasses - 1);

        return (ALPHA << 24) + (rot << 16) + (gelb << 8) + (blau);
    }
}