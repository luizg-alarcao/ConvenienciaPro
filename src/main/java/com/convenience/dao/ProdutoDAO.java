package com.convenience.dao;

import com.convenience.model.Produto;
import com.convenience.model.CategoriaProduto;
import com.convenience.util.DatabaseManager;

import java.sql.*;
import java.util.*;

public class ProdutoDAO implements Dao<Produto, Integer> {

    @Override
    public Optional<Produto> findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do produto inválido");
        }

        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearProduto(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID: " + id, e);
        }
    }

    @Override
    public List<Produto> findAll() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os produtos", e);
        }

        return produtos;
    }

    @Override
    public Produto save(Produto produto) {
        validarProduto(produto);
        String sql = "INSERT INTO produtos (nome, categoria, precoVenda, quantEstoque) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria().name());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQuantEstoque());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar produto, nenhuma linha afetada");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                    return produto;
                }
                throw new SQLException("Falha ao criar produto, nenhum ID obtido");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto update(Produto produto) {
        validarProduto(produto);

        if (produto.getId() == null || produto.getId() <= 0) {
            throw new IllegalArgumentException("ID do produto inválido para atualização");
        }

        String sql = "UPDATE produtos SET nome = ?, categoria = ?, precoVenda = ?, quantEstoque = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria().name());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQuantEstoque());
            stmt.setInt(5, produto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("Produto não encontrado para atualização");
            }

            return produto;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto ID: " + produto.getId(), e);
        }
    }

    @Override
    public void delete(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido para exclusão");
        }
        deleteById(produto.getId());
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão");
        }

        String sql = "DELETE FROM produtos WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new RuntimeException("Produto com ID " + id + " não encontrado");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto ID: " + id, e);
        }
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        try {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setCategoria(CategoriaProduto.valueOf(rs.getString("categoria")));
            produto.setPrecoVenda(rs.getDouble("precoVenda"));
            produto.setQuantEstoque(rs.getInt("quantEstoque"));
            return produto;
        } catch (IllegalArgumentException e) {
            throw new SQLException("Categoria inválida no banco de dados", e);
        }
    }

    private void validarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        if (produto.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria do produto não pode ser nula");
        }
        if (produto.getPrecoVenda() <= 0) {
            throw new IllegalArgumentException("Preço de venda deve ser positivo");
        }
        if (produto.getQuantEstoque() < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }
    }

}