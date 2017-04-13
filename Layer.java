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
    private boolean visible;
    private int priority; //higher priority means layer will show up on top
    private int id;

    public Layer(File f, int priority, int id) {
        {
            Image read = new Image("file:" + f.getAbsolutePath());
            PixelReader reader = read.getPixelReader();
            picture = new WritableImage(reader, (int)read.getWidth(), (int)read.getHeight());
            this.priority = priority;
            visible = true;
            name = f.getName();
            this.id = id;
        }
    }
    public int getId() { return id; }
    public String getName() {return name; }
    public WritableImage getPicture() {
        return picture;
    }
    public int getPriority() { return priority; }
    public boolean isVisible() { return visible; }
    public void setPicture(Image picture) {
        this.picture = new WritableImage(picture.getPixelReader(), (int)picture.getWidth(), (int)picture.getHeight());
    }
    public void setPriority(int priority) { this.priority = priority; }
    public void setVisibility(boolean visible) {
        this.visible = visible;
    }
    public void setName(String name) {this.name = name;}
}
