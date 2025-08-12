package com.convenience.util;

//classe para aplicar o mesmo tema em todas janelas do sistema

import javafx.scene.Scene;

public class StyleManager {
    public static void applyDarkTheme(Scene scene) {
        try {
            String cssPath = StyleManager.class
                    .getResource("/com/convenience/styles.css")
                    .toExternalForm();
            if (!scene.getStylesheets().contains(cssPath)) {
                scene.getStylesheets().add(cssPath);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar CSS: " + e.getMessage());
        }
    }
}