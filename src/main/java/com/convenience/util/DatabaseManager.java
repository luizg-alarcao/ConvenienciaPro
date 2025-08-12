package com.convenience.util;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        //Para o programa funcionar na sua máquina crie uma database nova no PostgreSQL
        //Em seguida execute o código para criar a tabela no Postgres, o arquivo está na pasta sql com nome "createTable.sql"
        //Se quiser usar MySQL tem que trocar a dependency no Maven (pom.xml)
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/SeuDatabase"); //Adicione o URL do seu database
        config.setUsername("postgres"); //Aqui adiciona o usuario
        config.setPassword("Senha"); //Aqui adiciona sua senha do database

        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(30000); //30 seg
        config.setIdleTimeout(600000); //10 min
        config.setMaxLifetime(1800000); //30 min
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("ConveniencePool");

        config.setInitializationFailTimeout(-1); //Tentar indefinidamente
        config.setLeakDetectionThreshold(10000); //10 seg

        try {
            dataSource = new HikariDataSource(config);
            System.out.println("Pool de conexões inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao iniciar pool de conexões");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource não foi inicializado corretamente");
        }
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexões fechado com sucesso!");
        }
    }
}
