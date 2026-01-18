package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

/**
 * Two-player CLI dice game.
 * Rules:
 * - Two human players alternate turns
 * - On a turn a single 6-sided die is rolled
 * - Player may re-roll up to 2 times (i.e. up to 3 rolls total)
 * - When player ends their turn, the current die value is added to their score
 * - Each player gets 10 turns
 * - Highest score wins
 */
public class App {
    private static final int TURNS_PER_PLAYER = 10;
    private static final int MAX_REROLLS = 2; // up to 2 re-rolls
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Random random = new Random();
            playGame(scanner, random);
        }
    }

    private static void playGame(Scanner scanner, Random random) {
        int[] scores = new int[2];
        System.out.println("Welcome to the 2-player Dice Game!");

        for (int turn = 1; turn <= TURNS_PER_PLAYER; turn++) {
            for (int player = 0; player < 2; player++) {
                System.out.println();
                System.out.println("--- Turn " + turn + " - Player " + (player + 1) + " ---");
                int gained = playTurn(player + 1, scanner, random);
                scores[player] += gained;
                System.out.println("Player " + (player + 1) + " gains " + gained + " points. Total: " + scores[player]);
            }
        }

        printFinalScores(scores);
    }

    private static int playTurn(int playerNumber, Scanner scanner, Random random) {
        int rerollsLeft = MAX_REROLLS;
        int die = rollDie(random);

        while (true) {
            System.out.println("Current roll: " + die + " (rerolls left: " + rerollsLeft + ")");
            if (rerollsLeft == 0) {
                System.out.println("No rerolls left. Keeping " + die + ".");
                break;
            }

            if (!promptReroll(scanner)) {
                break;
            }

            die = rollDie(random);
            rerollsLeft--;
        }

        return die;
    }

    private static boolean promptReroll(Scanner scanner) {
        System.out.print("Re-roll? (y/n): ");
        String choice = scanner.next().trim();
        return choice.equalsIgnoreCase("y");
    }

    static int rollDie(Random random) {
        return random.nextInt(6) + 1;
    }

    /**
     * Add dieValue to currentScore and return new total. Exposed for testing.
     */
    public static int addScore(int currentScore, int dieValue) {
        return currentScore + dieValue;
    }

    private static void printFinalScores(int[] scores) {
        System.out.println();
        System.out.println("=== Final Scores ===");
        System.out.println("Player 1: " + scores[0]);
        System.out.println("Player 2: " + scores[1]);

        if (scores[0] > scores[1]) {
            System.out.println("Player 1 wins!");
        } else if (scores[1] > scores[0]) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}
