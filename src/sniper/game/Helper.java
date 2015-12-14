/*
 * Copyright (C) 2015 Kamil Cukrowski
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package sniper.game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Klasa z prostymi funkcjami potrzebnymi w innych klasach
 *
 * @author Kamil Cukrowski
 */
public class Helper {
    
    /**
     * Funkcja zmienia obraz przekazywany przez parametr w taki sposób, że
     * wartości kolorów (RGB) pikseli są przemnażane przez współczynniki podane
     * jako parametr do funkcji o wartościach od 0.0 do 1.0.
     *
     * @param image Object Image
     * @param adjRed Współczynnik czerwony 0.0 - 1.0
     * @param adjGreen współczynnik zielony 0.0 - 1.0
     * @param adjBlue współczynnik niebieski 0.0 - 1.0
     * @return nowy obraz
     */
    public static Image overrideImage(Image image, double adjRed, double adjGreen, double adjBlue) {
        if (image == null) {
            return null;
        }
        double height = image.getHeight();
        double width = image.getWidth();
        PixelWriter pixelWriter;
        PixelReader pixelReader;
        pixelReader = image.getPixelReader();
        WritableImage writableImage = new WritableImage((int) width, (int) height);
        pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);

                double red = color.getRed() + adjRed;
                if (red > 1.0) {
                    red = 1.0;
                } else if (red < 0.0) {
                    red = 0.0;
                }

                double green = color.getGreen() + adjGreen;
                if (green > 1.0) {
                    green = 1.0;
                } else if (green < 0.0) {
                    green = 0.0;
                }

                double blue = color.getBlue() + adjBlue;
                if (blue > 1.0) {
                    blue = 1.0;
                } else if (blue < 0.0) {
                    blue = 0.0;
                }

                double opacity = color.getOpacity();

                pixelWriter.setColor(x, y, new Color(red, green, blue, opacity));
            }
        }
        return (Image) writableImage;
    }

    /**
     * Zwraca int randomowy z zakresu od low do high włącznie.
     *
     * @param low
     * @param high
     * @return
     */
    public static int Rnd(int low, int high) {
        return (int) (low + ((double) (high - low) * Math.random()) + 0.5);
    }

    /**
     * Zwraca int randomowy z zakresu od 0 (włąćznie!) do limit(też włącznie).
     *
     * @param limit
     * @return
     */
    public static int Rnd(int limit) {
        return Rnd(0, limit);
    }

    /**
     * zwraca kąt zawarty pomiędzy osią OY a wektorem wyznaczonym przez podane
     * dwa punkty
     *
     * @param p1
     * @param p2
     * @return
     */
    public static double GetAngleOfLineBetweenTwoPoints(Point2D p1, Point2D p2) {
        //return new Point2D(1, 0).angle(p2.subtract(p1));
        return Math.atan2(p1.getX() - p2.getX(), p1.getY() - p2.getY()) * 180 / Math.PI;
    }
}
