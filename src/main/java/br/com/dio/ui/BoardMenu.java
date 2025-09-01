package br.com.dio.ui;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.persistence.entity.CardEntity;
import br.com.dio.service.BoardColumnQueryService;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.CardQueryService;
import br.com.dio.service.CardService;
import br.com.dio.service.StatisticsService;
import br.com.dio.ui.util.ConsoleColors;
import br.com.dio.ui.util.InputValidator;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner scanner;
    private final BoardEntity entity;

    public BoardMenu(BoardEntity entity) {
        this.scanner = new Scanner(System.in);
        this.entity = entity;
    }

    public void execute() {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("BOARD: " + entity.getName());
        
        System.out.println(ConsoleColors.info("Bem-vindo ao board " + entity.getName() + "! Selecione a operação desejada:"));
        
        var option = -1;
        while (option != 10) {
            try {
                ConsoleColors.printSeparator();
                System.out.println(ConsoleColors.primary("📋 MENU DO BOARD"));
                System.out.println("1️⃣  - Criar um card");
                System.out.println("2️⃣  - Mover um card");
                System.out.println("3️⃣  - Bloquear um card");
                System.out.println("4️⃣  - Desbloquear um card");
                System.out.println("5️⃣  - Cancelar um card");
                System.out.println("6️⃣  - Ver board");
                System.out.println("7️⃣  - Ver coluna com cards");
                System.out.println("8️⃣  - Ver card");
                System.out.println("9️⃣  - Estatísticas do board");
                System.out.println("🔟 - Voltar para o menu anterior");
                System.out.println("❌ - Sair");
                
                System.out.print(ConsoleColors.info("\nEscolha uma opção: "));
                option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> showStatistics();
                    case 10 -> {
                        System.out.println(ConsoleColors.info("🔄 Voltando para o menu anterior..."));
                        return;
                    }
                    case 11 -> {
                        System.out.println(ConsoleColors.success("👋 Obrigado por usar o gerenciador de boards!"));
                        System.exit(0);
                    }
                    default -> System.out.println(ConsoleColors.error("❌ Opção inválida! Escolha uma opção válida."));
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("❌ Opção inválida! Digite um número válido."));
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(ConsoleColors.error("❌ Erro inesperado: " + e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    private void createCard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("CRIAR NOVO CARD");
        
        var card = new CardEntity();
        
        card.setTitle(InputValidator.validateNonEmptyString(scanner, "📝 Título do card: ", "título"));
        card.setDescription(InputValidator.validateDescription(scanner, "📄 Descrição do card: "));
        card.setBoardColumn(entity.getInitialColumn());
        
        try(var connection = getConnection()){
            new CardService(connection).create(card);
            System.out.println(ConsoleColors.success("🎉 Card criado com sucesso!"));
            System.out.println(ConsoleColors.info("ID do card: " + card.getId()));
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void moveCardToNextColumn() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("MOVER CARD");
        
        Long cardId = InputValidator.validatePositiveLong(scanner, "🔄 ID do card a ser movido: ", "ID do card");
        
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        
        try(var connection = getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
            System.out.println(ConsoleColors.success("✅ Card movido com sucesso para a próxima coluna!"));
        } catch (RuntimeException ex){
            System.out.println(ConsoleColors.error("❌ Erro ao mover card: " + ex.getMessage()));
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void blockCard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("BLOQUEAR CARD");
        
        Long cardId = InputValidator.validatePositiveLong(scanner, "🚫 ID do card a ser bloqueado: ", "ID do card");
        String reason = InputValidator.validateNonEmptyString(scanner, "📝 Motivo do bloqueio: ", "motivo do bloqueio");
        
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        
        try(var connection = getConnection()){
            new CardService(connection).block(cardId, reason, boardColumnsInfo);
            System.out.println(ConsoleColors.success("🔒 Card bloqueado com sucesso!"));
        } catch (RuntimeException ex){
            System.out.println(ConsoleColors.error("❌ Erro ao bloquear card: " + ex.getMessage()));
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void unblockCard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("DESBLOQUEAR CARD");
        
        Long cardId = InputValidator.validatePositiveLong(scanner, "🔓 ID do card a ser desbloqueado: ", "ID do card");
        String reason = InputValidator.validateNonEmptyString(scanner, "📝 Motivo do desbloqueio: ", "motivo do desbloqueio");
        
        try(var connection = getConnection()){
            new CardService(connection).unblock(cardId, reason);
            System.out.println(ConsoleColors.success("🔓 Card desbloqueado com sucesso!"));
        } catch (RuntimeException ex){
            System.out.println(ConsoleColors.error("❌ Erro ao desbloquear card: " + ex.getMessage()));
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void cancelCard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("CANCELAR CARD");
        
        Long cardId = InputValidator.validatePositiveLong(scanner, "❌ ID do card a ser cancelado: ", "ID do card");
        
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        
        try(var connection = getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
            System.out.println(ConsoleColors.success("❌ Card cancelado com sucesso!"));
        } catch (RuntimeException ex){
            System.out.println(ConsoleColors.error("❌ Erro ao cancelar card: " + ex.getMessage()));
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void showBoard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("VISUALIZAR BOARD");
        
        try(var connection = getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.println(ConsoleColors.primary("📋 DETALHES DO BOARD"));
                System.out.printf("🆔 ID: %d | 📝 Nome: %s\n", b.id(), b.name());
                System.out.println();
                System.out.println(ConsoleColors.info("📊 COLUNAS:"));
                b.columns().forEach(c ->
                        System.out.printf("📁 %s | 🏷️  Tipo: %s | 📊 Cards: %d\n", 
                            c.name(), c.kind(), c.cardsAmount())
                );
            });
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void showColumn() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("VISUALIZAR COLUNA");
        
        var columnsIds = entity.getBoardColumns().stream().map(BoardColumnEntity::getId).toList();
        Long selectedColumnId = -1L;
        
        while (!columnsIds.contains(selectedColumnId)){
            System.out.println(ConsoleColors.info("Escolha uma coluna do board " + entity.getName() + ":"));
            entity.getBoardColumns().forEach(c -> 
                System.out.printf("🆔 %d - %s [%s]\n", c.getId(), c.getName(), c.getKind()));
            
            selectedColumnId = InputValidator.validatePositiveLong(scanner, "📋 ID da coluna: ", "ID da coluna");
            
            if (!columnsIds.contains(selectedColumnId)) {
                System.out.println(ConsoleColors.error("❌ ID de coluna inválido! Tente novamente."));
            }
        }
        
        try(var connection = getConnection()){
            var column = new BoardColumnQueryService(connection).findById(selectedColumnId);
            column.ifPresent(co -> {
                System.out.println(ConsoleColors.primary("📋 DETALHES DA COLUNA"));
                System.out.printf("📁 Nome: %s | 🏷️  Tipo: %s\n", co.getName(), co.getKind());
                System.out.println();
                
                if (co.getCards().isEmpty()) {
                    System.out.println(ConsoleColors.info("ℹ️  Esta coluna não possui cards."));
                } else {
                    System.out.println(ConsoleColors.info("📊 CARDS NA COLUNA:"));
                    co.getCards().forEach(ca -> {
                        System.out.printf("🆔 %d - %s\n", ca.getId(), ca.getTitle());
                        System.out.printf("📄 Descrição: %s\n", ca.getDescription());
                        ConsoleColors.printSeparator();
                    });
                }
            });
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void showCard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("VISUALIZAR CARD");
        
        Long selectedCardId = InputValidator.validatePositiveLong(scanner, "🔍 ID do card: ", "ID do card");
        
        try(var connection = getConnection()){
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c -> {
                                System.out.println(ConsoleColors.primary("📋 DETALHES DO CARD"));
                                System.out.printf("🆔 ID: %d | 📝 Título: %s\n", c.id(), c.title());
                                System.out.printf("📄 Descrição: %s\n", c.description());
                                System.out.println();
                                
                                if (c.blocked()) {
                                    System.out.println(ConsoleColors.warning("🚫 Status: BLOQUEADO"));
                                    System.out.printf("📝 Motivo: %s\n", c.blockReason());
                                } else {
                                    System.out.println(ConsoleColors.success("✅ Status: ATIVO"));
                                }
                                
                                System.out.printf("📊 Total de bloqueios: %d\n", c.blocksAmount());
                                System.out.printf("📁 Coluna atual: %s (ID: %d)\n", c.columnName(), c.columnId());
                            },
                            () -> System.out.println(ConsoleColors.error("❌ Card com ID " + selectedCardId + " não encontrado!"))
                    );
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }
    
    private void showStatistics() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("ESTATÍSTICAS DO BOARD");
        
        var boardColumnsInfo = entity.getBoardColumns().stream()
                .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind()))
                .toList();
        
        try(var connection = getConnection()){
            var statsService = new StatisticsService(connection);
            statsService.generateBoardStatistics(entity.getId(), boardColumnsInfo);
            statsService.generateColumnStatistics(entity.getId(), boardColumnsInfo);
            statsService.generatePerformanceReport(entity.getId());
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }
}
