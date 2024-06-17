package za.co.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    private static enum GameState {WON, LOST, PLAYING}
    private static GameState currentState;
    private static int start = 1;
    private static int end = 100;

    public static void main(String[] args) {
        Random random = new Random();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        int total = 0;
        int score = 0;
        do {
            int target = random.nextInt(start -1, end - 1);
            System.out.println("Secret number is between " + start + " to " + end + ".");
            System.out.println("Secret number has been generated");
            currentState = GameState.PLAYING;
            int guesses = 5;
            System.out.println("You have " + guesses + " chances to guess.");
            String input;
            do {
                try {
                    System.out.println("Enter you guess:");
                    input = inputReader.readLine();
                    if (!input.matches("\\d+")) {
                        guesses--;
                        throw new IllegalArgumentException();
                    } else {
                        guesses--;
                        currentState = testGuess(target, guesses, Integer.parseInt(input));
                    }
                } catch (IOException | IllegalArgumentException e) {
                    if (e instanceof IllegalArgumentException) {
                        System.out.println("Your guess is not a number.");
                    }
                }
                if (currentState == GameState.PLAYING && guesses == 0) {
                    currentState = GameState.LOST;
                    break;
                } else if (currentState == GameState.WON) {
                    break;
                } else
                    System.out.println("Guesses left: " + guesses);
            } while (true);
            System.out.println("You " + currentState + " the game!");
            try {
                if (currentState == GameState.WON) {
                    score++;
                }
                total++;
                if (total != 1) {
                    System.out.println("Score: " + score + " out of " + total + " Games.");
                }
                int quit = 0;
                while (quit == 0) {
                    System.out.println("Do you want to play again? (Yes/n)");
                    input = inputReader.readLine().toLowerCase();
                    if (input.equals("y") || input.equals("yes")) {
                        quit = 1;
                    } else if (input.equals("n") || input.equals("no")) {
                        quit = 2;
                        break;
                    } else {
                        System.out.println("Please enter YES or no");
                    }
                }
                if (quit==2) break;
            } catch (IOException e){
                break;
            }
        } while (true);
    }

    private static GameState testGuess(int target, int guesses, int guess) {
        if (guess < target) {
            System.out.println("Too Low!");
            return GameState.PLAYING;
        } else if (guess > target) {
            System.out.println("Too High!");
            return GameState.PLAYING;
        } else {
            System.out.println("Correct");
            return GameState.WON;
        }
    }
}