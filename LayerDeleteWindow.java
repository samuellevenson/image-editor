import javafx.collections.ObservableList;
import javafx.scene.Node;
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
 * Created by sammy on 5/10/17.
 */
public class LayerDeleteWindow {
    private static int layerToDelete;

    public static int display(ArrayList<Layer> layerList) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("delete layer");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("what layer do you want to delete?");

        ChoiceBox<String> choicebox = new ChoiceBox<>();
        for (Layer l : layerList) {
            choicebox.getItems().add(l.getName());
        }
        choicebox.getSelectionModel().selectFirst();

        Button ok = new Button("delete");
        ok.setOnAction(e -> {
            String name = choicebox.getValue();
            for(int i = 0; i < layerList.size(); i++) {
                if(name.equals(layerList.get(i).getName())) {
                    layerToDelete = i;
                }
            }
            window.close();
        });
        Button cancel = new Button("close");
        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().addAll(ok,cancel);
        cancel.setOnAction(e -> {
            window.close();
            layerToDelete = -1;
        });
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(label, choicebox, buttons);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return layerToDelete;
    }
}
