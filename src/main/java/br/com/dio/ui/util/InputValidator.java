package br.com.dio.ui.util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\s]{2,50}$");
    
    public static String validateNonEmptyString(Scanner scanner, String prompt, String fieldName) {
        String input;
        do {
            System.out.print(ConsoleColors.info(prompt));
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println(ConsoleColors.error("O campo " + fieldName + " não pode estar vazio!"));
            }
        } while (input.isEmpty());
        
        return input;
    }
    
    public static String validateName(Scanner scanner, String prompt, String fieldName) {
        String input;
        do {
            input = validateNonEmptyString(scanner, prompt, fieldName);
            
            if (!NAME_PATTERN.matcher(input).matches()) {
                System.out.println(ConsoleColors.error("O campo " + fieldName + " deve conter apenas letras e espaços (2-50 caracteres)!"));
                input = "";
            }
        } while (input.isEmpty());
        
        return input;
    }
    
    public static int validatePositiveInteger(Scanner scanner, String prompt, String fieldName) {
        int value;
        do {
            System.out.print(ConsoleColors.info(prompt));
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println(ConsoleColors.error("O campo " + fieldName + " deve ser um número positivo!"));
                    value = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Por favor, informe um número válido para " + fieldName + "!"));
                value = -1;
            }
        } while (value < 0);
        
        return value;
    }
    
    public static long validatePositiveLong(Scanner scanner, String prompt, String fieldName) {
        long value;
        do {
            System.out.print(ConsoleColors.info(prompt));
            try {
                value = Long.parseLong(scanner.nextLine().trim());
                if (value <= 0) {
                    System.out.println(ConsoleColors.error("O campo " + fieldName + " deve ser um número maior que zero!"));
                    value = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.error("Por favor, informe um número válido para " + fieldName + "!"));
                value = -1;
            }
        } while (value <= 0);
        
        return value;
    }
    
    public static boolean validateYesNo(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(ConsoleColors.info(prompt + " (s/n): "));
            input = scanner.nextLine().trim().toLowerCase();
            
            if (!input.equals("s") && !input.equals("n") && !input.equals("sim") && !input.equals("não")) {
                System.out.println(ConsoleColors.error("Por favor, responda com 's' para sim ou 'n' para não!"));
            }
        } while (!input.equals("s") && !input.equals("n") && !input.equals("sim") && !input.equals("não"));
        
        return input.equals("s") || input.equals("sim");
    }
    
    public static String validateDescription(Scanner scanner, String prompt) {
        String input = validateNonEmptyString(scanner, prompt, "descrição");
        
        if (input.length() > 500) {
            System.out.println(ConsoleColors.warning("A descrição foi truncada para 500 caracteres."));
            input = input.substring(0, 500);
        }
        
        return input;
    }
    
    public static void waitForEnter(Scanner scanner, String message) {
        System.out.println(ConsoleColors.info(message));
        scanner.nextLine();
    }
}
