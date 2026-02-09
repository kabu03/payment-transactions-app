
import models.TransactionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import repositories.DatabaseConnection;
import repositories.HibernateTransactionRepository;
import repositories.JdbcTransactionRepository;
import repositories.MemoryTransactionRepository;
import requests.AliasRequest;
import requests.TransactionIdRequest;
import requests.TransactionRequest;
import services.*;
import usecases.*;
import validators.AliasValidator;
import validators.CurrencyValidator;
import validators.TransactionValidator;

import java.util.Scanner;

class Main {
    static boolean exitMainMenu = false;
static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TransactionRepository repository = startMenu();
        while (!exitMainMenu) {
            mainMenu(sc, repository);
        }

    }


    public static int getValidatedChoice(Scanner scanner, int[] validChoices) {
        int choice;

        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                if (isValidChoice(choice, validChoices)) {
                    return choice; // Valid choice, return it
                } else {
                    System.out.println("Invalid input. Please enter one of the valid options.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static boolean isValidChoice(int choice, int[] validChoices) {
        for (int validChoice : validChoices) {
            if (choice == validChoice) {
                return true;
            }
        }
        return false;
    }

    private static TransactionRepository startMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Refactor in progress...");
        System.out.println("Welcome to the Payment Transactions application!");
        System.out.println("Before proceeding, would you like to perform all operations:");
        System.out.println("1. In Memory?");
        System.out.println("2. In Database using JDBC?");
        System.out.println("3. In Database using Hibernate ORM?");
        switch (getValidatedChoice(sc, new int[]{1, 2, 3})) {
            case 1:
                System.out.println("You have chosen to perform all operations in memory.");
                return new MemoryTransactionRepository();
            case 2:
                System.out.println("You have chosen to perform all operations on a database using JDBC.");
                try {
                    DatabaseConnection db = new DatabaseConnection();
                    return new JdbcTransactionRepository("transactions", db);
                } catch (Exception e) {
                    System.out.println("Error connecting to database");
                }
            case 3:
                System.out.println("You have chosen to perform all operations on a database using Hibernate ORM.");
                try {
//                    return new HibernateTransactionRepository();
                    System.out.println("Under maintenance...");
                } catch (Exception e) {
                    System.out.println("Error connecting to database");
                }
        }
        return null;
    }


    public static void mainMenu(Scanner sc, TransactionRepository repository) {
        TransactionValidator transactionValidator = new TransactionValidator();
        AliasValidator aliasValidator = new AliasValidator();
        CurrencyValidator currencyValidator = new CurrencyValidator();
        String aliasType;
        String aliasValue;
        System.out.println("What would you like to do?");
        System.out.println("1. Add a transaction");
        System.out.println("2. Remove a transaction by ID");
        System.out.println("3. Get a transaction by ID");
        System.out.println("4. Get inward transactions for an alias");
        System.out.println("5. Get outward transactions for an alias");
        System.out.println("6. Get all transactions for an alias");
        System.out.println("7. Get all saved transactions");
        System.out.println("8. Exit");
        switch (getValidatedChoice(sc, new int[]{1, 2, 3, 4, 5, 6, 7, 8})) {
            case 1:
                System.out.println("You have chosen to add a transaction.");
                assert repository != null;
                System.out.println("Enter the transaction details:");
                System.out.println("Sender alias type:");
                String senderAliasType = getAliasType(sc);
                System.out.println("You chose: " + senderAliasType);
                System.out.println("Sender alias value:");
                String senderAliasValue = sc.next();
                System.out.println("Receiver alias type:");
                String receiverAliasType = getAliasType(sc);
                System.out.println("You chose: " + receiverAliasType);
                System.out.println("Receiver alias value:");
                String receiverAliasValue = sc.next();
                System.out.println("Amount:");
                double amount = sc.nextDouble();
                System.out.println("Currency (JOD, USD, EUR, GBP, HUF):");
                String currency = sc.next();
                System.out.println("Purpose:");
                String purpose = sc.next();
                assert senderAliasType != null;
                assert receiverAliasType != null;
                TransactionRequest tr = new TransactionRequest(senderAliasType, senderAliasValue,
                        receiverAliasType, receiverAliasValue, amount, currency, purpose, null);
                AddTransactionService addTransactionService =
                        new AddTransactionService(new AddTransactionUseCase(repository, transactionValidator, aliasValidator, currencyValidator), tr);
                System.out.println(addTransactionService.processRequest());
                break;
            case 2:
                System.out.println("You have chosen to remove a transaction by ID.");
                System.out.println("Enter the ID of the transaction you'd like to remove:");
                int transactionID = sc.nextInt();
                TransactionIdRequest tir = new TransactionIdRequest(transactionID, null);
                RemoveTransactionService removeTransactionService =
                        new RemoveTransactionService(new RemoveTransactionUseCase(repository, transactionValidator, aliasValidator), tir);
                System.out.println(removeTransactionService.processRequest());
                break;
            case 3:
                System.out.println("You have chosen to get a transaction by ID.");
                System.out.println("Enter the ID of the transaction you'd like to fetch:");
                int transactionIDToFetch = sc.nextInt();
                GetTransactionByIdService getTransactionByIdService =
                        new GetTransactionByIdService(new GetTransactionByIdUseCase(repository), new TransactionIdRequest(transactionIDToFetch, null));
                System.out.println(getTransactionByIdService.processRequest());
                break;
            case 4:
                System.out.println("You have chosen to get inward transactions for an alias.");
                System.out.println("Enter alias type:");
                aliasType = getAliasType(sc);
                System.out.println("Enter alias value:");
                aliasValue = sc.next();
                assert aliasType != null;
                GetInwardTransactionsService getInwardTransactionsService =
                        new GetInwardTransactionsService(new GetInwardTransactionsUseCase(repository, aliasValidator), new AliasRequest(aliasType, aliasValue, null));
                System.out.println(getInwardTransactionsService.processRequest());
                break;
            case 5:
                System.out.println("You have chosen to get outward transactions for an alias.");
                System.out.println("Enter alias type:");
                aliasType = getAliasType(sc);
                System.out.println("Enter alias value:");
                aliasValue = sc.next();
                assert aliasType != null;
                GetOutwardTransactionsService getOutwardTransactionsService =
                        new GetOutwardTransactionsService(new GetOutwardTransactionsUseCase(repository, aliasValidator), new AliasRequest(aliasType, aliasValue, null));
                System.out.println(getOutwardTransactionsService.processRequest());
                break;
            case 6:
                System.out.println("You have chosen to get all transactions for an alias.");
                System.out.println("Enter alias type:");
                aliasType = getAliasType(sc);
                System.out.println("Enter alias value:");
                aliasValue = sc.next();
                assert aliasType != null;
                GetTransactionsByAliasService getTransactionsByAliasService =
                        new GetTransactionsByAliasService(new GetTransactionsByAliasUseCase(repository, aliasValidator), new AliasRequest(aliasType, aliasValue, null));
                System.out.println(getTransactionsByAliasService.processRequest());
                break;
            case 7:
                System.out.println("You have chosen to get all saved transactions.");
                GetAllTransactionsService getAllTransactionsService =
                        new GetAllTransactionsService(new GetAllTransactionsUseCase(repository, transactionValidator, aliasValidator), null);
                System.out.println(getAllTransactionsService.processRequest());
                break;
            case 8:
                System.out.println("You have chosen to exit.");
                System.out.println("See you later!");
                exitMainMenu = true;
                return;
        }
    }

    private static String getAliasType(Scanner sc) {
        System.out.println("1. ALPHANUMERIC");
        System.out.println("2. IBAN");
        System.out.println("3. PHONE NUMBER");
        return switch (getValidatedChoice(sc, new int[]{1, 2, 3})) {
            case 1 -> "ALPHANUMERIC";
            case 2 -> "IBAN";
            case 3 -> "PHONE NUMBER";
            default -> null;
        };
    }
}


