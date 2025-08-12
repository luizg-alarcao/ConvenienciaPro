package com.convenience.model;

public enum CategoriaProduto {
    BEBIDAS("Bebidas"),
    DOCES("Doces"),
    SALGADOS("Salgados"),
    PORCOES("Porções"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
