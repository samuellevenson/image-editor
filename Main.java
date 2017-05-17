import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        borderpane.setTop(menuBar);
        showVisibilityCheckboxesWindow();

        //create menu at the top
        Menu fileMenu = new Menu("file");
        MenuItem save = new MenuItem("save");
        save.setOnAction(e -> {
            save();
        });
        MenuItem open = new MenuItem("open");
        MenuItem close = new MenuItem("close program");
        close.setOnAction(e -> Platform.exit());
        fileMenu.getItems().addAll(save,open,close);
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
            currentLayer = imageStack.getChildren().size() - 1;
        });
        MenuItem applyFilter = new MenuItem("apply filter");
        applyFilter.setOnAction(e -> {
            EffectAndLayer f = FilterSelectionWindow.display(layerList);
            if(f != null) {
                imageStack.getChildren().get(f.layerNum).setEffect(f.effect);
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
        Point2D.Double mousePos = new Point2D.Double();
        newLayer.getImageView().setOnMousePressed(e -> {
            mousePos.setLocation(e.getSceneX(), e.getSceneY());

        });
        newLayer.getImageView().setOnMouseDragged(e -> {
            double deltaX = e.getSceneX() - mousePos.getX() ;
            double deltaY = e.getSceneY() - mousePos.getY() ;
            double newX = newLayer.getImageView().getLayoutX() + deltaX;
            double newY = newLayer.getImageView().getLayoutY() + deltaY;
            if(true) { //condition for checking if image will still be on screen?
                newLayer.getImageView().relocate(newX, newY);
            }
            mousePos.setLocation(e.getSceneX(), e.getSceneY());
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

    /**
     * sets up the separate window containing the visibility checkboxes
     */
    public static void showVisibilityCheckboxesWindow() {
        Stage visibilityCheckboxesWindow = new Stage();
        visibilityCheckboxesWindow.setTitle("visibility");
        visibilityCheckboxesWindow.setMaxWidth(180);
        visibilityCheckboxes.setSpacing(15);
        visibilityCheckboxesWindow.setScene(new Scene(visibilityCheckboxes));
        visibilityCheckboxesWindow.show();
    }

    public static void save() {
        //set up the window
        Stage saveWindow = new Stage();
        saveWindow.initModality(Modality.APPLICATION_MODAL);
        saveWindow.setTitle("save your work");
        Label saveAsLabel = new Label("save as");
        TextField saveAsField = new TextField();
        HBox saveAs = new HBox();
        saveAs.getChildren().addAll(saveAsLabel,saveAsField);
        saveAs.setSpacing(10);
        Label whereLabel = new Label("where");
        TextField whereField = new TextField();
        HBox where = new HBox();
        where.getChildren().addAll(whereLabel,whereField);
        where.setSpacing(10);
        Button save = new Button("save");
        Button cancel = new Button("cancel");
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(cancel, save);
        VBox vb = new VBox();
        vb.getChildren().addAll(saveAs,where,buttons);
        vb.setSpacing(10);
        saveWindow.setScene(new Scene(vb));
        saveWindow.show();

        save.setOnAction(e -> {
            if(!whereField.getText().equals("")){
                saveWindow.close();
                String saveTo = whereField.getText();
                System.out.println(whereField.getText());
                try {
                    File outputFolder = new File(saveTo + "/" + saveAsField.getText());
                    outputFolder.mkdirs();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder.getAbsolutePath() + "/positions")); //create text document for image positions
                    for(Layer l: layerList){
                        writer.write(l.getImageView().getLayoutX() + "," +  l.getImageView().getLayoutY() + ";");
                    }
                    File imageFolder = new File(outputFolder.getAbsolutePath() + "/images"); //create folder for images
                    imageFolder.mkdirs();
                    for(Layer l: layerList) {
                        File image = new File(imageFolder.getAbsolutePath() + "/" + l.getName());
                        BufferedImage bImage = SwingFXUtils.fromFXImage(l.getImage(), null);
                        ImageIO.write(bImage, "png", image);
                    }
                    writer.close();

                } catch(IOException ex) {
                    System.out.println("Unable to output to file: " + ex.getMessage());
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
        cancel.setOnAction(e -> {
            saveWindow.close();
        });
    }

}
