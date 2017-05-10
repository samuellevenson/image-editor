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

    private static Effect filter;

    public static Effect display(Layer l) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("filter selection");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("what filter do you want?");

        ImageView imagePreview = new ImageView(l.getImage());

        ChoiceBox<Effect> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add(new BoxBlur());
        choiceBox.getItems().add(new GaussianBlur());
        choiceBox.getItems().add(new SepiaTone());
        choiceBox.getItems().add(new Glow());
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setOnAction(event -> {
            Effect f = choiceBox.getValue();
            imagePreview.setEffect(f);
            window.show();
        });

        Button ok = new Button("ok");
        ok.setOnAction(e -> {
            filter = choiceBox.getValue();
            window.close();
        });

        Button close = new Button("close");
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,close);
        close.setOnAction(e -> window.close());
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(label, choiceBox, imagePreview, buttons);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return filter;
    }

}
