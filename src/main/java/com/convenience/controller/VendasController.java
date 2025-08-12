package com.convenience.controller;

import com.convenience.dao.ProdutoDAO;
import com.convenience.model.Produto;
import com.convenience.util.StyleManager;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class VendasController {

    @FXML
    private FlowPane painelComandas;

    private int contadorComandas = 1;
    private String atendentePadrao = "Atendente 1";

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final List<Produto> produtosDisponiveis = new ArrayList<>();

    public void initialize() {
        produtosDisponiveis.addAll(produtoDAO.findAll());
    }

    @FXML
    private void criarComanda() {
        VBox comandaBox = new VBox(10);
        comandaBox.setPadding(new Insets(10));
        comandaBox.setStyle("-fx-background-color: #3d4a52; -fx-padding: 10; -fx-background-radius: 8;");
        comandaBox.setPrefWidth(300);

        TextField nomeComanda = new TextField("Comanda " + contadorComandas++);
        nomeComanda.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        nomeComanda.setPromptText("Nome da comanda");

        TextField campoMesa = new TextField();
        campoMesa.setPromptText("Número da mesa");

        TextField campoAtendente = new TextField();
        campoAtendente.setPromptText("Nome do atendente");

        if (!atendentePadrao.isEmpty()) {
            campoAtendente.setText(atendentePadrao);
        }

        campoAtendente.textProperty().addListener((obs, oldVal, newVal) -> {
            atendentePadrao = newVal;
        });

        ListView<String> listaItens = new ListView<>();
        listaItens.setPrefHeight(150);

        Label labelTotal = new Label("Total: R$ 0,00");
        labelTotal.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ComboBox<Produto> comboProdutos = new ComboBox<>();
        comboProdutos.getItems().addAll(produtosDisponiveis);
        comboProdutos.setPromptText("Selecione um produto");
        comboProdutos.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Produto produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                } else {
                    setText(produto.getNome() + " - R$" + String.format("%.2f", produto.getPrecoVenda()));
                }
            }
        });
        comboProdutos.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Produto produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                } else {
                    setText(produto.getNome() + " - R$" + String.format("%.2f", produto.getPrecoVenda()));
                }
            }
        });

        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> {
            Produto selecionado = comboProdutos.getValue();
            if (selecionado != null) {
                String item = selecionado.getNome() + " - R$" + String.format("%.2f", selecionado.getPrecoVenda());
                listaItens.getItems().add(item);
                atualizarTotal(listaItens, labelTotal);
            }
        });

        Button btnRemover = new Button("Remover");
        btnRemover.setOnAction(e -> {
            int index = listaItens.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                listaItens.getItems().remove(index);
                atualizarTotal(listaItens, labelTotal);
            }
        });

        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Comanda Finalizada");
            alert.setHeaderText("Comanda fechada com sucesso!");
            alert.setContentText("Total: " + labelTotal.getText());
            StyleManager.applyDarkTheme(alert.getDialogPane().getScene());
            alert.showAndWait();

            painelComandas.getChildren().remove(comandaBox);
        });

        HBox botoes = new HBox(10, btnAdicionar, btnRemover, btnFechar);
        botoes.setPadding(new Insets(5));

        comandaBox.getChildren().addAll(nomeComanda, campoMesa, campoAtendente, comboProdutos, listaItens, labelTotal, botoes);
        painelComandas.getChildren().add(comandaBox);
    }

    private void atualizarTotal(ListView<String> lista, Label labelTotal) {
        double total = 0.0;

        for (String item : lista.getItems()) {
            if (item.contains("R$")) {
                try {
                    String valorStr = item.substring(item.indexOf("R$") + 2).replace(",", ".").trim();
                    total += Double.parseDouble(valorStr);
                } catch (Exception ignored) {}
            }
        }

        labelTotal.setText(String.format("Total: R$ %.2f", total));
    }
}
