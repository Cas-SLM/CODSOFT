package za.co.cas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Grade {
    private Map<Subject, Integer> subjectsAndMarks;
    private double average;
    private double grade;
    private double sum;
    private double total;
    private final Mapper mapper;
    private final Comparator<Subject> comparator;

    public Grade() {
        this.subjectsAndMarks = new HashMap<>();
        this.average = 0;
        this.grade = 0;
        this.sum = 0;
        this.total = 0;
        this.mapper = new Mapper("/home/cas/Documents/CODSOFT/Student Grade Calculator/src/main/java/za/co/cas/Grades.json");
        this.comparator = Comparator.comparing(Enum::name);
    }

    private void calculateAll() {
        double sum = 0;
        int count = 0;
        int total = 0;
        for (Subject key : Subjects()) {
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
        return getSubjectsAndMarks().getOrDefault(subject, 0);
    }

    public Map<Subject, Integer> getSubjectsAndMarks() {
        return subjectsAndMarks;
    }

    public void setSubjectsAndMarks(Map<String, Integer> subjects) {
        Map<Subject,  Integer> newGrades = new HashMap<>();
        for (String key : subjects.keySet()) {
            try {
                newGrades.put(Subject.valueOf(key), subjects.get(key));
            } catch (IllegalArgumentException ignored) {}
        }
        this.subjectsAndMarks = newGrades;
        calculateAll();
    }

    public ArrayList<Subject> Subjects() {
        ArrayList<Subject> subjects1 = new ArrayList<>(subjectsAndMarks.keySet());
        subjects1.sort(comparator);
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
        ArrayList<Subject> subjects = Subjects();
        if (!subjects.isEmpty()) {
            output.append("├────────────────────────────────┬─────────┤\n");
            for (Subject subject : subjects) {
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