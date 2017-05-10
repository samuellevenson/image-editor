import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * having trouble deciding if this class needs to exist
 */
public class Layer {
    private Image image;
    private ImageView imageView;
    private String name;

    public Layer(File f) {
        image = new Image("file:" + f.getAbsolutePath());
        imageView = new ImageView(image);
        name = f.getName();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
