//package org.wilsonhs.slevenson;

import javafx.scene.image.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */
public class PictureBox {
    private ArrayList<Layer> layers;
    private int currentLayer;
    private static int width = Layer.maxWidth;
    private static int height = Layer.maxHeight;
    private ImageView view;

    public PictureBox() {
        layers = new ArrayList<>();
        currentLayer = -1;
    }
    /*
     * adds a new layer to the picture
     */
    public void addLayer(File f) {
        layers.add(new Layer(f));
        if(currentLayer == -1) {
            currentLayer = 0;
        }
    }
    /*
     * puts the layers into the order where 0 is the backmost and the last layer element in the frontmost to create one image
     */
    public Image display() {
        //temporary
        return layers.get(layers.size()-1).getPicture();
    }
    /*
     * removes the given layer from the picture
     */
    public void deleteLayer(int layer) {
        layers.remove(layer);
    }
    /*
     * changes how big the image in the current layer is
     */
    public void scale (int newW, int newH) {
        PixelReader reader = layers.get(currentLayer).getPicture().getPixelReader();

    }
    /*
     * rotates the image in the current layer by some amount of degrees
     */
    public void rotate(double degrees) {

    }
    /*
     * applies the kernel to the image in the current layer
     */
    public void applyFilter(double[][] kernel) {
        int r = kernel.length/2;
        PixelWriter writer = layers.get(currentLayer).getPicture().getPixelWriter();
        for(int x0 = r; x0 < layers.get(currentLayer).getPicture().getWidth() - r; x0++) {
            for(int y0 = r; y0 < layers.get(currentLayer).getPicture().getHeight() - r; y0++) {
                //
            }
        }
    }
    public ArrayList<Layer> getLayers() {
        return layers;
    }

}
