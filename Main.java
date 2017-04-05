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

public class Main extends Application {

    Stage window;
    File file;
    StackPane imageStack;
    ArrayList<Layer> layers = new ArrayList<>();
    VBox visibilityCheckboxes = new VBox();
    int currentLayer = -1;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("image editor");
        imageStack = new StackPane();
        imageStack.setMaxWidth(500);
        MenuBar menus = new MenuBar();
        BorderPane layout = new BorderPane();
        
        Menu fileMenu = new Menu("file");
        MenuItem save = new MenuItem("save");
        MenuItem saveAs = new MenuItem("save as...");
        MenuItem open = new MenuItem("open...");
        open.setOnAction(e -> {
            FileChooser choose = new FileChooser();
            file = choose.showOpenDialog(window);
            System.out.println(file.getAbsolutePath());
            currentLayer = 0;
            layers.add(new Layer(file));
            imageStack.getChildren().add(new ImageView(layers.get(layers.size()-1).getPicture()));
            window.show();
        });
        MenuItem close = new MenuItem("close program");
        close.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(save,saveAs,open,close);

        Menu editMenu = new Menu("edit");
        MenuItem cut = new Menu("cut");
        MenuItem copy = new Menu("copy");
        MenuItem paste = new Menu("paste");
        MenuItem deselect = new Menu("deselect");
        editMenu.getItems().addAll(cut,copy,paste,deselect);

        Menu toolsMenu = new Menu("tools");
        MenuItem brush = new Menu("brush");
        MenuItem fill = new Menu("fill");
        MenuItem eraser = new Menu("eraser");
        MenuItem crop = new Menu("crop");
        MenuItem resize = new Menu("resize");
        MenuItem rotate = new Menu("rotate");
        MenuItem filter = new Menu("filter");
        filter.setOnAction(e -> {
            Effect f = FilterSelectionBox.display();
            imageStack.getChildren().get(currentLayer).setEffect(f);
        });
        toolsMenu.getItems().addAll(brush,fill,eraser,crop,resize,rotate,filter);

        Menu layersMenu = new Menu("layers");
        MenuItem addLayer = new Menu("add layer...");
        addLayer.setOnAction(e -> {
            FileChooser choose = new FileChooser();
            File add = choose.showOpenDialog(window);
            layers.add(new Layer(add));
            imageStack.getChildren().add(new ImageView(layers.get(layers.size()-1).getPicture()));
            window.show();
        });

        MenuItem changeLayer = new Menu("change layer... ");
        changeLayer.setOnAction(e -> {
            currentLayer = ChangeLayerBox.display(layers);
        });
        MenuItem deleteLayer = new Menu("deleteLayer...");
        deleteLayer.setOnAction(e -> {
            int toDelete = LayerDeleteBox.display(layers);
            layers.remove(toDelete);
            imageStack.getChildren().remove(toDelete,toDelete+1);
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
}
