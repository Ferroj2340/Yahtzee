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

    // Update the scorecards for the players
    public static int[][] initializeScorecards(int numPlayers) {
        int[][] scorecards = new int[numPlayers][13];
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < 13; j++) {
                scorecards[i][j] = -1;
            }
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

    // Choosing which dice to keep
    public static int[] rerollDice(int[] dice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter which indices of dice to keep (separate it by space, for example '0 2 4'), or press 'Enter' to reroll all: ");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return rollDice();
        }
        String[] indices = input.split(" ");
        for (String indexStr : indices) {
            int index = Integer.parseInt(indexStr);
            dice[index] = rollDice()[index];
        }
        return dice;
    }

    // Print all scorecards
    public static void printScorecards(int[][] scorecards, int numPlayers) {
        System.out.println("Scorecards:");
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            for (int score : scorecards[i]) {
                if (score == -1) {
                    System.out.print("[ ] ");
                } else {
                    System.out.print("[" + score + "] ");
                }
            }
            System.out.println();
        }
    }

    // Calculate score in card
    public static int calculateScore(int[] dice, int category) {
        int score = 0;
        int[] counts = new int[6];
        for (int die : dice) {
            counts[die - 1]++;
        }

        switch (category) {
            case 0:
                score = counts[0] * 1;
                break;
            case 1:
                score = counts[1] * 2;
                break;
            case 2:
                score = counts[2] * 3;
                break;
            case 3:
                score = counts[3] * 4;
                break;
            case 4:
                score = counts[4] * 5;
                break;
            case 5:
                score = counts[5] * 6;
                break;
            case 6:
                for (int count : counts) {
                    if (count >= 3) {
                        score = sumOfDice(dice);
                        break;
                    }
                }
                break;
            case 7:
                for (int count : counts) {
                    if (count >= 4) {
                        score = sumOfDice(dice);
                        break;
                    }
                }
                break;
            case 8:
                boolean threeOfAKind = false, pair = false;
                for (int count : counts) {
                    if (count == 3) threeOfAKind = true;
                    if (count == 2) pair = true;
                }
                if (threeOfAKind && pair) score = 25;
                break;
            case 9:
                if (containsSequence(counts, 4)) score = 30;
                break;
            case 10:
                if (containsSequence(counts, 5)) score = 40;
                break;
            case 11:
                for (int count : counts) {
                    if (count == 5) score = 50;
                }
                break;
            case 12:
                score = sumOfDice(dice);
                break;
        }

        return score;
    }

    // Calculates total of dices
    public static int sumOfDice(int[] dice) {
        int sum = 0;
        for (int die : dice) {
            sum += die;
        }
        return sum;
    }

    // Check if a sequence can be made
    public static boolean containsSequence(int[] counts, int length) {
        int consecutive = 0;
        for (int count : counts) {
            if (count > 0) {
                consecutive++;
                if (consecutive >= length) return true;
            } else {
                consecutive = 0;
            }
        }
        return false;
    }

    // Yahtzee mian setup
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get number of players
        int numPlayers = getNumberOfPlayers();

        // Initialize scorecards
        int[][] scorecards = initializeScorecards(numPlayers);

        // Play for 13 rounds
        for (int round = 0; round < 13; round++) {
            System.out.println("\nRound " + (round + 1) + ":");
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("\nPlayer " + (i + 1) + "'s turn:");
                int[] dice = rollDice();
                displayDice(dice);

                // Reroll up to 3 rolls
                for (int rollNum = 1; rollNum < 3; rollNum++) {
                    System.out.print("Would you like to reroll some of the dice? (enter 'yes' or 'no'): ");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("yes")) {
                        dice = rerollDice(dice);
                        displayDice(dice);
                    } else {
                        break;
                    }
                }

                // Select scoring category
                int category;
                do {
                    System.out.print("Choose a category (0: Ones, 1: Twos, 2: Threes, 3: Fours, 4: Fives, 5: Sixes, 6: Three of a kind, 7: Four of a kind, 8: Full House, 9: Small Straight, 10: Large Straight, 11: Yahtzee, 12: Chance): ");
                    category = scanner.nextInt();
                    scanner.nextLine();
                    if (scorecards[i][category] != -1) {
                        System.out.println("Category has already been used. Please choose another one.");
                    }
                } while (scorecards[i][category] != -1);

                // Update scorecard
                int score = calculateScore(dice, category);
                scorecards[i][category] = score;

                System.out.println("Score for this turn: " + score);
            }

            // Prints scorecard for each round
            printScorecards(scorecards, numPlayers);
        }
        
        // Final results
        System.out.println("\nGame Over! Final Scores:");
        printScorecards(scorecards, numPlayers);
    }
}