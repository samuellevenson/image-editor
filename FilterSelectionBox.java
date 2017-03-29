//package org.wilsonhs.slevenson;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 *
 */
public class FilterSelectionBox {

    private static ArrayList<double[][]> filterList;
    private static double[][] filterChoice;

    public static double[][] display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("filter selection");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("what filter do you want?");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("a list");
        choiceBox.getItems().add("of filters");
        choiceBox.getItems().add("that do not do anything yet");
        choiceBox.getSelectionModel().selectFirst();
        //continue to add more filters in the future

        Button ok = new Button("ok");
        ok.setOnAction(e -> getChoice(choiceBox));
        Button close = new Button("close");
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,close);
        close.setOnAction(e -> window.close());
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(label, choiceBox, buttons);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return filterChoice;
    }

    private static void getChoice(ChoiceBox<String> choiceBox) {
        if(choiceBox.getValue().equals("test")) {
            //
        }

    }
}
