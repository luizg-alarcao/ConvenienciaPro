package com.convenience.controller;

import com.convenience.dao.ProdutoDAO;
import com.convenience.model.Produto;
import com.convenience.model.CategoriaProduto;

import javafx.fxml.FXML;

import javafx.scene.control.*;

public class CadastroProdutoController {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @FXML private TextField txtNome;
    @FXML private ComboBox<CategoriaProduto> cbCategoria;
    @FXML private TextField txtPrecoVenda;
    @FXML private TextField txtQuantEstoque;
    @FXML private Label lblMensagem;

    @FXML
    private void initialize() {
        cbCategoria.getItems().setAll(CategoriaProduto.values());
        cbCategoria.setValue(CategoriaProduto.OUTROS);
        cbCategoria.getStyleClass().add("transition");

        lblMensagem.setText("");
        lblMensagem.getStyleClass().add("mensagem");

        txtNome.getStyleClass().add("transition");
        txtPrecoVenda.getStyleClass().add("transition");
        txtQuantEstoque.getStyleClass().add("transition");
    }

    @FXML
    private void onSalvar() {
        try {
            if (!validarCampos()) return;

            Produto produto = new Produto(null,
                    txtNome.getText().trim(),
                    cbCategoria.getValue(),
                    Double.parseDouble(txtPrecoVenda.getText().replace(",", ".")),
                    Integer.parseInt(txtQuantEstoque.getText())
            );

            Produto produtoSalvo = produtoDAO.save(produto);
            mostrarMensagem("Produto salvo com sucesso! ID: " + produtoSalvo.getId(), "sucesso");
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarMensagem("Valores numéricos inválidos!", "erro");
        } catch (IllegalArgumentException e) {
            mostrarMensagem(e.getMessage(), "erro");
        } catch (Exception e) {
            mostrarMensagem("Erro ao salvar produto", "erro");
            e.printStackTrace();
        }
    }

    @FXML
    private void onLimpar() {
        limparCampos();
    }

    private boolean validarCampos() {
        resetarEstilosErro();
        boolean valido = true;

        if (txtNome.getText().trim().isEmpty()) {
            txtNome.getStyleClass().add("error-field");
            valido = false;
        }

        if (!txtPrecoVenda.getText().matches("\\d+(\\.\\d+)?|\\d+(,\\d+)?")) {
            txtPrecoVenda.getStyleClass().add("error-field");
            valido = false;
        }

        if (!txtQuantEstoque.getText().matches("\\d+")) {
            txtQuantEstoque.getStyleClass().add("error-field");
            valido = false;
        }

        if (!valido) {
            mostrarMensagem("Preencha todos os campos corretamente!", "erro");
        }

        return valido;
    }

    private void limparCampos() {
        txtNome.clear();
        cbCategoria.setValue(CategoriaProduto.OUTROS);
        txtPrecoVenda.clear();
        txtQuantEstoque.clear();
        resetarEstilosErro();
    }

    private void resetarEstilosErro() {
        txtNome.getStyleClass().remove("error-field");
        txtPrecoVenda.getStyleClass().remove("error-field");
        txtQuantEstoque.getStyleClass().remove("error-field");
    }

    private void mostrarMensagem(String mensagem, String tipo) {
        lblMensagem.getStyleClass().removeAll("mensagem-sucesso", "mensagem-erro");
        lblMensagem.getStyleClass().add("mensagem-" + tipo);
        lblMensagem.setText(mensagem);
    }
}