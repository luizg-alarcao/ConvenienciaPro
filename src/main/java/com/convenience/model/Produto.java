package com.convenience.model;

public class Produto {
    private Integer id;
    private String nome;
    private CategoriaProduto categoria;
    private double precoVenda;
    private int quantEstoque;

    public Produto() {}

    public Produto(Integer id, String nome, CategoriaProduto categoria, double precoVenda, int quantEstoque) {
        setId(id);
        setNome(nome);
        setCategoria(categoria);
        setPrecoVenda(precoVenda);
        setQuantEstoque(quantEstoque);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id < 0) {
            throw new IllegalArgumentException("ID não pode ser negativo.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do Produto não pode ser nulo ou vazio.");
        }
        if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome do Produto não pode conter mais de 100 caracteres.");
        }
        if (!nome.matches("^[\\p{L}\\d\\s-]+$")) {
            throw new IllegalArgumentException("Nome inválido! Utilize apenas letras, números e espaços.");
        }
        this.nome = nome.trim();
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula");
        }
        this.categoria = categoria;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        if (precoVenda <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.precoVenda = precoVenda;
    }

    public int getQuantEstoque() {
        return quantEstoque;
    }

    public void setQuantEstoque(int quantEstoque) {
        if (quantEstoque < 0) {
            throw new IllegalArgumentException("Quantidade de estoque não pode ser menor que zero.");
        }
        this.quantEstoque = quantEstoque;
    }
}