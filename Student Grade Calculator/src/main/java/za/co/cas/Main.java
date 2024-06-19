package za.co.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Grade grade = new Grade();
        boolean done = false;
        String input = "";
        do {
            System.out.println("Do you want random subject? (Y/no)");
            try {
                switch (input = inputReader.readLine().toLowerCase()) {
                    case "y", "yes":
                        System.out.println("");

                        break;
                    case "n", "no":
                        System.out.println("");

                        break;
                }
            } catch (IOException e) {

            }
        } while (!done);

    }

}