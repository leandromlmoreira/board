package br.com.dio.persistence.config;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = ConnectionConfig.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar database.properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        var url = properties.getProperty("db.url", "jdbc:mysql://localhost:3306/board_db");
        var user = properties.getProperty("db.username", "root");
        var password = properties.getProperty("db.password", "");
        var driver = properties.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado: " + e.getMessage());
        }
        
        System.out.println("Conectando ao banco: " + url);
        System.out.println("Usuário: " + user);
        
        var connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
