package main.java.com.kowi.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task2Main {

    public static String line = " 52+  10*25/5 -    70/35";
    public static String line1 = " 283746.45-    54*33.33/34/34 + 111*333";
    public static String lineBad = "  7.54 / 23 * 6 - 2 + 1 - 1.91 2389";

    private static List<Double> numbers = new ArrayList<>();
    private static List<Character> operators = new ArrayList<>();


    public static void main(String[] args) {

        myParse(line.trim());
        System.out.println(line + " = " + process());

        numbers = new ArrayList<>();
        operators = new ArrayList<>();
        myParse(line1.trim());
        System.out.println(line1 + " = " + process());

        numbers = new ArrayList<>();
        operators = new ArrayList<>();
        System.out.println("Получаем ошибку: ");
        myParse(lineBad.trim());
    }

    static Double process() {

        // Выполняем операции с бОльшим приоритетом
        for (int i = 0; i < operators.size(); i++) {
            switch (operators.get(i)) {
                case '*':
                    numbers.set(i + 1, numbers.get(i) * numbers.get(i + 1));
                    numbers.set(i, null);
                    operators.set(i, null);
                    break;
                case '/':
                    numbers.set(i + 1, numbers.get(i) / numbers.get(i + 1));
                    numbers.set(i, null);
                    operators.set(i, null);
                    break;
            }
        }

        // Убираем выполненные операции
        numbers = numbers.stream().filter(a -> a != null).collect(Collectors.toList());
        operators = operators.stream().filter(o -> o != null).collect(Collectors.toList());

        // Выполняем операции с меньшим приоритетом
        for (int i = 0; i < operators.size(); i++) {
            switch (operators.get(i)) {
                case '+':
                    numbers.set(i + 1, numbers.get(i) + numbers.get(i + 1));
                    numbers.set(i, null);
                    break;
                case '-':
                    numbers.set(i + 1, numbers.get(i) - numbers.get(i + 1));
                    numbers.set(i, null);
                    break;
            }
        }
        return numbers.get(numbers.size() - 1);
    }

    // Парсим строку в листы чисел и операторов
    static void myParse(String exp) {

        // Паттерны и матчеры для чисел и операторов
        Pattern isNumber = Pattern.compile("[0-9]+(\\.?[0-9]+)*");
        Pattern isOperator = Pattern.compile("[' ']*[/\\*\\+-]{1}[' ']*");
        Matcher numberMatcher = isNumber.matcher(exp);
        Matcher operatorMatcher = isOperator.matcher(exp);
        // Индекс конца последнего найденного фрагмента
        int counter = 0;

        //Пока в строке есть числа
        while (numberMatcher.find()) {
            /*Если строка не начинается с цифры или между последним оператором и числом есть
              посторонние символы, выходим с ошибкой*/
            if (numberMatcher.start() != counter) {
                if (counter == 0) exit("Выражение должно начинаться с цифры!");
                else exit("Некорректное выражение!!");
            }

            numbers.add(Double.valueOf(numberMatcher.group()));
            counter = numberMatcher.end();
            //Если мы не в конце строки, то после числа должен быть оператор, иначе выходим с ошибкой
            if (counter < exp.length() - 1) {
                if (operatorMatcher.find() && operatorMatcher.start() == counter) {
                    operators.add(operatorMatcher.group().trim().charAt(0));
                    counter = operatorMatcher.end();
                } else exit("Некорректное выражение!");
            }
        }
    }

    static void exit(String message) {
        System.out.println(message);
        System.exit(1);
    }

}
