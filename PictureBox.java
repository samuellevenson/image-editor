import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.io.File;
import java.util.ArrayList;

//package org.wilsonhs.slevenson;

/**
 *
 */

public class PictureBox {
    private ArrayList<Layer> layers;
    private int currentLayer;
    private static int width = Layer.maxWidth;
    private static int height = Layer.maxHeight;

    public PictureBox() {
        layers = new ArrayList<Layer>();
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
        WritableImage result = new WritableImage(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                for(int layer = layers.size()-1; layer >= 0; layer --) {
                    //check if this layer has a pixel in the spot x,y
                    //if yes, set the result image to that pixel
                    //if no, move on to the next layer
                }
            }
        }
        //temporary
        return layers.get(layers.size()-1).getPicture();
    }
    /*
     * removes the given layer from the picture
     */
    public void removeLayer(int layer) {
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

    }

}
