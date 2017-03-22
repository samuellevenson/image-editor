import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Main extends Application {

    Stage window;
    File file;
    Image picture;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("image editor");
        ImageView pictureFrame = new ImageView();
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
            picture = new Image("file:"+ file.getAbsolutePath());
            pictureFrame.setImage(picture);
            window.show();
        });
        MenuItem close = new MenuItem("close program");
        fileMenu.getItems().addAll(save,saveAs,open,close);

        Menu editMenu = new Menu("edit");
        MenuItem cut = new Menu("cut");
        MenuItem copy = new Menu("copy");
        MenuItem paste = new Menu("paste");
        MenuItem deselect = new Menu("deselect");
        MenuItem apply = new Menu("apply...");
        editMenu.getItems().addAll(cut,copy,paste,deselect,apply);

        Menu toolsMenu = new Menu("tools");
        MenuItem brush = new Menu("brush");
        MenuItem fill = new Menu("fill");
        MenuItem eraser = new Menu("eraser");
        MenuItem crop = new Menu("crop");
        MenuItem resize = new Menu("resize");
        MenuItem rotate = new Menu("rotate");
        toolsMenu.getItems().addAll(brush,fill,eraser,crop,resize,rotate);

        Menu layersMenu = new Menu("layers");
        MenuItem addLayer = new Menu("add layer...");
        MenuItem changeLayer = new Menu("change layer... ");
        MenuItem deleteLayer = new Menu("deleteLayer...");
        layersMenu.getItems().addAll(addLayer,changeLayer,deleteLayer);

        menus.getMenus().addAll(fileMenu,editMenu,toolsMenu,layersMenu);


        layout.setTop(menus);
        layout.setCenter(pictureFrame);

        Scene scene = new Scene(layout, 700, 500);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
