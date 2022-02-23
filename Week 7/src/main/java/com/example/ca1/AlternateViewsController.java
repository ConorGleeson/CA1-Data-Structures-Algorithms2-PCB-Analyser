package com.example.ca1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AlternateViewsController {


    public static Image imageOriginal;
    @FXML
    ImageView componentColor , randomColor;




    public void initialize() {
        imageOriginal = MainMenuController.image;
        componentColor.setImage(MainMenuController.componentColorImage);
        componentColor.setFitHeight(MainMenuController.componentColorImage.getHeight());
        componentColor.setFitWidth(MainMenuController.componentColorImage.getWidth());

        randomColor.setImage(MainMenuController.randomColorImage);
        randomColor.setFitHeight(MainMenuController.randomColorImage.getHeight());
        randomColor.setFitWidth(MainMenuController.randomColorImage.getWidth());

    }


    public void componentColor(){

    }



    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
