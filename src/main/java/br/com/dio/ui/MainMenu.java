package br.com.dio.ui;

import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;
import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.service.BoardQueryService;
import br.com.dio.service.BoardService;
import br.com.dio.ui.util.ConsoleColors;
import br.com.dio.ui.util.InputValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.dio.persistence.config.ConnectionConfig.getConnection;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.INITIAL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.PENDING;

public class MainMenu {

    private final Scanner scanner;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void execute() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("GERENCIADOR DE BOARDS");
        
        System.out.println(ConsoleColors.info("Bem-vindo ao gerenciador de boards! Escolha a opção desejada:"));
        
        boolean running = true;
        while (running) {
            try {
                ConsoleColors.printSeparator();
                System.out.println(ConsoleColors.primary("📋 MENU PRINCIPAL"));
                System.out.println("1️⃣  - Criar um novo board");
                System.out.println("2️⃣  - Selecionar um board existente");
                System.out.println("3️⃣  - Excluir um board");
                System.out.println("4️⃣  - Sair");
                
                System.out.print(ConsoleColors.info("\nEscolha uma opção: "));
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println(ConsoleColors.error("❌ Opção não pode estar vazia!"));
                    continue;
                }
                
                int option;
                try {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(ConsoleColors.error("❌ Opção inválida! Digite um número de 1 a 4."));
                    continue;
                }
                
                switch (option) {
                    case 1 -> createBoard();
                    case 2 -> selectBoard();
                    case 3 -> deleteBoard();
                    case 4 -> {
                        System.out.println(ConsoleColors.success("👋 Obrigado por usar o gerenciador de boards!"));
                        running = false;
                    }
                    default -> System.out.println(ConsoleColors.error("❌ Opção inválida! Escolha uma opção de 1 a 4."));
                }
            } catch (Exception e) {
                System.out.println(ConsoleColors.error("❌ Erro inesperado: " + e.getMessage()));
                e.printStackTrace();
                running = false;
            }
        }
    }

    private void createBoard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("CRIAR NOVO BOARD");
        
        var entity = new BoardEntity();
        
        System.out.println(ConsoleColors.info("Vamos criar seu novo board! 🚀"));
        
        entity.setName(InputValidator.validateName(scanner, "📝 Nome do board: ", "nome"));
        
        int additionalColumns = InputValidator.validatePositiveInteger(scanner, 
            "📊 Quantas colunas adicionais além das 3 padrões? (0 para nenhuma): ", "número de colunas");

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println(ConsoleColors.info("\n📋 Configurando colunas padrão:"));
        
        String initialColumnName = InputValidator.validateName(scanner, "🚀 Nome da coluna inicial: ", "nome da coluna inicial");
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            String pendingColumnName = InputValidator.validateName(scanner, 
                String.format("⏳ Nome da coluna pendente %d: ", i + 1), "nome da coluna pendente");
            var pendingColumn = createColumn(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        String finalColumnName = InputValidator.validateName(scanner, "✅ Nome da coluna final: ", "nome da coluna final");
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        String cancelColumnName = InputValidator.validateName(scanner, "❌ Nome da coluna de cancelamento: ", "nome da coluna de cancelamento");
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
            System.out.println(ConsoleColors.success("🎉 Board criado com sucesso!"));
            System.out.println(ConsoleColors.info("ID do board: " + entity.getId()));
        }

        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private void selectBoard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("SELECIONAR BOARD");
        
        Long id = InputValidator.validatePositiveLong(scanner, "🔍 ID do board: ", "ID do board");
        
        try(var connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            if (optional.isPresent()) {
                new BoardMenu(optional.get()).execute();
            } else {
                System.out.println(ConsoleColors.error("❌ Board com ID " + id + " não encontrado!"));
                InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
            }
        }
    }

    private void deleteBoard() throws SQLException {
        ConsoleColors.clearScreen();
        ConsoleColors.printHeader("EXCLUIR BOARD");
        
        Long id = InputValidator.validatePositiveLong(scanner, "🗑️  ID do board a ser excluído: ", "ID do board");
        
        boolean confirm = InputValidator.validateYesNo(scanner, "⚠️  Tem certeza que deseja excluir este board? Esta ação não pode ser desfeita!");
        
        if (!confirm) {
            System.out.println(ConsoleColors.info("✅ Operação cancelada pelo usuário."));
            InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
            return;
        }
        
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            if (service.delete(id)){
                System.out.println(ConsoleColors.success("✅ Board " + id + " excluído com sucesso!"));
            } else {
                System.out.println(ConsoleColors.error("❌ Board com ID " + id + " não encontrado!"));
            }
        }
        
        InputValidator.waitForEnter(scanner, "Pressione ENTER para continuar...");
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
