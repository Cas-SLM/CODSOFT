package za.co.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        boolean done = false;
        String input = "";
        String fullName = "";
        String[] name = new String[0];
        do {
            try {
                System.out.println("Enter your Full Name: ");
                fullName = inputReader.readLine();
                name = fullName.split(" ");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (name.length < 2);

        Grade grade = new Grade(fullName);
        System.out.println(grade.tabulate());
        String subject = "";
        Double amount = 0d;
        try {
            do {
                try {
                    System.out.print("Enter a subject: ");
                    input = inputReader.readLine();
                    if (!input.matches("\\w+")) throw new IllegalArgumentException();
                    else if (input.toLowerCase().equals("quit")) throw new NullPointerException();
                    else {
                        subject = input;
//                        grade.addSubject(subject);
                        break;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    continue;
                }
            } while (!done);
            do {
                try {
                    System.out.print("Enter mark for " + subject + ": ");
                    input = inputReader.readLine();
                    if (!input.matches("(\\d+)?(\\d+.\\d+)|(\\d+)")) throw new IllegalArgumentException();
                    else if (input.toLowerCase().equals("quit")) throw new NullPointerException();
                    else {
                        amount = Double.parseDouble(input);
                        if (amount < 0 || amount > 100) throw new IllegalArgumentException();
                        break;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    continue;
                }
            } while (!done);
            grade.addSubject(subject, amount);
        } catch (NullPointerException e) {
            //
        }
        System.out.println(grade);
        System.out.println(grade.tabulate());

    }
}