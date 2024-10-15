package com.pluralsight.ledger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AccountingLedger {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<LedgerList> payments = new ArrayList<>();
    static ArrayList<LedgerList> deposits = new ArrayList<>();
    static ArrayList<ATransaction> transactions = new ArrayList<>();
    static HashMap<String, ATransaction> ledgerEntries = new HashMap<String, ATransaction>();
    private static final String loggingFile = "transaction.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static void main(String[] args) {
       // loadLedger();
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
    //method to log transactions
    private void logAction(String action) {
        String timeStamp = LocalDateTime.now().format(formatter);       //getting timestamp and formatting
        String logEntry = String.format("%s %s%n", timeStamp, action);    //using the printformat to construct a log entry

//creating a FileWriter to create an instance to write to a file named "transactions". Buffwriter improves efficiency
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true))){ //true (append) means if the file does not exist we are going to create it.
            bufWriter.write(logEntry); // this is the code that actually writes to the transactions.csv
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //new screen
    private void showLedgerDisplay() {
        //show options for
        // 1. All Entries---add double int to show total or put it in reports
        // 2.Deposits
        // 3.Payments
        // 4.Reports --will be its own screen

    }
    /*
//method to read data from csv and write to the ledger
    private static void loadLedger() {
        try {
            FileReader fileReader = new FileReader("C:\\pluralsight\\AccountingLedger\\AccountingLedger\\transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String eachLine;

            while ((eachLine = bufReader.readLine()) !=null) {
                String[] newEachLine = eachLine.split("\\|");

                if (newEachLine.length < 5) {
                    System.out.println("Invalid format: " + eachLine);
                    continue;
                }
                LocalDate date;
                try {
                    date = LocalDate.parse(newEachLine[0].trim());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format: " + newEachLine[0]);
                    continue;
                }
                LocalTime time;
                try {
                    time = LocalTime.parse(newEachLine[1].trim());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid time format: " + newEachLine[1]);
                    continue;
                }
                String description = newEachLine[2].trim();
                String vendor = newEachLine[3].trim();
                double amount;
                try {
                    amount = Double.parseDouble(newEachLine[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount format: " + newEachLine[3]);
                    continue;
                }
                ATransaction userTransaction = new ATransaction(date, time, description, vendor, amount);
                transactions.add(userTransaction);
                //System.out.println(userTransaction);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
    /*
    private addDeposit(){

    }
    private makePayment(){

    }
    private displayEntries(){

    }
    private displayDeposits(){

    }
    private displayPayments(){

    }
    private displayReports(){
    }*/

}
