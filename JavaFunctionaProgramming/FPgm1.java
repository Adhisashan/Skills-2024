/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FunctionalProgrammingMeet;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Dell
 */
public class FPgm1 {

    public static int sum(int a, int b) {
        System.out.println("a:" + a + "  --> b::" + b);
        return a + b;
    }

    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        // traditional Sum
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }

        System.out.println("sum::" + sum);

        // Sum using reduce and Lambda
        int sum_ = numbers.stream().reduce(0, redfunc());
        System.out.println("sum_" + sum_);

        int sum_val = numbers.stream().reduce(0, FPgm1::sum);
        System.out.println("sum_val" + sum_val);

        // numbers.stream().map(x->x*x).forEach(System.out::println);
        // Sum using reduce and Method reference
        numbers.stream().reduce(0, (a, b) -> FPgm1.sum(a, b));

        final Function<Integer, Integer> squarefunction = x -> x * x;
        final Function<Integer, Integer> cubeFunction = x -> x * x * x;

        printmodifiednumber(numbers, squarefunction);
        printmodifiednumber(numbers, cubeFunction);

        numbers.stream().sorted().forEach(System.out::println);
        numbers.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        List<String> Courses = List.of("API", "AWS", "Spring", "Rust", "Ruby on Rails", "python", "JS", "Node", "Spring Boot");
        Courses.stream().sorted().forEach(System.out::println);
        System.out.println("reverse::------------");
        Courses.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        Courses.stream()
                .sorted(Comparator.comparing(String::length))
                .filter(str -> str.length() > 5)
                .map(str -> str + "=>" + str.length())
                .forEach(System.out::println);

        Predicate<Integer> evenPredicate = x -> x % 2 == 0;
        Function<Integer, Integer> SquareFunction = x -> {
            x = x + 1;
            return x * x;
        };
        Consumer<Integer> sysoutConsumer = System.out::println;
        
        Function<Integer, Integer> SquareFunction_1 = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x * x;
            }
        };

        Predicate<String> gt5_predicate = new Predicate<String>() {
            @Override
            public boolean test(String t) {
                if (t.contains("spring") && t.length() > 5) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        Predicate<String> gt4_predicate = new Predicate<String>() {
            @Override
            public boolean test(String t) {
                return t.length() > 4;
            }
        };

        // 
        List<String> gt5 = Courses.stream()
                .sorted(Comparator.comparing(String::length))
                .filter(gt5_predicate)
                .map(str -> str + "=>" + str.length()).collect(Collectors.toList());
        System.out.println("gt5::" + gt5);

    }

    private static void printmodifiednumber(List<Integer> numbers, final Function<Integer, Integer> function) {
        // Behaviour Parameterization

        numbers.stream()
                .map(function)
                .forEach(System.out::println);
    }

    private static BinaryOperator<Integer> redfunc() {
        return (a, b) -> a + b;
    }

}
