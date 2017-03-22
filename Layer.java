import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.io.File;

//package org.wilsonhs.slevenson;

/**
 *
 */
public class Layer {
    private WritableImage picture;
    private double xPos;
    private double yPos;
    private boolean visible;
    public static int maxWidth = 100;
    public static int maxHeight = 100;

    public Layer(File f) {
        {
            Image read = new Image("file:" + f.getAbsolutePath());
            PixelReader reader = read.getPixelReader();
            picture = new WritableImage(reader, (int)read.getWidth(), (int)read.getHeight());

            //scale image down if it is greater than maxWidth or maxHeight
            int newWidth = (int)read.getWidth();
            int newHeight = (int)read.getHeight();
            if(newWidth > maxWidth) {
                newWidth = maxWidth;
                newHeight = (int)(read.getHeight()/read.getWidth()) * maxWidth;
            }
            if(newHeight > maxHeight) {
                newHeight = maxHeight;
                newWidth = (int)(read.getWidth()/read.getHeight()) * maxHeight;
            }
            PixelWriter writer = picture.getPixelWriter();
            for(int x = 0; x < newWidth; x++) {
                for(int y = 0; y < newHeight; y++) {
                    int sx = x * (int)read.getWidth()/newWidth;
                    int sy = y * (int)read.getHeight()/newHeight;
                    writer.setColor(x, y, reader.getColor(sx, sy));
                }
            }
            xPos = 0;
            yPos = 0;
            visible = true;
        }
    }
    public double getxPos() {
        return xPos;
    }
    public double getyPos() {
        return yPos;
    }
    public WritableImage getPicture() {
        return picture;
    }
    public void setPicture(Image picture) {
        this.picture = new WritableImage(picture.getPixelReader(), (int)picture.getWidth(), (int)picture.getHeight());
    }
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }
}
