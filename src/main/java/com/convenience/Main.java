package com.convenience;

import com.convenience.controller.TelaInicialController;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/convenience/view/tela_inicial.fxml"));
        Parent root = loader.load();

        TelaInicialController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(300);
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);

        primaryStage.setTitle("Sistema de Conveniência - ConveniênciaPro");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}