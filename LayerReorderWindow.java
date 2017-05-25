import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by sammy on 5/25/17.
 */
public class LayerReorderWindow {
    private static ReorderAndLayer reorderAndLayer = new ReorderAndLayer();

    public static ReorderAndLayer display(ArrayList<Layer> layerList) {
        Stage window = new Stage();
        Pane imagePreviewStack = new Pane();
        for(Layer l: layerList) {
            imagePreviewStack.getChildren().add(l.getImageView());
        }
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("filter selection");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("change the order of the images");

        ChoiceBox<String> layersCb = new ChoiceBox<>();
        for (Layer layer : layerList) {
            layersCb.getItems().add(layer.getName());
        }
        layersCb.getSelectionModel().selectFirst();
        layersCb.setOnAction(e -> {
            String name = layersCb.getValue();
            for(int i = 0; i < layerList.size(); i++) {
                if(name.equals(layerList.get(i).getName())) {
                    reorderAndLayer.layerNum = i;
                    //update preview
                    imagePreviewStack.getChildren().add(reorderAndLayer.newPlace, imagePreviewStack.getChildren().remove(i));
                }
            }
        });

        Button ok = new Button("ok");
        ok.setOnAction(e -> {
            window.close();
        });
        Button cancel = new Button("cancel");
        cancel.setOnAction(e -> window.close());

        TextField newPlaceField = new TextField();
        newPlaceField.setOnAction(e -> {
            if(!newPlaceField.getText().equals(null)) {
                reorderAndLayer.newPlace = Integer.parseInt(newPlaceField.getText());
                //update preview
                imagePreviewStack.getChildren().add(reorderAndLayer.newPlace, imagePreviewStack.getChildren().remove(reorderAndLayer.layerNum));
            }
        });

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,cancel);
        VBox layout = new VBox();
        layout.getChildren().addAll(label, layersCb, newPlaceField, buttons);
        layout.setSpacing(10);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return reorderAndLayer;
    }
}
