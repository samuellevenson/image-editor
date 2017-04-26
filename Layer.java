//package org.wilsonhs.slevenson;

import javafx.scene.image.*;

import java.io.File;
/*
 *
 */
public class Layer {
    private WritableImage picture;
    private ImageView imageView;
    private String name;
    private boolean visible;
    private int id;

    public Layer(File f, int priority, int id) {
        {
            Image read = new Image("file:" + f.getAbsolutePath());
            PixelReader reader = read.getPixelReader();
            picture = new WritableImage(reader, (int)read.getWidth(), (int)read.getHeight());
            imageView = new ImageView(picture);
            visible = true;
            name = f.getName();
            this.id = id;
        }
    }
    public int getId() { return id; }
    public ImageView getImageView() {return imageView; }
    public String getName() {return name; }
    public WritableImage getPicture() {
        return picture;
    }
    public boolean isVisible() { return visible; }
    public void setPicture(Image picture) {
        this.picture = new WritableImage(picture.getPixelReader(), (int)picture.getWidth(), (int)picture.getHeight());
    }
    public void setVisibility(boolean visible) {
        this.visible = visible;
        imageView.setVisible(visible);
    }
    public void setName(String name) {this.name = name;}
}
