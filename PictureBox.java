import javafx.scene.image.Image;
import java.io.File;
/**
 *
 */
public class PictureBox {
    Image picture;

    public PictureBox(File f) {
        picture = new Image("file:"+f.getAbsolutePath());
    }
    /*
     * changes how big the image is
     */
    public void resize(double scale) {

    }
    /*
     * rotates the image some amount of degrees
     */
    public void rotate(double degrees) {

    }
    /*
     * applies the kernel to the image
     */
    public void applyFilter(double[][] kernel) {

    }

}
