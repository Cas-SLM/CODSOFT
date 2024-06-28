package za.co.cas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Grade {
//    private final String name;
    private Map<Subject, Integer> subjectsAndMarks;
    private double average;
    private double grade;
    private double sum;
    private double total;
    private final Mapper mapper;

//    public Grade(String fullname) {
    public Grade() {
//        this.name = fullname;
        this.subjectsAndMarks = new HashMap<>();
        this.average = 0;
        this.grade = 0;
        this.sum = 0;
        this.total = 0;
        this.mapper = new Mapper("/home/cas/Documents/CODSOFT/Student Grade Calculator/src/main/java/za/co/cas/Grades.json");
    }

    private void calculateAll() {
        double sum = 0;
        int count = 0;
        int total = 0;
        for (Subject key : getSubjectsAndMarks().keySet()) {
            count++;
            sum += getSubjectsAndMarks().get(key);
            total += 100;
        }

        if (Double.isNaN((this.average = sum / count)))
            this.average = 0;
        this.sum = sum;
        this.total = total;
        this.grade = (getAverage() / 100) * 5;
    }

    public void addSubject(Subject subject, double mark) {
        if (!getSubjectsAndMarks().containsKey(subject)) {
            getSubjectsAndMarks().put(subject, (int) mark);
            calculateAll();
        }

    }

    public void removeSubject(Subject subject) {
        if (getSubjectsAndMarks().containsKey(subject)) {
            this.subjectsAndMarks.remove(subject);
            calculateAll();
        }
    }

    public void addMark(Subject subject, double grade) {
        if (getSubjectsAndMarks().containsKey(subject)) {
            getSubjectsAndMarks().put(subject, (int) grade);
            calculateAll();
        }
    }

    public boolean hasSubject(Subject subject) {
        return getSubjectsAndMarks().containsKey(subject);
    }

    public int getMark(Subject subject) {
        return getSubjectsAndMarks().get(subject);
    }

    public Map<Subject, Integer> getSubjectsAndMarks() {
        return subjectsAndMarks;
    }

    public void setSubjectsAndMarks(Map<Subject, Integer> subjects) {
        this.subjectsAndMarks = subjects;
    }

    public ArrayList<Subject> Subjects() {
        ArrayList<Subject> subjects1 = new ArrayList<>(subjectsAndMarks.keySet());
        subjects1.sort(new Comparator<Subject>() {
            @Override
            public int compare(Subject subject1, Subject subject2) {
                return subject1.compareTo(subject2);
            }
        });
        return subjects1;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void save() {
        this.mapper.save(this);
    }

    public String toJSON() {
        return this.mapper.map(this);
    }

    public String tabulate() {
        StringBuilder output = new StringBuilder();
        output.append("┌──────────────────────────────────────────┐\n");
//        output.append("┌─────────┬───────────────────────────┐\n");
//        output.append(String.format("│ %-7s │ %25s │\n", "Name", getName()));
//        output.append("├─────────┴───────────────────────────┤\n");
        output.append("│                Grades               │\n");
        if (!getSubjectsAndMarks().keySet().isEmpty()) {
            output.append("├────────────────────────────────┬─────────┤\n");
            for (Subject subject : getSubjectsAndMarks().keySet()) {
                output.append(String.format("│ %-30s │ %7s │\n", subject.name(), String.format("%3d%s", getSubjectsAndMarks().get(subject), '%')));
            }
            output.append("├────────────────────────────────┼─────────┤\n");
        }else
            output.append("├────────────────────────────────┬─────────┤\n");
        output.append(String.format("│ %30s │ %7.2f │\n", "Score", getSum()));
        output.append(String.format("│ %30s │ %7.2f │\n", "Total", getTotal()));
        output.append(String.format("│ %30s │ %7.2f │\n", "Average", getAverage()));
        output.append(String.format("│ %30s │ %7.2f │\n", "Grade", getGrade()));
        output.append("└────────────────────────────────┴─────────┘\n");



        return output.toString();
    }


    @Override
    public String toString() {
        return "Grade{" +
                "average=" + getAverage() +
                ", grade=" + getGrade() +
                ", total=" + getTotal() +
                ", marks=" + getSubjectsAndMarks() +
                '}';
    }
}