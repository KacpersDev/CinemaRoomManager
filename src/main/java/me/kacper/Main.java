package me.kacper;

import java.text.DecimalFormat;
import java.util.*;

public class Main {

    private final static Map<Integer, int[]> map = new HashMap<>();
    private static final char EMPTY = 'S';
    private static final char SELECTED = 'B';

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        Scanner rowsScanner = new Scanner(System.in);
        int rows = rowsScanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        Scanner seatsScanner = new Scanner(System.in);
        int seats = seatsScanner.nextInt();

        Scanner optionScanner = new Scanner(System.in);
        int option;

        while (true) {
            System.out.println(" ");
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            option = optionScanner.nextInt();
            System.out.println(" ");
            switch (option) {
                case 0 -> { return; }
                case 1 -> getCinemaSeats(rows, seats);
                case 2 -> {
                    System.out.println("Enter a row number:");
                    Scanner rowScanner = new Scanner(System.in);
                    int row = rowScanner.nextInt();

                    System.out.println("Enter a seat number in that row:");
                    Scanner seatScanner = new Scanner(System.in);
                    int seat = seatScanner.nextInt();

                    if (isPurchased(row, seat)) {
                        do {
                            System.out.println(" ");
                            System.out.println("That ticket has already been purchased!");
                            System.out.println(" ");
                            System.out.println("Enter a row number:");
                            row = rowScanner.nextInt();

                            System.out.println("Enter a seat number in that row:");
                            seat = seatScanner.nextInt();

                        } while (isPurchased(row, seat));
                    }
                    if (isExceeded(row, seat, rows ,seats)) {
                        do {
                            System.out.println(" ");
                            System.out.println("Wrong input!");
                            System.out.println(" ");
                            System.out.println("Enter a row number:");
                            row = rowScanner.nextInt();

                            System.out.println("Enter a seat number in that row:");
                            seat = seatScanner.nextInt();

                        } while (isExceeded(row, seat, rows, seats));
                    }

                    if (map.get(row) == null) {
                        int[] integers = new int[seats + 1];
                        integers[seat] = seat;
                        map.put(row, integers);
                    } else {
                        int[] integers = map.get(row);
                        integers[seat] = seat;
                        map.replace(row, map.get(row), integers);
                    }
                    System.out.println(" ");
                    System.out.println("Ticket Price: $" + getTicketPrice(rows, row, seats));
                }
                case 3 -> {
                    System.out.println("Number of purchased tickets: " + getPurchasedTicketCount());
                    System.out.println("Percentage: " + getPercentage(rows, seats) + "%");
                    System.out.println("Current income: $" + getCurrentIncome(rows, seats));
                    System.out.println("Total income: $" + getIncome(rows, seats));
                }
            }
        }
    }

    private static void getCinemaSeats(int rows, int seats){
        char[][] cinema = new char[rows][seats];

        System.out.println("Cinema: ");
        for (int z = 0; z < rows; z++) {
            for (int i = 0; i < seats; i++) {
                cinema[z][i] = EMPTY;
            }
        }

        for (Map.Entry<Integer, int[]> values : map.entrySet()) {
            for (int value : values.getValue()) {
                if (value != 0) {
                    cinema[values.getKey() - 1][value - 1] = SELECTED;
                }
            }
        }

        for (int i = 1; i < seats + 1; i++) {
            if (i == 1) System.out.print(" ");
            System.out.print(" ");
            System.out.print(i);
            if (i == seats)
                System.out.println(" ");
        }
        int current = 0;
        boolean isValue = false;
        for (int z = 1; z < rows + 1; z++) {
            for (int i = 1; i < seats + 1; i++) {
                current++;
                if (!isValue) {
                    System.out.print(z);
                    isValue = true;
                }
                System.out.print(" ");
                System.out.print(cinema[z - 1][i - 1]);
                if (current == seats) {
                    System.out.println(" ");
                    current = 0;
                    isValue = false;
                }
            }
        }
    }

    private static int getTicketPrice(int rows, int row, int seats) {
        if (rows * seats <= 60) return 10;
        if (rows % 2 == 0) {
            int divided = rows / 2;
            if (row <= divided) {
                return 10;
            } else {
                return 8;
            }
        } else {
            int firstHalf = (rows - 1) / 2;
            if (row <= firstHalf) return 10;
            else return 8;
        }
    }

    private static int getPurchasedTicketCount(){
        int tickets = 0;

        if (map.isEmpty()) return tickets;

        for (Integer keys : map.keySet()) {
            for (int i : map.get(keys)) {
                if (i != 0) {
                    tickets++;
                }
            }
        }

        return tickets;
    }

    private static String getPercentage(int rows, int seats){
        double value = (float) (getPurchasedTicketCount() * 100) / getTotalSeats(rows, seats);
        return new DecimalFormat("0.00").format(value);
    }

    private static int getTotalSeats(int row, int seat){
        return row * seat;
    }

    private static boolean isExceeded(int row, int seat, int rows, int seats){
        return row > rows || seat > seats;
    }

    private static int getCurrentIncome(int rows, int seats){
        int income = 0;
        if (map.isEmpty()) return income;

        for (Map.Entry<Integer, int[]> values : map.entrySet()) {
            int keySize = 0;
            for (int i : values.getValue()) {
                if (i != 0) {
                    keySize++;
                }
            }
            income += (keySize * getTicketPrice(rows, values.getKey(), seats));
        }

        return income;
    }

    private static boolean isPurchased(int row, int seat) {
        if (map.isEmpty()) return false;
        boolean isPurchased = false;

        for (Map.Entry<Integer, int[]> values : map.entrySet()) {
            if (values.getKey() == row) {
                for (int value : values.getValue()) {
                    if (value == seat) {
                        isPurchased = true;
                        break;
                    }
                }
            }
        }

        return isPurchased;
    }

    private static int getIncome(int rows, int seats) {
        int ticketPrice = 0;
        if (rows * seats <= 60) ticketPrice = (10 * (seats * rows));
        else {
            if (rows % 2 == 0) {
                int half = rows / 2;
                for (int i = 0; i < rows; i++) {
                    if (i < half) {
                        ticketPrice = ticketPrice + (8 * seats);
                    } else {
                        ticketPrice = ticketPrice + (10 * seats);
                    }
                }
            } else {
                int firstHalf = (rows - 1) / 2;
                int secondHalf = (rows + 1) / 2;

                ticketPrice = ((firstHalf * seats * 10) + (secondHalf * seats * 8));
            }
        }

        return ticketPrice;
    }
}