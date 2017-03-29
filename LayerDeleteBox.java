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
public class LayerDeleteBox {
    private static int layerToDelete;

    public static int display(ArrayList<Layer> layers) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("delete layer");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("what layer do you want to delete?");

        ChoiceBox<String> choicebox = new ChoiceBox<>();
        for (Layer layer : layers) {
            choicebox.getItems().add(layer.getName());
        }
        choicebox.getSelectionModel().selectFirst();

        Button ok = new Button("delete");
        ok.setOnAction(e -> getChoice(choicebox));
        Button close = new Button("close");
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,close);
        close.setOnAction(e -> window.close());
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(label, choicebox, buttons);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return layerToDelete;
    }

    private static void getChoice(ChoiceBox<String> choiceBox) {
            //
    }
}
