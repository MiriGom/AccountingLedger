package com.pluralsight.ledger;

import java.util.Scanner;

public class AccountingLedger {
    /*
     * 1.create a class called 'Atransaction'- to describe a transaction and 'FullLedger' to hold the list of transaction.
     * 2. make HomeScreen a while loop
     *           ask user to initiate or "log on" by giving his name.
     * 3. start with all the methods to call*/

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please enter your full name to start you Accounting Ledger Application");
        String userFullName = scanner.nextLine();
        boolean isRunning = false;
        if (!userFullName.isEmpty()) {
            isRunning = true;
        }
        while (isRunning) {
            System.out.println("""
                    ==============================
                    """);
            System.out.println("       WELCOME " + userFullName + "\n\n==============================");
            System.out.println("""
                    D: Add Deposit
                    P: Make a Payment
                    L: Ledger
                    X: Exit
                    """);
            String userInputLetter = scanner.nextLine().trim().toUpperCase();
            switch (userInputLetter) {
                case "D":
                    //addDeposit();
                    break;
                case "P":
                    //makePayment();
                    break;
                case "L":
                    //showLedgerDisplay();
                    break;
                case "X":
                    isRunning = false;
            }
        }
    }
}
