package main.java.View;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;


public class Popup {


    public static void display(String s) {
        Stage popupwindow=new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Error");

        String imageName = "file:resources/media/oops.gif";
        ImageView image = new ImageView(imageName);
        image.setFitHeight(50);
        image.setFitWidth(110);

        Label label1= new Label(s);

        Button button1= new Button("OK");
        button1.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);

        layout.getChildren().addAll(label1, image, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 250, 200);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

    }

}

