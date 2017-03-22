import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by sammy on 3/22/17.
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

        ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        choiceBox.getItems().add("blur");
        choiceBox.getItems().add("don't do anything if you choose this option");
        //continue to add more filters in the future

        Button ok = new Button("ok");
        //ok.setOnAction(e -> getChoice(choiceBox));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, choiceBox, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return filterChoice;
    }

    private void getChoice(ChoiceBox<String> choiceBox) {
        if(choiceBox.getValue().equals("blur")) {
            filterChoice = new double[3][3];
            for(int row = 0; row < 3; row++) {
                for(int col = 0; col < 3; col++){
                    filterChoice[row][col] = 1.0/9;
                }
            }
        }
    }
}
