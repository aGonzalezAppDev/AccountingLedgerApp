package com.learntocode;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {
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
            String time = parts[1];
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);
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
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv"));
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
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("transactions.csv"));
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


    public static void displayReportScreen(Scanner myScanner){
        




    }











}
