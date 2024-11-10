import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private int pin;
    private List<String> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial deposit: " + initialDeposit);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean authenticate(int inputPin) {
        return this.pin == inputPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount + " | New Balance: " + balance);
            System.out.println("Deposit successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + " | New Balance: " + balance);
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for " + accountHolderName + " (" + accountNumber + "):");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists.");
            return;
        }

        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();

        System.out.print("Set a 4-digit PIN for your account: ");
        int pin = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Account newAccount = new Account(accountNumber, accountHolderName, initialDeposit, pin);
        accounts.put(accountNumber, newAccount);
        System.out.println("Account created successfully!");
    }

    private Account authenticateAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);

        if (account != null) {
            System.out.print("Enter PIN: ");
            int inputPin = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (account.authenticate(inputPin)) {
                return account;
            } else {
                System.out.println("Incorrect PIN.");
                return null;
            }
        } else {
            System.out.println("Account not found.");
            return null;
        }
    }

    public void depositMoney() {
        Account account = authenticateAccount();
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            account.deposit(amount);
        }
    }

    public void withdrawMoney() {
        Account account = authenticateAccount();
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            account.withdraw(amount);
        }
    }

    public void checkBalance() {
        Account account = authenticateAccount();
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Balance: " + account.getBalance());
        }
    }

    public void viewTransactionHistory() {
        Account account = authenticateAccount();
        if (account != null) {
            account.printTransactionHistory();
        }
    }
}

public class Banking_System {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Banking System Menu ***");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    bank.depositMoney();
                    break;
                case 3:
                    bank.withdrawMoney();
                    break;
                case 4:
                    bank.checkBalance();
                    break;
                case 5:
                    bank.viewTransactionHistory();
                    break;
                case 6:
                    System.out.println("Thank you for using the banking system!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
