package za.co.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    private static final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    private static String[] name;
    private static ArrayList<String> subjects;

    public static void main(String[] args) {
        boolean done = false;
        String input;
        String fullName = "";
        name = new String[0];
//        fullName = getName(fullName);

        Grade grade = new Grade("Riri Momo");//fullName);
        String subject;
        subjects = new ArrayList<>() {{
            for (Subject subject : Subject.values()) {
                add(subject.name());
            }
        }};
        double amount;
        int subjectNumber = getSubjectNumber();
        for (int i = 1; i <= subjectNumber; i++) {
            try {
                subject = getSubject();
                amount = getMark(subject);
                grade.addSubject(subject, amount);
            } catch (NullPointerException e) {
                //
            }
        }
        do {
            try {
                System.out.print("What do you want to do next: ");
                input = inputReader.readLine().strip();
                switch (input.toLowerCase()) {
                    case "quit":
                        done = true;
                        break;
                    case "average":
                        System.out.printf("Average: %.2f%%%n", grade.getAverage());
                        break;
                    case "sum":
                        System.out.printf("Sum: %.2f out of %.2f%n",grade.getTotal()*grade.getAverage(), grade.getTotal());
                        break;
                    case "grade":
                        System.out.printf("Grade: %.2f%n", grade.getGrade());
                        break;
                    case "grades":
                        System.out.println(grade.tabulate());
                        break;
                    case "help":
                        String help = "average - shows your average.\n" +
                                "grades - shows a table of your results.\n" +
                                "grade - shows your grade.\n" +
                                "help - shows commands available.\n" +
                                "quit - exit the app.\n" +
                                "sum - sum of all grades. Eg. 43 out of 60.";
                        System.out.println(help);
                        break;
                    default:
                        System.out.printf("Doing %s%n", input);
                }
            } catch (IOException e) {
                continue;
            }
        } while (!done);
    }

    private static int getSubjectNumber() throws NullPointerException {
        String input;
        try {
            System.out.print("How many subjects do you have: ");
            input = inputReader.readLine().strip();
            if (input.equalsIgnoreCase("quit")) throw new NullPointerException();
            else if (!input.matches("\\d+")) throw new IllegalArgumentException();
            else return Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a number!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private static String getName(String fullName){
        do {
            try {
                System.out.print("Enter your Full Name: ");
                fullName = inputReader.readLine().strip();
                name = fullName.split(" ");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (name.length < 2);
        return fullName;
    }

    private static String getSubject() throws NullPointerException {
        String subject;
        String input;
        do {
            try {
                int i = 1;
                for (String sub : subjects) {
                    System.out.printf("%d: %s%n", i, sub);
                    i++;
                }
                System.out.print("Enter a subject: ");
                input = inputReader.readLine().strip();
                if (input.matches("\\d+")) {
                    return subjects.remove(Integer.parseInt(input) -1);
                } else if (!input.matches("(\\w+ \\w+)|(\\w+)")) throw new IllegalArgumentException("Subject should be alphanumeric.");
                else if (input.equalsIgnoreCase("quit")) throw new NullPointerException();
                else {
                    input = input.replaceFirst("\\s+", "_").toUpperCase();
                    if (subjects.contains(input)) {
                        subjects.remove(input);
                        return input;
                    } else throw new IllegalArgumentException("Invalid subject, choose from the list.");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please choose a number or subject in the list.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        } while (true);
    }

    private static double getMark(String subject) {
        String input;
        double amount;
        do {
            try {
                System.out.print("Enter mark for " + subject + ": ");
                input = inputReader.readLine().strip();
                if (!input.matches("(\\d+)?(\\d+.\\d+)|(\\d+)")) throw new IllegalArgumentException("Please enter an number!");
                else if (input.equalsIgnoreCase("quit")) throw new NullPointerException();
                else {
                    amount = Double.parseDouble(input);
                    if (amount < 0 || amount > 100) throw new IllegalArgumentException("Enter a grade between 0 - 100");
                    break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
        } while (true);
        return amount;
    }
}