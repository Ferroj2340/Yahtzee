import java.util.Random;

public class Yahtzee {
    public static void main(String[] args) {
        Random random = new Random();
        int numberOfDice = 6;  // Number of dice to roll
        int[] diceRolls = new int[numberOfDice];

        // Rolling the dice
        for (int i = 0; i < numberOfDice; i++) {
            diceRolls[i] = random.nextInt(6) + 1;  // Rolls a 6-sided dice (1-6)
        }

        // Printing the dice rolls
        System.out.println("Dice rolls:");
        for (int roll : diceRolls) {
            System.out.println(roll);
        }
    }
}