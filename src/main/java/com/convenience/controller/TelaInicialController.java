package com.convenience.controller;

import com.convenience.util.StyleManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialController {
    private Stage primaryStage;

    //Metodo para receber a referência do stage
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void abrirCadastroProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/convenience/view/cadastro_produto.fxml"));
            Parent root = loader.load();

            Stage cadastroStage = new Stage();
            cadastroStage.initModality(Modality.APPLICATION_MODAL); //bloqueia a janela principal quando estiver com outra janela aberta
            cadastroStage.initOwner(primaryStage); //define a janela pai

            cadastroStage.setScene(new Scene(root));
            cadastroStage.setTitle("Cadastro de Produtos");
            cadastroStage.showAndWait(); //bloqueia interação com a janela principal

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirEstoque() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/convenience/view/estoque.fxml"));
            Parent root = loader.load();

            Stage estoqueStage = new Stage();

            estoqueStage.setTitle("Controle de Estoque");
            estoqueStage.setScene(new Scene(root));
            estoqueStage.initModality(Modality.APPLICATION_MODAL);
            estoqueStage.show();

        } catch (IOException e) {
            System.err.println("Erro ao abrir estoque.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaVendas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/convenience/view/vendas.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Realizar Venda");
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}