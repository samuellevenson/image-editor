//package org.wilsonhs.slevenson;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.io.File;
/*
 *
 */
public class Layer {
    private WritableImage picture;
    private String name;
    private double xPos;
    private double yPos;
    private boolean visible;

    public Layer(File f) {
        {
            Image read = new Image("file:" + f.getAbsolutePath());
            PixelReader reader = read.getPixelReader();
            picture = new WritableImage(reader, (int)read.getWidth(), (int)read.getHeight());

            xPos = 0;
            yPos = 0;
            visible = true;
            name = f.getName();
        }
    }
    public String getName() {return name; }
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
    public void setName(String name) {this.name = name;}
}
