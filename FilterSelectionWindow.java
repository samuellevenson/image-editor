//package org.wilsonhs.slevenson;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 *
 */
public class FilterSelectionWindow {

    private static EffectAndLayer effectAndLayer = new EffectAndLayer();

    public static EffectAndLayer display(ArrayList<Layer> layerList) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("filter selection");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("what filter do you want?");
        ImageView imagePreview = new ImageView(layerList.get(0).getImage());

        ChoiceBox<Effect> filterCb = new ChoiceBox<>();
        filterCb.getItems().add(new BoxBlur());
        filterCb.getItems().add(new GaussianBlur());
        filterCb.getItems().add(new SepiaTone());
        filterCb.getItems().add(new Glow());
        filterCb.getSelectionModel().selectFirst();
        filterCb.setOnAction(event -> {
            Effect f = filterCb.getValue();
            imagePreview.setEffect(f);
            window.show();
        });
        imagePreview.setEffect(filterCb.getValue());

        ChoiceBox<String> layersCb = new ChoiceBox<>();
        for (Layer layer : layerList) {
            layersCb.getItems().add(layer.getName());
        }
        layersCb.getSelectionModel().selectFirst();
        effectAndLayer.layerNum = 0;
        layersCb.setOnAction(e -> {
            String name = layersCb.getValue();
            for(int i = 0; i < layerList.size(); i++) {
                if(name.equals(layerList.get(i).getName())) {
                    imagePreview.setImage(layerList.get(i).getImage());
                    effectAndLayer.layerNum = i;
                }
            }
        });

        Button ok = new Button("ok");
        ok.setOnAction(e -> {
            effectAndLayer.effect = filterCb.getValue();
            window.close();
        });

        Button cancel = new Button("cancel");
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,cancel);
        cancel.setOnAction(e -> window.close());
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(label, filterCb, layersCb, imagePreview, buttons);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return effectAndLayer;
    }

}
