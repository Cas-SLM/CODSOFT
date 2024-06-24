package za.co.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static final BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    private static String[] name;
    private static ArrayList<Subject> subjects;

    public static void main(String[] args) {
        boolean done = false;
        String input;
        String fullName = "Riri Momo";
        name = new String[0];
//        fullName = getName(fullName);

        Grade grade = new Grade(fullName);
        Subject subject;
        subjects = new ArrayList<>() {{
            this.addAll(Arrays.asList(Subject.values()));
        }};
        double amount;
        int subjectNumber = getSubjectNumber();
        for (int i = 1; i <= subjectNumber; i++) {
            try {
                subject = getSubject();
                amount = getMark(subject);
                grade.addSubject(subject, amount);
            } catch (NullPointerException ignored) {
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
                        System.out.printf("Sum: %.2f out of %.2f%n",grade.getSum(), grade.getTotal());
                        break;
                    case "grade":
                        System.out.printf("Grade: %.2f%n", grade.getGrade());
                        break;
                    case "grades":
                        System.out.println(grade.tabulate());
                        break;
                    case "help":
                        String help = """
                                average - shows your average.
                                grades - shows a table of your results.
                                grade - shows your grade.
                                help - shows commands available.
                                quit - exit the app.
                                sum - sum of all grades. Eg. 43 out of 60.""";
                        System.out.println(help);
                        break;
                    default:
                        System.out.printf("Doing %s%n", input);
                }
            } catch (IOException ignored) {
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

    private static Subject getSubject() throws NullPointerException {
        String input;
        do {
            try {
                int i = 1;
                for (Subject sub : subjects) {
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
                    if (subjects.contains(Subject.valueOf(input))) {
                        subjects.remove(Subject.valueOf(input));
                        return Subject.valueOf(input);
                    } else throw new IllegalArgumentException("Invalid subject, choose from the list.");
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please choose a number or subject in the list.");
            }
        } while (true);
    }

    private static double getMark(Subject subject) {
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
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        return amount;
    }
}