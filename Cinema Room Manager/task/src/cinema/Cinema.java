package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        inCome();

    }

    static void cinemaMap(){

        System.out.println("Cinema:");
        System.out.println("  1 2 3 4 5 6 7 8");
        System.out.println("1 S S S S S S S S");
        System.out.println("2 S S S S S S S S");
        System.out.println("3 S S S S S S S S");
        System.out.println("4 S S S S S S S S");
        System.out.println("5 S S S S S S S S");
        System.out.println("6 S S S S S S S S");
        System.out.println("7 S S S S S S S S");
        System.out.println("");
    }

    static void inCome(){

        int totalIncome = 0;

        Scanner seats = new Scanner(System.in);
        Scanner rows = new Scanner(System.in);

        System.out.println("Enter the number of rows: ");
        int rowsNumber = Integer.parseInt(rows.nextLine());
        System.out.println("Enter the number of seats in each row: ");
        int seatsNumber = Integer.parseInt(seats.nextLine());
        int total = (seatsNumber * rowsNumber);

        if (total < 60) {
            totalIncome = (total * 10);
            System.out.println("Total income:");
            System.out.println("$" + totalIncome);
        } else {
            if (rowsNumber % 2 == 0) {
                int splitRows = (rowsNumber / 2);
                int first = splitRows * 10;
                int second = splitRows * 8;
                int valueOfTotal = first + second;
                int valueForSeats = (valueOfTotal * seatsNumber);
                System.out.println("Total income:");
                System.out.println("$" + valueForSeats);
            } else {
                int front = ((rowsNumber - 1) / 2);
                int back = ((rowsNumber + 1) / 2);
                int totalFront = front * 10;
                int totalBack = back * 8;
                int totalPrice = ((totalFront + totalBack) * seatsNumber);
                System.out.println("Total income:");
                System.out.println("$" + (totalPrice));
            }
        }
    }
}