package com.convenience.controller;

import com.convenience.dao.ProdutoDAO;
import com.convenience.model.Produto;
import com.convenience.util.StyleManager;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class EstoqueController {
    @FXML
    private TableView<Produto> tabelaEstoque;
    @FXML
    private TableColumn<Produto, Integer> colId;
    @FXML
    private TableColumn<Produto, String> colNome;
    @FXML
    private TableColumn<Produto, String> colCategoria;
    @FXML
    private TableColumn<Produto, Double> colPreco;
    @FXML
    private TableColumn<Produto, Integer> colEstoque;
    @FXML
    private TableColumn<Produto, Void> colAcoes;
    @FXML
    private TextField txtBusca;
    @FXML
    private Label lblTotal;


    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantEstoque"));

        atualizarTabela();
        addColunaAcoes();
    }

    private void atualizarTabela() {
        tabelaEstoque.getItems().setAll(produtoDAO.findAll());
        atualizarContador();
    }

    private void atualizarContador() {
        lblTotal.setText("Total de produtos: " + tabelaEstoque.getItems().size());
    }

    @FXML
    private void onBuscar() {
        String termo = txtBusca.getText().trim().toLowerCase();

        if (termo.isEmpty()) {
            atualizarTabela();
        } else {
            tabelaEstoque.getItems().setAll(
                    produtoDAO.findAll().stream()
                            .filter(p -> p.getNome().toLowerCase().contains(termo))
                            .toList()
            );
            atualizarContador();
        }
    }

    @FXML
    private void onAtualizar() {
        atualizarTabela();
    }

    private void addColunaAcoes() {
        colAcoes.setCellFactory(coluna -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");

            {
                btnEditar.getStyleClass().add("btn-editar");
                btnExcluir.getStyleClass().add("btn-excluir");

                btnExcluir.setOnAction(e -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    boolean confirmacao = mostrarConfirmacao("Excluir", "Deseja realmente excluir o produto '" + produto.getNome() + "'?");
                    if (confirmacao) {
                        produtoDAO.deleteById(produto.getId());
                        atualizarTabela();
                    }
                });

                btnEditar.setOnAction(e -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    System.out.println("Editar produto: " + produto.getNome());
                    //preciso implementar a função de editar produto, aqui está apenas o botão...
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);

                if (vazio) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(8, btnEditar, btnExcluir);
                    setGraphic(box);
                }
            }
        });
    }

    private boolean mostrarConfirmacao(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        ButtonType sim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.NO);

        alerta.getButtonTypes().setAll(sim, nao);

        StyleManager.applyDarkTheme(alerta.getDialogPane().getScene());

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == sim;
    }

}
