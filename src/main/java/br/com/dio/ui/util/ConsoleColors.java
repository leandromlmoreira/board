package br.com.dio.ui.util;

public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    public static String success(String text) {
        return colorize(text, GREEN);
    }
    
    public static String error(String text) {
        return colorize(text, RED);
    }
    
    public static String warning(String text) {
        return colorize(text, YELLOW);
    }
    
    public static String info(String text) {
        return colorize(text, CYAN);
    }
    
    public static String primary(String text) {
        return colorize(text, BLUE);
    }
    
    public static String bold(String text) {
        return colorize(text, BOLD);
    }
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void printHeader(String title) {
        System.out.println();
        System.out.println(ConsoleColors.bold(ConsoleColors.primary("=".repeat(50))));
        System.out.println(ConsoleColors.bold(ConsoleColors.primary(" " + title)));
        System.out.println(ConsoleColors.bold(ConsoleColors.primary("=".repeat(50))));
        System.out.println();
    }
    
    public static void printSeparator() {
        System.out.println(ConsoleColors.primary("-".repeat(50)));
    }
}
