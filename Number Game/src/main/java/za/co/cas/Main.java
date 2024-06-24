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
    private static Score gameScore;

    public static void main(String[] args) {
        Random random = new Random();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        gameScore = new Score(0,0);
        do {
            int target = random.nextInt(start -1, end - 1);
            System.out.println("Secret number, between " + start + " and " + end + ", has been generated");
            currentState = GameState.PLAYING;
            int guesses = 5;
            System.out.println("You have " + guesses + " chances to guess.");
            do {
                try {
                    System.out.println("Enter you guess:");
                    String input = inputReader.readLine();
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
                int total = gameScore.total();
                int score = gameScore.score();
                if (currentState == GameState.WON) {
                    score++;
                }
                gameScore = new Score(score, total++);
                if (total != 1) {
                    System.out.println("Score: " + gameScore.score() + " out of " + gameScore.total() + " Games.");
                }
                if (getQuit(inputReader) == 2) break;
            } catch (IOException e){
                break;
            }
        } while (true);
    }

    private static int getQuit(BufferedReader inputReader) throws IOException {
        String input;
        int quit=0;
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
        return quit;
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

record Score(int score, int total) {
    public Score(int score, int total) {
        this.total = total;
        this.score = score;
    }
}