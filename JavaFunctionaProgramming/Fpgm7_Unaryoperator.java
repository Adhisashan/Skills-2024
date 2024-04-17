/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FunctionalProgrammingExercise;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;

/**
 *
 * Unary operator and other Functional interface
 */
public class Fpgm7_Unaryoperator {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        UnaryOperator<Integer> unaryoperator = (x) -> x * 3;
        Integer return_val = unaryoperator.apply(10);
        System.out.println("return val::" + return_val);

        UnaryOperator<Double> areaOfCircle = (r) -> 3.14 * r * r;
        System.out.println("Area of Circle val::" + areaOfCircle.apply(5.0));

        // Bipredicate
        BiPredicate<Integer, String> bipredicate = (number, str) -> true;
        bipredicate.test(10, "5");

        BiPredicate<Integer, String> bipredicate2 = (number, str) -> {
            if (number > 10 && str.length() > 5) {
                return true;
            } else {
                return false;
            }
        };
        bipredicate2.test(10, "5");

        //          I/P 1,  I/p 2   o/p
        BiFunction<Integer, String, String> biFunction = (number, str) -> {
            return number + " " + str;
        };
        biFunction.apply(5, "value");
        
        
        BiConsumer<Integer, String> biConsumer = (number, str) -> {
            System.out.println(number);
            System.out.println(str);
        };
        biConsumer.accept(10, "biconsumer check");
        
        /*
        BiPredicate
        BiFunction
        BiConsumer
        
        Lambda expressions created can be used to store in these type of functional interface
        */
        
    }

}
