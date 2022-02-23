package com.example.ca1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class MainMenuController {


    @FXML
    private Button chooseFile, greyScale, loadButton, quit, reset, resetTolerance;

    @FXML
    private ImageView imageView;
    @FXML
    private Label header, fileName, filePath, fileSize, timeStamp;

    @FXML
    private CheckBox convertBox, convertBox2;

    @FXML
    private Window stage1;
    @FXML
    private Slider brightnessSlider, saturationSlider, hueSlider, brightnessToleranceSlider, saturationToleranceSlider, hueToleranceSlider;

    public static Image image;
    public static Color color1;
    public static Image componentColorImage;
    public static Image randomColorImage;
    public Image blackAndWhite;

//    public static double color1Red;
//    public static double color1Green;
//    public static double color1Blue;


    public void initialize() {
        imageView.setImage(AlternateViewsController.imageOriginal);
        if(AlternateViewsController.imageOriginal != null) {
            imageView.setFitHeight(AlternateViewsController.imageOriginal.getHeight());
            imageView.setFitWidth(AlternateViewsController.imageOriginal.getWidth());
        }

    }


    public void fileChoose() {
        FileChooser file_chooser = new FileChooser();

        EventHandler<ActionEvent> event = e -> {
            File file = file_chooser.showOpenDialog(stage1);

            if (file != null) {

                //image = new Image("file:///" + file.getAbsolutePath(), 512, 512, false, false);
                image = new Image("file:///" + file.getAbsolutePath());
                imageView.setFitHeight(image.getHeight());
                imageView.setFitWidth(image.getWidth());
//                imageView.setFitHeight(512);
//                imageView.setFitHeight(512);
                imageView.setImage(image);

                fileName.setText("File Name: " + file.getName());
                filePath.setText("File Path: " + file.getPath());
                fileSize.setText("File Size: " + file.length() / 1024 + "KB");


            }
        };
        loadButton.setOnAction(event);


    }



    public void convertToBlackAndWhite(MouseEvent e) {

        double randomRed = Math.random();
        double randomGreen = Math.random();
        double randomBlue = Math.random();

        Image image = imageView.getImage();
        PixelReader pixelReader = image.getPixelReader();

         color1 = pixelReader.getColor((int) e.getX(), (int) e.getY());
        double color1Red = color1.getRed();
         double color1Green = color1.getGreen();
        double  color1Blue = color1.getBlue();
        double color1Hue = color1.getHue();
        double color1Brightness = color1.getBrightness();
        double color1Saturation = color1.getSaturation();


        WritableImage newImage = new WritableImage((int) (image.getWidth()), (int) image.getHeight());
        WritableImage writableComponentColorImage = new WritableImage((int) (image.getWidth()), (int) image.getHeight());
        WritableImage writableRandomColorImage =  new WritableImage((int) (image.getWidth()), (int) image.getHeight());

        PixelWriter pixelWriter = newImage.getPixelWriter();
        PixelWriter pixelWriterComponentColor = writableComponentColorImage.getPixelWriter();
        PixelWriter pixelWriterRandomColor = writableRandomColorImage.getPixelWriter();
        for (int i = 0; i < newImage.getWidth(); ++i) {
            for (int j = 0; j < newImage.getHeight(); ++j) {
                Color color2 = pixelReader.getColor(i, j);
                double color2Hue = color2.getHue();
                double color2Brightness = color2.getBrightness();
                double color2Saturation = color2.getSaturation();
//                System.out.println(color1Hue);
//                System.out.println(color2Hue);
                //double difference = Math.abs(color1Hue - color2Hue);
               double hueDifference = color2Hue - color1Hue;
               double brightnessDifference = color2Brightness - color1Brightness;
               double saturationDifference = color2Saturation - color1Saturation;



                if(hueDifference >= -hueToleranceSlider.getValue() && hueDifference <= hueToleranceSlider.getValue() && saturationDifference >= -saturationToleranceSlider.getValue() && saturationDifference <= saturationToleranceSlider.getValue() && brightnessDifference >=-brightnessToleranceSlider.getValue() && brightnessDifference <= brightnessToleranceSlider.getValue()){
                    pixelWriter.setColor(i, j, new Color(0, 0, 0, 1.0));
                    //pixelWriter.setColor(i,j,new Color(color1Red, color1Green, color1Blue, 1.0)); //this sets the found components to the selected color
                }else  pixelWriter.setColor(i, j, new Color(1, 1, 1, 1.0));
            }
        }imageView.setImage(newImage);
        blackAndWhite = newImage;
        disjointSet();

        for (int i = 0; i < writableComponentColorImage.getWidth(); ++i) {
            for (int j = 0; j < writableComponentColorImage.getHeight(); ++j) {
                Color color2 = pixelReader.getColor(i, j);
                double color2Hue = color2.getHue();
                double color2Brightness = color2.getBrightness();
                double color2Saturation = color2.getSaturation();
                double hueDifference = color2Hue - color1Hue;
                double brightnessDifference = color2Brightness - color1Brightness;
                double saturationDifference = color2Saturation - color1Saturation;



                if(hueDifference >= -hueToleranceSlider.getValue() && hueDifference <= hueToleranceSlider.getValue() && saturationDifference >= -saturationToleranceSlider.getValue() && saturationDifference <= saturationToleranceSlider.getValue() && brightnessDifference >=-brightnessToleranceSlider.getValue() && brightnessDifference <= brightnessToleranceSlider.getValue()){
                    pixelWriterComponentColor.setColor(i,j,new Color(color1Red, color1Green, color1Blue, 1.0)); //this sets the found components to the selected color
                }else  pixelWriterComponentColor.setColor(i, j, new Color(1, 1, 1, 1.0));
            }
        }componentColorImage = writableComponentColorImage;

        for (int i = 0; i < writableRandomColorImage.getWidth(); ++i) {
            for (int j = 0; j < writableRandomColorImage.getHeight(); ++j) {
                Color color2 = pixelReader.getColor(i, j);
                double color2Hue = color2.getHue();
                double color2Brightness = color2.getBrightness();
                double color2Saturation = color2.getSaturation();
                double hueDifference = color2Hue - color1Hue;
                double brightnessDifference = color2Brightness - color1Brightness;
                double saturationDifference = color2Saturation - color1Saturation;



                if(hueDifference >= -hueToleranceSlider.getValue() && hueDifference <= hueToleranceSlider.getValue() && saturationDifference >= -saturationToleranceSlider.getValue() && saturationDifference <= saturationToleranceSlider.getValue() && brightnessDifference >=-brightnessToleranceSlider.getValue() && brightnessDifference <= brightnessToleranceSlider.getValue()){
                    pixelWriterRandomColor.setColor(i,j,new Color(randomRed, randomGreen, randomBlue, 1.0)); //this sets the found components to the selected color
                }else pixelWriterRandomColor.setColor(i, j, new Color(1, 1, 1, 1.0));
            }
        }randomColorImage = writableRandomColorImage;

    }

    //maybe try using grey value



    public void disjointSet(){
        if(blackAndWhite != null) {
            int[][] imageArray = new int[(int) blackAndWhite.getWidth()][(int) blackAndWhite.getHeight()];
            for (int x = 0; x < blackAndWhite.getWidth(); x++) {
                for (int y = 0; y < blackAndWhite.getHeight(); y++) {
                    imageArray[x][y] = x + x;
                    System.out.println(imageArray[x][y]);
                }
            }

        }

    }




    //this method works using th color adjust
    public void convertToGreyscaleColorAdjust() {
        if (imageView.getImage() != null) {

            if (convertBox2.isSelected() && !convertBox.isSelected()) {

                ColorAdjust convert = new ColorAdjust();
                convert.setSaturation(-1);
                imageView.setEffect(convert);
            } else {
                ColorAdjust convert = new ColorAdjust();
                convert.setSaturation(0);
                imageView.setEffect(convert);
            }
        }
    }

    public void adjustBrightness() {
        if (imageView.getImage() != null) {
            Image image = imageView.getImage();
            ColorAdjust brightnessAdjust = new ColorAdjust();
            brightnessAdjust.setBrightness(brightnessSlider.getValue());
            imageView.setEffect(brightnessAdjust);
            imageView.setImage(image);
        }
    }

    public void adjustSaturation() {
        if (imageView.getImage() != null) {
            Image image = imageView.getImage();
            ColorAdjust saturationAdjust = new ColorAdjust();
            saturationAdjust.setSaturation(saturationSlider.getValue());
            imageView.setEffect(saturationAdjust);
            imageView.setImage(image);
        }
    }


    public void adjustHue() {
        Image image = imageView.getImage();
        ColorAdjust hueAdjust = new ColorAdjust();
        hueAdjust.setHue(hueSlider.getValue());
        imageView.setEffect(hueAdjust);
        imageView.setImage(image);
    }

    public void switchToRBGChannels(ActionEvent event) throws IOException {
        if (imageView.getImage() != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AlternateViews.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void quit() {
        System.exit(0);
    }



    public void reset() {
        imageView.setFitHeight(image.getHeight());
        imageView.setFitWidth(image.getWidth());

//        imageView.setFitHeight(512);
//        imageView.setFitWidth(512);
        imageView.setImage(image);

    }

    public void resetTolerances(ActionEvent event) {
        hueToleranceSlider.setValue(40);
        saturationToleranceSlider.setValue(0.3);
        brightnessToleranceSlider.setValue(0.2);

    }


}



