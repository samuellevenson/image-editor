//package org.wilsonhs.slevenson;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.effect.Effect;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.ArrayList;

import static com.sun.tools.doclint.Entity.Delta;

public class Main extends Application {

    static Stage window;
    static File file;
    static StackPane imageStack;
    static ArrayList<Layer> layers = new ArrayList<>();
    static VBox visibilityCheckboxes = new VBox();
    static int currentLayer = 0;
    static int layerIdCounter = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("image editor");
        imageStack = new StackPane();
        imageStack.setMaxWidth(500);
        MenuBar menus = new MenuBar();
        visibilityCheckboxes.setSpacing(10);
        visibilityCheckboxes.getChildren().add(new Label("layer visibilty:  "));
        BorderPane layout = new BorderPane();
        
        Menu fileMenu = new Menu("file");
        MenuItem save = new MenuItem("save");
        MenuItem saveAs = new MenuItem("save as...");
        MenuItem open = new MenuItem("open...");
        open.setOnAction(e -> {

        });
        MenuItem close = new MenuItem("close program");
        close.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(save,saveAs,open,close);

        Menu editMenu = new Menu("edit");
        MenuItem cut = new MenuItem("cut");
        MenuItem copy = new MenuItem("copy");
        MenuItem paste = new MenuItem("paste");
        MenuItem deselect = new MenuItem("deselect");
        editMenu.getItems().addAll(cut,copy,paste,deselect);

        Menu toolsMenu = new Menu("tools");
        MenuItem brush = new MenuItem("brush");
        MenuItem fill = new MenuItem("fill");
        MenuItem eraser = new MenuItem("eraser");
        MenuItem crop = new MenuItem("crop");
        MenuItem resize = new MenuItem("resize");
        MenuItem rotate = new MenuItem("rotate");
        MenuItem filter = new MenuItem("filter");
        filter.setOnAction(e -> {
            Effect f = FilterSelectionBox.display(layers.get(currentLayer));
            if(f != null) {
                imageStack.getChildren().get(currentLayer).setEffect(f);
            }
        });
        toolsMenu.getItems().addAll(brush,fill,eraser,crop,resize,rotate,filter);

        Menu layersMenu = new Menu("layers");
        MenuItem addLayer = new MenuItem("add layer...");
        addLayer.setOnAction(e -> {
            FileChooser choose = new FileChooser();
            File add = choose.showOpenDialog(window);
            if(add != null) {
                int priority = layers.size(); //adds new layer on top
                newLayer(add, priority, layerIdCounter++);
                makeCheckbox(add.getName(), layers.get(layers.size() - 1));
            }
            window.show();
        });

        MenuItem changeLayer = new MenuItem("change layer... ");
        changeLayer.setOnAction(e -> {
            currentLayer = ChangeLayerBox.display(layers);
        });
        MenuItem deleteLayer = new MenuItem("deleteLayer...");
        deleteLayer.setOnAction(e -> {
            int toDelete = LayerDeleteBox.display(layers);
            if(toDelete >= 0) {
                layers.remove(toDelete);
                imageStack.getChildren().remove(toDelete);
                visibilityCheckboxes.getChildren().remove(toDelete+1);
            }
            window.show();
        });
        layersMenu.getItems().addAll(addLayer,changeLayer,deleteLayer);

        menus.getMenus().addAll(fileMenu,editMenu,toolsMenu,layersMenu);
        layout.setTop(menus);
        layout.setCenter(imageStack);
        layout.setRight(visibilityCheckboxes);
        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void newLayer(File f, int priority, int id) {
        Layer newLayer = new Layer(f, priority, id);
        layers.add(newLayer);
        AnchorPane anchor = new AnchorPane(newLayer.getImageView());
        anchor.setLeftAnchor(newLayer.getImageView(), 0.0);
        anchor.setTopAnchor(newLayer.getImageView(), 0.0);
        //trying to drag images around
        class Drag {double x, y, dx, dy;} //tutorial for dragging nodes used this, represents distance to move (or moved)
        final Drag drag = new Drag();
        newLayer.getImageView().setOnMousePressed(e -> {
            drag.x = e.getSceneX(); //when the mouse is pressed, remember where it was
            drag.y = e.getSceneY();
        });
        newLayer.getImageView().setOnMouseDragged(e -> {
            drag.dx = e.getX() - drag.x;
            drag.dy = e.getY() - drag.y;

            double currentXAnchor = AnchorPane.getLeftAnchor(newLayer.getImageView());
            double currentYAnchor = AnchorPane.getTopAnchor(newLayer.getImageView());

            AnchorPane.setLeftAnchor(newLayer.getImageView(), currentXAnchor + drag.dx);
            AnchorPane.setTopAnchor(newLayer.getImageView(), currentYAnchor + drag.dy);
        });
        imageStack.getChildren().add(newLayer.getImageView());
    }

    private static void makeCheckbox(String name, Layer l) {
        CheckBox cb = new CheckBox(name);
        cb.setSelected(true);
        cb.setOnAction(e -> {
            for(int i = 0; i < layers.size(); i++) { //search through all layers to find the one with the matching id
                if(layers.get(i).getId() == l.getId()) {
                    layers.get(i).setVisibility(!layers.get(i).isVisible());  //switch the visibility of the layer
                }
            }
        });
        visibilityCheckboxes.getChildren().add(cb);
    }
}
