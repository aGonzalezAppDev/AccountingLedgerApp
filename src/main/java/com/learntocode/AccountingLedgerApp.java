package com.learntocode;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {
    private static String DATE_FORMAT = String.valueOf(LocalDate.now());
    private static String TIME_FORMAT = String.valueOf(LocalTime.now());

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static void main(String[] args) {

    // create new arrayList to pass information to
    ArrayList<Ledger> ledger = getLedger();

    Scanner myScanner = new Scanner(System.in); // initialize scanner

     // create empty string variable for exit (X)
     String choice = "";
     // create while loop to show user Home Screen
     while(!choice.equalsIgnoreCase("X")){
         //Display Home Screen
         System.out.println("---------------");
         System.out.println("Home Screen");
         System.out.println("---------------");
         System.out.println();
         System.out.println("D) Add Deposit");
         System.out.println("P) Make Payment");
         System.out.println("L) Ledger");
         System.out.println("X) Exit");
         System.out.println("---------------");
         choice = myScanner.nextLine(); // save user answer in variable
         // create switch for different cases
         switch(choice) {
             case "D":
                 // Add Deposit method
                 addDeposit(myScanner);
                 break;
             case "P":
                 // Make Payment method
                 makePayment(myScanner);
                 break;
             case "L":
                 // Display Ledger Screen and sub methods
                 displayLedgerScreen(myScanner);
                 break;
             case "X":
                 System.out.println("Exiting Application!!");
                 break;
             default:
                 System.out.println("Invalid input! Please use one of the above inputs!");
                 break;
            }
        }
    }

    // create method to get transactions from file into arraylist
    private static ArrayList<Ledger> getLedger(){
        ArrayList<Ledger> ledger = new ArrayList<>();

        String line;

        try {
        BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            while((line = reader.readLine()) != null) {
            String [] parts = line.split("\\|");
            String date = parts[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date1 = LocalDate.parse(date, formatter);
            String time = parts[1];
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time1 = LocalTime.parse(time,formatter1);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);
            ledger.add(new Ledger(date1, time1, description, vendor, amount));
                }
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ledger;
    }

    // make method for adding a new deposit into the csv file
    private static void addDeposit(Scanner myScanner){
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv",true));// add true to append to file
            System.out.println("Enter all deposit information for new transaction in following format: ");
            System.out.println("date|time|description|vendor|amount");
            String newDeposit = myScanner.nextLine();
            bufWriter.write(newDeposit);
        bufWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // make method for making a payment and adding it to the csv file
    private static void makePayment(Scanner myScanner){
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv",true));
            System.out.println("Enter all payment information for new transaction in following format: ");
            System.out.println("date|time|description|vendor|amount");
            String newPayment = myScanner.nextLine();
            bufWriter.write(newPayment);
            bufWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void displayLedgerScreen(Scanner myScanner){
        String ledgerInput = "";
        while (!ledgerInput.equalsIgnoreCase("H")){
            //Display Ledger Screen
            System.out.println("---------------");
            System.out.println("Ledger Screen");
            System.out.println("---------------");
            System.out.println();
            System.out.println("A) All - Display all entries.");
            System.out.println("D) Deposits - Display only deposits into account");
            System.out.println("P) Payments - Display only payments"); // negative entries
            System.out.println("R) Reports - Menu with pre-defined reports or run custom search feature");
            System.out.println("H) Home - go back to home screen");
            System.out.println("----------------------------------------------------------------------------");
            ledgerInput = myScanner.nextLine();
            // create switch for Ledger Screen options
            switch(ledgerInput){
                case "A":
                    // display all entries
                    displayAllEntries();
                    break;
                case "D":
                    // display deposits only
                    displayDepositsOnly();
                    break;
                case "P":
                    // display payments only
                    displayPaymentsOnly();
                    break;
                case "R":
                    // show reports and do other pre-defined reports and custom search
                    displayReportScreen(myScanner);
                    break;
                case "H":
                    // go back to home screen
                    System.out.println("Going back to Home Screen!");
                    break;
                default:
                    System.out.println("Invalid input!! Please use on of the above inputs!");
                    break;
            }

        }
    }

    public static void displayAllEntries(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));

            String input;
            // read all the data in the file
            while (((input = reader.readLine()) != null)) {
                System.out.println(input);
            }
            reader.close();
            } catch(FileNotFoundException e){
                throw new RuntimeException(e);
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }



    public static void displayDepositsOnly(){
        ArrayList<Ledger> ledger = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            while((line = reader.readLine()) != null) {
                String [] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                for (Ledger deposit : ledger) {
                    if(amount > 0) {
                        System.out.printf("%s|%s|%s|%s|$%.2f%n",
                                deposit.getDate(), deposit.getTime(), deposit.getDescription(),deposit.getVendor(),deposit.getAmount());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void displayPaymentsOnly(){
        ArrayList<Ledger> ledger = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            while((line = reader.readLine()) != null) {
                String [] parts = line.split("\\|");
                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                for (Ledger deposit : ledger) {
                    if(amount > 0) {
                        System.out.printf("%s|%s|%s|%s|$%.2f%n",
                                deposit.getDate(), deposit.getTime(), deposit.getDescription(),deposit.getVendor(),deposit.getAmount());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // code to show reports menu
    public static void displayReportScreen(Scanner myScanner){
        int reportInput = -1;
        while (reportInput!=0){
            //Display Ledger Screen
            System.out.println("---------------");
            System.out.println("Reports Screen");
            System.out.println("---------------");
            System.out.println();
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to report page");
            System.out.println("----------------------------------------------------------------------------");
            reportInput = myScanner.nextInt();
            myScanner.nextLine();
            // create switch for Ledger Screen options
            switch(reportInput){
                case 1:
                    // Month To Date
                    monthToDate(LocalDate.of(2023, Month.MAY, 1), LocalDate.now());
                    break;
                case 2:
                    // Previous Month
                    previousMonth(LocalDate.of(2023, Month.MAY, 1), LocalDate.of(2023, Month.APRIL, 1));
                    break;
                case 3:
                    // Year To Date
                    yearToDate(LocalDate.of(2023, Month.JANUARY, 1), LocalDate.now());
                    break;
                case 4:
                    // Previous Year
                    previousYear(LocalDate.of(2023, Month.JANUARY, 1), LocalDate.of(2022, Month.JANUARY, 1));
                    break;
                case 5:
                    // Search by Vendor
                    searchByVendor(getLedger(),myScanner);
                    break;
                case 0:
                    // go back to report page
                    System.out.println("Going back to Report Page!");
                    continue; // use continue instead of break in order to go back to report page
                default:
                    System.out.println("Invalid input!! Please use on of the above inputs!");
                    break;
            }
        }
    }


    // method for monthToDate report
    public static void monthToDate(LocalDate startDate, LocalDate endDate) {
        boolean isBefore = endDate.isBefore(LocalDate.of(2023, Month.JUNE, 1));
        boolean isAfter = endDate.isAfter(startDate);
        for(Ledger sheet : getLedger()) {
        if(isBefore && isAfter) { // if true
            System.out.printf("%s|%s|%s|%s|$%.2f%n",
                    sheet.getDate(), sheet.getTime(), sheet.getDescription(), sheet.getVendor(), sheet.getAmount());
           }
       }
    }

    // method for previous month
    public static void previousMonth(LocalDate startDate, LocalDate endDate) {
        boolean isBefore = endDate.isBefore(LocalDate.of(2023, Month.MAY, 1));
        boolean isAfter = endDate.isAfter(LocalDate.of(2023, Month.APRIL, 1));
        for (Ledger sheet : getLedger()) {
            if (isBefore && isAfter) { // if true
                System.out.printf("%s|%s|%s|%s|$%.2f%n",
                        sheet.getDate(), sheet.getTime(), sheet.getDescription(), sheet.getVendor(), sheet.getAmount());
            }
        }
    }

    // method for year to Date
    public static void yearToDate(LocalDate startDate, LocalDate endDate) {
        boolean isBefore = endDate.isBefore(LocalDate.of(2023, Month.JANUARY, 1));
        boolean isAfter = endDate.isAfter(LocalDate.now());
        for (Ledger sheet : getLedger()) {
            if (isBefore && isAfter) { // if true
                System.out.printf("%s|%s|%s|%s|$%.2f%n",
                        sheet.getDate(), sheet.getTime(), sheet.getDescription(), sheet.getVendor(), sheet.getAmount());
            }
        }
    }

    // method for previous year
    public static void previousYear(LocalDate startDate, LocalDate endDate) {
        boolean isBefore = endDate.isBefore(LocalDate.of(2023, Month.JANUARY, 1));
        boolean isAfter = endDate.isAfter(LocalDate.of(2022, Month.JANUARY, 1));
        for (Ledger sheet : getLedger()) {
            if (isBefore && isAfter) { // if true
                System.out.printf("%s|%s|%s|%s|$%.2f%n",
                        sheet.getDate(), sheet.getTime(), sheet.getDescription(), sheet.getVendor(), sheet.getAmount());
            }
        }
    }

    // method for searchByVendor
    public static void searchByVendor(ArrayList<Ledger> ledger, Scanner myScanner) {
        System.out.println("Enter Vendor Name: ");
        String vendor = myScanner.nextLine();
        for (Ledger transaction : ledger) {
            if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(transaction);
                return;
            }
        }
    }




}
