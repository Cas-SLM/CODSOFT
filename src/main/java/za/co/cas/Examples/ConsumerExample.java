package za.co.cas.Examples;

import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        // Using a lambda expression
        executeMethod(s -> System.out.println("Hello, " + s), "World");

        // Using a method reference
        executeMethod(ConsumerExample::greet, "Alice");
    }

    // Method that takes a Consumer functional interface
    public static void executeMethod(Consumer<String> consumer, String value) {
        consumer.accept(value);
    }

    // Method to be passed as a parameter
    public static void greet(String name) {
        System.out.println("Greetings, " + name);
    }
}