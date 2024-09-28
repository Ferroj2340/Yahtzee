import java.util.Scanner;
import java.util.Random;

public class Yahtzee {

    // Num of players
    public static int getNumberOfPlayers() {
        Scanner scanner = new Scanner(System.in);
        int numPlayers;
        do {
            System.out.print("What is the number of players (1 to 6): ");
            numPlayers = scanner.nextInt();
            if (numPlayers < 1 || numPlayers > 6) {
                System.out.println("Invalid number of players");
            }
        } while (numPlayers < 1 || numPlayers > 6);
        return numPlayers;
    }

    public static String[] initializeScorecards(int numPlayers) {
        String[] scorecards = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            scorecards[i] = "Player " + (i + 1) + ": [ ]";
        }
        return scorecards;
    }

    // Roll a number between 1 and 6
    public static int[] rollDice() {
        Random rand = new Random();
        int[] dice = new int[5];
        for (int i = 0; i < 5; i++) {
            dice[i] = rand.nextInt(6) + 1; 
        }
        return dice;
    }
    // Print dice roll
    public static void displayDice(int[] dice) {
        System.out.print("Current roll: ");
        for (int die : dice) {
            System.out.print(die + " ");
        }
        System.out.println();
    }

    // update the scorecards for the players
    public static void updateScorecard(int player, String[] scorecards, String score) {
        scorecards[player] = "Player " + (player + 1) + ": " + score;
    }

    // Print all scorecards
    public static void printScorecards(String[] scorecards) {
        for (String scorecard : scorecards) {
            System.out.println(scorecard);
        }
    }
}