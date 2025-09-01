package br.com.dio.service;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.persistence.entity.BoardColumnKindEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.*;

public class StatisticsService {
    private final Connection connection;
    
    public StatisticsService(Connection connection) {
        this.connection = connection;
    }
    
    public void generateBoardStatistics(Long boardId, List<BoardColumnInfoDTO> columns) throws SQLException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 ESTATÍSTICAS DO BOARD");
        System.out.println("=".repeat(60));
        
        System.out.printf("📋 Board ID: %d\n", boardId);
        System.out.printf("📁 Total de Colunas: %d\n", columns.size());
        
        int initialColumns = (int) columns.stream().filter(c -> c.kind() == INITIAL).count();
        int pendingColumns = (int) columns.stream().filter(c -> c.kind() == PENDING).count();
        int finalColumns = (int) columns.stream().filter(c -> c.kind() == FINAL).count();
        int cancelColumns = (int) columns.stream().filter(c -> c.kind() == CANCEL).count();
        
        System.out.printf("🚀 Colunas Iniciais: %d\n", initialColumns);
        System.out.printf("⏳ Colunas Pendentes: %d\n", pendingColumns);
        System.out.printf("✅ Colunas Finais: %d\n", finalColumns);
        System.out.printf("❌ Colunas de Cancelamento: %d\n", cancelColumns);
        
        System.out.println("=".repeat(60));
    }
    
    public void generateColumnStatistics(Long boardId, List<BoardColumnInfoDTO> columns) throws SQLException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 ESTATÍSTICAS POR COLUNA");
        System.out.println("=".repeat(60));
        
        for (BoardColumnInfoDTO column : columns) {
            String columnType = getColumnTypeDisplayName(column.kind());
            System.out.printf("📁 Coluna %d (%s): Ordem %d\n", 
                column.id(), columnType, column.order());
        }
        
        System.out.println("=".repeat(60));
    }
    
    public void generatePerformanceReport(Long boardId) throws SQLException {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🚀 RELATÓRIO DE PERFORMANCE");
        System.out.println("=".repeat(60));
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        System.out.printf("📅 Data do Relatório: %s\n", now.format(formatter));
        System.out.printf("📋 Board ID: %d\n", boardId);
        System.out.println("ℹ️  Estatísticas detalhadas serão implementadas em versões futuras");
        
        System.out.println("=".repeat(60));
    }
    
    private String getColumnTypeDisplayName(BoardColumnKindEnum kind) {
        return switch (kind) {
            case INITIAL -> "Inicial";
            case PENDING -> "Pendente";
            case FINAL -> "Final";
            case CANCEL -> "Cancelamento";
        };
    }
}
