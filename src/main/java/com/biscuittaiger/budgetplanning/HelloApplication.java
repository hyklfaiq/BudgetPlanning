package com.biscuittaiger.budgetplanning;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);

        VBox utilityBox = new VBox();
        VBox groceriesBox = new VBox();
        VBox transportationBox = new VBox();
        VBox otherBox = new VBox();

        Label utilitytext = new Label("Utility");
        utilityBox.getChildren().add(utilitytext);
        utilityBox.setAlignment(Pos.CENTER);

        BorderStroke stroke1 = new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, new
                CornerRadii(0), new
                BorderWidths(2));

        utilityBox.setBorder(new Border(stroke1));
        root.getChildren().addAll(utilityBox, groceriesBox, transportationBox, otherBox);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("BudgetPlanning");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}