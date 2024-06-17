package za.co.cas;

import java.util.HashMap;
import java.util.Random;

public class Grade {
    private HashMap<Subject, Double> marks = new HashMap<>();
    private double average = 0;
    private double grade = 0;
    private double sum = 0;
    private double total = 0;

    public void randomSubjects(int number) {
        Random random = new Random();
        Subject newSubject;
        do {
            int pick = random.nextInt(Subject.values().length);
            newSubject = Subject.values()[pick];
            double grade = random.nextDouble() * 100;
            if (!marks.containsKey(newSubject)) {
                marks.put(newSubject, grade);
            }
        } while (marks.size() < number);
        setAverage();
    }

    private void setAverage() {
        double sum = 0;
        int count = 0;
        int total = 0;
        for (Subject key : marks.keySet()) {
            count++;
            sum += marks.get(key);
            total += 100;
        }

        if (Double.isNaN((this.average = sum / count)))
            this.average = 0;
        this.sum = sum;
        this.total = total;
        this.grade = (average / 100) * 5;

//        return this.average;
    }

    public boolean addSubject(Subject subject) {
        if (marks.containsKey(subject))
            return false;
        else {
            marks.put(subject, 0d);
            return true;
        }
    }

    public boolean addSubject(Subject subject, double grade) {
        if (marks.containsKey(subject))
            return false;
        else {
            marks.put(subject, grade);
            return true;
        }
    }

    public String tabulate() {
        StringBuilder output = new StringBuilder();
        output.append("|           Grades          |        |\n");
        output.append("--------------------------------------\n");
        for (Subject subject : marks.keySet()) {
            output.append(String.format("| %-25s | %6s |\n", subject, String.format("%3d%s" , marks.get(subject).intValue(), '%')));
        }
        output.append("--------------------------------------\n");
        output.append(String.format("| %25s | %6.2f |\n", "Score", sum));
        output.append(String.format("| %25s | %6.2f |\n", "Total", total));
        output.append(String.format("| %25s | %6.2f |\n", "Average", average));
        output.append(String.format("| %25s | %6.2f |\n", "Grade", grade));
        output.append("--------------------------------------\n");



        return output.toString();
    }


    @Override
    public String toString() {
        return "Grade{" +
                "average=" + average +
                ", grade=" + grade +
                ", total=" + total +
                ", marks=" + marks +
                '}';
    }
}