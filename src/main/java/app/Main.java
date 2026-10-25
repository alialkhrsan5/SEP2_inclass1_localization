package app;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    private static final Locale[] LOCALES = {
            Locale.of("en", "US"),
            Locale.of("fi", "FI"),
            Locale.of("sv", "SE"),
            Locale.of("ja", "JP")
    };

    private final TemperatureConverter converter = new TemperatureConverter();
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    private ResourceBundle messages;
    private Locale locale;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        selectLanguage();
        out.println();
        out.println(messages.getString("app.title"));

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> convertFahrenheitToCelsius();
                case "2" -> convertCelsiusToFahrenheit();
                case "3" -> checkExtremeTemperature();
                case "4" -> running = false;
                default -> out.println(messages.getString("error.invalidChoice"));
            }
            out.println();
        }

        out.println(messages.getString("app.goodbye"));
    }

    private void selectLanguage() {
        out.println("1) English");
        out.println("2) Suomi");
        out.println("3) Svenska");
        out.println("4) 日本語");
        out.print("> ");

        String input = scanner.nextLine().trim();
        locale = LOCALES[0];

        if (input.matches("[1-4]")) {
            locale = LOCALES[Integer.parseInt(input) - 1];
        }

        messages = ResourceBundle.getBundle("MessagesBundle", locale);
    }

    private void showMenu() {
        out.println(messages.getString("menu.1"));
        out.println(messages.getString("menu.2"));
        out.println(messages.getString("menu.3"));
        out.println(messages.getString("menu.4"));
        out.print("> ");
    }

    private void convertFahrenheitToCelsius() {
        Double value = readTemperature();
        if (value == null) {
            return;
        }
        double result = converter.fahrenheitToCelsius(value);
        out.printf(locale, messages.getString("result.celsius") + "%n", result);
    }

    private void convertCelsiusToFahrenheit() {
        Double value = readTemperature();
        if (value == null) {
            return;
        }
        double result = converter.celsiusToFahrenheit(value);
        out.printf(locale, messages.getString("result.fahrenheit") + "%n", result);
    }

    private void checkExtremeTemperature() {
        Double value = readTemperature();
        if (value == null) {
            return;
        }
        boolean extreme = converter.isExtremeTemperature(value);
        out.println(messages.getString(extreme ? "result.extreme.yes" : "result.extreme.no"));
    }

    private Double readTemperature() {
        out.print(messages.getString("prompt.temperature") + " ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            out.println(messages.getString("error.invalidNumber"));
            return null;
        }
    }
}
