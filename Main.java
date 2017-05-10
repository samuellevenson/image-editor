import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 *
 */
public class Main extends Application{

    static Stage mainStage;
    static Scene mainScene;
    static BorderPane borderpane = new BorderPane();
    static Pane imageStack = new Pane();
    static VBox visibilityCheckboxes = new VBox();
    MenuBar menuBar = new MenuBar();
    static ArrayList<Layer> layerList = new ArrayList<>();
    static int currentLayer = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //setup the window
        mainStage = stage;
        stage.setTitle("my program");
        mainScene = new Scene(borderpane, 1200, 800);
        stage.setScene(mainScene);
        mainStage.show();
        borderpane.setCenter(imageStack);
        borderpane.setLeft(visibilityCheckboxes);
        borderpane.setTop(menuBar);

        //create menu at the top
        Menu fileMenu = new Menu("file");
        MenuItem save = new MenuItem("save");
        MenuItem close = new MenuItem("close program");
        close.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(save,close);
        Menu layersMenu = new Menu("layers");
        MenuItem addLayer = new MenuItem("add layer...");
        addLayer.setOnAction(e -> {
            FileChooser choose = new FileChooser();
            File f = choose.showOpenDialog(mainStage);
            if(f != null) {
                newLayer(f);
            }
            mainStage.show();
        });

        MenuItem changeLayer = new MenuItem("change layer... ");
        changeLayer.setOnAction(e -> {
            currentLayer = ChangeLayerWindow.display(layerList);
        });
        MenuItem deleteLayer = new MenuItem("delete layer...");
        deleteLayer.setOnAction(e -> {
            int toDelete = LayerDeleteWindow.display(layerList);
            layerList.remove(toDelete);
            imageStack.getChildren().remove(toDelete);
            visibilityCheckboxes.getChildren().remove(toDelete);
        });
        MenuItem applyFilter = new MenuItem("apply filter");
        applyFilter.setOnAction(e -> {
            Effect f = FilterSelectionWindow.display(layerList.get(currentLayer));
            if(f != null) {
                imageStack.getChildren().get(currentLayer).setEffect(f);
            }
        });
        layersMenu.getItems().addAll(addLayer,changeLayer,deleteLayer,applyFilter);
        menuBar.getMenus().addAll(fileMenu,layersMenu);

    }

    /**
     * all the stuff that happens when a new image is added
     */
    public static void newLayer(File f) {
        Layer newLayer = new Layer(f);
        //make new image draggable around the scene
        class MousePos {private double mouseX, mouseY;}
        MousePos m = new MousePos();
        newLayer.getImageView().setOnMousePressed(e -> {
            m.mouseX = e.getSceneX();
            m.mouseY = e.getSceneY();
        });
        newLayer.getImageView().setOnMouseDragged(e -> {
            double deltaX = e.getSceneX() - m.mouseX ;
            double deltaY = e.getSceneY() - m.mouseY ;
            newLayer.getImageView().relocate(newLayer.getImageView().getLayoutX() + deltaX, newLayer.getImageView().getLayoutY() + deltaY);
            m.mouseX = e.getSceneX() ;
            m.mouseY = e.getSceneY() ;
        });
        //create checkbox to change image's visibilty
        CheckBox newCb = new CheckBox(f.getName());
        visibilityCheckboxes.getChildren().add(newCb);
        newCb.setSelected(true);
        newCb.setOnAction(e -> {
            newLayer.getImageView().setVisible(!newLayer.getImageView().isVisible());
        });
        layerList.add(newLayer);
        imageStack.getChildren().add(newLayer.getImageView());
        //new current image is most recent on to be added until changed by user
        currentLayer = imageStack.getChildren().size()-1;
    }

}
