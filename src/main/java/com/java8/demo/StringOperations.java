package com.java8.demo;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringOperations {
        public static void operations() {
            System.out.println("Performing string operations...");
            String input = "Rama ia good boy. He loves coding and enjoys learning new programming languages. Rama is also a great team player.";
            // Add your string operations logic here
            characterFreqenceCount(input);
            characterFreqenceHighCount(input);
           reverseString("hello world");
            /*             isPalindrome("madam");*/
        }

    private static void reverseString(String helloWorld) {
        String reversed = new StringBuilder(helloWorld).reverse().toString();
        System.out.println("Reversed string: " + reversed);
        System.out.println("Reversed string using streams: " + helloWorld.chars()
                .mapToObj(c -> (char) c)
                .reduce("", (s, c) -> c + s, (s1, s2) -> s2 + s1));
        System.out.println("Reversed string using streams forEach: " +
                helloWorld.chars()
                        .mapToObj(c -> (char) c)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                            StringBuilder sb = new StringBuilder();
                            for (int i = list.size() - 1; i >= 0; i--) {
                                sb.append(list.get(i));
                            }
                            return sb.toString();
                        })));


    }

    private static void characterFreqenceHighCount(String input) {
        System.out.println("Character with highest frequency for: " + input);

        input.chars()
                .filter(c -> c != ' ') // Exclude spaces from frequency count
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry ->
                        System.out.println("Character with highest frequency: '" + entry.getKey() + "' - Frequency: " + entry.getValue()));

    }

    private static void characterFreqenceCount(String input) {
        System.out.println("Character frequency count for: " + input);

        input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((character, count) ->
                        System.out.println("Character: '" + character + "' - Frequency: " + count));
    }


}
