package com.pluralsight.ledger;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class AccountingLedger {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<ATransaction> transactions = new ArrayList<>();
    private static final String loggingFile = "transaction.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");


    public static void main(String[] args) {
        System.out.println("\nPlease enter your full name to start you Accounting Ledger Application");
        String userFullName = scanner.nextLine().toUpperCase().trim();

        boolean isRunning = false;
        if (!userFullName.isEmpty()) {
            isRunning = true;
        }
        while (isRunning) {
            String userInput;
            System.out.printf("""
                    ====================================================================
                    
                                           WELCOME %s
                    
                    ====================================================================
                    ENTER CORRESPONDING LETTER TO ACCESS THE OPTIONS BELOW
                    
                    D: Add Deposit
                    P: Make a Payment
                    L: Ledger
                    X: Exit
                    
                    ====================================================================
                    """, userFullName);
            String userInputLetter = scanner.nextLine().trim().toUpperCase();
            switch (userInputLetter) {
                case "D":
                    addDeposit();
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    showLedgerDisplay();
                    break;
                case "X":
                    System.out.println("Have a great day! Bye Bye now.");
                    isRunning = false;
            }
        }
    }

    //method to log transactions
    private static void logAction(String action) {
        String timeStamp = LocalDateTime.now().format(formatter);       //getting timestamp and formatting
        String logEntry = String.format("%s|%s%n", timeStamp, action);    //using the printformat to construct a log entry

//creating a FileWriter to create an instance to write to a file named "transactions". Buffwriter improves efficiency
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) { //true (append) means if the file does not exist we are going to create it.
            bufWriter.write(logEntry); // this is the code that actually writes to the transactions.csv
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //new screen
    private static void showLedgerDisplay() {
        loadLedger();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("""
                    ===============================================================
                                               DISPLAY
                    ===============================================================
                    ENTER CORRESPONDING LETTER TO ACCESS THE OPTIONS BELOW
                    
                    A: All Entries
                    D: Deposits
                    P: Payment
                    R: Reports
                    H: Home Screen
                    
                    ===============================================================
                    """);

            String userInputLetter = scanner.nextLine();
            String userInput;
            switch (userInputLetter.toUpperCase().trim()) {
                case "A":
                    System.out.println("""
                            ----------------------------------------------------------------------------------------
                                                                    ALL ENTRIES
                            ----------------------------------------------------------------------------------------
                            """);
                    for (ATransaction transaction : transactions) {
                        System.out.println(transaction);
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case "D":
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                DEPOSITS
                            --------------------------------------------------------------------------------------                                    
                            """);
                    for (ATransaction transaction : transactions) {
                        if (transaction.getAmount() > 0) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case "P":
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                PAYMENTS
                            --------------------------------------------------------------------------------------                                    
                            """);
                    for (ATransaction transaction : transactions) {
                        if (transaction.getAmount() < 0) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case "R":
                    runReports();
                    break;
                case "H":
                    return;
            }
        }
    }


    //method to read data from csv and write to the ledger
    private static void loadLedger() {
        try {
            FileReader fileReader = new FileReader("C:\\pluralsight\\AccountingLedger\\AccountingLedger\\transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String eachLine;

            while ((eachLine = bufReader.readLine()) != null) {
                String[] newEachLine = eachLine.split("\\|");
                //bufReader.readLine();
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
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDeposit() {
        System.out.println("Please enter name of the funder for this deposit");//prompt user for name of funder
        String funderName = scanner.nextLine().toLowerCase().trim(); //variable to save name provided

        System.out.println("Please type the amount of the deposit:");// prompt user for deposit amount
        double depositAmount = scanner.nextDouble();//create variable to save deposit amount
        String formattedDeposit = String.format("%.2f", depositAmount);
        scanner.nextLine(); //used after an nextInt or nextDouble to complete

        System.out.println("Please type reason for the deposit"); //prompting user for a description
        String depositDescription = scanner.nextLine(); // saving it in a variable
        LocalDate date = LocalDate.now();//making a variable to save the current date so we can later insert it as a parameter to a transaction in the list called transactions
        LocalTime time = LocalTime.now();//making a variable to save the current time so we can later insert it as a parameter to a transaction in a list called transactions
        System.out.println("Deposit of Amount: $" + formattedDeposit + " successful!");// telling user their deposit was successful

        ATransaction userDeposit = new ATransaction(date, time, depositDescription, funderName, depositAmount); //with all the parameters provided above we create a transaction under the variable userDeposit
        transactions.add(userDeposit);//we add our above deposit as transaction to a list called transactions
        logAction(depositDescription + "|" + funderName + "|" + depositAmount); // we are calling the log action to write this down in as csv called transactions.csv


    }

    private static void makePayment() {
        System.out.println("Please enter name of payee");
        String vendorName = scanner.nextLine().trim();

        System.out.println(("Please type the amount you want to transfer to payee"));
        double paymentAmount = scanner.nextDouble();
        scanner.nextLine();
        double ledgerPaymentAmount = -Math.abs(paymentAmount); //changed variable name so I can still use the positive number to the user.
        String formattedPaymentAmount = String.format("%.2f", paymentAmount);
        System.out.println("Please type reason for payment");
        String paymentDescription = scanner.nextLine();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println("Payment to " + vendorName + " of $" + formattedPaymentAmount + " successful!");
        ATransaction payTransfer = new ATransaction(date, time, paymentDescription, vendorName, ledgerPaymentAmount);
        transactions.add(payTransfer);
        logAction(paymentDescription + "|" + vendorName + "|" + ledgerPaymentAmount);

    }

    public static void runReports() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.printf("""
                    =========================================================
                                           Reports
                    =========================================================
                    ENTER CORRESPONDING NUMBER TO ACCESS AN OPTION BELOW
                    
                    1. Month To Date
                    2. Previous Month
                    3. Year To Date
                    4. Previous Year
                    5. Search By Vendor
                    0. Back To Ledger Screen
                    
                    =========================================================
                    """);
            int numberCommand = scanner.nextInt();
            scanner.nextLine();
            LocalDate date = LocalDate.now();
            String userInput;

            switch (numberCommand) {
                case 0:
                    return;
                case 1:
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                MONTH TO DATE
                            --------------------------------------------------------------------------------------                                    
                            """);
                    LocalDate firstDayOfMonth = date.withDayOfMonth(1);
                    LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());
                    for (ATransaction transaction : transactions) {
                        LocalDate transactionDate = transaction.getDate();
                        if (!transactionDate.isBefore(firstDayOfMonth) && !transactionDate.isAfter(lastDayOfMonth)) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case 2:
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                PREVIOUS MONTH
                            --------------------------------------------------------------------------------------                                    
                            """);
                    LocalDate previousMonth = date.minusMonths(1);//this gets us exactly one month from today
                    LocalDate firstDayOfPreviousMonth = previousMonth.withDayOfMonth(1);//to build beginning of month range
                    LocalDate lastDayOfPreviousMonth = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());// to end the month range

                    for (ATransaction transaction : transactions) {
                        LocalDate transactionDate = transaction.getDate();
                        if (!transactionDate.isBefore(firstDayOfPreviousMonth) && !transactionDate.isAfter(lastDayOfPreviousMonth)) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case 3:
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                YEAR TO DATE
                            --------------------------------------------------------------------------------------                                    
                            """);
                    for (ATransaction transaction : transactions) {
                        if (transaction.getDate().getYear() == date.getYear()) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case 4:
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                PREVIOUS YEAR
                            --------------------------------------------------------------------------------------                                    
                            """);
                    LocalDate previousYear = date.minusYears(1);//this gets us exactly one month from today
                    LocalDate firstDayOfPreviousYear = previousYear.withDayOfYear(1);//to build beginning of month range
                    LocalDate lastDayOfPreviousYear = previousYear.withDayOfMonth(previousYear.lengthOfMonth());// to end the month range

                    for (ATransaction transaction : transactions) {
                        LocalDate transactionDate = transaction.getDate();
                        if (!transactionDate.isBefore(firstDayOfPreviousYear) && !transactionDate.isAfter(lastDayOfPreviousYear)) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
                case 5:
                    System.out.println("""
                            --------------------------------------------------------------------------------------
                                                                VENDOR
                            --------------------------------------------------------------------------------------                                    
                            """);
                    System.out.println("Type and enter the vender you are searching for");
                    String searchingForVender = scanner.nextLine().toLowerCase().trim();
                    for (ATransaction transaction : transactions) {
                        if (searchingForVender.equals(transaction.getVendor().toLowerCase())) {
                            System.out.println(transaction);
                        }
                    }
                    System.out.println("\npress \"B\" to go back to previous screen.");
                    userInput = scanner.nextLine().toUpperCase().trim();
                    break;
            }
        }
    }
}
