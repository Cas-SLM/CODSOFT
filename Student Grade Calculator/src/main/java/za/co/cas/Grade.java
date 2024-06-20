package za.co.cas;

import java.util.HashMap;
import java.util.Random;

public class Grade {
    private final String name;
//    private final HashMap<Subject, Double> marks;
    private final HashMap<String, Double> marks;
    private double average;// = 0;
    private double grade;// = 0;
    private double sum;// = 0;
    private double total;// = 0;

    public Grade(String fullname) {
        this.name = fullname;
        this.marks = new HashMap<>();
        this.average = 0;
        this.grade = 0;
        this.sum = 0;
        this.total = 0;
    }

    public void randomSubjects(int number) {
        Random random = new Random();
        Subject newSubject;
        do {
            int pick = random.nextInt(Subject.values().length);
            newSubject = Subject.values()[pick];
            double grade = random.nextDouble() * 100;
//            addSubject(newSubject, grade);
            addSubject(newSubject.toString(), grade);
        } while (getMarks().size() < number);
    }

    private void calculateAll() {
        double sum = 0;
        int count = 0;
        int total = 0;
//        for (Subject key : getMarks().keySet()) {
        for (String key : getMarks().keySet()) {
            count++;
            sum += getMarks().get(key);
            total += 100;
        }

        if (Double.isNaN((this.average = sum / count)))
            this.average = 0;
        this.sum = sum;
        this.total = total;
        this.grade = (getAverage() / 100) * 5;
    }

//    public boolean addSubject(Subject subject) {
    public boolean addSubject(String subject) {
        if (getMarks().containsKey(subject))
            return false;
        else {
            getMarks().put(subject, 0d);
            calculateAll();
            return true;
        }
    }

//    public boolean addSubject(Subject subject, double grade) {
    public boolean addSubject(String subject, double grade) {
        if (getMarks().containsKey(subject))
            return false;
        else {
            getMarks().put(subject, grade);
            calculateAll();
            return true;
        }
    }
//    public boolean addMark(Subject subject, double grade) {
    public boolean addMark(String subject, double grade) {
        if (!getMarks().containsKey(subject))
            return false;
        else {
            getMarks().put(subject, grade);
            calculateAll();
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public double getAverage() {
        return average;
    }

    public double getTotal() {
        return total;
    }

    private double getSum() {
        return sum;
    }

//    private HashMap<Subject, Double> getMarks() {
    private HashMap<String, Double> getMarks() {
        return marks;
    }

    public String tabulate() {
        StringBuilder output = new StringBuilder();
        output.append("┌───────────────────────────┬─────────┐\n");
        output.append("│           Grades          │         │\n");
        output.append("├─────────┼───────────────────────────┤\n");
        output.append(String.format("│ %7s │ %25s │\n", "Name", getName()));
        output.append("├─────────┼───────────────────────────┤\n");
//        for (Subject subject : getMarks().keySet()) {
        for (String subject : getMarks().keySet()) {
            output.append(String.format("│ %-25s │ %7s │\n", subject, String.format("%3d%s" , getMarks().get(subject).intValue(), '%')));
        }
        output.append("├───────────────────────────┼─────────┤\n");
        output.append(String.format("│ %25s │ %7.2f │\n", "Score", getSum()));
        output.append(String.format("│ %25s │ %7.2f │\n", "Total", getTotal()));
        output.append(String.format("│ %25s │ %7.2f │\n", "Average", getAverage()));
        output.append(String.format("│ %25s │ %7.2f │\n", "Grade", getGrade()));
        output.append("└───────────────────────────┴─────────┘\n");



        return output.toString();
    }


    @Override
    public String toString() {
        return "Grade{" +
                "average=" + getAverage() +
                ", grade=" + getGrade() +
                ", total=" + getTotal() +
                ", marks=" + getMarks() +
                '}';
    }
}