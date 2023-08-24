package org.example;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void concurrentPancake(){
        Executor executor = Executors.newFixedThreadPool(4); // 1 for the shopkeeper and 3 for users

        for (int slot = 1; slot <= 10; slot++) { // Assuming 10 slots in total
            System.out.println("Slot " + slot + ":");
            System.out.println("Starting time: " + (slot - 1) * 30 + " seconds");
            System.out.println("Ending time: " + slot * 30 + " seconds");

            int shopkeeperPancakesMade = Math.min(12, 30); // Maximum of 12 pancakes in 30 seconds

            int[] userPancakeDesires = new int[3];
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                userPancakeDesires[i] = random.nextInt(6); // Generating random numbers of pancakes between 0 and 5
            }

            executor.execute(() -> {
                int totalUserPancakesEaten = 0;
                for (int i = 0; i < 3; i++) {
                    int pancakesToEat = Math.min(5, userPancakeDesires[i]); // Maximum of 5 pancakes per user
                    totalUserPancakesEaten += pancakesToEat;
                }

                int wastedPancakes = Math.max(0, totalUserPancakesEaten - shopkeeperPancakesMade);

                System.out.println("Pancakes made by shopkeeper: " + shopkeeperPancakesMade);
                System.out.println("Pancakes eaten by users: " + totalUserPancakesEaten);
                System.out.println("Shopkeeper met user needs: " + (shopkeeperPancakesMade >= totalUserPancakesEaten));
                System.out.println("Pancakes wasted: " + wastedPancakes);
                System.out.println("Orders not met: " + (wastedPancakes > 0 ? 1 : 0));
                System.out.println();
            });
        }

        ((ExecutorService) executor).shutdown();
    }

    public static void linearPancake(){
        int totalPancakesMade = 0;
        int totalPancakesEaten = 0;
        int totalPancakesWasted = 0;
        int totalOrdersNotMet = 0;

        for (int slot = 1; slot <= 10; slot++) { // Assuming 10 slots in total
            System.out.println("Slot " + slot + ":");
            System.out.println("Starting time: " + (slot - 1) * 30 + " seconds");
            System.out.println("Ending time: " + slot * 30 + " seconds");

            int shopkeeperPancakesMade = Math.min(12, 30); // Maximum of 12 pancakes in 30 seconds
            totalPancakesMade += shopkeeperPancakesMade;

            int[] userPancakeDesires = new int[3];
            Random random = new Random();
            for (int i = 0; i < 3; i++) {
                userPancakeDesires[i] = random.nextInt(6); // Generating random numbers of pancakes between 0 and 5
            }

            int totalUserPancakesEaten = 0;
            for (int i = 0; i < 3; i++) {
                int pancakesToEat = Math.min(5, userPancakeDesires[i]); // Maximum of 5 pancakes per user
                totalUserPancakesEaten += pancakesToEat;
            }
            totalPancakesEaten += totalUserPancakesEaten;

            if (totalUserPancakesEaten > shopkeeperPancakesMade) {
                totalPancakesWasted += (totalUserPancakesEaten - shopkeeperPancakesMade);
                totalOrdersNotMet++;
            }

            System.out.println("Pancakes made by shopkeeper: " + shopkeeperPancakesMade);
            System.out.println("Pancakes eaten by users: " + totalUserPancakesEaten);
            System.out.println("Shopkeeper met user needs: " + (shopkeeperPancakesMade >= totalUserPancakesEaten));
            System.out.println("Pancakes wasted: " + (totalUserPancakesEaten - shopkeeperPancakesMade));
            System.out.println("Orders not met: " + totalOrdersNotMet);
            System.out.println();
        }

        System.out.println("Total pancakes made: " + totalPancakesMade);
        System.out.println("Total pancakes eaten: " + totalPancakesEaten);
        System.out.println("Total pancakes wasted: " + totalPancakesWasted);
        System.out.println("Total orders not met: " + totalOrdersNotMet);
    }

    public static void main(String[] args) {
        //linearPancake();
        concurrentPancake();
    }
}
