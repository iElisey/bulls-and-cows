package bullscows;

import java.util.*;

public class Main {
    private static final int LIMIT_UP = 10;
    private static List<String> secretNumber;
    private static int cows = 0;
    private static int bulls = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static int attempt = 1;
    private static boolean isEnd = true;
    private static int maxLength = 36;
    private static final String dict = "0123456789abcdefghijklmnopqrstuvwxyz";


    public static void main(String[] args) {
        System.out.println("Please, enter the secret code's length:");

        String lengthStr = scanner.nextLine();
        int length;
        if (!lengthStr.matches("\\d+")) {
            System.out.println("Error: \"" + lengthStr + "\" isn't a valid number.\n");
        } else {
            System.out.println("Input the length of the secret code:");
            int possibleSymbols = scanner.nextInt();
            length = Integer.parseInt(lengthStr);
            if (length > maxLength || length == 0) {
                System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
            } else if (possibleSymbols > maxLength) {
                System.out.printf("Error: maximum number of possible symbols in the code is %d (0-%s, a-%s).%n", possibleSymbols, dict.charAt(9), dict.charAt(maxLength - 1));
            } else if (possibleSymbols < length) {
                System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + possibleSymbols + " unique symbols.");
            } else {
                start(length, possibleSymbols);
            }
        }

    }

    private static void start(int length, int possibleSymbols) {
        secretNumber = randomGenerator(length, possibleSymbols);
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < possibleSymbols; i++) {
            stars.append("*");
        }
        StringBuilder stringBuilder = new StringBuilder("The secret is prepared: " + stars + " ");

        if (possibleSymbols <= 9) stringBuilder.append("(0-" + dict.charAt(possibleSymbols) + ")");
        else if (possibleSymbols == 10) stringBuilder.append("(0-9, a-" + dict.charAt(possibleSymbols) + ")");
        else stringBuilder.append("(0-9, a-" + dict.charAt(possibleSymbols - 1) + ")");

        System.out.println(stringBuilder);
        System.out.println("Okay, let's start a game!");

        while (isEnd) {
            cowsAndBulls();
            attempt++;
        }
    }

    private static List<String> randomGenerator(int size, int possibleSymbols) {
        Random random = new Random();
        Set<String> set = new HashSet<>();
        while (set.size() < size) {
            int i1 = random.nextInt(possibleSymbols);
            set.add(String.valueOf(dict.charAt(i1)));
        }
        return new ArrayList<>(set);
    }

    private static void cowsAndBulls() {
        System.out.println("Turn " + attempt + ":");
        String number = scanner.next();
        String[] numberSplit = number.split("");
        for (int i = 0; i < secretNumber.size(); i++) {
            if (secretNumber.get(i).equals(numberSplit[i])) {
                bulls++;
                continue;
            }
            cows += secretNumber.contains(numberSplit[i]) ? 1 : 0;
        }
        StringBuilder stringBuilder = new StringBuilder("Grade: ");
        if (bulls > 0 && cows > 0) {
            stringBuilder.append(bulls).append(" bull").append(bulls > 1 ? "s" : "").append(" and ").append(cows).append(" cow").append(cows > 1 ? "s" : "");
        } else if (bulls > 0 || cows > 0) {
            stringBuilder.append(bulls > 0 ? bulls + " bull" : cows + " cow").append(bulls > 1 ? "s" : "").append(cows > 1 ? "s" : "");
        } else {
            stringBuilder.append("None");
        }
        System.out.println(stringBuilder);
        if (bulls == secretNumber.size()) {
            System.out.println("Congratulations! You guessed the secret code!");
            isEnd = false;
        }

        cows = 0;
        bulls = 0;
    }
}
